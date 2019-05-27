package ${testcasePackage};

import io.toolisticon.annotationprocessortoolkit.tools.MessagerUtils;
import io.toolisticon.compiletesting.CompileTestBuilder;
import io.toolisticon.compiletesting.JavaFileObjectUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.Ignore;

/**
 * Tests of {@link ${processorClassName}}.
 */
public class ${processorClassName}Test {

    CompileTestBuilder.CompilationTestBuilder compileTestBuilder;


    @Before
    public void init() {
        MessagerUtils.setPrintMessageCodes(true);

        compileTestBuilder = CompileTestBuilder
            .compilationTest()
            .addProcessors(${processorClassName}.class);
    }

    !{for testcase : testcases}
    // --------------------------------------------------------------
    // -- ${testcase.elementTypeName} : ${testcase.javaElementKindName}
    // --------------------------------------------------------------

    @Test
    @Ignore
    public void test_${testcase.elementTypeName}_${testcase.javaElementKindName}_valid_usage() {

        compileTestBuilder
                .addSources(JavaFileObjectUtils.readFromResource("${testcase.filePath}"))
                .compilationShouldSucceed()
                .testCompilation();
    }

    !{/for}




}