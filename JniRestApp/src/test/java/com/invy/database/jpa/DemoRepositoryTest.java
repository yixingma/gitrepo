/**
 * 
 */
package com.invy.database.jpa;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author ema
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/config/test-application-config.xml")
public class DemoRepositoryTest {

	@Test
	public void testNothing(){
		assertNotNull(1);
	}
}
