package cn.ewsd.material.service.impl;

import cn.ewsd.common.service.impl.MybatisBaseServiceImpl;
import cn.ewsd.system.service.SystemBaseService;
import org.springframework.stereotype.Service;

import java.io.Serializable;

/**
 * 基础服务实现
 * ============================================================================
 * @author  佐佑科技<service@ewsd.cn>
 * @date    2015-06-01
 * @version 0.1
 * ============================================================================
 */

@Service("systemBaseServiceImpl")
public abstract class MaterialBaseServiceImpl<T, PK extends Serializable> extends MybatisBaseServiceImpl<T, Serializable> implements SystemBaseService<T, PK> {
	
}
