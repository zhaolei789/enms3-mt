package cn.ewsd.material.controller;

import cn.ewsd.base.utils.XDate;
import cn.ewsd.base.utils.XException;
import cn.ewsd.base.utils.jwt.LoginInfo;
import cn.ewsd.common.utils.BaseUtils;
import cn.ewsd.common.utils.easyui.PageParam;
import cn.ewsd.common.utils.easyui.PageSet;
import cn.ewsd.material.model.MAskCode;
import cn.ewsd.material.model.MMakeForm;
import cn.ewsd.material.service.MAskCodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @Author fengjw
 * @Email 11128474@qq.cn
 * @Date 2021-04-21 16:55:52
 */
@Controller
@RequestMapping("/material/matAskEdit")
public class MatAskEditController extends MaterialBaseController {

    @Autowired
    private MAskCodeService mAskCodeService;
    //分页页面
    @RequestMapping("/index")
    public String index(@RequestParam Map<String, Object> params) {
        request.setAttribute("startDate", XDate.dateTo10(XDate.getMonth()+"01"));
        request.setAttribute("endDate", XDate.dateTo10(XDate.getDate()));
        request.setAttribute("needDate", XDate.dateTo10(XDate.addDate(XDate.getDate(), 30)));

        return "material/matAskEdit/index";
    }

    //获取分页集
    @ResponseBody
    @RequestMapping(value = "/getPageSet", method = RequestMethod.POST)
    public Object getPageSet(PageParam pageParam) {
        String occDate1Qry = XDate.dateTo8(request.getParameter("occDate1Qry"));
        String occDate2Qry = XDate.dateTo8(request.getParameter("occDate2Qry"));
        String askNameQry = request.getParameter("askNameQry");

        String filterSort = "";
        filterSort = BaseUtils.filterSort(request, filterSort + getAuthFilter());
        PageSet<MAskCode> pageSet = mAskCodeService.getPageSet(pageParam, filterSort, occDate1Qry, occDate2Qry, LoginInfo.getOrgId(), askNameQry);
        return pageSet;
    }

    @ResponseBody
    @RequestMapping(value = "/getMakeFormList", method = RequestMethod.POST)
    public Object getMakeFormList() {
        String matQry = request.getParameter("matQry");
        String askNo = request.getParameter("askNo");

        try {
            List<MMakeForm> list = mAskCodeService.getMakeFormList(askNo, matQry);
            return list;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @RequestMapping("/add")
    public String add() {
        return "material/matAskEdit/add";
    }

    @ResponseBody
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public Object saveMatAsk(@ModelAttribute MAskCode mAskCode) {
        try {
            mAskCodeService.saveAskCode(request, LoginInfo.get(), mAskCode);
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
        try {
            String askNo = request.getParameter("askNo");
            mAskCodeService.deleteAskCode(askNo);
            return success("删除成功！");
        }catch (XException e){
            return failure(e.getInfo());
        }catch (Exception e){
            e.printStackTrace();
            return failure("删除失败！");
        }
    }

    @ResponseBody
    @RequestMapping(value = "/submit")
    public Object submit() {
        try {
            mAskCodeService.submitAskCode(request, LoginInfo.get());
            return success("提交成功！");
        }catch (XException e){
            return failure(e.getInfo());
        }catch (Exception e){
            e.printStackTrace();
            return failure("提交失败！");
        }
    }
}
