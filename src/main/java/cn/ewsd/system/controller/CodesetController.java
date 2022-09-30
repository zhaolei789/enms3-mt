package cn.ewsd.system.controller;

import cn.ewsd.common.utils.BaseUtils;
import cn.ewsd.common.utils.easyui.PageParam;
import cn.ewsd.common.utils.easyui.PageSet;
import cn.ewsd.system.model.Codeset;
import cn.ewsd.system.service.CodesetService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

@Controller
@RequestMapping("/system/codeSet")
public class CodesetController extends SystemBaseController {

    @Resource
    private CodesetService codesetService;

    @RequestMapping("index")
    public String index() throws Exception {
        return display();
    }

    @ResponseBody
    @RequestMapping(value = "getAllList")
    public Object getAllList(PageParam pageParam) throws Exception {
        String filterSort = "";
        /*if(request.getParameter("codeItemId") != null) {
            filterStr = "AND orgId = '"+ request.getParameter("codeItemId") +"'";
		} else {
			filterStr = "";
		}*/

        filterSort = BaseUtils.filter(request, filterSort) + " ORDER BY sort ASC";
        PageSet<Codeset> pageSet = codesetService.getPageSet(pageParam, filterSort);
        //PageSet<Codeset> pageSet = codesetService.getPageSetByHql(request, "FROM Codeset ORDER BY sort DESC");
        return pageSet;
    }

    @ResponseBody
    @RequestMapping("getDetailByUuid")
    public Object getDetailByUuid() throws Exception {
        String uuid = request.getParameter("uuid");
        Codeset info = codesetService.selectByPrimaryKey(uuid);
        return info;
    }


    @RequestMapping("add")
    public String add() {
        return display();
    }

    @ResponseBody
    @RequestMapping(value = "save", method = RequestMethod.POST)
    public Integer save(Codeset codeset) throws Exception {
        return codesetService.insertSelective(getSaveData(codeset));
    }

    @RequestMapping("edit")
    public String edit() throws Exception {
        return display();
    }

    @ResponseBody
    @RequestMapping(value = "update", method = RequestMethod.POST)
    public Integer update(Codeset codeset) throws Exception {
        return codesetService.updateByPrimaryKey(getUpdateData(codeset));
    }

    @ResponseBody
    @RequestMapping(value = "delete", method = RequestMethod.POST)
    public Integer delete() throws Exception {
        String uuid = request.getParameter("uuid");
        // String hql = "DELETE FROM Codeset WHERE uuid IN (" + uuid + ")";
        return codesetService.deleteByPrimaryKey(uuid);
    }

}
