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
package hu.icellmobilsoft.jaxb.openapi.tool.filter;

import org.eclipse.microprofile.openapi.OASFilter;
import org.eclipse.microprofile.openapi.models.media.Schema;
import org.eclipse.microprofile.openapi.models.media.XML;

import hu.icellmobilsoft.jaxb.openapi.tool.constants.XmlExtensionConstants;
import hu.icellmobilsoft.jaxb.openapi.tool.filter.util.XmlExtensionUtil;

/**
 * Example implementation for {@link OASFilter} handling {@link XmlExtensionConstants} extensions.<br>
 * 
 * Replaces every {@link XmlExtensionConstants} extension with the corresponding {@link XML} interface field.
 * 
 * @author mark.petrenyi
 * @since 2.1.0
 */
public class XmlExtensionOpenAPIFilter implements OASFilter {

    @Override
    public Schema filterSchema(Schema schema) {
        return XmlExtensionUtil.replaceXmlExtensions(schema);
    }
}
