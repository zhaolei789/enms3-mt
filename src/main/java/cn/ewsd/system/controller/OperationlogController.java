package cn.ewsd.system.controller;

import cn.ewsd.common.utils.BaseUtils;
import cn.ewsd.common.utils.easyui.PageParam;
import cn.ewsd.common.utils.easyui.PageSet;
import cn.ewsd.system.model.Operationlog;
import cn.ewsd.system.service.OperationlogService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

@Controller
@RequestMapping("/system/operationlog")
public class OperationlogController extends SystemBaseController {

	@Resource
	private OperationlogService operationlogService;
	
	@RequestMapping("index")
	public String index(PageParam pageParam) throws Exception {
//		String hql = "FROM Operationlog" + Common.searchInfo(request);
//		request.setAttribute("pageSet",operationlogService.getPageSet(request, hql));
//		return display();

		String filterSort = BaseUtils.filterSort(request);
		PageSet<Operationlog> pageSet = operationlogService.getPageSet(pageParam, filterSort);
		request.setAttribute("pageSet",pageSet);
		return display();
	}
	
	@RequestMapping("add")
	public String add() {
		return display();
	}
	
	@ResponseBody
	@RequestMapping(value="doAdd", method= RequestMethod.POST)
	public Object doAdd(Operationlog operationlog) {
		return operationlogService.insertSelective(getSaveData(operationlog));
	}
	
	@RequestMapping("edit")
	public String edit(String id) throws Exception{
		Operationlog info = operationlogService.selectByPrimaryKey(id);
		request.setAttribute("info", info);
		return display();
	}
	
	@ResponseBody
	@RequestMapping(value="doEdit", method= RequestMethod.POST)
	public Object doEdit(Operationlog operationlog) {
		return operationlogService.insertSelective(getSaveData(operationlog));
	}
	
	@ResponseBody
	@RequestMapping("delete")
	public Object delete(String id) {
		Operationlog operationlog = operationlogService.selectByPrimaryKey(id);
		return operationlogService.delete(operationlog);
	}
}
