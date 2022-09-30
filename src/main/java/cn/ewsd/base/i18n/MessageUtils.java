package cn.ewsd.base.i18n;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.ResourceBundleMessageSource;

public class MessageUtils extends ResourceBundleMessageSource {
    private static MessageSource messageSource;

    public static void setMessageSource(MessageSource source){
        messageSource=source;
    }
    public MessageUtils() {
        super();
        //this.messageSource = messageSource;
    }

    /**
     * 获取单个国际化翻译值
     */
    public static String get(String pvsKey) {
        try {
            return messageSource.getMessage(pvsKey, null, LocaleContextHolder.getLocale());
        } catch (Exception e) {
            return pvsKey;
        }
    }
    /**
     * 获取单个国际化翻译值
     */
    public static String get(String pvsKey,Object ... pvParams) {
        try {
            return messageSource.getMessage(pvsKey, pvParams, LocaleContextHolder.getLocale());
        } catch (Exception e) {
            return pvsKey;
        }
    }
}
