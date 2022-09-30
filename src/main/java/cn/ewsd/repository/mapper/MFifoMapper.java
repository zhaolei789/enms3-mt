package cn.ewsd.repository.mapper;
import cn.ewsd.repository.model.MFifo;

/**
 * @Author fengjw
 * @Email 11128474@qq.cn
 * @Date 2021-04-25 17:06:36
 */
public interface MFifoMapper extends tk.mybatis.mapper.common.Mapper<MFifo> {
    int insertFifo(MFifo mFifo);
}
