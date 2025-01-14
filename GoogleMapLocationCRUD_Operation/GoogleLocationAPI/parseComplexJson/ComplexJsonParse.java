package parseComplexJson;

import org.testng.Assert;
import org.testng.annotations.Test;

import io.restassured.path.json.JsonPath;
import parseResponseToJson.ResponseToJson;
import paylods.Bodies;

public class ComplexJsonParse {

	/*
	 * 1. Print No of courses returned by API
	 * 
	 * 2.Print Purchase Amount
	 * 
	 * 3. Print Title of the first course
	 * 
	 * 4. Print All course titles and their respective Prices
	 * 
	 * 5. Print no of copies sold by RPA Course
	 * 
	 * 6. Verify if Sum of all Course prices matches with Purchase Amount
	 */

	public static void main(String[] args) {

		JsonPath js = ResponseToJson.responseToJson(Bodies.courses());

//		1. Print No of courses returned by API
		
		int courseCount = js.getInt("courses.size()");
		System.out.println(courseCount);
		
//		2.Print Purchase Amount
		
		int purcheAmount = js.getInt("dashboard.purchaseAmount");//navigation from parent dashboard.purchaseAmount
		System.out.println(purcheAmount);
		
//		3. Print Title of the first course
		
		String firstCourseTitle = js.getString("courses[0].title");
		System.out.println(firstCourseTitle);
		
//		4. Print All course titles and their respective Prices

		for (int i = 0; i < courseCount; i++) {
			System.out.println(js.getString("courses[" + i + "].title").toString());
			System.out.println(js.getString("courses[" + i + "].price").toString());
			System.out.println(js.getString("courses[" + i + "].copies").toString());
		}

//		5. Print no of copies sold by RPA Course
	
		for (int i = 0; i < courseCount; i++) {
			if(js.getString("courses[" + i + "].title").equals("RPA")) {
			System.out.println("copies = "+js.getString("courses[" + i + "].copies").toString());
			}
		}
		
//		6. Verify if Sum of all Course prices matches with Purchase Amount
		int totalPrice = 0;
		for (int i = 0; i < courseCount; i++) {
			int price = js.getInt("courses[" + i + "].price");
			int copies = js.getInt("courses["+ i +"].copies");
			totalPrice = totalPrice + copies * price;
		}
		System.out.println("totalPrice "+totalPrice);
		if(totalPrice == js.getInt("dashboard.purchaseAmount")){
			System.out.println("Sum of all Course prices matches with Purchase Amount");
		}else {
			System.out.println("Sum of all Course prices NOT matches with Purchase Amount");

		}
		Assert.assertEquals(totalPrice, js.getInt("dashboard.purchaseAmount"));
	}

}
