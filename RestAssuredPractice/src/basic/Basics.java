package basic;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import org.testng.Assert;

import files.Payload;

public class Basics {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		RestAssured.baseURI="https://rahulshettyacademy.com/";
		String response=given().log().all().queryParam("key","qaclick123").header("Content-Type","application/json")
		.body(Payload.addplace())
		.when().post("/maps/api/place/add/json")
		.then().assertThat().statusCode(200).body("scope",equalTo("APP"))
		.header("server","Apache/2.4.18 (Ubuntu)").extract().response().asString();
		
		System.out.println(response);
		
		//for parsing json
		JsonPath js=new JsonPath(response);
		
		String placeid=js.getString("place_id");
		
		System.out.println(placeid);
		
		//update place
		
		String newAddress="Summer walk, NewYork";
		given().log().all().queryParam("key","qaclick123").header("Content-Type","application/json")
		.body("{\r\n" + 
				"\"place_id\":\""+placeid+"\",\r\n" + 
				"\"address\":\""+newAddress+"\",\r\n" + 
				"\"key\":\"qaclick123\"\r\n" + 
				"}\r\n" + 
				"")
		.when().put("/maps/api/place/update/json")
		.then().assertThat().log().all().statusCode(200).body("msg",equalTo("Address successfully updated"));
		
		//get place api
		String getPlaceresponse=given().log().all().queryParam("key","qaclick123")
		.queryParam("place_id",placeid)
		.when().get("/maps/api/place/update/json")
		.then().assertThat().log().all().statusCode(200).extract().response().asString();
		
		JsonPath js1=new JsonPath(getPlaceresponse);
		
		String actualaddress=js1.getString("address");
		
		System.out.println(actualaddress);
		Assert.assertEquals(actualaddress,newAddress);
		
	}

}
