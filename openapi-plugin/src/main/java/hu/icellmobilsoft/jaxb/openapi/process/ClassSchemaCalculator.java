package hu.icellmobilsoft.jaxb.openapi.process;

import java.util.Optional;
import java.util.logging.Logger;

import com.sun.tools.xjc.outline.ClassOutline;
import com.sun.tools.xjc.reader.xmlschema.bindinfo.BindInfo;
import com.sun.xml.xsom.XSAnnotation;

/**
 * {@link ClassOutline} implementation for {@link SchemaCalculator}
 *
 * @author mark.petrenyi
 * @since 2.0.0
 */
public class ClassSchemaCalculator implements SchemaCalculator<ClassOutline> {

    private static final ClassSchemaCalculator INSTANCE = new ClassSchemaCalculator();

    /**
     * Gets instance.
     *
     * @return the instance
     */
    public static ClassSchemaCalculator getInstance() {
        return INSTANCE;
    }

    private Logger log = Logger.getLogger(ClassSchemaCalculator.class.getName());

    @Override
    public Optional<SchemaHolder> calculateSchema(ClassOutline classOutline, boolean verboseDescriptions) {
        if (classOutline == null || classOutline.target == null || classOutline.ref == null) {
            return Optional.empty();
        }

        SchemaHolder schema = new SchemaHolder();
        String name = classOutline.target.isElement() ? classOutline.target.getElementName().getLocalPart() : classOutline.ref.name();
        schema.setName(name);
        String documentation = getDocumentation(classOutline);
        schema.setDescription(documentation);
        return Optional.of(schema);
    }

    private String getDocumentation(final ClassOutline o) {
        if (o == null || o.target == null) {
            return null;
        }
        XSAnnotation annotation = o.target.getSchemaComponent().getAnnotation();
        if (annotation != null && annotation.getAnnotation() instanceof BindInfo) {
            return ((BindInfo) annotation.getAnnotation()).getDocumentation();
        }
        return null;
    }
}
