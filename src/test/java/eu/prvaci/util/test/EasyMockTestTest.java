package eu.prvaci.util.test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;

import org.junit.Test;

import eu.prvaci.util.test.annotation.Inject;
import eu.prvaci.util.test.annotation.Mock;
import eu.prvaci.util.test.annotation.Tested;
import eu.prvaci.util.test.mock.EasyMockTest;
import eu.prvaci.util.test.model.Model;
import eu.prvaci.util.test.model.Service;

public class EasyMockTestTest extends EasyMockTest {

	@Tested
	private Model model;

	@Inject
	private int primitive = 2;

	@Inject
	private Long nonPrimitive = 1L;

	@Mock
	private Service service;

	@Test
	public void testEasyMock() {
		assertArrayNotNull();
		assertMocks();
		assertDependencyInjection();
	}

	private void assertArrayNotNull() {
		for (Object o : new Object[] { model, nonPrimitive, service }) {
			assertNotNull(o);
		}
	}

	private void assertMocks() {
		assertSame(service, getMocks()[0]);
	}

	private void assertDependencyInjection() {
		assertSame(primitive, model.getPrimitive());
		assertSame(nonPrimitive, model.getNonPrimitive());
		assertSame(service, model.getService());
	}

}
