package cn.ewsd.mdata.controller;

import cn.ewsd.base.utils.ImportExeclUtil;
import cn.ewsd.base.utils.PoiUtils;
import cn.ewsd.base.utils.StringUtils;
import cn.ewsd.common.annotation.ControllerLog;
import cn.ewsd.base.utils.jwt.LoginInfo;
import cn.ewsd.common.utils.BaseUtils;
import cn.ewsd.mdata.model.CsExcelUser;
import cn.ewsd.mdata.model.DataBaseField;
import cn.ewsd.mdata.service.CsExcelUserService;

import cn.ewsd.system.mapper.GeneratorMapper;
import org.apache.http.entity.ContentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;


/**
 * @Author zhuyongjing
 * @Email 1239290112@qq.com
 * @Date 2019-06-05 09:15:58
 */
@Controller
@RequestMapping("/mdata/exportImport")
public class ExportImportController extends MdataBaseController {


    @Autowired
    private GeneratorMapper generatorMapper;

    @Autowired
    private CsExcelUserService csExcelUserService;
    @Value("${my.upload-dir}")
    private String uploadDir;

    //导出Excel文件
    @RequestMapping(value = "/exportExcel")
    @ControllerLog(description = "导出Excel文件")
    public void exportExcel(HttpServletResponse response, String tablesName) {
        String textName = request.getParameter("textName").substring(0, request.getParameter("textName").length() - 1);
        String fieldName = request.getParameter("fieldName").substring(0, request.getParameter("fieldName").length() - 1);
        List<CsExcelUser> list = csExcelUserService.getDataByTablesAndField(tablesName, fieldName);
        //导出
        PoiUtils.exportExcel(response, "Excel用户", textName, StringUtils.camelCaseName(fieldName), list, LoginInfo.getUserName());
    }

    //导出Excel文件模板

    @RequestMapping(value = "/exportExcelTemplate")
    @ControllerLog(description = "导出Excel文件")
    public void exportExcelTemplate(HttpServletResponse response, String tablesName) {
        String textName = request.getParameter("textName").substring(0, request.getParameter("textName").length() - 1);
        String fieldName = request.getParameter("fieldName").substring(0, request.getParameter("fieldName").length() - 1);
        List<CsExcelUser> list = csExcelUserService.getDataByTablesAndField(tablesName+" limit 1", fieldName);
        for(int i = 0; i < 5; i++){
            CsExcelUser csExcelUser  = new CsExcelUser();
            list.add(csExcelUser);
        }
        //导出
        PoiUtils.exportExcelTemplate(response, "Excel用户模板", textName, StringUtils.camelCaseName(fieldName), list,LoginInfo.getUserName());
    }

    //查询字段信息
    @ResponseBody
    @RequestMapping(value = "/fieldInformation")
    @ControllerLog(description = "查询字段信息")
    public Object fieldInformation(String tablesName) {
        //查询表字段信息
        List<Map<String, String>> columns = generatorMapper.queryColumns(tablesName);
        return columns;
    }

    //打开选择导出字段界面
    @RequestMapping(value = "/select")
    @ControllerLog(description = "打开选择导出字段界面")
    public String select(String tablesName) {
        //查询表字段信息
        List<Map<String, String>> columns = generatorMapper.queryColumns(tablesName);
        List<DataBaseField> dataBaseFieldList = new ArrayList<>();
        for (int i = 0; i < columns.size(); i++) {
            DataBaseField dataBaseField = new DataBaseField();
            dataBaseField.setColumnName(columns.get(i).get("columnName"));
            dataBaseField.setColumnComment(columns.get(i).get("columnComment"));
            dataBaseFieldList.add(dataBaseField);
        }
        request.setAttribute("dataBaseFieldList", dataBaseFieldList);
        request.setAttribute("tablesName", tablesName);
        return "ucenter/exportImport/export";
    }

