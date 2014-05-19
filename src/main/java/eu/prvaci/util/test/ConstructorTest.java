package eu.prvaci.util.test;

import com.openpojo.reflection.PojoClass;
import com.openpojo.reflection.PojoMethod;
import com.openpojo.reflection.impl.PojoClassFactory;
import com.openpojo.validation.PojoValidator;
import com.openpojo.validation.rule.impl.NoPublicFieldsExceptStaticFinalRule;

import java.util.Collection;

import org.junit.Before;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.collections.Lists;

abstract public class ConstructorTest extends JUnitTest<Object> {

	private Collection<PojoClass> pojoClasses;
	private PojoValidator pojoValidator;

	@Before
	@BeforeMethod
	@Override
	public void setUp() {
		pojoClasses = Lists.newArrayList();
		for (Class<?> clazz : getClasses()) {
			pojoClasses.add(PojoClassFactory.getPojoClass(clazz));
		}

		pojoValidator = new PojoValidator();
		pojoValidator.addRule(new NoPublicFieldsExceptStaticFinalRule());
	}

	abstract protected Collection<Class<?>> getClasses();

	@org.junit.Test
	@Test
	public void testPojoStructureAndBehavior() {
		for (PojoClass pojoClass : pojoClasses) {
			pojoValidator.runValidation(pojoClass);
			for (PojoMethod c : pojoClass.getPojoConstructors()) {
				c.invoke(null);
			}
		}
	}
}
