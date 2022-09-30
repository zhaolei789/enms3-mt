package cn.ewsd.fix.service.impl;

import cn.ewsd.common.service.impl.MybatisBaseServiceImpl;
import cn.ewsd.system.service.SystemBaseService;
import org.springframework.stereotype.Service;

import java.io.Serializable;

@Service("logisticsBaseServiceImpl")
public abstract class FixBaseServiceImpl<T, PK extends Serializable> extends MybatisBaseServiceImpl<T, Serializable> implements SystemBaseService<T, PK> {

}