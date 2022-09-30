package cn.ewsd.fix.mapper;
import cn.ewsd.fix.model.MBackPlan;

import java.util.List;

/**
 * @Author zxrmine
 * @Email zxrmine@163.cn
 * @Date 2021-05-12 14:32:59
 */
public interface OtherModuleMapper {

    MBackPlan queryObject(Object var1);

    List<MBackPlan> queryList(Object var1);

}
