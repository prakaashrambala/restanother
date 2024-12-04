package Pet;

import org.testng.annotations.Test;
import org.testng.annotations.BeforeClass;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import static io.restassured.RestAssured.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import static org.hamcrest.Matchers.equalTo;
//import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.json.JSONObject;


public class PetstoreAPITest2 {

    private static final Logger logger = LoggerFactory.getLogger(PetstoreAPITest2.class);
    
    // Base URL for the Petstore API
    private static final String BASE_URL = "https://petstore.swagger.io/v2";
    //private static final String PET_API = "/pet";
    private static String petId;
    private RequestSpecification request;

    @BeforeClass
    public void setup() {
        // Initialize RestAssured
        RestAssured.baseURI = BASE_URL;
        request = RestAssured.given();
    }
    
    // Utility method to read the JSON file and return as String
    private String readJsonFile(String filePath) throws IOException {
        return new String(Files.readAllBytes(Paths.get(filePath)));
    }
    
    @Test (priority =1)
    public void testPostCreatePet() throws IOException {
    	    	// Sample pet data for POST request
    	
    	File jsonFile = new File("D:/Users/rabalasubramaniam/eclipse-workspace/PetSample/src/test/resources/testdata/CreatePetBody.json");
    	 // Sending the POST request with JSON file
        Response response = given()
                .contentType(ContentType.JSON)
                .body(jsonFile)  // Attach the JSON file to the request
                .when()
                .post("/pet");

        // Validate the response status code
        response.then().statusCode(200);
        logger.info("Response status code: {}", response.statusCode());
     
        System.out.println("Response Body: ");
        response.body().prettyPrint(); 
        
        String jsonString = new String(Files.readAllBytes(Paths.get(jsonFile.getPath())));
        JSONObject jsonObject = new JSONObject(jsonString);
        // Extract values from the JSON object for validation
        String expectedName = jsonObject.getString("name");
        String expectedStatus = jsonObject.getString("status");

        // Validate that the response matches the name and status from the JSON file
        response.then().body("name", equalTo(expectedName));
        response.then().body("status", equalTo(expectedStatus));
        System.out.println("This will execute first as Create Operation"); 
        petId = response.jsonPath().getString("id");
        System.out.println("Pet Created with ID: " + petId);
        logger.info("Pet created successfully !!!!!!.");
  
        
    }
    
    // Read: GET Request to fetch pet details
    @Test(priority = 2)
    public void getPet() {
        Response response = RestAssured.given()
                .pathParam("petId", petId)
                .when()
                .get("/pet/{petId}")
                .then()
                .statusCode(200)
                .extract().response();
        System.out.println("Response Body: For Get Operatiom");
        
        logger.info("Response status code: {}", response.statusCode());
        logger.info("Pet Details below !!!!!!.");
        response.body().prettyPrint(); 
        String petName = response.jsonPath().getString("name");
        System.out.println("Fetched Pet Name: " + petName);
    }
 
     // Update: PUT Request to update pet details
    
        @Test(priority = 3)
       
        public void updatePet() throws Exception  {
            // Read pet data from JSON file and update the status
        	 System.out.println("Checking update ops");
            String petJson = readJsonFile("D:/Users/rabalasubramaniam/eclipse-workspace/PetSample/src/test/resources/testdata/CreatePetBody.json");
            //petJson = petJson.replace("available", "sold");
            JSONObject jsonObject = new JSONObject(petJson);

            // Replace the status value with "sold"
            jsonObject.put("id", 119);
            jsonObject.put("name", "ram");
            jsonObject.put("status", "sold");
            System.out.println("updated status");
            // Convert the modified JSONObject back to a string
            String updatedPetJson = jsonObject.toString();

            // Send PUT request to update pet
            Response response = RestAssured.given()
            		.header("accept", "application/json") 
                    .contentType(ContentType.JSON)
                    .accept(ContentType.JSON)
                    .body(updatedPetJson)
                    //.pathParam("petId", petId)
                    .when()
                    .put("/pet")
                    .then()
                    .statusCode(200)
                    .extract().response();
            System.out.println("Response Body: For Update ");
            response.body().prettyPrint(); 
            logger.info("Response status code: {}", response.statusCode());
            logger.info("Pet Details Updated !!!!!!.");

            String petStatus = response.jsonPath().getString("status");
            System.out.println("Updated Pet Status: " + petStatus);
            logger.debug("Updated Pet Status: {}", petStatus);
            //Assert.assertEquals(petName, "dummy", "Pet name should be 'dummy' after update.");
            Assert.assertEquals(petStatus, "sold", "Pet status should be updated to 'sold'.");
        }

        // Delete: DELETE Request to remove a pet
        @Test(priority = 4, enabled = true)
        public void deletePet() 
        {
            Response response = RestAssured.given()
                    .pathParam("petId", petId)
                    .when()
                    .delete("/pet/{petId}")
                    .then()
                    .statusCode(200)
                    .extract().response();
            System.out.println("Response Body: for Delete");
            response.body().prettyPrint(); 

            System.out.println("Deleted Pet with ID: " + petId);
            System.out.println("Checking after After delete");
            // Verify pet is deleted by trying to fetch the pet details again
            Response getResponse = RestAssured.given()
            		.pathParam("petId", petId)
                    .when()
                    .get("/pet/{petId}")
                    .then()
                    .statusCode(404)
                    .extract().response();       
            System.out.println("Response Body: After delete");
            response.body().prettyPrint(); 
            logger.info("Response status code: {}", response.statusCode());
            logger.info("Pet Details Deleted !!!!!!.");
            String errorMessage = getResponse.jsonPath().getString("message");
            Assert.assertEquals(errorMessage, "Pet not found", "Expected 'Pet not found' error message after deletion.");
            System.out.println("End");
        }
    	

  

}


   
    

