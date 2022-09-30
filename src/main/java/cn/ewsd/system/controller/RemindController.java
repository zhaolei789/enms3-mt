package cn.ewsd.system.controller;

import cn.ewsd.common.utils.BaseUtils;
import cn.ewsd.common.utils.easyui.PageParam;
import cn.ewsd.common.utils.easyui.PageSet;
import cn.ewsd.system.model.Remind;
import cn.ewsd.system.service.RemindService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

@Controller
@RequestMapping("/system/remind")
public class RemindController extends SystemBaseController {

	@Resource
	private RemindService remindService;
	
	@RequestMapping("index")
	public String index(PageParam pageParam) throws Exception {
//		String hql = "FROM Remind" + Common.searchInfo(request);
//		request.setAttribute("pageSet",remindService.getPageSetByHql(request, hql));
//		return display();

		String filterSort = BaseUtils.filterSort(request);
		PageSet<Remind> pageSet = remindService.getPageSet(pageParam, filterSort);
		request.setAttribute("pageSet",pageSet);
		return display();
	}
	
	@RequestMapping("add")
	public String add() {
		return display();
	}
	
	@ResponseBody
	@RequestMapping(value="doAdd", method= RequestMethod.POST)
	public Object doAdd(Remind remind) {
		return remindService.insertSelective(remind);
	}
	
	@RequestMapping("edit")
	public String edit(String id) throws Exception {
		Remind info = remindService.selectByPrimaryKey(id);
		request.setAttribute("info", info);
		return display();
	}
	
	@ResponseBody
	@RequestMapping(value="doEdit", method= RequestMethod.POST)
	public Object doEdit(Remind remind) {
		return remindService.insertSelective(remind);
	}
	
	@ResponseBody
	@RequestMapping("delete")
	public Object delete(String id) {
		Remind remind = remindService.selectByPrimaryKey(id);
		return remindService.delete(remind);
	}
}
