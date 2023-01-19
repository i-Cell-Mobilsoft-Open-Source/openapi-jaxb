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

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Optional;
import java.util.logging.Logger;

import org.apache.commons.lang3.StringUtils;
import org.eclipse.microprofile.openapi.annotations.enums.SchemaType;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

import com.sun.tools.xjc.model.CPropertyInfo;
import com.sun.tools.xjc.outline.FieldOutline;
import com.sun.tools.xjc.reader.xmlschema.bindinfo.BindInfo;
import com.sun.xml.xsom.XSAnnotation;
import com.sun.xml.xsom.XSAttributeUse;
import com.sun.xml.xsom.XSComponent;
import com.sun.xml.xsom.XSElementDecl;
import com.sun.xml.xsom.XSFacet;
import com.sun.xml.xsom.XSParticle;
import com.sun.xml.xsom.XSSimpleType;
import com.sun.xml.xsom.XSTerm;
import com.sun.xml.xsom.XmlString;

import hu.icellmobilsoft.jaxb.openapi.constants.SchemaFields;
import hu.icellmobilsoft.jaxb.openapi.process.configuration.OpenApiPluginConfiguration;

/**
 * {@link FieldOutline} implementation for {@link SchemaCalculator}
 *
 * @author mark.petrenyi
 * @since 2.0.0
 */
public class FieldSchemaCalculator implements SchemaCalculator<FieldOutline> {

    private static final String NEW_LINE = "\n";
    private static final String LIST_ITEM_MARK = "* ";
    private static final String COLON_MARK = ": ";
    private static final String RESTRICTIONS = "Restrictions";

    private static final FieldSchemaCalculator INSTANCE = new FieldSchemaCalculator();

    /**
     * Gets instance.
     *
     * @return the instance
     */
    public static FieldSchemaCalculator getInstance() {
        return INSTANCE;
    }

    private Logger log = Logger.getLogger(FieldSchemaCalculator.class.getName());

    @Override
    public Optional<SchemaHolder> calculateSchema(FieldOutline field, OpenApiPluginConfiguration openApiPluginConfiguration) {
        if (field == null) {
            return Optional.empty();
        }
        CPropertyInfo propertyInfo = field.getPropertyInfo();
        String javaName = propertyInfo.getName(false);
        String xmlName = getXmlName(propertyInfo);

        SchemaHolder schema = new SchemaHolder();
        String name = openApiPluginConfiguration.isPreferJavaName() ? javaName : xmlName;
        schema.setName(name);
        schema.setTitle(name);
        if (!openApiPluginConfiguration.isSkipExtensions() && !StringUtils.equals(name, xmlName)) {
            schema.xml().setName(name);
        }
        addArrayProperties(schema, propertyInfo);
        String description = getDescription(propertyInfo);
        StringBuilder restrictionBuilder = new StringBuilder();

        addRestrictions(schema, restrictionBuilder, propertyInfo, openApiPluginConfiguration);

        String restrictions = restrictionBuilder.toString();
        StringBuilder descriptionBuilder = new StringBuilder();
        if (StringUtils.isNotBlank(description)) {
            descriptionBuilder.append(description);
        }
        if (StringUtils.isNotBlank(restrictions) && openApiPluginConfiguration.isVerboseDescriptions()) {
            descriptionBuilder.append(NEW_LINE).append(NEW_LINE)//
                    .append(RESTRICTIONS).append(COLON_MARK)//
                    .append(restrictions);
        }

        schema.setDescription(StringUtils.trim(descriptionBuilder.toString()));
        return Optional.of(schema);
    }

    private String getXmlName(CPropertyInfo property) {
        String name = null;
        // xsd szerinti nevet nézzük
        XSComponent schemaComponent = property.getSchemaComponent();
        if (schemaComponent instanceof XSParticle) {
            XSParticle particle = (XSParticle) schemaComponent;
            XSTerm xsTerm = particle.getTerm();
            if (xsTerm != null && xsTerm.isElementDecl()) {
                XSElementDecl elementDecl = xsTerm.asElementDecl();
                name = elementDecl.getName();
            }
        }

        // ha nem sikerül java nevet
        if (name == null) {
            name = property.getName(false);
        }
        return name;
    }

