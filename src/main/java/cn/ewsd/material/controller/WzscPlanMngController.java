package cn.ewsd.material.controller;

import cn.ewsd.base.utils.ImportExeclUtil;
import cn.ewsd.base.utils.PoiUtils;
import cn.ewsd.base.utils.XDate;
import cn.ewsd.base.utils.XException;
import cn.ewsd.base.utils.jwt.LoginInfo;
import cn.ewsd.common.utils.BaseUtils;
import cn.ewsd.common.utils.easyui.PageParam;
import cn.ewsd.common.utils.easyui.PageSet;
import cn.ewsd.material.model.WzscPlan;
import cn.ewsd.material.service.WzscPlanService;
import cn.ewsd.mdata.service.UtilService;
import cn.ewsd.system.service.SysUserQryOrgService;
import org.apache.http.entity.ContentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @Author fengjw
 * @Email 11128474@qq.cn
 * @Date 2021-04-23 11:07:12
 */
@Controller
@RequestMapping("/material/wzscPlan")
public class WzscPlanMngController extends MaterialBaseController {
    @Autowired
    SysUserQryOrgService sysUserQryOrgService;
    @Autowired
    WzscPlanService wzscPlanService;
    @Autowired
    UtilService utilService;
    @Value("${my.upload-dir}")
    private String uploadDir;
    //分页页面
    @RequestMapping("/index")
    public String index(@RequestParam Map<String, Object> params) {
        request.setAttribute("currentYear", Integer.parseInt(XDate.getYear()));
        request.setAttribute("endYear", Integer.parseInt(XDate.getYear())+10);
        request.setAttribute("currentMonth", Integer.parseInt(XDate.getMon()));

        request.setAttribute("hasRight", utilService.checkRight(LoginInfo.getRoleId(), 2544));
        return "material/wzscPlan/index";
    }

    //获取分页集
    @ResponseBody
    @RequestMapping(value = "/getPageSet", method = RequestMethod.POST)
    public Object getPageSet(PageParam pageParam) {
        String filterSort = "";
        filterSort = BaseUtils.filterSort(request, filterSort + getAuthFilter());

        String yearQry = request.getParameter("yearQry");
        String monQry = request.getParameter("monQry");
        String monthQry = yearQry+monQry;
        String codeQry = request.getParameter("codeQry");
        String nameQry = request.getParameter("nameQry");
        String typeQry = request.getParameter("typeQry");
        String purcQry = request.getParameter("purcQry");
        String centerQry = request.getParameter("centerQry");
        String matTypeQry = request.getParameter("matTypeQry");
        String statusQry = request.getParameter("statusQry");

        try{
            String userId = LoginInfo.getUuid();
            String userDeptIds = sysUserQryOrgService.getUserDeptId(userId);
            PageSet<WzscPlan> pageSet = wzscPlanService.getPageSet(pageParam, filterSort, monthQry, userDeptIds, codeQry, typeQry, centerQry, purcQry, matTypeQry, nameQry, statusQry);
            return pageSet;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @RequestMapping("/importPage")
    public String importPage() {
        return "material/wzscPlan/import";
    }

    @ResponseBody
    @RequestMapping(value = "/import")
    public Object importData(String address) {
        //判断是否Excel文档
        String strsub =address.substring(address.length() -4);
        if (!".xls".equals(strsub) && !strsub.equals("xlsx")){
            return failure("请上传正确的Excel文档！");
        }

        //时间处理
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
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
            List<Map<String, String>> varList = ImportExeclUtil.readExcel(multipartFile, 0, 0, 0);
            if (varList.size() <= 1) {
                return failure("Excel文档没有数据！");
            }

            //删除第一个下标（是字段无需添加）
            varList.remove(0);

            String errorInfo = "";
            for (int i = 0; i < varList.size(); i++) {
                String factAddr = varList.get(i).get("供应仓库编码");
                if (!factAddr.contains("-")) {
                    errorInfo += "第" + (i + 2) + "行：供应仓库编码，格式不正确！<br/>";
                }
            }
            if (!"".equals(errorInfo)) {
                return failure(errorInfo);
            }

            wzscPlanService.importWzscPlan(varList, LoginInfo.getUuid(), LoginInfo.getUserName(), LoginInfo.getOrgId());
            return success("导入成功！");
        }catch (XException e){
            return failure(e.getInfo());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return failure("导入失败！");
        } catch (IOException e) {
            e.printStackTrace();
            return failure("导入失败！");
        } catch (Exception e){
            e.printStackTrace();
            return failure("导入失败！");
        }
    }

    @RequestMapping(value = "/export")
    public void export(HttpServletResponse response) {
        try{
            String textName = "行号,成本中心,中心描述,库存地,物料组,物料组描述,材料编码,材料描述,单位,价格,提报数量,金额,出账数,提报单号,提报日期,科目类型,计划类型,wbs元素,采购单号,行号,备注";
            String fieldName = "factoryNo,centerNo,centerName,matAddrName,erpType,typeName,matCode,matName,matUnit,matPrice,matAmount,matBala,amount,epId,createDate,itemTypeName,planTypeName,wbs,purchaseNo,purchaseList,remark";
            String yearQry = request.getParameter("yearQry");
            String monQry = request.getParameter("monQry");
            String monthQry = yearQry+monQry;
            String codeQry = request.getParameter("codeQry");
            String nameQry = request.getParameter("nameQry");
            String typeQry = request.getParameter("typeQry");
            String purcQry = request.getParameter("purcQry");
            String centerQry = request.getParameter("centerQry");
            String matTypeQry = request.getParameter("matTypeQry");
            String statusQry = request.getParameter("statusQry");
            String userId = LoginInfo.getUuid();
            String userDeptIds = sysUserQryOrgService.getUserDeptId(userId);
            List<WzscPlan> list = wzscPlanService.getList("", monthQry, userDeptIds, codeQry, typeQry, centerQry, purcQry, matTypeQry, nameQry, statusQry);
            for(int i=0; i<list.size();i++){
                WzscPlan wzscPlan = list.get(i);
                wzscPlan.setFactoryNo(i+1+"");
                wzscPlan.setCreateDate(XDate.dateTo10(wzscPlan.getCreateDate()));
            }
            PoiUtils.exportExcelOld(response, "商城计划", textName, fieldName, list);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @ResponseBody
    @RequestMapping(value = "/delete")
    public Object delete() {
        try{
            String[] uuids = request.getParameterValues("uuid");

            int result = wzscPlanService.deleteBatch(uuids);

            return result > 0 ? success("删除成功！") : failure("删除失败！");
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
}
