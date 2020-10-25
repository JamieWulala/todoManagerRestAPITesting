import io.restassured.RestAssured;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;

public class CategoriesTodosTests extends ApiTest{

    private static String categoryId;

    @Before
    public void setUpCategroy() {

        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("title", "categoryTitle");

        categoryId = given().
                contentType("application/json")
                .body(requestBody).
                        when().
                        post("/categories").
                        then().
                        statusCode(201)
                .extract()
                .jsonPath().getString("id");
    }

    @Test
    public void testGetCategoriesIdTodods(){
        System.out.println("Test: GET /categories/:id/todos - Valid Operation");

        when()
                .get("/categories/{categoryId}/todos", categoryId).
        then()
                .statusCode(200);
    }

    @Test
    public void testHeadCategoriesIdTodods(){
        System.out.println("Test: HEAD /categories/:id/todos - Valid Operation");

        when()
                .head("/categories/{categoryId}/todos", categoryId).
        then()
                .statusCode(200);
    }

    @Test
    public void testPostCategoriesIdTodods(){
        System.out.println("Test: POST /categories/:id/todos - Valid Operation");
        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("title", "todoTitle");

        given()
                .body(requestBody)
                .contentType("application/json").
        when()
                .post("/categories/{categoryId}/todos", categoryId).
        then()
                .statusCode(201);
    }

    @Test
    public void testDeleteCategoriesIdTododsId(){
        System.out.println("Test: DELETE categories/:id/todos/:id - Valid Operation");

        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("id", "1");

        given()
                .body(requestBody).
        when()
                .post("/categories/{categoriesId}/todos", categoryId).
        then()
                .statusCode(201);

        when()
                .delete("/categories/{categoriesId}/todos/{projectId}", categoryId, 1)
                .then()
                .statusCode(200);
    }
}
