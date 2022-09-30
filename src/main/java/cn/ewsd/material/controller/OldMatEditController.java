package cn.ewsd.material.controller;

import cn.ewsd.base.utils.PoiUtils;
import cn.ewsd.base.utils.XDate;
import cn.ewsd.base.utils.XException;
import cn.ewsd.base.utils.jwt.LoginInfo;
import cn.ewsd.common.utils.BaseUtils;
import cn.ewsd.common.utils.easyui.PageParam;
import cn.ewsd.common.utils.easyui.PageSet;
import cn.ewsd.material.model.MTypeMat;
import cn.ewsd.material.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * @Author fengjw
 * @Email 11128474@qq.cn
 * @Date 2021-04-23 11:07:12
 */
@Controller
@RequestMapping("/material/oldMatEdit")
public class OldMatEditController extends MaterialBaseController {

    @Autowired
    private MTypeMatService mTypeMatService;
    @RequestMapping("/index")
    public String index(@RequestParam Map<String, Object> params) {
        request.setAttribute("currentYear", Integer.parseInt(XDate.getYear()));
        request.setAttribute("endYear", Integer.parseInt(XDate.getYear())+10);
        request.setAttribute("currentMonth", Integer.parseInt(XDate.getMon()));

        return "material/oldMatEdit/index";
    }

    @ResponseBody
    @RequestMapping("/getPageSet")
    public Object getPageSet(PageParam pageParam) {
        String filterSort = "";
        filterSort = BaseUtils.filterSort(request, filterSort + getAuthFilter());
        String matCodeQry = request.getParameter("matCodeQry");
        String matNameQry = request.getParameter("matNameQry");

        try{
            PageSet<MTypeMat> pageSet = mTypeMatService.getPageSet(pageParam, filterSort, matCodeQry, matNameQry);
            return pageSet;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @ResponseBody
    @RequestMapping(value = "/download")
    public void downloadPlan(HttpServletResponse response) {
        try{
            String matCodeQry = request.getParameter("matCodeQry");
            String matNameQry = request.getParameter("matNameQry");

            List<MTypeMat> list = mTypeMatService.getMTypeMatList(matCodeQry, matNameQry);

            String textName = "分类,分类描述,材料编码,材料名称,计量单位,单价,交旧比例";
            String fieldName = "erpType,typeName,matCode,matName,matUnit,matPrice,oldRate";
            PoiUtils.exportExcelOld(response, "交旧物资标准", textName, fieldName, list);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @ResponseBody
    @RequestMapping("/getComPlanPageSet")
    public Object getComPlanPageSet(PageParam pageParam) {
        String filterSort = "";
        filterSort = BaseUtils.filterSort(request, filterSort + getAuthFilter());
        String yearQry = request.getParameter("yearQry");
        String monQry = request.getParameter("monQry");
        String planMonth = yearQry+monQry;
        String matQry = request.getParameter("matQry");

        try{
            PageSet<MTypeMat> pageSet = mTypeMatService.getComPlanPageSet(pageParam, filterSort, planMonth, matQry);
            return pageSet;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @ResponseBody
    @RequestMapping("/comIns")
    public Object comIns() {
        try{
            mTypeMatService.comIns(request, LoginInfo.get());
            return success("增加成功！");
        }catch (XException e){
            return failure(e.getInfo());
        }catch (Exception e){
            e.printStackTrace();
            return failure("增加失败！");
        }
    }

    @RequestMapping("/add")
    public String add() {
        return "material/oldMatEdit/edit";
    }

    @ResponseBody
    @RequestMapping(value = "/insert", method = RequestMethod.POST)
    public Object insert(@ModelAttribute MTypeMat mTypeMat) {
        try{
            mTypeMatService.insertTypeMat(mTypeMat);
            return success("增加成功！");
        }catch (XException e){
            return failure(e.getInfo());
        }catch (Exception e){
            e.printStackTrace();
            return failure("增加失败！");
        }
    }

    @ResponseBody
    @RequestMapping(value = "/save")
    public Object save() {
        try{
            mTypeMatService.saveTypeMat(request, LoginInfo.get());
            return success("保存成功！");
        }catch (XException e){
            return failure(e.getInfo());
        }catch (Exception e){
            e.printStackTrace();
            return failure("保存失败！");
        }
    }

    @ResponseBody
    @RequestMapping(value = "/delete")
    public Object delete() {
        try{
            mTypeMatService.deleteTypeMat(request, LoginInfo.get());
            return success("删除成功！");
        }catch (XException e){
            return failure(e.getInfo());
        }catch (Exception e){
            e.printStackTrace();
            return failure("删除失败！");
        }
    }

    @ResponseBody
    @RequestMapping(value = "/deal")
    public Object deal() {
        try{
            mTypeMatService.dealTypeMat(request, LoginInfo.get());
            return success("生成成功！");
        }catch (XException e){
            return failure(e.getInfo());
        }catch (Exception e){
            e.printStackTrace();
            return failure("生成失败！");
        }
    }
}
