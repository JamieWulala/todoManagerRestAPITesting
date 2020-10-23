import io.restassured.RestAssured;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;

public class ProjectsTasksTests extends ApiTest{
    @Test
    public void testGetProjectTasks() {
        //get all the tasks of a project
        String projectId = "1";
        System.out.println("Test: GET /projects/:id/tasks - Valid Operation");
        when().
                get("/projects/{id}/tasks", projectId).
        then().
                statusCode(200);
    }

    @Test
    public void testPostProjectTask() {

        String projectId = "1";
        String taskTitle = "Some task";
        Map<String, String> requestBodyTitle = new HashMap<>();
        requestBodyTitle.put("title", taskTitle);
        Map<String, String> requestBodyEmpty = new HashMap<>();

//-------------------------------------------------------------------------------------------------------------
        //post a new task with title
        System.out.println("Test: POST /projects/:id/tasks - Valid Operation: task with title ");
        given().
                contentType("application/json").
                body(requestBodyTitle).
        when().
                post("/projects/{id}/tasks", projectId).
        then().
                statusCode(201);
//-------------------------------------------------------------------------------------------------------------
        //post new task with EMPTY body
        System.out.println("Test: POST /projects/:id/tasks - invalid Operation: task with empty body ");
        String emptyBodyError =
        given().
                contentType("application/json").
                body(requestBodyEmpty).
        when().
                post("/projects/{id}/tasks", projectId).
        then().
                statusCode(400).
                 extract().
                jsonPath().getString("errorMessages");
        System.out.println("Unexpected error message when posting with all params: " + emptyBodyError);
    }


    @Test
    public void testDeleteProjectTask() {
        String projectId = "1";

        String taskID = "1";
        String taskID_invalid = "77";

//-------------------------------------------------------------------------------------------------------------
//        //delete an existing task
//        System.out.println("Test: DELETE /projects/:id/tasks/:id - valid operation: valid task id");
//
//        when().
//                delete("/projects/{id}/tasks/{task_id}", projectId, taskID).
//        then().
//                statusCode(200);
//-------------------------------------------------------------------------------------------------------------
        //delete non-existing task
        System.out.println("Test: DELETE /projects/:id/taskss/:id - invalid operation: invalid task id");

        when().
                delete("/projects/{id}/tasks/{task_id}", projectId, taskID_invalid).
        then().
                statusCode(404);
    }


}
