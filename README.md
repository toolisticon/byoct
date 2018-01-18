# BYOCT - Bring Your Own Compliance Test
[![Maven Central](https://maven-badges.herokuapp.com/maven-central/io.toolisticon.byoct/byoct-parent/badge.svg)](https://maven-badges.herokuapp.com/maven-central/io.toolisticon.byoct/byoct-parent)
[![Build Status](https://travis-ci.org/toolisticon/byoct.svg?branch=master)](https://travis-ci.org/toolisticon/byoct)
[![codecov](https://codecov.io/gh/toolisticon/byoct/branch/master/graph/badge.svg)](https://codecov.io/gh/toolisticon/byoct)

# Why you should use this project?
Nowadays most Java frameworks and standards make heavily use of annotations.
One drawback of annotations is that java offers only little support to apply constraints regarding how to use annotations.
Usually constraints about how to use annotations are just described in their Java docs.

Annotation processors can help to provide compliance tests to check the correct usage of annotation based apis in most cases.

This project helps you to generate a working maven compliance test annotation processor project for any kind of annotation based api.

# Features
Generates Maven project for annotation processor based compliance tests to validate if an annotation based api is used correctly.

This includes
- annotation processors for all runtime annotations
- unit tests classes for all annotation processors
- example unit testcases for all annotation processors

# How does it work?

It's quite simple. Create a package-info.java file.
Annotate that package with the _GenerateProjectStructure_ annotation.

For example for JPA:

    @GenerateProjectStructure(
            mvnGroupId = "io.toolisticon.byoct.jpa",
            mvnArtifactId = "byoct-jpa",
            sourcePackageRoot = "javax.persistence",
            relocationBasePackage = "javax.persistence",
            targetPackageName = "io.toolisticon.byoct.jpa")

Make sure that both byoct jar and the annotation api jar are on classpath during compilation.
You can just use the example project to do that.

The Project will be located in the target/classes folder.

Copy the generated pom.xml and src folder.

# Contributing

We welcome any kind of suggestions and pull requests.

## Building and developing the BYOCT

The BYOCT is built using Maven (at least version 3.0.0).
A simple import of the pom in your IDE should get you up and running. To build the byoct on the commandline, just run `mvn` or `mvn clean install`

## Requirements

The likelihood of a pull request being used rises with the following properties:

- You have used a feature branch.
- You have included a test that demonstrates the functionality added or fixed.
- You adhered to the [code conventions](http://www.oracle.com/technetwork/java/javase/documentation/codeconvtoc-136057.html).

## Contributions

- (2018) Tobias Stamann (Holisticon AG)

## Sponsoring

This project is sponsored and supported by [holisticon AG](http://www.holisticon.de/)

![Holisticon AG](/holisticon-logo.png)

# License

This project is released under the revised [BSD License](LICENSE).

This project includes and repackages the [Annotation-Processor-Toolkit](https://github.com/holisticon/annotation-processor-toolkit) released under the  [BSD License](/3rdPartyLicenses/annotation-processor-toolkit/LICENSE.txt).
