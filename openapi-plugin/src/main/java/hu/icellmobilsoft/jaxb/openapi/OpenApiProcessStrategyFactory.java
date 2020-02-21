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
        return getProcessStrategy(access, false);
    }

    public static ProcessStrategy getProcessStrategy(XmlAccessType access, boolean verboseDescriptions) {
        OpenApiProcessUtil processUtil = OpenApiProcessUtil.getInstance(verboseDescriptions);
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
