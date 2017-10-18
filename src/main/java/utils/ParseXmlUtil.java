package utils;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.Namespace;
import org.dom4j.QName;
import org.jdom.input.SAXBuilder;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;


/**
 * 解析xml工具类
 * <br>创建日期：2017年1月19日 下午3:17:06
 * <br><b>Copyright 2015 上海量投网络科技有限公司 All Rights Reserved</b>
 * @author 周胜兵
 * @since 1.0
 * @version 1.0
 */
public class ParseXmlUtil {
	
	public static Logger logger = Logger.getLogger(ParseXmlUtil.class); 
	
//	@Autowired(required = true)
//	private FrameworkJsonService frameworkJsonService;
	  
     public static void main(String[] args) throws ClassNotFoundException, InstantiationException, IllegalAccessException, NoSuchFieldException, SecurityException {   
             // 获取一个xml文件   
//             String textFromFile = MyXmlUtil.XmlToString();
    	 
    	 String textFromFile = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
									+"<Task xmlns:namespace-prefix=\"namespaceURI\">    "
									+"	<FileType>xml</FileType>            "
									+"	<SubTasks>                                    "
									+"		<SubTask>                                 "
									+"			<Table>com.quantdo.datahub.securities.entity.SecurityCategory</Table>   "
									+"			<Columns>                             "
									+"				<Column>                          "
									+"					<SourceName>SecurityID</SourceName>     "
									+"					<TargetName>exchID</TargetName>     "
									+"					<DataType>String</DataType>         "
									+"				</Column>                         "
									+"				<Column>                          "
									+"					<SourceName>SecurityIDSource</SourceName>     "
									+"					<TargetName>categoryID</TargetName>     "
									+"					<DataType>String</DataType>         "
									+"				</Column>                         "
									+"				<Column>                          "
									+"					<SourceName>SecurityID</SourceName>     "
									+"					<TargetName>exchID</TargetName>     "
									+"					<DataType>String</DataType>         "
									+"				</Column>                         "
									+"				<Column>                          "
									+"					<SourceName>SecurityIDSource</SourceName>     "
									+"					<TargetName>categoryID</TargetName>     "
									+"					<DataType>String</DataType>         "
									+"				</Column>                         "
									+"			</Columns>                            "
									+"		</SubTask>                                "
									+"	</SubTasks>                                   "
									+"</Task>                                         ";
    	 
    	 
    	 String sourceXml = "<Securities xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">			"
    			 +"	<Security>                                                             "
    			 +"	<SecurityID>000001  </SecurityID>                                      "
    			 +"	<SecurityIDSource>102</SecurityIDSource>                               "
    			 +"	<Symbol>平安银行                                    </Symbol>          "
    			 +"	<EnglishName>PAB                                     </EnglishName>    "
    			 +"	<ISIN>            </ISIN>                                              "
    			 +"	<UnderlyingSecurityID>000001  </UnderlyingSecurityID>                  "
    			 +"	<UnderlyingSecurityIDSource>102</UnderlyingSecurityIDSource>           "
    			 +"	<ListDate>19910606</ListDate>                                          "
    			 +"	<SecurityType>1</SecurityType>                                         "
    			 +"	<Currency>CNY </Currency>                                              "
    			 +"	<QtyUnit>1.00</QtyUnit>                                                "
    			 +"	<DayTrading>N</DayTrading>                                             "
    			 +"	<PrevClosePx>9.1600</PrevClosePx>                                      "
    			 +"	<SecurityStatus/>                                                      "
    			 +"	<OutstandingShare>17170411366.00</OutstandingShare>                    "
    			 +"	<PublicFloatShareQuantity>14623201251.00</PublicFloatShareQuantity>    "
    			 +"	<ParValue>1.0000</ParValue>                                            "
    			 +"	<GageFlag>Y</GageFlag>                                                 "
    			 +"	<GageRatio>70.00</GageRatio>                                           "
    			 +"	<CrdBuyUnderlying>Y</CrdBuyUnderlying>                                 "
    			 +"	<CrdSellUnderlying>Y</CrdSellUnderlying>                               "
    			 +"	<PriceCheckMode>1</PriceCheckMode>                                     "
    			 +"	<PledgeFlag>N</PledgeFlag>                                             "
    			 +"	<ContractMultiplier/>                                                  "
    			 +"	<RegularShare/>                                                        "
    			 +"	<QualificationFlag>N</QualificationFlag>                               "
    			 +"		<StockParams>                                                      "
    			 +"			<StockParam>"		
    			 +"				<IndustryClassification>J66</IndustryClassification>           "
    			 +"				<PreviousYearProfitPerShare>1.2561</PreviousYearProfitPerShare>"
    			 +"				<CurrentYearProfitPerShare>0.0000</CurrentYearProfitPerShare>  "
    			 +"				<OfferingFlag>N</OfferingFlag>                                 "
    			 +"			</StockParam>"		
    			 +"			<StockParam>"		
    			 +"				<IndustryClassification>J67</IndustryClassification>           "
    			 +"				<PreviousYearProfitPerShare>1.2734</PreviousYearProfitPerShare>"
    			 +"				<CurrentYearProfitPerShare>0.0000</CurrentYearProfitPerShare>  "
    			 +"				<OfferingFlag>N</OfferingFlag>                                 "
    			 +"			</StockParam>"		
    			 +"			<StockParam>"		
    			 +"				<IndustryClassification>J68</IndustryClassification>           "
    			 +"				<PreviousYearProfitPerShare>3.5261</PreviousYearProfitPerShare>"
    			 +"				<CurrentYearProfitPerShare>0.0000</CurrentYearProfitPerShare>  "
    			 +"				<OfferingFlag>N</OfferingFlag>                                 "
    			 +"			</StockParam>"		
    			 +"		</StockParams>                                                     "
    			 +"	</Security>                                                            "
    			 +"	<Security>                                                             "
    			 +"	<SecurityID>000001  </SecurityID>                                      "
    			 +"	<SecurityIDSource>102</SecurityIDSource>                               "
    			 +"	<Symbol>平安银行                                    </Symbol>          "
    			 +"	<EnglishName>PAB                                     </EnglishName>    "
    			 +"	<ISIN>            </ISIN>                                              "
    			 +"	<UnderlyingSecurityID>000001  </UnderlyingSecurityID>                  "
    			 +"	<UnderlyingSecurityIDSource>102</UnderlyingSecurityIDSource>           "
    			 +"	<ListDate>19910606</ListDate>                                          "
    			 +"	<SecurityType>1</SecurityType>                                         "
    			 +"	<Currency>CNY </Currency>                                              "
    			 +"	<QtyUnit>1.00</QtyUnit>                                                "
    			 +"	<DayTrading>N</DayTrading>                                             "
    			 +"	<PrevClosePx>9.1600</PrevClosePx>                                      "
    			 +"	<SecurityStatus/>                                                      "
    			 +"	<OutstandingShare>17170411366.00</OutstandingShare>                    "
    			 +"	<PublicFloatShareQuantity>14623201251.00</PublicFloatShareQuantity>    "
    			 +"	<ParValue>1.0000</ParValue>                                            "
    			 +"	<GageFlag>Y</GageFlag>                                                 "
    			 +"	<GageRatio>70.00</GageRatio>                                           "
    			 +"	<CrdBuyUnderlying>Y</CrdBuyUnderlying>                                 "
    			 +"	<CrdSellUnderlying>Y</CrdSellUnderlying>                               "
    			 +"	<PriceCheckMode>1</PriceCheckMode>                                     "
    			 +"	<PledgeFlag>N</PledgeFlag>                                             "
    			 +"	<ContractMultiplier/>                                                  "
    			 +"	<RegularShare/>                                                        "
    			 +"	<QualificationFlag>N</QualificationFlag>                               "
    			 +"		<StockParams>                                                      "
    			 +"			<StockParam>"		
    			 +"				<IndustryClassification>J70</IndustryClassification>           "
    			 +"				<PreviousYearProfitPerShare>5.2734</PreviousYearProfitPerShare>"
    			 +"				<CurrentYearProfitPerShare>0.0000</CurrentYearProfitPerShare>  "
    			 +"				<OfferingFlag>N</OfferingFlag>                                 "
    			 +"			</StockParam>"		
    			 +"			<StockParam>"		
    			 +"				<IndustryClassification>J71</IndustryClassification>           "
    			 +"				<PreviousYearProfitPerShare>1.0134</PreviousYearProfitPerShare>"
    			 +"				<CurrentYearProfitPerShare>0.0000</CurrentYearProfitPerShare>  "
    			 +"				<OfferingFlag>N</OfferingFlag>                                 "
    			 +"			</StockParam>"		
    			 +"			<StockParam>"		
    			 +"				<IndustryClassification>J72</IndustryClassification>           "
    			 +"				<PreviousYearProfitPerShare>7.2734</PreviousYearProfitPerShare>"
    			 +"				<CurrentYearProfitPerShare>0.0000</CurrentYearProfitPerShare>  "
    			 +"				<OfferingFlag>N</OfferingFlag>                                 "
    			 +"			</StockParam>"		
    			 +"		</StockParams>                                                     "
    			 +"	</Security>                                                            "
    			 +"</Securities>                                                            ";


         //将xml解析为Map  
         Map<String, Object> resultMap = xml2map(textFromFile);
         System.out.println("resultMap----------->" + resultMap);
         //将xml解析为Json  
//         String resultJson = xml2Json(textFromFile);  
         
         Map<String, Object> sourceMap = xml2map(sourceXml);
         List<Map<String, Object>> sourceList = (List<Map<String, Object>>) sourceMap.get("Securities");
         List<Map<String, Object>> resultList = generateObject(sourceList);
         for(Map<String, Object> map :resultList){
        	 System.out.println("map---------->" + map);
         }
         
         
           
     }   
       
       
     
