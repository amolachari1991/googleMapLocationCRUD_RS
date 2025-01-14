package parseComplexJson;

import org.testng.Assert;
import org.testng.annotations.Test;

import io.restassured.path.json.JsonPath;
import parseResponseToJson.ResponseToJson;
import paylods.Bodies;

public class SumOfCoursesPrice {

	@Test
	public static void coursePriceSum() {
	JsonPath js = ResponseToJson.responseToJson(Bodies.courses());
	int courseCount = js.getInt("courses.size()");
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
