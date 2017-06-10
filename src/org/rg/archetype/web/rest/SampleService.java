package org.rg.archetype.web.rest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.rg.archetype.data.shared.ResponseDTO;

import com.google.gson.Gson;

@Path("sample")
public class SampleService {

	@GET
	@Path("/sayHello")
	@Produces({"application/json"})
	public String sayHello() {
		return "Hello World!";
	}
	
	@GET
	@Path("/json")
	@Produces({MediaType.APPLICATION_JSON})
	public Response json() {
		ResponseDTO<String> helloResponse = new ResponseDTO<String>(ResponseDTO.RESULT_OK, "Hello World!");
		String json = new Gson().toJson(helloResponse);
		return Response.ok(json, MediaType.APPLICATION_JSON).build();
	}
}
