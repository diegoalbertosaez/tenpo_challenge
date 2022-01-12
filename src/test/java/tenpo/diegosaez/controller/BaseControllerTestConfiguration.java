package tenpo.diegosaez.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

@ActiveProfiles("test")
public class BaseControllerTestConfiguration {

	@Autowired
	protected MockMvc mockMvc;

	protected String getJsonFrom(Object toJson) {
		String json = null;
		try {
			ObjectMapper mapper = new ObjectMapper();
			ObjectWriter writer = mapper.writer().withDefaultPrettyPrinter();
			json = writer.writeValueAsString(toJson);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return json;
	}

}