    //打开选择导出字段界面
    @RequestMapping(value = "/import")
    @ControllerLog(description = "打开选择导出字段界面")
    public String imports(String tablesName) {
        //查询表字段信息
        List<Map<String, String>> columns = generatorMapper.queryColumns(tablesName);
        List<DataBaseField> dataBaseFieldList = new ArrayList<>();
        for (int i = 0; i < columns.size(); i++) {
            DataBaseField dataBaseField = new DataBaseField();
            dataBaseField.setColumnName(columns.get(i).get("columnName"));
            dataBaseField.setColumnComment(columns.get(i).get("columnComment"));
            dataBaseFieldList.add(dataBaseField);
        }
        request.setAttribute("dataBaseFieldList", dataBaseFieldList);
        request.setAttribute("tablesName", tablesName);
        return "ucenter/exportImport/import";
    }


    @ResponseBody
    @RequestMapping(value = "/exportleadingIn")
    @ControllerLog(description = "导入Excel文件")
    public  Object exportleadingIn(String address) {

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
            MultipartFile multipartFile = new MockMultipartFile(pdfFile.getName(), pdfFile.getName(),ContentType.APPLICATION_OCTET_STREAM.toString(), fileInputStream);
            //调ImportExeclUtil工具类
            List<Map<String,String>> varList = ImportExeclUtil.readExcel(multipartFile,1,1,0);
            if (varList.size() <= 1){
              return failure("Exclel文档没有数据！");
            }

            //删除第一个下标（是字段无需添加）
            varList.remove(0);
            //创建list对象
            //数据处理
            List<CsExcelUser> list = new ArrayList<>();
            for(int i = 0; i < varList.size(); i++){
                CsExcelUser csExcelUser = new CsExcelUser();
                csExcelUser.setUuid(varList.get(i).get("uuid"));
                csExcelUser.setRemark(varList.get(i).get("remark"));
                csExcelUser.setBirthday(varList.get(i).get("birthday"));
                csExcelUser.setEducation(varList.get(i).get("education"));
                csExcelUser.setCellphone(varList.get(i).get("cellphone"));
                csExcelUser.setEmail(varList.get(i).get("email"));
                csExcelUser.setUserName(varList.get(i).get("userName"));
                csExcelUser.setUserNameId(varList.get(i).get("userNameId"));
                csExcelUser.setPassword(varList.get(i).get("password"));
                if (varList.get(i).get("age") != null){
                    csExcelUser.setAge(Integer.parseInt(varList.get(i).get("age")));

                }
                csExcelUser.setCountry(varList.get(i).get("country"));

                csExcelUser.setCreatorId(varList.get(i).get("creatorId"));
                csExcelUser.setCreator(varList.get(i).get("creator"));
                if (varList.get(i).get("createTime") != null){
                    csExcelUser.setCreateTime(format.parse(varList.get(i).get("createTime")));

                }
                if (varList.get(i).get("creatorOrgId") != null){
                    csExcelUser.setCreatorOrgId(Integer.parseInt(varList.get(i).get("creatorOrgId")));

                }
                csExcelUser.setModifier(varList.get(i).get("modifier"));
                csExcelUser.setModifierId(varList.get(i).get("modifierId"));
                if (varList.get(i).get("modifyTime") != null ){
                    csExcelUser.setModifyTime(format.parse(varList.get(i).get("modifyTime")));

                }
                if (csExcelUser.getUuid() == null || "".equals(csExcelUser.getUuid())){
                    csExcelUser.setUuid(BaseUtils.UUIDGenerator());
                }
                if (csExcelUser.getCreatorOrgId() == null || "".equals(csExcelUser.getCreatorOrgId())){
                    csExcelUser.setCreatorOrgId(Integer.parseInt(LoginInfo.getOrgId()));
                }
                list.add(csExcelUser);
            }

            //数据插入
            int result =csExcelUserService.batchInsert(list);
            return result > 0 ? success("导入成功！") : failure("导入失败！");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }
}
