package cn.ewsd.material.mapper;
import cn.ewsd.material.model.MTeamStockLog;

/**
 * @Author fengjw
 * @Email 11128474@qq.cn
 * @Date 2021-04-23 11:07:12
 */
public interface MTeamStockLogMapper extends tk.mybatis.mapper.common.Mapper<MTeamStockLog> {
    int insertTeamStockLog(MTeamStockLog mTeamStockLog);
}
