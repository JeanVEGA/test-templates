package eu.prvaci.util.test;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;

import eu.prvaci.util.test.annotation.Tested;
import eu.prvaci.util.test.model.Model;

public class JUnitTestTest extends JUnitTest {

	@Tested
	private Model model;

	@Test
	public void testSetUp() {
		assertNotNull(model);
	}

}
