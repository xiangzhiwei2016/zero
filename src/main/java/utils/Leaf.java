
package utils;
/**
 *   <br>
 * 创建日期：2015年11月25日 下午3:10:45 <br>
 * <b>Copyright 2015 上海量投网络科技有限公司 All Rights Reserved</b>
 * 
 * @author 李会军
 * @since 1.0
 * @version 1.0
 */
public class Leaf
{
	private String xpath;
	private String value;
	public Leaf(String xpath, String value)
	{
		super();
		this.xpath = xpath;
		this.value = value;
	}
	public String getXpath()
	{
		return xpath;
	}
	public void setXpath(String xpath)
	{
		this.xpath = xpath;
	}
	public String getValue()
	{
		return value;
	}
	public void setValue(String value)
	{
		this.value = value;
	}
	}
