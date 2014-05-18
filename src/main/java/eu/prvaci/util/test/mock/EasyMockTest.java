package eu.prvaci.util.test.mock;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import eu.prvaci.util.test.annotation.Inject;
import eu.prvaci.util.test.annotation.Mock;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang3.StringUtils;
import org.junit.After;
import org.testng.annotations.AfterMethod;

abstract public class EasyMockTest<TC> extends MockTest<TC> {

	@After
	@AfterMethod
	public void after() {
		verifyMocks();
	}

	protected void verifyMocks() {
		verify(getMocks());
	}

	protected void replayMocks() {
		replay(getMocks());
	}

	@Override
	protected Object[] initMocks(Entry<Field, TC> tested, Map<Field, Object> mockInstancePairs) throws Exception {
		injectInstances(tested, mockInstancePairs);
		Collection<Object> instances = mockInstancePairs.values();
		return instances.toArray(new Object[instances.size()]);
	}

	@Override
	protected Map<Field, Object> createMockInstances() throws Exception {
		List<Field> mockFields = collectMocks();
		Map<Field, Object> instancePairs = createMockInstances(mockFields);
		return instancePairs;
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

	@Override
	protected void initNonMockInjects(Entry<Field, TC> tested) throws Exception {
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

	private void injectInstances(Entry<Field, TC> tested, Map<Field, Object> instances) throws Exception {
		tested.getKey().setAccessible(true);
		Field[] fields = tested.getValue().getClass().getDeclaredFields();
		for (Field fieldToInject : fields) {
			for (Field field : instances.keySet()) {
				if (isSameName(field, fieldToInject) && isCompatibleType(field, fieldToInject)) {
					fieldToInject.setAccessible(true);
					fieldToInject.set(tested.getValue(), instances.get(field));
				}
			}
		}
	}

	private boolean isSameName(Field field, Field fieldToInject) {
		return StringUtils.equals(field.getName(), fieldToInject.getName());
	}

	private boolean isCompatibleType(Field field, Field fieldToInject) {
		return fieldToInject.getType().isAssignableFrom(field.getType());
	}

	private List<Field> collectInjectFields() {
		return getAnnotated(Inject.class);
	}

	private List<Field> collectMocks() {
		return getAnnotated(Mock.class);
	}

}
