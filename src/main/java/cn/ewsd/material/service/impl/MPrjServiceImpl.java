package cn.ewsd.material.service.impl;

import cn.ewsd.common.utils.easyui.PageParam;
import cn.ewsd.common.utils.easyui.PageSet;
import cn.ewsd.common.utils.easyui.PageUtils;
import cn.ewsd.material.mapper.MPrjMapper;
import cn.ewsd.material.model.MPrj;
import cn.ewsd.material.service.MPrjService;
import cn.ewsd.mdata.mapper.OrganizationMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("mPrjServiceImpl")
public class MPrjServiceImpl extends MaterialBaseServiceImpl<MPrj, String> implements MPrjService {
	@Autowired
	private MPrjMapper mPrjMapper;
	@Autowired
    private OrganizationMapper organizationMapper;

    @Override
    public PageSet<MPrj> getPageSet(PageParam pageParam, String filterSort) {
        PageHelper.startPage(pageParam.getPage(), pageParam.getRows());
        List<MPrj> list = mPrjMapper.getPageSet(filterSort);
        PageInfo<MPrj> pageInfo = new PageInfo<>(list);
        return PageUtils.getPageSet(pageInfo);
    }

    @Override
    public int executeSave(MPrj mPrj) {
        return mPrjMapper.executeSave(mPrj);
    }

    @Override
    public MPrj queryObject(String uuid){
        return mPrjMapper.queryObject(uuid);
    }

    @Override
    public int executeUpdate(MPrj mPrj) {
        return mPrjMapper.executeUpdate(mPrj);
    }

    @Override
    public int executeDelete(String uuid){
        return mPrjMapper.executeDelete(uuid);
    }

    @Override
    public List<MPrj> getUpNameAll(){
        return mPrjMapper.getUpNameAll();
    }

    @Override
    public List<MPrj> getPrjByStatusAndTeamNoAndItemNo(String status, String teamNo, String itemNo){
        return mPrjMapper.getPrjByStatusAndTeamNoAndItemNo(status, teamNo, itemNo);
    }

    @Override
    public MPrj getPrjByPrjNo(String prjNo){
        return  mPrjMapper.getPrjByPrjNo(prjNo);
    }

    @Override
    public List<MPrj> getPrjList(String teamNo, String prjStatus){
        return mPrjMapper.getPrjList(teamNo, prjStatus);
    }

    @Override
    public List<MPrj> getPrjSet(String teamNo, String prjStatus){
        Map map = new HashMap<>();
        map.put("p1", "sys_organization");
        map.put("p2", teamNo);
        map.put("p3", "idStr");
        organizationMapper.getChildIds(map);
        String teamNos = map.get("p3").toString();
        return mPrjMapper.getPrjSet(teamNos, prjStatus);
    }

    @Override
    public List<MPrj> getPrjFromBackQuota(){
        return mPrjMapper.getPrjFromBackQuota();
    }
}
