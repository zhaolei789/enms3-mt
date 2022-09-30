package cn.ewsd.material.service.impl;

import cn.ewsd.base.utils.BaseUtils;
import cn.ewsd.base.utils.Snow;
import cn.ewsd.base.utils.XDate;
import cn.ewsd.base.utils.XException;
import cn.ewsd.base.utils.jwt.UserInfo;
import cn.ewsd.common.utils.easyui.PageParam;
import cn.ewsd.common.utils.easyui.PageSet;
import cn.ewsd.common.utils.easyui.PageUtils;
import cn.ewsd.material.mapper.MAskCodeMapper;
import cn.ewsd.material.mapper.MMakeFormMapper;
import cn.ewsd.material.mapper.MMaterialMapper;
import cn.ewsd.material.mapper.MStockMapper;
import cn.ewsd.material.model.MAskCode;
import cn.ewsd.material.model.MMakeForm;
import cn.ewsd.material.model.MMaterial;
import cn.ewsd.material.model.MStock;
import cn.ewsd.material.service.MAskCodeService;
import cn.ewsd.material.service.MMaterialService;
import cn.ewsd.repository.mapper.MInMapper;
import cn.ewsd.repository.mapper.MStoreMapper;
import cn.ewsd.repository.model.MIn;
import cn.ewsd.repository.model.MStore;
import cn.ewsd.system.mapper.TCheckMapper;
import cn.ewsd.system.model.TCheck;
import cn.ewsd.system.service.ConfigService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.List;

@Service("mAskCodeServiceImpl")
public class MAskCodeServiceImpl extends MaterialBaseServiceImpl<MAskCode, String> implements MAskCodeService {
	@Autowired
	private MAskCodeMapper mAskCodeMapper;
	@Autowired
    private MMakeFormMapper mMakeFormMapper;
	@Autowired
    private MMaterialMapper mMaterialMapper;
	@Autowired
    private TCheckMapper tCheckMapper;
	@Autowired
    private MMaterialService mMaterialService;
	@Autowired
    private MStoreMapper mStoreMapper;
	@Autowired
    private ConfigService configService;
	@Autowired
    private MInMapper mInMapper;
	@Autowired
    private MStockMapper mStockMapper;

    @Override
    public PageSet<MAskCode> getPageSet(PageParam pageParam, String filterSort, String occDate1Qry, String occDate2Qry, String userTeam, String askNameQry) {
        PageHelper.startPage(pageParam.getPage(), pageParam.getRows());
        List<MAskCode> list = mAskCodeMapper.getMAskCodeList(occDate1Qry, occDate2Qry, userTeam, askNameQry);
        PageInfo<MAskCode> pageInfo = new PageInfo<>(list);
        return PageUtils.getPageSet(pageInfo);
    }

    @Override
    public List<MMakeForm> getMakeFormList(String askNo, String matQry){
        return mMakeFormMapper.getMakeFormList(matQry, askNo);
    }

