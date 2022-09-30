package cn.ewsd.system.service;

import cn.ewsd.common.utils.easyui.PageParam;
import cn.ewsd.common.utils.easyui.PageSet;
import cn.ewsd.system.model.Attachment;

import java.util.List;

public interface AttachmentService extends SystemBaseService<Attachment, String> {

    PageSet getPageSet(PageParam pageParam, String filterSort);

    Integer getListByPid(String uuid);

    Attachment getByAuuid(String uuid);

    int updateFileNameAndFileVersionAndFileStatusAndUuid(String fileName, String fileVersion, String fileStatus, String uuid);

    List<Attachment> getListByPuuid(String uuid);

    List<Attachment> getListByUuid(String uuid);

    Attachment getAttachmentByPath(String path);

    Attachment getByPuuidOne(String uuid);
}