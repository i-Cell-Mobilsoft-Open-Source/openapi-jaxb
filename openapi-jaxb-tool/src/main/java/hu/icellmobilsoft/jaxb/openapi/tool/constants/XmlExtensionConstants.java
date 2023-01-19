/*-
 * #%L
 * OpenAPI JAXB Plugin
 * %%
 * Copyright (C) 2019 - 2023 i-Cell Mobilsoft Zrt.
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
package hu.icellmobilsoft.jaxb.openapi.tool.constants;

import org.eclipse.microprofile.openapi.annotations.extensions.Extension;
import org.eclipse.microprofile.openapi.models.media.XML;

/**
 * Constants for XML extension
 *
 * @author mark.petrenyi
 * @since 2.1.0
 */
public class XmlExtensionConstants {
    private XmlExtensionConstants() {
    }

    private static final String EXTENSION_PREFIX = "x-";
    private static final String XML = EXTENSION_PREFIX + "xml-";
    /**
     * {@link Extension#name()} constant representing {@link XML#name(String)}
     */
    public static final String XML_NAME = XML + "name";

    /**
     * {@link Extension#name()} constant representing {@link XML#namespace(String)}
     */
    public static final String XML_NAMESPACE = XML + "namespace";

    /**
     * {@link Extension#name()} constant representing {@link XML#prefix(String)}
     */
    public static final String XML_PREFIX = XML + "prefix";

    /**
     * {@link Extension#name()} constant representing {@link XML#attribute(Boolean)}
     */
    public static final String XML_ATTRIBUTE = XML + "attribute";

}
