package eu.prvaci.util.test;

import static org.hamcrest.Matchers.instanceOf;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertThat;

import java.lang.reflect.Proxy;

import org.junit.Test;

import eu.prvaci.util.test.annotation.Inject;
import eu.prvaci.util.test.annotation.Mock;
import eu.prvaci.util.test.mock.EasyMockTest;
import eu.prvaci.util.test.model.Model;
import eu.prvaci.util.test.model.Service;

public class EasyMockTestTest extends EasyMockTest<Model> {

	@Inject
	private final int primitive = 2;

	@Inject
	private final Long nonPrimitive = 1L;

	@Mock
	private Service service;

	@Test
	public void testEasyMock() {
		replayMocks();

		assertArrayNotNull();
		assertMocks();
		assertDependencyInjection();
	}

	private void assertArrayNotNull() {
		for (Object o : new Object[] { testedClass, nonPrimitive, service }) {
			assertNotNull(o);
		}
	}

	private void assertMocks() {
		assertSame(service, getMocks()[0]);
		assertThat(service, instanceOf(Proxy.class));

	}

	private void assertDependencyInjection() {
		assertSame(primitive, testedClass.getPrimitive());
		assertSame(nonPrimitive, testedClass.getNonPrimitive());
		assertSame(service, testedClass.getService());
	}

}
