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
package hu.icellmobilsoft.openapi.jaxb23.test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.stream.Stream;

import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import hu.icellmobilsoft.openapi.dto.sample.sample.SampleType;

class SampleTypeTest {

    public static Stream<Arguments> givenWeHaveFields() {
        return Stream.of(SampleType.class.getDeclaredFields()).map(Arguments::of);
    }

    public static Stream<Arguments> givenWeHaveMethods() {
        return Stream.of(SampleType.class.getMethods()).map(Arguments::of);
    }

    @Test
    void classLevelAnnotation() {
        Schema annotation = SampleType.class.getAnnotation(Schema.class);
        Schema expected = hu.icellmobilsoft.openapi.jaxb23.test.expected.SampleType.class.getAnnotation(Schema.class);

        assertEquals(expected, annotation);
    }

    @ParameterizedTest
    @MethodSource("givenWeHaveFields")
    void fieldLevelAnnotation(Field fieldToTest) throws NoSuchFieldException {
        Field expectedField = hu.icellmobilsoft.openapi.jaxb23.test.expected.SampleType.class.getDeclaredField(fieldToTest.getName());
        Schema actual = fieldToTest.getAnnotation(Schema.class);
        Schema expected = expectedField.getAnnotation(Schema.class);

        assertEquals(expected, actual);
    }

    @ParameterizedTest
    @MethodSource("givenWeHaveMethods")
    void methodLevelAnnotation(Method methodToTest) throws NoSuchMethodException {

        Method expectedMethod = hu.icellmobilsoft.openapi.jaxb23.test.expected.SampleType.class.getMethod(methodToTest.getName(),
                methodToTest.getParameterTypes());
        Schema actual = methodToTest.getAnnotation(Schema.class);
        Schema expected = expectedMethod.getAnnotation(Schema.class);

        assertEquals(expected, actual);
    }
}
