package cn.ewsd.cost.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import cn.ewsd.common.utils.BaseUtils;
import cn.ewsd.common.utils.easyui.PageParam;
import cn.ewsd.common.utils.easyui.PageSet;
import cn.ewsd.common.utils.easyui.PageUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import cn.ewsd.cost.mapper.FFeeReportMapper;
import cn.ewsd.cost.model.FFeeReport;
import cn.ewsd.cost.service.FFeeReportService;
import cn.ewsd.cost.service.impl.CostBaseServiceImpl;

@Service("fFeeReportServiceImpl")
public class FFeeReportServiceImpl extends CostBaseServiceImpl<FFeeReport, String> implements FFeeReportService {
	@Autowired
	private FFeeReportMapper fFeeReportMapper;

    @Override
    public PageSet<FFeeReport> getPageSet(PageParam pageParam, String filterSort) {
        PageHelper.startPage(pageParam.getPage(), pageParam.getRows());
        List<FFeeReport> list = fFeeReportMapper.getPageSet(filterSort);
        PageInfo<FFeeReport> pageInfo = new PageInfo<>(list);
        return PageUtils.getPageSet(pageInfo);
    }

	@Override
	public FFeeReport queryObject(String uuid){
		return fFeeReportMapper.queryObject(uuid);
	}

	@Override
	public List<FFeeReport> queryList(Map<String, Object> map){
		return fFeeReportMapper.queryList(map);
	}

	@Override
	public int queryTotal(Map<String, Object> map){
		return fFeeReportMapper.queryTotal(map);
	}

	@Override
	public int executeSave(FFeeReport fFeeReport){
		return fFeeReportMapper.executeSave(fFeeReport);
	}

	@Override
	public int executeUpdate(FFeeReport fFeeReport){
		return fFeeReportMapper.executeUpdate(fFeeReport);
	}

	@Override
	public int executeDelete(String uuid){
		return fFeeReportMapper.executeDelete(uuid);
	}

	@Override
	public int executeDeleteBatch(String[] uuids){
		return fFeeReportMapper.executeDeleteBatch(uuids);
	}

}
