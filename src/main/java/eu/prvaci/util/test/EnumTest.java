package eu.prvaci.util.test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.emptyArray;
import static org.hamcrest.Matchers.not;
import static org.testng.Assert.assertSame;

import com.openpojo.reflection.PojoClass;
import com.openpojo.reflection.PojoMethod;
import com.openpojo.reflection.impl.PojoClassFactory;
import com.openpojo.validation.PojoValidator;
import com.openpojo.validation.rule.impl.NoPublicFieldsExceptStaticFinalRule;

import java.util.Collection;
import java.util.List;

import org.junit.Before;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.collections.Lists;

public class EnumTest {

	private static final String VALUES = "values";

	private final Class<Enum<?>>[] classes;

	private Collection<PojoClass> pojoClasses;
	private PojoValidator pojoValidator;

	public EnumTest(Class<Enum<?>>[] classes) {
		this.classes = classes;
	}

	@Before
	@BeforeMethod
	public void setup() {
		pojoClasses = Lists.newArrayList();
		for (Class<Enum<?>> clazz : classes) {
			pojoClasses.add(PojoClassFactory.getPojoClass(clazz));
		}

		pojoValidator = new PojoValidator();
		pojoValidator.addRule(new NoPublicFieldsExceptStaticFinalRule());
	}

	@org.junit.Test
	@Test
	public void testPojoStructureAndBehavior() {
		for (PojoClass pojoClass : pojoClasses) {
			pojoValidator.runValidation(pojoClass);
			List<PojoMethod> pojoMethods = pojoClass.getPojoMethods();

			Enum<?>[] values = (Enum[]) getMethod(VALUES, pojoMethods).invoke(null);
			assertThat(values, not(emptyArray()));
			PojoMethod valueOf = getMethod("valueOf", pojoMethods);
			for (Enum<?> value : values) {
				assertSame(value, valueOf.invoke(null, value.name()));
			}
		}
	}

	private PojoMethod getMethod(String name, List<PojoMethod> pojoMethods) {
		for (PojoMethod method : pojoMethods) {
			if (method.getName().equals(name)) {
				return method;
			}
		}
		throw new IllegalArgumentException(Enum.class.getSimpleName() + " has no method with name=" + name);
	}
}
