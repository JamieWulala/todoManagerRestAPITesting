import io.restassured.RestAssured;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static org.hamcrest.Matchers.equalTo;

public class ProjectsTests extends ApiTest{

    @Test
    public void testGetProjects() {
        //get all the instances of project
        System.out.println("Test: GET /projects - Valid Operation");
        when().
                get("/projects").
        then().
                statusCode(200);
    }



    @Test
    public void testPostProjects() {
        String projectTitle = "Some Project Title";
        String projectDescription = "Some project Description";
        //Request body with title only
        Map<String, String> requestBodyTitle = new HashMap<>();
        requestBodyTitle.put("title", projectTitle);



        //create a project with title parameter
        System.out.println("Test: POST /projects - Valid Operation: Only Title");
        given().
                contentType("application/json").
                body(requestBodyTitle).
        when().
                post("/projects").
        then().
                statusCode(201).
                body("title", equalTo(projectTitle));

//-------------------------------------------------------------------------------------------------------------
        //create a project with empty body
        String emptyJSONPayload = "";
        System.out.println("Test: POST /projects - Valid Operation: Empty Body");
        given().
                contentType("application/json").
                body(emptyJSONPayload).
        when().
                post("/projects").
        then().
                statusCode(201);

//-------------------------------------------------------------------------------------------------------------

        //create a project with malformed JSON
        String malformedJSONPayload = "{\"title\": \"test title\"";
        System.out.println("Test: POST /projects - Invalid Operation: Malformed JSON");
        String errorMessage =
        given().
                contentType("application/json").
                body(malformedJSONPayload).
        when().
                post("/projects").
        then().
                statusCode(400).
        extract().
                jsonPath().getString("errorMessages");
        System.out.println("   Known Bug/Java Exception caused by Malformed JSON: " + errorMessage);
//-------------------------------------------------------------------------------------------------------------

    }


    @Test
    public void testPostSpecificProject() {
        String projectId = "1";
        String projectTitle = "New Project Title";
        String projectDescription = "New project Description";
        //Request body with title only
        Map<String, String> requestBodyTitle = new HashMap<>();
        requestBodyTitle.put("title", projectTitle);
        requestBodyTitle.put("description", projectDescription);

        //amend a specific instances of project
        System.out.println("Test: POST /projects/:id - Valid Operation");
        given().
                contentType("application/json").
                body(requestBodyTitle).
        when().
                post("/projects/{id}", projectId).
        then().
                statusCode(200).
                body("title", equalTo(projectTitle)).
                body("description", equalTo(projectDescription));
    }


    @Test
    public void testPutSpecificProject() {
        //put a todo with malformed XML file
        String projectId = "1";
        //Request body with title only
        Map<String, String> requestBodyTitle = new HashMap<>();
        requestBodyTitle.put("title", "New Title");
        String malformedXMLPayload = "<project>test project";
 //-------------------------------------------------------------------------------------------------------------
        System.out.println("Test: PUT /projects/:id - Invalid Operation: Malformed XML");
        String errorMessage =
                given().
                        contentType("application/xml").
                        body(malformedXMLPayload).
                when().
                        put("/projects/{id}", projectId).
                then().
                        statusCode(400).
                extract().
                        jsonPath().getString("errorMessages");
        System.out.println("   Error Message caused by Malformed XML: " + errorMessage);
//-------------------------------------------------------------------------------------------------------------
        System.out.println("Test: PUT /projects/:id - Invalid Operation: invalid id");
        String invalidIDError =
                given().
                        contentType("application/json").
                        body(requestBodyTitle).
                when().
                        put("/projects/{id}", 77).
                then().
                        statusCode(404).
                extract().
                        jsonPath().getString("errorMessages");
        System.out.println("   Error Message caused by invalid id: " + invalidIDError);


    }


}
