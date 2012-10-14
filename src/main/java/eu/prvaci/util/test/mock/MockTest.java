package eu.prvaci.util.test.mock;

import java.lang.reflect.Field;
import java.util.Map.Entry;

import eu.prvaci.util.test.JUnitTest;

abstract public class MockTest extends JUnitTest {

	private Object[] mocks;

	@Override
	public void setUp() throws Exception {
		Entry<Field, Object> tested = createTested();

		mocks = initMocks(tested);
		initOtherDependencies(tested);
	}

	abstract protected Object[] initMocks(Entry<Field, Object> tested) throws Exception;

	abstract protected void initOtherDependencies(Entry<Field, Object> tested) throws Exception;

	protected Object[] getMocks() {
		return mocks;
	}

}
