package cn.ewsd.repository.service;

import cn.ewsd.repository.model.MAddr;
import java.util.List;

/**
 * @Author fengjw
 * @Email 11128474@qq.cn
 * @Date 2021-04-25 17:06:36
 */
public interface MAddrService extends RepositoryBaseService<MAddr, String> {
	List<MAddr> getAddr(String teamNo);
}
