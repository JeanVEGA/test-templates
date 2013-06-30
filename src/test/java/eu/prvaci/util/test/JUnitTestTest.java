package eu.prvaci.util.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;

import eu.prvaci.util.test.model.Model;

public class JUnitTestTest extends JUnitTest<Model> {

	@Test
	public void testSetUp() {
		assertNotNull(testedClass);
		assertEquals(Model.class, testedClass.getClass());
	}

}
