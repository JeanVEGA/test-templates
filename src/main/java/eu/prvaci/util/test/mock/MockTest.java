package eu.prvaci.util.test.mock;

import java.lang.reflect.Field;
import java.util.Map.Entry;

import eu.prvaci.util.test.JUnitTest;

abstract public class MockTest<TC> extends JUnitTest<TC> {

	private Object[] mocks;

	@Override
	public void setUp() throws Exception {
		Entry<Field, TC> tested = createTested();

		mocks = initMocks(tested);
		initNonMockInjects(tested);
	}

	abstract protected Object[] initMocks(Entry<Field, TC> tested) throws Exception;

	abstract protected void initNonMockInjects(Entry<Field, TC> tested) throws Exception;

	protected Object[] getMocks() {
		return mocks;
	}

}
