package cn.ewsd.system.model;

import cn.ewsd.common.model.MCoreBase;
import lombok.Data;

import javax.persistence.Table;

@Data
@Table(name = "report_templet")
public class Templet extends MCoreBase {

    private String name;
    private byte[] content;

}
