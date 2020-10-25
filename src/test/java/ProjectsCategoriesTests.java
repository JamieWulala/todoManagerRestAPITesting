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

    private static String ProjectId;
    private static String CategoryId;

    @Before
    public void setUp() {
        //Post a project and a category
        Map<String, String> requestBodyProj = new HashMap<>();
        requestBodyProj.put("title", "New ProjectTitle");
        requestBodyProj.put("description", "New Description");

        Map<String, String> requestBodyCat = new HashMap<>();
        requestBodyCat.put("title", "New CategoryTitle");

        ProjectId = given().
                contentType("application/json")
                .body(requestBodyProj).
                when().
                post("/projects").
                then().
                statusCode(201)
                .extract()
                .jsonPath().getString("id");

        CategoryId = given().
                contentType("application/json")
                .body(requestBodyCat).
                when().
                post("/categories").
                then().
                statusCode(201)
                .extract()
                .jsonPath().getString("id");

        //Create a valid project-category relationship
        Map<String, String> requestBodyID = new HashMap<>();
        requestBodyID.put("id", CategoryId);
        given().
                contentType("application/json").
                body(requestBodyID).
        when().
                post("/projects/{id}/categories", ProjectId).
        then().
                statusCode(201);

    }

    @Test
    public void testGetProjectCategories() {
        //get all the categories of a project
        System.out.println("Test: GET /projects/:id/categories - Valid Operation");
        when().
                get("/projects/{id}/categories", ProjectId).
        then().
                statusCode(200);
    }

    @Test
    public void testHeadProjectCategories() {
        //get all the categories of a project
        System.out.println("Test: HEAD /projects/:id/categories - Valid Operation");
        when().
                head("/projects/{id}/categories", ProjectId).
        then().
                statusCode(200);
    }



    @Test
    public void testPostProjectCategory() {

        Map<String, String> requestBodyID = new HashMap<>();
        requestBodyID.put("id", CategoryId);
        var categoryID_invalid = "9999";

        //post a new relationship with a new category
        System.out.println("Test: POST /projects/:id/categories - Valid Operation: new category");
        given().
                contentType("application/json").
                body(requestBodyID).
        when().
                post("/projects/{id}/categories", ProjectId).
        then().
                statusCode(201);

        //post a new relationship with a category that is already a category under this project
        System.out.println("Test: POST /projects/:id/categories - valid Operation: valid category id");
        given().
                contentType("application/json").
                body(requestBodyID).
        when().
                post("/projects/{id}/categories", ProjectId).
        then().
                statusCode(201);

        //post a new relationship with an non-existing category ID
        System.out.println("Test: POST /projects/:id/categories - Invalid Operation: invalid category id");
        requestBodyID.put("id", categoryID_invalid);
        given().
                contentType("application/json").
                body(requestBodyID).
        when().
                post("/projects/{id}/categories", ProjectId).
        then().
                statusCode(404);

    }

    @Test
    public void testDeleteProjectCategory() {

        var categoryID_invalid = 9999;

        //delete an existing category relationship
        System.out.println("Test: DELETE /projects/:id/categories/:id - valid operation: valid category");

        when().
                delete("/projects/{id}/categories/{category_id}", ProjectId, CategoryId).
        then().
                statusCode(200);

        //delete non-existing category relationship
        System.out.println("Test: DELETE /projects/:id/categories/:id - invalid operation: invalid category");

        when().
                delete("/projects/{id}/categories/{category_id}", ProjectId, categoryID_invalid).
        then().
                statusCode(404);
    }

}
