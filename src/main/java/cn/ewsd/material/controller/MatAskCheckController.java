package cn.ewsd.material.controller;

import cn.ewsd.base.utils.XException;
import cn.ewsd.base.utils.jwt.LoginInfo;
import cn.ewsd.common.utils.BaseUtils;
import cn.ewsd.common.utils.easyui.PageParam;
import cn.ewsd.common.utils.easyui.PageSet;
import cn.ewsd.material.model.MAskCode;
import cn.ewsd.material.service.MAskCodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @Author fengjw
 * @Email 11128474@qq.cn
 * @Date 2021-04-21 16:55:52
 */
@Controller
@RequestMapping("/material/matAskCheck")
public class MatAskCheckController extends MaterialBaseController {

    @Autowired
    private MAskCodeService mAskCodeService;
    //分页页面
    @RequestMapping("/index")
    public String index(@RequestParam Map<String, Object> params) {
        return "material/matAskCheck/index";
    }

    @ResponseBody
    @RequestMapping(value = "/getIndex", method = RequestMethod.POST)
    public Object getIndex() {
        return mAskCodeService.getCheckIndexList(LoginInfo.getUuid(), LoginInfo.getOrgId());
    }

    @ResponseBody
    @RequestMapping(value = "/getPageSet", method = RequestMethod.POST)
    public Object getPageSet(PageParam pageParam) {
        String askTeam = request.getParameter("askTeam");
        String askMonth = request.getParameter("askMonth");
        String askStep = request.getParameter("askStep");

        String filterSort = "";
        filterSort = BaseUtils.filterSort(request, filterSort + getAuthFilter());
        PageSet<MAskCode> pageSet = mAskCodeService.getCheckPageSet(pageParam, filterSort, askTeam, askMonth, askStep);
        return pageSet;
    }

    @ResponseBody
    @RequestMapping(value = "/submit")
    public Object submitCheck() {
        try {
            mAskCodeService.checkAskCode(request, LoginInfo.get());
            return success("审批成功！");
        }catch (XException e){
            return failure(e.getInfo());
        }catch (Exception e){
            e.printStackTrace();
            return failure("审批失败！");
        }
    }

    @ResponseBody
    @RequestMapping(value = "/back")
    public Object backCheck() {
        try {
            mAskCodeService.backAskCode(request, LoginInfo.get());
            return success("退回成功！");
        }catch (XException e){
            return failure(e.getInfo());
        }catch (Exception e){
            e.printStackTrace();
            return failure("退回失败！");
        }
    }
}
