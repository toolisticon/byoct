package ${testcasePackage};

import io.toolisticon.annotationprocessortoolkit.testhelper.AbstractAnnotationProcessorIntegrationTest;
import io.toolisticon.annotationprocessortoolkit.testhelper.integrationtest.AnnotationProcessorIntegrationTestConfiguration;
import io.toolisticon.annotationprocessortoolkit.testhelper.integrationtest.AnnotationProcessorIntegrationTestConfigurationBuilder;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.Ignore;

import java.util.Arrays;
import java.util.List;

/**
 * Tests of {@link ${processorClassName}}.
 */
@RunWith(Parameterized.class)
public class ${processorClassName}Test extends AbstractAnnotationProcessorIntegrationTest<${processorClassName}> {


    public ${processorClassName}Test(String description, AnnotationProcessorIntegrationTestConfiguration configuration) {
        super(configuration);
    }

    @Before
    public void init() {
        ${processorClassName}Messages.setPrintMessageCodes(true);
    }

    @Override
    protected ${processorClassName} getAnnotationProcessor() {
        return new ${processorClassName}();
    }

    @Parameterized.Parameters(name = "{index}: \"{0}\"")
    public static List<Object[]> data() {

        return Arrays.asList(new Object[][]{

                !{for testcase : testcases}
                // --------------------------------------------------------------
                // -- ${testcase.elementTypeName} : ${testcase.javaElementKindName}
                // --------------------------------------------------------------

                {
                        "Test valid usage",
                        AnnotationProcessorIntegrationTestConfigurationBuilder
                                .createTestConfig()
                                .setSourceFileToCompile("${testcase.filePath}")
                                .compilationShouldSucceed()
                                .build()
                },

                !{/for}

        });

    }


    @Test
    @Ignore
    public void testCorrectnessOfAdviceArgumentAnnotation() {
        super.test();
    }


}