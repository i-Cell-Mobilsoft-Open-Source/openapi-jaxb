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
package hu.icellmobilsoft.openapi.jaxb23.test.expected;

import org.eclipse.microprofile.openapi.annotations.extensions.Extension;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

@Schema(name = "SampleObject", description = "Sample object (typeDoc)", extensions = {
        @Extension(name = "x-xml-namespace", value = "http://sample.dto.openapi.icellmobilsoft.hu/sample")
})
public class SampleObject {

    @Schema(name = "property", title = "property", description = "Restrictions: \n* maxLength: 50\n* minLength: 10\n* pattern: .*[^\\s].*",
            required = true, maxLength = 50, minLength = 10, pattern = ".*[^\\s].*")
    protected String property;

    /**
     * Gets the value of the property property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getProperty() {
        return property;
    }

    /**
     * Sets the value of the property property.
     * 
     * @param value
     *            allowed object is {@link String }
     * 
     */
    public void setProperty(String value) {
        this.property = value;
    }

    @Schema(hidden = true)
    public boolean isSetProperty() {
        return (this.property != null);
    }

}
