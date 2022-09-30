package cn.ewsd.material.controller;

import cn.ewsd.base.utils.Data;
import cn.ewsd.base.utils.ImportExeclUtil;
import cn.ewsd.base.utils.PoiUtils;
import cn.ewsd.base.utils.XDate;
import cn.ewsd.base.utils.jwt.LoginInfo;
import cn.ewsd.common.utils.BaseUtils;
import cn.ewsd.common.utils.easyui.PageParam;
import cn.ewsd.common.utils.easyui.PageSet;
import cn.ewsd.material.model.MInAccount;
import cn.ewsd.material.service.MInAccountService;
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
import java.util.List;
import java.util.Map;

/**
 * @Author fengjw
 * @Email 11128474@qq.cn
 * @Date 2021-04-23 11:07:12
 */
@Controller
@RequestMapping("/material/mInAccount")
public class MInAccountController extends MaterialBaseController {

    @Autowired
    private MInAccountService mInAccountService;
    @Value("${my.upload-dir}")
    private String uploadDir;
    //分页页面
    @RequestMapping("/index")
    public String index(@RequestParam Map<String, Object> params) {
        request.setAttribute("currentYear", Integer.parseInt(XDate.getYear()));
        request.setAttribute("endYear", Integer.parseInt(XDate.getYear())+10);
        request.setAttribute("currentMonth", Integer.parseInt(XDate.getMon()));

        return "material/mInAccount/index";
    }

    //获取分页集
    @ResponseBody
    @RequestMapping(value = "/getPageSet", method = RequestMethod.POST)
    public Object getPageSet(PageParam pageParam) {
        try{
            String yearQry = request.getParameter("yearQry");
            String monQry = request.getParameter("monQry");
            String numberQry = request.getParameter("numberQry");
            String orderQry = request.getParameter("orderQry");

            String filterSort = "";
            filterSort = BaseUtils.filterSort(request, filterSort + getAuthFilter());
            PageSet<MInAccount> pageSet = mInAccountService.getPageSet(pageParam, filterSort, yearQry, monQry, numberQry, orderQry);
            return pageSet;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @RequestMapping("/importPage")
    public String importPage() {
        return "material/mInAccount/import";
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
       // File pdfFile =null;
//        if (status){
//            pdfFile = new File("D:/zysd/"+address);
//        }else {
        //pdfFile = new File("/home/"+address);
//        }
        File pdfFile = new File(uploadDir+address);
        try {
            //解析Excel文件数据
            FileInputStream fileInputStream = new FileInputStream(pdfFile);
            MultipartFile multipartFile = new MockMultipartFile(pdfFile.getName(), pdfFile.getName(), ContentType.APPLICATION_OCTET_STREAM.toString(), fileInputStream);
            //调ImportExeclUtil工具类
            List<Map<String,String>> varList = ImportExeclUtil.readExcel(multipartFile,2,0,0);
            if (varList.size() <= 1){
                return failure("Excel文档没有数据！");
            }

            //删除第一个下标（是字段无需添加）
            varList.remove(0);

            String errorInfo = "";
            for(int i=0; i<varList.size(); i++){
                String factAmount = varList.get(i).get("实发数量");
                String factCheck = varList.get(i).get("实验数量");
                String noTaxPrice = varList.get(i).get("进货单价");
                String noTaxBala = varList.get(i).get("金额");
                String taxPrice = varList.get(i).get("含税单价");
                String taxBala = varList.get(i).get("价税金额");
                String occDate = varList.get(i).get("日期");
                if (!"".equals(factAmount) && !Data.isDouble(Data.finalToNormal(factAmount))) {
                    errorInfo += "第"+(i+3)+"行：实发数，格式不正确！<br/>";
                }
                if (!"".equals(factCheck) && !Data.isDouble(Data.finalToNormal(factCheck))) {
                    errorInfo += "第"+(i+3)+"行：实验数，格式不正确！<br/>";
                }
                if (!"".equals(noTaxPrice) && !Data.isDouble(Data.finalToNormal(noTaxPrice))) {
                    errorInfo += "第"+(i+3)+"行：进货单价，格式不正确！<br/>";
                }
                if (!"".equals(noTaxBala) && !Data.isDouble(Data.finalToNormal(noTaxBala))) {
                    errorInfo += "第"+(i+3)+"行：金额，格式不正确！<br/>";
                }
                if (!"".equals(taxPrice) && !Data.isDouble(Data.finalToNormal(taxPrice))) {
                    errorInfo += "第"+(i+3)+"行：含税单价，格式不正确！<br/>";
                }
                if (!"".equals(taxBala) && !Data.isDouble(Data.finalToNormal(taxBala))) {
                    errorInfo += "第"+(i+3)+"行：价税金额，格式不正确！<br/>";
                }
                if ("".equals(occDate) || !XDate.validDate8(occDate)) {
                    errorInfo += "第"+(i+3)+"行：结算日期，格式不正确！<br/>";
                }
            }
            if(!"".equals(errorInfo)){
                return failure(errorInfo);
            }

            int result = mInAccountService.importInAccount(varList, LoginInfo.getUuid(), LoginInfo.getUserName(), LoginInfo.getOrgId());
            return result > 0 ? success("导入成功！") : failure("导入失败！");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    @RequestMapping(value = "/export")
    public void export(HttpServletResponse response) {
        try{

            String yearQry = request.getParameter("yearQry");
            String monQry = request.getParameter("monQry");
            String numberQry = request.getParameter("numberQry");
            String orderQry = request.getParameter("orderQry");
            List<MInAccount> list = mInAccountService.getList(yearQry, monQry, numberQry, orderQry);

            String textName = "行号,成本中心,中心描述,材料编码,材料描述,实发数量,实验数量,进货单价,金额,税率,含税单价,价税金额,订单号,发票号";
            String fieldName = "rowNumber,centerNo,centerName,matCode,matName,outAmount,realAmount,setPrice,inBala,taxRate,taxPrice,setBala,orderNo,billNumber";
            for(int i=0; i<list.size();i++){
                MInAccount mInAccount = list.get(i);
                mInAccount.setRowNumber(i+1);
            }
            PoiUtils.exportExcelOld(response, "入库结算", textName, fieldName, list);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @ResponseBody
    @RequestMapping(value = "/delete")
    public Object delete() {
        try{
            String[] uuids = request.getParameterValues("uuid");

            int result = mInAccountService.deleteBatch(uuids);

            return result > 0 ? success("删除成功！") : failure("删除失败！");
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
}
