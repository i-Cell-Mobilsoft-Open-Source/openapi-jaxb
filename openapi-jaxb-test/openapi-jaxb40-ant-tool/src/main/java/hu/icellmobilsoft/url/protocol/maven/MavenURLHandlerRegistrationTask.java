/*-
 * #%L
 * OpenAPI JAXB Plugin
 * %%
 * Copyright (C) 2019 - 2022 i-Cell Mobilsoft Zrt.
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */
package hu.icellmobilsoft.url.protocol.maven;

import java.net.URL;

import org.apache.tools.ant.AntClassLoader;
import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Task;
import org.apache.tools.ant.types.Path;

/**
 * Ant task maven: url protocol kezelő regisztrálására <br>
 * maven-antrun-plugin-nal használva regisztrálja a maven compile dependencyket, egyébként a {@code xsdclasspath} attribute/element-en keresztül kell
 * megadni a catlog-ban szereplő jarokat. <br>
 * Használat:
 *
 * <pre>
 * {@code
 * <taskdef name="register-maven-url-handler" classname="hu.icellmobilsoft.url.protocol.maven.MavenURLHandlerRegistrationTask"/>
 * <register-maven-url-handler/>
 * }
 * </pre>
 *
 * Külön classpath-al:
 * 
 * <pre>
 *  {@code
 *  <taskdef name="register-maven-url-handler" classname="hu.icellmobilsoft.url.protocol.maven.MavenURLHandlerRegistrationTask"/>
 *  <register-maven-url-handler xsdclasspath="some-lib.jar"/>
 *  }
 * </pre>
 *
 * Külön, több classpath-al:
 * 
 * <pre>
 *  {@code
 *  <taskdef name="register-maven-url-handler" classname="hu.icellmobilsoft.url.protocol.maven.MavenURLHandlerRegistrationTask"/>
 *  <register-maven-url-handler>
 *      <xsdclasspath path="xsd-dependcy-1.jar"/>
 *      <xsdclasspath path="xsd-dependcy-2.jar"/>
 *  </register-maven-url-handler>
 *  }
 * </pre>
 * 
 * 
 * @author mark.petrenyi
 * @since 2.0.0
 */
public class MavenURLHandlerRegistrationTask extends Task {

    private final Path xsdClasspath;

    /**
     * Instantiates a new MavenURLHandlerRegistrationTask.
     */
    public MavenURLHandlerRegistrationTask() {
        super();
        xsdClasspath = new Path(null);
    }

    @Override
    public void execute() throws BuildException {

        addMavenCompileClasspath();
        URL.setURLStreamHandlerFactory(new MavenURLStreamHandlerProvider(new AntClassLoader(getProject(), xsdClasspath)));
    }

    private void addMavenCompileClasspath() {
        if (getProject() == null) {
            return;
        }

        Path mvn = getProject().getReference("maven.compile.classpath");
        if (mvn != null) {
            // called from maven ant run plugin - use the compile classpath
            addXsdClasspath(mvn);
        }
    }

    /**
     * Add xsd classpath. (supports element declaration)
     *
     * @param xsdclasspath
     *            the xsdclasspath
     */
    public void addXsdClasspath(Path xsdclasspath) {
        this.xsdClasspath.createPath().append(xsdclasspath);
    }

    /**
     * Sets xsd classpath. (supports attribute declaration)
     *
     * @param xsdclasspath
     *            the xsdclasspath
     */
    public void setXsdClasspath(Path xsdclasspath) {
        this.xsdClasspath.createPath().append(xsdclasspath);
    }
}
