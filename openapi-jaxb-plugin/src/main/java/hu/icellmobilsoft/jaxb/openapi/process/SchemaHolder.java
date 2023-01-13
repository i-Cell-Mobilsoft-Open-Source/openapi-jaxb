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
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.eclipse.microprofile.openapi.annotations.enums.SchemaType;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

import com.sun.codemodel.JAnnotatable;
import com.sun.codemodel.JAnnotationArrayMember;
import com.sun.codemodel.JAnnotationUse;

import hu.icellmobilsoft.jaxb.openapi.constants.SchemaFields;

/**
 * Class for handling properties of {@link org.eclipse.microprofile.openapi.annotations.media.Schema}
 *
 * @author mark.petrenyi
 * @since 1.2.0
 */
public class SchemaHolder {
    private String name;
    private String title;
    private String description;
    private Boolean required;
    private List<String> enumeration;
    private String defaultValue;
    private SchemaType type;
    private Integer maxItems;
    private Integer minItems;
    private Integer maxLength;
    private Integer minLength;
    private Integer totalDigits;
    private Integer fractionDigits;
    private BigDecimal maximum;
    private Boolean exclusiveMaximum;
    private BigDecimal minimum;
    private Boolean exclusiveMinimum;
    private String pattern;
    private Boolean hidden;
    private String format;
    private String example;
    private boolean base64Binary;

    // private ExternalDocumentation externalDocs;

    /**
     * Annotate with {@link Schema} annotation using the SchemaHolder's parameters
     *
     * @param annotatable the annotatable (class, field, method, etc...)
     */
    public void annotate(JAnnotatable annotatable) {
        if (annotatable == null) {
            return;
        }
        JAnnotationUse annotationUse = annotatable.annotate(Schema.class);
        if (StringUtils.isNotBlank(name)) {
            annotationUse.param(SchemaFields.NAME, name);
        }
        if (StringUtils.isNotBlank(title)) {
            annotationUse.param(SchemaFields.TITLE, title);
        }
        if (StringUtils.isNotBlank(description)) {
            annotationUse.param(SchemaFields.DESCRIPTION, description);
        }
        if (required != null) {
            annotationUse.param(SchemaFields.REQUIRED, required);
        }
        if (isSetEnumeration()) {
            JAnnotationArrayMember paramArray = annotationUse.paramArray(SchemaFields.ENUMERATION);
            for (String enumValue : enumeration) {
                paramArray.param(enumValue);
            }
        }
        if (StringUtils.isNotBlank(defaultValue)) {
            annotationUse.param(SchemaFields.DEFAULT_VALUE, defaultValue);
        }
        if (type != null) {
            annotationUse.param(SchemaFields.TYPE, type);
        }
        if (maxItems != null) {
            annotationUse.param(SchemaFields.MAX_ITEMS, maxItems);
        }
        if (minItems != null) {
            annotationUse.param(SchemaFields.MIN_ITEMS, minItems);
        }
        if (maxLength != null) {
            annotationUse.param(SchemaFields.MAX_LENGTH, maxLength);
        }
        if (minLength != null) {
            annotationUse.param(SchemaFields.MIN_LENGTH, minLength);
        }
        if (maximum != null) {
            annotationUse.param(SchemaFields.MAXIMUM, maximum.toString());
        }
        if (exclusiveMaximum != null) {
            annotationUse.param(SchemaFields.EXCLUSIVE_MAXIMUM, exclusiveMaximum);
        }
        if (minimum != null) {
            annotationUse.param(SchemaFields.MINIMUM, minimum.toString());
        }
        if (exclusiveMinimum != null) {
            annotationUse.param(SchemaFields.EXCLUSIVE_MINIMUM, exclusiveMinimum);
        }
        if (StringUtils.isNotBlank(pattern)) {
            annotationUse.param(SchemaFields.PATTERN, pattern);
        }
        if (hidden != null) {
            annotationUse.param(SchemaFields.HIDDEN, hidden);
        }
        if (StringUtils.isNotBlank(format)) {
            annotationUse.param(SchemaFields.FORMAT, format);
        }
        if (StringUtils.isNotBlank(example)) {
            annotationUse.param(SchemaFields.EXAMPLE, example);
        }
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
     * @param name the name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets title.
     *
     * @return the title
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets title.
     *
     * @param title the title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Gets description.
     *
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets description.
     *
     * @param description the description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Gets required.
     *
     * @return the required
     */
    public Boolean getRequired() {
        return required;
    }

    /**
     * Sets required.
     *
     * @param required the required
     */
    public void setRequired(Boolean required) {
        this.required = required;
    }

    /**
     * Gets enumeration.
     *
     * @return the enumeration
     */
    public List<String> getEnumeration() {
        if (enumeration == null) {
            enumeration = new ArrayList<>();
        }
        return enumeration;
    }

    /**
     * Add enumeration value.
     *
     * @param enumValue the enum value
     */
    public void addEnumerationValue(String enumValue) {
        if (enumeration == null) {
            enumeration = new ArrayList<>();
        }
        this.enumeration.add(enumValue);
    }

    /**
     * Is set enumeration boolean.
     *
     * @return the boolean
     */
    public boolean isSetEnumeration() {
        return ((this.enumeration != null) && (!this.enumeration.isEmpty()));
    }

    /**
     * Gets default value.
     *
     * @return the default value
     */
    public String getDefaultValue() {
        return defaultValue;
    }

    /**
     * Sets default value.
     *
     * @param defaultValue the default value
     */
    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }

