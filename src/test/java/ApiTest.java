import io.restassured.RestAssured;
import org.junit.After;
import org.junit.Before;

import java.io.InputStream;

public class ApiTest {
    protected static Process proc;

    @Before
    public void setup() {
        //Set base URI
        RestAssured.baseURI = "http://localhost:4567";
        System.out.println("------Set Up------");
        //start the application
        try {
            //System.out.println("Starting application......");
            proc = Runtime.getRuntime().exec("java -jar runTodoManagerRestAPI-1.5.5.jar");
            InputStream in = proc.getInputStream();
            InputStream err = proc.getErrorStream();
        } catch (Exception e) {
            System.out.println(e.toString());
            e.printStackTrace();
        }
    }

    @After
    public void cleanUp() {
        //stop the application
        System.out.println("------Clean Up------");
        //System.out.println("Application Stopped");
        proc.destroy();
    }
}
