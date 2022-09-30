package cn.ewsd.fix.controller;

import cn.ewsd.base.utils.XException;
import cn.ewsd.base.utils.jwt.LoginInfo;
import cn.ewsd.common.utils.BaseUtils;
import cn.ewsd.common.utils.easyui.PageParam;
import cn.ewsd.common.utils.easyui.PageSet;
import cn.ewsd.repository.model.MOut;
import cn.ewsd.repository.model.MOut;
import cn.ewsd.repository.service.MOutService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

/**
 * 回收计划
 *
 * @Author zxrmine
 * @Email zxrmine@163.cn
 * @Date 2021-05-12 14:32:59
 */
@Controller
@RequestMapping("/fix/oldOutChk")
public class OldOutChkController extends FixBaseController {

    @Autowired
    private MOutService mOutService;


    @RequestMapping("/index")
    public String index(@RequestParam Map<String, Object> params) {
        return "fix/oldOutChk/index";
    }

    @ResponseBody
    @RequestMapping(value = "/getPageSet", method = RequestMethod.POST)
    public Object getPageSet(PageParam pageParam) {
        String teamNoQry = request.getParameter("teamNoQry");

        try {
            String filterSort = "";
            filterSort = BaseUtils.filterSort(request, filterSort + getAuthFilter());
            PageSet<MOut> pageSet = mOutService.getOldOutChkList(pageParam, filterSort, LoginInfo.getUuid(), LoginInfo.getOrgId(), teamNoQry);
            return pageSet;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @ResponseBody
    @RequestMapping(value = "/submit")
    public Object submitOldOutChk() {
        try {
            mOutService.submitOldOutChk(request, LoginInfo.get());
            return success("提交成功！");
        }catch (XException e){
            return failure(e.getInfo());
        }catch (Exception e){
            e.printStackTrace();
            return failure("提交失败！");
        }
    }

    @ResponseBody
    @RequestMapping(value = "/back")
    public Object backOldOutChk(PageParam pageParam) {
        try {
            mOutService.backOldOutChk(request, LoginInfo.get());
            return success("提交成功！");
        }catch (XException e){
            return failure(e.getInfo());
        }catch (Exception e){
            e.printStackTrace();
            return failure("提交失败！");
        }
    }
}