     /** 
      * 将xml格式响应报文解析为Map格式 
      * @param responseXmlTemp 
      * @param thirdXmlServiceBean 
      * @return 
      * @throws DocumentException 
      */  
     public static Map<String, Object> xml2map(String responseXmlTemp) {  
           Document doc = null;  
           try {  
                 doc = DocumentHelper.parseText(responseXmlTemp);  
           } catch (DocumentException e) {  
                 logger.error("parse text error : " + e);  
           }  
           Element rootElement = doc.getRootElement();  
           Map<String,Object> mapXml = new HashMap<String,Object>();  
           element2Map(mapXml,rootElement);  
           return mapXml;  
     }  
     
     
     public static List<Map<String, Object>> xml2List(String responseXmlTemp) {  
         Document doc = null;  
         try {  
               doc = DocumentHelper.parseText(responseXmlTemp);  
         } catch (DocumentException e) {  
               logger.error("parse text error : " + e);  
         }  
         Element rootElement = doc.getRootElement();  
         List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();
         Map<String,Object> mapXml = new HashMap<String,Object>();  
         element2Map(mapXml,rootElement);
         if(mapXml.size() == 0){
        	 return resultList;
         }
         for (Map.Entry<String, Object> entry : mapXml.entrySet()) {
        	 if(entry.getValue().getClass().isInstance(new String())){
        		 if(null != entry.getValue() && !"".equals(entry.getValue())){
        			 resultList.add(mapXml);
        		 }
        		 return resultList;
        	 }else if(entry.getValue().getClass().isInstance(new HashMap<String, Object>())){
        		 resultList.add((Map<String, Object>) entry.getValue());
        	 }else{
        		 resultList = (List<Map<String, Object>>) entry.getValue();
        	 }
        	 
         }
         return resultList;  
   } 
     
