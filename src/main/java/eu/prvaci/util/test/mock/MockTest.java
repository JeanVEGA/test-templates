package eu.prvaci.util.test.mock;

import org.junit.Before;

import eu.prvaci.util.test.JUnitTest;

abstract public class MockTest extends JUnitTest {

	private Object[] mocks;

	@Override
	@Before
	public void setUp() throws Exception {
		super.setUp();
		initMocks();
	}

	private void initMocks() {

	}

	protected Object[] getMocks() {
		return mocks;
	}

}
