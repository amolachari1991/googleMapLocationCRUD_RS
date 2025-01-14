package GoogleLocationCRUD;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import parseResponseToJson.ResponseToJson;
import paylods.Bodies;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import org.testng.Assert;


public class GoogleLocationPost {

	public static void main(String[] args) {
		
		//createPlace
		System.out.println("\ncreatePlace***************************************************\n");

		RestAssured.baseURI ="https://rahulshettyacademy.com";
		
		String response= given().log().all().queryParam("key", "qaclick123").
		header("Content-Type" , "application/json").
		body(Bodies.createLocation()).
		when().post("maps/api/place/add/json").
		then().log().all().assertThat().statusCode(200).body("scope", equalTo("APP")).
		header("Server", "Apache/2.4.52 (Ubuntu)").extract().response().asString();
		
		System.out.println(response);
		
		JsonPath js = ResponseToJson.responseToJson(response);//parsing the string response into Json format
		String place_ID =  js.getString("place_id");//use the JsonPath class methods to featch required attribute
		System.out.println(place_ID);
		
		//updatePlace
		System.out.println("\nupdatePlace***************************************************\n");
		String placeName = "Balewadi High Street, Baner, pune";
		String responseUpdate = given().log().all().queryParam("key", "qaclick123").queryParam("place_id", place_ID).
		body(Bodies.updateLocation(place_ID , placeName)).
		when().put("/maps/api/place/update/json").
		then().log().all().assertThat().statusCode(200).extract().response().asString();
		
		JsonPath jUpdate = ResponseToJson.responseToJson(responseUpdate);
		System.out.println(jUpdate.getString("msg"));
		
		
		//getPlace
		System.out.println("\ngetPlace***************************************************\n");
		
		String responseGet = given().log().all().queryParam("key", "qaclick123").
		queryParam("place_id", place_ID).
		when().get("/maps/api/place/get/json").
		then().log().all().assertThat().statusCode(200).//body("address", equalTo("canicutt, USA")).
		extract().response().asString();
		
		JsonPath jget = ResponseToJson.responseToJson(responseGet);
		String updatedAddress = jget.getString("address");
		System.out.println("Updated Adress = "+updatedAddress);
		
		Assert.assertEquals("Balewadi High Street, Baner, pune", updatedAddress);

		
		//deletePlace
		
		System.out.println("\ndeletePlace***************************************************\n");
		String responseDelete = given().log().all().queryParam("key", "qaclick123").
		body(Bodies.deleteLocation(place_ID)).
		when().delete("/maps/api/place/delete/json").
		then().extract().response().asString();
		
		System.out.println("Location deleted= "+ResponseToJson.responseToJson(responseDelete).getString("status"));
		
		//getPlace
		System.out.println("\ngetPlace***************************************************\n");
		
		String responseGet1 = given().log().all().queryParam("key", "qaclick123").
		queryParam("place_id", place_ID).
		when().get("/maps/api/place/get/json").
		then().log().all().assertThat().statusCode(200).body("address", equalTo("canicutt, USA")).
		extract().response().asString();
		
		JsonPath jget1 = ResponseToJson.responseToJson(responseGet1);
		String updatedAddress1 = jget1.getString("address");
		System.out.println("Updated Adress = "+updatedAddress1);
		
	}

}
