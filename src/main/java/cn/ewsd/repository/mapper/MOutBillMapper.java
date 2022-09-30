package cn.ewsd.repository.mapper;
import cn.ewsd.repository.model.MOutBill;

/**
 * @Author fengjw
 * @Email 11128474@qq.cn
 * @Date 2021-04-25 17:06:36
 */
public interface MOutBillMapper extends tk.mybatis.mapper.common.Mapper<MOutBill> {
    int insertOutBill(MOutBill mOutBill);
}
