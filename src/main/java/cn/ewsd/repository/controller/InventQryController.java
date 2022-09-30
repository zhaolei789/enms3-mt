package cn.ewsd.repository.controller;

import cn.ewsd.base.utils.Data;
import cn.ewsd.base.utils.PoiUtils;
import cn.ewsd.base.utils.XDate;
import cn.ewsd.base.utils.XException;
import cn.ewsd.base.utils.jwt.LoginInfo;
import cn.ewsd.common.utils.BaseUtils;
import cn.ewsd.common.utils.easyui.PageParam;
import cn.ewsd.common.utils.easyui.PageSet;
import cn.ewsd.mdata.service.UtilService;
import cn.ewsd.repository.model.MBulkList;
import cn.ewsd.repository.model.MCheck;
import cn.ewsd.repository.model.MIn;
import cn.ewsd.repository.service.MBulkListService;
import cn.ewsd.repository.service.MCheckService;
import cn.ewsd.repository.service.MInService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * @Author fengjw
 * @Email 11128474@qq.cn
 * @Date 2021-04-25 17:06:36
 */
@Controller
@RequestMapping("/repository/inventQry")
public class InventQryController extends RepositoryBaseController {
    @Autowired
    private MCheckService checkService;
    @Autowired
    private MBulkListService mBulkListService;
    //分页页面
    @RequestMapping("/index")
    public String index(@RequestParam Map<String, Object> params) {
        String checkNoQry = "";
        List<MCheck> list = checkService.getCheckSet("7213F");
        if(list.size()>0){
            checkNoQry = list.get(0).getCheckNo();
        }
        request.setAttribute("checkNoQry", checkNoQry);

        return "repository/inventQry/index";
    }

    //获取分页集
    @ResponseBody
    @RequestMapping(value = "/getPageSet", method = RequestMethod.POST)
    public Object getPageSet(PageParam pageParam) {
        try{
            String checkNoQry = request.getParameter("checkNoQry");

            String filterSort = "";
            filterSort = BaseUtils.filterSort(request, filterSort + getAuthFilter());
            PageSet<MBulkList> pageSet = mBulkListService.getInventQryPageSet(pageParam, filterSort, checkNoQry);
            return pageSet;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @ResponseBody
    @RequestMapping(value = "/getCheckSet", method = RequestMethod.POST)
    public Object getCheckSet() {
        return checkService.getCheckSet("7213F");
    }
}
