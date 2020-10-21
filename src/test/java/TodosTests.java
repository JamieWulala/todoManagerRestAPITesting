import io.restassured.RestAssured;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;

public class TodosTests {

    private static Process proc;

    @BeforeClass
    public static void setup() {
        //Set base URI
        RestAssured.baseURI = "http://localhost:4567";
        System.out.println("------Set Up------");
        //start the application
        try {
            System.out.println("Starting application......");
            proc = Runtime.getRuntime().exec("java -jar runTodoManagerRestAPI-1.5.5.jar");
            InputStream in = proc.getInputStream();
            InputStream err = proc.getErrorStream();
        } catch (Exception e) {
            System.out.println(e.toString());
            e.printStackTrace();
        }
    }

    @AfterClass
    public static void cleanUp(){
        //stop the application
        System.out.println("------Clean Up------");
        System.out.println("Application Stopped");
        proc.destroy();
    }

    @Test
    public void
    testGetToDos() {

        when().
                get("/todos").
                then()
                .statusCode(200);

        when().
                get("/todos/{id}", 1).
                then()
                .statusCode(200);
    }

    @Test
    public void
    testPoseToDos() {

        Map<String, String> request = new HashMap<>();
        request.put("title", "someTitle");

        given()
                .contentType("application/json")
                .body(request).
        when()
                .post("/todos")
                .then()
                .statusCode(201);
    }
}
