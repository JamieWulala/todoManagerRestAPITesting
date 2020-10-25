import io.restassured.RestAssured;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.InputStream;
import java.util.*;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.equalTo;

public class CategoriesTests extends ApiTest{

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
    public void testGetCategories(){
        System.out.println("Test: GET /categories - Valid Operation");
        when().
                get("/categories").
        then().
                statusCode(200);
    }

    @Test
    public void testHeadCategories(){
        System.out.println("Test: HEAD /categories - Valid Operation");
        when().
                head("/categories").
                then().
                statusCode(200);
    }

    @Test
    public void testPostCategories(){
        System.out.println("Test: POST /categories - Valid Operation");
        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("title", "categoryTitle");

        given().
                contentType("application/json")
                .body(requestBody).
        when().
                post("/categories").
                then().
                statusCode(201);
    }

    @Test
    public void testGetCategoriesWithId(){
        System.out.println("Test: GET /categories/:id - Valid Operation");
        when().
                get("/categories/{id}", categoryId).
                then().
                statusCode(200);
    }

    @Test
    public void testHeadCategoriesWithId(){
        System.out.println("Test: HEAD /categories/:id - Valid Operation");
        when().
                head("/categories/{id}", categoryId).
                then().
                statusCode(200);
    }

    @Test
    public void testGetCategoriesWithInvalidId(){
        System.out.println("Test: GET /categories/:id - Non-existing id Invalid Operation");
        when().
                get("/categories/99").
                then().
                statusCode(404);
    }

    @Test
    public void testPostCategoriesWithId(){
        System.out.println("Test: Post /Categories/:id - Valid Operation");
        String categoryTitle = "new title";
        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("title", categoryTitle);

        given().
                contentType("application/json")
                .body(requestBody)
        .when()
                .post("/categories/{id}", categoryId)
        .then()
                .statusCode(200)
                .body("title", equalTo(categoryTitle));
    }

    @Test
    public void testPutCategoriesWithId(){
        System.out.println("Test: Put /Categories/:id - Valid Operation");
        String categoryTitle = "new title";
        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("title", categoryTitle);

        given().
                contentType("application/json")
                .body(requestBody)
                .when()
                .post("/categories/{id}", categoryId)
                .then()
                .statusCode(200)
                .body("title", equalTo(categoryTitle));
    }

    @Test
    public void testPostCategoriesWithTitle(){
        System.out.println("Test: Post /Categories - Valid Operation");
        String categoryTitle = "school";
        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("title", categoryTitle);

        given().
                contentType("application/json")
                .body(requestBody)
        .when()
                .post("/categories")
        .then()
                .statusCode(201)
                .body("title", equalTo(categoryTitle));
    }

    @Test
    public void testPostCategoriesWithEmptyBody(){
        System.out.println("Test: Post /Categories with empty body - Invalid Operation");
        List<String> errorMessage = Arrays.asList("title : field is mandatory");
        Map<String, String> requestBody = new HashMap<>();

        given().
                contentType("application/json")
                .body(requestBody)
        .when()
                .post("/categories")
        .then()
                .statusCode(400)
                .body("errorMessages", equalTo(errorMessage));
    }

    @Test
    public void testDeleteCategories() {
        System.out.println("Test: Delete /Categories/:id - Valid Operation");
        Map<String, String> requestBody = new HashMap<String, String>();
        requestBody.put("title", "someTitle");
        String id =
                given().
                        body(requestBody).
                        post("/categories")
                .then().
                        statusCode(201).
                extract().
                        jsonPath().getString("id");

        given().
                contentType("application/json")
        .when()
                .delete("/categories/{id}", id)
        .then()
                .statusCode(200);
    }

    @Test
    public void testDeleteNonExistingCategories() {
        System.out.println("Test: Delete /Categories/:id - Non-existing id, Invalid Operation");

        List<String> errorMessage = Arrays.asList("Could not find any instances with categories/1");
        delete("/categories/1");

        given().
                contentType("application/json")
        .when()
                .delete("/categories/1")
        .then()
                .statusCode(404)
                .body("errorMessages", equalTo(errorMessage));
    }
}
