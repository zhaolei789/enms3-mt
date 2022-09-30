package cn.ewsd.cost.mapper;
import cn.ewsd.common.mapper.BaseMapper;
import cn.ewsd.cost.model.MWork;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 生产
 * 
 * @Author zxrmine
 * @Email zxrmine@163.cn
 * @Date 2021-09-13 18:44:03
 */
public interface MWorkMapper extends tk.mybatis.mapper.common.Mapper<MWork> {

    //获取分页集
    List<MWork> getPageSet(@Param("filterSort") String filterSort);

    int executeSave(MWork var1);

    int executeSave(Map<String, Object> var1);

    //void executeSaveBatch(List<MWork> var1);

    int executeUpdate(MWork var1);

    int executeUpdate(Map<String, Object> var1);

    int executeDelete(Object var1);

    int executeDelete(Map<String, Object> var1);

    int executeDeleteBatch(Object[] var1);

    MWork queryObject(Object var1);

    List<MWork> queryList(Map<String, Object> var1);

    List<MWork> queryList(Object var1);

    int queryTotal(Map<String, Object> var1);

    int queryTotal();

    List<MWork> getMatAssessOutList(@Param("month1") String month1, @Param("month2") String month2, @Param("teamNo") String teamNo);

    List<MWork> getMatAssZhWorkList(@Param("workMonth") String workMonth, @Param("prjs") String prjs);

    List<MWork> getMatAssDcWorkList(@Param("occMonth") String occMonth, @Param("teamNos") String teamNos);

    List<MWork> getMatAssTyWorkList(String occMonth);

    List<MWork> getRationAnalWorkList();
}
