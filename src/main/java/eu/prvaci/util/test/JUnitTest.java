package eu.prvaci.util.test;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;

import eu.prvaci.util.test.annotation.Tested;

abstract public class JUnitTest {

	@Before
	public void setUp() throws Exception {
		Field tested = getTestedField();
		createInstance(tested);
	}

	private Field getTestedField() {
		List<Field> annotated = getAnnotated(Tested.class);
		if (annotated.size() != 1) {
			throw new IllegalStateException(String.format(
					"There must be a single tested class annotated with %s, found %d", Tested.class.getName(),
					annotated.size()));
		}
		return annotated.get(0);
	}

	private <T extends Annotation> List<Field> getAnnotated(Class<T> clazz) {
		List<Field> annotated = new ArrayList<>();
		for (Field field : getFields()) {
			if (field.isAnnotationPresent(clazz)) {
				annotated.add(field);
			}
		}
		return annotated;
	}

	private Field[] getFields() {
		return getClass().getDeclaredFields();
	}

	private void createInstance(Field field) throws Exception {
		field.setAccessible(true);
		field.set(this, field.getType().newInstance());
	}
}
