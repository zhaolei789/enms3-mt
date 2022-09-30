package cn.ewsd.system.service.impl;

import cn.ewsd.system.model.Template;
import cn.ewsd.system.service.TemplateService;
import org.springframework.stereotype.Service;

@Service("templateServiceImpl")
public class TemplateServiceImpl extends SystemBaseServiceImpl<Template, String> implements TemplateService {

//	@Resource
//	private ISystemBaseDao<Template, String> userDao;
//
//	@Resource
//	public void setSystemBaseDao(ISystemBaseDao<Template, String> userDao) {
//		super.setBaseDao(userDao);
//	}

}
