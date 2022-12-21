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

import java.util.ArrayList;
import java.util.List;

import org.eclipse.microprofile.openapi.annotations.enums.SchemaType;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

import hu.icellmobilsoft.openapi.dto.sample.sample.EnumeratedType;
import hu.icellmobilsoft.openapi.dto.sample.sample.NoDocObject;

@Schema(name = "SampleType", description = "Sample type (typeDoc)")
public class SampleType {

    @Schema(name = "someString", title = "someString",
            description = "String value restricted (elementDoc)\n\nRestrictions: \n* maxLength: 50\n* minLength: 10\n* pattern: .*[^\\s].*",
            required = true, maxLength = 50, minLength = 10, pattern = ".*[^\\s].*")
    protected String someString;
    @Schema(name = "someEnum", title = "someEnum", description = "String value restricted (elementDoc)", required = true)
    protected EnumeratedType someEnum;
    @Schema(name = "someInt", title = "someInt",
            description = "integer greater than 4, less than or equal to 10 (elementDoc)\n\nRestrictions: \n* maximum: 10\n* exclusiveMaximum: false\n* minimum: 4\n* exclusiveMinimum: true",
            maximum = "10", exclusiveMaximum = false, minimum = "4", exclusiveMinimum = true)
    protected Integer someInt;
    @Schema(name = "someNoDoc", title = "someNoDoc")
    protected NoDocObject someNoDoc;
    @Schema(name = "someCollection", title = "someCollection", description = "SampleObject array, containing 2 to 10 elements (elementDoc)",
            required = true, type = SchemaType.ARRAY, maxItems = 10, minItems = 2)
    protected List<SampleObject> someCollection;

    /**
     * Gets the value of the someString property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getSomeString() {
        return someString;
    }

    /**
     * Sets the value of the someString property.
     * 
     * @param value
     *            allowed object is {@link String }
     * 
     */
    public void setSomeString(String value) {
        this.someString = value;
    }

    @Schema(hidden = true)
    public boolean isSetSomeString() {
        return (this.someString != null);
    }

    /**
     * Gets the value of the someEnum property.
     * 
     * @return possible object is {@link EnumeratedType }
     * 
     */
    public EnumeratedType getSomeEnum() {
        return someEnum;
    }

    /**
     * Sets the value of the someEnum property.
     * 
     * @param value
     *            allowed object is {@link EnumeratedType }
     * 
     */
    public void setSomeEnum(EnumeratedType value) {
        this.someEnum = value;
    }

    @Schema(hidden = true)
    public boolean isSetSomeEnum() {
        return (this.someEnum != null);
    }

    /**
     * Gets the value of the someInt property.
     * 
     * @return possible object is {@link Integer }
     * 
     */
    public Integer getSomeInt() {
        return someInt;
    }

    /**
     * Sets the value of the someInt property.
     * 
     * @param value
     *            allowed object is {@link Integer }
     * 
     */
    public void setSomeInt(Integer value) {
        this.someInt = value;
    }

    @Schema(hidden = true)
    public boolean isSetSomeInt() {
        return (this.someInt != null);
    }

    /**
     * Gets the value of the someNoDoc property.
     * 
     * @return possible object is {@link NoDocObject }
     * 
     */
    public NoDocObject getSomeNoDoc() {
        return someNoDoc;
    }

    /**
     * Sets the value of the someNoDoc property.
     * 
     * @param value
     *            allowed object is {@link NoDocObject }
     * 
     */
    public void setSomeNoDoc(NoDocObject value) {
        this.someNoDoc = value;
    }

    @Schema(hidden = true)
    public boolean isSetSomeNoDoc() {
        return (this.someNoDoc != null);
    }

    /**
     * Gets the value of the someCollection property.
     * 
     * <p>
     * This accessor method returns a reference to the live list, not a snapshot. Therefore any modification you make to the returned list will be
     * present inside the JAXB object. This is why there is not a <CODE>set</CODE> method for the someCollection property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * 
     * <pre>
     * getSomeCollection().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list {@link SampleObject }
     * 
     * 
     */
    public List<SampleObject> getSomeCollection() {
        if (someCollection == null) {
            someCollection = new ArrayList<SampleObject>();
        }
        return this.someCollection;
    }

    @Schema(hidden = true)
    public boolean isSetSomeCollection() {
        return ((this.someCollection != null) && (!this.someCollection.isEmpty()));
    }

    public void unsetSomeCollection() {
        this.someCollection = null;
    }

}
