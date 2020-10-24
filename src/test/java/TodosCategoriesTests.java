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

public class TodosCategoriesTests extends ApiTest{

    @Test
    public void testGetTodosCategories() {
        String todoId = "1";
        String categoryTitle = "Office";
        //get all the category items related to todo
        System.out.println("Test: GET /todos/:id/categories - Valid Operation Line Queries");
        System.out.println("   Undocumentated Capibility: Line Queries for GET /todos/:id/categories");
        when().
            get("/todos/{id}/categories?title={title}", todoId, categoryTitle).
        then().
            statusCode(200).
            body("categories[0].title", equalTo(categoryTitle));
    }

    @Test
    public void testHeadTodosCategories() {
        String todoId = "1";
        //headers for the category items related to todo
        System.out.println("Test: HEAD /todos/:id/categories - Valid Operation");
        when().
            head("/todos/{id}/categories", todoId).
        then().
            statusCode(200);
    }

    @Test
    public void testPostTodosCategories() {
        String todoId = "2";
        //create an instance of a relationship named tasksof between todo
        String malformedJSONPayload = "{\"title\": \"test title\"";
        System.out.println("Test: POST /todos/:id/categories - Invalid Operation: Malformed JSON");
        String errorMessageJSON =
            given().
                contentType("application/json").
                body(malformedJSONPayload).
            when().
                post("/todos/{id}/categories", todoId).
            then().
                statusCode(400).
            extract().
                jsonPath().getString("errorMessages");
        System.out.println("   Known Bug/Java Exception caused by Malformed JSON: " + errorMessageJSON);

        String malformedXMLPayload = "<title>ent, sunt in culpa q</title>";
        System.out.println("Test: POST /todos/:id/categories - Invalid Operation: Malformed XML");
        String errorMessage =
            given().
                contentType("application/xml").
                body(malformedXMLPayload).
            when().
                post("/todos/{id}/categories", todoId).
            then().
                statusCode(400).
            extract().
                jsonPath().getString("errorMessages");
        System.out.println("   Known Bug/Java Exception caused by Malformed XML: " + errorMessage);

        String validXMLPayload = "<category>\n" +
            "  <title>test title</title>\n" +
            "</category>";
        System.out.println("Test: POST /todos/:id - Valid Operation");
        given().
            contentType("application/xml").
            body(validXMLPayload).
        when().
            post("/todos/{id}/categories", todoId).
        then().
            statusCode(201).
            body("title", equalTo("test title"));
    }

    @Test
    public void testDeleteTodosTasksof() {
        String todoId = "2";
        String categoryId = "1";
        //delete the instance of the relationship named categories between todo
        System.out.println("Test: DELETE /todos/:id/categories/:id - Invalid Operation: delete an item that does not exist");
        when().
            delete("/todos/{todoId}/categories/{categoryId}", todoId, categoryId).
        then().
            statusCode(404);
    }

}
