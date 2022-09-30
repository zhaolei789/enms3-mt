package cn.ewsd.system.controller;

import cn.ewsd.common.annotation.ControllerLog;
import cn.ewsd.common.utils.BaseUtils;
import cn.ewsd.common.utils.JsonUtils;
import cn.ewsd.common.utils.easyui.PageParam;
import cn.ewsd.common.utils.easyui.PageSet;
import cn.ewsd.system.model.DicItem;
import cn.ewsd.system.model.DicSet;
import cn.ewsd.system.service.DicItemService;
import cn.ewsd.system.service.DicSetService;
import cn.ewsd.system.service.RedissonService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import net.sf.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Api(tags = {"数据字典接口"})
@Controller
@RequestMapping("/system/dicSet")
public class DicSetController extends SystemBaseController {

    @Resource
    private DicSetService dicSetService;

    @Resource
    private DicItemService dicItemService;

    @Autowired
    private RedissonService redissonService;

    @ApiOperation(value = "页面配置方法")
    @RequestMapping(value = "index", method = RequestMethod.GET)
    @ControllerLog(description = "打开DicSet模块数据列表")
    public String index() {
        return "system/dicSet/index";
    }

    @ApiOperation(value = "获取数据字典模块分页集方法")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "页码", defaultValue = "1", required = true, dataType = "Int"),
            @ApiImplicitParam(name = "rows", value = "一页显示多少条记录", defaultValue = "20", required = true, dataType = "Int"),
            @ApiImplicitParam(name = "sort", value = "排序", defaultValue = "createTime", required = true, dataType = "String"),
            @ApiImplicitParam(name = "order", value = "排序规则", defaultValue = "desc", required = true, dataType = "String")
    })
    @ResponseBody
    @RequestMapping(value = "getPageSet", method = RequestMethod.POST)
    public Object getPageSet(PageParam pageParam) throws Exception {
//        String filterStr = "AND isDel <> 1 ";
//        /*if(request.getParameter("codeItemId") != null) {
//            filterStr += "AND orgId = '"+ request.getParameter("codeItemId") +"'";
//		} else {
//			filterStr += "";
//		}*/
//
//        String hql = BaseUtils.filterSort(request, DicSet.class, filterStr);
//        if (request.getParameter("sort") == null) {
//            hql = hql.replace(" ORDER BY createTime DESC ", "ORDER BY sort ASC, createTime DESC");
//        }
//        PageSet<DicSet> pageSet = dicSetService.getPageSetByHql(request, hql);
//        //PageSet<DicSet> pageSet = dicSetService.getPageSetByHql(request, "FROM DicSet ORDER BY sort DESC");
//        return pageSet;
        //String filterSort ="is_del != 1 ORDER BY sort ASC, create_time DESC";
        String filterSort ="is_del != 1 and ";
        filterSort += BaseUtils.filterSort(request, getAuthFilter());
        if (request.getParameter("sort") == null) {
            filterSort = filterSort.replace("ORDER BY create_time DESC", "ORDER BY sort ASC, create_time DESC");
        }
        PageSet<DicSet> pageSet = dicSetService.getPageSet(pageParam, filterSort);
//        List list = dicSetService.selectAll();
        return pageSet;
    }

    @ApiOperation(value = "根据uuid获取数据字典模块所有数据方法")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "uuid", value = "字典uuid", defaultValue = "", required = true, dataType = "String"),
    })
    @ResponseBody
    @RequestMapping(value = "getDetailByUuid", method = RequestMethod.GET)
    public Object getDetailByUuid() throws Exception {
        String uuid = request.getParameter("uuid");
        DicSet info = dicSetService.selectByPrimaryKey(uuid);
        return info;
    }

    @RequestMapping("add")
    public String add() {
        return display();
    }

    @ApiOperation(value = "保存数据字典模块数据方法")
    @ResponseBody
    @RequestMapping(value = "save", method = RequestMethod.POST)
    public Object save(DicSet dicSet) throws Exception {
        // String hql = "FROM DicSet WHERE code = '" + dicSet.getCode() + "'";
        List<DicSet> list = dicSetService.getListByCodes(dicSet.getCode());
        if (list.size() == 0) {
            dicSetService.insertSelective(getSaveData(dicSet));
            redissonService.getRBucket("sysDicItem").delete();
            return JsonUtils.messageJson(200, "操作提示", "新增成功");
        }
        return JsonUtils.messageJson(300, "操作提示", "新增失败，字典集代码重复");
    }

    @ApiOperation(value = "打开编辑字典模块数据方法")
    @RequestMapping(value = "edit",method =RequestMethod.GET )
    public String edit() throws Exception {
        return display();
    }

    @ApiOperation(value = "更新数据字典模块数据方法")
    @ResponseBody
    @RequestMapping(value = "update", method = RequestMethod.POST)
    public Integer update(DicSet dicSet) throws Exception {
        DicSet oriData = dicSetService.selectByPrimaryKey(request.getParameter("uuid"));
        redissonService.getRBucket("sysDicItem").delete();
        return dicSetService.updateByPrimaryKey(getUpdateData(dicSet));
    }

    @ApiOperation(value = "删除数据字典模块数据方法", notes = "根据url的uuid来指定删除对象")
    @ApiImplicitParam(name = "uuid", value = "数据字典ID", required = true, dataType = "String")
    @ResponseBody
    @RequestMapping(value = "delete", method = RequestMethod.POST)
    public Object delete(String[] uuid) throws Exception {
        Integer result = 0;
        for (int i = 0; i < uuid.length; i++) {
            //  String hql = "UPDATE DicSet SET isDel = 1 WHERE uuid = '"+uuid[i]+"'";
            result = dicSetService.updateIsDel(1, uuid[i]);
            result++;
        }
        redissonService.getRBucket("sysDicItem").delete();
        return result > 0 ? success("删除成功") : failure("删除失败");
    }

    @ApiOperation(value = "根据Code获取字典项数据")
    @ApiImplicitParam(name = "code",value = "代码",required = true,dataType = "String")
    @ResponseBody
    @RequestMapping(value = "getDicItemByCode", method = RequestMethod.POST)
    public Object getDicItemByCode(String code) {
        //"FROM DicSet WHERE code = '" + code + "'"
        DicSet dicSet = dicSetService.getListByCode(code);
        List<DicItem> dicItems = new ArrayList<>();
        if (dicSet == null) {
            //"FROM DicSet WHERE code = '" + code + "'"
            dicSet = dicSetService.getListByCode(code);
        }
        // String hql = "FROM DicItem WHERE puuid = '" + dicSet.getUuid() + "' ORDER BY sort ASC";
        dicItems = dicItemService.getListByUuid(dicSet.getUuid());
        return dicItems;
    }

    @ApiOperation(value = "获取数据字典表单项" ,notes="根据代码获取数据字典表单项")
    @ApiImplicitParam(name = "code",value = "代码",required = true,dataType = "String")
    @ResponseBody
    @RequestMapping(value = "getDicItemFromCacheByCode", method = RequestMethod.POST)
    public Object getDicItemFromCacheByCode(String code) throws Exception {
        Object dicItemJson = dicSetService.getDicItem(code + "DicSet");
        JSONArray jsonArray = JSONArray.fromObject(dicItemJson);
        return jsonArray;
    }

}
