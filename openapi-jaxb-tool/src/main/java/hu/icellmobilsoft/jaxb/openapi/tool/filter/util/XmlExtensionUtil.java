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
package hu.icellmobilsoft.jaxb.openapi.tool.filter.util;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;

import org.apache.commons.collections4.MapUtils;
import org.eclipse.microprofile.openapi.OASFactory;
import org.eclipse.microprofile.openapi.models.media.Schema;
import org.eclipse.microprofile.openapi.models.media.XML;

import hu.icellmobilsoft.jaxb.openapi.tool.constants.XmlExtensionConstants;

/**
 * Util class for handling {@link XmlExtensionConstants} extensions.<br>
 *
 * @author mark.petrenyi
 * @since 2.1.0
 */
public class XmlExtensionUtil {

    private XmlExtensionUtil() {
    }

    /**
     * Replaces every {@link XmlExtensionConstants} extension with the corresponding {@link XML} interface field.
     *
     * @param schema
     *            the input schema
     * @return the replaced schema
     */
    public static Schema replaceXmlExtensions(Schema schema) {
        if (schema == null || MapUtils.isEmpty(schema.getExtensions())) {
            return schema;
        }
        Map<String, Object> extensions = new HashMap<>(schema.getExtensions());

        if (extensions.containsKey(XmlExtensionConstants.XML_NAME)) {
            setXmlProperty(schema, (String) extensions.remove(XmlExtensionConstants.XML_NAME), XML::setName);
        }

        if (extensions.containsKey(XmlExtensionConstants.XML_NAMESPACE)) {
            setXmlProperty(schema, (String) extensions.remove(XmlExtensionConstants.XML_NAMESPACE), XML::setNamespace);

        }

        if (extensions.containsKey(XmlExtensionConstants.XML_PREFIX)) {
            setXmlProperty(schema, (String) extensions.remove(XmlExtensionConstants.XML_PREFIX), XML::setPrefix);
        }

        if (extensions.containsKey(XmlExtensionConstants.XML_ATTRIBUTE)) {
            setXmlProperty(schema, (Boolean) extensions.remove(XmlExtensionConstants.XML_ATTRIBUTE), XML::setAttribute);
        }
        schema.setExtensions(extensions);
        return schema;
    }

    private static <T> void setXmlProperty(Schema schema, T value, BiConsumer<XML, T> xmlPropertySetter) {
        XML xml = schema.getXml() != null ? schema.getXml() : OASFactory.createXML();
        xmlPropertySetter.accept(xml, value);
        schema.setXml(xml);
    }

}
