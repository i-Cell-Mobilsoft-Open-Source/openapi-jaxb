package hu.icellmobilsoft.jaxb.openapi.process;

import java.util.Collection;
import java.util.Map;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;

import com.sun.codemodel.JAnnotationUse;
import com.sun.codemodel.JDefinedClass;
import com.sun.codemodel.JFieldVar;
import com.sun.codemodel.JMethod;
import com.sun.tools.xjc.model.CClassInfo;
import com.sun.tools.xjc.outline.EnumOutline;

import be.redlab.jaxb.swagger.XJCHelper;
import be.redlab.jaxb.swagger.process.AbstractProcessStrategy;
import be.redlab.jaxb.swagger.process.AbstractProcessUtil;

/**
 * ProcessStrategy for handling both methods and fields. (useful ie. if isSet methods must be annotated with {@code @Schema(hidden=true)}.
 *
 * @author mark.petrenyi
 * @since 1.2.0
 */
public class MethodAndFieldProcessStrategy extends AbstractProcessStrategy {

    public MethodAndFieldProcessStrategy(AbstractProcessUtil processUtil) {
        super(processUtil);
    }

    public MethodAndFieldProcessStrategy() {
        super();
    }

    @Override
    public void doProcess(final JDefinedClass implClass, final CClassInfo targetClass, final Collection<JMethod> methods,
            final Map<String, JFieldVar> fields, final Collection<EnumOutline> enums) {
        for (Map.Entry<String, JFieldVar> e : fields.entrySet()) {
            int mods = e.getValue().mods().getValue();
            if (processUtil.validFieldMods(mods) && null == XJCHelper.getAnnotation(e.getValue().annotations(), XmlTransient.class)) {
                processUtil.addAnnotationForField(implClass, targetClass, e.getValue(), enums);
            }
        }
        for (JMethod jm : methods) {
            int mods = jm.mods().getValue();
            JAnnotationUse annotation = XJCHelper.getAnnotation(jm.annotations(), XmlElement.class);
            if (processUtil.validMethodMods(mods)) {
                processUtil.addAnnotationForMethod(implClass, targetClass, jm, processUtil.isRequiredByAnnotation(annotation), null, enums);
            }
        }

    }
}
