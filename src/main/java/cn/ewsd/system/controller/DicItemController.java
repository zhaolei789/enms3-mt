package cn.ewsd.system.controller;

import cn.ewsd.common.utils.BaseUtils;
import cn.ewsd.common.utils.easyui.PageParam;
import cn.ewsd.common.utils.easyui.PageSet;
import cn.ewsd.system.model.DicItem;
import cn.ewsd.system.model.DicSet;
import cn.ewsd.system.service.DicItemService;
import cn.ewsd.system.service.DicSetService;
import cn.ewsd.system.service.RedissonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

@Controller
@RequestMapping("/system/dicItem")
public class DicItemController extends SystemBaseController {

    @Resource
    private DicItemService dicItemService;

    @Resource
    private DicSetService dicSetService;

    @Autowired
    private RedissonService redissonService;

    @RequestMapping("index")
    public String index() throws Exception {
        return "system/dicItem/index";
    }

    @ResponseBody
    @RequestMapping(value = "getPageSet")
    public Object getPageSet(PageParam pageParam, String puuid) throws Exception {
        String filterSort = "";
        if (request.getParameter("sort") == null) {
            String myFilter = " and puuid = '" + puuid + "'";
            filterSort = BaseUtils.filterSort(request, myFilter).replace("ORDER BY create_time DESC", "ORDER BY sort ASC");
        }
        PageSet<DicItem> pageSet = dicItemService.getPageSet(pageParam, filterSort);
        //PageSet<DicSet> pageSet = dicSetService.getPageSetByHql(request, "FROM DicSet ORDER BY sort DESC");
        return pageSet;
    }


    @ResponseBody
    @RequestMapping("getDetailByUuid")
    public Object getDetailByUuid() throws Exception {
        String uuid = request.getParameter("uuid");
        DicItem info = dicItemService.selectByPrimaryKey(uuid);
        return info;
    }


    @RequestMapping("add")
    public String add() {
        return display();
    }

    @ResponseBody
    @RequestMapping(value = "save", method = RequestMethod.POST)
    public Object save(DicItem dicItem) throws Exception {
        DicItem dicItem1 = dicSetService.getListByValue(dicItem.getValue());
        if (null == dicItem1) {
            DicSet dicSet = dicSetService.getListByCode(request.getParameter("code"));
            dicItem.setPuuid(dicSet.getUuid());
            redissonService.getRBucket("sysDicItem").delete();
            return dicItemService.insertSelective(getSaveData(dicItem));
        }
        return failure("字典项值重复!!!");
    }

    @RequestMapping("edit")
    public String edit() throws Exception {
        return display();
    }

    @ResponseBody
    @RequestMapping(value = "update", method = RequestMethod.POST)
    public Object update(DicItem dicItem) throws Exception {
        Boolean isValueExist = dicSetService.getListByValueAndUuid(dicItem.getValue(), dicItem.getUuid());
        if (! isValueExist) {
            redissonService.getRBucket("sysDicItem").delete();
            return dicItemService.updateByPrimaryKeySelective(getUpdateData(dicItem));
        }
        return failure("更新失败，代码重复！");
    }

    @ResponseBody
    @RequestMapping(value = "delete", method = RequestMethod.POST)
    public Object delete(String[] uuid) throws Exception {
        Integer result = 0;
        for (int i = 0; i < uuid.length; i++) {
            // String hql = "DELETE FROM DicItem WHERE uuid IN ('" + uuid[i] + "')";
            result = dicItemService.deleteByPrimaryKey(uuid[i]);
            result++;
        }
        redissonService.getRBucket("sysDicItem").delete();
        return result > 0 ? success("删除成功") : failure("删除失败");
    }

}
