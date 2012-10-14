package eu.prvaci.util.test;

import static org.junit.Assert.fail;

import org.junit.Test;

import eu.prvaci.util.test.model.Model;

public class JUnitTestMultipleTestedFieldsTest extends JUnitTest {

	@SuppressWarnings("unused")
	private Model model;

	@Override
	public void setUp() throws Exception {
		try {
			super.setUp();
			fail();
		} catch (IllegalStateException e) {

		}
	}

	@Test
	public void testMultipleTestedFields() {

	}
}
