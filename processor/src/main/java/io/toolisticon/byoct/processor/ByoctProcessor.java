package io.toolisticon.byoct.processor;

import com.sun.tools.javac.code.TargetType;
import de.holisticon.annotationprocessortoolkit.AbstractAnnotationProcessor;
import de.holisticon.annotationprocessortoolkit.generators.SimpleJavaWriter;
import de.holisticon.annotationprocessortoolkit.generators.SimpleResourceWriter;
import de.holisticon.annotationprocessortoolkit.tools.ElementUtils;
import io.toolisticon.byoct.api.GenerateProjectStructure;
import io.toolisticon.spiap.api.Service;

import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.PackageElement;
import javax.lang.model.element.TypeElement;
import javax.tools.StandardLocation;
import java.io.IOException;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

/**
 * Annotation processor for {@link io.toolisticon.byoct.api.GenerateProjectStructure}
 */
@Service(Processor.class)
public class ByoctProcessor extends AbstractAnnotationProcessor {

    public static final Pattern PACKAGE_PATTERN = Pattern.compile("[a-z](?:[a-z0-9])*([.][a-z](?:[a-z0-9])*)*");


    @Override
    public Set<String> getSupportedAnnotationTypes() {
        return createSupportedAnnotationSet(
                GenerateProjectStructure.class
        );
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {

        for (Element element : roundEnv.getElementsAnnotatedWith(GenerateProjectStructure.class)) {

            PackageElement packageElement = (PackageElement) element;

            GenerateProjectStructure annotation = element.getAnnotation(GenerateProjectStructure.class);

            // now do some validations
            String targetPath = annotation.targetPath().trim();
            if (targetPath.isEmpty()) {
                targetPath = StandardLocation.SOURCE_OUTPUT.toString();
            }


            String targetPackageName = annotation.targetPackageName().trim();
            if (targetPackageName.isEmpty()) {
                getMessager().error(element, "Target package name must not be empty");
            }
            if (!PACKAGE_PATTERN.matcher(targetPackageName).matches()) {
                getMessager().error(element, "Target package name must be a valid package name");
            }

            String sourcePackageRoot = annotation.sourcePackageRoot().trim().isEmpty() ? packageElement.getQualifiedName().toString() : annotation.sourcePackageRoot().trim();


            String relocationBasePackage = annotation.relocationBasePackage().trim();
            if (relocationBasePackage.isEmpty()) {
                getMessager().error(element, "Target package name must not be empty");
            }
            if (!PACKAGE_PATTERN.matcher(relocationBasePackage).matches()) {
                getMessager().error(element, "Relocation base package name must be a valid package name");
            }
            if (!relocationBasePackage.equals(sourcePackageRoot.substring(0, relocationBasePackage.length()))) {
                getMessager().error(element, "Relocation base package name '${0}' must be a prefix of  source package root '${1}'", relocationBasePackage, sourcePackageRoot);
            }

            createPom(element, annotation);


            PackageElement sourcePackageElement = getElements().getPackageElement(sourcePackageRoot);

            List<? extends Element> enclosedAnnotations = ElementUtils.AccessEnclosedElements.getEnclosedElementsOfKind(sourcePackageElement, ElementKind.ANNOTATION_TYPE);

            for (Element annotationElement : enclosedAnnotations) {

                String targetPackage = getTargetPackage((TypeElement) annotationElement, targetPackageName, relocationBasePackage);
                String annotationProcessorClassName = annotationElement.getSimpleName().toString() + "Processor";

                Target targetAnnotation = annotationElement.getAnnotation(Target.class);
                ElementType[] elementKinds = targetAnnotation != null ? targetAnnotation.value() : new ElementType[0];

                createAnnotationProcessor((TypeElement) annotationElement, targetPackage, annotationProcessorClassName);
                createAnnotationProcessorTestcase((TypeElement) annotationElement, targetPackage, annotationProcessorClassName);

            }


        }

        return false;
    }

    private String getTargetPackage(TypeElement annotationType, String targetPackageName, String relocationPackageName) {

        PackageElement annotationsPackageElement = (PackageElement) ElementUtils.AccessEnclosingElements.getFirstEnclosingElementOfKind(annotationType, ElementKind.PACKAGE);
        return targetPackageName + annotationsPackageElement.getQualifiedName().toString().substring(relocationPackageName.length());

    }

    private void createAnnotationProcessor(TypeElement annotationType, String targetPkg, String annotationProcessorClassName) {


        // create Model
        Map<String, Object> model = new HashMap<String, Object>();
        model.put("targetPackage", targetPkg);
        model.put("fqAnnotationName", annotationType.getQualifiedName().toString());
        model.put("annotationClassSimpleName", annotationType.getSimpleName());


        String filePath = "src.main.java." + targetPkg;
        String fileName = annotationProcessorClassName + ".java";


        try {
            SimpleResourceWriter resourceWriter = getFileObjectUtils().createResource(fileName, filePath, StandardLocation.CLASS_OUTPUT);
            resourceWriter.writeTemplate("/AnnotationProcessorTemplate.java.tpl", model);
            resourceWriter.close();
        } catch (IOException e) {
            getMessager().error(null, ByoctProcessorMessages.ERROR_COULD_NOT_GENERATE_ANNOTATION_PROCESSOR.getMessage(), filePath);
        }

    }

    private void createAnnotationProcessorTestcase(TypeElement annotationType, String targetPackage, String annotationProcessorClassName) {


        // create Model
        Map<String, Object> model = new HashMap<String, Object>();
        model.put("testcasePackage", targetPackage);
        model.put("processorClassName", annotationProcessorClassName);
        model.put("testcaseFile", "");


        String filePath = "src.test.java." + targetPackage;
        String fileName = annotationProcessorClassName + "Test.java";


        try {
            SimpleResourceWriter resourceWriter = getFileObjectUtils().createResource(fileName, filePath, StandardLocation.CLASS_OUTPUT);
            resourceWriter.writeTemplate("/AnnotationProcessorTestTemplate.java.tpl", model);
            resourceWriter.close();
        } catch (IOException e) {
            getMessager().error(null, ByoctProcessorMessages.ERROR_COULD_NOT_GENERATE_UNIT_TEST.getMessage(), filePath);
        }

    }

    private void createPom(Element element, GenerateProjectStructure generateProjectStructureAnnotation) {

        // create Model
        Map<String, Object> model = new HashMap<String, Object>();

        model.put("groupId", generateProjectStructureAnnotation.mvnGroupId());
        model.put("artifactId", generateProjectStructureAnnotation.mvnArtifactId());
        model.put("githubOrganization", generateProjectStructureAnnotation.githubOrganization().isEmpty() ? null : generateProjectStructureAnnotation.githubOrganization());
        model.put("githubProject", generateProjectStructureAnnotation.githubProject().isEmpty() ? null : generateProjectStructureAnnotation.githubProject());
        model.put("year", Calendar.getInstance().get(Calendar.YEAR));


        String fileName = "pom.xml";

        try {
            SimpleResourceWriter resourceWriter = getFileObjectUtils().createResource(fileName, "", StandardLocation.CLASS_OUTPUT);
            resourceWriter.writeTemplate("/pom.xml.tpl", model);
            resourceWriter.close();
        } catch (IOException e) {
            getMessager().error(element, ByoctProcessorMessages.ERROR_COULD_NOT_GENERATE_POM_XML.getMessage());
        }


    }

}
