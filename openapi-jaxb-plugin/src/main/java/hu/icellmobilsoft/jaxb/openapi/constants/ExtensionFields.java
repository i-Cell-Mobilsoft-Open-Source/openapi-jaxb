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

import org.eclipse.microprofile.openapi.annotations.extensions.Extension;

/**
 * Contains string constants for {@link Extension} properties
 *
 * @author mark.petrenyi
 * @since 2.1.0
 */
public class ExtensionFields {

    private ExtensionFields() {
    }

    /**
     * Constant for {@link Extension#name()}
     */
    public static final String NAME = "name";

    /**
     * Constant for {@link Extension#value()}
     */
    public static final String VALUE = "value";

    /**
     * Constant for {@link Extension#parseValue()}
     */
    public static final String PARSE_VALUE = "parseValue";

}
