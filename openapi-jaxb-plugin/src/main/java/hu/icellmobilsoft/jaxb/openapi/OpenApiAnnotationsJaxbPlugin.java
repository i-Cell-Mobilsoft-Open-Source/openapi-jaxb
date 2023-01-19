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
package hu.icellmobilsoft.jaxb.openapi;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.Collection;
import java.util.Optional;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

import com.sun.codemodel.JFieldVar;
import com.sun.codemodel.JMethod;
import com.sun.tools.xjc.BadCommandLineException;
import com.sun.tools.xjc.Options;
import com.sun.tools.xjc.Plugin;
import com.sun.tools.xjc.outline.ClassOutline;
import com.sun.tools.xjc.outline.EnumOutline;
import com.sun.tools.xjc.outline.FieldOutline;
import com.sun.tools.xjc.outline.Outline;

import hu.icellmobilsoft.jaxb.openapi.process.ClassSchemaCalculator;
import hu.icellmobilsoft.jaxb.openapi.process.EnumSchemaCalculator;
import hu.icellmobilsoft.jaxb.openapi.process.FieldSchemaCalculator;
import hu.icellmobilsoft.jaxb.openapi.process.SchemaCalculator;
import hu.icellmobilsoft.jaxb.openapi.process.SchemaHolder;
import hu.icellmobilsoft.jaxb.openapi.process.configuration.OpenApiPluginConfiguration;

/**
 * The type Open api annotations jaxb plugin.
 *
 * @author mark.petrenyi
 */
public class OpenApiAnnotationsJaxbPlugin extends Plugin {

    /**
     * Plugin activation constant {@value}
     */
    public static final String OPENAPIFY = "openapify";
    private static final String USAGE = "Add this plugin to the JAXB classes generator classpath and provide the argument '-" + OPENAPIFY + "'.";

    private OpenApiPluginConfiguration.ArgBuilder configBuilder = OpenApiPluginConfiguration.builder();
    private OpenApiPluginConfiguration config;

    /**
     * {@inheritDoc}
     * 
     * @return {@value OPENAPIFY}
     */
    @Override
    public String getOptionName() {
        return OPENAPIFY;
    }

