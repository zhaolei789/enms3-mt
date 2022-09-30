package cn.ewsd.repository.controller;

import cn.ewsd.base.utils.ImportExeclUtil;
import cn.ewsd.base.utils.XDate;
import cn.ewsd.base.utils.jwt.LoginInfo;
import cn.ewsd.common.annotation.ControllerLog;
import cn.ewsd.common.utils.BaseUtils;
import cn.ewsd.common.utils.easyui.PageParam;
import cn.ewsd.common.utils.easyui.PageSet;
import cn.ewsd.material.model.MMaterial;
import cn.ewsd.material.service.MMaterialService;
import cn.ewsd.repository.model.MIn;
import cn.ewsd.repository.model.MStore;
import cn.ewsd.repository.service.MInService;
import cn.ewsd.repository.service.MStoreService;
import org.apache.http.entity.ContentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * @Author fengjw
 * @Email 11128474@qq.cn
 * @Date 2021-04-25 17:06:36
 */
@Controller
@RequestMapping("/repository/directIn")
public class DirectInController extends RepositoryBaseController {
    @Autowired
    private MInService mInService;
    @Autowired
    private MMaterialService mMaterialService;
    @Autowired
    private MStoreService mStoreService;
    @Value("${my.upload-dir}")
    private String uploadDir;
    //分页页面
    @RequestMapping("/index")
    public String index(@RequestParam Map<String, Object> params) {
        request.setAttribute("applyDate", XDate.dateTo10(XDate.getDate()));
        request.setAttribute("startDate", XDate.dateTo10(XDate.getMonth()+"01"));
        return "repository/directIn/index";
    }

    //获取分页集
    @ResponseBody
    @RequestMapping(value = "/getPageSet", method = RequestMethod.POST)
    public Object getPageSet(PageParam pageParam) {
        String filterSort = "";
        filterSort = BaseUtils.filterSort(request, filterSort + getAuthFilter());

        String startDateQry = XDate.dateTo8(request.getParameter("startDateQry"));
        String endDateQry = XDate.dateTo8(request.getParameter("endDateQry"));
        String storeNoQry = request.getParameter("storeNoQry");
        String matQry = request.getParameter("matQry");

        try{
            PageSet<MIn> pageSet = mInService.getDirectInPageSet(pageParam, filterSort, LoginInfo.getUuid(), startDateQry, endDateQry, matQry, storeNoQry);
            return pageSet;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @ResponseBody
    @RequestMapping(value = "/save")
    public Object save() {
        String matNo = request.getParameter("matNo");
        String storeNo = request.getParameter("storeNo");
        //String offerNo = request.getParameter("offerNo");
        String curAmount = request.getParameter("curAmount");
        String reserve2 = request.getParameter("reserve2");
        double amount = 0;
        try{
            amount = Double.parseDouble(curAmount);
        }catch (Exception e){
            return failure("入库数量必须是数字！");
        }
        String applyDate = XDate.dateTo8(request.getParameter("applyDate"));
        String ifAccount = request.getParameter("ifAccount");
        String remark = request.getParameter("remark");
        if(amount <= 0){
            return failure("入库数量必须是大于0的数字！");
        }
//        if("".equals(offerNo)){
//            return failure("请选择供应商！");
//        }

        try{
            mInService.saveDirectIn(LoginInfo.get(), reserve2, matNo, storeNo, amount, remark, ifAccount);
        }catch (Exception e){
            e.printStackTrace();
        }

        return success("入库成功！");
    }

    @RequestMapping("/goImport")
    public String goImport() {
        return "repository/directIn/go_import";
    }

    @Transactional
    @ResponseBody
    @RequestMapping(value = "/exportleadingIn")
    @ControllerLog(description = "导入Excel文件")
    public  Object exportleadingIn(String address) {
        int successNum = 0;
        int errNum = 0;
        String errText = "";
        //判断是否Excel文档
        String strsub =address.substring(address.length() -4);
        if (!".xls".equals(strsub) && !strsub.equals("xlsx")){
            return failure("请上传正确的Excel文档！");
        }

        //时间处理
        DateFormat formatD2 = new SimpleDateFormat("yyyyMMdd");
        String nowDay = formatD2.format(new Date());

        //获取系统判断存储地址
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
            //解析Excel文件数据
            FileInputStream fileInputStream = new FileInputStream(pdfFile);
            MultipartFile multipartFile = new MockMultipartFile(pdfFile.getName(), pdfFile.getName(), ContentType.APPLICATION_OCTET_STREAM.toString(), fileInputStream);
            //调ImportExeclUtil工具类
            List<Map<String,String>> varList = ImportExeclUtil.readExcel(multipartFile,0,0,0);
            if (varList.size() <= 1){
                return failure("Exclel文档没有数据！");
            }
            //删除第一个下标（是字段无需添加）
            varList.remove(0);

            //校验文件
            for (int i = 0; i < varList.size(); i++) {
                if(varList.get(i).get("入库仓库")==null){
                    return failure("第"+(i+1)+"行入库仓库错误!");
                }
                MStore mstore = mStoreService.getStoreByName(varList.get(i).get("入库仓库"));
                if(mstore==null){
                    return failure("第"+(i+1)+"行入库仓库不存在!");
                }
                varList.get(i).put("storeNo",mstore.getStoreNo());
                if(varList.get(i).get("物料编码")==null||varList.get(i).get("物料编码").length()!=8){
                    return failure("第"+(i+1)+"行物料编码错误!");
                }
                MMaterial material = mMaterialService.getMatByCode("X"+varList.get(i).get("物料编码"));
                if(material==null){
                    return failure("第"+(i+1)+"行物料编码不存在!");
                }
                varList.get(i).put("matNo",material.getMatNo());
                if(varList.get(i).get("单价")==null||new BigDecimal(varList.get(i).get("单价"))==null){
                    return failure("第"+(i+1)+"行单价错误!");
                }
                if (varList.get(i).get("入库数量")==null||"".equals(varList.get(i).get("入库数量"))
                        ||Integer.parseInt(varList.get(i).get("入库数量"))<1){
                    return failure("第"+(i+1)+"行入库数量错误!");
                }
                if (varList.get(i).get("入库日期")==null||"".equals(varList.get(i).get("入库日期"))||varList.get(i).get("入库日期").length()!=8){
                    return failure("第"+(i+1)+"行入库日期错误!");
                }
            }

            //存储操作
            for (int i = 0; i < varList.size(); i++) {
                mInService.saveDirectIn2(LoginInfo.get(),
                        varList.get(i).get("上架货位"),
                        varList.get(i).get("matNo"),
                        varList.get(i).get("storeNo"),
                        Double.parseDouble(varList.get(i).get("入库数量")),
                        varList.get(i).get("备注"),
                        "1",
                        new BigDecimal(varList.get(i).get("单价"))
                        );
            }

            return success("执行成功！");
        }catch (Exception e){
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            e.printStackTrace();
        }
        return null;
    }

}
