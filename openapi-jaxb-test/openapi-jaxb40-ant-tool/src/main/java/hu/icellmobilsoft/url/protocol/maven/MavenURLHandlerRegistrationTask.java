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

public class MavenURLHandlerRegistrationTask extends Task {

    private final Path xsdClasspath;

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

    public void addXsdClasspath(Path xsdclasspath) {
        this.xsdClasspath.createPath().append(xsdclasspath);
    }

    public void setXsdClasspath(Path xsdclasspath) {
        this.xsdClasspath.createPath().append(xsdclasspath);
    }
}
