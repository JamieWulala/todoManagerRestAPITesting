import io.restassured.RestAssured;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.*;

public class CategoriesProjectsTests extends ApiTest{

    private static String categoriesId;
    private static String projectId;

    @Before
    public void setUpCategoris(){
        HashMap categoryRequestBody = new HashMap<>();
        categoryRequestBody.put("title", "categoryTitle");
        categoriesId =
            given().
                contentType("application/json").
                body(categoryRequestBody).
                post("/categories").
            then().
                 statusCode(201).
            extract().
                jsonPath().getString("id");
        projectId =
            given().
                contentType("application/json").
                post("/projects").
            then().
                statusCode(201).
            extract().
                jsonPath().getString("id");
    }

    @Test
    public void testGetCategoriesProjects() {
        System.out.println("Test: GET /categories/:id/projects - Valid Operation");

        when().
            get("/categories/{categoriesId}/projects", categoriesId).
        then().
            statusCode(200);
    }

    @Test
    public void testGetNonExistingCategoriesProjects() {
        System.out.println("Test: GET /categories/:id/projects - Invalid Operation");

        when().
            get("/categories/{categoriesId}/projects", 999).
        then().
            statusCode(200);
        //bug caught
        System.out.println("   Known Bug/should return errorMessage and 404 status code when finding a non-existing id");
    }

    @Test
    public void testHeadCategories() {
        System.out.println("Test: HEAD /categories/:id/projects - Valid Operation");

        when().
            head("/categories/{categoriesId}/projects", categoriesId).
        then().
            statusCode(200);
    }

    @Test
    public void testPostCategoriesProjects() {
        System.out.println("Test: POST /categories/:id/projects - Valid Operation");

        given().
            contentType("application/json").
        when().
            post("/categories/{id}/projects", categoriesId).
        then().
            statusCode(201);
    }

    @Test
    public void testDeleteCategoriesProjectsId() {
        System.out.println("Test: DELETE /categories/:id/projects/:id - Valid Operation");
        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("id", projectId);

        given().
            body(requestBody).
        when().
            post("/categories/{categoriesId}/projects", categoriesId).
        then().
            statusCode(201);

        when().
            delete("/categories/{categoriesId}/projects/{projectId}", categoriesId, projectId).
        then().
            statusCode(200);
    }

}
