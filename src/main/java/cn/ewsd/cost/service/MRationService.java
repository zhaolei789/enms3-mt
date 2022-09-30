package cn.ewsd.cost.service;

import cn.ewsd.common.utils.easyui.PageParam;
import cn.ewsd.common.utils.easyui.PageSet;
import cn.ewsd.cost.model.MRation;
import cn.ewsd.material.model.MMaterial;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 生产
 * 
 * @Author zxrmine
 * @Email zxrmine@163.cn
 * @Date 2021-09-13 18:44:03
 */
public interface MRationService extends CostBaseService<MRation, String> {

    PageSet<MRation> getRationPageSet(PageParam pageParam, String filterSort, String rationQry);

    void insertRation(HttpServletRequest request) throws Exception;

    List<MRation> getRationAnalList(String rationQry);

    MRation getRationByNo(String rationNo);

    void updateRation(MRation mRation) throws Exception;

    void deleteRation(HttpServletRequest request) throws Exception;

    PageSet<MMaterial> getRationMatPageSet(PageParam pageParam, String filterSort, String rationNo, String matQry);

    List<MMaterial> getMatForSelect(String q);

    void insertMat(HttpServletRequest request) throws Exception;

    void deleteMat(HttpServletRequest request) throws Exception;
}
