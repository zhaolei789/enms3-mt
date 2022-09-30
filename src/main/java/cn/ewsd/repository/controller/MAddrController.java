package cn.ewsd.repository.controller;

import cn.ewsd.repository.service.MAddrService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Author fengjw
 * @Email 11128474@qq.cn
 * @Date 2021-04-25 17:06:36
 */
@Controller
@RequestMapping("/repository/mAddr")
public class MAddrController extends RepositoryBaseController {
    @Autowired
    private MAddrService mAddrService;

    //获取分页集
    @ResponseBody
    @RequestMapping(value = "/getAddr")
    public Object getAddr() {
        String teamNo = request.getParameter("teamNo");
        return mAddrService.getAddr(teamNo);
    }
}
