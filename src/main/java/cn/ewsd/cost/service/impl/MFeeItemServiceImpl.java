package cn.ewsd.cost.service.impl;

import cn.ewsd.base.utils.Data;
import cn.ewsd.base.utils.Snow;
import cn.ewsd.base.utils.XException;
import cn.ewsd.base.utils.jwt.LoginInfo;
import cn.ewsd.common.utils.easyui.PageParam;
import cn.ewsd.common.utils.easyui.PageSet;
import cn.ewsd.common.utils.easyui.PageUtils;
import cn.ewsd.cost.mapper.FAwardMapper;
import cn.ewsd.cost.mapper.MFeeItemMapper;
import cn.ewsd.cost.model.FAward;
import cn.ewsd.cost.model.MFeeItem;
import cn.ewsd.cost.service.FAwardService;
import cn.ewsd.cost.service.MFeeItemService;
import cn.ewsd.mdata.service.UtilService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service("mFeeItemServiceImpl")
public class MFeeItemServiceImpl extends CostBaseServiceImpl<MFeeItem, String> implements MFeeItemService {
	@Autowired
	private MFeeItemMapper mFeeItemMapper;
    @Autowired
    private UtilService utilService;

    @Override
    public List<MFeeItem> getItemForSelect(String itemType, String ifUse, String teamNo) {
        return mFeeItemMapper.getItemForSelect(itemType, ifUse, teamNo);
    }

    @Override
    public PageSet<MFeeItem> getFeeItemPageSet(PageParam pageParam, String filterSort, String itemType, String ifUse, String mngDept, String teamNo) {
        PageHelper.startPage(pageParam.getPage(), pageParam.getRows());
        List<MFeeItem> list = mFeeItemMapper.getFeeItemList(itemType, ifUse, mngDept, teamNo);
        PageInfo<MFeeItem> pageInfo = new PageInfo<>(list);
        return PageUtils.getPageSet(pageInfo);
    }

    @Override
    public void insertFeeItem(MFeeItem mFeeItem) throws Exception{
        if(mFeeItem.getAssPrice()==null) mFeeItem.setAssPrice(new BigDecimal(1));
        if(mFeeItem.getAssRatio()==null) mFeeItem.setAssRatio(new BigDecimal(1));
        if(mFeeItem.getAwardRatio()==null) mFeeItem.setAwardRatio(new BigDecimal(0));
        if(mFeeItem.getPunishRatio()==null) mFeeItem.setPunishRatio(new BigDecimal(0));

        if (mFeeItem.getAssRatio().compareTo(new BigDecimal(0)) == 0) {
            mFeeItem.setAssRatio(new BigDecimal(1));
        }
        if (Data.formatDouble(mFeeItem.getAssRatio().doubleValue(), 4).length() > 9) {
            throw new XException(XException.ERR_DEFAULT, "考核系数超出长度！");
        }
        if (Data.formatDouble(mFeeItem.getAwardRatio().doubleValue(), 4).length() > 9) {
            throw new XException(XException.ERR_DEFAULT, "奖励系数超出长度！");
        }
        if (Data.formatDouble(mFeeItem.getPunishRatio().doubleValue(), 4).length() > 9) {
            throw new XException(XException.ERR_DEFAULT, "惩罚系数超出长度！");
        }

        mFeeItem.setItemNo(Snow.getUUID()+"");
        mFeeItem.setItemType("m.itemType.42");
        mFeeItem.setIfUse("1");
        mFeeItem.setMngDept(Integer.parseInt(LoginInfo.getOrgId()));
        if(mFeeItem.getOrderNo()==null){
            mFeeItem.setOrderNo(0);
        }

        mFeeItemMapper.insertFeeItem(mFeeItem);
    }

    @Override
    public MFeeItem getFeeItemByItemNo(String itemNo){
        return mFeeItemMapper.getFeeItemByItemNo(itemNo);
    }

    @Override
    public void updateFeeItem(MFeeItem mFeeItem) throws Exception{
        if(mFeeItem.getAssPrice()==null) mFeeItem.setAssPrice(new BigDecimal(1));
        if(mFeeItem.getAssRatio()==null) mFeeItem.setAssRatio(new BigDecimal(1));
        if(mFeeItem.getAwardRatio()==null) mFeeItem.setAwardRatio(new BigDecimal(0));
        if(mFeeItem.getPunishRatio()==null) mFeeItem.setPunishRatio(new BigDecimal(0));

        if (mFeeItem.getAssRatio().compareTo(new BigDecimal(0)) == 0) {
            mFeeItem.setAssRatio(new BigDecimal(1));
        }
        if (Data.formatDouble(mFeeItem.getAssRatio().doubleValue(), 4).length() > 9) {
            throw new XException(XException.ERR_DEFAULT, "考核系数超出长度！");
        }
        if (Data.formatDouble(mFeeItem.getAwardRatio().doubleValue(), 4).length() > 9) {
            throw new XException(XException.ERR_DEFAULT, "奖励系数超出长度！");
        }
        if (Data.formatDouble(mFeeItem.getPunishRatio().doubleValue(), 4).length() > 9) {
            throw new XException(XException.ERR_DEFAULT, "惩罚系数超出长度！");
        }

        if(mFeeItem.getOrderNo()==null){
            mFeeItem.setOrderNo(0);
        }

        mFeeItemMapper.updateFeeItem(mFeeItem);
    }

    @Override
    @Transactional
    public void deleteFeeItem(String[] itemNos) throws Exception{
        try{
            for(int i=0; i<itemNos.length; i++){
                String id = itemNos[i];

                ArrayList<String[]> condList = new ArrayList<>();
                condList.add(new String[]{"item_no", "=", id});
                boolean flag = utilService.checkColumnDataExist("m_assess_norm", condList);
                if(flag == true){
                    throw new XException(XException.ERR_DEFAULT, "无法删除！该指标定额正在使用！");
                }
                flag = utilService.checkColumnDataExist("m_out_assess", condList);
                if(flag == true){
                    throw new XException(XException.ERR_DEFAULT, "无法删除！该指标出账正在使用！");
                }

                mFeeItemMapper.deleteFeeItem(id);
            }
        }catch (Exception e){
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            throw e;
        }
    }

    @Override
    public List<MFeeItem> getTeamItemQuotaItem(String teamNo, String mngTeam){
        return mFeeItemMapper.getTeamItemQuotaItem(teamNo, mngTeam);
    }
}
