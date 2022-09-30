package cn.ewsd.material.service.impl;

import cn.ewsd.base.utils.Snow;
import cn.ewsd.base.utils.XDate;
import cn.ewsd.base.utils.XException;
import cn.ewsd.base.utils.jwt.UserInfo;
import cn.ewsd.common.utils.easyui.PageParam;
import cn.ewsd.common.utils.easyui.PageSet;
import cn.ewsd.common.utils.easyui.PageUtils;
import cn.ewsd.material.mapper.ClassConsumeMapper;
import cn.ewsd.material.mapper.MAccountMatMapper;
import cn.ewsd.material.mapper.MTeamStockLogMapper;
import cn.ewsd.material.mapper.MTeamStockMapper;
import cn.ewsd.material.model.*;
import cn.ewsd.material.service.ClassConsumeService;
import cn.ewsd.repository.mapper.MTeamBillMapper;
import cn.ewsd.repository.model.MTeamBill;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.List;

@Service("classConsumeServiceImpl")
public class ClassConsumeServiceImpl implements ClassConsumeService {
	@Autowired
	private ClassConsumeMapper classConsumeMapper;
	@Autowired
    private MTeamStockMapper mTeamStockMapper;
	@Autowired
    private MAccountMatMapper mAccountMatMapper;
	@Autowired
    private MTeamBillMapper mTeamBillMapper;
	@Autowired
    private MTeamStockLogMapper mTeamStockLogMapper;

    @Override
    public PageSet<ConsumeStock> getPageSet(PageParam pageParam, String filterSort, String userTeam, String occDate1Qry, String occDate2Qry, String deptNoQry) {
        PageHelper.startPage(pageParam.getPage(), pageParam.getRows());
        List<ConsumeStock> list = classConsumeMapper.getPageSet(userTeam, occDate1Qry, occDate2Qry, deptNoQry);
        PageInfo<ConsumeStock> pageInfo = new PageInfo<>(list);
        return PageUtils.getPageSet(pageInfo);
    }

    @Override
    public PageSet<ConsumeStock> getPageSetXzcl(PageParam pageParam, String filterSort, String teamNo, String storeNoQry, String statusQry, String deptNoQry, String occDate2Qry, String prjNoQry, String matCodeQry, String matNameQry) {
        PageHelper.startPage(pageParam.getPage(), pageParam.getRows());
        List<ConsumeStock> list = classConsumeMapper.getPageSetXzcl(teamNo, storeNoQry, statusQry, deptNoQry, occDate2Qry, prjNoQry, matCodeQry, matNameQry);
        PageInfo<ConsumeStock> pageInfo = new PageInfo<>(list);
        return PageUtils.getPageSet(pageInfo);
    }

    @Override
    @Transactional
    public void saveMat(HttpServletRequest request, UserInfo userInfo) throws Exception{
        try {
            String prjNoQry = request.getParameter("prjNoQry");
            if("".equals(prjNoQry)){
                throw new XException(XException.ERR_DEFAULT, "班组消耗，必须选择具体、准确的工程项目！");
            }
            String deptNoQry = request.getParameter("deptNoQry");
            String teamNo = userInfo.getOrgId();
            String occDate2Qry = XDate.dateTo8(request.getParameter("occDate2Qry"));
            String storeNoQry = request.getParameter("storeNoQry");
            String useAddrQry = request.getParameter("useAddrQry");
            String statusQry = request.getParameter("statusQry");
            String ifAssess = "0";
            String ifStock = "1";
            String userNo = userInfo.getUuid();

            String[] matNos = request.getParameterValues("matNo");
            for(int i=0; i<matNos.length; i++){
                String matNo = matNos[i];
                double matAmount = 0;
                try {
                    String amt = request.getParameter("amt_"+matNo);
                    if("".equals(amt)){
                        amt = "0";
                    }
                    matAmount = Double.parseDouble(amt);
                }catch (Exception e){
                    throw new XException(XException.ERR_DEFAULT, "消耗数必须是数字！");
                }
                if(matAmount <= 0){
                    continue;
                }
                String itemNo = request.getParameter("item_"+matNo);
                if("".equals(itemNo)){
                    throw new XException(XException.ERR_DEFAULT, "请选择科目！");
                }
                String remark = request.getParameter("rmk_"+matNo);

                double matPrice = classConsumeMapper.getPrice(teamNo, matNo);
                if(matPrice<0){
                    matPrice = 0;
                }

                MTeamStock mTeamStock = mTeamStockMapper.getTeamStock(teamNo, storeNoQry, matNo, statusQry);
                BigDecimal stockAmount = mTeamStock.getMatAmount();
                if(stockAmount.compareTo(new BigDecimal(matAmount+"")) == -1){
                    throw new XException(XException.ERR_DEFAULT, "库存不足！");
                }

                String accType = "r.accountType.1";
                MAccountMat mAccountMat = mAccountMatMapper.getAccountMat(teamNo, matNo);
                if(mAccountMat!=null){
                    accType = mAccountMat.getAccountType();
                }

                String billNo = Snow.getUUID()+"";
                MTeamBill mTeamBill = new MTeamBill();
                mTeamBill.setBillNo(billNo);
                mTeamBill.setStoreNo(storeNoQry);
                mTeamBill.setTeamNo(Integer.parseInt(teamNo));
                mTeamBill.setDeptNo(Integer.parseInt(deptNoQry));
                mTeamBill.setMatNo(matNo);
                mTeamBill.setOccAmount(new BigDecimal(matAmount+""));
                mTeamBill.setMatPrice(new BigDecimal(matPrice+""));
                mTeamBill.setOccDate(occDate2Qry);
                mTeamBill.setPrjNo(prjNoQry);
                mTeamBill.setDrawEmp("");
                mTeamBill.setUseAddr(useAddrQry);
                mTeamBill.setNormNo("0");
                mTeamBill.setIfAssess(ifAssess);
                mTeamBill.setIfStock(ifStock);
                mTeamBill.setRemark(remark);
                mTeamBill.setModiEmp(userNo);
                mTeamBill.setModiDate(XDate.getDate());
                mTeamBill.setModiTime(XDate.getTime());
                mTeamBill.setDataSrc("m.dataSrc.7");
                mTeamBill.setAccountType(accType);
                mTeamBill.setMatStatus(statusQry);
                mTeamBill.setItemNo(itemNo);
                mTeamBillMapper.saveTeamBill(mTeamBill);

                MTeamStock ts = new MTeamStock();
                ts.setTeamNo(Integer.parseInt(teamNo));
                ts.setStoreNo(storeNoQry);
                ts.setMatNo(matNo);
                ts.setMatStatus(statusQry);
                ts.setMatAmount(new BigDecimal(-matAmount+""));
                ts.setUseAmount(new BigDecimal(0+""));
                int c = mTeamStockMapper.updateTeamStock(ts);

                if(c<1){
                    ts.setSiteCode("");
                    mTeamStockMapper.insertTeamStock(ts);
                }

                MTeamStock mTeamStock1 = mTeamStockMapper.getTeamStock(teamNo, storeNoQry, matNo, statusQry);
                BigDecimal ma = mTeamStock1.getMatAmount();
                if(ma.compareTo(new BigDecimal("0")) == -1){
                    throw new XException(XException.ERR_DEFAULT, "总数不能小于0");
                }

                BigDecimal occBala = new BigDecimal(matPrice+"").multiply(new BigDecimal(-matAmount+""));

                MTeamStockLog mTeamStockLog = new MTeamStockLog();
                mTeamStockLog.setLogId(Snow.getUUID()+"");
                mTeamStockLog.setStoreNo(storeNoQry);
                mTeamStockLog.setTeamNo(Integer.parseInt(teamNo));
                mTeamStockLog.setMatNo(matNo);
                mTeamStockLog.setOccDate(occDate2Qry);
                mTeamStockLog.setOccAmount(new BigDecimal(-matAmount+""));
                mTeamStockLog.setOccBala(occBala);
                mTeamStockLog.setInoutFlag("r.inOutFlag.2");
                mTeamStockLog.setDataSrc("m.dataSrc.7");
                mTeamStockLog.setBillNo(billNo);
                mTeamStockLog.setMatStatus(statusQry);
                mTeamStockLogMapper.insertTeamStockLog(mTeamStockLog);
            }

        }catch (Exception e){
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            throw e;
        }
    }

