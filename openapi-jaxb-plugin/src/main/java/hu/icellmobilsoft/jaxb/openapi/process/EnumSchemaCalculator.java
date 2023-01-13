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

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.logging.Logger;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.eclipse.microprofile.openapi.annotations.enums.SchemaType;

import com.sun.tools.xjc.model.CEnumConstant;
import com.sun.tools.xjc.outline.EnumConstantOutline;
import com.sun.tools.xjc.outline.EnumOutline;
import com.sun.tools.xjc.reader.xmlschema.bindinfo.BindInfo;
import com.sun.xml.xsom.XSAnnotation;

/**
 * {@link EnumOutline} implementation for {@link SchemaCalculator}
 *
 * @author mark.petrenyi
 * @since 2.0.0
 */
public class EnumSchemaCalculator implements SchemaCalculator<EnumOutline> {

    private static final EnumSchemaCalculator INSTANCE = new EnumSchemaCalculator();

    /**
     * Gets instance.
     *
     * @return the instance
     */
    public static EnumSchemaCalculator getInstance() {
        return INSTANCE;
    }

    private Logger log = Logger.getLogger(EnumSchemaCalculator.class.getName());

    @Override
    public Optional<SchemaHolder> calculateSchema(EnumOutline enumOutline, boolean verboseDescriptions) {
        if (enumOutline == null) {
            return Optional.empty();
        }
        SchemaHolder schema = new SchemaHolder();
        schema.setType(SchemaType.STRING);
        schema.setDescription(getDocumentation(enumOutline));
        List<EnumConstantOutline> constants = enumOutline.constants;
        if (CollectionUtils.isNotEmpty(constants)) {
            // gets the actual lexical value defined in the xsd instead of the enum name,
            // since it can be escaped in java (ie. xsd: "example.test" vs java enum: EXAMPLE_TEST)
            constants.stream()//
                    .map(eco -> eco.target).filter(Objects::nonNull)//
                    .map(CEnumConstant::getLexicalValue).filter(Objects::nonNull)//
                    .forEach(schema::addEnumerationValue);
        }
        return Optional.of(schema);
    }

    private String getDocumentation(final EnumOutline o) {
        if (o == null || o.target == null) {
            return null;
        }
        XSAnnotation annotation = o.target.getSchemaComponent().getAnnotation();
        if (annotation != null && annotation.getAnnotation() instanceof BindInfo) {
            return StringUtils.trim(((BindInfo) annotation.getAnnotation()).getDocumentation());
        }
        return null;
    }
}
