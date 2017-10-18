package utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

/**
 * 
 * 
 * <br>
 * 创建日期：2015年11月25日 下午3:15:29 <br>
 * <b>Copyright 2015 上海量投网络科技有限公司 All Rights Reserved</b>
 * 
 * @author 李会军
 * @since 1.0
 * @version 1.0
 */
public class ParserXML {
	// private static Map xmlmap = new HashMap();
	// 存储xml元素信息的容器
	private List<Leaf> elemList = new ArrayList<Leaf>();

	public Map<String, String> map = new HashMap<String, String>();

	// 要测试的xml对象
	private String srcXml = "";

	public void setSrcXml(String srcXml) {
		this.srcXml = srcXml;
	}

	public Element getRootElement() throws DocumentException {
		Document srcdoc = DocumentHelper.parseText(srcXml);
		Element elem = srcdoc.getRootElement();
		return elem;
	}

	@SuppressWarnings("rawtypes")
	public void getElementList(Element element) {
		List elements = element.elements();
		if (elements.size() == 0) {
			// 没有子元素
			String xpath = element.getPath();
			String value = element.getText();

			// 增加属性节点值获取lihj
			int attrs = element.attributeCount();
			if (attrs > 0) {
				for (int i = 0; i < attrs; i++) {
					Attribute attribute = element.attribute(i);
					String attPath = attribute.getPath();
					String attValue = attribute.getText();
					elemList.add(new Leaf(attPath, StringUtils.trimToEmpty(attValue)));
					map.put(attPath, StringUtils.trimToEmpty(attValue));
				}
			}
			elemList.add(new Leaf(xpath, StringUtils.trimToEmpty(value)));
			map.put(xpath, StringUtils.trimToEmpty(value));
		} else {
			// 有子元素
			for (Iterator it = elements.iterator(); it.hasNext();) {
				Element elem = (Element) it.next();
				// 递归遍历
				getElementList(elem);
			}
		}
	}

	@SuppressWarnings("rawtypes")
	public String getListString(List elemList) {
		StringBuffer sb = new StringBuffer();
		for (Iterator it = elemList.iterator(); it.hasNext();) {
			Leaf leaf = (Leaf) it.next();
			sb.append(leaf.getXpath()).append(" = ").append(leaf.getValue()).append("\n");
		}
		return sb.toString();
	}

	public static void main(String[] args) throws DocumentException {

		String xml = "<?xml version=\"1.0\" encoding=\"GB2312\"?><body><head><trade_no>0020</trade_no><return_code>9999</return_code></head><content><bank_order_no>  52309115</bank_order_no><center_order_no>          </center_order_no><trade_date>20131101</trade_date><trade_time>154222</trade_time><asso_no>              222222</asso_no><bank_acct_no>             1103034019200011816</bank_acct_no><fee>00000000001000000</fee><check_user_no>         7</check_user_no><in_date>20131101</in_date><in_time>160709</in_time><remark>                                                                                </remark><validate_code>29bc26180bc5b551a7e49d13d5ad0c9c</validate_code></content></body>";

		ParserXML test = new ParserXML();
		test.setSrcXml(xml.trim());
		Element root = test.getRootElement();
		test.getElementList(root);
		String x = test.getListString(test.elemList);
		System.out.println("-----------原xml内容------------");
		System.out.println(test.srcXml);
		System.out.println("-----------解析结果------------");
		System.out.println(x);

		System.out.println(test.map.get("/body/head/trade_no"));

	}
}
