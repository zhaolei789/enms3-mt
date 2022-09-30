package cn.ewsd.system.controller;

import cn.ewsd.base.utils.DatasourceUtils;
import cn.ewsd.common.utils.BaseUtils;
import cn.ewsd.common.utils.easyui.PageParam;
import cn.ewsd.common.utils.easyui.PageUtils;
import cn.ewsd.system.model.CodeTable;
import cn.ewsd.system.service.GeneratorService;

import com.alibaba.fastjson.JSONArray;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 代码生成器
 */
@Api(tags = {"代码生成接口"})
@Controller("generatorIndexController")
@RequestMapping("/system/generator")
public class GeneratorController extends SystemBaseController {

    @Autowired
    public HttpServletRequest request;

    @Autowired
    private GeneratorService generatorService;

    @ApiOperation(value = "列表页面配置方法")
    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public String index() {
        return "system/generator/index";
    }

    @ApiOperation(value = "主子表")
    @RequestMapping(value = "/mainSubtableIndex", method = RequestMethod.GET)
    public String mainSubtableIndex() {
        return "system/generator/mainSubtableIndex";
    }

    @RequestMapping(value = "/codeGeneration", method = RequestMethod.GET)
    public String codeGeneration() {
        return "system/generator/codeGeneration";
    }

    @RequestMapping(value = "/codeGenerationAdd", method = RequestMethod.GET)
    public String codeGenerationAdd() {
        return "system/generator/codeGenerationAdd";
    }

    /**
     * 页面
     */
    @ApiOperation(value = "打开代码生成页面")
    @RequestMapping(value = "/page",method = RequestMethod.GET)
    public String page() {
        return "sys/generatorlist";
    }

    /**
     * 列表
     */
    @ApiOperation(value = "获取分页集方法")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "页码", defaultValue = "1", required = true, dataType = "Int"),
            @ApiImplicitParam(name = "rows", value = "一页显示多少条记录", defaultValue = "20", required = true, dataType = "Int"),
            @ApiImplicitParam(name = "sort", value = "排序", defaultValue = "createTime", required = true, dataType = "String"),
            @ApiImplicitParam(name = "order", value = "排序规则", defaultValue = "desc", required = true, dataType = "String"),
            @ApiImplicitParam(name = "q", value = "表名模糊查询", defaultValue = "", required = false, dataType = "String"),
    })
    @RequestMapping(value = "/getPageSet", method = RequestMethod.POST)
    @ResponseBody
    public Object getPageSet(PageParam pageParam, String q) {

        String datasourceType = DatasourceUtils.getDatasourceTypeByDriverClassName(driverClassName);

        HashMap<String, Object> hashMap = new HashMap<>();

        PageHelper.startPage(pageParam.getPage(), pageParam.getRows());
        List<HashMap<String, Object>> list = new ArrayList<>();
        if (datasourceType.equals("mysql")) {
            String filterSort = BaseUtils.filterSort(request, " and table_schema = (select database())");
            filterSort = filterSort.replace("ORDER BY create_time DESC", "ORDER BY tableName ASC");
            list = generatorService.getPageSet(filterSort);
        }
        if (datasourceType.equals("sqlserver")) {
            String filterSort = BaseUtils.filterSort(request);
            list = generatorService.getPageSetBySqlserver(filterSort);
        }
        //if (datasourceType.equals("oracle")) {
        //list = generatorService.getPageSetByOracle(hashMap); }

        PageInfo<HashMap<String, Object>> pageInfo = new PageInfo<>(list);
        return PageUtils.getPageSet(pageInfo);

//        String filterSort = BaseUtils.filterSort(request);
//        PageSet<CarUse> pageSet = iCrUseService.getPageSet(pageParam, filterSort);
//        return pageSet;
//       String simpleFilter = request.getParameter("filterRules");
//        JSONArray jsonArray = JSONArray.parseArray(simpleFilter);
//        String fieldName="";
//        for(int i = 0; i < jsonArray.size(); i++){
//            JSONObject jsonObject = (JSONObject)jsonArray.get(i);
//            fieldName= jsonObject.getString("value");
//        }
//        List<HashMap<String, Object>> list1 = generatorService.getPageSet(hashMap,fieldName);
//        PageSet<HashMap<String, Object>> pageSet =new PageSet<>();
//        return pageSet;

    }


    @ApiOperation(value = "获取所有表")
    @RequestMapping(value = "/getAll", method = RequestMethod.POST)
    @ResponseBody
    public Object getAll(String q) {
        String datasourceType = DatasourceUtils.getDatasourceTypeByDriverClassName(driverClassName);
        HashMap<String, Object> hashMap = new HashMap<>();
        List<HashMap<String, Object>> list = new ArrayList<>();
        if (datasourceType.equals("mysql")) {
            String filterSort = BaseUtils.filterSort(request, " and table_schema = (select database())");
            filterSort = filterSort.replace("ORDER BY create_time DESC", "ORDER BY tableName ASC");
            list = generatorService.getAll(filterSort);
        }
        return list;


    }

    @ApiOperation(value = "根据表明获取表的所有字段")
    @RequestMapping(value = "/getColumns", method = RequestMethod.POST)
    @ResponseBody
    public Object getColumns(String tableName) {
        List<Map<String, String>> columns =generatorService.queryColumns(tableName);
        return columns;
    }


    /**
     * 生成代码
     */
    @ApiOperation(value = "生成代码方法")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "tables", value = "表名", defaultValue = "", required = true, dataType = "String",allowMultiple = true)
    })
    @RequestMapping(value = "/code", method = RequestMethod.GET)
    public void code(@RequestParam String[] tables, HttpServletRequest request, HttpServletResponse response) throws IOException {

        String datasourceType = DatasourceUtils.getDatasourceTypeByDriverClassName(driverClassName);
        byte[] data = null;
        if (datasourceType.equals("mysql")) {
            data = generatorService.generatorCode(tables);
        }
        if (datasourceType.equals("sqlserver")) {
            //获取表名，不进行xss过滤
            data = generatorService.generatorCodeBySqlserver(tables);
        }

        response.reset();
        response.setHeader("Content-Disposition", "attachment; filename=\"sourcecode.zip\"");
        response.addHeader("Content-Length", "" + data.length);
        response.setContentType("application/octet-stream; charset=UTF-8");

        IOUtils.write(data, response.getOutputStream());
    }

    @ApiOperation(value = "多表生成代码方法")
    @RequestMapping(value = "/codeMultTable")
    public void codeMultTable(CodeTable codeTable, HttpServletResponse response) throws IOException {
        byte[] data = generatorService.codeMultTable(codeTable);
        response.reset();
        response.setContentType("multipart/form-data");
        response.setHeader("Content-Disposition", "attachment; filename=\"sourcecode.zip\"");
        response.addHeader("Content-Length", "" + data.length);
        response.setContentType("application/octet-stream; charset=UTF-8");
        IOUtils.write(data, response.getOutputStream());
    }

    /**
     * @param code  1 成功  0 失败
     * @param msg   消息内容
     * @param count 最大条数
     * @param data  具体内容
     * @return
     */
    public Object putMsgToJsonString(int code, String msg, int count, Object data) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("code", code);
        map.put("msg", msg);
        map.put("count", count);
        map.put("data", data);
        return JSONArray.toJSON(map);
    }
}
