package cn.ewsd.cost.controller;

import cn.ewsd.base.utils.PoiUtils;
import cn.ewsd.base.utils.XDate;
import cn.ewsd.base.utils.XException;
import cn.ewsd.base.utils.jwt.LoginInfo;
import cn.ewsd.common.utils.BaseUtils;
import cn.ewsd.common.utils.easyui.PageParam;
import cn.ewsd.common.utils.easyui.PageSet;
import cn.ewsd.cost.model.MOutAssess;
import cn.ewsd.cost.model.MRation;
import cn.ewsd.cost.service.MOutAssessService;
import cn.ewsd.cost.service.MRationService;
import cn.ewsd.material.model.MMaterial;
import cn.ewsd.system.service.ConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * 工程结算
 *
 * @Author zxrmine
 * @Email zxrmine@163.cn
 * @Date 2021-09-03 08:39:36
 */
@Controller
@RequestMapping("/cost/ration")
public class RationController extends CostBaseController {

    @Autowired
    private MRationService mRationService;


    //分页页面
    @RequestMapping("/index")
    public String index(@RequestParam Map<String, Object> params) {
        return "cost/ration/index";
    }

    @ResponseBody
    @RequestMapping(value = "/getPageSet", method = RequestMethod.POST)
    public Object getPageSet(PageParam pageParam) {
        String rationQry = request.getParameter("rationQry");

        try{
            String filterSort = "";
            filterSort = BaseUtils.filterSort(request, filterSort + getAuthFilter());
            PageSet<MRation> pageSet = mRationService.getRationPageSet(pageParam, filterSort, rationQry);
            return pageSet;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @RequestMapping("/edit")
    public String add() {
        return "cost/ration/add";
    }

    @ResponseBody
    @RequestMapping(value = "/insert")
    public Object insert() {
        try {
            mRationService.insertRation(request);
            return success("新增成功");
        }catch (XException e){
            return failure(e.getInfo());
        }catch (Exception e){
            e.printStackTrace();
            return failure("新增失败");
        }
    }

    @ResponseBody
    @RequestMapping(value = "/getRationByNo")
    public Object getRationByNo() {
        try{
            String rationNo = request.getParameter("rationNo");
            MRation ration = mRationService.getRationByNo(rationNo);
            System.out.println(ration.getRationName());
            return ration;
        }catch (Exception e){
            return null;
        }
    }

    @ResponseBody
    @RequestMapping(value = "/update")
    public Object update(MRation mRation) {
        try {
            mRationService.updateRation(mRation);
            return success("修改成功");
        }catch (XException e){
            return failure(e.getInfo());
        }catch (Exception e){
            e.printStackTrace();
            return failure("修改失败");
        }
    }

    @ResponseBody
    @RequestMapping(value = "/delete")
    public Object delete() {
        try {
            mRationService.deleteRation(request);
            return success("删除成功");
        }catch (XException e){
            return failure(e.getInfo());
        }catch (Exception e){
            e.printStackTrace();
            return failure("删除失败");
        }
    }

    @ResponseBody
    @RequestMapping(value = "/getMatPageSet", method = RequestMethod.POST)
    public Object getMatPageSet(PageParam pageParam) {
        String rationNo = request.getParameter("rationNo");
        String matQry = request.getParameter("matQry");

        try{
            String filterSort = "";
            filterSort = BaseUtils.filterSort(request, filterSort + getAuthFilter());
            PageSet<MMaterial> pageSet = mRationService.getRationMatPageSet(pageParam, filterSort, rationNo, matQry);
            return pageSet;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @ResponseBody
    @RequestMapping(value = "/getMatForSelect")
    public Object getMat() {
        String q = request.getParameter("q");
        return mRationService.getMatForSelect(q);
    }

    @ResponseBody
    @RequestMapping(value = "/deleteMat")
    public Object deleteMat() {
        try {
            mRationService.deleteMat(request);
            return success("删除成功");
        }catch (XException e){
            return failure(e.getInfo());
        }catch (Exception e){
            e.printStackTrace();
            return failure("删除失败");
        }
    }

    @ResponseBody
    @RequestMapping(value = "/insertMat")
    public Object save() {
        try {
            mRationService.insertMat(request);
            return success("保存成功");
        }catch (XException e){
            return failure(e.getInfo());
        }catch (Exception e){
            e.printStackTrace();
            return failure("保存失败");
        }
    }
}
