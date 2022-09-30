package cn.ewsd.logistics.service.impl;

import cn.ewsd.common.service.impl.MybatisBaseServiceImpl;
import cn.ewsd.logistics.service.LogisticsBaseService;
import cn.ewsd.system.service.SystemBaseService;
import org.springframework.stereotype.Service;

import java.io.Serializable;

@Service("logisticsBaseServiceImpl")
public abstract class LogisticsBaseServiceImpl <T, PK extends Serializable> extends MybatisBaseServiceImpl<T, Serializable> implements LogisticsBaseService<T, PK> {

}