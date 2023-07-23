package in.reqres.tests;

import in.reqres.utils.TestData;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;

public class BaseTest {
    TestData testData = new TestData();
    @BeforeAll
    static void beforeAll() {
        RestAssured.baseURI = "https://reqres.in/";
    }
}
