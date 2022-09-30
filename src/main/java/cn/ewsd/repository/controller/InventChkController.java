package cn.ewsd.repository.controller;

import cn.ewsd.base.utils.XException;
import cn.ewsd.base.utils.jwt.LoginInfo;
import cn.ewsd.common.utils.BaseUtils;
import cn.ewsd.common.utils.easyui.PageParam;
import cn.ewsd.common.utils.easyui.PageSet;
import cn.ewsd.repository.model.MBulkList;
import cn.ewsd.repository.model.MCheck;
import cn.ewsd.repository.service.MBulkListService;
import cn.ewsd.repository.service.MCheckService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @Author fengjw
 * @Email 11128474@qq.cn
 * @Date 2021-04-25 17:06:36
 */
@Controller
@RequestMapping("/repository/inventChk")
public class InventChkController extends RepositoryBaseController {
    @Autowired
    private MCheckService mCheckService;
    @Autowired
    private MBulkListService mBulkListService;
    //分页页面
    @RequestMapping("/index")
    public String index(@RequestParam Map<String, Object> params) {
        List<MCheck> list = mCheckService.getCheckSet("72134");
        String checkNo = "";
        if(list.size()>0){
            checkNo = list.get(0).getCheckNo();
        }
        request.setAttribute("checkNo", checkNo);

        return "repository/inventChk/index";
    }

    //获取分页集
    @ResponseBody
    @RequestMapping(value = "/getPageSet", method = RequestMethod.POST)
    public Object getPageSet(PageParam pageParam) {
        String filterSort = "";
        filterSort = BaseUtils.filterSort(request, filterSort + getAuthFilter());

        String checkNoQry = request.getParameter("checkNoQry");

        try{
            PageSet<MBulkList> pageSet = mBulkListService.getChkPageSet(pageParam, filterSort, checkNoQry);
            return pageSet;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @ResponseBody
    @RequestMapping(value = "/getCheckSet", method = RequestMethod.POST)
    public Object getCheckSet() {
        return mCheckService.getCheckSet("72134");
    }

    @ResponseBody
    @RequestMapping(value = "/delete")
    public Object deleteBulk() {
        String key = request.getParameter("key");
        String listId = key.split(",")[0];
        String checkNo = key.split(",")[1];

        try {
            mCheckService.deleteBulk(listId, checkNo);
            return success("删除成功！");
        }catch (Exception e){
            return failure("删除失败！");
        }
    }

    @ResponseBody
    @RequestMapping(value = "/finish")
    public Object finishCheck() {
        try {
            mCheckService.finishCheck(request, LoginInfo.get());
            return success("完成盘点成功！");
        }catch (XException e){
            return failure(e.getInfo());
        }catch (Exception e){
            e.printStackTrace();
            return failure("完成盘点失败！");
        }
    }
}
