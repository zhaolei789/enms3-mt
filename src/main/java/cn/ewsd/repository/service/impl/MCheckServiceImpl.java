package cn.ewsd.repository.service.impl;

import cn.ewsd.base.utils.Snow;
import cn.ewsd.base.utils.XDate;
import cn.ewsd.base.utils.XException;
import cn.ewsd.base.utils.jwt.UserInfo;
import cn.ewsd.common.utils.easyui.PageParam;
import cn.ewsd.common.utils.easyui.PageSet;
import cn.ewsd.common.utils.easyui.PageUtils;
import cn.ewsd.material.mapper.MMaterialMapper;
import cn.ewsd.material.mapper.MStockMapper;
import cn.ewsd.material.model.MStock;
import cn.ewsd.material.service.MStockService;
import cn.ewsd.repository.mapper.MBulkListMapper;
import cn.ewsd.repository.mapper.MCheckBillMapper;
import cn.ewsd.repository.mapper.MCheckMapper;
import cn.ewsd.repository.mapper.MInMapper;
import cn.ewsd.repository.model.MBulkList;
import cn.ewsd.repository.model.MCheck;
import cn.ewsd.repository.model.MCheckBill;
import cn.ewsd.repository.model.MIn;
import cn.ewsd.repository.service.MCheckService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.List;

@Service("mCheckServiceImpl")
public class MCheckServiceImpl extends RepositoryBaseServiceImpl<MCheck, String> implements MCheckService {
	@Autowired
	private MCheckMapper mCheckMapper;
	@Autowired
    private MStockMapper mStockMapper;
	@Autowired
    private MBulkListMapper mBulkListMapper;
	@Autowired
    private MMaterialMapper mMaterialMapper;
	@Autowired
    private MStockService mStockService;
	@Autowired
    private MCheckBillMapper mCheckBillMapper;
	@Autowired
    private MInMapper mInMapper;

    @Override
    public PageSet<MCheck> getPageSet(PageParam pageParam, String filterSort, String yearQry, String userId) {
        PageHelper.startPage(pageParam.getPage(), pageParam.getRows());
        List<MCheck> list = mCheckMapper.getCheckList(yearQry, userId);
        PageInfo<MCheck> pageInfo = new PageInfo<>(list);
        return PageUtils.getPageSet(pageInfo);
    }

    @Override
    @Transactional
    public int insertCheck(MCheck mCheck){
        List<MStock> stockList = mStockMapper.getStockByStoreNo(mCheck.getStoreNo());
        for(int i=0; i< stockList.size(); i++){
            MStock mStock = stockList.get(i);

            MBulkList mBulkList = new MBulkList();
            mBulkList.setListId(Snow.getUUID()+"");
            mBulkList.setCheckNo(mCheck.getCheckNo());
            mBulkList.setMatNo(mStock.getMatNo());
            mBulkList.setStoreNo(mStock.getStoreNo());
            mBulkList.setSiteNo(mStock.getSiteCode());
            mBulkList.setTheoryAmount(mStock.getStockAmount());
            mBulkList.setRealAmount(mStock.getStockAmount());
            mBulkListMapper.insertBulk(mBulkList);
        }

        return mCheckMapper.insertCheck(mCheck);
    }

    @Transactional
    @Override
    public int deleteCheck(String checkNo){
        mBulkListMapper.deleteBulkByCheckNo(checkNo);
        return mCheckMapper.deleteCheckByNo(checkNo);
    }

    @Override
    public MCheck getCheckByNo(String checkNo){
        return mCheckMapper.getCheckByNo(checkNo);
    }

    @Override
    public List<MCheck> getCheckSet(String checkStep){
        return mCheckMapper.getCheckSet(checkStep);
    }

    @Override
    @Transactional
    public int deleteBulk(String listId, String checkNo){
        int ret = mBulkListMapper.deleteBulkByListId(listId);
        int c = mBulkListMapper.getBulkCount(checkNo);
        if(c < 1){
            mBulkListMapper.deleteBulkByCheckNo(checkNo);
            mCheckMapper.deleteCheckByNo(checkNo);
        }

        return ret;
    }

