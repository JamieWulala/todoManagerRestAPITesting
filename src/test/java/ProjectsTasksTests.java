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

    private static String ProjectId;
    private static String TaskId;

    @Before
    public void setUp() {
        //Post a project and a task
        Map<String, String> requestBodyProj = new HashMap<>();
        requestBodyProj.put("title", "New ProjectTitle");
        requestBodyProj.put("description", "New Description");

        Map<String, String> requestBodyTask = new HashMap<>();
        requestBodyTask.put("title", "New Task Title");

        ProjectId = given().
                contentType("application/json")
                .body(requestBodyProj).
                when().
                post("/projects").
                then().
                statusCode(201)
                .extract()
                .jsonPath().getString("id");


        TaskId = given().
                contentType("application/json")
                .body(requestBodyTask).
                when().
                post("/todos").
                then().
                statusCode(201)
                .extract()
                .jsonPath().getString("id");


        //Create a valid project-task relationship
        Map<String, String> requestBodyID = new HashMap<>();
        requestBodyID.put("id", TaskId);
        given().
                contentType("application/json").
                body(requestBodyID).
        when().
                post("/projects/{id}/tasks", ProjectId).
        then().
                statusCode(201);



    }


    @Test
    public void testGetProjectTasks() {
        //get all the tasks of a project
        System.out.println("Test: GET /projects/:id/tasks - Valid Operation");
        when().
                get("/projects/{id}/tasks", ProjectId).
        then().
                statusCode(200);
    }

    @Test
    public void testHeadProjectTasks() {
        System.out.println("Test: HEAD /projects/:id/tasks - Valid Operation");
        when().
                head("/projects/{id}/tasks", ProjectId).
        then().
                statusCode(200);
    }

    @Test
    public void testPostProjectTask() {

        Map<String, String> requestBodyId = new HashMap<>();
        requestBodyId.put("id", TaskId);

        Map<String, String> requestBodyEmpty = new HashMap<>();


        //post a new task relationship with an existing task
        System.out.println("Test: POST /projects/:id/tasks - Valid Operation: task with title ");
        given().
                contentType("application/json").
                body(requestBodyId).
        when().
                post("/projects/{id}/tasks", ProjectId).
        then().
                statusCode(201);


        //post new task with EMPTY body
        System.out.println("Test: POST /projects/:id/tasks - invalid Operation: task with empty body ");
        String emptyBodyError =
        given().
                contentType("application/json").
                body(requestBodyEmpty).
        when().
                post("/projects/{id}/tasks", ProjectId).
        then().
                statusCode(400).
                 extract().
                jsonPath().getString("errorMessages");
        System.out.println("Error message when posting task relationship with empty body: " + emptyBodyError);
    }


    @Test
    public void testDeleteProjectTask() {
        String taskID_invalid = "77";

        //delete an existing task relationship
        System.out.println("Test: DELETE /projects/:id/tasks/:id - valid operation: valid task id");

        when().
                delete("/projects/{id}/tasks/{task_id}", ProjectId, TaskId).
        then().
                statusCode(200);

        //delete non-existing task relationship
        System.out.println("Test: DELETE /projects/:id/taskss/:id - invalid operation: invalid task id");

        when().
                delete("/projects/{id}/tasks/{task_id}", ProjectId, taskID_invalid).
        then().
                statusCode(404);
    }


}
