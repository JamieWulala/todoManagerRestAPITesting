import io.restassured.RestAssured;
import org.junit.*;

import java.util.HashMap;
import java.util.Map;
import java.lang.*;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static org.hamcrest.Matchers.equalTo;

public class DocsTests extends ApiTest{

    @Test
    public void testGetDocs() {
        //get docs
        System.out.println("Test: GET /docs - Valid Operation");
        when().
                get("/docs").
                then().
                statusCode(200);
    }

}