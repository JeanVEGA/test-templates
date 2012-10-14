package eu.prvaci.util.test.mock;

import static org.easymock.EasyMock.createMock;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;

import eu.prvaci.util.test.JUnitTest;
import eu.prvaci.util.test.annotation.Inject;
import eu.prvaci.util.test.annotation.Mock;

abstract public class EasyMockTest extends JUnitTest {

	private Object[] mocks;

	@Override
	public void setUp() throws Exception {
		super.setUp();
		Field tested = getTestedField();
		initMocks(tested);
		initOtherDependencies(tested);
	}

	private void initMocks(Field tested) throws Exception {
		List<Field> mockFields = collectMocks();
		Map<Field, Object> instancePairs = createMockInstances(mockFields);

		injectInstances(tested, instancePairs);
		Collection<Object> instances = instancePairs.values();
		mocks = instances.toArray(new Object[instances.size()]);
	}

	private Map<Field, Object> createMockInstances(List<Field> fields) throws Exception {
		Map<Field, Object> instances = new HashMap<>();
		for (Field field : fields) {
			if (shouldCreateInstance(field)) {
				Object instance = createMock(field.getType());
				instances.put(field, instance);
				assignField(field, instance);
			}
		}
		return instances;
	}

	private boolean shouldCreateInstance(Field field) {
		return Object.class.isAssignableFrom(field.getType());
	}

	private void initOtherDependencies(Field tested) throws Exception {
		List<Field> injectFields = collectInjectFields();
		Map<Field, Object> instances = collectInstances(injectFields);
		injectInstances(tested, instances);
	}

	private Map<Field, Object> collectInstances(List<Field> fields) throws Exception {
		Map<Field, Object> instances = new HashMap<>();
		for (Field field : fields) {
			field.setAccessible(true);
			instances.put(field, field.get(this));
		}
		return instances;
	}

	private void injectInstances(Field tested, Map<Field, Object> instances) throws Exception {
		tested.setAccessible(true);
		Field[] fields = tested.get(this).getClass().getDeclaredFields();
		for (Field fieldToInject : fields) {
			for (Field field : instances.keySet()) {
				if (isSameName(field, fieldToInject) && isSameType(field, fieldToInject)) {
					fieldToInject.setAccessible(true);
					fieldToInject.set(tested.get(this), instances.get(field));
				}
			}
		}
	}

	private boolean isSameName(Field field, Field fieldToInject) {
		return StringUtils.equals(field.getName(), fieldToInject.getName());
	}

	private boolean isSameType(Field field, Field fieldToInject) {
		return ObjectUtils.equals(field.getType(), fieldToInject.getType());
	}

	private List<Field> collectInjectFields() {
		return getAnnotated(Inject.class);
	}

	private List<Field> collectMocks() {
		return getAnnotated(Mock.class);
	}

	protected Object[] getMocks() {
		return mocks;
	}

}
