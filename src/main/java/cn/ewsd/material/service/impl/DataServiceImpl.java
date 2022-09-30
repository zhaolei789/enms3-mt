package cn.ewsd.material.service.impl;

import cn.ewsd.base.utils.Snow;
import cn.ewsd.base.utils.XDate;
import cn.ewsd.base.utils.XException;
import cn.ewsd.base.utils.jwt.UserInfo;
import cn.ewsd.common.utils.BaseUtils;
import cn.ewsd.common.utils.easyui.PageParam;
import cn.ewsd.common.utils.easyui.PageSet;
import cn.ewsd.common.utils.easyui.PageUtils;
import cn.ewsd.material.mapper.DataMapper;
import cn.ewsd.material.mapper.MErpMaterialMapper;
import cn.ewsd.material.mapper.MMakeMatDeptMapper;
import cn.ewsd.material.mapper.MMaterialMapper;
import cn.ewsd.material.model.*;
import cn.ewsd.material.service.DataService;
import cn.ewsd.material.service.MMaterialService;
import cn.ewsd.mdata.mapper.UtilMapper;
import cn.ewsd.repository.mapper.MOutMapper;
import cn.ewsd.repository.model.MOut;
import cn.ewsd.system.model.DicItem;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service("dataServiceImpl")
public class DataServiceImpl extends MaterialBaseServiceImpl<MSettleItem, String> implements DataService {
	@Autowired
	private DataMapper dataMapper;

	@Override
	public List<MSettleItem> getData(String userTeam, String occMonth) {
		return dataMapper.getList(userTeam, occMonth);
	}

	@Override
	public List<MSettleItem> getItem(String userTeam) {
		return dataMapper.getItem(userTeam);
	}

	@Override
	public void save(HttpServletRequest request, UserInfo userInfo) throws Exception{
		String itemNo = request.getParameter("itemNo");
		String dataType = request.getParameter("dataType");
		String year = request.getParameter("year");
		String month = request.getParameter("month");
		double occValue = 0;
		if("".equals(itemNo)){
			throw new XException(XException.ERR_DEFAULT, "请选择费用科目！");
		}
		if("".equals(dataType)){
			throw new XException(XException.ERR_DEFAULT, "请选择数据类型！");
		}
		try{
			occValue = Double.parseDouble(request.getParameter("occValue"));
		}catch (Exception e){
			throw new XException(XException.ERR_DEFAULT, "发生值必须是数字！");
		}
		if(occValue<=0){
			throw new XException(XException.ERR_DEFAULT, "发生值必须是大于0的数字！");
		}

		MSettleItem settleItem = new MSettleItem();
		settleItem.setItemNo(itemNo);
		settleItem.setDataType(dataType);
		settleItem.setOccMonth(year+month);
		settleItem.setOccValue(new BigDecimal(occValue));
		int c = dataMapper.updateSettleValue(settleItem);

		if(c < 1){
			settleItem.setDataId(Snow.getUUID()+"");
			dataMapper.insertSettleValue(settleItem);
		}
	}

	@Override
	public void saveData(HttpServletRequest request, UserInfo userInfo) throws Exception{
		String itemNo = request.getParameter("itemNo");
		String year = request.getParameter("yearQry");
		String month = request.getParameter("monthQry");
		String occMonth = year + month;
		double occValue = 0;
		if("".equals(itemNo)){
			throw new XException(XException.ERR_DEFAULT, "请选择费用科目！");
		}

		int lastDay = Integer.parseInt(XDate.getLastDayOfMonth(occMonth+"01").substring(6, 8));

		for(int i=1; i<=lastDay; i++){
			String currentDay = i < 10 ? "0"+i : i+"";
			double jh = 0;
			double sj = 0;
			try{
				jh = Double.parseDouble(request.getParameter("jh_"+currentDay));
			}catch (Exception e){}
			try{
				sj = Double.parseDouble(request.getParameter("sj_"+currentDay));
			}catch (Exception e){}
System.out.println(jh);
System.out.println(sj);
			MSettleItem settleItem = new MSettleItem();
			settleItem.setItemNo(itemNo);
			settleItem.setDataType("JH");
			settleItem.setOccMonth(year+month);
			settleItem.setOccValue(new BigDecimal(jh));
			settleItem.setOccDay(currentDay);
			int c = dataMapper.updateDayData(settleItem);

			if(c < 1){
				settleItem.setDataId(Snow.getUUID()+"");
				dataMapper.insertDayData(settleItem);
			}

			MSettleItem settleItem1 = new MSettleItem();
			settleItem1.setItemNo(itemNo);
			settleItem1.setDataType("SJ");
			settleItem1.setOccMonth(year+month);
			settleItem1.setOccValue(new BigDecimal(sj));
			settleItem1.setOccDay(currentDay);
			int c1 = dataMapper.updateDayData(settleItem1);

			if(c1 < 1){
				settleItem1.setDataId(Snow.getUUID()+"");
				dataMapper.insertDayData(settleItem1);
			}
		}
	}

	@Override
	public List<MSettleItem> getDayList(String occMonth, String itemNo){
		return dataMapper.getDayList(occMonth, itemNo);
	}
}
