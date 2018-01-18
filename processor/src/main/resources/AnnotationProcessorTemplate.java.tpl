package ${targetPackage};

import io.toolisticon.annotationprocessortoolkit.AbstractAnnotationProcessor;
import ${fqAnnotationName};
import io.toolisticon.spiap.api.Service;
import io.toolisticon.spiap.api.OutOfService;

import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.PackageElement;
import javax.lang.model.element.TypeElement;
import javax.tools.StandardLocation;
import java.util.Set;

/**
 * Annotation processor for {@link ${annotationClassSimpleName}}.
 */
@OutOfService
@Service(Processor.class)
public class ${annotationClassSimpleName}Processor extends AbstractAnnotationProcessor {


    @Override
    public Set<String> getSupportedAnnotationTypes() {
        return createSupportedAnnotationSet(
                ${annotationClassSimpleName}.class
        );
    }

    @Override
    public boolean processAnnotations(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {

        for (Element element : roundEnv.getElementsAnnotatedWith(${annotationClassSimpleName}.class)) {

            // Add your own validation code here

        }

        return false;
    }
}
