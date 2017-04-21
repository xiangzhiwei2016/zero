package com.entity;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Digits;

import org.hibernate.validator.constraints.Length;

/**
 * 深圳证券代码信息
 * <br>创建日期�?2017�?2�?7�? 上午11:31:19
 * <br><b>Copyright 2015 上海量投网络科技有限公司 All Rights Reserved</b>
 * @author 周胜�?
 * @since 1.0
 * @version 1.0
 */
@Entity
@Table(name = "t_sz_securities", uniqueConstraints = { @UniqueConstraint(columnNames = { "security_id"}) })
public class Securities implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private long id;
	
	/**
	 * 证券代码
	 */
	@Column(name = "security_id", length = 10)
	@Length(min = 0, max = 10)
	private String securityID;
	
	/**
	 * 证券来源
	 */
/*	@Column(name = "security_id_source", length = 10)
	@Length(min = 1, max = 10)
	private String securityIDSource;*/
	
	/**
	 * 证券�?�?
	 */
	@Column(name = "symbol", length = 50)
	@Length(min = 0, max = 50)
	private String symbol;
	
	/**
	 * 英文�?�?
	 */
/*	@Column(name = "english_name", length = 50)
	@Length(min = 0, max = 50)
	private String englishName;*/
	
	/**
	 * ISIN代码
	 */
/*	@Column(name = "isin", length = 12)
	@Length(min = 0, max = 12)
	private String isin;*/
	
	/**
	 * 基础证券代码
	 */
