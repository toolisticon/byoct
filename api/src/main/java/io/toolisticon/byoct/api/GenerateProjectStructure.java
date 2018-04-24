package io.toolisticon.byoct.api;

import java.lang.annotation.Annotation;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation used to create project structure for compliance test.
 */
@Retention(RetentionPolicy.SOURCE)
@Target(ElementType.PACKAGE)
public @interface GenerateProjectStructure {

    /**
     * The path to create the project.
     * If not set it should default into the StandardLocation.SOURCE_OUTPUT
     *
     * @return
     */
    String targetPath() default "";

    /**
     * The target package prefix, replaces package prefix defined in sourcePackageRoot.
     *
     * @return
     */
    String targetPackageName();


    /**
     * The mvn groupId to use.
     *
     * @return
     */
    String mvnGroupId();

    /**
     * The mvn artifactId to use.
     *
     * @return
     */
    String mvnArtifactId();


    /**
     * (Optional) Defines the annotations to be processed. If not set the package defined in sourcePackageRoot or the package where this annotation is used should be used.
     *
     * @return
     */
    Class<? extends Annotation>[] annotations() default {};


    /**
     * (Optional) Defines the root package to search annotations in. If not set the package where this annotation is used should be used.
     *
     * @return
     */
    String sourcePackageRoot() default "";

    /**
     * (Optional) Defines the base package which should be used for relocation.
     * * @return
     */
    String relocationBasePackage() default "";

    /**
     * (Optional) Defines the github organization to use.
     *
     * @return
     */
    String githubOrganization() default "";

    /**
     * (Optional) Defines the github organization to use.
     *
     * @return
     */
    String githubProject() default "";


}
