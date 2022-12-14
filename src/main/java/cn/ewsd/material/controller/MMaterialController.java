package cn.ewsd.material.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import cn.ewsd.base.utils.ImportExeclUtil;
import cn.ewsd.base.utils.Snow;
import cn.ewsd.base.utils.XDate;
import cn.ewsd.base.utils.XException;
import cn.ewsd.common.annotation.ControllerLog;
import cn.ewsd.material.mapper.MErpMaterialMapper;
import cn.ewsd.material.model.MErpMaterial;
import cn.ewsd.material.model.MMakeMatDept;
import cn.ewsd.material.model.MMatType;
import cn.ewsd.material.service.*;
import cn.ewsd.mdata.service.UtilService;
import cn.ewsd.repository.service.MOutService;
import cn.ewsd.system.service.DicItemService;
import cn.hutool.core.util.StrUtil;
import org.apache.http.entity.ContentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.stereotype.Controller;

import cn.ewsd.common.utils.BaseUtils;
import cn.ewsd.common.utils.easyui.PageParam;
import cn.ewsd.common.utils.easyui.PageSet;

import cn.ewsd.material.model.MMaterial;
import org.springframework.web.multipart.MultipartFile;

/**
 * @Author fengjw
 * @Email 11128474@qq.cn
 * @Date 2021-04-23 11:07:12
 */
@Controller
@RequestMapping("/material/mMaterial")
public class MMaterialController extends MaterialBaseController {

    @Autowired
    private MMaterialService mMaterialService;
    @Autowired
    private DicItemService dicItemService;
    @Autowired
    private MTeamStockService mTeamStockService;
    @Autowired
    private MStockService mStockService;
    @Autowired
    private MPlanService mPlanService;
    @Autowired
    private MOutService mOutService;
    @Autowired
    private UtilService utilService;
    @Autowired
    private MErpMaterialMapper mErpMaterialMapper;
    @Autowired
    private MMatTypeService mMatTypeService;
    @Value("${my.upload-dir}")
    private String uploadDir;
    //?????????
    @RequestMapping("/index")
    public String index(@RequestParam Map<String, Object> params) {
        return "material/mMaterial/index";
    }

