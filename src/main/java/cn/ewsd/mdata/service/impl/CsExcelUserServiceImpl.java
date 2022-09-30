package cn.ewsd.mdata.service.impl;

import cn.ewsd.common.utils.easyui.PageParam;
import cn.ewsd.common.utils.easyui.PageSet;
import cn.ewsd.common.utils.easyui.PageUtils;
import cn.ewsd.mdata.mapper.CsExcelUserMapper;
import cn.ewsd.mdata.model.CsExcelUser;
import cn.ewsd.mdata.service.CsExcelUserService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service("csExcelUserServiceImpl")
public class CsExcelUserServiceImpl extends MdataBaseServiceImpl<CsExcelUser, String> implements CsExcelUserService {
	@Autowired
	private CsExcelUserMapper csExcelUserMapper;

    @Override
    public PageSet<CsExcelUser> getPageSet(PageParam pageParam, String filterSort) {
        PageHelper.startPage(pageParam.getPage(), pageParam.getRows());
        List<CsExcelUser> list = csExcelUserMapper.getPageSet(filterSort);
        PageInfo<CsExcelUser> pageInfo = new PageInfo<>(list);
        return PageUtils.getPageSet(pageInfo);
    }

	@Override
	public CsExcelUser queryObject(String uuid){
		return csExcelUserMapper.queryObject(uuid);
	}

	@Override
	public List<CsExcelUser> queryList(Map<String, Object> map){
		return csExcelUserMapper.queryList(map);
	}

	@Override
	public int queryTotal(Map<String, Object> map){
		return csExcelUserMapper.queryTotal(map);
	}

	@Override
	public int executeSave(CsExcelUser csExcelUser){
		return csExcelUserMapper.executeSave(csExcelUser);
	}

	@Override
	public int executeUpdate(CsExcelUser csExcelUser){
		return csExcelUserMapper.executeUpdate(csExcelUser);
	}

	@Override
	public int executeDelete(String uuid){
		return csExcelUserMapper.executeDelete(uuid);
	}

	@Override
	public int executeDeleteBatch(String[] uuids){
		return csExcelUserMapper.executeDeleteBatch(uuids);
	}

	@Override
	public List<CsExcelUser> getDataByTablesAndField(String tables, String field) {
		return csExcelUserMapper.getDataByTablesAndField(tables,field);
	}

	@Override
	public int batchInsert(List<CsExcelUser> list) {
		return csExcelUserMapper.batchInsert(list);
	}

}