     /** 
      * 使用递归调用将多层级xml转为map 
      * @param map 
      * @param rootElement 
      */  
     public static void element2Map(Map<String, Object> map, Element rootElement) {  
             
           //获得当前节点的子节点  
           List<Element> elements = rootElement.elements();  
           if (elements.size() == 0) {  
        	   //没有子节点说明当前节点是叶子节点，直接取值  
        	   map.put(rootElement.getName(),rootElement.getText());  
           }else if (elements.size() == 1) {
        	   Element subElement = elements.get(0);
        	   List<Element> subElements = subElement.elements();
        	   if(subElements.size() == 0){
        		   element2Map(map,elements.get(0)); 
        	   }else{
        		 //只有一个子节点说明不用考虑list的情况，继续递归  
    		       Map<String,Object> tempMap = new HashMap<String,Object>();  
    		       element2Map(tempMap,elements.get(0));  
    		       map.put(rootElement.getName(),tempMap);
        	   }
           }else {  
        	   //多个子节点的话就要考虑list的情况了，特别是当多个子节点有名称相同的字段时  
        	   Map<String,Object> tempMap = new HashMap<String,Object>();  
        	   for (Element element : elements) {  
        		   tempMap.put(element.getName(),null);  
        	   }  
        	   Set<String> keySet = tempMap.keySet();  
        	   for (String string : keySet) {  
        		   Namespace namespace = elements.get(0).getNamespace();  
        		   List<Element> sameElements = rootElement.elements(new QName(string,namespace));  
        		   //如果同名的数目大于1则表示要构建list  
        		   if (sameElements.size() > 1) {  
        			   List<Map> list = new ArrayList<Map>();  
        			   for(Element element : sameElements){  
        				   Map<String,Object> sameTempMap = new HashMap<String,Object>();  
        				   element2Map(sameTempMap,element);  
        				   list.add(sameTempMap);  
        			   } 
        			   map.put(sameElements.get(0).getParent().getName(),list);  
        		   }else {  
        			   //同名的数量不大于1直接递归  
        			   element2Map(map,sameElements.get(0)); 
        		   }  
        	   }  
           }  
     }
     
     
     
