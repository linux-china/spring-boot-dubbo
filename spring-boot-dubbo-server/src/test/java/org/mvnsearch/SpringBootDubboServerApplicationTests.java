package org.mvnsearch;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mvnsearch.uic.SpringBootDubboServerApplication;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = SpringBootDubboServerApplication.class)
@WebAppConfiguration
public class SpringBootDubboServerApplicationTests {

	@Test
	public void contextLoads() {
	}

}
