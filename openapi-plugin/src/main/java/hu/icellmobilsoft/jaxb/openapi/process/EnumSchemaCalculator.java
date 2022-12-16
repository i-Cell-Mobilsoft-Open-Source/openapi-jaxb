package hu.icellmobilsoft.jaxb.openapi.process;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.logging.Logger;

import org.apache.commons.collections4.CollectionUtils;
import org.eclipse.microprofile.openapi.annotations.enums.SchemaType;

import com.sun.tools.xjc.model.CEnumConstant;
import com.sun.tools.xjc.outline.EnumConstantOutline;
import com.sun.tools.xjc.outline.EnumOutline;

/**
 * {@link EnumOutline} implementation for {@link SchemaCalculator}
 *
 * @author mark.petrenyi
 * @since 2.0.0
 */
public class EnumSchemaCalculator implements SchemaCalculator<EnumOutline> {

    private static final EnumSchemaCalculator INSTANCE = new EnumSchemaCalculator();

    /**
     * Gets instance.
     *
     * @return the instance
     */
    public static EnumSchemaCalculator getInstance() {
        return INSTANCE;
    }

    private Logger log = Logger.getLogger(EnumSchemaCalculator.class.getName());

    @Override
    public Optional<SchemaHolder> calculateSchema(EnumOutline enumOutline, boolean verboseDescriptions) {
        if (enumOutline == null) {
            return Optional.empty();
        }
        SchemaHolder schema = new SchemaHolder();
        schema.setType(SchemaType.STRING);
        List<EnumConstantOutline> constants = enumOutline.constants;
        if (CollectionUtils.isNotEmpty(constants)) {
            // gets the actual lexical value defined in the xsd instead of the enum name,
            // since it can be escaped in java (ie. xsd: "example.test" vs java enum: EXAMPLE_TEST)
            constants.stream()//
                    .map(eco -> eco.target).filter(Objects::nonNull)//
                    .map(CEnumConstant::getLexicalValue).filter(Objects::nonNull)//
                    .forEach(schema::addEnumerationValue);
        }
        return Optional.of(schema);
    }
}