    /**
     * Extract value from {@code <xs:annotation><xs:documentation>} if exists.
     *
     * @param property
     *            the property info
     * @return value from {@code <xs:annotation><xs:documentation>} or <code>null</code> if {@code <xs:annotation><xs:documentation>} does not exists.
     */
    protected String getDescription(CPropertyInfo property) {
        if (property == null) {
            return null;
        }
        XSComponent schemaComponent = property.getSchemaComponent();
        if (schemaComponent instanceof XSParticle) {
            XSAnnotation annotation = ((XSParticle) schemaComponent).getTerm().getAnnotation();
            if (annotation != null) {
                Object annotationObj = annotation.getAnnotation();
                if (annotationObj instanceof BindInfo && ((BindInfo) annotationObj).getDocumentation() != null) {
                    return ((BindInfo) annotationObj).getDocumentation();
                }
            }
        }
        return null;
    }

    /**
     * Obtains the given property from targetClass using propertyName, if its a collection property {@link Schema#type()} is set to
     * {@link SchemaType#ARRAY}, additionally {@link Schema#maxItems()}, {@link Schema#minItems()} is set if the collection is bounded.
     *
     * @param schema
     *            {@link SchemaHolder} representation
     * @param propertyInfo
     *            field property info
     */
    private void addArrayProperties(SchemaHolder schema, CPropertyInfo propertyInfo) {
        boolean collection = propertyInfo.isCollection();
        if (collection) {
            schema.setType(SchemaType.ARRAY);
        }
        XSComponent schemaComponent = propertyInfo.getSchemaComponent();
        if (schemaComponent instanceof XSParticle) {
            XSParticle particle = (XSParticle) schemaComponent;
            BigInteger maxOccurs = particle.getMaxOccurs();
            if (collection && maxOccurs != null && XSParticle.UNBOUNDED != maxOccurs.intValue()) {
                schema.setMaxItems(maxOccurs.intValue());
            }

            BigInteger minOccurs = particle.getMinOccurs();
            if (minOccurs != null) {
                int minOccursInt = minOccurs.intValue();
                if (collection) {
                    schema.setMinItems(minOccursInt);
                }
                if (minOccursInt > 0) {
                    schema.setRequired(true);
                }
            }

        }

        if (schemaComponent instanceof XSElementDecl) {
            XSElementDecl elementDecl = (XSElementDecl) schemaComponent;

            XmlString defaultValue = elementDecl.getDefaultValue();
            if (defaultValue != null) {
                schema.setDefaultValue(defaultValue.toString());
            }
        }

    }

    /**
     * Adds <xs:restriction>-s to {@link Schema} (ie."pattern", "length" etc.)
     *
     * @param schema
     *            {@link SchemaHolder} representation
     * @param restrictionBuilder
     *            restriction related description builder
     * @param property
     *            field property info
     */
    private void addRestrictions(SchemaHolder schema, StringBuilder restrictionBuilder, CPropertyInfo property,
            OpenApiPluginConfiguration openApiPluginConfiguration) {
        XSComponent schemaComponent = property.getSchemaComponent();
        if (schemaComponent instanceof XSParticle) {
            XSParticle particle = (XSParticle) schemaComponent;
            XSTerm xsTerm = particle.getTerm();
            if (xsTerm != null && xsTerm instanceof XSElementDecl && ((XSElementDecl) xsTerm).getType() != null
                    && ((XSElementDecl) xsTerm).getType().asSimpleType() != null) {
                addSimpleTypeRestrictions(schema, restrictionBuilder, ((XSElementDecl) xsTerm).getType().asSimpleType());
            }
        } else if (schemaComponent instanceof XSAttributeUse) {
            XSAttributeUse attributeUse = (XSAttributeUse) schemaComponent;
            if (!openApiPluginConfiguration.isSkipExtensions()) {
                XmlHolder xml = schema.xml();
                xml.setAttribute(true);
            }
            if (attributeUse.isRequired()) {
                schema.setRequired(true);
            }
            if (attributeUse.getDefaultValue() != null) {
                schema.setDefaultValue(attributeUse.getDefaultValue().toString());
            }
            if (attributeUse.getDecl() != null && attributeUse.getDecl().getType() != null) {
                addSimpleTypeRestrictions(schema, restrictionBuilder, attributeUse.getDecl().getType());
            }
        }
    }

