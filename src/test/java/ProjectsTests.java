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
    private static String ProjectId;

     @Before
    public void setUpProject() {
        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("title", "New ProjectTitle");
        requestBody.put("description", "New Description");

        ProjectId =
        given().
            contentType("application/json").
            body(requestBody).
        when().
            post("/projects").
        then().
            statusCode(201).
        extract().
            jsonPath().getString("id");
        System.out.println("Project Created With ID: " + ProjectId);
    }

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
    public void testHeadProjects() {
        System.out.println("Test: HEAD /projects - Valid Operation");
        when().
            head("/projects").
        then().
            statusCode(200);
    }

    @Test
    public void testPostProjectsValid() {
        String projectTitle = "Some Project Title";
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
    }

    @Test
    public void testPostProjectsEmptyJSON() {
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
    }

    @Test
    public void testPostProjectsMalJSON() {
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
    }

    @Test
    public void testGetSpecificProject() {
        System.out.println("Test: GET /projects/:id - Valid Operation");
        when().
            get("/projects/{id}", ProjectId).
        then().
            statusCode(200);
    }

    @Test
    public void testHeadSpecificProject() {
        System.out.println("Test: HEAD /projects/:id - Valid Operation");
        when().
            head("/projects/{id}", ProjectId).
        then().
            statusCode(200);
    }

    @Test
    public void testPostSpecificProject() {
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
            post("/projects/{id}", ProjectId).
        then().
            statusCode(200).
            body("title", equalTo(projectTitle)).
            body("description", equalTo(projectDescription));
    }

    @Test
    public void testPutSpecificProjectMalXML() {
        String malformedXMLPayload = "<project>test project";
        //put a todo with malformed XML file
        System.out.println("Test: PUT /projects/:id - Invalid Operation: Malformed XML");
        String errorMessage =
            given().
                contentType("application/xml").
                body(malformedXMLPayload).
            when().
                put("/projects/{id}", ProjectId).
            then().
                statusCode(400).
            extract().
                jsonPath().getString("errorMessages");
        System.out.println("   Error Message caused by Malformed XML: " + errorMessage);
    }

    @Test
    public void testPutSpecificProjectInvalidId() {
        Map<String, String> requestBodyTitle = new HashMap<>();
        requestBodyTitle.put("title", "New Title");

        System.out.println("Test: PUT /projects/:id - Invalid Operation: invalid id");
        String invalidIDError =
            given().
                contentType("application/json").
                body(requestBodyTitle).
            when().
                put("/projects/{id}", 9999).
            then().
                statusCode(404).
            extract().
                jsonPath().getString("errorMessages");
        System.out.println("   Error Message caused by invalid id: " + invalidIDError);
    }

    @Test
    public void testDeleteSpecificProject() {
         //Create a project to delete
        String projectTitle = "New Project Title";
        //Request body with title only
        Map<String, String> requestBodyTitle = new HashMap<>();
        requestBodyTitle.put("title", projectTitle);

        var projectId =
            given().
                contentType("application/json").
                body(requestBodyTitle).
            when().
                post("/projects").
            then().
                statusCode(201).
            extract().
                jsonPath().getString("id");
        System.out.println("Project Created With ID: " + projectId);

        //Delete the created project
        System.out.println("Test: DELETE /projects/:id - Valid Operation");
        when().
            delete("/projects/{id}", projectId).
        then().
            statusCode(200);
    }

}
