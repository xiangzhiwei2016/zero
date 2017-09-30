package junit;

import javax.inject.Inject;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.service.OperateService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:springmvc.xml" })
@WebAppConfiguration
public class JunitTest {
	@Inject
	OperateService operateService;
	
	@Test
	public void test(){
		operateService.test("hello");
	}
		
}
