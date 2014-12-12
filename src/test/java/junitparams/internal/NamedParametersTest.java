package junitparams.internal;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.Description;
import org.junit.runner.RunWith;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.TestClass;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import junitparams.Named;

@RunWith(JUnitParamsRunner.class)
public class NamedParametersTest {
    TestMethod one_named_param;
    TestMethod two_named_params;

    @Before
    public void setUp() throws Exception {
        one_named_param = new TestMethod(
                new FrameworkMethod(NamedParametersTest.class.getMethod("one_named_param", new Class[]{Integer.class,Boolean.class})),
                new TestClass(this.getClass()));
        two_named_params = new TestMethod(
                new FrameworkMethod(NamedParametersTest.class.getMethod("two_named_params",new Class[]{Integer.class,Boolean.class})),
                new TestClass(this.getClass()));
    }

    @Test
    @Parameters({"20,true", "17,false"})
    public void one_named_param(@Named("age") Integer age, Boolean adult) {
    }

    @Test
    @Parameters({"20,true", "17,false"})
    public void two_named_params(@Named("age") Integer age, @Named("adult")Boolean adult) {
    }

    @Test
    public void description_with_one_named_parameter() throws Exception {
        Description description = one_named_param.describe();

        assertEquals("one_named_param", description.getDisplayName());
        assertEquals("[0] age:20,true (one_named_param)(junitparams.internal.NamedParametersTest)", description.getChildren().get(0).getDisplayName());
        assertEquals("[1] age:17,false (one_named_param)(junitparams.internal.NamedParametersTest)", description.getChildren().get(1).getDisplayName());
    }

    @Test
    public void description_with_two_named_parameter() throws Exception {
        Description description = two_named_params.describe();

        assertEquals("two_named_params", description.getDisplayName());
        assertEquals("[0] age:20,adult:true (two_named_params)(junitparams.internal.NamedParametersTest)", description.getChildren().get(0).getDisplayName());
        assertEquals("[1] age:17,adult:false (two_named_params)(junitparams.internal.NamedParametersTest)", description.getChildren().get(1).getDisplayName());
    }

}

