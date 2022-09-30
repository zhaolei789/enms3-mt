package cn.ewsd.material.service.impl;

import cn.hutool.core.util.StrUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import cn.ewsd.common.utils.easyui.PageParam;
import cn.ewsd.common.utils.easyui.PageSet;
import cn.ewsd.common.utils.easyui.PageUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import cn.ewsd.material.mapper.MItemMapper;
import cn.ewsd.material.model.MItem;
import cn.ewsd.material.service.MItemService;

@Service("mItemServiceImpl")
public class MItemServiceImpl extends MaterialBaseServiceImpl<MItem, String> implements MItemService {
	@Autowired
	private MItemMapper mItemMapper;

    @Override
    public PageSet<MItem> getPageSet(PageParam pageParam, String filterSort) {
        PageHelper.startPage(pageParam.getPage(), pageParam.getRows());
        List<MItem> list = mItemMapper.getPageSet(filterSort);
        PageInfo<MItem> pageInfo = new PageInfo<>(list);
        return PageUtils.getPageSet(pageInfo);
    }

	@Override
	public MItem queryObject(String itemNo){
		return mItemMapper.queryObject(itemNo);
	}

	@Override
	public int executeSave(MItem mItem){
		return mItemMapper.executeSave(mItem);
	}

	@Override
	public int executeUpdate(MItem mItem){
		return mItemMapper.executeUpdate(mItem);
	}

	@Override
	public int executeDeleteBatch(String[] uuid){
		return mItemMapper.executeDeleteBatch(uuid);
	}

	@Override
	public MItem getItemByItemNoAndNotByUuid(String itemNo, String uuid){
    	return mItemMapper.getItemByItemNoAndNotByUuid(itemNo, uuid);
	}

	@Override
	public List<MItem> getMatItemSet(boolean isMyItem, String userNo){
    	return mItemMapper.getMatItemSet(isMyItem, userNo);
	}

	@Override
	public List<MItem> getItemSet(String prjNo){
		return mItemMapper.getItemSet(prjNo);
	}

	@Override
	public String getPayTeam(String prjNo, String itemNo, String teamNo){
    	if(StrUtil.hasBlank(itemNo)){
    		return teamNo;
		}
    	MItem mItem = mItemMapper.queryObject(itemNo);
    	String payType = mItem.getPayType();
    	String payTeam = mItem.getPayTeam()==null ? "" : mItem.getPayTeam()+"";

    	if("m.payType.1".equals(payType)){
    		return teamNo;
		}else if("m.payType.3".equals(payType)){
    		return "20";
		}else if("m.payType.2".equals(payType)){
			return payTeam;
		}else {
    		return "";
		}
	}

	@Override
	public List<MItem> getItemList(String prjNo, String planMonth, String planType, String planStep){
    	return mItemMapper.getItemList(prjNo, planMonth, planType, planStep);
	}

	@Override
	public List<MItem> getItemList1(String teamNo, String fMonth){
    	return mItemMapper.getItemList1(teamNo, fMonth);
	}
}
