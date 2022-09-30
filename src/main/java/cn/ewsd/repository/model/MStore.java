package cn.ewsd.repository.model;

import cn.ewsd.common.model.MCoreBase;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;

import javax.persistence.Table;
import java.math.BigDecimal;

/**
 * 
 * 
 * @Author fengjw
 * @Email 11128474@qq.cn
 * @Date 2021-04-25 17:06:36
 */
@Table(name="m_store")
public class MStore extends MCoreBase{
	private static final long serialVersionUID = 1L;
	private String storeNo;
	private String storeName;
	private String upDown;
	private String upDownName;
	private String storeAttr;
	private String storeLevel;
	private String storeLevelName;
	private String bigType;
	private String storeType;
	private String storeTypeName;
	private Integer mngTeam;
	private String mngTeamName;
	private String ifSafe;
	private String ifStock;
	private String ifAssess;
	private String ifControl;
	private String reserve1;
	private String reserve1Name;
	private String reserve2;
	private String reserve3;
	private String reserve4;
	private String reserve5;

	public String getUpDownName() {
		return upDownName;
	}

	public void setUpDownName(String upDownName) {
		this.upDownName = upDownName;
	}

	public String getStoreLevelName() {
		return storeLevelName;
	}

	public void setStoreLevelName(String storeLevelName) {
		this.storeLevelName = storeLevelName;
	}

	public String getStoreTypeName() {
		return storeTypeName;
	}

	public void setStoreTypeName(String storeTypeName) {
		this.storeTypeName = storeTypeName;
	}

	public String getMngTeamName() {
		return mngTeamName;
	}

	public void setMngTeamName(String mngTeamName) {
		this.mngTeamName = mngTeamName;
	}

	public String getReserve1Name() {
		return reserve1Name;
	}

	public void setReserve1Name(String reserve1Name) {
		this.reserve1Name = reserve1Name;
	}

	public String getStoreNo() {
		return storeNo;
	}

	public void setStoreNo(String storeNo) {
		this.storeNo = storeNo;
	}

	public String getStoreName() {
		return storeName;
	}

	public void setStoreName(String storeName) {
		this.storeName = storeName;
	}

	public String getUpDown() {
		return upDown;
	}

	public void setUpDown(String upDown) {
		this.upDown = upDown;
	}

	public String getStoreAttr() {
		return storeAttr;
	}

	public void setStoreAttr(String storeAttr) {
		this.storeAttr = storeAttr;
	}

	public String getStoreLevel() {
		return storeLevel;
	}

	public void setStoreLevel(String storeLevel) {
		this.storeLevel = storeLevel;
	}

	public String getBigType() {
		return bigType;
	}

	public void setBigType(String bigType) {
		this.bigType = bigType;
	}

	public String getStoreType() {
		return storeType;
	}

	public void setStoreType(String storeType) {
		this.storeType = storeType;
	}

	public Integer getMngTeam() {
		return mngTeam;
	}

	public void setMngTeam(Integer mngTeam) {
		this.mngTeam = mngTeam;
	}

	public String getIfSafe() {
		return ifSafe;
	}

	public void setIfSafe(String ifSafe) {
		this.ifSafe = ifSafe;
	}

	public String getIfStock() {
		return ifStock;
	}

	public void setIfStock(String ifStock) {
		this.ifStock = ifStock;
	}

	public String getIfAssess() {
		return ifAssess;
	}

	public void setIfAssess(String ifAssess) {
		this.ifAssess = ifAssess;
	}

	public String getIfControl() {
		return ifControl;
	}

	public void setIfControl(String ifControl) {
		this.ifControl = ifControl;
	}

	public String getReserve1() {
		return reserve1;
	}

	public void setReserve1(String reserve1) {
		this.reserve1 = reserve1;
	}

	public String getReserve2() {
		return reserve2;
	}

	public void setReserve2(String reserve2) {
		this.reserve2 = reserve2;
	}

	public String getReserve3() {
		return reserve3;
	}

	public void setReserve3(String reserve3) {
		this.reserve3 = reserve3;
	}

	public String getReserve4() {
		return reserve4;
	}

	public void setReserve4(String reserve4) {
		this.reserve4 = reserve4;
	}

	public String getReserve5() {
		return reserve5;
	}

	public void setReserve5(String reserve5) {
		this.reserve5 = reserve5;
	}
}