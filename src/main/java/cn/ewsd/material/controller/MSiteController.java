package cn.ewsd.material.controller;

import java.util.Date;
import java.util.List;
import java.util.Map;

import cn.ewsd.base.utils.PingYinUtil;
import cn.hutool.core.util.PinyinUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.stereotype.Controller;

import cn.ewsd.common.annotation.ControllerLog;
import cn.ewsd.common.controller.BaseController;
import cn.ewsd.common.utils.BaseUtils;
import cn.ewsd.common.utils.easyui.PageParam;
import cn.ewsd.common.utils.easyui.PageSet;

import cn.ewsd.material.model.MSite;
import cn.ewsd.material.service.MSiteService;

/**
 * 货位
 *
 * @Author zxrmine
 * @Email zxrmine@163.cn
 * @Date 2022-07-06 17:28:02
 */
@Controller
@RequestMapping("/material/mSite")
public class MSiteController extends MaterialBaseController {

    @Autowired
    private MSiteService mSiteService;

    //分页页面
    @RequestMapping("/index")
    @ControllerLog(description = "打开MSite模块管理页面")
    public String index(@RequestParam Map<String, Object> params) {
        return "material/mSite/index";
    }

    //获取分页集
    @ResponseBody
    @RequestMapping(value = "/getPageSet", method = RequestMethod.POST)
    @ControllerLog(description = "获得MSite分页集数据")
    public Object getPageSet(PageParam pageParam) {
        String filterSort = "";
        filterSort = BaseUtils.filterSort(request, filterSort + getAuthFilter());
        PageSet<MSite> pageSet = mSiteService.getPageSet(pageParam, filterSort);
        return pageSet;
    }

    //获取详情
    @ResponseBody
    @RequestMapping(value = "/getDetailByUuid")
    @ControllerLog(description = "获得MSite模块详细数据")
    public Object getDetailByUuid(String uuid) {
        MSite mSite = mSiteService.selectByPrimaryKey(uuid);
        return mSite;
    }

    //新增页面
    @RequestMapping("/add")
    @ControllerLog(description = "打开MSite模块新增页面")
    public String add() {
        return "material/mSite/add";
    }

    //保存数据
    @ResponseBody
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @ControllerLog(description = "保存MSite模块数据")
    public Object save(@ModelAttribute MSite mSite) {
        mSite.setUuid(mSite.getStoreNo()+mSite.getSiteCode());
        if(mSite.getWordLevel()==null||"".equals(mSite.getWordLevel())){
            mSite.setWordLevel(PingYinUtil.getFirstSpell(mSite.getSiteName()));
        }
        mSite.setNote("");
        mSite.setType(1);
        mSite.setStatus(1);
        mSite.setIsDel(0);
        int result = mSiteService.insertSelective(getSaveData(mSite));
        return result > 0 ? success("保存成功！") : failure("保存失败！");
    }

    //编辑页面
    @RequestMapping("/edit")
    @ControllerLog(description = "打开MSite模块编辑页面")
    public String edit() {
        return "material/mSite/edit";
    }

    //更新数据
    @ResponseBody
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ControllerLog(description = "更新MSite模块数据")
    public Object update(@ModelAttribute MSite mSite) {
        int result = mSiteService.updateByPrimaryKeySelective(getUpdateData(mSite));
        return result > 0 ? success("更新成功！") : failure("更新失败！");
    }

    //删除数据
    @ResponseBody
    @RequestMapping(value = "/deleteBatch", method = RequestMethod.POST)
    @ControllerLog(description = "删除MSite模块数据")
    public Object deleteBatch(@RequestParam String[] uuid) {
        int result = mSiteService.executeDeleteBatch(uuid);
        return result > 0 ? success("删除成功！") : failure("删除失败！");
    }


    @ResponseBody
    @RequestMapping(value = "/getListAll", method = RequestMethod.POST)
    public Object getListAll() {
        List<MSite> list = mSiteService.getAllList();
        return list;
    }

    @ResponseBody
    @RequestMapping(value = "/getListByStoreNo", method = RequestMethod.POST)
    public Object getListByStoreNo(String storeNo) {
        List<MSite> list = mSiteService.getListByStoreNo(storeNo);
        return list;
    }
}
