package cn.ewsd.system.controller;

import cn.ewsd.base.utils.jwt.UserInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@RequestMapping("/system/user")
@Controller
public class RedisLoginController {
    private static final Logger logger = LoggerFactory.getLogger(RedisLoginController.class);

    /**
     * 退出	 * @param request	 * @return
     */
    @RequestMapping("/loginOut")
    @ResponseBody
    public String loginOut(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        if (session != null) {
            session.setAttribute(session.getId(), null);
        }
        return "ok";
    }

    /**
     * 登录验证	 * @param request	 * @return
     */
    @RequestMapping("/login")
    public ModelAndView login(HttpServletRequest request, Model model) {
        String userNameId = request.getParameter("userNameId");
        String password = request.getParameter("password");
        //TODO校验
        Map<String, String> map = new HashMap<>();
        map.put("name", userNameId);
        map.put("pwd", password);
        UserInfo userInfo = null;
        try {
            userInfo = userInfo;
        } catch (Exception e) {
            logger.error("user login is error...", e);
        }
        if (userInfo != null) {
            HttpSession session = request.getSession();
            session.setAttribute(session.getId(), userInfo);
            model.addAttribute("user", userInfo);
            logger.info("user login is success,{}", userNameId);
            return new ModelAndView("redirect:/index");
        } else {
            request.setAttribute("errorInfo", "验证失败");
            return new ModelAndView("login/login");
        }
    }
}