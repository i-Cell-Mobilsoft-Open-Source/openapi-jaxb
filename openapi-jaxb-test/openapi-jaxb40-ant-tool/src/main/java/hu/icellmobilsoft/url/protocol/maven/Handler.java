/*-
 * #%L
 * Coffee
 * %%
 * Copyright (C) 2020 i-Cell Mobilsoft Zrt.
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

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLStreamHandler;
import java.text.MessageFormat;

import org.apache.commons.lang3.StringUtils;

/**
 * URL protocol kezeles ilyen formatumra:
 *
 * <pre>
 * maven:hu.icellmobilsoft.coffee:coffee-dto-xsd:jar::!/xsd/hu/icellmobilsoft/coffee/dto/common/common.xsd
 * </pre>
 *
 * Ennek a formatuma a kovetkezo: <code>maven:groupId:atifactId:package:version:!fajl_path</code>
 * <ul>
 * <li>protocol - URL schema protocol, ebben az esetben "maven"</li>
 * <li>hu.icellmobilsoft.coffee.dto.xsd - maven groupId</li>
 * <li>coffee-dto-xsd - maven artifactId</li>
 * <li>jar - maven package</li>
 * <li>maven version</li>
 * </ul>
 *
 * @author imre.scheffer
 * @since 1.0.0
 */
public class Handler extends URLStreamHandler {

    private static final String SEPARATOR = "!";
    public static final String DIR_SEPARATOR = "/";

    private ClassLoader classLoader;

    public Handler() {
        this(Handler.class.getClassLoader());
    }

    public Handler(ClassLoader classLoader) {
        this.classLoader = classLoader;
    }

    /** {@inheritDoc} */
    @Override
    protected URLConnection openConnection(URL url) throws IOException {
        String path = url.getPath();
        String originalPath = path;

        // levagjuk az elso "!" jelig majd a maradekot a classpathban keressuk
        if (StringUtils.contains(path, SEPARATOR)) {
            path = StringUtils.substringAfter(path, SEPARATOR);
        }
        URL classPathUrl = getResource(path);
        // Ha nem sikerült levágjuk a path kezdő "/"-t ant jar-okban keres
        if (classPathUrl == null && StringUtils.startsWith(path, DIR_SEPARATOR)) {
            path = StringUtils.substringAfter(path, DIR_SEPARATOR);
            classPathUrl = getResource(path);
        }
        if (classPathUrl == null) {
            throw new IOException(MessageFormat.format("Could not find resource with path: [{0}]", originalPath));
        }
        return classPathUrl.openConnection();
    }

    private URL getResource(String path) {

        URL result = null;
        if (classLoader != null) {
            result = getResourceRecursively(classLoader, path);
        }
        return result;
    }

    private URL getResourceRecursively(ClassLoader classLoader, String path) {
        URL result = null;
        if (classLoader != null) {
            result = classLoader.getResource(path);
            if (result == null) {
                result = getResourceRecursively(classLoader.getParent(), path);
            }
        }
        return result;
    }
}
