package cn.ewsd.mdata.model;

import javax.persistence.Id;
import java.io.Serializable;
import java.util.UUID;

/**
 * @author 小策一喋<xvpindex@qq.com>
 * @className Uuid
 * @description
 * @date 2018-03-15 14:31
 */
public class Uuid implements Serializable {

    @Id
    private String uuid;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid == "" ? UUID.randomUUID().toString().replaceAll("-", "") : uuid;
    }

    @Override
    public int hashCode() {
        return uuid == null ? System.identityHashCode(this) : uuid.hashCode();
    }

}
