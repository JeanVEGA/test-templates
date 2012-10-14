package eu.prvaci.util.test;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.Before;

import eu.prvaci.util.test.annotation.Tested;

abstract public class JUnitTest {

	@Before
	public void setUp() throws Exception {
		createTested();
	}

	protected Map.Entry<Field, Object> createTested() throws Exception {
		Field field = getTestedField();
		Object instance = createInstance(field);

		return new AbstractMap.SimpleEntry<>(field, instance);
	}

	protected Field getTestedField() {
		List<Field> annotated = getAnnotated(Tested.class);
		if (annotated.size() != 1) {
			throw new IllegalStateException(String.format(
					"There must be a single tested class annotated with %s, found %d", Tested.class.getName(),
					annotated.size()));
		}
		return annotated.get(0);
	}

	protected <T extends Annotation> List<Field> getAnnotated(Class<T> clazz) {
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

	protected Object createInstance(Field field) throws Exception {
		Object instance = field.getType().newInstance();
		assignField(field, instance);

		return instance;
	}

	protected void assignField(Field field, Object o) throws Exception {
		field.setAccessible(true);
		field.set(this, o);
	}
}
