package Pet;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;
import io.restassured.http.ContentType;
public class PetDel {
	    // Base URI for Petstore API
	    private static final String BASE_URI = "https://petstore.swagger.io/v2";

	    @Test
	    public void deletePetWithoutAuth() {
	        // Define the petId to delete (replace with an existing petId for successful deletion)
	        int petId = 102;  // Example petId to delete

	        // Send DELETE request to delete the pet by petId
	        Response response = RestAssured
	                .given()
	                .baseUri(BASE_URI)
	                .contentType(ContentType.JSON)  // Set Content-Type to JSON
	                .accept(ContentType.JSON)  // Expect a JSON response
	                .pathParam("petId", petId)  // Path parameter for petId
	                .when()
	                .delete("/pet/{petId}")  // DELETE request to /pet/{petId}
	                .then()
	                .statusCode(200)  // Check if status code is 200 (successful deletion)
	                .extract()
	                .response();

	        // Print the response for debugging
	        System.out.println("Response Body: " + response.getBody().asString());

	        // Assert the status code is 200 (successful deletion)
	        Assert.assertEquals(response.getStatusCode(), 200);
	    }

	

}
