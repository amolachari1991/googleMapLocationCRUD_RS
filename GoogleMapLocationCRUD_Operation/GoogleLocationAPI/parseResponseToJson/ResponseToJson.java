package parseResponseToJson;

import io.restassured.path.json.JsonPath;

public class ResponseToJson {
	
	public static JsonPath responseToJson(String response) {
		JsonPath js = new JsonPath(response);//navigation from parent like "parnet.response" passed to Json object
		return js;
	}

}