    @Override
    @Transactional
    public void saveAskCode(HttpServletRequest request, UserInfo userInfo, MAskCode mAskCode) throws Exception{
        try {
            String teamNo = userInfo.getOrgId();
            String askName = mAskCode.getAskName();
            if("".equals(askName)){
                throw new XException(XException.ERR_DEFAULT, "请选择加工材料！");
            }
            String matCode = "";
            if(askName.indexOf("|") >= 0 && !"".equals(askName)){
                String[] str = askName.split("\\|");
                askName = str[1];
                matCode = str[0];
                mAskCode.setAskName(askName);
            }

            if(!"".equals(matCode)){
                MAskCode mAskCode1 = mAskCodeMapper.getAskCode(askName);
                mAskCode.setAskModel(mAskCode1==null ? "" : mAskCode1.getAskModel());
                mAskCode.setAskUnit(mAskCode1==null ? "" : mAskCode1.getAskUnit());
            }

            if(mAskCode.getUseAddr()==null || "".equals(mAskCode.getUseAddr())){
                throw new XException(XException.ERR_DEFAULT, "使用地点，必须输入！");
            }
            if(mAskCode.getAskAmount()==null || mAskCode.getAskAmount().compareTo(new BigDecimal("0")) <= 0){
                throw new XException(XException.ERR_DEFAULT, "申请数量必须是大于0的数字！");
            }
            if("".equals(mAskCode.getPrjNo())){
                mAskCode.setPrjNo("0");
            }
            if(mAskCode.getMakeTeam()==null){
                throw new XException(XException.ERR_DEFAULT, "请选择加工单位！");
            }
            if(mAskCode.getCheckTeam()==25){
                throw new XException(XException.ERR_DEFAULT, "经营管理科不能作为业务科室！");
            }
            String matNos = request.getParameter("matNos");
            if("".equals(matNos)){
                throw new XException(XException.ERR_DEFAULT, "请至少添加一条构成材料！");
            }
            if("0".equals(mAskCode.getNeedType()) && "".equals(mAskCode.getNeedDate())){
                throw new XException(XException.ERR_DEFAULT, "预期类型为普通时，必须选择回收预期！");
            }
            if("0".equals(mAskCode.getNeedType())){
                String nDate = XDate.dateTo8(mAskCode.getNeedDate());
                if(XDate.diffDate(nDate, XDate.getDate()) > 180){
                    throw new XException(XException.ERR_DEFAULT, "回收预期最长180天！");
                }
                mAskCode.setNeedDate(nDate);
            }else{
                if(!"".equals(mAskCode.getNeedDate())){
                    throw new XException(XException.ERR_DEFAULT, "非普通预期类型，不需要选择回收预期！");
                }
                mAskCode.setNeedDate("");
            }
            if(mAskCode.getCheckTeam()==null){
                throw new XException(XException.ERR_DEFAULT, "请选择业务科室！");
            }

            String matName = (askName+ " " + mAskCode.getAskModel()).trim();

            MMaterial material = mMaterialMapper.getMaterialByNameOrCode(matCode, matName);
            if(material!=null){
                mAskCode.setMatNo(material.getMatNo());
                mAskCode.setAskUnit(material.getMatUnit());
                mAskCode.setMatFee(material.getMatPrice());
                mAskCode.setPayFee(material.getPayFee());
                mAskCode.setOtherFee(material.getOtherFee());
            }else{
                mAskCode.setMatNo("0");
                mAskCode.setMatFee(new BigDecimal("0"));
                mAskCode.setPayFee(new BigDecimal("0"));
                mAskCode.setOtherFee(new BigDecimal("0"));
            }
            mAskCode.setAskNo(Snow.getUUID()+"");
            mAskCode.setMatWeight(new BigDecimal("0"));
            mAskCode.setAskDate(XDate.getDate());
            mAskCode.setAskTeam(Integer.parseInt(teamNo));
            mAskCode.setPayTeam(Integer.parseInt(teamNo));
            mAskCode.setCheckAmount(mAskCode.getAskAmount());
            mAskCode.setIfSelf("1");
            mAskCode.setAskStep("73000");
            mAskCode.setCheckNo(Snow.getUUID()+"");
            mAskCode.setDoDate("");
            mAskCode.setQuotaId("0");
            mAskCode.setIfOldmat("");
            mAskCode.setPriceStep("");
            mAskCodeMapper.executeSave(mAskCode);

            String[] mNos = matNos.split(",");

            for(int i=0; i<mNos.length; i++){
                String matNo = request.getParameter("matNo_"+mNos[i]);
                if(matNo==null || "".equals(matNo)){
                    continue;
                }
                double amount = 0;
                try {
                    amount = Double.parseDouble(request.getParameter("amount_"+mNos[i]));
                }catch (Exception e){
                    throw new XException(XException.ERR_DEFAULT, "数量必须是数字！");
                }
                if(amount <= 0){
                    throw new XException(XException.ERR_DEFAULT, "数量必须是大于0！");
                }
                MMaterial mMaterial = mMaterialMapper.getMatByNo(matNo);
                MMakeForm mMakeForm = new MMakeForm();
                mMakeForm.setAskNo(mAskCode.getAskNo());
                mMakeForm.setProdNo(mAskCode.getMatNo());
                mMakeForm.setMatNo(matNo);
                mMakeForm.setMatPrice(mMaterial.getMatPrice());
                mMakeForm.setMatAmount(new BigDecimal(amount+""));
                mMakeForm.setUuid(BaseUtils.UUIDGenerator());
                mMakeFormMapper.insertMakeForm(mMakeForm);
            }
        }catch (Exception e){
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            throw e;
        }
    }

