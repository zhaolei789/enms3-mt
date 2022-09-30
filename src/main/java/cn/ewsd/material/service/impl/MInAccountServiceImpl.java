package cn.ewsd.material.service.impl;

import cn.ewsd.base.utils.Data;
import cn.ewsd.base.utils.Snow;
import cn.ewsd.common.utils.BaseUtils;
import cn.ewsd.common.utils.easyui.PageParam;
import cn.ewsd.common.utils.easyui.PageSet;
import cn.ewsd.common.utils.easyui.PageUtils;
import cn.ewsd.material.mapper.MInAccountMapper;
import cn.ewsd.material.model.MInAccount;
import cn.ewsd.material.service.MInAccountService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("mInAccountServiceImpl")
public class MInAccountServiceImpl extends MaterialBaseServiceImpl<MInAccount, String> implements MInAccountService {
	@Autowired
	private MInAccountMapper mInAccountMapper;

    @Override
    public PageSet<MInAccount> getPageSet(PageParam pageParam, String filterSort, String yearQry, String monQry, String numberQry, String orderQry) {
        PageHelper.startPage(pageParam.getPage(), pageParam.getRows());
        List<MInAccount> list = mInAccountMapper.getInAccountList(yearQry, monQry, numberQry, orderQry);
        PageInfo<MInAccount> pageInfo = new PageInfo<>(list);
        return PageUtils.getPageSet(pageInfo);
    }

    @Transactional
    @Override
    public int importInAccount(List<Map<String, String>> dataList, String creatorId, String creator, String creatorOrgId){
        int ret = 0;

        try{
            for(int i=0; i<dataList.size(); i++){
                HashMap<String, String> map = (HashMap)dataList.get(i);
                String orderNo = map.get("订单");
                if("".equals(orderNo)){
                    continue;
                }
                String factAmount = map.get("实发数量");
                String dfAmount = "".equals(factAmount) ? "0" : Data.finalToNormal(factAmount);
                String factCheck = map.get("实验数量");
                String dcAmount = "".equals(factCheck) ? "0" : Data.finalToNormal(factCheck);
                String noTaxPrice = map.get("进货单价");
                String ntPrice = "".equals(noTaxPrice) ? "0" : Data.finalToNormal(noTaxPrice);
                String noTaxBala = map.get("金额");
                String ntBala = "".equals(noTaxBala) ? "0" : Data.finalToNormal(noTaxBala);
                String taxRate = map.get("税率");
                String tRate = "".equals(factAmount) ? "0" : taxRate.substring(0, taxRate.length()-1);
                String taxPrice = map.get("含税单价");
                String tPrice = "".equals(taxPrice) ? "0" : Data.finalToNormal(taxPrice);
                String taxBala = map.get("价税金额");
                String tBala = "".equals(taxBala) ? "0" : Data.finalToNormal(taxBala);
                String billNumber = map.get("发票号");
                String occDate = map.get("日期");

                MInAccount mInAccount = new MInAccount();
                mInAccount.setAccountId(Snow.getUUID()+"");
                mInAccount.setBillNo("0");
                mInAccount.setOfferNo("");
                mInAccount.setOrderNo(orderNo);
                mInAccount.setOccDate(occDate);
                mInAccount.setBillNumber(billNumber);
                mInAccount.setOutAmount(new BigDecimal(dfAmount+""));
                mInAccount.setRealAmount(new BigDecimal(dcAmount+""));
                mInAccount.setSetPrice(new BigDecimal(ntPrice+""));
                mInAccount.setInBala(new BigDecimal(ntBala+""));
                mInAccount.setTaxRate(new BigDecimal(tRate+""));
                mInAccount.setTaxPrice(new BigDecimal(tPrice+""));
                mInAccount.setSetBala(new BigDecimal(tBala+""));
                mInAccount.setRemark("");
                mInAccount.setUuid(BaseUtils.UUIDGenerator());
                mInAccount.setCreator(creator);
                mInAccount.setCreatorId(creatorId);
                mInAccount.setCreatorOrgId(Integer.parseInt(creatorOrgId));
                mInAccount.setCreateTime(new Date());
                mInAccountMapper.insertInAccount(mInAccount);

                ret++;
            }
        }catch (Exception e){
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            throw e;
        }

        return ret;
    }

    @Override
    public List<MInAccount> getList(String yearQry, String monQry, String numberQry, String orderQry){
        return mInAccountMapper.getInAccountList(yearQry, monQry, numberQry, orderQry);
    }

    @Override
    @Transactional
    public int deleteBatch(String[] uuids){
        int ret=0;

        try{
            for(int i=0; i<uuids.length; i++){
                String uuid = uuids[i];

                mInAccountMapper.deleteByUuid(uuid);

                ret++;
            }
        }catch (Exception e){
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            throw e;
        }

        return ret;
    }
}
