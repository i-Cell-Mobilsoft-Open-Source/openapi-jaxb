package hu.icellmobilsoft.jaxb.openapi;

import javax.xml.bind.annotation.XmlAccessType;

import be.redlab.jaxb.swagger.ProcessStrategy;
import be.redlab.jaxb.swagger.process.FieldProcessStrategy;
import be.redlab.jaxb.swagger.process.NoProcessStrategy;
import be.redlab.jaxb.swagger.process.PropertyProcessStrategy;
import be.redlab.jaxb.swagger.process.PublicMemberProcessStrategy;
import hu.icellmobilsoft.jaxb.openapi.process.OpenApiProcessUtil;

/**
 * @author mark.petrenyi
 */
public class OpenApiProcessStrategyFactory {

    private static FieldProcessStrategy fieldProcessor;
    private static NoProcessStrategy noProcessor;
    private static PropertyProcessStrategy propProcessor;
    private static PublicMemberProcessStrategy publicMemberProcessor;

    private OpenApiProcessStrategyFactory() {
        super();
    }

    public static ProcessStrategy getProcessStrategy(final XmlAccessType access) {
        OpenApiProcessUtil processUtil = OpenApiProcessUtil.getInstance();
        switch (access) {
            case FIELD:
                return null == fieldProcessor ? fieldProcessor = new FieldProcessStrategy(processUtil) : fieldProcessor;
            case NONE:
                return null == noProcessor ? noProcessor = new NoProcessStrategy(processUtil) : noProcessor;
            case PROPERTY:
                return null == propProcessor ? propProcessor = new PropertyProcessStrategy(processUtil) : propProcessor;
            case PUBLIC_MEMBER:
                return null == publicMemberProcessor ? publicMemberProcessor = new PublicMemberProcessStrategy(processUtil) : publicMemberProcessor;
            default:
                throw new UnsupportedOperationException(String.format("%s not supported as ProcessStrategy", access));
        }
    }
}
