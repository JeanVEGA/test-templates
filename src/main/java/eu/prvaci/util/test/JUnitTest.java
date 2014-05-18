package eu.prvaci.util.test;

import eu.prvaci.util.test.annotation.Tested;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.ClassUtils;
import org.junit.Before;
import org.testng.annotations.BeforeMethod;

abstract public class JUnitTest<TC> {

	@Tested
	protected TC testedClass;

	@Before
	@BeforeMethod
	public void setUp() throws Exception {
		createTested();
	}

	protected Map.Entry<Field, TC> createTested() throws Exception {
		Field field = getTestedField();
		testedClass = createInstance(field);

		return new AbstractMap.SimpleEntry<>(field, testedClass);
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
		return collectFields(getClass());
	}

	private Field[] collectFields(Class<?> clazz) {
		Field[] fields = clazz.getDeclaredFields();
		List<Class<?>> classes = ClassUtils.getAllSuperclasses(clazz);
		for (Class<?> c : classes) {
			fields = ArrayUtils.addAll(fields, c.getDeclaredFields());
		}
		return fields;
	}

	@SuppressWarnings("unchecked")
	protected TC createInstance(Field field) throws Exception {
		ParameterizedType type = (ParameterizedType) getClass().getGenericSuperclass();
		Type[] arguments = type.getActualTypeArguments();
		TC instance = ((Class<TC>) arguments[0]).newInstance();
		assignField(field, instance);

		return instance;
	}

	protected void assignField(Field field, Object o) throws Exception {
		field.setAccessible(true);
		field.set(this, o);
	}
}
