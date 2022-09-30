package cn.ewsd.system.controller;

import cn.ewsd.common.annotation.ControllerLog;
import cn.ewsd.common.utils.BaseUtils;
import cn.ewsd.common.utils.easyui.PageParam;
import cn.ewsd.common.utils.easyui.PageSet;
import cn.ewsd.system.model.SysUserQryOrg;
import cn.ewsd.system.service.SysUserQryOrgService;
import cn.ewsd.system.service.TCheckService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * 
 *
 * @Author zxrmine
 * @Email zxrmine@163.cn
 * @Date 2021-06-09 17:58:25
 */
@Controller
@RequestMapping("/system/tCheck")
public class TCheckController extends SystemBaseController {

    @Autowired
    private TCheckService tCheckService;

    @ResponseBody
    @RequestMapping(value = "/getCheck")
    public Object getCheck() {
        String checkNo = request.getParameter("checkNo");
        return tCheckService.getTCheckByNo(checkNo);
    }
}