    /**
     * {@inheritDoc}
     *
     * @return {@value USAGE}
     */
    @Override
    public String getUsage() {
        return USAGE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int parseArgument(Options opt, String[] args, int i) throws BadCommandLineException, IOException {
        int consumed = super.parseArgument(opt, args, i);
        String arg = args[i];
        if (configBuilder.processArg(arg)) {
            consumed++;
        }
        return consumed;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean run(final Outline outline, final Options opt, final ErrorHandler errorHandler) throws SAXException {
        config = configBuilder.build();
        processEnums(outline.getEnums(), errorHandler);
        processClasses(outline.getClasses(), errorHandler);
        return true;
    }

    /**
     * Processing of EnumOutlines.
     *
     * @param enums
     *            collection of enum outlines to process
     * @param errorHandler
     *            the error handler
     */
    protected void processEnums(Collection<EnumOutline> enums, ErrorHandler errorHandler) {
        if (CollectionUtils.isEmpty(enums)) {
            return;
        }
        for (EnumOutline eo : enums) {
            Optional<SchemaHolder> schema = getEnumSchemaCalculator().calculateSchema(eo, config);
            if (schema.isPresent() && eo.clazz != null) {
                schema.get().annotate(eo.clazz);
            }
        }
    }

    /**
     * Process ClassOutlines.
     *
     * @param classes
     *            the ClassOutline to process
     * @param errorHandler
     *            the error handler
     * @throws SAXException
     *             in case of error
     */
    protected void processClasses(Collection<? extends ClassOutline> classes, ErrorHandler errorHandler) throws SAXException {
        for (ClassOutline o : classes) {
            addClassAnnotation(o, errorHandler);
            processFields(o, o.getDeclaredFields(), errorHandler);
            if (o.implClass != null) {
                processMethodImplementations(o.implClass.methods(), errorHandler);
            }
        }
    }

    /**
     * Add {@link org.eclipse.microprofile.openapi.annotations.media.Schema} annotation on class level.
     *
     * @param classOutline
     *            the classOutline
     * @param errorHandler
     *            the error handler
     */
    protected void addClassAnnotation(final ClassOutline classOutline, ErrorHandler errorHandler) {
        if (classOutline == null) {
            return;
        }

        Optional<SchemaHolder> schema = getClassSchemaCalculator().calculateSchema(classOutline, config);
        if (schema.isPresent() && classOutline.implClass != null) {
            schema.get().annotate(classOutline.implClass);
        }
    }

    /**
     * Process FieldOutline.
     *
     * @param parentOutline
     *            the parent ClassOutline of the fields
     * @param declaredFields
     *            the declared fields on parentOutline
     * @param errorHandler
     *            the error handler
     * @throws SAXException
     *             in case of error
     */
    protected void processFields(ClassOutline parentOutline, FieldOutline[] declaredFields, ErrorHandler errorHandler) throws SAXException {
        if (ArrayUtils.isNotEmpty(declaredFields)) {
            for (FieldOutline declaredField : declaredFields) {
                Optional<SchemaHolder> schemaHolderOpt = getFieldSchemaCalculator().calculateSchema(declaredField, config);

                if (schemaHolderOpt.isPresent()) {
                    annotateFields(schemaHolderOpt.get(), parentOutline, declaredField, errorHandler);
                }
            }
        }
    }

    /**
     * Process actual method declarations. ie.: can be used to hide isSet methods
     *
     * @param methods
     *            the methods to process
     * @param errorHandler
     *            the error handler
     */
    protected void processMethodImplementations(Collection<JMethod> methods, ErrorHandler errorHandler) {
        if (CollectionUtils.isNotEmpty(methods)) {
            for (JMethod method : methods) {
                String name = method.name();
                if (name != null && name.startsWith("isSet")) {
                    SchemaHolder schemaHolder = new SchemaHolder();
                    schemaHolder.setHidden(true);
                    schemaHolder.annotate(method);
                }
            }
        }
    }

    /**
     * Gets SchemaCalculator used to create {@link org.eclipse.microprofile.openapi.annotations.media.Schema} from {@link EnumOutline}
     *
     * @return {@link SchemaCalculator} instance for {@link EnumOutline}
     */
    protected SchemaCalculator<EnumOutline> getEnumSchemaCalculator() {
        return EnumSchemaCalculator.getInstance();
    }

    /**
     * Gets SchemaCalculator used to create {@link org.eclipse.microprofile.openapi.annotations.media.Schema} from {@link ClassOutline}
     *
     * @return {@link SchemaCalculator} instance for {@link ClassOutline}
     */
    protected SchemaCalculator<ClassOutline> getClassSchemaCalculator() {
        return ClassSchemaCalculator.getInstance();
    }

    /**
     * Gets SchemaCalculator used to create {@link org.eclipse.microprofile.openapi.annotations.media.Schema} from {@link FieldOutline}
     *
     * @return {@link SchemaCalculator} instance for {@link FieldOutline}
     */
    protected SchemaCalculator<FieldOutline> getFieldSchemaCalculator() {
        return FieldSchemaCalculator.getInstance();
    }

    private void annotateFields(SchemaHolder schemaHolder, ClassOutline parentOutline, FieldOutline declaredField, ErrorHandler errorHandler)
            throws SAXException {
        String name = declaredField.getPropertyInfo().getName(false);
        if (parentOutline != null && parentOutline.implClass != null && MapUtils.isNotEmpty(parentOutline.implClass.fields())
                && parentOutline.implClass.fields().containsKey(name)) {
            JFieldVar jFieldVar = parentOutline.implClass.fields().get(name);
            schemaHolder.annotate(jFieldVar);
        } else {
            errorHandler.warning(
                    new SAXParseException(MessageFormat.format("Could not annotate field: [{0}]!", name), declaredField.getPropertyInfo().locator));
        }
    }

}
