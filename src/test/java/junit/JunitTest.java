package junit;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.service.OperateService;
import com.util.ApplicationContextUtils;

/**
 * 
 * @author xiangzhiwei
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:springmvc.xml" })
@WebAppConfiguration
public class JunitTest {
	private static final Logger logger = LoggerFactory.getLogger(JunitTest.class);
	
	
	@Test
	public void test() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException{
		Object obj = ApplicationContextUtils.getBean("operateService");
		List<Method> methodList = new ArrayList<Method>();
		logger.info("name:"+obj.getClass().getName());
		Class<?> clazz = obj.getClass();
		Class<?>[] interfaces = null;
		if(!clazz.isInterface()){
			interfaces = new Class<?>[]{clazz};
		}else{
			interfaces = clazz.getInterfaces();
		}
		for(Class<?> inter:interfaces){
			Method[] methods = inter.getMethods();
			for(Method method : methods){
				methodList.add(method);
			}
		}
		
		for(Method method : methodList){
			if("test".equals(method.getName())){
				logger.info("method:"+method.toString());
				method.invoke(obj, "s");
			}
		}
		OperateService operateService = (OperateService)ApplicationContextUtils.getBean("operateService");
		operateService.test("hello");
		
	}
		
}
