/*-
 * #%L
 * OpenAPI JAXB Plugin
 * %%
 * Copyright (C) 2019 - 2023 i-Cell Mobilsoft Zrt.
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
package hu.icellmobilsoft.jaxb.openapi.process.configuration;

import hu.icellmobilsoft.jaxb.openapi.OpenApiAnnotationsJaxbPlugin;

/**
 * Configuration class for OpenAPI plugin
 * 
 * @author mark.petrenyi
 * @since 2.1.0
 */
public class OpenApiPluginConfiguration {

    private static final String VERBOSE_DESCRIPTIONS = OpenApiAnnotationsJaxbPlugin.OPENAPIFY + ":verboseDescriptions";
    private static final String SKIP_EXTENSIONS = OpenApiAnnotationsJaxbPlugin.OPENAPIFY + ":skipExtensions";
    private static final String PREFER_JAVA_NAME = OpenApiAnnotationsJaxbPlugin.OPENAPIFY + ":preferJavaName";

    private final boolean verboseDescriptions;
    private final boolean skipExtensions;
    private final boolean preferJavaName;

    /**
     * Creates a plugin argument based builder
     *
     * @return the builder
     */
    public static ArgBuilder builder() {
        return new ArgBuilder();
    }

    private OpenApiPluginConfiguration(boolean verboseDescriptions, boolean skipExtensions, boolean preferJavaName) {
        this.verboseDescriptions = verboseDescriptions;
        this.skipExtensions = skipExtensions;
        this.preferJavaName = preferJavaName;
    }

    /**
     * Indicates if schema descriptions should be verbose
     *
     * @return verbose descriptions needed
     */
    public boolean isVerboseDescriptions() {
        return verboseDescriptions;
    }

    /**
     * Indicates if schema extensions should be skipped
     *
     * @return skip extensions
     */
    public boolean isSkipExtensions() {
        return skipExtensions;
    }

    /**
     * Indicates if java field or xml name should be used as schema name
     *
     * @return if java field names shoul be prefered
     */
    public boolean isPreferJavaName() {
        return preferJavaName;
    }

    /**
     * Plugin arg processing builder
     */
    public static class ArgBuilder {
        private boolean verboseDescriptions = false;
        private boolean skipExtensions = false;
        private boolean preferJavaName = false;

        /**
         * Instantiates a new Arg builder.
         */
        public ArgBuilder() {
        }

        /**
         * Processes a plugin argument as possible config activation
         *
         * @param arg
         *            the arg to process
         * @return if the argument was processed
         */
        public boolean processArg(String arg) {
            boolean processed = false;
            if (arg != null) {
                if (arg.contains(VERBOSE_DESCRIPTIONS)) {
                    verboseDescriptions = true;
                    processed = true;
                }
                if (arg.contains(SKIP_EXTENSIONS)) {
                    skipExtensions = true;
                    processed = true;
                }
                if (arg.contains(PREFER_JAVA_NAME)) {
                    preferJavaName = true;
                    processed = true;
                }
            }
            return processed;
        }

        /**
         * Build open api plugin configuration.
         *
         * @return the open api plugin configuration
         */
        public OpenApiPluginConfiguration build() {
            return new OpenApiPluginConfiguration(verboseDescriptions, skipExtensions, preferJavaName);
        }
    }
}
