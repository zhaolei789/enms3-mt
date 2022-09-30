package cn.ewsd.repository.controller;

import cn.ewsd.base.utils.XException;
import cn.ewsd.base.utils.jwt.LoginInfo;
import cn.ewsd.common.utils.BaseUtils;
import cn.ewsd.common.utils.easyui.PageParam;
import cn.ewsd.common.utils.easyui.PageSet;
import cn.ewsd.repository.model.MIn;
import cn.ewsd.repository.service.MInService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

/**
 * @Author fengjw
 * @Email 11128474@qq.cn
 * @Date 2021-04-25 17:06:36
 */
@Controller
@RequestMapping("/repository/inCheck")
public class InCheckController extends RepositoryBaseController {
    @Autowired
    private MInService mInService;
    //分页页面
    @RequestMapping("/index")
    public String index(@RequestParam Map<String, Object> params) {
        return "repository/inCheck/index";
    }

    //获取分页集
    @ResponseBody
    @RequestMapping(value = "/getIndexPageSet", method = RequestMethod.POST)
    public Object getIndexPageSet(PageParam pageParam) {
        String filterSort = "";
        filterSort = BaseUtils.filterSort(request, filterSort + getAuthFilter());

        try{
            PageSet<MIn> pageSet = mInService.getCheckIndexPageSet(pageParam, filterSort, LoginInfo.getUuid());
            return pageSet;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @ResponseBody
    @RequestMapping(value = "/getInCheckPageSet", method = RequestMethod.POST)
    public Object getInCheckPageSet(PageParam pageParam) {
        String filterSort = "";
        filterSort = BaseUtils.filterSort(request, filterSort + getAuthFilter());

        String applyDate = request.getParameter("applyDate");
        String inType = request.getParameter("inType");
        String inStep = request.getParameter("inStep");
        String storeNo = request.getParameter("storeNo");
        String matQry = request.getParameter("matQry");

        try{
            PageSet<MIn> pageSet = mInService.getInCheckPageSet(pageParam, filterSort, applyDate, inType, inStep, storeNo, matQry);
            return pageSet;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @ResponseBody
    @RequestMapping(value = "/submit")
    public Object submit() {
        try {
            mInService.submitMIn(request, LoginInfo.get());
            return success("入库成功！");
        }catch (XException e){
            return failure(e.getInfo());
        }catch (Exception e){
            e.printStackTrace();
            return failure("入库失败！");
        }
    }

    @ResponseBody
    @RequestMapping(value = "/back")
    public Object back(){
        try {
            mInService.backMIn(request, LoginInfo.get());
            return success("退回成功！");
        }catch (XException e){
            return failure(e.getInfo());
        }catch (Exception e){
            e.printStackTrace();
            return failure("退回失败！");
        }
    }
}