    @Override
    @Transactional
    public void deleteAskCode(String askNo) throws Exception{
        MAskCode askCode = mAskCodeMapper.getAskCodeByNo(askNo);
        if(!"73000".equals(askCode.getAskStep())){
            throw new XException(XException.ERR_DEFAULT, "非草稿状态，不能删除！");
        }

        try{
            mMakeFormMapper.deleteByAskNo(askNo);
            mAskCodeMapper.deleteByNo(askNo);
        }catch (Exception e){
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            throw e;
        }
    }

    @Override
    @Transactional
    public void submitAskCode(HttpServletRequest request, UserInfo userInfo) throws Exception{
        try{
            String[] askNos = request.getParameterValues("askNo");
            if(askNos==null || askNos.length<1){
                throw new XException(XException.ERR_DEFAULT, "请先选择要提交的申请！");
            }

            for(int i=0; i<askNos.length; i++){
                String askNo = askNos[i];

                if(mAskCodeMapper.getAskCodeFileNum(askNo)<1){
                    throw new XException(XException.ERR_DEFAULT, "未上传附件，不能提交！");
                }

                String nextStep = getNextStep("73000");
                MAskCode oldAskCode = mAskCodeMapper.getAskCodeByNo(askNo);
                String askName = oldAskCode.getAskName();
                String askModel = oldAskCode.getAskModel();
                String matName = askName + ' ' + askModel;
                String matNo = oldAskCode.getMatNo();
                BigDecimal matFee = oldAskCode.getMatFee();
                BigDecimal payFee = oldAskCode.getPayFee();
                BigDecimal otherFee = oldAskCode.getOtherFee();

                MMaterial material = mMaterialMapper.getFBMaterial(matName, matNo);
                if(material!=null){
                    matNo = material.getMatNo();
                }else{
                    String matElse = mMaterialService.getNewSequenceByMatCode("XFB00");
                    String matCode = "XFB00"+matElse;

                    MMaterial m = new MMaterial();
                    matNo = Snow.getUUID()+"";
                    m.setMatNo(matNo);
                    m.setMatCode(matCode);
                    m.setMatName(matName);
                    m.setNewOld("X");
                    m.setMatType("FB");
                    m.setMatUnit("");
                    m.setMatPrice(matFee);
                    m.setOldRebate(new BigDecimal("0"));
                    m.setRealFlag("m.realFlag.0");
                    m.setReFlag("m.reFlag.0");
                    m.setOffenFlag("m.offenFlag.1");
                    m.setUseInfo("");
                    m.setInsDate(XDate.getDate());
                    m.setErpCode("");
                    m.setAbcType("");
                    m.setIfSend("");
                    m.setPackScale(new BigDecimal("0"));
                    m.setGoodsType("");
                    m.setPayFee(payFee);
                    m.setOtherFee(otherFee);
                    m.setErpType("FB00");
                    m.setTypeName("非标加工件");
                    mMaterialMapper.executeSave(m);
                }

                MAskCode mAskCode = new MAskCode();
                mAskCode.setAskDate(XDate.getDate());
                mAskCode.setAskStep(nextStep);
                mAskCode.setMatNo(matNo);
                mAskCode.setAskNo(askNo);
                mAskCode.setCreator("73000");
                mAskCodeMapper.updateAskCode(mAskCode);

                TCheck tCheck = new TCheck();
                tCheck.setCheckNo(oldAskCode.getCheckNo());
                tCheck.setStepKey("7300");
                tCheck.setStepCode("73000");
                tCheck.setCheckType("sys.checkType.0");
                tCheck.setDirect("sys.checkDirect.0");
                tCheck.setIdea("");
                tCheck.setUserId(userInfo.getUuid());
                tCheck.setUserName(userInfo.getUserName());
                tCheck.setTeamNo(Integer.parseInt(userInfo.getOrgId()));
                tCheck.setOccDate(XDate.getDate());
                tCheck.setOccTime(XDate.getTime());
                tCheck.setLogInfo("");
                tCheck.setBefAmount(oldAskCode.getAskAmount());
                tCheck.setBefPrice(new BigDecimal("0"));
                tCheck.setAftAmount(oldAskCode.getAskAmount());
                tCheck.setAftPrice(new BigDecimal("0"));
                tCheck.setEmpId(0L);
                tCheckMapper.executeSave(tCheck);
            }
        }catch (Exception e){
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            throw e;
        }
    }

