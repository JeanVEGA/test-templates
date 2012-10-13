package eu.prvaci.util.test;

import eu.prvaci.util.test.annotation.Mock;
import eu.prvaci.util.test.annotation.Tested;
import eu.prvaci.util.test.mock.EasyMockTest;
import eu.prvaci.util.test.model.Model;
import eu.prvaci.util.test.model.Service;

public class EasyMockTestTest extends EasyMockTest {

	@Tested
	private Model model;

	@Mock
	private int primitive;

	@Mock
	private Long nonPrimitive;

	@Mock
	private Service service;

}
