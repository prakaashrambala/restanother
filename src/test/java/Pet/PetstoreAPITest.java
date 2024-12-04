package Pet;


import io.restassured.RestAssured;
import static io.restassured.RestAssured.*;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PetstoreAPITest {

    private static final Logger logger = LoggerFactory.getLogger(PetstoreAPITest.class);
    
    // Base URL for the Petstore API
    private static final String BASE_URL = "https://petstore.swagger.io/v2";
    private RequestSpecification request;

    @BeforeClass
    public void setup() {
        // Initialize RestAssured
        RestAssured.baseURI = BASE_URL;
        request = RestAssured.given();
    }

    @Test(priority =0)
    public void testPostCreatePet() {
    	
    
        // Sample pet data for POST request
        String jsonPayload = "{\n" +
                "  \"id\": 119,\n" +
                "  \"category\": {\n" +
                "    \"id\": 1,\n" +
                "    \"name\": \"India\"\n" +
                "  },\n" +
                "  \"name\": \"ponds\",\n" +
                "  \"photoUrls\": [\n" +
                "    \"string\"\n" +
                "  ],\n" +
                "  \"tags\": [\n" +
                "    {\n" +
                "      \"id\": 1,\n" +
                "      \"name\": \"Dots\"\n" +
                "    }\n" +
                "  ],\n" +
                "  \"status\": \"available\"\n" +
                "}";


        // POST request to create a pet
        Response response = request
                .contentType("application/json")
                .body(jsonPayload)
                .post("/pet");

        //Log the response for debugging
        logger.info("Response: " + response.getBody().asString());
        

        	
        // Validate the response code
        //Assert.assertEquals(response.getStatusCode(), 200, "Status Code Success");

        // Validate response body contains expected pet data
        //Assert.assertTrue(response.getBody().asString().contains("tom"), "Pet name mismatch");

        // Additional data validation (e.g., pet ID)
        //Assert.assertTrue(response.getBody().asString().contains("111"), "Pet ID mismatch");
        //given().header("accept", "content-type=json");
        
    }

}
    

