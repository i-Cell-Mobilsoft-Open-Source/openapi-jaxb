package hu.icellmobilsoft.jaxb.openapi.process;

import java.util.Optional;

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
     * @param verboseDescriptions
     *            if verboseDescriptions should be generated
     * @return optional {@link SchemaHolder}
     */
    Optional<SchemaHolder> calculateSchema(T outline, boolean verboseDescriptions);

}
