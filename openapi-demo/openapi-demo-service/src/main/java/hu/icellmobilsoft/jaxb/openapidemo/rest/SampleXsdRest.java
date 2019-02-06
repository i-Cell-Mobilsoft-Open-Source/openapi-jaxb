package hu.icellmobilsoft.jaxb.openapidemo.rest;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import hu.icellmobilsoft.openapi.dto.sample.sample.SampleType;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

@Tag(name = "OpenAPI xsd example", description = "Rest service example with objects generated from xsd")
@Path("/sample")
public class SampleXsdRest {

    @Operation(summary = "OpenAPI xsd operation example", description = "Rest operation example with objects generated from xsd")
    @POST
    @Produces({MediaType.APPLICATION_JSON, MediaType.TEXT_XML, MediaType.APPLICATION_XML})
    @Consumes({MediaType.APPLICATION_JSON, MediaType.TEXT_XML, MediaType.APPLICATION_XML})
    @Path("/xsd")
    public SampleType getLocalizationEnum(SampleType sampleType) {
        return null;
    }
}
