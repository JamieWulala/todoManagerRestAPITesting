import io.restassured.RestAssured;
import org.junit.*;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.lang.*;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static org.hamcrest.Matchers.equalTo;

public class TodosTests extends ApiTest{

    @Test
    public void testGetTodos() {
        //get todos list
        when().
                get("/todos").
        then().
                statusCode(200);
        //get a todo item
        when().
                get("/todos/{id}", 1).
        then().
                statusCode(200).
                body("todos.get(0).title", equalTo("scan paperwork")).
                body("todos.get(0).id", equalTo("1"));
    }

    @Test
    public void testPostTodos() {
        //request body
        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("title", "someTitle");

        //post a todo item
        given().
                contentType("application/json").
                body(requestBody).
        when().
                post("/todos").
        then().
                statusCode(201).
                body("title", equalTo("someTitle"));
    }
}
