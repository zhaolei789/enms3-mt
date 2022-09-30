package cn.ewsd.material.controller;

import cn.ewsd.base.utils.IParameter;
import cn.ewsd.base.utils.XDate;
import cn.ewsd.base.utils.jwt.LoginInfo;
import cn.ewsd.common.utils.BaseUtils;
import cn.ewsd.common.utils.easyui.PageParam;
import cn.ewsd.common.utils.easyui.PageSet;
import cn.ewsd.material.model.MPlan;
import cn.ewsd.material.model.Riku;
import cn.ewsd.material.service.RikuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.ArrayList;

/**
 * @Author fengjw
 * @Email 11128474@qq.cn
 * @Date 2021-04-23 11:07:12
 */
@Controller
@RequestMapping("/material/riku")
public class RikuController extends MaterialBaseController {
    @Autowired
    private RikuService rikuService;

    //分页页面
    @RequestMapping("/index")
    public String index() {
        String date = XDate.getDate();
        int day = Integer.parseInt(date.substring(6));

        request.setAttribute("startDateQry", day>=6&&day<=24 ? XDate.dateTo10(XDate.getMonth()+"06") : day>=25 ? XDate.dateTo10(XDate.getMonth()+"25") : XDate.dateTo10(XDate.addMonth(XDate.getMonth(), -1)+"25"));
        request.setAttribute("endDateQry", day>=6&&day<=24 ? XDate.dateTo10(XDate.getMonth()+"24") : day>=25 ? XDate.dateTo10(XDate.addMonth(XDate.getMonth(), 1)+"05") : XDate.dateTo10(XDate.getMonth()+"05"));
        request.setAttribute("monthQry", XDate.getMonth());

        return "material/riku/index";
    }

    //获取分页集
    @ResponseBody
    @RequestMapping(value = "/getMatList", method = RequestMethod.POST)
    public Object getMatList(PageParam pageParam) {
        String filterSort = "";
        filterSort = BaseUtils.filterSort(request, filterSort + getAuthFilter());

        try{
            IParameter iParameter = new IParameter(request);

            String matQry = iParameter.getParameter("matQry");
            String abcTypeQry = iParameter.getParameter("abcTypeQry");
            String startDateQry = XDate.dateTo8(iParameter.getParameter("startDateQry"));
            String endDateQry = XDate.dateTo8(iParameter.getParameter("endDateQry"));
            String planTypeQry = iParameter.getParameter("planTypeQry");
            String[] costCenterQry = iParameter.getParameters("costCenterQry");
            String monthQry = iParameter.getParameter("monthQry");
            String ifAqfyQry = iParameter.getParameter("ifAqfyQry");
            String wbsQry = iParameter.getParameter("wbsQry");
            String csStr = "";
            for(int i=0; i<costCenterQry.length; i++){
                csStr += i==0 ? costCenterQry[i] : ","+costCenterQry[i];
            }

            PageSet<Riku> pageSet = rikuService.getMatList(pageParam.getPage(), pageParam.getRows(), matQry, abcTypeQry, startDateQry, endDateQry, planTypeQry, csStr, monthQry, ifAqfyQry, wbsQry);
            return pageSet;
        }catch (Exception e){
            e.printStackTrace();
        }

        return null;
    }

    @ResponseBody
    @RequestMapping("/delete")
    public Object deletePlan() {
        String[] planNos = request.getParameterValues("planNo");

        if(planNos==null || planNos.length<1){
            return failure("请至少选择一条计划！");
        }

        rikuService.deletePlan(planNos, LoginInfo.getUuid(), LoginInfo.getUserName(), LoginInfo.getOrgId());

        return success("删除成功！");
    }

    @ResponseBody
    @RequestMapping(value = "/save")
    public Object savePlan() {
        String[] planNos = request.getParameterValues("planNo");

        if(planNos==null || planNos.length<1){
            return failure("请至少选择一条计划！");
        }

        ArrayList<MPlan> list = new ArrayList<>();
        for(int i=0; i<planNos.length; i++){
            String planNo = planNos[i];
            double matAmount = 0;
            try{
                matAmount = Double.parseDouble(request.getParameter("amount_"+planNo));
            }catch (Exception e){
                return failure("计划数必须是大于0的数字！");
            }
            if(matAmount<=0){
                return failure("计划数必须是大于0的数字！");
            }
            String abcType = request.getParameter("abcType_"+planNo);

            MPlan mPlan = new MPlan();
            mPlan.setPlanNo(planNo);
            mPlan.setMatAmount(new BigDecimal(matAmount));
            mPlan.setAbcType(abcType);
            list.add(mPlan);
        }

        rikuService.savePlan(list, LoginInfo.getUuid(), LoginInfo.getUserName(), LoginInfo.getOrgId());

        return success("保存计划成功！");
    }

    @ResponseBody
    @RequestMapping(value = "/finish", method = RequestMethod.POST)
    public Object save() {
        rikuService.finish(request, LoginInfo.getUuid(), LoginInfo.getUserName());

        return success("完成利库！");
    }
}