    private void addSimpleTypeRestrictions(SchemaHolder schema, StringBuilder restrictionBuilder, XSSimpleType xsSimpleType) {
        addLength(schema, xsSimpleType, restrictionBuilder);
        addExtremum(schema, xsSimpleType, true, restrictionBuilder);
        addExtremum(schema, xsSimpleType, false, restrictionBuilder);
        addPattern(schema, xsSimpleType, restrictionBuilder);
    }

    private void addPattern(SchemaHolder schema, XSSimpleType xsSimpleType, StringBuilder restrictionBuilder) {
        String pattern = getFacetAsString(xsSimpleType, XSFacet.FACET_PATTERN);
        if (pattern != null) {
            schema.setPattern(pattern);
            appendRestriction(restrictionBuilder, SchemaFields.PATTERN, pattern);
        }
    }

    private void addLength(SchemaHolder schema, XSSimpleType xsSimpleType, StringBuilder restrictionBuilder) {
        Integer length = getFacetAsInteger(xsSimpleType, XSFacet.FACET_LENGTH);
        Integer maxLength = null;
        Integer minLength = null;
        if (length != null) {
            maxLength = length;
            minLength = length;
        } else {

            maxLength = getFacetAsInteger(xsSimpleType, XSFacet.FACET_MAXLENGTH);
            minLength = getFacetAsInteger(xsSimpleType, XSFacet.FACET_MINLENGTH);
        }
        if (maxLength != null) {
            schema.setMaxLength(maxLength);
            appendRestriction(restrictionBuilder, SchemaFields.MAX_LENGTH, maxLength);
        }
        if (minLength != null) {
            schema.setMinLength(minLength);
            appendRestriction(restrictionBuilder, SchemaFields.MIN_LENGTH, minLength);
        }
    }

    private void addExtremum(SchemaHolder schema, XSSimpleType xsSimpleType, boolean isMaximum, StringBuilder restrictionBuilder) {
        String extremum = getFacetAsString(xsSimpleType, isMaximum ? XSFacet.FACET_MAXEXCLUSIVE : XSFacet.FACET_MINEXCLUSIVE);
        Boolean exlusive = true;
        if (extremum == null) {
            exlusive = false;
            extremum = getFacetAsString(xsSimpleType, isMaximum ? XSFacet.FACET_MAXINCLUSIVE : XSFacet.FACET_MININCLUSIVE);
        }
        if (extremum != null) {
            try {
                BigDecimal value = new BigDecimal(extremum);
                if (isMaximum) {
                    schema.setMaximum(value);
                    schema.setExclusiveMaximum(exlusive);
                } else {
                    schema.setMinimum(value);
                    schema.setExclusiveMinimum(exlusive);
                }
            } catch (NumberFormatException e) {
                log.fine(String.format("Extremum: [%s] could not be set for SimpleType:[%s], since it cannot be parsed as BigDecimal!", extremum,
                        xsSimpleType));
            }
            appendRestriction(restrictionBuilder, isMaximum ? SchemaFields.MAXIMUM : SchemaFields.MINIMUM, extremum);
            appendRestriction(restrictionBuilder, isMaximum ? SchemaFields.EXCLUSIVE_MAXIMUM : SchemaFields.EXCLUSIVE_MINIMUM, exlusive);
        }
    }

    private void appendRestriction(StringBuilder restrictionBuilder, String key, Object value) {
        restrictionBuilder.append(NEW_LINE).append(LIST_ITEM_MARK)//
                .append(key).append(COLON_MARK).append(value);
    }

    private Integer getFacetAsInteger(XSSimpleType xsSimpleType, String facetName) {
        String stringValue = getFacetAsString(xsSimpleType, facetName);
        if (stringValue != null) {
            try {
                return Integer.valueOf(stringValue);
            } catch (NumberFormatException e) {
                log.warning(
                        String.format("Could not obtain facet : [%s]=[%s] as Integer from SimpleType:[%s]!", facetName, stringValue, xsSimpleType));
                return null;
            }
        }
        return null;
    }

    private String getFacetAsString(XSSimpleType xsSimpleType, String facetName) {
        XSFacet facet = xsSimpleType.getFacet(facetName);
        String value = null;
        if (facet != null) {
            value = facet.getValue().value;
        }
        return value;
    }
}
