package Pet;


import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

public class GetOperation {

    @Test
    public void getPetByIdWithAPIKey() {
        // Define the base URI for the Petstore API
        RestAssured.baseURI = "https://petstore.swagger.io/v2";
        
        // Define the petId that we want to fetch
        int petId = 116;  // Replace with the actual petId you want to test
        
        // Define your API Key
        //String apiKey = "your_api_key_here";  // Replace with your actual API key

        // Send GET request to fetch pet by ID with API Key
        Response response = RestAssured
                .given()
                // Add API Key in header
                .pathParam("petId", petId)  // Set the path parameter (petId)
                .when()
                .get("/pet/{petId}")  // GET request to /pet/{petId}
                .then()
                .statusCode(200)  // Assert that the status code is 200
                .extract()
                .response();

        // Print the response body for debugging
        System.out.println("Response Body: " + response.getBody().asString());

        // Assert that the response body is not null
        Assert.assertNotNull(response.getBody().asString());
        
        // Additional checks can be performed on the response
        Assert.assertEquals(response.getStatusCode(), 200);
    }

    
}
