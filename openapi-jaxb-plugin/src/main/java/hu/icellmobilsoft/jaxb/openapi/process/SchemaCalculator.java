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

import java.util.Optional;

import hu.icellmobilsoft.jaxb.openapi.process.configuration.OpenApiPluginConfiguration;

/**
 * SchemaCalculator interface.
 *
 * @param <T>
 *            the outline type to calculate from
 */
public interface SchemaCalculator<T> {

    /**
     * Calculate {@link SchemaHolder} from outline
     *
     * @param outline
     *            the outline to calculate from
     * @param openApiPluginConfiguration
     *            plugin configurations
     * @return optional {@link SchemaHolder}
     */
    Optional<SchemaHolder> calculateSchema(T outline, OpenApiPluginConfiguration openApiPluginConfiguration);

}
