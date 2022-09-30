package cn.ewsd.fix.controller;

import cn.ewsd.base.utils.XDate;
import cn.ewsd.base.utils.XException;
import cn.ewsd.base.utils.jwt.LoginInfo;
import cn.ewsd.common.utils.BaseUtils;
import cn.ewsd.common.utils.easyui.PageParam;
import cn.ewsd.common.utils.easyui.PageSet;
import cn.ewsd.fix.service.ReturnMatService;
import cn.ewsd.material.model.MMaterial;
import cn.ewsd.repository.model.MIn;
import cn.ewsd.repository.service.MInService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

/**
 * 回收计划
 *
 * @Author zxrmine
 * @Email zxrmine@163.cn
 * @Date 2021-05-12 14:32:59
 */
@Controller
@RequestMapping("/fix/returnMat")
public class ReturnMatController extends FixBaseController {

    @Autowired
    private ReturnMatService returnMatService;
    @Autowired
    private MInService mInService;

    @RequestMapping("/index")
    public String index(@RequestParam Map<String, Object> params) {
        request.setAttribute("currentYear", Integer.parseInt(XDate.getYear()));
        request.setAttribute("endYear", Integer.parseInt(XDate.getYear())+10);
        request.setAttribute("currentMonth", Integer.parseInt(XDate.getMon()));
        request.setAttribute("date2Qry", XDate.dateTo10(XDate.getDate()));
        request.setAttribute("date1Qry", XDate.dateTo10(XDate.getMonth()+"01"));

        return "fix/returnMat/index";
    }

    @ResponseBody
    @RequestMapping(value = "/getPageSet", method = RequestMethod.POST)
    public Object getPageSet(PageParam pageParam) {
        String teamQry = LoginInfo.getOrgId();
        String matQry = request.getParameter("matQry");
        String yearQry = request.getParameter("yearQry");
        String monQry = request.getParameter("monQry");
        String month = yearQry+monQry;

        try {
            String filterSort = "";
            filterSort = BaseUtils.filterSort(request, filterSort + getAuthFilter());
            PageSet<MMaterial> pageSet = returnMatService.getPageSet(pageParam, filterSort, month, teamQry, matQry);
            return pageSet;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @ResponseBody
    @RequestMapping(value = "/getDetail", method = RequestMethod.POST)
    public Object getDetailPageSet(PageParam pageParam) {
        String date1Qry = XDate.dateTo8(request.getParameter("date1Qry"));
        String date2Qry = XDate.dateTo8(request.getParameter("date2Qry"));
        String matQry = request.getParameter("matQry1");

        try {
            String filterSort = "";
            filterSort = BaseUtils.filterSort(request, filterSort + getAuthFilter());
            PageSet<MIn> pageSet = mInService.getReturnMatList(pageParam, filterSort, LoginInfo.getOrgId(), date1Qry, date2Qry, matQry);
            return pageSet;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @ResponseBody
    @RequestMapping(value = "/submit")
    public Object submitReturnMat() {
        try {
            mInService.insertReturnMat(request, LoginInfo.get());
            return success("申请成功！");
        }catch (XException e){
            return failure(e.getInfo());
        }catch (Exception e){
            e.printStackTrace();
            return failure("申请失败！");
        }
    }

    @ResponseBody
    @RequestMapping(value = "/delete")
    public Object deleteReturnMat() {
        try {
            mInService.deleteReturnMat(request, LoginInfo.get());
            return success("删除成功！");
        }catch (XException e){
            return failure(e.getInfo());
        }catch (Exception e){
            e.printStackTrace();
            return failure("删除失败！");
        }
    }
}