    //????????????
    @ResponseBody
    @RequestMapping(value = "/getPageSet", method = RequestMethod.POST)
    public Object getPageSet(PageParam pageParam) {
        try{
            String filterSort = "";
            filterSort = BaseUtils.filterSort(request, filterSort + getAuthFilter());
            PageSet<MMaterial> pageSet = mMaterialService.getPageSet(pageParam, filterSort.replace("ORDER BY ", "ORDER BY m."));
            return pageSet;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    //????????????
    @RequestMapping("/add")
    public String add() {
        return "material/mMaterial/edit";
    }

    @RequestMapping("/goImport")
    public String goImport() {
        return "material/mMaterial/go_import";
    }

    //????????????
    @ResponseBody
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public Object save(@ModelAttribute MMaterial mMaterial) {
        try {
            String errorInfo = "";
            String matCode = mMaterial.getMatCode().toUpperCase();
            String newOld = matCode.substring(0, 1);
            String matType = matCode.substring(1, 3);
            String matName = mMaterial.getMatName();
            BigDecimal matPrice = mMaterial.getMatPrice() == null ? new BigDecimal("0") : mMaterial.getMatPrice();
            mMaterial.setMatPrice(matPrice);
            BigDecimal payFee = mMaterial.getPayFee() == null ? new BigDecimal("0") : mMaterial.getPayFee();
            mMaterial.setPayFee(payFee);
            BigDecimal otherFee = mMaterial.getOtherFee() == null ? new BigDecimal("0") : mMaterial.getOtherFee();
            mMaterial.setOtherFee(otherFee);
            BigDecimal planPrice = mMaterial.getPlanPrice() == null ? new BigDecimal("0") : mMaterial.getPlanPrice();
            mMaterial.setPlanPrice(planPrice);
            BigDecimal fixPrice = mMaterial.getFixPrice() == null ? new BigDecimal("0") : mMaterial.getFixPrice();
            mMaterial.setFixPrice(fixPrice);
            Integer qaPeriod = mMaterial.getQaPeriod() == null ? new Integer("0") : mMaterial.getQaPeriod();
            mMaterial.setQaPeriod(qaPeriod);
            BigDecimal packScale = mMaterial.getPackScale() == null ? new BigDecimal("0") : mMaterial.getPackScale();
            mMaterial.setPackScale(packScale);
            ArrayList<String[]> condArr1 = new ArrayList<String[]>();
            condArr1.add(new String[]{"mat_code", "=", matCode});
            ArrayList<String[]> condArr2 = new ArrayList<String[]>();
            condArr2.add(new String[]{"mat_name", "=", matName});
            if (matCode.length() != 5 && matCode.length() != 9) {
                errorInfo = "??????????????????????????????5?????????9??????";
            } else if (!"X".equals(newOld) && !"J".equals(newOld)) {
                errorInfo = "?????????????????????????????????X?????????J??????";
            } else if ("FB".equals(matType) && matPrice.doubleValue() < 0) {
                errorInfo = "??????????????????0???";
            } else if (!"FB".equals(matType) && matPrice.doubleValue() <= 0) {
                errorInfo = "??????????????????0???";
            } else if (dicItemService.getDicItemByValue("m.matType." + matType) == null) {
                errorInfo = "?????????" + matType + "?????????????????????";
            } else if (matCode.length() == 5 && (matCode = matCode + mMaterialService.getNewSequenceByMatCode(matCode)).length() == 5) {
                errorInfo = "???????????????????????????????????????";
            } else if (utilService.checkColumnDataExist("m_material", condArr1)) {
                errorInfo = "?????????????????????";
            } else if (utilService.checkColumnDataExist("m_material", condArr2)) {
                errorInfo = "?????????????????????";
            }

            if (!"".equals(errorInfo)) {
                return failure(errorInfo);
            }

            String mu = mMaterial.getMatUnit();
            String matUnit = mMaterial.getMatUnit();
            if(matUnit!=null && !"".equals(matUnit)){
                matUnit = matUnit.substring(matUnit.lastIndexOf(".")+1);
            }

            mMaterial.setMatUnit(matUnit);
            mMaterial.setMatCode(matCode);
            mMaterial.setMatNo(Snow.getUUID() + "");
            mMaterial.setNewOld(newOld);
            mMaterial.setMatType(matType);
            mMaterial.setOldRebate(new BigDecimal("0.3"));
            mMaterial.setInsDate(XDate.getDate());
            MMatType mMatType = mMatTypeService.getMatTypeByCode(mMaterial.getErpCode());
            if(mMatType==null||mMatType.getUuid()==null||mMatType.getUuid().equals("")){
                mMaterial.setErpCode(matCode);
            }
            /*if ("".equals(mMaterial.getErpCode())) {
                mMaterial.setErpCode(matCode);
            }*/
            mMaterial.setJfPrice(new BigDecimal("0"));
            mMaterial.setArrivePeriod(0);

            MErpMaterial mErpMaterial = new MErpMaterial();
            mErpMaterial.setFactoryNo(mMaterial.getFactoryNo());
            mErpMaterial.setMatAddr(mMaterial.getMatAddr());
            mErpMaterial.setMatNo(mMaterial.getMatNo());
            mErpMaterial.setMatUnit(mu);

            MMakeMatDept mMakeMatDept = new MMakeMatDept();

            mMaterialService.executeSave(getSaveData(mMaterial), getSaveData(mErpMaterial), getSaveData(mMakeMatDept));
            return success("???????????????");
        }catch (XException e){
            return failure(e.getInfo());
        }catch (Exception e){
            e.printStackTrace();
            return failure("???????????????");
        }
    }

    //???????????????
    @RequestMapping("/edit")
    public String edit() {
        return "material/mMaterial/edit";
    }

    //???????????????
    @ResponseBody
    @RequestMapping(value = "/getDetailByUuid")
    public Object getDetailByMatNo(String uuid) {
        MMaterial mMaterial = mMaterialService.queryObject(uuid);
        return mMaterial;
    }

    //????????????
    @ResponseBody
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public Object update(@ModelAttribute MMaterial mMaterial) {
        try {
            String errorInfo = "";
            String matCode = mMaterial.getMatCode().toUpperCase();
            String matName = mMaterial.getMatName();
            String newOld = matCode.substring(0, 1);
            String uuid = mMaterial.getUuid();
            BigDecimal matPrice = mMaterial.getMatPrice() == null ? new BigDecimal("0") : mMaterial.getMatPrice();
            mMaterial.setMatPrice(matPrice);
            BigDecimal payFee = mMaterial.getPayFee() == null ? new BigDecimal("0") : mMaterial.getPayFee();
            mMaterial.setPayFee(payFee);
            BigDecimal otherFee = mMaterial.getOtherFee() == null ? new BigDecimal("0") : mMaterial.getOtherFee();
            mMaterial.setOtherFee(otherFee);
            BigDecimal planPrice = mMaterial.getPlanPrice() == null ? new BigDecimal("0") : mMaterial.getPlanPrice();
            mMaterial.setPlanPrice(planPrice);
            BigDecimal fixPrice = mMaterial.getFixPrice() == null ? new BigDecimal("0") : mMaterial.getFixPrice();
            mMaterial.setFixPrice(fixPrice);
            Integer qaPeriod = mMaterial.getQaPeriod() == null ? new Integer(0) : mMaterial.getQaPeriod();
            mMaterial.setQaPeriod(qaPeriod);
            BigDecimal packScale = mMaterial.getPackScale() == null ? new BigDecimal("0") : mMaterial.getPackScale();
            mMaterial.setPackScale(packScale);
            ArrayList<String[]> condArr1 = new ArrayList<String[]>();
            condArr1.add(new String[]{"mat_code", "=", matCode});
            condArr1.add(new String[]{"uuid", "!=", uuid});
            ArrayList<String[]> condArr2 = new ArrayList<String[]>();
            condArr2.add(new String[]{"mat_name", "=", matName});
            condArr2.add(new String[]{"uuid", "!=", uuid});

            if (matCode.length() != 9) {
                errorInfo = "??????????????????????????????9??????";
            } else if (!"X".equals(newOld) && !"J".equals(newOld)) {
                errorInfo = "?????????????????????????????????X?????????J??????";
            } else if (utilService.checkColumnDataExist("m_material", condArr1)) {
                errorInfo = "?????????????????????";
            } else if (utilService.checkColumnDataExist("m_material", condArr2)) {
                errorInfo = "?????????????????????";
            }
            if (!"".equals(errorInfo)) {
                return failure(errorInfo);
            }

            String matUnit = mMaterial.getMatUnit();
            if(matUnit!=null && !"".equals(matUnit)){
                matUnit = matUnit.substring(matUnit.lastIndexOf(".")+1);
            }
            mMaterial.setMatUnit(matUnit);

            MMakeMatDept mMakeMatDept = new MMakeMatDept();
            mMaterialService.executeUpdate(getUpdateData(mMaterial), getSaveData(mMakeMatDept));
            return success("???????????????");
        }catch (XException e){
            return failure(e.getInfo());
        }catch (Exception e){
            e.printStackTrace();
            return failure("????????????");
        }
    }

    //???????????????
    @ResponseBody
    @RequestMapping(value = "/getFactoryAddr", method = RequestMethod.POST)
    public Object getFactoryAddr() {
        return mMaterialService.getFactoryAddr();
    }

    //??????????????????
    @ResponseBody
    @RequestMapping(value = "/getOldTeamForSelect", method = RequestMethod.POST)
    public Object getOldTeamForSelect() {
        return mMaterialService.getOldTeamForSelect();
    }

    @ResponseBody
    @RequestMapping(value = "/getFixTeamForSelect", method = RequestMethod.POST)
    public Object getFixTeamForSelect() {
        return mMaterialService.getFixTeamForSelect();
    }

    @ResponseBody
    @RequestMapping(value = "/deleteBatch", method = RequestMethod.POST)
    public Object delete(@RequestParam String uuid[]) {
        for(int i=0; i<uuid.length; i++){
            String id = uuid[i];

            MMaterial mMaterial = mMaterialService.queryObject(id);
            String matNo = mMaterial.getMatNo();

            if(mTeamStockService.getCountByMatNo(matNo)>0){
                return failure(mMaterial.getMatCode()+"?????????????????????????????????");
            }
            if(mStockService.getCountByMatNo(matNo)>0){
                return failure(mMaterial.getMatCode()+"??????????????????????????????");
            }
            if(mPlanService.getCountByMatNo(matNo)>0){
                return failure(mMaterial.getMatCode()+"??????????????????????????????");
            }
            if(mOutService.getCountByMatNo(matNo)>0){
                return failure(mMaterial.getMatCode()+"??????????????????????????????");
            }
            if(mOutService.getCountByMatNo(matNo)>0){
                return failure(mMaterial.getMatCode()+"??????????????????????????????");
            }
        }

        int result = mMaterialService.executeDeleteBatch(uuid);
        return result > 0 ? success("???????????????") : failure("???????????????");
    }

    @ResponseBody
    @RequestMapping(value = "/getMatListForSel")
    public Object getMatListForSel() {
        String q = request.getParameter("q");
        return mMaterialService.getMatListForSel(q, "");
    }

    @ResponseBody
    @RequestMapping(value = "/getMatListForSelJ")
    public Object getMatListForSelJ() {
        String q = request.getParameter("q");
        return mMaterialService.getMatListForSel(q, "J");
    }

    @ResponseBody
    @RequestMapping(value="/getMaterialName")
    public Object getMaterialName(){
        try{
            String q = request.getParameter("q");
            return mMaterialService.getMaterialName("FB", q);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    //??????????????????
    @ResponseBody
    @RequestMapping(value = "/exportleadingIn")
    @ControllerLog(description = "??????Excel??????")
    public  Object exportleadingIn(String address) {

        int successNum = 0;
        int errNum = 0;
        String errText = "";

        //????????????Excel??????
        String strsub =address.substring(address.length() -4);
        if (!".xls".equals(strsub) && !strsub.equals("xlsx")){
            return failure("??????????????????Excel?????????");
        }

        //????????????
        DateFormat formatD2 = new SimpleDateFormat("yyyyMMdd");
        String nowDay = formatD2.format(new Date());

        //??????????????????????????????
//        Properties props=System.getProperties();
//        String osName = props.getProperty("os.name");
//        boolean status = osName.contains("Windows");
//        File pdfFile =null;
//        if (status){
//            pdfFile = new File("D:/zysd/"+address);
//        }else {
//            pdfFile = new File("/home/"+address);
//        }
        File pdfFile = new File(uploadDir+address);

        try {
            //??????Excel????????????
            FileInputStream fileInputStream = new FileInputStream(pdfFile);
            MultipartFile multipartFile = new MockMultipartFile(pdfFile.getName(), pdfFile.getName(), ContentType.APPLICATION_OCTET_STREAM.toString(), fileInputStream);
            //???ImportExeclUtil?????????
            List<Map<String,String>> varList = ImportExeclUtil.readExcel(multipartFile,0,0,0);
            if (varList.size() <= 1){
                return failure("Exclel?????????????????????");
            }
            //????????????????????????????????????????????????
            varList.remove(0);

            //??????
            for (int i = 0; i < varList.size(); i++) {
                if(varList.get(i).get("????????????")==null||varList.get(i).get("????????????").length()!=8){
                    return failure("???"+(i+1)+"?????????????????????!");
                }
                if (varList.get(i).get("????????????")==null||"".equals(varList.get(i).get("????????????"))){
                    return failure("???"+(i+1)+"?????????????????????!");
                }
                if(varList.get(i).get("????????????")==null||varList.get(i).get("????????????").length()!=6){
                    return failure("???"+(i+1)+"?????????????????????!");
                }
                if (varList.get(i).get("????????????")==null||"".equals(varList.get(i).get("????????????"))){
                    return failure("???"+(i+1)+"?????????????????????!");
                }
                if("".equals(varList.get(i).get("??????"))){
                    varList.get(i).put("????????????","1");
                }
                if(varList.get(i).get("??????")==null||new BigDecimal(varList.get(i).get("??????"))==null){
                    return failure("???"+(i+1)+"???????????????!");
                }
            }

            MMaterial mat_upd;
            MMaterial mat_ins;
            MErpMaterial mat_erp_ins;
            String codePrefix = "X";
            //??????
            for (int i = 0; i < varList.size(); i++) {
                String matNo = "";
                String matCode = varList.get(i).get("????????????");
                BigDecimal matPrice = new BigDecimal(varList.get(i).get("??????"));
                MMaterial checkHave = mMaterialService.getMatByCode(codePrefix+matCode);
                if(checkHave!=null&&checkHave.getMatNo()!=null&&!"".equals(checkHave.getMatNo())){
                    matNo = checkHave.getMatNo();
                    mat_upd = new MMaterial();
                    mat_upd.setUuid(checkHave.getUuid());
                    mat_upd.setMatNo(matNo);
                    if(checkHave.getMatPrice().compareTo(new BigDecimal(1))==0){
                        if(matPrice.compareTo(new BigDecimal(1)) != 0){
                            mat_upd.setMatPrice(matPrice);
                        }
                    }
                    mat_upd.setMatName(varList.get(i).get("????????????"));
                    mat_upd.setMatType(varList.get(i).get("????????????").substring(0,2));
                    mat_upd.setMatUnit(varList.get(i).get("????????????"));
                    mat_upd.setErpType(varList.get(i).get("????????????"));
                    mat_upd.setTypeName(varList.get(i).get("????????????"));
                    successNum += mMaterialService.updateByPrimaryKeySelective(mat_upd);
                }else {
                    matNo = Long.toString(Snow.getUUID());
                    mat_ins = new MMaterial();
                    mat_ins.setMatNo(matNo);
                    mat_ins.setMatCode(codePrefix+matCode);
                    mat_ins.setMatName(varList.get(i).get("????????????"));
                    mat_ins.setNewOld("X");
                    mat_ins.setMatType(varList.get(i).get("????????????").substring(0,2));
                    mat_ins.setMatUnit(varList.get(i).get("????????????"));
                    mat_ins.setMatPrice(matPrice);
                    mat_ins.setOldRebate(new BigDecimal(0));
                    mat_ins.setRealFlag("");
                    mat_ins.setReFlag("");
                    mat_ins.setOffenFlag("m.offenFlag.1");
                    mat_ins.setUseInfo("");
                    mat_ins.setInsDate(nowDay);
                    mat_ins.setErpCode("");
                    mat_ins.setAbcType("");
                    mat_ins.setIfSend("");
                    mat_ins.setPackScale(new BigDecimal(1));
                    mat_ins.setGoodsType("");
                    mat_ins.setPayFee(new BigDecimal(0));
                    mat_ins.setOtherFee(new BigDecimal(0));
                    mat_ins.setErpType(varList.get(i).get("????????????"));
                    mat_ins.setTypeName(varList.get(i).get("????????????"));
                    mat_ins.setPlanPrice(matPrice);
                    mat_ins.setFixPrice(new BigDecimal(0));
                    mat_ins.setFixContent("");
                    mat_ins.setJfPrice(new BigDecimal(0));
                    mat_ins.setArrivePeriod(0);
                    mat_ins.setQaPeriod(0);
                    successNum += mMaterialService.insertSelective(getSaveData(mat_ins));
                }
                if(!StrUtil.hasBlank(varList.get(i).get("????????????"))){
                    MMatType mMatType = mMatTypeService.getMatTypeByCode(varList.get(i).get("????????????"));
                    if(mMatType==null&&!StrUtil.hasBlank(varList.get(i).get("????????????"))){
                        MMatType mMatType_ins = new MMatType();
                        mMatType_ins.setTypeCode(varList.get(i).get("????????????"));
                        mMatType_ins.setTypeName(varList.get(i).get("????????????"));
                        mMatType_ins.setRemark("");
                        mMatType_ins.setUseFlag("1");
                        mMatTypeService.executeSave(getSaveData(mMatType_ins));
                    }
                }
            }
            return successNum > 0 ? success(successNum+"????????????????????????") : failure("???????????????");
        }catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    /*@ResponseBody
    @RequestMapping(value = "/exportleadingIn")
    @ControllerLog(description = "??????Excel??????")
    public  Object exportleadingIn(String address) {

        int successNum = 0;
        int errNum = 0;
        String errText = "";

        //????????????Excel??????
        String strsub =address.substring(address.length() -4);
        if (!".xls".equals(strsub) && !strsub.equals("xlsx")){
            return failure("??????????????????Excel?????????");
        }

        //????????????
        DateFormat formatD2 = new SimpleDateFormat("yyyyMMdd");
        String nowDay = formatD2.format(new Date());

        //??????????????????????????????
        Properties props=System.getProperties();
        String osName = props.getProperty("os.name");
        boolean status = osName.contains("Windows");
        File pdfFile =null;
        if (status){
            pdfFile = new File("D:/zysd/"+address);
        }else {
            pdfFile = new File("/home/"+address);
        }

        try {
            //??????Excel????????????
            FileInputStream fileInputStream = new FileInputStream(pdfFile);
            MultipartFile multipartFile = new MockMultipartFile(pdfFile.getName(), pdfFile.getName(), ContentType.APPLICATION_OCTET_STREAM.toString(), fileInputStream);
            //???ImportExeclUtil?????????
            List<Map<String,String>> varList = ImportExeclUtil.readExcel(multipartFile,0,0,0);
            if (varList.size() <= 1){
                return failure("Exclel?????????????????????");
            }
            //????????????????????????????????????????????????
            varList.remove(0);

            //??????
            for (int i = 0; i < varList.size(); i++) {
                if(varList.get(i).get("????????????")==null||varList.get(i).get("????????????").length()!=8){
                    return failure("???"+(i+2)+"?????????????????????!");
                }
                if (varList.get(i).get("????????????")==null||"".equals(varList.get(i).get("????????????"))){
                    return failure("???"+(i+2)+"?????????????????????!");
                }
                if(varList.get(i).get("?????????")==null||varList.get(i).get("?????????").length()!=6){
                    return failure("???"+(i+2)+"??????????????????!");
                }
                if("".equals(varList.get(i).get("????????????"))){
                    varList.get(i).put("????????????","1");
                }
                if(varList.get(i).get("????????????")==null||new BigDecimal(varList.get(i).get("????????????"))==null){
                    return failure("???"+(i+2)+"?????????????????????!");
                }
                if (varList.get(i).get("??????")==null||"".equals(varList.get(i).get("??????"))){
                    return failure("???"+(i+2)+"???????????????!");
                }
                if (varList.get(i).get("?????????")==null||"".equals(varList.get(i).get("?????????"))){
                    return failure("???"+(i+2)+"??????????????????!");
                }
            }

            MMaterial mat_upd;
            MMaterial mat_ins;
            MErpMaterial mat_erp_ins;
            String codePrefix = "X";
            //??????
            for (int i = 0; i < varList.size(); i++) {
                String matNo = "";
                String matCode = varList.get(i).get("????????????");
                BigDecimal matPrice = new BigDecimal(varList.get(i).get("????????????"));
                MMaterial checkHave = mMaterialService.getMatByCode(codePrefix+matCode);
                if(checkHave!=null&&checkHave.getMatNo()!=null&&!"".equals(checkHave.getMatNo())){
                    matNo = checkHave.getMatNo();
                    mat_upd = new MMaterial();
                    mat_upd.setUuid(checkHave.getUuid());
                    mat_upd.setMatNo(matNo);
                    if(checkHave.getMatPrice().compareTo(new BigDecimal(1))==0){
                        if(matPrice.compareTo(new BigDecimal(1)) != 0){
                            mat_upd.setMatPrice(matPrice);
                        }
                    }
                    mat_upd.setMatName(varList.get(i).get("????????????"));
                    mat_upd.setMatType(varList.get(i).get("?????????").substring(0,2));
                    mat_upd.setMatUnit(varList.get(i).get("????????????"));
                    mat_upd.setErpType(varList.get(i).get("?????????"));
                    mat_upd.setTypeName(varList.get(i).get("???????????????"));
                    successNum += mMaterialService.updateByPrimaryKeySelective(mat_upd);
                    //????????????????????????????????????1,????????????????????????1,??????????????????
//                    if(checkHave.getMatPrice().compareTo(new BigDecimal(1))==0){
//                        if(matPrice.compareTo(new BigDecimal(1)) != 0){
//                            mat_upd = new MMaterial();
//                            mat_upd.setMatNo(checkHave.getMatNo());
//                            mat_upd.setMatPrice(matPrice);
//                            mat_upd.setUuid(checkHave.getUuid());
//                            mMaterialService.updateByPrimaryKeySelective(mat_upd);
//                        }
//                    }
//                    //?????????????????????????????????1,???????????????????????????1,?????????????????????
//                    if(matPrice.compareTo(new BigDecimal(1)) == 0){
//                        if(checkHave.getMatPrice().compareTo(new BigDecimal(1))!=0){
//                            matPrice = checkHave.getMatPrice();
//                        }
//                    }
//
//                    while (matCode.equals(checkHave.getMatCode().substring(1))){
//                        String snowStr = Long.toString(Snow.getUUID());
//                        matCode = snowStr.substring(snowStr.length()-8);
//                    }
                }else {
                    matNo = Long.toString(Snow.getUUID());
                    mat_ins = new MMaterial();
                    mat_ins.setMatNo(matNo);
                    mat_ins.setMatCode(codePrefix+matCode);
                    mat_ins.setMatName(varList.get(i).get("????????????"));
                    mat_ins.setNewOld("X");
                    mat_ins.setMatType(varList.get(i).get("?????????").substring(0,2));
                    mat_ins.setMatUnit(varList.get(i).get("????????????"));
                    mat_ins.setMatPrice(matPrice);
                    mat_ins.setOldRebate(new BigDecimal(0));
                    mat_ins.setRealFlag("");
                    mat_ins.setReFlag("");
                    mat_ins.setOffenFlag("m.offenFlag.1");
                    mat_ins.setUseInfo("");
                    mat_ins.setInsDate(nowDay);
                    mat_ins.setErpCode("");
                    mat_ins.setAbcType("");
                    mat_ins.setIfSend("");
                    mat_ins.setPackScale(new BigDecimal(1));
                    mat_ins.setGoodsType("");
                    mat_ins.setPayFee(new BigDecimal(0));
                    mat_ins.setOtherFee(new BigDecimal(0));
                    mat_ins.setErpType(varList.get(i).get("?????????"));
                    mat_ins.setTypeName(varList.get(i).get("???????????????"));
                    mat_ins.setPlanPrice(matPrice);
                    mat_ins.setFixPrice(new BigDecimal(0));
                    mat_ins.setFixContent("");
                    mat_ins.setJfPrice(new BigDecimal(0));
                    mat_ins.setArrivePeriod(0);
                    mat_ins.setQaPeriod(0);
                    successNum += mMaterialService.insertSelective(getSaveData(mat_ins));
                }

                MErpMaterial merp_check = mErpMaterialMapper.getErpMaterialByMatNoAndFactoryAndAddr(matNo,"m.factoryNo."+varList.get(i).get("??????"),"m.matAddr."+varList.get(i).get("?????????"));
                if(merp_check == null || merp_check.getUuid()==null ||"".equals(merp_check.getUuid())){
                    mat_erp_ins = new MErpMaterial();
                    mat_erp_ins.setMatNo(matNo);
                    mat_erp_ins.setMatUnit(varList.get(i).get("????????????"));
                    mat_erp_ins.setFactoryNo("m.factoryNo."+varList.get(i).get("??????"));
                    mat_erp_ins.setMatAddr("m.matAddr."+varList.get(i).get("?????????"));
                    mErpMaterialMapper.insertSelective(getSaveData(mat_erp_ins));
                }
            }

            return successNum > 0 ? success(successNum+"????????????????????????") : failure("???????????????");
        }catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }*/

}
