package cn.ewsd.cost.controller;

import cn.ewsd.base.utils.PoiUtils;
import cn.ewsd.base.utils.XDate;
import cn.ewsd.base.utils.XException;
import cn.ewsd.base.utils.jwt.LoginInfo;
import cn.ewsd.common.utils.BaseUtils;
import cn.ewsd.common.utils.easyui.PageParam;
import cn.ewsd.common.utils.easyui.PageSet;
import cn.ewsd.cost.model.MOutAssess;
import cn.ewsd.cost.service.MNormFeeService;
import cn.ewsd.cost.service.MOutAssessService;
import cn.ewsd.mdata.model.Organization;
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
@RequestMapping("/cost/matAssess")
public class MatAssessController extends CostBaseController {

    @Autowired
    private MOutAssessService mOutAssessService;
    @Autowired
    private MNormFeeService mNormFeeService;

    //分页页面
    @RequestMapping("/index")
    public String index(@RequestParam Map<String, Object> params) {
        String year = XDate.getYear();
        String month = XDate.getMon();
        request.setAttribute("currentYear", Integer.parseInt(year));
        request.setAttribute("endYear", Integer.parseInt(XDate.getYear())+10);
        request.setAttribute("currentMonth", Integer.parseInt(month));

        List<Organization> deptList = mOutAssessService.getOutAssessDept(year+month);
        String teamQry = "";
        if(deptList.size()>0){
            teamQry = deptList.get(0).getId()+"";
        }
        request.setAttribute("teamQry", teamQry);

        return "cost/matAssess/index";
    }

    @ResponseBody
    @RequestMapping(value = "/getDept")
    public Object getDept() {
        String month = request.getParameter("month");
        return mOutAssessService.getOutAssessDept(month);
    }

    @ResponseBody
    @RequestMapping(value = "/getList", method = RequestMethod.POST)
    public Object getList() {
        String yearQry = request.getParameter("yearQry");
        String monQry = request.getParameter("monQry");
        String teamQry = request.getParameter("teamQry");
        String month = yearQry + monQry;

        try{
           return mNormFeeService.getMatAssessList(month, teamQry);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @ResponseBody
    @RequestMapping(value = "/getForm")
    public Object getFormData() {
        try {
            String month = request.getParameter("yearQry") + request.getParameter("monQry");
            String teamQry = request.getParameter("teamQry");

            return mNormFeeService.getMatAssessFormData(month, teamQry);
        }catch (Exception e){
            e.printStackTrace();
            return failure("获取表单数据失败");
        }
    }

    @ResponseBody
    @RequestMapping(value = "/calcData")
    public Object calcData() {
        try {
            mNormFeeService.calcMatAssess(request, LoginInfo.get());
            return success("产生考核成功");
        }catch (XException e){
            return failure(e.getInfo());
        }catch (Exception e){
            e.printStackTrace();
            return failure("产生考核失败");
        }
    }

    @ResponseBody
    @RequestMapping(value = "/delete")
    public Object delete() {
        try {
            mNormFeeService.deleteMatAssess(request, LoginInfo.get());
            return success("删除成功");
        }catch (XException e){
            return failure(e.getInfo());
        }catch (Exception e){
            e.printStackTrace();
            return failure("删除失败");
        }
    }

    @ResponseBody
    @RequestMapping(value = "/save")
    public Object save() {
        try {
            mNormFeeService.saveMatAssess(request, LoginInfo.get());
            return success("保存成功");
        }catch (XException e){
            return failure(e.getInfo());
        }catch (Exception e){
            e.printStackTrace();
            return failure("保存失败");
        }
    }
}
