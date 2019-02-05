package hu.icellmobilsoft.jaxb.openapi;

import javax.xml.bind.annotation.XmlAccessType;

import be.redlab.jaxb.swagger.ProcessStrategy;
import be.redlab.jaxb.swagger.SwaggerAnnotationsJaxbPlugin;
import com.sun.codemodel.JAnnotationUse;
import com.sun.tools.xjc.outline.ClassOutline;
import com.sun.tools.xjc.reader.xmlschema.bindinfo.BindInfo;
import com.sun.xml.xsom.XSAnnotation;
import hu.icellmobilsoft.jaxb.openapi.constants.SchemaFields;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

/**
 * @author mark.petrenyi
 */
public class OpenApiAnnotationsJaxbPlugin extends SwaggerAnnotationsJaxbPlugin {

    private static final String OPENAPIFY = "openapify";
    private static final String USAGE =
            "Add this plugin to the JAXB classes generator classpath and provide the argument '-"+OPENAPIFY+"'.";

    /**
     * The option name to activate OpenApi annotations.
     *
     * @return {@value OPENAPIFY}
     */
    @Override
    public String getOptionName() {
        return OPENAPIFY;
    }

    /**
     * A usage description
     *
     * @return {@value USAGE}
     */
    @Override
    public String getUsage() {
        return USAGE;
    }

    /**
     * Add the class level annotation, {@link io.swagger.annotations.ApiModel}
     *
     * @param o the ClassOutline
     */
    @Override
    protected void addClassAnnotation(final ClassOutline o) {
        JAnnotationUse apiClass = o.implClass.annotate(Schema.class);
        String value = o.target.isElement() ? o.target.getElementName().getLocalPart() : o.ref.name();
        apiClass.param(SchemaFields.NAME, value);
        String documentation = getDocumentation(o);
        apiClass.param(SchemaFields.DESCRIPTION, (documentation != null) ? documentation : o.ref.fullName());
    }

    @Override
    protected ProcessStrategy getProcessStrategy(XmlAccessType access) {
        return OpenApiProcessStrategyFactory.getProcessStrategy(access);
    }

}
