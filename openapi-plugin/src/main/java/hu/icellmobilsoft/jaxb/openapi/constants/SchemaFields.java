package hu.icellmobilsoft.jaxb.openapi.constants;

import org.eclipse.microprofile.openapi.annotations.media.Schema;

/**
 * Contains string constants for {@link org.eclipse.microprofile.openapi.annotations.media.Schema} properties
 *
 * @author mark.petrenyi
 */
public class SchemaFields {

    /**
     * Constant for {@link Schema#name()}
     */
    public static final String NAME = "name";

    /**
     * Constant for {@link Schema#title()}
     */
    public static final String TITLE = "title";

    /**
     * Constant for {@link Schema#description()}
     */
    public static final String DESCRIPTION = "description";

    /**
     * Constant for {@link Schema#required()}
     */
    public static final String REQUIRED = "required";

    /**
     * Constant for {@link Schema#enumeration()}
     */
    public static final String ENUMERATION = "enumeration";

    /**
     * Constant for {@link Schema#defaultValue()}
     */
    public static final String DEFAULT_VALUE = "defaultValue";

    /**
     * Constant for {@link Schema#type()}
     */
    public static final String TYPE = "type";

    /**
     * Constant for {@link Schema#maxItems()}
     */
    public static final String MAX_ITEMS = "maxItems";

    /**
     * Constant for {@link Schema#minItems()}
     */
    public static final String MIN_ITEMS = "minItems";

    /**
     * Constant for {@link Schema#minLength()}
     */
    public static final String MIN_LENGTH = "minLength";

    /**
     * Constant for {@link Schema#maxLength()}
     */
    public static final String MAX_LENGTH = "maxLength";

    /**
     * Constant for {@link Schema#minimum()}
     */
    public static final String MINIMUM = "minimum";

    /**
     * Constant for {@link Schema#maximum()}
     */
    public static final String MAXIMUM = "maximum";

    /**
     * Constant for {@link Schema#exclusiveMinimum()}
     */
    public static final String EXCLUSIVE_MINIMUM = "exclusiveMinimum";

    /**
     * Constant for {@link Schema#exclusiveMaximum()}
     */
    public static final String EXCLUSIVE_MAXIMUM = "exclusiveMaximum";

    /**
     * Constant for {@link Schema#pattern()}
     */
    public static final String PATTERN = "pattern";

}
