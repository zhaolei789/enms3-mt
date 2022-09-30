package cn.ewsd.system.event;

import cn.ewsd.mdata.model.User;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class UserRegisterEvent extends ApplicationEvent {

    //注册用户对象
    private User user;

    /**
     * 重写构造函数
     *
     * @param source 发生事件的对象
     * @param user   注册用户对象
     */
    public UserRegisterEvent(Object source, User user) {
        super(source);
        this.user = user;
    }

}