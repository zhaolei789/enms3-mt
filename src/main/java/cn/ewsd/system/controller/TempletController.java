package cn.ewsd.system.controller;

import cn.ewsd.common.annotation.ControllerLog;
import cn.ewsd.common.utils.BaseUtils;
import cn.ewsd.common.utils.easyui.PageParam;
import cn.ewsd.common.utils.easyui.PageSet;
import cn.ewsd.system.model.Templet;
import cn.ewsd.system.service.TempletService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;


@Api(tags = {"报表管理接口"})
@Controller
@RequestMapping("/system/templet")
public class TempletController extends SystemBaseController {

    @Autowired
    private TempletService templetService;

    @ApiOperation(value = "打开报表模板分页界面")
    @RequestMapping(value = "/index", method = RequestMethod.GET)
    @ControllerLog(description = "报表模板分页集界面")
    public String index() {
        return "report/templet/index";
    }

    @ResponseBody
    @RequestMapping(value = "/getPageSet", method = RequestMethod.POST)
    @ApiOperation(value = "打开报表模板分页集界面")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "页码", defaultValue = "1", required = true, dataType = "Int"),
            @ApiImplicitParam(name = "rows", value = "一页显示多少条记录", defaultValue = "20", required = true, dataType = "Int"),
            @ApiImplicitParam(name = "sort", value = "排序", defaultValue = "createTime", required = true, dataType = "String"),
            @ApiImplicitParam(name = "order", value = "排序规则", defaultValue = "desc", required = true, dataType = "String")
    })
    public Object getPageSet(PageParam pageParam) throws Exception {
        String filterStr = BaseUtils.filterSort(request).replace("ORDER BY create_time DESC", "");
        PageSet<Templet> pageSet = templetService.getPageSet(pageParam, filterStr);
        return pageSet;
    }

    @ResponseBody
    @RequestMapping(value = "/getDetailByUuid", method = RequestMethod.GET)
    @ApiOperation(value = "获取报表数据详情")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "uuid", value = "标识", required = true, dataType = "String")
    })
    public Object getDetailByUuid(String uuid) {
        Templet templet = templetService.selectByPrimaryKey(uuid);
        return templet;
    }

}