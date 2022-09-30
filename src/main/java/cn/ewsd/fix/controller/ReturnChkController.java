package cn.ewsd.fix.controller;

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
 * 回收计划
 *
 * @Author zxrmine
 * @Email zxrmine@163.cn
 * @Date 2021-05-12 14:32:59
 */
@Controller
@RequestMapping("/fix/returnChk")
public class ReturnChkController extends FixBaseController {
    @Autowired
    private MInService mInService;

    @RequestMapping("/index")
    public String index(@RequestParam Map<String, Object> params) {
        return "fix/returnChk/index";
    }

    @ResponseBody
    @RequestMapping(value = "/getPageSet", method = RequestMethod.POST)
    public Object getPageSet(PageParam pageParam) {
        String storeQry = request.getParameter("storeQry");
        String matQry = request.getParameter("matQry");
        String userTeam = LoginInfo.getOrgId();
        String userNo = LoginInfo.getUuid();

        try {
            String filterSort = "";
            filterSort = BaseUtils.filterSort(request, filterSort + getAuthFilter());
            PageSet<MIn> pageSet = mInService.getReturnChkList(pageParam, filterSort, userNo, userTeam, matQry, storeQry);
            return pageSet;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @ResponseBody
    @RequestMapping("/submit")
    public Object submitTurnChk() {
        try {
            mInService.submitReturnChk(request, LoginInfo.get());
            return success("提交成功");
        }catch (XException e){
            return failure(e.getInfo());
        }catch (Exception e){
            e.printStackTrace();
            return failure("提交失败！");
        }
    }

    @ResponseBody
    @RequestMapping(value = "/back")
    public Object backTurnChk() {
        try {
            mInService.backReturnChk(request, LoginInfo.get());
            return success("退回成功");
        }catch (XException e){
            return failure(e.getInfo());
        }catch (Exception e){
            e.printStackTrace();
            return failure("退回失败！");
        }
    }
}