    private String getNextStep(String prevStep) throws Exception{
        String nextStepCode = "";

        if ("73000".equals(prevStep)) {
            nextStepCode = "73003";
        } else if ("73003".equals(prevStep)) {
            nextStepCode = "73004";
        } else if ("73004".equals(prevStep)) {
            nextStepCode = "73001";
        } else if ("73001".equals(prevStep)) {
            nextStepCode = "73005";
        } else if ("73005".equals(prevStep)) {
            nextStepCode = "7300F";
        }

        if ("".equals(nextStepCode)) {
            throw new XException(XException.ERR_DEFAULT, "不能确定计划流向");
        }

        return nextStepCode;
    }

    @Override
    public List<MAskCode> getCheckIndexList(String userId, String userTeam){
        return mAskCodeMapper.getCheckIndexList(userId, userTeam);
    }

    @Override
    public PageSet<MAskCode> getCheckPageSet(PageParam pageParam, String filterSort, String askTeam, String askMonth, String askStep) {
        PageHelper.startPage(pageParam.getPage(), pageParam.getRows());
        List<MAskCode> list = mAskCodeMapper.getCheckList(askTeam, askMonth, askStep);
        PageInfo<MAskCode> pageInfo = new PageInfo<>(list);
        return PageUtils.getPageSet(pageInfo);
    }

