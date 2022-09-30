package cn.ewsd.cost.controller;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import cn.ewsd.base.bean.PageData;
import cn.ewsd.base.utils.DbUtil;
import cn.ewsd.base.utils.Snow;
import cn.ewsd.base.utils.jwt.LoginInfo;
import cn.ewsd.cost.model.FContract;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.stereotype.Controller;

import cn.ewsd.common.annotation.ControllerLog;
import cn.ewsd.common.controller.BaseController;
import cn.ewsd.common.utils.BaseUtils;
import cn.ewsd.common.utils.easyui.PageParam;
import cn.ewsd.common.utils.easyui.PageSet;

import cn.ewsd.cost.model.FAccount;
import cn.ewsd.cost.service.FAccountService;

/**
 * 工程结算
 *
 * @Author zxrmine
 * @Email zxrmine@163.cn
 * @Date 2021-09-03 08:39:36
 */
@Controller
@RequestMapping("/cost/fAccount")
public class FAccountController extends CostBaseController {

    @Autowired
    private FAccountService fAccountService;

    //分页页面
    @RequestMapping("/index")
    @ControllerLog(description = "打开FAccount模块管理页面")
    public String index(@RequestParam Map<String, Object> params) {
        return "cost/fAccount/index";
    }

    @RequestMapping("/indexSelect")
    @ControllerLog(description = "打开FAccount模块管理页面")
    public String indexSelect(@RequestParam Map<String, Object> params) {
        return "cost/fAccount/index_select";
    }

    //获取分页集
    @ResponseBody
    @RequestMapping(value = "/getPageSet", method = RequestMethod.POST)
    @ControllerLog(description = "获得FAccount分页集数据")
    public Object getPageSet(PageParam pageParam) {
        String filterSort = "";
        //页面前置参数
        if(request.getParameter("contractId")!=null&&!"".equals(request.getParameter("contractId"))&&!"undefined".equals(request.getParameter("contractId"))){
            filterSort += " and contract_id = '"+request.getParameter("contractId")+"'";
        }
        //筛选
        String query_accountMonth = request.getParameter("query_accountMonth");
        if(query_accountMonth!=null&&!"".equals(query_accountMonth)&&!"undefined".equals(query_accountMonth)){
            filterSort += " and account_month = '"+query_accountMonth+"'";
        }
        String query_creditTeam = request.getParameter("query_creditTeam");
        if(query_creditTeam!=null&&!"".equals(query_creditTeam)&&!"undefined".equals(query_creditTeam)){
            filterSort += " and credit_team = " + query_creditTeam;
        }
        String query_workTeam = request.getParameter("query_workTeam");
        if(query_workTeam!=null&&!"".equals(query_workTeam)&&!"undefined".equals(query_workTeam)){
            filterSort += " and work_team = " + query_workTeam;
        }

        filterSort = BaseUtils.filterSort(request, filterSort + getAuthFilter());
        PageSet<FAccount> pageSet = fAccountService.getPageSet(pageParam, filterSort);
        pageSet.setRows((List<FAccount>)covDataListDic(pageSet.getRows(),"workType#f.workType","fundType#f.fundType","accountType#f.accountType"));
        return pageSet;
    }

    //获取详情
    @ResponseBody
    @RequestMapping(value = "/getDetailByUuid")
    @ControllerLog(description = "获得FAccount模块详细数据")
    public Object getDetailByUuid(String uuid) {
        FAccount fAccount = fAccountService.queryObject(uuid);
        return fAccount;
    }

    //新增页面
    @RequestMapping("/add")
    @ControllerLog(description = "打开FAccount模块新增页面")
    public String add() {
        return "cost/fAccount/edit";
    }

    //保存数据
    @ResponseBody
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @ControllerLog(description = "保存FAccount模块数据")
    public Object save(@ModelAttribute FAccount fAccount) {
        fAccount.setAccountId(Snow.getUUID()+"");
        fAccount.setMngTeam(LoginInfo.getOrgId());
        fAccount.setSumPay(new BigDecimal(0));
        fAccount.setContractDiff(0+"");
        fAccount.setCheckDate("");
        fAccount.setCheckUser("");
        fAccount.setModiDate(sdfd.format(new Date()));
        fAccount.setModiUser(LoginInfo.getUserNameId());
        fAccount.setStatus(0+"");
        fAccount.setCheckNo(Snow.getUUID()+"");
        int result = fAccountService.insertSelective(getSaveData(fAccount));
        return result > 0 ? success("保存成功！") : failure("保存失败！");
    }

    //编辑页面
    @RequestMapping("/edit")
    @ControllerLog(description = "打开FAccount模块编辑页面")
    public String edit() {
        return "cost/fAccount/edit";
    }

    //更新数据
    @ResponseBody
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ControllerLog(description = "更新FAccount模块数据")
    public Object update(@ModelAttribute FAccount fAccount) {
        int result = fAccountService.updateByPrimaryKeySelective(getUpdateData(fAccount));
        return result > 0 ? success("更新成功！") : failure("更新失败！");
    }

    //删除数据
    @ResponseBody
    @RequestMapping(value = "/deleteBatch", method = RequestMethod.POST)
    @ControllerLog(description = "删除FAccount模块数据")
    public Object deleteBatch(@RequestParam String[] uuid) {
        int result = fAccountService.executeDeleteBatch(uuid);
        return result > 0 ? success("删除成功！") : failure("删除失败！");
    }

    @ResponseBody
    @RequestMapping(value = "/getWorkAddr")
    public Object getWorkAddr() throws Exception {
        String sql = "select work_addr from f_account group by work_addr order by create_time desc limit 50";
        List<PageData> workAddrList = DbUtil.executeQueryList(sql);
        return workAddrList;
    }

}
