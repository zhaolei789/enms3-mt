package cn.ewsd.mdata.service.impl;


import cn.ewsd.common.utils.easyui.PageParam;
import cn.ewsd.common.utils.easyui.PageSet;
import cn.ewsd.common.utils.easyui.PageUtils;
import cn.ewsd.mdata.mapper.OrganizationPostMapper;
import cn.ewsd.mdata.model.OrganizationPost;
import cn.ewsd.mdata.service.OrganizationPostService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@SuppressWarnings({"unchecked"})
@Service("organizationPostService")
public class OrganizationPostServiceImpl extends MdataBaseServiceImpl<OrganizationPost, String> implements OrganizationPostService {

    @Resource
    OrganizationPostMapper organizationPostMapper;



//    @Resource
//    public void setUcenterBaseDao(IUcenterBaseDao<OrganizationPost, String> organizationPostDao) {
//        super.setBaseDao(organizationPostDao);
//    }

    @Override
    public OrganizationPost getOrgPostByOrgUuidAndPost(String orgUuid, String postValue) {
        return organizationPostMapper.getOrgPostByOrgUuidAndPost(orgUuid,postValue);
    }

    @Override
    public PageSet<OrganizationPost> getPageSet(PageParam pageParam, String filterSort) {
        PageHelper.startPage(pageParam.getPage(), pageParam.getRows());
        List<OrganizationPost> list = organizationPostMapper.getPageSet(filterSort);
        PageInfo<OrganizationPost> pageInfo = new PageInfo<>(list);
        return PageUtils.getPageSet(pageInfo);
    }


}
