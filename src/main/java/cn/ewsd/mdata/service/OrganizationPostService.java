package cn.ewsd.mdata.service;

import cn.ewsd.common.utils.easyui.PageParam;
import cn.ewsd.common.utils.easyui.PageSet;
import cn.ewsd.mdata.model.OrganizationPost;

public interface OrganizationPostService extends MdataBaseService<OrganizationPost, String> {

    OrganizationPost getOrgPostByOrgUuidAndPost(String orgUuid, String code);

    PageSet<OrganizationPost> getPageSet(PageParam pageParam, String filterSort);

}