/*	@Column(name = "underlying_security_id", length = 10)
	@Length(min = 0, max = 10)
	private String UnderlyingSecurityID;*/
	
	/**
	 * 上市日期
	 */
	@Column(name = "list_date", length = 8)
	@Length(min = 0, max = 8)
	private String listDate;
	
	/**
	 * 证券类别
	 */
	@Column(name = "security_type", length = 8)
	@Length(min = 0, max = 8)
	private String securityType;
	
	/**
	 * 货币种类
	 */
	@Column(name = "currency", length = 8)
	@Length(min = 0, max = 8)
	private String currency;
	
	/**
	 * 数量单位
	 */
	@Column(name = "qty_unit", precision = 17, scale = 2)
	@Digits(integer = 15, fraction = 2)
	private BigDecimal qtyUnit;
	
	/**
	 * 是否支持当日回转交易
	 */
	@Column(name = "day_trading", length = 1)
	@Length(min = 0, max = 1)
	private String dayTrading;
	
	/**
	 * 昨日收盘�?
	 */
	@Column(name = "prev_close_px", precision = 22, scale = 8)
	@Digits(integer = 14, fraction =8)
	private BigDecimal prevClosePx;
	
	
	/**
	 * 总发行量
	 */
	@Column(name = "outstanding_share", precision = 22, scale = 2)
	@Digits(integer = 20, fraction =2)
	private BigDecimal outstandingShare;
	
	/**
	 * 流�?�股�?
	 */
	/*@Column(name = "public_float_share_quantity", precision = 22, scale = 2)
	@Digits(integer = 20, fraction =2)
	private BigDecimal publicFloatShareQuantity;*/
	
	/**
	 * 面�??
	 */
	@Column(name = "par_value", precision = 22, scale = 8)
	@Digits(integer = 14, fraction =8)
	private BigDecimal parValue;
	
	/**
	 * 是否可作为融资融券可充抵保证金券
	 */
	/*@Column(name = "gage_flag", length = 1)
	@Length(min = 0, max = 1)
	private String gageFlag;*/
	
	/**
	 * 可充抵保证金折算�?
	 */
	/*@Column(name = "gage_ratio", precision = 22, scale = 8)
	@Digits(integer = 14, fraction =8)
	private BigDecimal gageRatio;*/
	
	/**
	 * 是否为融资标�?
	 */
	@Column(name = "crd_buy_underlying", length = 1)
	@Length(min = 0, max = 1)
	private String crdBuyUnderlying;
	
	/**
	 * 是否为融券标�?
	 */
	@Column(name = "crd_sell_underlying", length = 1)
	@Length(min = 0, max = 1)
	private String crdSellUnderlying;
	
	/**
	 * 提价�?查方�?
	 */
	@Column(name = "price_check_mode", length = 2)
	@Length(min = 0, max = 2)
	private String priceCheckMode;
	
	/**
	 * 是否可质押入�?
	 */
	@Column(name = "pledge_flag", length = 1)
	@Length(min = 0, max = 1)
	private String pledgeFlag;
	
	/**
	 * 对回购标准券折算�?
	 */
	@Column(name = "contract_multiplier", precision = 22, scale = 8)
	@Digits(integer = 14, fraction =8)
	private BigDecimal contractMultiplier;
	
	/**
	 * 对应回购标准�?
	 */
	@Column(name = "regular_share", length = 10)
	@Length(min = 0, max = 10)
	private String regularShare;
	
	/**
	 * T-1 日基金净�?
	 */
	@Column(name = "nav", precision = 22, scale = 8)
	@Digits(integer = 14, fraction = 8)
	private BigDecimal nav;
	
	/**
	 * 每百元应计利�?
	 */
	@Column(name = "interest", precision = 22, scale = 8)
	@Digits(integer = 14, fraction = 8)
	private BigDecimal interest;
	
	/**
	 * 投资者�?�当性管理标�?
	 */
	@Column(name = "qualification_flag", length = 1)
	@Length(min = 0, max = 1)
	private String qualificationFlag;
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getSecurityID() {
		return securityID;
	}

	public void setSecurityID(String securityID) {
		this.securityID = securityID;
	}

	public String getSymbol() {
		return symbol;
	}

	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}

	public String getListDate() {
		return listDate;
	}

	public void setListDate(String listDate) {
		this.listDate = listDate;
	}

	public String getSecurityType() {
		return securityType;
	}

	public void setSecurityType(String securityType) {
		this.securityType = securityType;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public BigDecimal getQtyUnit() {
		return qtyUnit;
	}

	public void setQtyUnit(BigDecimal qtyUnit) {
		this.qtyUnit = qtyUnit;
	}

	public String getDayTrading() {
		return dayTrading;
	}

	public void setDayTrading(String dayTrading) {
		this.dayTrading = dayTrading;
	}

	public BigDecimal getPrevClosePx() {
		return prevClosePx;
	}

	public void setPrevClosePx(BigDecimal prevClosePx) {
		this.prevClosePx = prevClosePx;
	}

	public BigDecimal getOutstandingShare() {
		return outstandingShare;
	}

	public void setOutstandingShare(BigDecimal outstandingShare) {
		this.outstandingShare = outstandingShare;
	}

	public BigDecimal getParValue() {
		return parValue;
	}

	public void setParValue(BigDecimal parValue) {
		this.parValue = parValue;
	}

	public String getCrdBuyUnderlying() {
		return crdBuyUnderlying;
	}

	public void setCrdBuyUnderlying(String crdBuyUnderlying) {
		this.crdBuyUnderlying = crdBuyUnderlying;
	}

	public String getCrdSellUnderlying() {
		return crdSellUnderlying;
	}

	public void setCrdSellUnderlying(String crdSellUnderlying) {
		this.crdSellUnderlying = crdSellUnderlying;
	}

	public String getPriceCheckMode() {
		return priceCheckMode;
	}

	public void setPriceCheckMode(String priceCheckMode) {
		this.priceCheckMode = priceCheckMode;
	}

	public String getPledgeFlag() {
		return pledgeFlag;
	}

	public void setPledgeFlag(String pledgeFlag) {
		this.pledgeFlag = pledgeFlag;
	}

	public BigDecimal getContractMultiplier() {
		return contractMultiplier;
	}

	public void setContractMultiplier(BigDecimal contractMultiplier) {
		this.contractMultiplier = contractMultiplier;
	}

	public String getRegularShare() {
		return regularShare;
	}

	public void setRegularShare(String regularShare) {
		this.regularShare = regularShare;
	}

	public BigDecimal getNav() {
		return nav;
	}

	public void setNav(BigDecimal nav) {
		this.nav = nav;
	}

	public BigDecimal getInterest() {
		return interest;
	}

	public void setInterest(BigDecimal interest) {
		this.interest = interest;
	}

	public String getQualificationFlag() {
		return qualificationFlag;
	}

	public void setQualificationFlag(String qualificationFlag) {
		this.qualificationFlag = qualificationFlag;
	}
	
	
}
