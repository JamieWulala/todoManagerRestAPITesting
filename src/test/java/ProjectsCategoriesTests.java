import io.restassured.RestAssured;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;

public class ProjectsCategoriesTests extends ApiTest{

    @Test
    public void testGetProjectCategories() {
        //get all the categories of a project
        String projectId = "1";
        System.out.println("Test: GET /projects/:id/categories - Valid Operation");
        when().
                get("/projects/{id}/categories", projectId).
        then().
                statusCode(200);
    }



    @Test
    public void testPostProjectCategory() {

        String projectId = "1";
        String categoryID = "1";
        String categoryID_invalid = "77";
        Map<String, String> requestBodyTitle = new HashMap<>();
        requestBodyTitle.put("title", "category title");
        Map<String, String> requestBodyID = new HashMap<>();
        requestBodyID.put("id", categoryID);
//-------------------------------------------------------------------------------------------------------------
        //post a new relationship with a new category
        System.out.println("Test: POST /projects/:id/categories - Valid Operation: new category");
        given().
                contentType("application/json").
                body(requestBodyTitle).
        when().
                post("/projects/{id}/categories", projectId).
        then().
                statusCode(201);
 //-------------------------------------------------------------------------------------------------------------
        //post a new relationship with an existing category ID
        System.out.println("Test: POST /projects/:id/categories - valid Operation: valid category id");
        given().
                contentType("application/json").
                body(requestBodyID).
        when().
                post("/projects/{id}/categories", projectId).
        then().
                statusCode(201);
 //-------------------------------------------------------------------------------------------------------------
        //post a new relationship with an non-existing category ID
        System.out.println("Test: POST /projects/:id/categories - Invalid Operation: invalid category id");
        requestBodyID.put("id", categoryID_invalid);
        given().
                contentType("application/json").
                body(requestBodyID).
        when().
                post("/projects/{id}/categories", projectId).
        then().
                statusCode(404);

    }

    @Test
    public void testDeleteProjectCategory() {
        String projectId = "1";

        String categoryID = "1";
        String categoryID_invalid = "77";

//-------------------------------------------------------------------------------------------------------------
        //delete an existing category
        System.out.println("Test: DELETE /projects/:id/categories/:ID - valid operation: valid category");

        when().
                delete("/projects/{id}/categories/{category_id}", projectId, categoryID).
        then().
                statusCode(200);
//-------------------------------------------------------------------------------------------------------------
        //delete non-existing category
        System.out.println("Test: DELETE /projects/:id/categories/:ID - invalid operation: invalid category");

        when().
                delete("/projects/{id}/categories/{category_id}", projectId, categoryID_invalid).
        then().
                statusCode(404);
    }

}
