package cn.ewsd.repository.controller;

import cn.ewsd.base.utils.DataCantainer;
import cn.ewsd.base.utils.XDate;
import cn.ewsd.base.utils.XException;
import cn.ewsd.base.utils.jwt.LoginInfo;
import cn.ewsd.common.utils.BaseUtils;
import cn.ewsd.common.utils.easyui.PageParam;
import cn.ewsd.common.utils.easyui.PageSet;
import cn.ewsd.logistics.util.LLDReportTest;
import cn.ewsd.material.model.MMaterial;
import cn.ewsd.material.model.MPlan;
import cn.ewsd.material.service.MPlanService;
import cn.ewsd.repository.model.MMove;
import cn.ewsd.repository.model.MOut;
import cn.ewsd.repository.service.MMoveService;
import cn.ewsd.repository.service.MOutService;
import cn.ewsd.system.model.TCheck;
import cn.ewsd.system.service.TCheckService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @Author fengjw
 * @Email 11128474@qq.cn
 * @Date 2021-04-25 17:06:36
 */
@Controller
@RequestMapping("/repository/moveOrder")
public class MoveOrderController extends RepositoryBaseController {
    @Autowired
    private MMoveService mMoveService;
    @Autowired
    private MOutService mOutService;
    @Autowired
    private TCheckService tCheckService;

    @RequestMapping("/index")
    public String index(@RequestParam Map<String, Object> params) {
        request.setAttribute("currentYear", Integer.parseInt(XDate.getYear()));
        request.setAttribute("endYear", Integer.parseInt(XDate.getYear())+10);
        request.setAttribute("currentMonth", Integer.parseInt(XDate.getMon()));

        return "repository/moveOrder/index";
    }

    @ResponseBody
    @RequestMapping(value = "/getMoveOrderList", method = RequestMethod.POST)
    public Object getMoveOrderList(PageParam pageParam) {

        String yearQry = request.getParameter("yearQry");
        String monQry = request.getParameter("monQry");

        try{
            PageSet<MMove> pageSet  = mMoveService.getMoveOrderList(pageParam, yearQry+monQry);
            return pageSet;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @RequestMapping("/add")
    public String add() {
        return "repository/moveOrder/edit";
    }

    @ResponseBody
    @RequestMapping("/save")
    public Object save() {
        String inStore = request.getParameter("inStore");
        String outStore = request.getParameter("outStore");

        if("".equals(outStore)){
            return failure("移出仓库必须选择！");
        }
        if("".equals(inStore)){
            return failure("移入仓库必须选择！");
        }
        if(outStore.equals(inStore)){
            return failure("移出仓库不能和移入仓库相同！");
        }

        try {
            mMoveService.insertMove(inStore, outStore);
            return success("保存成功");
        }catch (XException e){
            return failure(e.getInfo());
        }catch (Exception e){
            e.printStackTrace();
            return failure("保存失败");
        }
    }

    @ResponseBody
    @RequestMapping("/delete")
    public Object delete() {
        String transNo = request.getParameter("transNo");

        try {
            mMoveService.deleteMove(transNo);
            return success("删除成功");
        }catch (XException e){
            return failure(e.getInfo());
        }catch (Exception e){
            e.printStackTrace();
            return failure("删除失败");
        }
    }

    @ResponseBody
    @RequestMapping(value = "/getMoveMatList", method = RequestMethod.POST)
    public Object getMoveMatList(PageParam pageParam) {

        String transNo = request.getParameter("transNo");
        String matCodeQry = request.getParameter("matCodeQry");
        String matNameQry = request.getParameter("matNameQry");

        String outStore = mMoveService.getMove(transNo).getOutStore();

        try{
            PageSet<MMaterial> pageSet  = mMoveService.getMoveMatList(pageParam, transNo, outStore, matCodeQry, matNameQry);
            return pageSet;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @ResponseBody
    @RequestMapping("/saveMat")
    public Object saveMat() {
        String transNo = request.getParameter("transNo");
        String[] matNos = request.getParameterValues("matNo");

        if(matNos==null || matNos.length<1){
            return failure("请选择要移库的材料！");
        }

        try {
            mMoveService.saveMat(transNo, matNos);
            return success("保存成功");
        }catch (XException e){
            return failure(e.getInfo());
        }catch (Exception e){
            e.printStackTrace();
            return failure("保存失败");
        }
    }

    @ResponseBody
    @RequestMapping(value = "/getMoveList", method = RequestMethod.POST)
    public Object getMoveList(PageParam pageParam) {

        String transNo = request.getParameter("transNo");

        try{
            PageSet<MMove> pageSet  = mMoveService.getMoveList(pageParam, transNo);
            return pageSet;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @ResponseBody
    @RequestMapping("/deleteMat")
    public Object deleteMat() {
        String transNo = request.getParameter("transNo1");
        String[] listNos = request.getParameterValues("listNo");

        if(listNos==null || listNos.length<1){
            return failure("请选择要删除的材料！");
        }

        try {
            mMoveService.deleteMat(transNo, listNos);
            return success("删除成功");
        }catch (XException e){
            return failure(e.getInfo());
        }catch (Exception e){
            e.printStackTrace();
            return failure("删除失败");
        }
    }

    @ResponseBody
    @RequestMapping("/saveAmount")
    public Object saveAmount() {
        String transNo = request.getParameter("transNo1");
        String[] listNos = request.getParameterValues("listNo");

        if(listNos==null || listNos.length<1){
            return failure("请选择要删除的材料！");
        }

        try {
            mMoveService.saveAmount(transNo, listNos, request);
            return success("保存成功");
        }catch (XException e){
            return failure(e.getInfo());
        }catch (Exception e){
            e.printStackTrace();
            return failure("保存失败");
        }
    }

    @ResponseBody
    @RequestMapping("/submit")
    public Object submitOrder() {
        String transNo = request.getParameter("transNo");

        try {
            mMoveService.submit(transNo);
            return success("提交成功");
        }catch (XException e){
            return failure(e.getInfo());
        }catch (Exception e){
            e.printStackTrace();
            return failure("提交失败");
        }
    }
}
