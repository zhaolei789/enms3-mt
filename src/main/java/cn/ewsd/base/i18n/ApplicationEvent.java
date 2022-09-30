package cn.ewsd.base.i18n;

import org.springframework.context.ApplicationListener;
import org.springframework.context.MessageSource;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class ApplicationEvent implements ApplicationListener<ContextRefreshedEvent> {
    @Resource
    protected MessageSource messageSource;
    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        MessageUtils.setMessageSource(messageSource);
    }
}
