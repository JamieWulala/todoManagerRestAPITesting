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


public class TodoTasksofTests extends ApiTest{

    @Test
    public void testGetTodosTasksof() {
        String projectId = "2";
        String todoTitle = "Office Work";
        //get all the project items related to todo
        System.out.println("Test: GET /todos/:id/tasksof - Valid Operation Line Queries");
        System.out.println("   Undocumentated Capibility: Line Queries for GET /todos/:id/tasksof");
        when().
            get("/todos/{id}/tasksof?title={title}", projectId, todoTitle).
        then().
            statusCode(200).
            body("projects[0].title", equalTo(todoTitle));
    }

    @Test
    public void testHeadTodosTasksof() {
        String projectId = "2";
        //headers for the project items related to todo
        System.out.println("Test: HEAD /todos/:id/tasksof - Valid Operation");
        when().
            head("/todos/{id}", projectId).
        then().
            statusCode(200);
    }

    @Test
    public void testPostTodosTasksof() {
        String projectId = "2";
        //create an instance of a relationship named tasksof between todo
        String malformedJSONPayload = "{\"title\": \"test title\"";
        System.out.println("Test: POST /todos/:id/tasksof - Invalid Operation: Malformed JSON");
        String errorMessage =
            given().
                contentType("application/json").
                body(malformedJSONPayload).
            when().
                post("/todos/{id}/tasksof", projectId).
            then().
                statusCode(400).
            extract().
                jsonPath().getString("errorMessages");
        System.out.println("   Known Bug/Java Exception caused by Malformed JSON: " + errorMessage);
    }

    @Test
    public void testDeleteTodosTasksof() {
        String projectId = "1";
        String todoId = "1";
        //delete the instance of the relationship named tasksof between todo and project
        System.out.println("Test: DELETE /todos/:id/tasksof/:id - Valid Operation");
        when().
            delete("/todos/{projectId}/tasksof/{todoId}", projectId, todoId).
        then().
            statusCode(200);
    }

}
