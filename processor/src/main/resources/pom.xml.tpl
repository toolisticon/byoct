<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>

    <groupId>${groupId}</groupId>
    <artifactId>${artifactId}</artifactId>
    <name>${artifactId}</name>
    <packaging>bundle</packaging>

    <description>Please refer to https://...</description>
    <url>https://github.com/...</url>

    <parent>
        <groupId>io.toolisticon.maven</groupId>
        <artifactId>maven-oss-parent</artifactId>
        <version>0.7.0</version>
    </parent>


    <!-- TODO: FILL ORGANIZATION AND DEVELOPERS -->
    <organization>
        <name>...</name>
        <url>...</url>
    </organization>
    <developers>
        <!--
        <developer>
            <name>...</name>
            <email>...</email>
            <organization>...</organization>
            <organizationUrl>...</organizationUrl>
        </developer>
        -->
    </developers>

    <licenses>
        <!-- TODO: Add your license here or keep the default BDS License -->
        <!--
        <license>
            <name>Revised BSD License</name>
            <url>
                http://en.wikipedia.org/wiki/BSD_licenses#3-clause_license_.28.22Revised_BSD_License.22.2C_.22New_BSD_License.22.2C_or_.22Modified_BSD_License.22.29
            </url>
            <comments>s. LICENSE file</comments>
            <distribution>repo</distribution>
        </license>
        -->
    </licenses>

    <inceptionYear>${year}</inceptionYear>

    <prerequisites>
        <maven>3.0.4</maven>
    </prerequisites>


    <issueManagement>
        <url>https://github.com/.../issues</url>
        <system>GitHub Issues</system>
    </issueManagement>

    <scm>
    !{if githubOrganization != null && githubProject != null}
        <connection>scm:git:git@github.com:${githubOrganization}/${githubProject}.git</connection>
        <developerConnection>scm:git:git@github.com:${githubOrganization}/${githubProject}.git</developerConnection>
        <url>git@github.com:${githubOrganization}/${githubProject}.git</url>
        <tag>HEAD</tag>
    !{/if}
    !{if githubOrganization == null || githubProject == null}
        <!-- TODO: CONFIGURE YOUR SCM -->
        <!--
        <connection>scm:git:git@github.com:XXX/XXX.git</connection>
        <developerConnection>scm:git:git@github.com:XXX/XXX.git</developerConnection>
        <url>git@github.com:XXX/XXX.git</url>
        <tag>HEAD</tag>
        -->
    !{/if}
    </scm>

    !{static}

    <properties>
        <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
        <project.build.resourceEncoding>UTF-8</project.build.resourceEncoding>
        <encoding>UTF-8</encoding>

        <annotationProcessorToolkit.version>0.12.0</annotationProcessorToolkit.version>

        <spiap.version>0.7.0</spiap.version>

        <!-- versions of test dependencies -->
        <junit.version>4.12</junit.version>
        <hamcrest.version>1.3</hamcrest.version>
        <mockito.version>1.10.19</mockito.version>
        <lombok.version>1.16.6</lombok.version>
        <toolisticon-compile-testing.version>0.5.0</toolisticon-compile-testing.version>

    </properties>


    <build>
        <defaultGoal>clean install</defaultGoal>
         <pluginManagement>
            <plugins>
                <plugin>
                    <groupId>org.codehaus.mojo</groupId>
                    <artifactId>cobertura-maven-plugin</artifactId>
                    <version>2.6</version>
                </plugin>
                <plugin>
                    <artifactId>maven-clean-plugin</artifactId>
                    <version>2.6.1</version>
                </plugin>
                <plugin>
                    <artifactId>maven-resources-plugin</artifactId>
                    <version>2.7</version>
                </plugin>
                <plugin>
                    <artifactId>maven-compiler-plugin</artifactId>
                    <version>3.6.0</version>
                    <configuration>
                        <source>1.6</source>
                        <target>1.6</target>
                    </configuration>
                </plugin>
                <plugin>
                    <artifactId>maven-dependency-plugin</artifactId>
                    <version>2.9</version>
                </plugin>
                <plugin>
                    <artifactId>maven-jar-plugin</artifactId>
                    <version>2.5</version>
                </plugin>
                <plugin>
                    <artifactId>maven-war-plugin</artifactId>
                    <version>2.4</version>
                </plugin>
                <plugin>
                    <artifactId>maven-surefire-plugin</artifactId>
                    <version>2.18.1</version>
                </plugin>
                <plugin>
                    <artifactId>maven-failsafe-plugin</artifactId>
                    <version>2.18.1</version>
                </plugin>
                <plugin>
                    <artifactId>maven-install-plugin</artifactId>
                    <version>2.5.2</version>
                </plugin>
                <plugin>
                    <artifactId>maven-deploy-plugin</artifactId>
                    <version>2.8.2</version>
                </plugin>
                <plugin>
                    <artifactId>maven-release-plugin</artifactId>
                    <version>2.5</version>
                </plugin>
                <plugin>
                    <artifactId>maven-javadoc-plugin</artifactId>
                    <version>2.10.4</version>
                </plugin>
                <plugin>
                    <artifactId>maven-site-plugin</artifactId>
                    <version>3.4</version>
                </plugin>
                <plugin>
                    <artifactId>maven-enforcer-plugin</artifactId>
                    <version>1.3.1</version>
                </plugin>
                <plugin>
                    <artifactId>maven-project-info-reports-plugin</artifactId>
                    <version>2.7</version>
                    <configuration>
                        <dependencyLocationsEnabled>false</dependencyLocationsEnabled>
                    </configuration>
                </plugin>
                <plugin>
                    <groupId>org.apache.felix</groupId>
                    <artifactId>maven-bundle-plugin</artifactId>
                    <extensions>true</extensions>
                    <configuration>
                        <instructions>
                            <Export-Package>io.toolisticon.byoct</Export-Package>
                        </instructions>
                    </configuration>
                    <version>2.5.3</version>
                </plugin>
                <plugin>
                    <artifactId>maven-ejb-plugin</artifactId>
                    <version>2.5</version>
                </plugin>
                <plugin>
                    <artifactId>maven-ear-plugin</artifactId>
                    <version>2.10</version>
                </plugin>
                <plugin>
                    <groupId>org.mortbay.jetty</groupId>
                    <artifactId>jetty-maven-plugin</artifactId>
                    <version>8.1.15.v20140411</version>
                </plugin>
                <plugin>
                    <groupId>org.bsc.maven</groupId>
                    <artifactId>maven-processor-plugin</artifactId>
                    <version>2.2.4</version>
                </plugin>
                <plugin>
                    <groupId>org.apache.maven.plugins</groupId>
                    <artifactId>maven-assembly-plugin</artifactId>
                    <version>2.4</version>
                </plugin>

            </plugins>
        </pluginManagement>
        <plugins>
            <plugin>
                <groupId>org.codehaus.mojo</groupId>
                <artifactId>cobertura-maven-plugin</artifactId>
                <configuration>
                    <instrumentation>
                        <ignoreTrivial>true</ignoreTrivial>
                    </instrumentation>
                    <format>xml</format>
                    <maxmem>256m</maxmem>
                    <!-- aggregated reports for multi-module projects -->
                    <aggregate>true</aggregate>
                </configuration>
            </plugin>
            <plugin>
                <artifactId>maven-enforcer-plugin</artifactId>
                <executions>
                    <execution>
                        <id>enforce</id>
                        <goals>
                            <goal>enforce</goal>
                        </goals>
                        <configuration>
                            <rules>
                                <requireMavenVersion>
                                    <version>[3.0.4,)</version>
                                </requireMavenVersion>
                                <requireJavaVersion>
                                    <version>1.6</version>
                                </requireJavaVersion>
                                <bannedDependencies>
                                    <searchTransitive>false</searchTransitive>
                                    <excludes>
                                        <exclude>*</exclude>
                                    </excludes>
                                    <includes>
                                        <include>io.toolisticon.byoct:*</include>
                                        <include>io.toolisticon.spiap:*</include>
                                        <include>io.toolisticon.annotationprocessortoolkit:*</include>
                                        <include>*:*:*:*:test:*</include>
                                        <include>*:*:*:*:provided:*</include>
                                    </includes>
                                </bannedDependencies>
                            </rules>
                        </configuration>
                    </execution>
                </executions>
            </plugin>

            <plugin>
                <groupId>org.eluder.coveralls</groupId>
                <artifactId>coveralls-maven-plugin</artifactId>
                <version>3.0.1</version>
                <configuration>
                    <repoToken>${COVERALL_TOKEN}</repoToken>
                </configuration>
            </plugin>

            <plugin>
                <groupId>org.apache.felix</groupId>
                <artifactId>maven-bundle-plugin</artifactId>
            </plugin>
            <plugin>
                <artifactId>maven-failsafe-plugin</artifactId>
                <executions>
                    <execution>
                        <goals>
                            <goal>integration-test</goal>
                            <goal>verify</goal>
                        </goals>
                    </execution>
                </executions>
            </plugin>

            <plugin>
                <artifactId>maven-compiler-plugin</artifactId>
            </plugin>

            <plugin>
                <groupId>org.codehaus.mojo</groupId>
                <artifactId>build-helper-maven-plugin</artifactId>
                <version>1.9.1</version>
                <executions>
                    <execution>
                        <id>add-resource</id>
                        <phase>generate-resources</phase>
                        <goals>
                            <goal>add-resource</goal>
                        </goals>
                        <configuration>
                            <resources>
                                <resource>
                                    <directory>target/generated-sources/annotations</directory>
                                    <targetPath/>
                                </resource>
                            </resources>
                        </configuration>
                    </execution>
                </executions>
            </plugin>

           <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-shade-plugin</artifactId>
                <version>2.4.3</version>
                <executions>
                    <execution>
                        <phase>package</phase>
                        <goals>
                            <goal>shade</goal>
                        </goals>


                        <configuration>

                            <artifactSet>
                                <excludes>
                                    <exclude>io.toolisticon.spiap:*</exclude>
                                </excludes>
                                <includes>
                                    <include>io.toolisticon.annotationprocessortoolkit:*</include>
                                </includes>
                            </artifactSet>

                            <!--
                                usually you should repackage all referenced 3RD party libraries into your annotation processor jar.
                                    - because the annotation processor should always be linked as provided dependency when it is used.
                                    - to prevent a version conflict of your annotation processors dependencies and the applications dependencies.
                            -->

                            <relocations>
                                <relocation>
                                    <pattern>io.toolisticon.annotationprocessortoolkit</pattern>
                                    <shadedPattern>
                                        io.toolisticon.byoct.processor._3rdparty.io.toolisticon.annotationprocessortoolkit
                                    </shadedPattern>
                                </relocation>

                            </relocations>

                        </configuration>

                    </execution>
                </executions>


            </plugin>


        </plugins>

    </build>

    <profiles>
        <profile>
            <id>doclint-java8-disable</id>
            <activation>
                <jdk>[1.8,)</jdk>
            </activation>
            <build>
                <plugins>
                    <plugin>
                        <artifactId>maven-javadoc-plugin</artifactId>
                        <configuration>
                            <additionalparam>-Xdoclint:none</additionalparam>
                        </configuration>
                    </plugin>
                </plugins>
            </build>
        </profile>
        <profile>
            <id>checkstyle</id>
            <build>
                <plugins>
                    <plugin>
                        <groupId>org.codehaus.mojo</groupId>
                        <artifactId>findbugs-maven-plugin</artifactId>
                        <version>3.0.0</version>
                        <configuration>
                            <xmlOutput>true</xmlOutput>
                            <findbugsXmlOutput>true</findbugsXmlOutput>
                            <!--<findbugsXmlWithMessages>true</findbugsXmlWithMessages>-->
                            <excludeFilterFile>config/findbugs-excludes.xml</excludeFilterFile>
                        </configuration>
                        <executions>
                            <execution>
                                <phase>verify</phase>
                                <goals>
                                    <goal>check</goal>
                                </goals>
                            </execution>
                        </executions>
                    </plugin>
                    <plugin>
                        <groupId>org.apache.maven.plugins</groupId>
                        <artifactId>maven-checkstyle-plugin</artifactId>
                        <version>2.12.1</version>
                        <executions>
                            <execution>
                                <id>validate</id>
                                <phase>validate</phase>
                                <configuration>
                                    <configLocation>${session.executionRootDirectory}/config/sun_checks.xml
                                    </configLocation>
                                    <encoding>UTF-8</encoding>
                                    <consoleOutput>true</consoleOutput>
                                    <failOnViolation>true</failOnViolation>
                                    <failsOnError>true</failsOnError>
                                    <violationSeverity>warn</violationSeverity>
                                </configuration>
                                <goals>
                                    <goal>check</goal>
                                </goals>
                            </execution>
                        </executions>
                    </plugin>
                    <plugin>
                        <artifactId>maven-pmd-plugin</artifactId>
                        <version>3.3</version>
                    </plugin>
                </plugins>
            </build>
        </profile>
        <profile>
            <id>ReleaseJavadoc</id>
            <build>
                <plugins>
                    <plugin>
                        <artifactId>maven-javadoc-plugin</artifactId>
                        <configuration>
                            <reportOutputDirectory>${project.basedir}/docs/assets/javadoc</reportOutputDirectory>
                            <destDir>${project.version}</destDir>
                            <excludePackageNames>io.toolisticon.example.*</excludePackageNames>
                        </configuration>
                    </plugin>
                </plugins>
            </build>

        </profile>
    </profiles>

    <dependencyManagement>
        <dependencies>



            <!-- spiap dependencies -->
            <dependency>
                <groupId>io.toolisticon.spiap</groupId>
                <artifactId>spiap-api</artifactId>
                <version>${spiap.version}</version>
                <scope>provided</scope>
            </dependency>

            <dependency>
                <groupId>io.toolisticon.spiap</groupId>
                <artifactId>spiap-processor</artifactId>
                <version>${spiap.version}</version>
                <scope>provided</scope>
            </dependency>

            <!-- annotation processot toolkit dependencies-->
            <dependency>
                <groupId>io.toolisticon.annotationprocessortoolkit</groupId>
                <artifactId>annotationprocessor</artifactId>
                <version>${annotationProcessorToolkit.version}</version>
            </dependency>



            <!-- Test dependencies -->
            <dependency>
                <groupId>junit</groupId>
                <artifactId>junit</artifactId>
                <version>${junit.version}</version>
                <scope>test</scope>
            </dependency>

            <dependency>
                <groupId>org.hamcrest</groupId>
                <artifactId>hamcrest-library</artifactId>
                <version>${hamcrest.version}</version>
                <scope>test</scope>
            </dependency>

            <dependency>
                <groupId>org.mockito</groupId>
                <artifactId>mockito-core</artifactId>
                <version>${mockito.version}</version>
                <scope>test</scope>
            </dependency>

            <dependency>
                <groupId>io.toolisticon.annotationprocessortoolkit</groupId>
                <artifactId>testhelper</artifactId>
                <version>${annotationProcessorToolkit.version}</version>
                <scope>test</scope>
            </dependency>

            <dependency>
                <groupId>io.toolisticon.compiletesting</groupId>
                <artifactId>compiletesting</artifactId>
                <version>${toolisticon-compile-testing.version}</version>
                <scope>test</scope>
            </dependency>

        </dependencies>
    </dependencyManagement>




    <dependencies>

        <!-- TODO: ADD YOUR ANNOTATION API DEPENDENCY AS PROVIDED SCOPE HERE -->


        <dependency>
            <groupId>io.toolisticon.annotationprocessortoolkit</groupId>
            <artifactId>annotationprocessor</artifactId>
        </dependency>

        <dependency>
            <groupId>io.toolisticon.spiap</groupId>
            <artifactId>spiap-api</artifactId>
        </dependency>

        <dependency>
            <groupId>io.toolisticon.spiap</groupId>
            <artifactId>spiap-processor</artifactId>
        </dependency>

        <dependency>
            <groupId>io.toolisticon.compiletesting</groupId>
            <artifactId>compiletesting</artifactId>
        </dependency>

        <dependency>
            <groupId>io.toolisticon.annotationprocessortoolkit</groupId>
            <artifactId>testhelper</artifactId>
        </dependency>

        <dependency>
            <groupId>junit</groupId>
            <artifactId>junit</artifactId>
        </dependency>

    </dependencies>

    !{/static}
</project>
