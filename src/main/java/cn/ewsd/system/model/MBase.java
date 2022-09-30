package cn.ewsd.system.model;

import javax.persistence.Id;
import java.io.Serializable;

/**
 * @className MBase
 * @description
 * @author 小策一喋<xvpindex@qq.com>
 * @date 2018-03-15 14:31
 */
public class MBase implements Serializable {

    @Id
    private String uuid;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    @Override
    public int hashCode() {
        return uuid == null ? System.identityHashCode(this) : uuid.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass().getPackage() != obj.getClass().getPackage()) {
            return false;
        }
        final MBase other = (MBase) obj;
        if (uuid == null) {
            if (other.getUuid() != null) {
                return false;
            }
        } else if (!uuid.equals(other.getUuid())) {
            return false;
        }
        return true;
    }

}
