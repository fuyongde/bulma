package com.sunflower.bulma.tools;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Reflections Tester.
 *
 * @author fuyongde
 * @version 1.0
 * @since <pre>11/01/2017</pre>
 */
public class ReflectionsTest {

    private final static String NAME_FIELD = "name";
    private final static String GET_NAME_METHOD = "getName";
    private final static String SET_NAME_METHOD = "setName";

    Person person;

    @Before
    public void before() throws Exception {
        person = new Person("Jason", 20);
    }

    @After
    public void after() throws Exception {
        person = null;
    }

    /**
     * Method: invokeGetter(Object obj, String propertyName)
     */
    @Test
    public void testInvokeGetter() throws Exception {
        Object name = Reflections.invokeGetter(person, NAME_FIELD);
        assertEquals(person.getName(), name);
    }

    /**
     * Method: invokeSetter(Object obj, String propertyName, Object value)
     */
    @Test
    public void testInvokeSetter() throws Exception {
        String name = "niki";
        Reflections.invokeSetter(person, NAME_FIELD, name);
        assertEquals(name, person.getName());
    }

    /**
     * Method: getFieldValue(final Object obj, final String fieldName)
     */
    @Test
    public void testGetFieldValue() throws Exception {
        Object name = Reflections.getFieldValue(person, NAME_FIELD);
        assertEquals(person.getName(), name);
    }

    /**
     * Method: setFieldValue(final Object obj, final String fieldName, final Object value)
     */
    @Test
    public void testSetFieldValue() throws Exception {
        String name = "niki";
        Reflections.setFieldValue(person, NAME_FIELD, name);
        assertEquals(name, person.getName());
    }

    /**
     * Method: invokeMethod(final Object obj, final String methodName, final Class<?>[] parameterTypes, final Object[] args)
     */
    @Test
    public void testInvokeMethod() throws Exception {
        Object name = Reflections.invokeMethod(person, GET_NAME_METHOD, null, null);
        assertEquals(person.getName(), name);

        String otherName = "niki";
        Class[] paramTypes = new Class[]{String.class};
        Object[] params = new Object[]{otherName};
        Reflections.invokeMethod(person, SET_NAME_METHOD, paramTypes, params);
        assertEquals(otherName, person.getName());
    }

    /**
     * Method: invokeMethodByName(final Object obj, final String methodName, final Object[] args)
     */
    @Test
    public void testInvokeMethodByName() throws Exception {
        String otherName = "niki";
        Object[] params = new Object[]{otherName};
        Reflections.invokeMethodByName(person, SET_NAME_METHOD, params);
        assertEquals(otherName, person.getName());
    }

    private static class Person {
        String name;
        int age;

        public Person(String name, int age) {
            this.name = name;
            this.age = age;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }
    }

} 
