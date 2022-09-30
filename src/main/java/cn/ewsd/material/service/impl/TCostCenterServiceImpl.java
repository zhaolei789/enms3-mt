package cn.ewsd.material.service.impl;

import cn.ewsd.common.utils.easyui.PageParam;
import cn.ewsd.common.utils.easyui.PageSet;
import cn.ewsd.common.utils.easyui.PageUtils;
import cn.ewsd.material.mapper.TCostCenterMapper;
import cn.ewsd.material.model.TCostCenter;
import cn.ewsd.material.service.TCostCenterService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("tCostCenterServiceImpl")
public class TCostCenterServiceImpl extends MaterialBaseServiceImpl<TCostCenter, String> implements TCostCenterService {
	@Autowired
	private TCostCenterMapper tCostCenterMapper;

    @Override
    public PageSet<TCostCenter> getPageSet(PageParam pageParam, String filterSort) {
        PageHelper.startPage(pageParam.getPage(), pageParam.getRows());
        List<TCostCenter> list = tCostCenterMapper.getPageSet(filterSort);
        PageInfo<TCostCenter> pageInfo = new PageInfo<>(list);
        return PageUtils.getPageSet(pageInfo);
    }

    @Override
    public int checkCostCenterExistByNoOrName(String centerNo, String centerName){
        return tCostCenterMapper.checkCostCenterExistByNoOrName(centerNo, centerName);
    }

    @Transactional
    @Override
    public int executeSave(TCostCenter tCostCenter, String[] deptNos) {
        int res = tCostCenterMapper.executeSave(tCostCenter);

        for(int i=0; i<deptNos.length; i++){
            tCostCenterMapper.saveCenterTeam(tCostCenter.getCenterNo(), deptNos[i]);
        }

        return res;
    }

    @Override
    public TCostCenter queryObject(String centerNo){
        return tCostCenterMapper.queryObject(centerNo);
    }

    @Transactional
    @Override
    public int executeUpdate(TCostCenter tCostCenter, String[] deptNos) {
        int res = tCostCenterMapper.executeUpdate(tCostCenter);

        tCostCenterMapper.deleteCenterTeam(tCostCenter.getCenterNo(), null);

        for(int i=0; i<deptNos.length; i++){
            tCostCenterMapper.saveCenterTeam(tCostCenter.getCenterNo(), deptNos[i]);
        }

        return res;
    }

    @Transactional
    @Override
    public int executeDelete(String centerNo, String deptNo){
        tCostCenterMapper.deleteCenterTeam(centerNo, deptNo);
        return tCostCenterMapper.executeDelete(centerNo);
    }

    @Override
    public List<TCostCenter> getTeamCenter(String teamNo){
        return tCostCenterMapper.getTeamCenter(teamNo);
    }

    @Override
    public String getMoveType(String centerNo, String wbsElement){
        String moveType = tCostCenterMapper.getMoveType(centerNo);
        if(!"".equals(wbsElement)){
            moveType = "m.erpItem.3";
        }
        return moveType;
    }

    @Override
    public List<TCostCenter> getCenterSet(String userDeptIds){
        return tCostCenterMapper.getCenterSet(userDeptIds);
    }

    @Override
    public List<TCostCenter> getCenterList(){
        return tCostCenterMapper.getCenterList();
    }
}
