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

import java.util.Optional;
import java.util.logging.Logger;

import javax.xml.namespace.QName;

import org.apache.commons.lang3.StringUtils;

import com.sun.tools.xjc.outline.ClassOutline;
import com.sun.tools.xjc.reader.xmlschema.bindinfo.BindInfo;
import com.sun.xml.xsom.XSAnnotation;

import hu.icellmobilsoft.jaxb.openapi.process.configuration.OpenApiPluginConfiguration;

/**
 * {@link ClassOutline} implementation for {@link SchemaCalculator}
 *
 * @author mark.petrenyi
 * @since 2.0.0
 */
public class ClassSchemaCalculator implements SchemaCalculator<ClassOutline> {

    private static final ClassSchemaCalculator INSTANCE = new ClassSchemaCalculator();

    /**
     * Gets instance.
     *
     * @return the instance
     */
    public static ClassSchemaCalculator getInstance() {
        return INSTANCE;
    }

    private Logger log = Logger.getLogger(ClassSchemaCalculator.class.getName());

    @Override
    public Optional<SchemaHolder> calculateSchema(ClassOutline classOutline, OpenApiPluginConfiguration openApiPluginConfiguration) {
        if (classOutline == null || classOutline.target == null || classOutline.ref == null) {
            return Optional.empty();
        }

        SchemaHolder schema = new SchemaHolder();
        String name;
        String namespace = null;
        String prefix = null;
        if (classOutline.target.isElement()) {
            name = classOutline.target.getElementName().getLocalPart();
            namespace = classOutline.target.getElementName().getNamespaceURI();
        } else if (classOutline.target.getTypeName() != null) {
            QName typeName = classOutline.target.getTypeName();
            name = typeName.getLocalPart();
            namespace = typeName.getNamespaceURI();
            prefix = typeName.getPrefix();
        } else {
            name = classOutline.ref.name();
        }
        schema.setName(name);
        String documentation = getDocumentation(classOutline);
        schema.setDescription(documentation);
        if (!openApiPluginConfiguration.isSkipExtensions() && !StringUtils.isAllBlank(namespace, prefix)) {
            XmlHolder xml = schema.xml();
            xml.setNamespace(namespace);
            xml.setPrefix(prefix);
        }
        return Optional.of(schema);
    }

    private String getDocumentation(final ClassOutline o) {
        if (o == null || o.target == null) {
            return null;
        }
        XSAnnotation annotation = o.getTarget().getSchemaComponent().getAnnotation();
        if (annotation != null && annotation.getAnnotation() instanceof BindInfo) {
            return StringUtils.trim(((BindInfo) annotation.getAnnotation()).getDocumentation());
        }
        return null;
    }
}
