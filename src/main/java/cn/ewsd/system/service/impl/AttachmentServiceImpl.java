package cn.ewsd.system.service.impl;

import cn.ewsd.common.utils.easyui.PageParam;
import cn.ewsd.common.utils.easyui.PageSet;
import cn.ewsd.common.utils.easyui.PageUtils;
import cn.ewsd.system.mapper.AttachmentMapper;
import cn.ewsd.system.model.Attachment;
import cn.ewsd.system.service.AttachmentService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@SuppressWarnings({"unchecked"})
@Service("attachmentService")
public class AttachmentServiceImpl extends SystemBaseServiceImpl<Attachment, String> implements AttachmentService {

    @Autowired
    private AttachmentMapper attachmentMapper;


    @Override
    public PageSet getPageSet(PageParam pageParam, String filterSort) {
        PageHelper.startPage(pageParam.getPage(), pageParam.getRows());
        List<Attachment> list = attachmentMapper.getPageSet(filterSort);
        PageInfo<Attachment> pageInfo = new PageInfo<>(list);
        return PageUtils.getPageSet(pageInfo);
    }

    @Override
    public Integer getListByPid(String uuid) {
        return attachmentMapper.getListByPid(uuid);
    }

    @Override
    public Attachment getByAuuid(String uuid) {
        return attachmentMapper.getByAuuid(uuid);
    }

    @Override
    public int updateFileNameAndFileVersionAndFileStatusAndUuid(String fileName, String fileVersion, String fileStatus, String uuid) {
        return attachmentMapper.updateFileNameAndFileVersionAndFileStatusAndUuid(fileName,fileVersion,fileStatus,uuid);
    }

    @Override
    public List<Attachment> getListByPuuid(String uuid) {
        return attachmentMapper.getListByPuuid(uuid);
    }

    @Override
    public List<Attachment> getListByUuid(String uuid) {
        return attachmentMapper.getListByUuid(uuid);
    }

    @Override
    public Attachment getAttachmentByPath(String path) {
        return attachmentMapper.getAttachmentByPath(path);
    }

    @Override
    public Attachment getByPuuidOne(String uuid) {
        return attachmentMapper.getByPuuidOne(uuid);
    }
}
