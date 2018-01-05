package ${testcasePackage};

import de.holisticon.annotationprocessortoolkit.testhelper.AbstractAnnotationProcessorIntegrationTest;
import de.holisticon.annotationprocessortoolkit.testhelper.integrationtest.AnnotationProcessorIntegrationTestConfiguration;
import de.holisticon.annotationprocessortoolkit.testhelper.integrationtest.AnnotationProcessorIntegrationTestConfigurationBuilder;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.Ignored;

import java.util.Arrays;
import java.util.List;

/**
 * Tests of {@link ${processorClassName}}.
 */
@RunWith(Parameterized.class)
public class ${processorClassName}Test extends AbstractAnnotationProcessorIntegrationTest<${processorClassName}> {


    public ServiceProcessorTest(String description, AnnotationProcessorIntegrationTestConfiguration configuration) {
        super(configuration);
    }

    @Before
    public void init() {
        ServiceProcessorMessages.setPrintMessageCodes(true);
    }

    @Override
    protected ${processorClassName} getAnnotationProcessor() {
        return new ${processorClassName}();
    }

    @Parameterized.Parameters(name = "{index}: \"{0}\"")
    public static List<Object[]> data() {

        return Arrays.asList(new Object[][]{
                {
                        "Test valid usage",
                        AnnotationProcessorIntegrationTestConfigurationBuilder
                                .createTestConfig()
                                .setSourceFileToCompile("${testcaseFile}")
                                .compilationShouldSucceed()
                                .build()
                },

        });

    }


    @Test
    @Ignored
    public void testCorrectnessOfAdviceArgumentAnnotation() {
        super.test();
    }


}