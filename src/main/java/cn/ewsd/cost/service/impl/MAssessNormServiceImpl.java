package cn.ewsd.cost.service.impl;

import cn.ewsd.base.utils.Data;
import cn.ewsd.base.utils.Snow;
import cn.ewsd.base.utils.XDate;
import cn.ewsd.base.utils.XException;
import cn.ewsd.base.utils.jwt.LoginInfo;
import cn.ewsd.common.utils.easyui.PageParam;
import cn.ewsd.common.utils.easyui.PageSet;
import cn.ewsd.common.utils.easyui.PageUtils;
import cn.ewsd.cost.mapper.MAssessNormMapper;
import cn.ewsd.cost.mapper.MFeeItemMapper;
import cn.ewsd.cost.model.MAssessNorm;
import cn.ewsd.cost.model.MFeeItem;
import cn.ewsd.cost.service.MAssessNormService;
import cn.ewsd.cost.service.MFeeItemService;
import cn.ewsd.material.mapper.MPrjMapper;
import cn.ewsd.material.model.MPrj;
import cn.ewsd.mdata.service.UtilService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service("mAssessNormServiceImpl")
public class MAssessNormServiceImpl extends CostBaseServiceImpl<MAssessNorm, String> implements MAssessNormService {
	@Autowired
	private MAssessNormMapper assessNormMapper;
	@Autowired
    private MPrjMapper mPrjMapper;

    @Override
    public PageSet<MAssessNorm> getTeamItemQuotaPageSet(PageParam pageParam, String filterSort, String assTeam, String teamNoQry, String prjQry) {
        PageHelper.startPage(pageParam.getPage(), pageParam.getRows());
        List<MAssessNorm> list = assessNormMapper.getTeamItemQuotaList(assTeam, teamNoQry, prjQry);
        PageInfo<MAssessNorm> pageInfo = new PageInfo<>(list);
        return PageUtils.getPageSet(pageInfo);
    }

    @Override
    public void saveAssessNorm(HttpServletRequest request) throws Exception{
        String teamNoQry = request.getParameter("teamNoQry");
        String prjQry = request.getParameter("prjQry");
        String itemNo = request.getParameter("itemNo");
        prjQry = "".equals(prjQry) ? "0" : prjQry;
        double amount = 0;
        double price = 0;
        try {
            amount = Double.parseDouble(request.getParameter("amount"));
        }catch (Exception e){
            throw new XException(XException.ERR_DEFAULT, "??????????????????????????????");
        }
        try {
            price = Double.parseDouble(request.getParameter("price"));
        }catch (Exception e){
            throw new XException(XException.ERR_DEFAULT, "??????????????????????????????");
        }
        if ("".equals(teamNoQry)) {
            throw new XException(XException.ERR_DEFAULT, "??????????????????????????????");
        }
        if ("".equals(itemNo)) {
            throw new XException(XException.ERR_DEFAULT, "????????????????????????");
        }
        if (amount < 0 || price < 0) {
            throw new XException(XException.ERR_DEFAULT, "????????????????????????????????????????????????");
        }
        if (amount == 0 && price == 0) {
            throw new XException(XException.ERR_DEFAULT, "????????????????????????????????????????????????");
        }

        MAssessNorm mAssessNorm = new MAssessNorm();
        mAssessNorm.setNormAmount(new BigDecimal(amount));
        mAssessNorm.setNormPrice(new BigDecimal(price));
        mAssessNorm.setModiDate(XDate.getDate());
        mAssessNorm.setItemNo(itemNo);
        mAssessNorm.setTeamNo(teamNoQry);
        mAssessNorm.setPrjNo(prjQry);
        mAssessNorm.setAssTeam(LoginInfo.getOrgId());
        int cnt = assessNormMapper.updateAssessNorm(mAssessNorm);
        if(cnt < 1){
            MPrj prj = mPrjMapper.getPrjByPrjNo(prjQry);
            mAssessNorm.setNormId(Snow.getUUID()+"");
            mAssessNorm.setBeginMonth("");
            mAssessNorm.setPrjName(prj==null ? "" : prj.getPrjName());
            mAssessNorm.setModiEmp(LoginInfo.getUuid());
            assessNormMapper.insertAssessNorm(mAssessNorm);
        }
    }

    @Override
    @Transactional
    public void updateAssessNorm(HttpServletRequest request) throws Exception{
        try {
            String[] normIds = request.getParameterValues("normId");
            if(normIds==null || normIds.length<1){
                throw new XException(XException.ERR_DEFAULT, "?????????????????????????????????");
            }

            for(int i=0; i<normIds.length; i++){
                String normId = normIds[i];
                double amount = 0;
                double price = 0;

                try {
                    amount = Double.parseDouble(request.getParameter("amount_"+normId));
                }catch (Exception e){
                    throw new XException(XException.ERR_DEFAULT, "??????????????????????????????");
                }
                try {
                    price = Double.parseDouble(request.getParameter("price_"+normId));
                }catch (Exception e){
                    throw new XException(XException.ERR_DEFAULT, "??????????????????????????????");
                }
                if (amount < 0 || price < 0) {
                    throw new XException(XException.ERR_DEFAULT, "????????????????????????????????????????????????");
                }
                if (amount == 0 && price == 0) {
                    throw new XException(XException.ERR_DEFAULT, "????????????????????????????????????????????????");
                }

                MAssessNorm mAssessNorm = new MAssessNorm();
                mAssessNorm.setNormAmount(new BigDecimal(amount));
                mAssessNorm.setNormPrice(new BigDecimal(price));
                mAssessNorm.setModiDate(XDate.getDate());
                mAssessNorm.setNormId(normId);
                assessNormMapper.updateAssessNorm(mAssessNorm);
            }
        }catch (Exception e){
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            throw e;
        }
    }

    @Override
    public void deleteAssessNorm(HttpServletRequest request) throws Exception{
        String[] normIds = request.getParameterValues("normId");
        if(normIds==null || normIds.length<1){
            throw new XException(XException.ERR_DEFAULT, "?????????????????????????????????");
        }

        try {
            for(int i=0; i<normIds.length; i++) {
                String normId = normIds[i];

                assessNormMapper.deleteAssessNorm(normId);
            }
        }catch (Exception e){
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            throw e;
        }
    }
}