    @Override
    @Transactional
    public void delete(HttpServletRequest request, UserInfo userInfo) throws Exception{
        try {
            String[] billNos = request.getParameterValues("billNo");
            if(billNos==null || billNos.length<1){
                throw new XException(XException.ERR_DEFAULT, "请选择要删除的记录！");
            }

            for(int i=0; i<billNos.length; i++){
                String billNo = billNos[i];

                MTeamBill mTeamBill = mTeamBillMapper.getTeamBill(billNo);
                String matNo  = mTeamBill.getMatNo();
                String clasNo = mTeamBill.getDeptNo()+"";
                String storeNo= mTeamBill.getStoreNo();
                String teamNo = userInfo.getOrgId();
                BigDecimal matAmount = mTeamBill.getOccAmount();
                String occDate   = mTeamBill.getOccDate();
                BigDecimal matPrice  = mTeamBill.getMatPrice();
                String ifStock   = mTeamBill.getIfStock();
                String matStatus = mTeamBill.getMatStatus();

                mTeamBillMapper.deleteTeamBill(billNo);

                MTeamStock ts = new MTeamStock();
                ts.setTeamNo(Integer.parseInt(teamNo));
                ts.setStoreNo(storeNo);
                ts.setMatNo(matNo);
                ts.setMatStatus(matStatus);
                ts.setMatAmount(matAmount);
                ts.setUseAmount(new BigDecimal("0"));
                int c = mTeamStockMapper.updateTeamStock(ts);

                if(c<1){
                    ts.setSiteCode("");
                    mTeamStockMapper.insertTeamStock(ts);
                }

                MTeamStock mTeamStock1 = mTeamStockMapper.getTeamStock(teamNo, storeNo, matNo, matStatus);
                BigDecimal ma = mTeamStock1.getMatAmount();
                if(ma.compareTo(new BigDecimal("0")) == -1){
                    throw new XException(XException.ERR_DEFAULT, "总数不能小于0");
                }

                BigDecimal occBala = matAmount.multiply(matPrice);

                MTeamStockLog mTeamStockLog = new MTeamStockLog();
                mTeamStockLog.setLogId(Snow.getUUID()+"");
                mTeamStockLog.setStoreNo(storeNo);
                mTeamStockLog.setTeamNo(Integer.parseInt(teamNo));
                mTeamStockLog.setMatNo(matNo);
                mTeamStockLog.setOccDate(occDate);
                mTeamStockLog.setOccAmount(matAmount);
                mTeamStockLog.setOccBala(occBala);
                mTeamStockLog.setInoutFlag("r.inOutFlag.2");
                mTeamStockLog.setDataSrc("m.dataSrc.7");
                mTeamStockLog.setBillNo(billNo);
                mTeamStockLog.setMatStatus(matStatus);
                mTeamStockLogMapper.insertTeamStockLog(mTeamStockLog);
            }

        }catch (Exception e){
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            throw e;
        }
    }

    @Override
    public List<MItem> getItem(String teamNo, String month){
        return classConsumeMapper.getItem(teamNo, month);
    }
}
