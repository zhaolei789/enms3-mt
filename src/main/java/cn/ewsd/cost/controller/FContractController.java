package cn.ewsd.cost.controller;

import java.util.Date;
import java.util.List;
import java.util.Map;

import cn.ewsd.base.bean.PageData;
import cn.ewsd.base.utils.DbUtil;
import cn.ewsd.base.utils.PingYinUtil;
import cn.ewsd.base.utils.Snow;
import cn.ewsd.base.utils.jwt.LoginInfo;
import cn.ewsd.cost.model.FAward;
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

import cn.ewsd.cost.model.FContract;
import cn.ewsd.cost.service.FContractService;

/**
 * 合同
 *
 * @Author zxrmine
 * @Email zxrmine@163.cn
 * @Date 2021-09-03 08:39:34
 */
@Controller
@RequestMapping("/cost/fContract")
public class FContractController extends CostBaseController {

    @Autowired
    private FContractService fContractService;

    //分页页面
    @RequestMapping("/index")
    @ControllerLog(description = "打开FContract模块管理页面")
    public String index(@RequestParam Map<String, Object> params) {
        return "cost/fContract/index";
    }

    //获取分页集
    @ResponseBody
    @RequestMapping(value = "/getPageSet", method = RequestMethod.POST)
    @ControllerLog(description = "获得FContract分页集数据")
    public Object getPageSet(PageParam pageParam) {
        String filterSort = "";
        //筛选条件
        String query_contractName = request.getParameter("query_contractName");
        if(query_contractName!=null&&!"".equals(query_contractName)&&!"undefined".equals(query_contractName)){
            filterSort += " and (f.contract_name LIKE concat(concat('%','" + query_contractName + "'),'%') " +
                    "or" +
                    " f.contract_name_jp LIKE concat(concat('%','" + query_contractName + "'),'%'))";
        }
        String query_buyType = request.getParameter("query_buyType");
        if(query_buyType!=null&&!"".equals(query_buyType)&&!"undefined".equals(query_buyType)){
            filterSort += " and f.buy_type = '"+query_buyType+"'";
        }
        String query_bidType = request.getParameter("query_bidType");
        if(query_bidType!=null&&!"".equals(query_bidType)&&!"undefined".equals(query_bidType)){
            filterSort += " and f.bid_type = '"+query_bidType+"'";
        }
        //筛选条件
        filterSort = BaseUtils.filterSort(request, filterSort + getAuthFilter());
        PageSet<FContract> pageSet = fContractService.getPageSet(pageParam, filterSort.replace("ORDER BY create_time","ORDER BY f.create_time"));
        pageSet.setRows((List<FContract>)covDataListDic(pageSet.getRows(),"bidType#f.bidType","buyType#f.buyType"));
        return pageSet;
    }

    //获取详情
    @ResponseBody
    @RequestMapping(value = "/getDetailByUuid")
    @ControllerLog(description = "获得FContract模块详细数据")
    public Object getDetailByUuid(String uuid) {
        FContract fContract = fContractService.queryObject(uuid);
        return fContract;
    }

    //新增页面
    @RequestMapping("/add")
    @ControllerLog(description = "打开FContract模块新增页面")
    public String add() {
        return "cost/fContract/edit";
    }

    //保存数据
    @ResponseBody
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @ControllerLog(description = "保存FContract模块数据")
    public Object save(@ModelAttribute FContract fContract) {
        fContract.setContractId(Snow.getUUID()+"");
        //简拼
        fContract.setContractNameJp(PingYinUtil.getFirstSpell(fContract.getContractName()));
        fContract.setLinkId("");
        fContract.setModiInfo("");
        fContract.setTeamNo(LoginInfo.getOrgId());
        fContract.setModiDate(sdfd.format(new Date()));
        int result = fContractService.insertSelective(getSaveData(fContract));
        return result > 0 ? success("保存成功！") : failure("保存失败！");
    }

    //编辑页面
    @RequestMapping("/edit")
    @ControllerLog(description = "打开FContract模块编辑页面")
    public String edit() {
        return "cost/fContract/edit";
    }

    //更新数据
    @ResponseBody
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ControllerLog(description = "更新FContract模块数据")
    public Object update(@ModelAttribute FContract fContract) throws Exception {
        fContract.setContractNameJp(PingYinUtil.getFirstSpell(fContract.getContractName()));

        String sql = "select count(uuid) as num from f_account where contract_id = '"+fContract.getUuid()+"'";
        PageData checkNum = DbUtil.executeQueryObject(sql);
        if(checkNum!=null&&checkNum.get("num")!=null) {
            if (Integer.parseInt(checkNum.get("num").toString()) > 0) {
                return failure("已有上报的合同数据,不能变更!");
            }
        }

        int result = fContractService.updateByPrimaryKeySelective(getUpdateData(fContract));
        return result > 0 ? success("更新成功！") : failure("更新失败！");
    }

    //删除数据
    @ResponseBody
    @RequestMapping(value = "/deleteBatch", method = RequestMethod.POST)
    @ControllerLog(description = "删除FContract模块数据")
    public Object deleteBatch(@RequestParam String[] uuid) throws Exception {
        int result = 0;
        int result_fail = 0;
        String errmsg2 = "";
        for (int i = 0; i < uuid.length; i++) {
            String sql = "select count(uuid) as num from f_account where contract_id = '"+uuid[i]+"'";
            PageData checkNum = DbUtil.executeQueryObject(sql);
            if(checkNum!=null&&checkNum.get("num")!=null){
                if(Integer.parseInt(checkNum.get("num").toString())>0){
                    result_fail ++;
                    errmsg2 = ",已有上报的合同数据,不能删除";
                    continue;
                }
                result += fContractService.executeDeleteBatch(new String[] {uuid[i]});
            }
        }
        String errmsg = result_fail>0 ? ",删除失败"+result_fail+"条!":"";
        return result > 0 ? success("删除成功"+result+"条"+errmsg+errmsg2) : failure("删除失败！"+errmsg2);
    }

}
