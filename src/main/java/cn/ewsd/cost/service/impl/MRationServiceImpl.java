package cn.ewsd.cost.service.impl;

import cn.ewsd.base.utils.*;
import cn.ewsd.common.utils.easyui.PageParam;
import cn.ewsd.common.utils.easyui.PageSet;
import cn.ewsd.common.utils.easyui.PageUtils;
import cn.ewsd.cost.mapper.MRationMapper;
import cn.ewsd.cost.model.MRation;
import cn.ewsd.cost.service.MRationService;
import cn.ewsd.material.model.MMaterial;
import cn.ewsd.mdata.service.UtilService;
import cn.ewsd.repository.mapper.MOutMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Service("mRationServiceImpl")
public class MRationServiceImpl extends CostBaseServiceImpl<MRation, String> implements MRationService {
	@Autowired
	private MRationMapper mRationMapper;
	@Autowired
    private UtilService utilService;
	@Autowired
    private MOutMapper mOutMapper;

    @Override
    public PageSet<MRation> getRationPageSet(PageParam pageParam, String filterSort, String rationQry) {
        PageHelper.startPage(pageParam.getPage(), pageParam.getRows());
        List<MRation> list = mRationMapper.getRationList(rationQry);
        PageInfo<MRation> pageInfo = new PageInfo<>(list);
        return PageUtils.getPageSet(pageInfo);
    }

    @Override
    public void insertRation(HttpServletRequest request) throws Exception{
        String rationName = request.getParameter("rationName");
        String rationUnit = request.getParameter("rationUnit");
        String denom = request.getParameter("denom");
        String orderNo = request.getParameter("orderNo");
        String isUsing = request.getParameter("isUsing");
        int iOrder = 0;
        try{
            iOrder = Integer.parseInt("orderNo");
        }catch (Exception e){

        }

        if("".equals(rationName)){
            throw new XException(XException.ERR_DEFAULT, "单耗名称，不能为空！");
        }

        ArrayList<String[]> condList = new ArrayList<>();
        condList.add(new String[]{"ration_name", "=", rationName});
        boolean flag = utilService.checkColumnDataExist("m_ration", condList);
        if(flag){
            throw new XException(XException.ERR_DEFAULT, "单耗名称已存在！");
        }

        MRation mRation = new MRation();
        mRation.setRationNo(Snow.getUUID()+"");
        mRation.setRationCode("");
        mRation.setRationName(rationName);
        mRation.setRationUnit(rationUnit);
        mRation.setDenom(denom);
        mRation.setOrderNo(iOrder);
        mRation.setIsUsing(isUsing);
        mRation.setIsImport("1");
        mRation.setResSet("");
        mRationMapper.insertRation(mRation);
    }

    @Override
    public List<MRation> getRationAnalList(String rationQry){
        String nowYear = XDate.getYear();
        String befOne = String.valueOf(Integer.parseInt(nowYear) - 1);
        String befTwo = String.valueOf(Integer.parseInt(nowYear) - 2);

        List<MRation> list = mRationMapper.getRationList(rationQry);
        List<MRation> dataList = mOutMapper.getRationAnalOutList();
        DataCantainer<MRation> dc = new DataCantainer<>((ArrayList)dataList);

        for(int i=0; i<list.size(); i++){
            MRation r = list.get(i);
            String rationNo = r.getRationNo();

            DataCantainer<MRation> nowDc = dc.findDataCantainer("occ_year", nowYear, "ration_no", rationNo);
            r.setNowAmount(Data.normalToFinal(nowDc.getSum("amount"), 4));
            r.setNowBala(Data.normalToFinal(nowDc.getSum("bala"), 2));
            r.setNowRawAmount(Data.normalToFinal(nowDc.getSum("raw_amount"), 4));
            r.setNowRawBala(Data.normalToFinal(nowDc.getSum("raw_bala"), 4));

            DataCantainer<MRation> oneDc = dc.findDataCantainer("occ_year", befOne, "ration_no", rationNo);
            r.setOneAmount(Data.normalToFinal(oneDc.getSum("amount"), 4));
            r.setOneBala(Data.normalToFinal(oneDc.getSum("bala"), 2));
            r.setOneRawAmount(Data.normalToFinal(oneDc.getSum("raw_amount"), 4));
            r.setOneRawBala(Data.normalToFinal(oneDc.getSum("raw_bala"), 4));

            DataCantainer<MRation> twoDc = dc.findDataCantainer("occ_year", befTwo, "ration_no", rationNo);
            r.setTwoAmount(Data.normalToFinal(twoDc.getSum("amount"), 4));
            r.setTwoBala(Data.normalToFinal(twoDc.getSum("bala"), 2));
            r.setTwoRawAmount(Data.normalToFinal(twoDc.getSum("raw_amount"), 4));
            r.setTwoRawBala(Data.normalToFinal(twoDc.getSum("raw_bala"), 4));
        }

        return list;
    }

    @Override
    public MRation getRationByNo(String rationNo){
        return mRationMapper.getRationByNo(rationNo);
    }

    @Override
    public void updateRation(MRation mRation) throws Exception{
        if("".equals(mRation.getRationName()) || mRation.getRationName()==null){
            throw new XException(XException.ERR_DEFAULT, "单耗名称，不能为空！");
        }
        ArrayList<String[]> condList = new ArrayList<>();
        condList.add(new String[]{"ration_name", "=", mRation.getRationName()});
        condList.add(new String[]{"ration_no", "!=", mRation.getRationNo()});
        boolean flag = utilService.checkColumnDataExist("m_ration", condList);
        if(flag){
            throw new XException(XException.ERR_DEFAULT, "单耗名称，已存在！");
        }

        mRationMapper.updateRation(mRation);
    }

    @Override
    @Transactional
    public void deleteRation(HttpServletRequest request) throws Exception{
        try {
            String rationNo = request.getParameter("rationNo");

            if(rationNo !=null && !"".equals(rationNo)){
                mRationMapper.deleteRationMat(rationNo);
                mRationMapper.deleteRation(rationNo);
            }
        }catch (Exception e){
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            throw e;
        }
    }

    @Override
    public PageSet<MMaterial> getRationMatPageSet(PageParam pageParam, String filterSort, String rationNo, String matQry) {
        PageHelper.startPage(pageParam.getPage(), pageParam.getRows());
        List<MMaterial> list = mRationMapper.getRationMatList(rationNo, matQry);
        PageInfo<MMaterial> pageInfo = new PageInfo<>(list);
        return PageUtils.getPageSet(pageInfo);
    }

    @Override
    public List<MMaterial> getMatForSelect(String q){
        return mRationMapper.getMatForSelect(q);
    }

    @Override
    public void insertMat(HttpServletRequest request) throws Exception{
        String matNo = request.getParameter("matNo1");
        String rationNo = request.getParameter("rationNo1");
        if("".equals(rationNo)){
            throw new XException(XException.ERR_DEFAULT, "请先选择要添加材料的项目！");
        }
        if("".equals(matNo)){
            throw new XException(XException.ERR_DEFAULT, "请先选择要添加的材料！");
        }

        mRationMapper.insertRationMat(matNo, rationNo);
    }

    @Override
    public void deleteMat(HttpServletRequest request) throws Exception{
        try {
            String[] matNo = request.getParameterValues("matNo");
            String rationNo = request.getParameter("rationNo1");

            if(matNo !=null && !"".equals(matNo)){
                mRationMapper.deleteRationMat1(rationNo, matNo);
            }
        }catch (Exception e){
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            throw e;
        }
    }
}
