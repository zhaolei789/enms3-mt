package cn.ewsd.system.controller;

import cn.ewsd.common.utils.BaseUtils;
import cn.ewsd.common.utils.easyui.PageParam;
import cn.ewsd.common.utils.easyui.PageSet;
import cn.ewsd.system.model.Rule;
import cn.ewsd.system.service.RuleService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

@Controller
@RequestMapping("/system/authRule")
public class AuthRuleController extends SystemBaseController {

	@Resource
	private RuleService ruleService;
	
	@RequestMapping("index")
	public String index(PageParam pageParam) throws Exception {
//		String hql = "FROM Rule" + Common.searchInfo(request);
//		request.setAttribute("pageSet",ruleService.getPageSetByHql(request, hql));
//		return display();

		String filterSort = BaseUtils.filterSort(request);
		PageSet<Rule> pageSet = ruleService.getPageSet(pageParam, filterSort);
		request.setAttribute("pageSet",pageSet);
		return display();
	}
	
	@ResponseBody
	@RequestMapping(value="getAllList")
	public Object getAllList(PageParam pageParam) throws Exception {
		String hql = null;
		String filterStr = "";
		/*if(request.getParameter("codeItemId") != null) {
			filterStr = "AND orgId = '"+ request.getParameter("codeItemId") +"'";
		} else {
			filterStr = "";
		}*/
		
		//hql = "FROM Rule WHERE " + BaseUtils.filter(request) + filterStr +" ORDER BY createTime DESC";
		String filterSort = BaseUtils.filterSort(request);
		PageSet<Rule> pageSet = ruleService.getPageSet(pageParam, filterSort);
        //PageSet<Rule> pageSet = ruleService.getPageSetByHql(request, "FROM Rule ORDER BY createTime DESC");
        //Map<String, Object> jsonMap = new HashMap<String, Object>();
        return pageSet;
        //jsonMap.put("total", pageSet.getAllRow());
        //jsonMap.put("rows", pageSet.getList());
		//return JSON.toJSONString(jsonMap);
	}
		
	@ResponseBody
	@RequestMapping("getDetailByUuid")
	public Object getDetailByUuid() throws Exception {
		String uuid = request.getParameter("uuid");
		Rule info = ruleService.selectByPrimaryKey(uuid);
		return info;
	}
	
	@RequestMapping("add")
	public String add() {
		return display();
	}
	
	@ResponseBody
	@RequestMapping(value="save", method= RequestMethod.POST)
	public Integer save(Rule rule) throws Exception {
		//group.setIsDel(0);
		//group.setCreator(common.getSession(request, Constants.USERNAME));
		//group.setCreateTime(new Date());
		//return ruleService.save(group);
		return ruleService.insertSelective(getSaveData(rule));
	}
	
	@RequestMapping("edit")
	public String edit() throws Exception {
		return display();
	}
	
	@ResponseBody
	@RequestMapping(value="update", method= RequestMethod.POST)
	public Integer update(Rule rule) throws Exception {
		return ruleService.updateByPrimaryKey(getUpdateData(rule));
	}
	
	@ResponseBody
	@RequestMapping(value="delete", method= RequestMethod.POST)
	public Integer delete() throws Exception{
		String uuid = request.getParameter("uuid");
//		String hql = "DELETE FROM Rule WHERE uuid IN ("+ uuid +")";
		return ruleService.deleteByPrimaryKey(uuid);
	}
}
