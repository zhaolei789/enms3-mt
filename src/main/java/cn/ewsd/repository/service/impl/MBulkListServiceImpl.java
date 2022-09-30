package cn.ewsd.repository.service.impl;

import cn.ewsd.base.utils.XException;
import cn.ewsd.base.utils.jwt.UserInfo;
import cn.ewsd.common.utils.easyui.PageParam;
import cn.ewsd.common.utils.easyui.PageSet;
import cn.ewsd.common.utils.easyui.PageUtils;
import cn.ewsd.repository.mapper.MBulkListMapper;
import cn.ewsd.repository.mapper.MCheckMapper;
import cn.ewsd.repository.model.MBulkList;
import cn.ewsd.repository.model.MCheck;
import cn.ewsd.repository.service.MBulkListService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.List;

@Service("mBulkListServiceImpl")
public class MBulkListServiceImpl extends RepositoryBaseServiceImpl<MBulkList, String> implements MBulkListService {
	@Autowired
    private MBulkListMapper mBulkListMapper;
	@Autowired
    private MCheckMapper mCheckMapper;

    @Override
    public PageSet<MBulkList> getPageSet(PageParam pageParam, String filterSort, String checkNo, String siteNoQry, String matNameQry) {
        PageHelper.startPage(pageParam.getPage(), pageParam.getRows());
        List<MBulkList> list = mBulkListMapper.getBulkList(checkNo, siteNoQry, matNameQry);
        PageInfo<MBulkList> pageInfo = new PageInfo<>(list);
        return PageUtils.getPageSet(pageInfo);
    }

    @Override
    public void updateBulk(HttpServletRequest request, UserInfo userInfo) throws Exception{
        try{
            String[] listIds = request.getParameterValues("listId");

            for(int i=0; i<listIds.length; i++){
                String listId = listIds[i];
                double realAmount = 0;
                try{
                    realAmount = Double.parseDouble(request.getParameter("realAmount_"+listId));
                }catch (Exception e){
                    throw new XException(XException.ERR_DEFAULT, "实际数量必须是数字！");
                }
                String siteNo = request.getParameter("site_"+listId);
                String reason = request.getParameter("reason_"+listId);

                MBulkList mBulkList = new MBulkList();
                mBulkList.setRealAmount(new BigDecimal(realAmount+""));
                mBulkList.setSiteNo(siteNo);
                mBulkList.setReason(reason);
                mBulkList.setListId(listId);
                mBulkListMapper.updateBulk(mBulkList);
            }
        }catch (Exception e){
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            throw e;
        }
    }

    @Override
    @Transactional
    public void submitBulk(HttpServletRequest request) throws Exception{
        try{
            String[] listIds = request.getParameterValues("listId");
            String checkNo = request.getParameter("checkNo");

            MCheck mc = mCheckMapper.getCheckByNo(checkNo);
            if(mc == null){
                throw new XException(XException.ERR_DEFAULT, "盘点不存在！");
            }
            if(!"72132".equals(mc.getCheckStep())){
                throw new XException(XException.ERR_DEFAULT, "盘点已提交或已完成！");
            }
            if(listIds==null || listIds.length<1){
                throw new XException(XException.ERR_DEFAULT, "没有盘点明细！");
            }

            for(int i=0; i<listIds.length; i++){
                String listId = listIds[i];

                MBulkList mBulkList = mBulkListMapper.getBulk(listId);
                double realAmount = mBulkList.getRealAmount().doubleValue();
                double inputRealAmount = 0;
                try {
                    inputRealAmount = Double.parseDouble(request.getParameter("realAmount_"+listId));
                }catch (Exception e){

                }
                if(realAmount != inputRealAmount){
                    throw new XException(XException.ERR_DEFAULT, "请先保存，然后再提交！");
                }
            }

            MCheck mCheck = new MCheck();
            mCheck.setCheckStep("72134");
            mCheck.setCheckNo(checkNo);
            mCheckMapper.updateCheck(mCheck);

            List<MBulkList> list = mBulkListMapper.getDiffBulkList(checkNo);
            if(list.size()<1){
                mCheck.setCheckStep("7213F");
                mCheckMapper.updateCheck(mCheck);
            }
        }catch (Exception e){
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            throw e;
        }
    }

    @Override
    public List<MBulkList> getBulkList(String checkNo, String siteNoQry, String matNameQry){
        return mBulkListMapper.getBulkList(checkNo, siteNoQry, matNameQry);
    }

    @Override
    public PageSet<MBulkList> getChkPageSet(PageParam pageParam, String filterSort, String checkNoQry) {
        PageHelper.startPage(pageParam.getPage(), pageParam.getRows());
        List<MBulkList> list = mBulkListMapper.getCheckBulkList(checkNoQry);
        PageInfo<MBulkList> pageInfo = new PageInfo<>(list);
        return PageUtils.getPageSet(pageInfo);
    }

    @Override
    public PageSet<MBulkList> getInventQryPageSet(PageParam pageParam, String filterSort, String checkNoQry) {
        PageHelper.startPage(pageParam.getPage(), pageParam.getRows());
        List<MBulkList> list = mBulkListMapper.getInventQryList(checkNoQry);
        PageInfo<MBulkList> pageInfo = new PageInfo<>(list);
        return PageUtils.getPageSet(pageInfo);
    }
}
