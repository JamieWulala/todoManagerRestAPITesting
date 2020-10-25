import io.restassured.RestAssured;
import org.junit.After;
import org.junit.Before;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class ApiTest {
    protected Process proc;

    @Before
    public void setup() {
        //Set base URI
        RestAssured.baseURI = "http://localhost:4567";
        //start the application
        try {
            //System.out.println("Starting application......");
            proc = Runtime.getRuntime().exec("java -jar runTodoManagerRestAPI-1.5.5.jar");
            //System.out.println("------Application started------");
            InputStream in = proc.getInputStream();
            InputStream err = proc.getErrorStream();
            Thread.sleep(1000);
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }

    @After
    public void cleanUp() {
        //stop the application
        try {
            while(proc.isAlive()) {
                proc.destroy();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
