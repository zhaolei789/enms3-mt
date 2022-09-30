package cn.ewsd.system.controller;

import cn.ewsd.base.utils.JsonUtils;
import cn.ewsd.base.utils.jwt.LoginInfo;
import cn.ewsd.cost.service.SysUserBarMasterService;
import cn.ewsd.mdata.model.User;
import cn.ewsd.system.model.AuthAccessView;
import cn.ewsd.system.model.DicItem;
import cn.ewsd.system.model.DicSet;
import cn.ewsd.system.service.*;
import cn.ewsd.mdata.service.UserService;
import org.redisson.api.RBucket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller("systemIndex")
@RequestMapping("/system/index")
public class IndexController extends SystemBaseController {

    @Resource
    private ConfigService configService;
    @Resource
    private AuthAccessViewService authAccessViewService;
    @Resource
    private UserService userService;


    private String defaultSystem = "system";

    @Value("${topjui.right-accordion.fit:true}")
    private Boolean accordionFit;

    @RequestMapping("index")
    public String index(String portal, HttpServletResponse response) throws Exception {
        if (!(portal == null || portal.equals("null"))) {
            defaultSystem = portal;
        }
        // 设置左侧导航是否自适应页面大小
        request.setAttribute("accordionFit", accordionFit);

        request.setAttribute("portal", defaultSystem);

        Map<String, Map<String, String>> stringMapMap = configService.redisCacheConfig();
        Map<String, String> sysConfig = stringMapMap.get("sysConfig");
        request.setAttribute("sysConfig",sysConfig);

        String systemVersion = configService.getValue("sysConfig","systemVersion");
        request.setAttribute("systemVersion", systemVersion);
        User uu = userService.getUserByUuid(LoginInfo.getUuid());
        request.setAttribute("userInfo", uu);

        List<AuthAccessView> systemList = getSystemList();
        request.setAttribute("systemList", systemList);

        return "system/index/index";
        //return display();
    }

//    @RequestMapping(value = "desk")
//    public String desk() {
//        String hql = "FROM Document WHERE cid = '23D7BC22BD43272AE055000000000001'";
//        List<Document> list1 = documentService.getHotListByCids('C4B5DA4F6D7C4C7CA100FDAFF9CFA36F',7);
//        request.setAttribute("list1", list1);
//
//        String hql2 = "FROM Document WHERE cid = 'D87D7DBC4D9E4620AFF82D652694E921'";
//        List<Document> list2 = documentService.getLimitedListByCid(D87D7DBC4D9E4620AFF82D652694E921, 7);
//        request.setAttribute("list2", list2);
//
//        String hql3 = "FROM Document WHERE cid = '23D7BC22BD89272AE055000000000001'";
//        List<Document> list3 = documentService.getLimitedListByCid('23D7BC22BD89272AE055000000000001', 7);
//        request.setAttribute("list3", list3);
//
//        String hql4 = "FROM Document WHERE cid = '23D7BC22BD87272AE055000000000001'";
//        List<Document> list4 = documentService.getLimitedListByCid('23D7BC22BD87272AE055000000000001', 7);
//        request.setAttribute("list4", list4);
//
//        String hql5 = "FROM Document WHERE cid = '23D7BC22BD88272AE055000000000001'";
//        List<Document> list5 = documentService.getLimitedListByCid('23D7BC22BD88272AE055000000000001', 7);
//        request.setAttribute("list5", list5);
//
//        String hql6 = "FROM Document WHERE cid = 'C934CE72417C46C6B0E6C0A8474E8AA2'";
//        List<Document> list6 = documentService.getLimitedListByCid('C934CE72417C46C6B0E6C0A8474E8AA2', 7);
//        request.setAttribute("list6", list6);
//
//        String hql7 = "FROM Document WHERE cid = '2B11A0D540EF41259754719CC727B0BE'";
//        List<Document> list7 = documentService.getLimitedListByCid('2B11A0D540EF41259754719CC727B0BE', 7);
//        request.setAttribute("list7", list7);
//
//        String hql8 = "FROM Document WHERE cid = 'C4B5DA4F6D7C4C7CA100FDAFF9CFA36F'";
//        List<Document> list8 = documentService.getLimitedListByCid('C4B5DA4F6D7C4C7CA100FDAFF9CFA36F', 7);
//        request.setAttribute("list8", list8);
//
//        return display();
//    }

    /**
     * 拥有哪些系统的使用权限
     *
     * @return
     */
    public List<AuthAccessView> getSystemList() throws Exception {
        //String currentUserNameId = LoginInfo.getUserNameId();
//        String currentUserNameId = "ewsd0001";
        String roleId = LoginInfo.getRoleId();
        return authAccessViewService.getSystemList("menu", "1", roleId);
    }

    @ResponseBody
    @RequestMapping("requestSuccess")
    public Object requestSuccess(HttpServletResponse response) {
        return JsonUtils.messageJson(200, "温馨提示", "操作成功");
    }

}