    @Override
    @Transactional
    public void checkAskCode(HttpServletRequest request, UserInfo userInfo) throws Exception{
        try{
            String askStep = request.getParameter("askStep");
            String[] askNos = request.getParameterValues("askNo");
            if(askNos==null || askNos.length<1){
                throw new XException(XException.ERR_DEFAULT, "请选择要审批的申请！");
            }

            for(int i=0; i<askNos.length; i++){
                String askNo = askNos[i];

                MAskCode mAskCode = mAskCodeMapper.getAskCodeByNo(askNo);
                String askName = mAskCode.getAskName();
                String askModel = mAskCode.getAskModel();
                String matName = askName + ' ' + askModel;
                BigDecimal askAmount = mAskCode.getAskAmount();
                String nowStep = mAskCode.getAskStep();
                String matNo = mAskCode.getMatNo();
                BigDecimal matFee = mAskCode.getMatFee();
                BigDecimal payFee = mAskCode.getPayFee();
                BigDecimal otherFee = mAskCode.getOtherFee();
                String checkNo = mAskCode.getCheckNo();

                double checkAmount = 0;
                try {
                    checkAmount = Double.parseDouble(request.getParameter("checkAmount_"+askNo));
                }catch (Exception e){
                    throw new XException(XException.ERR_DEFAULT, matName + ": 审核数量必须是数字！");
                }
                String applyInfo = request.getParameter("applyInfo_"+askNo);
                String makeTeam = request.getParameter("makeTeam_"+askNo);
                String needDate = request.getParameter("needDate_"+askNo);
                String askUnit = request.getParameter("askUnit_"+askNo);
                String preTeam = "73001".equals(nowStep) || "73005".equals(nowStep) ? request.getParameter("preTeam_"+askNo) : "0";

                if("".equals(preTeam)){
                    throw new XException(XException.ERR_DEFAULT, matName + ": 请选择预计回收单位！");
                }
                if("".equals(needDate) && "0".equals(mAskCode.getNeedType())){
                    throw new XException(XException.ERR_DEFAULT, matName + ": 请选择回收预期！");
                }
                needDate = needDate==null ? "" : XDate.dateTo8(needDate);
                if(!"".equals(needDate) && Integer.parseInt(XDate.getDate()) > Integer.parseInt(needDate)){
                    throw new XException(XException.ERR_DEFAULT, matName + ": 回收预期不能小于当天！");
                }
                if(askAmount.compareTo(new BigDecimal(checkAmount+"")) == -1){
                    throw new XException(XException.ERR_DEFAULT, matName + ": 审批数量必须小于等于申请数！");
                }
                if("".equals(makeTeam) && "73001".equals(nowStep)){
                    throw new XException(XException.ERR_DEFAULT, matName + ": 加工单位不能为空！");
                }
                if(!askStep.equals(nowStep)){
                    throw new XException(XException.ERR_DEFAULT, matName + ": 审批步骤不正确，请刷新页面！");
                }
                if("73001".equals(askStep)){
                    double mFee = 0;
                    double pFee = 0;
                    double oFee = 0;
                    try{
                        mFee = Double.parseDouble(request.getParameter("matFee_"+askNo));
                    }catch (Exception e){
                        throw new XException(XException.ERR_DEFAULT, matName + ": 价格必须是数字！");
                    }
                    if(mFee < 0){
                        throw new XException(XException.ERR_DEFAULT, matName + ": 价格必须大于0！");
                    }
                    try {
                        pFee = Double.parseDouble(request.getParameter("payFee_"+askNo));
                    }catch (Exception e){
                        throw new XException(XException.ERR_DEFAULT, matName + ": 工时费必须是数字！");
                    }
                    try {
                        oFee = Double.parseDouble(request.getParameter("otherFee_"+askNo));
                    }catch (Exception e){
                        throw new XException(XException.ERR_DEFAULT, matName + ": 其他费用必须是数字！");
                    }

                    matFee = new BigDecimal(mFee+"");
                    payFee = new BigDecimal(pFee+"");
                    otherFee = new BigDecimal(oFee+"");
                }

                String nextStep = getNextStep(nowStep);

                MAskCode ac = new MAskCode();
                ac.setAskNo(askNo);
                ac.setCheckAmount(new BigDecimal(checkAmount+""));
                ac.setMakeTeam(Integer.parseInt(makeTeam));
                ac.setAskStep(nextStep);
                ac.setNeedDate(needDate);
                ac.setAskUnit(askUnit);
                if("73001".equals(askStep)){
                    ac.setDoDate(XDate.getDate());
                    ac.setMatFee(matFee);
                    ac.setPayFee(payFee);
                    ac.setOtherFee(otherFee);
                }
                if("73001".equals(askStep) || "73005".equals(askStep)){
                    ac.setPreTeam(Integer.parseInt(preTeam));
                }
                mAskCodeMapper.updateAskCode(ac);

                if(!"0".equals(matNo)){
                    MMaterial material = new MMaterial();
                    material.setMatNo(matNo);
                    material.setMatPrice(matFee);
                    material.setPayFee(payFee);
                    material.setOtherFee(otherFee);
                    mMaterialMapper.updateMaterial(material);
                }

                TCheck tCheck = new TCheck();
                tCheck.setCheckNo(checkNo);
                tCheck.setStepKey("7300");
                tCheck.setStepCode(askStep);
                tCheck.setCheckType("sys.checkType.0");
                tCheck.setDirect("sys.checkDirect.0");
                tCheck.setIdea(applyInfo);
                tCheck.setUserId(userInfo.getUuid());
                tCheck.setUserName(userInfo.getUserName());
                tCheck.setTeamNo(Integer.parseInt(userInfo.getOrgId()));
                tCheck.setOccDate(XDate.getDate());
                tCheck.setOccTime(XDate.getTime());
                tCheck.setLogInfo("");
                tCheck.setBefAmount(new BigDecimal(checkAmount+""));
                tCheck.setBefPrice(new BigDecimal("0"));
                tCheck.setAftAmount(new BigDecimal(checkAmount+""));
                tCheck.setAftPrice(new BigDecimal("0"));
                tCheck.setEmpId(0L);
                tCheckMapper.executeSave(tCheck);
            }
        }catch (Exception e){
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            throw e;
        }
    }

    @Override
    @Transactional
    public void backAskCode(HttpServletRequest request, UserInfo userInfo) throws Exception{
        try{
            String[] askNos = request.getParameterValues("askNo");
            if(askNos==null || askNos.length<1){
                throw new XException(XException.ERR_DEFAULT, "请先选择要退回的申请！");
            }

            for(int i=0; i<askNos.length; i++){
                String askNo = askNos[i];
                MAskCode mAskCode = mAskCodeMapper.getAskCodeByNo(askNo);

                String askStep = mAskCode.getAskStep();
                String checkNo = mAskCode.getCheckNo();

                if("7300F".equals(askStep)){
                    throw new XException(XException.ERR_DEFAULT, "定稿计划不能退稿！");
                }

                MAskCode ac = new MAskCode();
                ac.setAskNo(askNo);
                ac.setAskStep("73000");
                mAskCodeMapper.updateAskCode(ac);

                TCheck tCheck = new TCheck();
                tCheck.setCheckNo(checkNo);
                tCheck.setStepKey("7300");
                tCheck.setStepCode(askStep);
                tCheck.setCheckType("sys.checkType.0");
                tCheck.setDirect("sys.checkDirect.1");
                tCheck.setIdea(request.getParameter("applyInfo_"+askNo));
                tCheck.setUserId(userInfo.getUuid());
                tCheck.setUserName(userInfo.getUserName());
                tCheck.setTeamNo(Integer.parseInt(userInfo.getOrgId()));
                tCheck.setOccDate(XDate.getDate());
                tCheck.setOccTime(XDate.getTime());
                tCheck.setLogInfo("");
                tCheck.setBefAmount(new BigDecimal("0"));
                tCheck.setBefPrice(new BigDecimal("0"));
                tCheck.setAftAmount(new BigDecimal("0"));
                tCheck.setAftPrice(new BigDecimal("0"));
                tCheck.setEmpId(0L);
                tCheckMapper.executeSave(tCheck);
            }
        }catch (Exception e){
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            throw e;
        }
    }

