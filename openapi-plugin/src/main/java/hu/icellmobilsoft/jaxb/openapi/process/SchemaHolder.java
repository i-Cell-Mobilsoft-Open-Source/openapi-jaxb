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
     * @param annotatable
     *            the annotatable (class, field, method, etc...)
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getRequired() {
        return required;
    }

    public void setRequired(Boolean required) {
        this.required = required;
    }

    public List<String> getEnumeration() {
        if (enumeration == null) {
            enumeration = new ArrayList<>();
        }
        return enumeration;
    }

    public void addEnumerationValue(String enumValue) {
        if (enumeration == null) {
            enumeration = new ArrayList<>();
        }
        this.enumeration.add(enumValue);
    }

    public boolean isSetEnumeration() {
        return ((this.enumeration != null) && (!this.enumeration.isEmpty()));
    }

    public String getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }

    public SchemaType getType() {
        return type;
    }

    public void setType(SchemaType type) {
        this.type = type;
    }

    public Integer getMaxItems() {
        return maxItems;
    }

    public void setMaxItems(Integer maxItems) {
        this.maxItems = maxItems;
    }

    public Integer getMinItems() {
        return minItems;
    }

    public void setMinItems(Integer minItems) {
        this.minItems = minItems;
    }

    public Integer getMaxLength() {
        return maxLength;
    }

    public void setMaxLength(Integer maxLength) {
        this.maxLength = maxLength;
    }

    public Integer getMinLength() {
        return minLength;
    }

    public void setMinLength(Integer minLength) {
        this.minLength = minLength;
    }

    public Integer getTotalDigits() {
        return totalDigits;
    }

    public void setTotalDigits(Integer totalDigits) {
        this.totalDigits = totalDigits;
    }

    public Integer getFractionDigits() {
        return fractionDigits;
    }

    public void setFractionDigits(Integer fractionDigits) {
        this.fractionDigits = fractionDigits;
    }

    public BigDecimal getMaximum() {
        return maximum;
    }

    public void setMaximum(BigDecimal maximum) {
        this.maximum = maximum;
    }

    public Boolean getExclusiveMaximum() {
        return exclusiveMaximum;
    }

    public void setExclusiveMaximum(Boolean exclusiveMaximum) {
        this.exclusiveMaximum = exclusiveMaximum;
    }

    public BigDecimal getMinimum() {
        return minimum;
    }

    public void setMinimum(BigDecimal minimum) {
        this.minimum = minimum;
    }

    public Boolean getExclusiveMinimum() {
        return exclusiveMinimum;
    }

    public void setExclusiveMinimum(Boolean exclusiveMinimum) {
        this.exclusiveMinimum = exclusiveMinimum;
    }

    public String getPattern() {
        return pattern;
    }

    public void setPattern(String pattern) {
        this.pattern = pattern;
    }

    public Boolean getHidden() {
        return hidden;
    }

    public void setHidden(Boolean hidden) {
        this.hidden = hidden;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public String getExample() {
        return example;
    }

    public void setExample(String example) {
        this.example = example;
    }

    public boolean isBase64Binary() {
        return base64Binary;
    }

    public void setBase64Binary(boolean base64Binary) {
        this.base64Binary = base64Binary;
    }
}
