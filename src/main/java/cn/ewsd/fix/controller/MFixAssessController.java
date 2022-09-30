package cn.ewsd.fix.controller;

import cn.ewsd.base.utils.jwt.LoginInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.stereotype.Controller;
import cn.ewsd.common.utils.easyui.PageParam;

import cn.ewsd.fix.service.MFixAssessService;

/**
 * @Author zxrmine
 * @Email zxrmine@163.cn
 * @Date 2021-05-13 15:11:59
 */
@Controller
@RequestMapping("/fix/mFixAssess")
public class MFixAssessController extends FixBaseController {
    @Autowired
    private MFixAssessService mFixAssessService;

    @ResponseBody
    @RequestMapping(value = "/getFixAssessTeam")
    public Object getPageSet(PageParam pageParam) {
        String assMonth = request.getParameter("assMonth");
        String userTeam = LoginInfo.getOrgId();

        try {
            return mFixAssessService.getFixAssessTeam(assMonth, userTeam);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
}
