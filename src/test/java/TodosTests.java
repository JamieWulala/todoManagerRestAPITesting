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
        //get all the instances of todo
        System.out.println("Test: GET /todos - Valid Operation");
        when().
            get("/todos").
        then().
            statusCode(200);
    }

    @Test
    public void testHeadTodos() {
        //get all the instances of todo
        System.out.println("Test: HEAD /todos - Valid Operation");
        when().
            head("/todos").
        then().
            statusCode(200);
    }

    @Test
    public void testPostTodos() {
        //request body
        String todoTitle = "clean office";
        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("title", todoTitle);
        //create a todo
        System.out.println("Test: POST /todos - Valid Operation");
        given().
                contentType("application/json").
                body(requestBody).
        when().
                post("/todos").
        then().
                statusCode(201).
                body("title", equalTo(todoTitle));

        //create a todo without title
        String emptyJSONPayload = "";
        System.out.println("Test: POST /todos - Invalid Operation: No Title");
        given().
                contentType("application/json").
                body(emptyJSONPayload).
        when().
                post("/todos").
        then().
                statusCode(400);

        //create a todo with malformed JSON
        String malformedJSONPayload = "{\"title\": \"test title\"";
        System.out.println("Test: POST /todos - Invalid Operation: Malformed JSON");
        String errorMessage =
        given().
            contentType("application/json").
            body(malformedJSONPayload).
        when().
            post("/todos").
        then().
            statusCode(400).
        extract().
            jsonPath().getString("errorMessages");
        System.out.println("   Known Bug/Java Exception caused by Malformed JSON: " + errorMessage);
    }

    @Test
    public void testGetSpecificTodo() {
        String todoId = "1";
        //get a specific instances of todo using a id
        System.out.println("Test: GET /todos/:id - Valid Operation");
        when().
            get("/todos/{id}", todoId).
        then().
            statusCode(200).
            body("todos.get(0).id", equalTo(todoId));
    }

    @Test
    public void testHeadSpecificTodo() {
        String todoId = "1";
        //get head of a specific instances of todo using a id
        System.out.println("Test: HEAD /todos/:id - Valid Operation");
        when().
            get("/todos/{id}", todoId).
        then().
            statusCode(200);
    }

    @Test
    public void testPostSpecificTodo() {
        //request body
        String todoId = "1";
        String todoTitle = "clean office";
        String todoDescription = "This is a todo item for testing";
        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("title", todoTitle);
        requestBody.put("description", todoDescription);
        //amend a specific instances of todo
        System.out.println("Test: POST /todos/:id - Valid Operation");
        given().
            contentType("application/json").
            body(requestBody).
        when().
            post("/todos/{id}", todoId).
        then().
            statusCode(200).
            body("title", equalTo(todoTitle)).
            body("description", equalTo(todoDescription));
    }

    @Test
    public void testPutSpecificTodo() {
        //put a todo with malformed XML file
        String todoId = "1";
        String malformedJSONPayload = "<todo>test todo";
        System.out.println("Test: PUT /todos/:id - Invalid Operation: Malformed XML");
        String errorMessage =
            given().
                contentType("application/xml").
                body(malformedJSONPayload).
            when().
                put("/todos/{id}", todoId).
            then().
                statusCode(400).
            extract().
                jsonPath().getString("errorMessages");
        System.out.println("   Error Message caused by Malformed XML: " + errorMessage);
    }

    @Test
    public void testDeleteSpecificTodo() {
        String todoId = "2";
        //delete a specific instances of todo using a id
        System.out.println("Test: HEAD /todos/:id - Valid Operation");
        when().
            delete("/todos/{id}", todoId).
        then().
            statusCode(200);
    }
}
