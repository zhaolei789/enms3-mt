package cn.ewsd.repository.mapper;
import cn.ewsd.repository.model.MBulkList;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author fengjw
 * @Email 11128474@qq.cn
 * @Date 2021-04-25 17:06:36
 */
public interface MBulkListMapper extends tk.mybatis.mapper.common.Mapper<MBulkList> {
    int insertBulk(MBulkList mBulkList);

    int deleteBulkByCheckNo(String checkNo);

    List<MBulkList> getBulkList(@Param("checkNo") String checkNo, @Param("matNameQry") String matNameQry, @Param("siteNoQry") String siteNoQry);

    int updateBulk(MBulkList mBulkList);

    MBulkList getBulk(String listId);

    List<MBulkList> getDiffBulkList(String checkNo);

    List<MBulkList> getCheckBulkList(String checkNoQry);

    int deleteBulkByListId(String listId);

    int getBulkCount(String checkNo);

    List<MBulkList> getBulkStock(String checkNo);

    int updBulk(@Param("checkEmp") String checkEmp, @Param("checkNo") String checkNo);

    List<MBulkList> getInventQryList(String checkNo);
}
