/**
 * Example about how to use byoct
 */

@GenerateProjectStructure(
        mvnGroupId = "io.toolisticon.byoct.jpa",
        mvnArtifactId = "byoct-jpa",
        sourcePackageRoot = "javax.persistence",
        relocationBasePackage = "javax.persistence",
        targetPackageName = "io.toolisticon.byoct.jpa")
package io.toolisticon.byoct.example.jpa;


import io.toolisticon.byoct.api.GenerateProjectStructure;