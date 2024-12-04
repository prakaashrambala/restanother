package Pet;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;
import io.restassured.http.ContentType;
public class UpdatePet {






    // Base URI for Petstore API
    private static final String BASE_URI = "https://petstore.swagger.io/v2";
    // Replace with your authentication token if needed
    //private static final String AUTH_TOKEN = "your_auth_token_here";

    @Test
    public void updatePetWithForm() {
    	// Define the petId to update
        int petId = 119;  // Example petId to update

        // Define the updated pet details as JSON
        String updatedPetJson = "{\n" +
                "  \"id\": " + petId + ",\n" +
                "  \"name\": \"Ponds\",\n" +
                "  \"status\": \"sold\"\n" +
                "}";

        // Send POST request to update pet using JSON payload
        Response response = RestAssured
                .given()
                .baseUri(BASE_URI)
                .header("accept", "application/json") 
                .contentType(ContentType.JSON)  // Set Content-Type to JSON
                .accept(ContentType.JSON)  // Expect a JSON response
                //.pathParam("petId", petId) // Path parameter  
                .body(updatedPetJson)  // JSON body with updated pet details
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
        
        // Optionally, you can check if the name and status were updated successfully in the response
       // Assert.assertTrue(response.getBody().asString().contains("Tommy"));
        //Assert.assertTrue(response.getBody().asString().contains("sold"));
    }

    
   
    }
