package cn.ewsd.fix.controller;

import java.util.List;
import java.util.Map;

import cn.ewsd.base.utils.XDate;
import cn.ewsd.base.utils.XException;
import cn.ewsd.base.utils.jwt.LoginInfo;
import cn.ewsd.material.model.MPrj;
import cn.ewsd.material.service.MPrjService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.stereotype.Controller;

import cn.ewsd.common.annotation.ControllerLog;
import cn.ewsd.common.utils.BaseUtils;
import cn.ewsd.common.utils.easyui.PageParam;
import cn.ewsd.common.utils.easyui.PageSet;

import cn.ewsd.fix.model.MBackQuota;
import cn.ewsd.fix.service.MBackQuotaService;

/**
 * 回收定额
 *
 * @Author zxrmine
 * @Email zxrmine@163.cn
 * @Date 2021-05-12 14:04:07
 */
@Controller
@RequestMapping("/fix/mBackQuota")
public class MBackQuotaController extends FixBaseController {

    @Autowired
    private MBackQuotaService mBackQuotaService;
    @Autowired
    private MPrjService mPrjService;


    //分页页面
    @RequestMapping("/index")
    public String index(@RequestParam Map<String, Object> params) {
        request.setAttribute("currentYear", Integer.parseInt(XDate.getYear()));
        request.setAttribute("endYear", Integer.parseInt(XDate.getYear())+10);
        request.setAttribute("currentMonth", Integer.parseInt(XDate.getMon()));

        String prjNo = "";
        List<MPrj> list = mPrjService.getPrjFromBackQuota();
        if(list.size()>0){
            prjNo = list.get(0).getPrjNo();
        }
        request.setAttribute("prjNo", prjNo);

        return "fix/mBackQuota/index";
    }

    //获取分页集
    @ResponseBody
    @RequestMapping(value = "/getPageSet", method = RequestMethod.POST)
    @ControllerLog(description = "获得MBackQuota分页集数据")
    public Object getPageSet(PageParam pageParam) {
        String yearQry = request.getParameter("yearQry");
        String monQry = request.getParameter("monQry");
        String monthQry = yearQry+monQry;
        String prjQry = request.getParameter("prjQry");

        try {
            String filterSort = "";
            filterSort = BaseUtils.filterSort(request, filterSort + getAuthFilter());
            PageSet<MBackQuota> pageSet = mBackQuotaService.getPageSet(pageParam, filterSort, monthQry, prjQry, LoginInfo.getOrgId());
            return pageSet;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @ResponseBody
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public Object saveBackQuota() {
        try {
            mBackQuotaService.insertBackQuota(request, LoginInfo.get());
            return success("保存成功！");
        }catch (XException e){
            return failure(e.getInfo());
        }catch (Exception e){
            e.printStackTrace();
            return failure("保存失败！");
        }
    }

    @ResponseBody
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public Object deleteBackQuota() {
        try {
            mBackQuotaService.deleteBackQuota(request);
            return success("保存成功！");
        }catch (XException e){
            return failure(e.getInfo());
        }catch (Exception e){
            e.printStackTrace();
            return failure("保存失败！");
        }
    }
}
