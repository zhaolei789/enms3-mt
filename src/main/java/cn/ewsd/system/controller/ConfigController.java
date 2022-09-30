package cn.ewsd.system.controller;

import cn.ewsd.common.annotation.ControllerLog;
import cn.ewsd.common.utils.*;
import cn.ewsd.common.utils.easyui.PageParam;
import cn.ewsd.common.utils.easyui.PageSet;
import cn.ewsd.system.model.Config;
import cn.ewsd.system.service.ChannelService;
import cn.ewsd.system.service.ConfigService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.ServletContext;

@Api(tags = {"基础配置接口"})
@SuppressWarnings({"rawtypes", "unchecked"})
@Controller("configController")
@RequestMapping("/system/config")
public class ConfigController extends SystemBaseController {

    @Resource
    public ConfigService configService;

    @Resource
    public ChannelService channelService;

    @Autowired
    private ServletContext servletContext;

    @ApiOperation(value = "列表页面配置方法")
    @RequestMapping(value = "index", method = RequestMethod.GET)
    @ControllerLog(description = "打开Config模块数据列表")
    public String index() {
        return "system/config/index";
    }

    @ApiOperation(value = "获取基础配置分页数据方法")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "页码", defaultValue = "1", required = true, dataType = "Int"),
            @ApiImplicitParam(name = "rows", value = "一页显示多少条记录", defaultValue = "20", required = true, dataType = "Int"),
            @ApiImplicitParam(name = "sort", value = "排序", defaultValue = "createTime", required = true, dataType = "String"),
            @ApiImplicitParam(name = "order", value = "排序规则", defaultValue = "desc", required = true, dataType = "String"),
            @ApiImplicitParam(name = "code", value = "代码", defaultValue = "", required = true, dataType = "String")
    })
    @ResponseBody
    @RequestMapping(value = "getPageSetData", method = RequestMethod.POST)
    @ControllerLog(description = "获取Config模块分页数据")
    public Object getPageSetData(PageParam pageParam, String code) throws Exception {
        String filterStr = BaseUtils.filterSort(request, getAuthFilter()).replace("create_time DESC", "sort ASC");
        PageSet<Config> pageSet = configService.getPageSet(pageParam, filterStr);
        return pageSet;
    }

    @ApiOperation(value = "基础配置新增界面方法")
    @RequestMapping(value = "add", method = RequestMethod.GET)
    @ControllerLog(description = "打开Config模块新增界面")
    public String add() {
        return "system/config/edit";
    }

    @ApiOperation(value = "基础配置编辑界面方法")
    @RequestMapping(value = "edit", method = RequestMethod.GET)
    @ControllerLog(description = "打开Config模块编辑界面")
    public String edit() {
        return "system/config/edit";
    }

    @ApiOperation(value = "获取基础配置详细数据方法")
    @ApiImplicitParam(name = "uuid", value = "标识", defaultValue = "", required = true, dataType = "String")
    @ResponseBody
    @RequestMapping(value = "getDetailByUuid", method = RequestMethod.GET)
    @ControllerLog(description = "获取Config模块详细数据")
    public Object getDetailByUuid(String uuid) throws Exception {
        Config entity = configService.selectByPrimaryKey(uuid);
        return entity;
    }

    @ApiOperation(value = "保存基础配置数据方法")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "code", value = "代码", defaultValue = "", required = false, dataType = "String"),
            @ApiImplicitParam(name = "type", value = "类型", defaultValue = "", required = false, dataType = "String"),
            @ApiImplicitParam(name = "name", value = "名称", defaultValue = "", required = false, dataType = "String"),
            @ApiImplicitParam(name = "value", value = "值", defaultValue = "", required = false, dataType = "String"),
            @ApiImplicitParam(name = "sort", value = "排序", defaultValue = "", required = true, dataType = "Int"),
            @ApiImplicitParam(name = "description", value = "描述", defaultValue = "", required = false, dataType = "String"),
            @ApiImplicitParam(name = "createTime", value = "创建时间", defaultValue = "", required = false, dataType = "Date"),
            @ApiImplicitParam(name = "creator", value = "创建人", defaultValue = "", required = false, dataType = "String"),
            @ApiImplicitParam(name = "creatorId", value = "创建人ID", defaultValue = "", required = false, dataType = "String"),
            @ApiImplicitParam(name = "creatorOrgId", value = "组织机构ID", defaultValue = "", required = false, dataType = "Int"),
            @ApiImplicitParam(name = "modifier", value = "修改人", defaultValue = "", required = false, dataType = "String"),
            @ApiImplicitParam(name = "modifierId", value = "修改人ID", defaultValue = "", required = false, dataType = "String"),
            @ApiImplicitParam(name = "modify_flag", value = "修改人旗帜", defaultValue = "1", required = false, dataType = "String"),
            @ApiImplicitParam(name = "modifyTime", value = "修改人时间", defaultValue = "", required = false, dataType = "Date"),
            @ApiImplicitParam(name = "uuid", value = "标识", defaultValue = "", required = true, dataType = "String")
    })
    @ResponseBody
    @RequestMapping(value = "save", method = RequestMethod.POST)
    @ControllerLog(description = "保存Config模块数据")
    @CacheEvict(cacheNames = "configList", allEntries = true)
    public Object save(Config config) throws Exception {
        String config1 = configService.getConfigByCode(config.getCode());
        if (config1 == null) {
            config.setType("");
            if (IntegerUtils.isNull(config.getModifyFlag()))
                config.setModifyFlag(1);
            if (IntegerUtils.isNull(config.getSort()))
                config.setSort(configService.getMaxSort() + 1);
            Integer result = configService.insertSelective(getSaveData(config));
            configService.cacheConfig();
            return result > 0 ? success("保存成功！") : failure("保存失败！");
        }
        return failure("更新失败，代码重复！");
    }

    @ApiOperation(value = "更新基础配置模块数据方法")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "code", value = "代码", defaultValue = "", required = true, dataType = "String"),
            @ApiImplicitParam(name = "type", value = "类型", defaultValue = "", required = true, dataType = "String"),
            @ApiImplicitParam(name = "name", value = "名称", defaultValue = "", required = true, dataType = "String"),
            @ApiImplicitParam(name = "value", value = "值", defaultValue = "", required = true, dataType = "String"),
            @ApiImplicitParam(name = "sort", value = "排序", defaultValue = "", required = false, dataType = "Int"),
            @ApiImplicitParam(name = "description", value = "描述", defaultValue = "", required = false, dataType = "String"),
            @ApiImplicitParam(name = "createTime", value = "创建时间", defaultValue = "", required = false, dataType = "Date"),
            @ApiImplicitParam(name = "creator", value = "创建人", defaultValue = "", required = false, dataType = "String"),
            @ApiImplicitParam(name = "creatorId", value = "创建人ID", defaultValue = "", required = false, dataType = "String"),
            @ApiImplicitParam(name = "creatorOrgId", value = "组织机构ID", defaultValue = "", required = false, dataType = "String"),
            @ApiImplicitParam(name = "modifier", value = "修改人", defaultValue = "", required = false, dataType = "String"),
            @ApiImplicitParam(name = "modifierId", value = "修改人ID", defaultValue = "", required = false, dataType = "String"),
            @ApiImplicitParam(name = "modifyTime", value = "修改人时间", defaultValue = "", required = false, dataType = "Date"),
            @ApiImplicitParam(name = "uuid", value = "标识", defaultValue = "", required = true, dataType = "String")
    })
    @ResponseBody
    @RequestMapping(value = "update", method = RequestMethod.POST)
    @ControllerLog(description = "更新基础配置模块数据")
    @CacheEvict(cacheNames = "configList", allEntries = true)
    public Object update(Config config) throws Exception {
        Config config1 = configService.getListByCodeAndUuid(config.getCode(), config.getUuid());
        if (config1 == null) {
            Integer result = configService.updateByPrimaryKey(getUpdateData(config));
            //configService.cacheConfig();
            return result;
        }
        return failure("更新失败，代码重复！");
    }

    @ApiOperation(value = "删除基础配置模块数据方法")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "uuid", value = "标识", defaultValue = "", required = false, dataType = "String"),
    })
    @ResponseBody
    @RequestMapping(value = "delete", method = RequestMethod.POST)
    @ControllerLog(description = "删除Config模块数据")
    @CacheEvict(cacheNames = "configList", allEntries = true)
    public Object delete(String uuid) throws Exception {
        uuid = uuid.replace("'", "");
        String[] uuidArr = uuid.split(","); //保存uuid的数组
        int result = 0; // 保存结果值

        for (int i = 0; i < uuidArr.length; i++) {
            uuid = uuidArr[i];
            Config config = configService.selectByPrimaryKey(uuid);
            if (!StringUtils.isNullOrEmpty(config.getType())) {
                if (config.getType().equals("system")) {
                    if (uuidArr.length == 1) {
                        return JsonUtils.messageJson(300, "操作提示", "该配置为系统预设配置，不可删除，强行删除将导致系统异常！");
                    }
                } else {
                    result = configService.deleteByPrimaryKey(uuid) > 0 ? result + 1 : result;
                }
            } else {
                result = configService.deleteByPrimaryKey(uuid) > 0 ? result + 1 : result;
            }

        }
        return result > 0 ? JsonUtils.messageJson(200, "操作提示", "删除成功！") : JsonUtils.messageJson(300, "操作提示", "删除失败，请重试！");
//        return JsonUtils.messageJson(300, "操作提示", "删除失败，请重试！");
    }

    @ApiOperation(value = "保存预设配置信息模块数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "uuid", value = "标识", defaultValue = "", required = false, dataType = "String"),
    })
    @ResponseBody
    @RequestMapping(value = "beforeOpen", method = RequestMethod.GET)
    @ControllerLog(description = "保存WeldMaterialStock模块数据")
    public Object beforeOpen(String uuid) throws Exception {
        Config config = configService.selectByPrimaryKey(uuid);
        if (config.getModifyFlag() == 0)
            return JsonUtils.messageJson(300, "操作提示", "该配置为系统预设配置，不可修改，强行修改将导致系统异常！");
        else
            return null;
    }

    @ApiOperation(value = "清理系统缓存方法")
    @RequestMapping(value = "cleanCache", method = RequestMethod.GET)
    @ControllerLog(description = "清理系统缓存")
    public String cleanCache() throws Exception {
        try {
            //设置初始化标志
            PropertiesUtil.setConfigValue("isInited", "false", "init.properties");
            String isInited = PropertiesUtil.getConfigValue("isInited", "init.properties");
            if ("false".equals(isInited) || "".equals(isInited)) {
                channelService.cacheChannel();
                configService.cacheConfig();

                // 重新设置初始化标志
                PropertiesUtil.setConfigValue("isInited", "true", "init.properties");
            }
            return "common/common/success";
//            return display("/common/common/success", "操作提示", "缓存清理成功!");
        } catch (Exception e) {
            return "common/common/failure";
//            return display("/common/common/failure", "操作提示", "缓存清理失败!");
        }
    }

}