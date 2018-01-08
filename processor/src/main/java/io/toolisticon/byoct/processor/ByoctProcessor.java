package io.toolisticon.byoct.processor;

import de.holisticon.annotationprocessortoolkit.AbstractAnnotationProcessor;
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
import java.lang.annotation.Target;
import java.util.ArrayList;
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

    public enum JavaElementKind {
        METHOD(ElementType.METHOD, "/testcases/methodTestcaseTemplate.java.tpl"),
        CONSTRUCTOR(ElementType.CONSTRUCTOR, "/testcases/methodTestcaseTemplate.java.tpl"),
        PARAMETER(ElementType.PARAMETER, "/testcases/parameterTestcaseTemplate.java.tpl"),
        FIELD(ElementType.FIELD, "/testcases/fieldTestcaseTemplate.java.tpl"),
        INTERFACE(ElementType.TYPE, "/testcases/interfaceTestcaseTemplate.java.tpl"),
        ENUM(ElementType.TYPE, "/testcases/enumTestcaseTemplate.java.tpl"),
        CLASS(ElementType.TYPE, "/testcases/classTestcaseTemplate.java.tpl"),
        ANNOTATION(ElementType.ANNOTATION_TYPE, "/testcases/annotationTestcaseTemplate.java.tpl"),
        PACKAGE(ElementType.PACKAGE, "/testcases/packageTestcaseTemplate.java.tpl");

        private final ElementType elementType;
        private final String templateName;

        JavaElementKind(ElementType elementType, String templateName) {
            this.elementType = elementType;
            this.templateName = templateName;
        }

        public static JavaElementKind[] getJavaElementKindForElementType(ElementType searchElementType) {

            List<JavaElementKind> result = new ArrayList<JavaElementKind>();

            for (JavaElementKind element : JavaElementKind.values()) {
                if (element.elementType.equals(searchElementType)) {
                    result.add(element);
                }
            }

            return result.toArray(new JavaElementKind[result.size()]);
        }

    }

    public static class Testcase {

        private final String processorName;
        private final String elementTypeName;
        private final String javaElementKindName;
        private final String filename;
        private final String packageName;
        private final String templateName;


        public Testcase(String processedAnnotationName, ElementType elementType, JavaElementKind javaElementKind, String filename, String packageName) {
            this.processorName = processedAnnotationName != null ? processedAnnotationName.toLowerCase() + "processor" : null;
            elementTypeName = elementType != null ? elementType.name().toLowerCase() : null;
            javaElementKindName = javaElementKind != null ? "test" + javaElementKind.name().toLowerCase() : null;
            this.filename = filename;
            this.packageName = packageName + "." + javaElementKindName;
            this.templateName = javaElementKind.templateName;
        }

        public String getProcessorName() {
            return processorName;
        }

        public String getPackageName() {
            return packageName;
        }

        public String getTemplateName() {
            return templateName;
        }

        public String getElementTypeName() {
            return elementTypeName;
        }

        public String getJavaElementKindName() {
            return javaElementKindName;
        }

        public String getFilename() {
            return filename;
        }

        public String getFilePath() {
            return "testcases/" + this.processorName + "/" + elementTypeName + "/" + javaElementKindName + "/" + filename + ".java";
        }

        public String getTestcasePackage() {
            return "testcases." + this.processorName + "." + elementTypeName + "." + javaElementKindName;
        }
    }


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

                Testcase[] testcases = getTestcases((TypeElement) annotationElement, targetPackage);

                createAnnotationProcessor((TypeElement) annotationElement, targetPackage, annotationProcessorClassName);
                createAnnotationProcessorMessages((TypeElement) annotationElement, targetPackage, annotationProcessorClassName);

                createAnnotationProcessorTestcases((TypeElement) annotationElement, testcases);
                createAnnotationProcessorTestcase((TypeElement) annotationElement, targetPackage, annotationProcessorClassName, testcases);

            }


        }

        return false;
    }

    private String getTargetPackage(TypeElement annotationType, String targetPackageName, String relocationPackageName) {

        PackageElement annotationsPackageElement = (PackageElement) ElementUtils.AccessEnclosingElements.getFirstEnclosingElementOfKind(annotationType, ElementKind.PACKAGE);
        return targetPackageName + annotationsPackageElement.getQualifiedName().toString().substring(relocationPackageName.length()) + "." + annotationType.getSimpleName().toString().toLowerCase() + "processor";

    }

    private Testcase[] getTestcases(TypeElement annotationType, String targetPackage) {
        Target targetAnnotation = annotationType.getAnnotation(Target.class);
        ElementType[] elementTypes = targetAnnotation != null ? targetAnnotation.value() : new ElementType[0];

        List<Testcase> testcases = new ArrayList<Testcase>();
        for (ElementType elementType : elementTypes) {

            for (JavaElementKind javaElementKind : JavaElementKind.getJavaElementKindForElementType(elementType)) {

                testcases.add(new Testcase(annotationType.getSimpleName().toString(),elementType, javaElementKind, "TestcaseValidUsage", targetPackage));

            }

        }
        return testcases.toArray(new Testcase[testcases.size()]);
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

    private void createAnnotationProcessorMessages(TypeElement annotationType, String targetPackage, String annotationProcessorClassName) {


        // create Model
        Map<String, Object> model = new HashMap<String, Object>();
        model.put("targetPackage", targetPackage);
        model.put("processorClassName", annotationProcessorClassName);


        String filePath = "src.main.java." + targetPackage;
        String fileName = annotationProcessorClassName + "Messages.java";


        try {
            SimpleResourceWriter resourceWriter = getFileObjectUtils().createResource(fileName, filePath, StandardLocation.CLASS_OUTPUT);
            resourceWriter.writeTemplate("/AnnotationProcessorMessageTemplate.java.tpl", model);
            resourceWriter.close();
        } catch (IOException e) {
            getMessager().error(null, ByoctProcessorMessages.ERROR_COULD_NOT_GENERATE_ANNOTATION_PROCESSOR_MESSAGES.getMessage(), filePath);
        }

    }

    private void createAnnotationProcessorTestcases(TypeElement annotationType, Testcase[] testcases) {

        if (testcases == null) {
            return;
        }

        for (Testcase testcase : testcases) {

            // create Model
            Map<String, Object> model = new HashMap<String, Object>();
            model.put("targetPackage", testcase.packageName);
            model.put("fullyQualifiedAnnotationName", annotationType.getQualifiedName().toString());
            model.put("annotationName", annotationType.getSimpleName());
            model.put("className", testcase.getFilename());


            String filePath = "src.test.resources." + testcase.getTestcasePackage();
            String fileName = testcase.getFilename() + ".java" ;


            try {
                SimpleResourceWriter resourceWriter = getFileObjectUtils().createResource(fileName, filePath, StandardLocation.CLASS_OUTPUT);
                resourceWriter.writeTemplate(testcase.templateName, model);
                resourceWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
                getMessager().error(null, ByoctProcessorMessages.ERROR_COULD_NOT_GENERATE_UNIT_TEST.getMessage(), filePath);
            }

        }
    }

    private void createAnnotationProcessorTestcase(TypeElement annotationType, String targetPackage, String annotationProcessorClassName, Testcase[] testcases) {


        // create Model
        Map<String, Object> model = new HashMap<String, Object>();
        model.put("testcasePackage", targetPackage);
        model.put("processorClassName", annotationProcessorClassName);
        model.put("testcases", testcases);
        //  model.put("elementTypes", )


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