     public static org.jdom.Document load(String url) throws Exception{  
         SAXBuilder reader = new SAXBuilder();
         File file = new File(url);
         return reader.build(file);  
     }
     
     
     /**
       * <p>Title: </p>
       * <p>Description: 将xml文件转成字符串</p>
       * @param @param fileUrl
       * @param @return
       * @author 周胜兵
     * @throws Exception 
       * @date 2017年1月20日上午10:47:03
      */
     public static String XmlToString(String fileUrl) throws Exception{  
         org.jdom.Document document=null;  
         document= load(fileUrl);  
           
         Format format =Format.getPrettyFormat();      
         format.setEncoding("UTF-8");//设置编码格式   
           
         StringWriter out=null; //输出对象  
         String sReturn =""; //输出字符串  
         XMLOutputter outputter =new XMLOutputter();   
         out=new StringWriter();   
         try {  
            outputter.output(document,out);  
         } catch (IOException e) {  
            e.printStackTrace();  
         }   
         sReturn=out.toString();   
         return sReturn;  
     }  
     
     
     
     private static List<Map<String, Object>> generateObject(List<Map<String, Object>> sourceDataList){
 		List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();
 		// 循环遍历XML转成的Map集合
 		for (Map<String, Object> dataMap : sourceDataList) {
 			//存放第一层Map值
 			Map<String, Object> firstDataMap = new HashMap<String, Object>();
 			//存放第二层Map值
 			List<Map<String, Object>> secondDataList = new ArrayList<Map<String, Object>>();
 			//将单挑数据中的map进行遍历
 			for (HashMap.Entry<String, Object> entry : dataMap.entrySet()) {
 				//判断如果Object是一个List, 则取出，不是则放第一层数据Map中
 				if(entry.getValue() instanceof ArrayList){
 					secondDataList = (List<Map<String, Object>>) entry.getValue();
 				}else{
 					firstDataMap.put(entry.getKey(), entry.getValue());
 				}
 			}
 			for(Map<String, Object> secondMap : secondDataList){
 				secondMap.putAll(firstDataMap);
 				resultList.add(secondMap);
 			}
 		}
 		return resultList;
 	}
}
