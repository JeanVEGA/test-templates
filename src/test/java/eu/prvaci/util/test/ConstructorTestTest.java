package eu.prvaci.util.test;

import eu.prvaci.util.test.model.Model;

import java.util.Arrays;
import java.util.Collection;

public class ConstructorTestTest extends ConstructorTest {

	@Override
	protected Collection<Class<?>> getClasses() {
		return Arrays.asList(Model.class, Object.class);
	}

}