    @Override
    public PageSet<MAskCode> getMakeBackPageSet(PageParam pageParam, String filterSort, String date1Qry, String addrQry, String matQry, String teamQry, boolean flagQry, String curDate, String stepQry, String day1Qry, String day2Qry, String needTypeQry) {
        PageHelper.startPage(pageParam.getPage(), pageParam.getRows());
        List<MAskCode> list = mAskCodeMapper.getMakeBackList(date1Qry, addrQry, matQry, teamQry, flagQry, curDate, stepQry, day1Qry, day2Qry, needTypeQry);

        for(int i=0; i<list.size(); i++){
            MAskCode mAskCode = list.get(i);
            String askNo = mAskCode.getAskNo();

            List<MAskCode> fileList = mAskCodeMapper.getFileListByAskNo(askNo);
            String fileNames = "";
            String filePaths = "";
            for(int j=0; j<fileList.size(); j++){
                MAskCode file = fileList.get(j);
                fileNames = (j==0 ? "" : ",") + file.getFileName();
                filePaths = (j==0 ? "" : ",") + file.getFilePath();
            }

            mAskCode.setFileName(fileNames);
            mAskCode.setFilePath(filePaths);
        }
        PageInfo<MAskCode> pageInfo = new PageInfo<>(list);
        return PageUtils.getPageSet(pageInfo);
    }

    @Override
    public List<MAskCode> getMakeBackList(String date1Qry, String addrQry, String matQry, String teamQry, boolean flagQry, String curDate, String stepQry, String day1Qry, String day2Qry, String needTypeQry) {
        return mAskCodeMapper.getMakeBackList(date1Qry, addrQry, matQry, teamQry, flagQry, curDate, stepQry, day1Qry, day2Qry, needTypeQry);
    }

    @Override
    public PageSet<MIn> getMakeBackDetailPageSet(PageParam pageParam, String filterSort, String startDateQry, String endDateQry, String matNameQry) {
        PageHelper.startPage(pageParam.getPage(), pageParam.getRows());
        List<MIn> list = mAskCodeMapper.getMakeBackDetailList(startDateQry, endDateQry, matNameQry);
        PageInfo<MIn> pageInfo = new PageInfo<>(list);
        return PageUtils.getPageSet(pageInfo);
    }

    @Override
    public List<MIn> getMakeBackDetailList(String startDateQry, String endDateQry, String matNameQry){
        return mAskCodeMapper.getMakeBackDetailList(startDateQry, endDateQry, matNameQry);
    }

