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
package hu.icellmobilsoft.jaxb.openapi.process;

import org.apache.commons.lang3.StringUtils;
import org.eclipse.microprofile.openapi.annotations.extensions.Extension;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

import com.sun.codemodel.JAnnotationArrayMember;
import com.sun.codemodel.JAnnotationUse;

import hu.icellmobilsoft.jaxb.openapi.constants.ExtensionFields;
import hu.icellmobilsoft.jaxb.openapi.tool.constants.XmlExtensionConstants;

/**
 * Class for handling properties of {@link org.eclipse.microprofile.openapi.models.media.XML}
 *
 * @author mark.petrenyi
 * @since 2.1.0
 */
public class XmlHolder {
    private String name;
    private String namespace;
    private String prefix;
    private Boolean attribute;

    /**
     * Annotate {@link Schema#extensions()} with {@link Extension} annotation using the XmlHolder's parameters
     *
     * @param extensionArray
     *            the {@link Schema#extensions()} array
     */
    public void annotate(JAnnotationArrayMember extensionArray) {
        if (extensionArray == null) {
            return;
        }
        if (StringUtils.isNotBlank(name)) {
            addExtension(extensionArray, XmlExtensionConstants.XML_NAME, name);
        }
        if (StringUtils.isNotBlank(namespace)) {
            addExtension(extensionArray, XmlExtensionConstants.XML_NAMESPACE, namespace);
        }
        if (StringUtils.isNotBlank(prefix)) {
            addExtension(extensionArray, XmlExtensionConstants.XML_PREFIX, prefix);
        }
        if (attribute != null) {
            addExtension(extensionArray, XmlExtensionConstants.XML_ATTRIBUTE, attribute);
        }
    }

    private void addExtension(JAnnotationArrayMember extensionArray, String extensionName, String value) {
        JAnnotationUse extension = extensionArray.annotate(Extension.class);
        extension.param(ExtensionFields.NAME, extensionName);
        extension.param(ExtensionFields.VALUE, value);
    }

    private void addExtension(JAnnotationArrayMember extensionArray, String extensionName, boolean value) {
        JAnnotationUse extension = extensionArray.annotate(Extension.class);
        extension.param(ExtensionFields.NAME, extensionName);
        extension.param(ExtensionFields.VALUE, String.valueOf(value));
        extension.param(ExtensionFields.PARSE_VALUE, true);
    }

    /**
     * Gets name.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets name.
     *
     * @param name
     *            the name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets namespace.
     *
     * @return the namespace
     */
    public String getNamespace() {
        return namespace;
    }

    /**
     * Sets namespace.
     *
     * @param namespace
     *            the namespace
     */
    public void setNamespace(String namespace) {
        this.namespace = namespace;
    }

    /**
     * Gets prefix.
     *
     * @return the prefix
     */
    public String getPrefix() {
        return prefix;
    }

    /**
     * Sets prefix.
     *
     * @param prefix
     *            the prefix
     */
    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    /**
     * Gets attribute.
     *
     * @return the attribute
     */
    public Boolean getAttribute() {
        return attribute;
    }

    /**
     * Sets attribute.
     *
     * @param attribute
     *            the attribute
     */
    public void setAttribute(Boolean attribute) {
        this.attribute = attribute;
    }
}
