/*-
 * #%L
 * Swagger JAXB
 * %%
 * Copyright (C) 2019 i-Cell Mobilsoft Zrt.
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

    /**
     * Constant for {@link Schema#hidden()}
     */
    public static final String HIDDEN = "hidden";

    /**
     * Constant for {@link Schema#externalDocs()}
     */
    public static final String EXTERNAL_DOCS = "externalDocs";

    /**
     * Constant for {@link Schema#format()} ()}
     */
    public static final String FORMAT = "format";

    /**
     * Constant for {@link Schema#example()} ()}
     */
    public static final String EXAMPLE = "example";
}
