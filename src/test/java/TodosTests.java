import io.restassured.RestAssured;
import org.junit.BeforeClass;
import org.junit.Test;

import static io.restassured.RestAssured.when;

public class TodosTests {

    @BeforeClass
    public static void setup() {
        //Set base URI
        RestAssured.baseURI = "http://localhost:4567";
    }

    @Test
    public void
    testGetToDos() {

        when().
                get("/todos").
                then()
                .statusCode(200);
    }
}
