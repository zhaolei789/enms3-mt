package cn.ewsd.base.tag;

import cn.ewsd.base.utils.jwt.LoginInfo;
import cn.ewsd.base.utils.jwt.UserInfo;
import cn.ewsd.system.service.AuthAccessViewService;
import cn.ewsd.system.service.RedissonService;
import org.redisson.api.RBucket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.thymeleaf.context.ITemplateContext;
import org.thymeleaf.model.IProcessableElementTag;
import org.thymeleaf.processor.element.AbstractElementTagProcessor;
import org.thymeleaf.processor.element.IElementTagStructureHandler;
import org.thymeleaf.spring5.context.SpringContextUtils;
import org.thymeleaf.templatemode.TemplateMode;

import java.util.ArrayList;
import java.util.List;

public class RbacElementTagProcessor extends AbstractElementTagProcessor {

    @Autowired
    public UserInfo userInfo;

    private static final String TAG_NAME = "rbac";
    private static final int PRECEDENCE = 1000;

    public RbacElementTagProcessor(final String dialectPrefix) {
        super(
                TemplateMode.HTML, // This processor will apply only to HTML mode
                dialectPrefix,     // Prefix to be applied to name for matching
                TAG_NAME,          // Tag name: match specifically this tag
                true,              // Apply dialect prefix to tag name
                null,              // No attribute name: will match by tag name
                false,             // No prefix to be applied to attribute name
                PRECEDENCE);       // Precedence (inside dialect's own precedence)
    }

    @Override
    protected void doProcess(
            final ITemplateContext context, final IProcessableElementTag tag,
            final IElementTagStructureHandler structureHandler) {

        final ApplicationContext appCtx = SpringContextUtils.getApplicationContext(context);
//        final UserClient userClient = appCtx.getBean(UserClient.class);
//        final UserInfo userInfo = appCtx.getBean(UserInfo.class);
        final AuthAccessViewService authAccessViewService = appCtx.getBean(AuthAccessViewService.class);

        final RedissonService redissonService = appCtx.getBean(RedissonService.class);
        //User user = userService.getUserByUserNameId("ewsd0001");

        // 添加权限 url
        List<String> privilegeList = new ArrayList<>();
        /*List<Map<String, Object>> privileges = authAccessViewService.getAuthorizedMenu(userClient.getRoleIdByUserNameId(userInfo.getUserNameId()));
        if (privileges != null && !privileges.isEmpty()) {
            for (Map<String, Object> entry : privileges) {
                Object obj = entry.get("url");
                if (obj != null && !"".equals(obj.toString())) {
                    privilegeList.add(entry.get("url").toString());
                }
            }
        }*/
        //session.setAttribute(Constants.USER_URLS, privilegeList);
        //在redis获取页面上的按钮权限
        RBucket<List<String>> rBucket = redissonService.getRBucket(LoginInfo.getUserNameId());
        privilegeList = rBucket.get();

        //获取标签自定义属性的值
        String str = tag.getAttributeValue("str");

        /*Boolean result = false;
        for (String p_url : privilegeList) {
            if (p_url.equals(str)) {
                result = true;
                break;
            }
        }*/

        if (privilegeList.contains(str)) {
            structureHandler.removeTags();
        } else {
            structureHandler.removeTags();
            structureHandler.removeElement();
        }
    }

}