package eu.prvaci.util.test;

import static org.junit.Assert.fail;

import java.lang.reflect.Field;
import java.util.Map.Entry;

import org.junit.Test;

import eu.prvaci.util.test.model.Model;

public class JUnitTestNoTestedFieldTest extends JUnitTest {

	@SuppressWarnings("unused")
	private Model model;

	@Override
	protected Entry<Field, Object> createTested() throws Exception {
		Entry<Field, Object> tested = null;
		try {
			tested = super.createTested();
			fail();
		} catch (IllegalStateException e) {

		}
		return tested;
	}

	@Test
	public void testNoTestedField() {
	}

}
