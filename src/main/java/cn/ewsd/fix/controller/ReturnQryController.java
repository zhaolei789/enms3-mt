package cn.ewsd.fix.controller;

import cn.ewsd.base.utils.PoiUtils;
import cn.ewsd.base.utils.XDate;
import cn.ewsd.base.utils.XException;
import cn.ewsd.base.utils.jwt.LoginInfo;
import cn.ewsd.common.utils.BaseUtils;
import cn.ewsd.common.utils.easyui.PageParam;
import cn.ewsd.common.utils.easyui.PageSet;
import cn.ewsd.repository.model.MIn;
import cn.ewsd.repository.service.MInService;
import cn.ewsd.system.service.SysUserQryOrgService;
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
 * 回收计划
 *
 * @Author zxrmine
 * @Email zxrmine@163.cn
 * @Date 2021-05-12 14:32:59
 */
@Controller
@RequestMapping("/fix/returnQry")
public class ReturnQryController extends FixBaseController {

    @Autowired
    private MInService mInService;
    @Autowired
    private SysUserQryOrgService sysUserQryOrgService;

    @RequestMapping("/index")
    public String index(@RequestParam Map<String, Object> params) {
        request.setAttribute("date1Qry", XDate.dateTo10(XDate.getMonth()+"01"));
        request.setAttribute("date2Qry", XDate.dateTo10(XDate.getDate()));

        return "fix/returnQry/index";
    }

    @ResponseBody
    @RequestMapping(value = "/getPageSet", method = RequestMethod.POST)
    public Object getPageSet(PageParam pageParam) {
        String date1Qry = XDate.dateTo8(request.getParameter("date1Qry"));
        String date2Qry = XDate.dateTo8(request.getParameter("date2Qry"));
        String matQry = request.getParameter("matQry");
        String typeQry = request.getParameter("typeQry");
        String userDeptIds = sysUserQryOrgService.getUserDeptId(LoginInfo.getUuid());

        try {
            String filterSort = "";
            filterSort = BaseUtils.filterSort(request, filterSort + getAuthFilter());
            PageSet<MIn> pageSet = mInService.getReturnQryList(pageParam, filterSort, date1Qry, date2Qry, userDeptIds, typeQry, matQry);
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
            String date1Qry = XDate.dateTo8(request.getParameter("date1Qry"));
            String date2Qry = XDate.dateTo8(request.getParameter("date2Qry"));
            String matQry = request.getParameter("matQry");
            String typeQry = request.getParameter("typeQry");
            String userDeptIds = sysUserQryOrgService.getUserDeptId(LoginInfo.getUuid());

            List<MIn> list = mInService.getRetQryList(date1Qry, date2Qry, userDeptIds, typeQry, matQry);

            String textName = "申请状态,回收类型,仓库,物料状态,物料编码,物料描述,单位,单价,回收数,申请日期,回收单位,回收地点,业务科室,接收单位,记录号,备注";
            String fieldName = "inStepName,inTypeName,storeName,planSrcName,matCode,matName,matUnit,setPrice,billAmount,applyDate,teamName,reserve2,offerTeamName,offerName,billNo,remark";
            PoiUtils.exportExcelOld(response, "回收情况", textName, fieldName, list);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @ResponseBody
    @RequestMapping(value = "/back")
    public Object backReturn() {
        try {
            mInService.backReturn(request, LoginInfo.get());
            return success("退回成功!");
        }catch (XException e){
            return failure(e.getInfo());
        }catch (Exception e){
            e.printStackTrace();
            return failure("退回失败！");
        }
    }
}