    @Override
    @Transactional
    public void finishCheck(HttpServletRequest request, UserInfo userInfo) throws Exception{
        try{
            String checkNo = request.getParameter("checkNoQry");
            String[] listIds = request.getParameterValues("listId");

            for(int i=0; i<listIds.length; i++){
                String listId = listIds[i];
                double inputRealAmount = 0;
                try{
                    inputRealAmount = Double.parseDouble(request.getParameter("realAmount_"+listId));
                }catch (Exception e){
                    throw new XException(XException.ERR_DEFAULT, "实际数量必须是数字！");
                }
                String siteNo = request.getParameter("site_"+listId);
                String reason = request.getParameter("reason_"+listId);

                MBulkList mBulkList = new MBulkList();
                mBulkList.setListId(listId);
                mBulkList.setReason(reason);
                mBulkList.setSiteNo(siteNo);
                mBulkList.setRealAmount(new BigDecimal(inputRealAmount+""));
                mBulkList.setCheckEmp(userInfo.getUuid());
                mBulkListMapper.updateBulk(mBulkList);
            }

            List<MBulkList> list = mBulkListMapper.getBulkStock(checkNo);
            for(int i=0; i<list.size(); i++){
                MBulkList mBulkList = list.get(i);
                String listId = mBulkList.getListId();
                String matNo = mBulkList.getMatNo();
                String storeNo = mBulkList.getStoreNo();
                String siteNo = mBulkList.getSiteNo();
                BigDecimal theoryAmount = mBulkList.getTheoryAmount();
                BigDecimal realAmount = mBulkList.getRealAmount();
                BigDecimal stockAmount = mBulkList.getStockAmount();
                BigDecimal diffAmount = theoryAmount.subtract(realAmount);
                BigDecimal matPrice = mMaterialMapper.getMatByNo(matNo).getMatPrice();
                stockAmount = stockAmount.subtract(diffAmount);

                if(diffAmount.compareTo(new BigDecimal("0")) == 1){
                    mStockMapper.updStock(diffAmount, stockAmount, diffAmount, siteNo, storeNo, matNo);
                }else{
                    mStockMapper.updStock1(diffAmount.multiply(new BigDecimal("-1")), stockAmount, diffAmount.multiply(new BigDecimal("-1")), siteNo, storeNo, matNo);
                }

                mStockService.checkStockAmount(matNo, storeNo);

                String remark = diffAmount.compareTo(new BigDecimal("0")) == 1 ? "库存盘点，红冲" : "库存盘点，蓝补";
                String inType = diffAmount.compareTo(new BigDecimal("0")) == 1 ? "r.inBillType.5" : "r.inBillType.4";
                BigDecimal amount = diffAmount.multiply(new BigDecimal("0"));

                if(diffAmount.compareTo(new BigDecimal("0")) != 0){
                    String billNo = Snow.getUUID()+"";
                    MCheckBill mCheckBill = new MCheckBill();
                    mCheckBill.setBillNo(billNo);
                    mCheckBill.setCheckNo(checkNo);
                    mCheckBill.setStoreNo(storeNo);
                    mCheckBill.setMatNo(matNo);
                    mCheckBill.setBillType(inType);
                    mCheckBill.setMatAmount(amount);
                    mCheckBill.setOccDate(XDate.getDate());
                    mCheckBill.setOccTime(XDate.getTime());
                    mCheckBill.setCheckEmp(userInfo.getUuid());
                    mCheckBillMapper.insertCheckBill(mCheckBill);

                    String inStep = "7201F";
                    String dataSrc = "m.dataSrc.5";
                    MIn mIn = new MIn();
                    mIn.setBillNo(billNo);
                    mIn.setInType(inType);
                    mIn.setPlanSrc("");
                    mIn.setPlanNo("");
                    mIn.setTeamNo(Integer.parseInt(userInfo.getOrgId()));
                    mIn.setStoreNo(storeNo);
                    mIn.setReserveNo("");
                    mIn.setMatNo(matNo);
                    mIn.setApplyAmount(amount);
                    mIn.setApplyDate(XDate.getDate());
                    mIn.setApplyEmp(userInfo.getUuid());
                    mIn.setInAmount(amount);
                    mIn.setInDate(XDate.getDate());
                    mIn.setInEmp(userInfo.getUuid());
                    mIn.setBillAmount(amount);
                    mIn.setSetPrice(matPrice);
                    mIn.setBillDate(XDate.getDate());
                    mIn.setBillEmp(userInfo.getUuid());
                    mIn.setOfferNo("");
                    mIn.setInStep(inStep);
                    mIn.setCheckNo(checkNo);
                    mIn.setRemark(remark);
                    mIn.setDataSrc(dataSrc);
                    mIn.setPrice1(new BigDecimal("0"));
                    mIn.setPrice2(new BigDecimal("0"));
                    mIn.setPrice3(new BigDecimal("0"));
                    mIn.setLinkNo(listId);
                    mIn.setNormNo("");
                    mIn.setReserve1("");
                    mIn.setReserve2("");
                    mIn.setReserve3("");
                    mIn.setReserve4("");
                    mIn.setErpBill("");
                    mInMapper.saveIn(mIn);
                }
            }

            mBulkListMapper.updBulk(userInfo.getUuid(), checkNo);
            MCheck mCheck = new MCheck();
            mCheck.setCheckStep("7213F");
            mCheck.setCheckNo(checkNo);
            mCheckMapper.updateCheck(mCheck);
        }catch (Exception e){
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            throw e;
        }
    }
}
