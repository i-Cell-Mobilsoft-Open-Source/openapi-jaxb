package hu.icellmobilsoft.jaxb.openapidemo.rest;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import hu.icellmobilsoft.openapi.dto.sample.sample.SampleType;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

@Tag(name = "OpenAPI xsd példa", description = "Példa rest service xsd-ből generált objektumokkal")
@Path("/sample")
public class SampleXsdRest {

    @Operation(summary = "OpenAPI xsd példa", description = "Példa rest service xsd-ből generált objektumokkal")
    @POST
    @Produces({MediaType.APPLICATION_JSON, MediaType.TEXT_XML, MediaType.APPLICATION_XML})
    @Consumes({MediaType.APPLICATION_JSON, MediaType.TEXT_XML, MediaType.APPLICATION_XML})
    @Path("/xsd")
    public SampleType getLocalizationEnum(SampleType sampleType) {
        return null;
    }
}
