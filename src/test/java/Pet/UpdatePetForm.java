package Pet;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;
import io.restassured.http.ContentType;

public class UpdatePetForm {



	    // Base URI for Petstore API
	    private static final String BASE_URI = "https://petstore.swagger.io/v2";

	    @Test
	    public void updatePet() {
	        // Define the petId to update
	        int petId = 119;  // Example petId to update

	        // Define updated data for pet
	        String updatedName = "P dog";
	        String updatedStatus = "available";

	        // Send POST request to update the pet with form data
	        Response response = RestAssured
	                .given()
	                .baseUri(BASE_URI)
	                .header("accept", "application/json") 
	                .contentType(ContentType.JSON)  // Set Content-Type to application/x-www-form-urlencoded
	                .accept(ContentType.JSON)  // Expect JSON response
	                .formParam("name", updatedName)  // Form parameter for name
	                .formParam("status", updatedStatus)  // Form parameter for status
	                .pathParam("petId", petId)  // Path parameter for petId
	                .when()
	                .put("/pet")  // POST request to /pet/{petId}
	                .then()
	                .statusCode(200)  // Check if status code is 200 (successful update)
	                .extract()
	                .response();

	        // Print the response for debugging
	        System.out.println("Response Body: " + response.getBody().asString());

	        // Assert the status code is 200 (successful update)
	        Assert.assertEquals(response.getStatusCode(), 200);
	    }
	}


