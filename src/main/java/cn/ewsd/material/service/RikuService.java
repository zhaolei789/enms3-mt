package cn.ewsd.material.service;

import cn.ewsd.common.utils.easyui.PageSet;
import cn.ewsd.material.model.MPlan;
import cn.ewsd.material.model.Riku;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;

/**
 * @Author fengjw
 * @Email 11128474@qq.cn
 * @Date 2021-04-23 11:07:12
 */
public interface RikuService extends MaterialBaseService<MPlan, String> {
    PageSet<Riku> getMatList(int page, int rows, String matQry, String abcTypeQry, String startDateQry, String endDateQry, String planTypeQry, String costCenterQry, String monthQry, String ifAqfyQry, String wbsQry);

    int deletePlan(String[] planNos, String userId, String userName, String teamNo);

    int savePlan(ArrayList<MPlan> list, String userId, String userName, String teamNo);

    int finish(HttpServletRequest request, String creatorId, String creator);
}
