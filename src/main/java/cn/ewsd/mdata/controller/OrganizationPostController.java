package cn.ewsd.mdata.controller;

import cn.ewsd.common.utils.BaseUtils;
import cn.ewsd.common.utils.easyui.PageParam;
import cn.ewsd.common.utils.easyui.PageSet;
import cn.ewsd.mdata.model.OrganizationPost;
import cn.ewsd.mdata.service.OrganizationPostService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;


@Controller
@RequestMapping("/mdata/ucenter/organizationPost")
public class OrganizationPostController extends MdataBaseController {

    @Resource
    private OrganizationPostService organizationPostService;


    @ResponseBody
    @RequestMapping(value = "getPageSet")
    public Object getPageSet(PageParam pageParam,String uuid) throws Exception {
        String filterSort = " orgUuid = '" + uuid + "'"+BaseUtils.filterSort(request);
        PageSet<OrganizationPost> pageSet = organizationPostService.getPageSet(pageParam, filterSort);
        return pageSet;
    }

    @RequestMapping("edit")
    public String edit() throws Exception {
        return display();
    }

    @ResponseBody
    @RequestMapping(value = "getDetailByUuid")
    public Object getDetailByUuid(String uuid) throws Exception {
        return organizationPostService.selectByPrimaryKey(uuid);
    }

    @ResponseBody
    @RequestMapping(value = "save", method = RequestMethod.POST)
    public Integer save(OrganizationPost organizationPost) throws Exception {
        return organizationPostService.insertSelective(getSaveData(organizationPost));
    }

    @ResponseBody
    @RequestMapping(value = "update", method = RequestMethod.POST)
    public Integer update(OrganizationPost organizationPost) throws Exception {
        OrganizationPost oriData = organizationPostService.selectByPrimaryKey(request.getParameter("uuid"));
        return organizationPostService.updateByPrimaryKeySelective(getUpdateData(organizationPost));
    }

    @ResponseBody
    @RequestMapping(value = "delete", method = RequestMethod.POST)
    public Object delete(String[] uuid) throws Exception {
        Integer result = 0;
        for (int i = 0; i < uuid.length; i++) {
            result = organizationPostService.deleteByPrimaryKey(uuid[i]);
            result++;
        }
        return result > 0 ? success("删除成功") : failure("删除失败");
    }

}