    /**
     * Gets type.
     *
     * @return the type
     */
    public SchemaType getType() {
        return type;
    }

    /**
     * Sets type.
     *
     * @param type the type
     */
    public void setType(SchemaType type) {
        this.type = type;
    }

    /**
     * Gets max items.
     *
     * @return the max items
     */
    public Integer getMaxItems() {
        return maxItems;
    }

    /**
     * Sets max items.
     *
     * @param maxItems the max items
     */
    public void setMaxItems(Integer maxItems) {
        this.maxItems = maxItems;
    }

    /**
     * Gets min items.
     *
     * @return the min items
     */
    public Integer getMinItems() {
        return minItems;
    }

    /**
     * Sets min items.
     *
     * @param minItems the min items
     */
    public void setMinItems(Integer minItems) {
        this.minItems = minItems;
    }

    /**
     * Gets max length.
     *
     * @return the max length
     */
    public Integer getMaxLength() {
        return maxLength;
    }

    /**
     * Sets max length.
     *
     * @param maxLength the max length
     */
    public void setMaxLength(Integer maxLength) {
        this.maxLength = maxLength;
    }

    /**
     * Gets min length.
     *
     * @return the min length
     */
    public Integer getMinLength() {
        return minLength;
    }

    /**
     * Sets min length.
     *
     * @param minLength the min length
     */
    public void setMinLength(Integer minLength) {
        this.minLength = minLength;
    }

    /**
     * Gets total digits.
     *
     * @return the total digits
     */
    public Integer getTotalDigits() {
        return totalDigits;
    }

    /**
     * Sets total digits.
     *
     * @param totalDigits the total digits
     */
    public void setTotalDigits(Integer totalDigits) {
        this.totalDigits = totalDigits;
    }

    /**
     * Gets fraction digits.
     *
     * @return the fraction digits
     */
    public Integer getFractionDigits() {
        return fractionDigits;
    }

    /**
     * Sets fraction digits.
     *
     * @param fractionDigits the fraction digits
     */
    public void setFractionDigits(Integer fractionDigits) {
        this.fractionDigits = fractionDigits;
    }

    /**
     * Gets maximum.
     *
     * @return the maximum
     */
    public BigDecimal getMaximum() {
        return maximum;
    }

    /**
     * Sets maximum.
     *
     * @param maximum the maximum
     */
    public void setMaximum(BigDecimal maximum) {
        this.maximum = maximum;
    }

    /**
     * Gets exclusive maximum.
     *
     * @return the exclusive maximum
     */
    public Boolean getExclusiveMaximum() {
        return exclusiveMaximum;
    }

    /**
     * Sets exclusive maximum.
     *
     * @param exclusiveMaximum the exclusive maximum
     */
    public void setExclusiveMaximum(Boolean exclusiveMaximum) {
        this.exclusiveMaximum = exclusiveMaximum;
    }

    /**
     * Gets minimum.
     *
     * @return the minimum
     */
    public BigDecimal getMinimum() {
        return minimum;
    }

    /**
     * Sets minimum.
     *
     * @param minimum the minimum
     */
    public void setMinimum(BigDecimal minimum) {
        this.minimum = minimum;
    }

    /**
     * Gets exclusive minimum.
     *
     * @return the exclusive minimum
     */
    public Boolean getExclusiveMinimum() {
        return exclusiveMinimum;
    }

    /**
     * Sets exclusive minimum.
     *
     * @param exclusiveMinimum the exclusive minimum
     */
    public void setExclusiveMinimum(Boolean exclusiveMinimum) {
        this.exclusiveMinimum = exclusiveMinimum;
    }

    /**
     * Gets pattern.
     *
     * @return the pattern
     */
    public String getPattern() {
        return pattern;
    }

    /**
     * Sets pattern.
     *
     * @param pattern the pattern
     */
    public void setPattern(String pattern) {
        this.pattern = pattern;
    }

    /**
     * Gets hidden.
     *
     * @return the hidden
     */
    public Boolean getHidden() {
        return hidden;
    }

    /**
     * Sets hidden.
     *
     * @param hidden the hidden
     */
    public void setHidden(Boolean hidden) {
        this.hidden = hidden;
    }

    /**
     * Gets format.
     *
     * @return the format
     */
    public String getFormat() {
        return format;
    }

    /**
     * Sets format.
     *
     * @param format the format
     */
    public void setFormat(String format) {
        this.format = format;
    }

    /**
     * Gets example.
     *
     * @return the example
     */
    public String getExample() {
        return example;
    }

    /**
     * Sets example.
     *
     * @param example the example
     */
    public void setExample(String example) {
        this.example = example;
    }

    /**
     * Is base 64 binary boolean.
     *
     * @return the boolean
     */
    public boolean isBase64Binary() {
        return base64Binary;
    }

    /**
     * Sets base 64 binary.
     *
     * @param base64Binary the base 64 binary
     */
    public void setBase64Binary(boolean base64Binary) {
        this.base64Binary = base64Binary;
    }
}