    @Override
    @Transactional
    public void submitMakeBack(HttpServletRequest request, UserInfo userInfo) throws Exception{
        try{
            String[] askNos = request.getParameterValues("askNos");
            if(askNos==null || askNos.length<1){
                throw new XException(XException.ERR_DEFAULT, "请选择要回收的记录！");
            }

            for(int i=0; i<askNos.length; i++){
                String askNo = askNos[i];
                double whAmount = 0;
                double dxAmount = 0;
                try {
                    whAmount = Double.parseDouble(request.getParameter("wh_"+askNo));
                }catch (Exception e){
                    throw new XException(XException.ERR_DEFAULT, "完好数必须是数字！");
                }
                try {
                    dxAmount = Double.parseDouble(request.getParameter("dx_"+askNo));
                }catch (Exception e){
                    throw new XException(XException.ERR_DEFAULT, "待修数必须是数字！");
                }
                String remark = request.getParameter("remark_"+askNo);

                MAskCode mAskCode = mAskCodeMapper.getMakeBack(askNo);
                Integer teamNo = mAskCode.getAskTeam();
                String matNo = mAskCode.getMatNo();
                BigDecimal inPrice = mAskCode.getMatFee();
                BigDecimal wait = mAskCode.getWaitAmount();
                if(new BigDecimal(whAmount+"").add(new BigDecimal(dxAmount+"")).compareTo(wait) == 1){
                    throw new XException(XException.ERR_DEFAULT, "超出待回数！");
                }
                if(!"7300F".equals(mAskCode.getAskStep())){
                    throw new XException(XException.ERR_DEFAULT, "非定稿状态，不能提交！");
                }

                String mngTeam = configService.getConfigByCode("XT_TEAM_MATERIAL");

                if(whAmount > 0){
                    List<MStore> whStoreList = mStoreMapper.getStoreList(null, null, mngTeam, null, null, null, "m.storeType2.14", "m.storeLevel.1");
                    if(whStoreList.size()<1){
                        throw new XException(XException.ERR_DEFAULT, "管理部门没有旧料库！");
                    }
                    String whStore = whStoreList.get(0).getStoreNo();

                    MIn mIn = new MIn();
                    mIn.setBillNo(Snow.getUUID()+"");
                    mIn.setInType("r.inBillType.C");
                    mIn.setPlanSrc("r.inPlanSrc.3");
                    mIn.setPlanNo(askNo);
                    mIn.setTeamNo(teamNo);
                    mIn.setOfferTeam(Integer.parseInt(mngTeam));
                    mIn.setStoreNo(whStore);
                    mIn.setReserveNo("");
                    mIn.setMatNo(matNo);
                    mIn.setApplyAmount(new BigDecimal(whAmount+""));
                    mIn.setApplyDate(XDate.getDate());
                    mIn.setApplyEmp(userInfo.getUuid());
                    mIn.setInAmount(new BigDecimal(whAmount+""));
                    mIn.setInDate(XDate.getDate());
                    mIn.setInEmp(userInfo.getUuid());
                    mIn.setBillAmount(new BigDecimal(whAmount+""));
                    mIn.setSetPrice(inPrice);
                    mIn.setBillDate(XDate.getDate());
                    mIn.setBillEmp("");
                    mIn.setOfferNo("");
                    mIn.setInStep("7201F");
                    mIn.setCheckNo("0");
                    mIn.setRemark(remark);
                    mIn.setDataSrc("m.dataSrc.C");
                    mIn.setPrice1(new BigDecimal("0"));
                    mIn.setPrice2(new BigDecimal("0"));
                    mIn.setPrice3(new BigDecimal("0"));
                    mIn.setLinkNo("");
                    mIn.setNormNo("");
                    mIn.setReserve1("");
                    mIn.setReserve2("");
                    mIn.setReserve3("");
                    mIn.setReserve4("");
                    mIn.setErpBill("");
                    mInMapper.saveIn(mIn);

                    MStock mStock = new MStock();
                    mStock.setMatNo(matNo);
                    mStock.setStoreNo(whStore);
                    mStock.setInAmount(new BigDecimal(whAmount+""));
                    mStock.setOutAmount(new BigDecimal("0"));
                    mStock.setStockAmount(new BigDecimal(whAmount+""));
                    mStock.setPackAmount(new BigDecimal("0"));
                    mStock.setLockAmount(new BigDecimal("0"));
                    mStock.setBulkAmount(new BigDecimal(whAmount+""));
                    int c = mStockMapper.updateStock(mStock);
                    if(c<1){
                        mStock.setSiteCode("");
                        mStockMapper.insertStock(mStock);
                    }
                }

                if(dxAmount > 0){
                    List<MStore> dxStoreList = mStoreMapper.getStoreList(null, null, mngTeam, null, null, null, "m.storeType2.21", "m.storeLevel.1");
                    if(dxStoreList.size()<1){
                        throw new XException(XException.ERR_DEFAULT, "管理部门没有待修库！");
                    }
                    String dxStore = dxStoreList.get(0).getStoreNo();

                    MIn mIn = new MIn();
                    mIn.setBillNo(Snow.getUUID()+"");
                    mIn.setInType("r.inBillType.D");
                    mIn.setPlanSrc("r.inPlanSrc.3");
                    mIn.setPlanNo(askNo);
                    mIn.setTeamNo(teamNo);
                    mIn.setOfferTeam(Integer.parseInt(mngTeam));
                    mIn.setStoreNo(dxStore);
                    mIn.setReserveNo("");
                    mIn.setMatNo(matNo);
                    mIn.setApplyAmount(new BigDecimal(dxAmount+""));
                    mIn.setApplyDate(XDate.getDate());
                    mIn.setApplyEmp(userInfo.getUuid());
                    mIn.setInAmount(new BigDecimal(dxAmount+""));
                    mIn.setInDate(XDate.getDate());
                    mIn.setInEmp(userInfo.getUuid());
                    mIn.setBillAmount(new BigDecimal(dxAmount+""));
                    mIn.setSetPrice(inPrice);
                    mIn.setBillDate(XDate.getDate());
                    mIn.setBillEmp("");
                    mIn.setOfferNo("");
                    mIn.setInStep("7201F");
                    mIn.setCheckNo("0");
                    mIn.setRemark(remark);
                    mIn.setDataSrc("m.dataSrc.C");
                    mIn.setPrice1(new BigDecimal("0"));
                    mIn.setPrice2(new BigDecimal("0"));
                    mIn.setPrice3(new BigDecimal("0"));
                    mIn.setLinkNo("");
                    mIn.setNormNo("");
                    mIn.setReserve1("");
                    mIn.setReserve2("");
                    mIn.setReserve3("");
                    mIn.setReserve4("");
                    mIn.setErpBill("");
                    mIn.setCheckAddr("");
                    mInMapper.saveIn(mIn);

                    MStock mStock = new MStock();
                    mStock.setMatNo(matNo);
                    mStock.setStoreNo(dxStore);
                    mStock.setInAmount(new BigDecimal(dxAmount+""));
                    mStock.setOutAmount(new BigDecimal("0"));
                    mStock.setStockAmount(new BigDecimal(dxAmount+""));
                    mStock.setPackAmount(new BigDecimal("0"));
                    mStock.setLockAmount(new BigDecimal("0"));
                    mStock.setBulkAmount(new BigDecimal(dxAmount+""));
                    int c = mStockMapper.updateStock(mStock);
                    if(c<1){
                        mStock.setSiteCode("");
                        mStockMapper.insertStock(mStock);
                    }
                }
            }
        }catch (Exception e){
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            throw e;
        }
    }

