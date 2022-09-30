package cn.ewsd.cost.service.impl;

import cn.ewsd.common.service.impl.MybatisBaseServiceImpl;
import cn.ewsd.cost.service.CostBaseService;
import org.springframework.stereotype.Service;

import java.io.Serializable;

@Service("costBaseServiceImpl")
public abstract class CostBaseServiceImpl<T, PK extends Serializable> extends MybatisBaseServiceImpl<T, Serializable> implements CostBaseService<T, PK> {

}