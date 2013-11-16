package eu.prvaci.util.test.mock;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.Map.Entry;

import eu.prvaci.util.test.JUnitTest;

abstract public class MockTest<TC> extends JUnitTest<TC> {

	private Object[] mocks;

	@Override
	public void setUp() throws Exception {
		Map<Field, Object> mockInstancePairs = createMockInstances();
		Entry<Field, TC> tested = createTested();

		mocks = initMocks(tested, mockInstancePairs);
		initNonMockInjects(tested);
	}

	abstract protected Map<Field, Object> createMockInstances() throws Exception;

	abstract protected Object[] initMocks(Entry<Field, TC> tested, Map<Field, Object> mockInstancePairs)
			throws Exception;

	abstract protected void initNonMockInjects(Entry<Field, TC> tested) throws Exception;

	protected Object[] getMocks() {
		return mocks;
	}

}