    @Override
    public MAskCode getAskByNo(String askNo){
        MAskCode mAskCode = mAskCodeMapper.getAskCodeByNo(askNo);
        mAskCode.setNeedDate(XDate.dateTo10(mAskCode.getNeedDate()));
        return mAskCode;
    }

    @Override
    @Transactional
    public void updateAsk(HttpServletRequest request, UserInfo userInfo) throws Exception{
        try{
            String askNo = request.getParameter("askNo");
            String needDate = request.getParameter("needDate");
            String preTeam = request.getParameter("preTeam");

            MAskCode m = mAskCodeMapper.getAskCodeByNo(askNo);

            MAskCode mAskCode = new MAskCode();
            mAskCode.setAskNo(askNo);
            mAskCode.setNeedDate(XDate.dateTo8(needDate));
            mAskCode.setPreTeam(Integer.parseInt(preTeam));
            mAskCodeMapper.updateAskCode(mAskCode);

            TCheck tCheck = new TCheck();
            tCheck.setCheckNo(m.getCheckNo());
            tCheck.setStepKey("7300");
            tCheck.setStepCode("");
            tCheck.setCheckType("sys.checkType.0");
            tCheck.setDirect("sys.checkDirect.1");
            tCheck.setIdea("调整回收预期");
            tCheck.setUserId(userInfo.getUuid());
            tCheck.setUserName(userInfo.getUserName());
            tCheck.setTeamNo(Integer.parseInt(userInfo.getOrgId()));
            tCheck.setOccDate(XDate.getDate());
            tCheck.setOccTime(XDate.getTime());
            tCheck.setLogInfo("");
            tCheck.setBefAmount(new BigDecimal("0"));
            tCheck.setBefPrice(new BigDecimal("0"));
            tCheck.setAftAmount(new BigDecimal("0"));
            tCheck.setAftPrice(new BigDecimal("0"));
            tCheck.setEmpId(0L);
            tCheckMapper.executeSave(tCheck);
        }catch (Exception e){
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            throw e;
        }
    }
}
