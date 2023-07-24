package in.reqres.tests;

import io.restassured.module.jsv.JsonSchemaValidator;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static org.hamcrest.Matchers.hasKey;
import static org.hamcrest.Matchers.is;

import java.io.File;
import java.io.InputStream;

public class UserTests extends BaseTest{

    @Test
    void successfulCreateUserTest() {
        given()
                .log().uri()
                .log().method()
                .log().body()
                .contentType(JSON)
                .body(testData.user_create_data)
                .when()
                .post("/users")
                .then()
                .log().status()
                .log().body()
                .statusCode(201)
                .body("name", is(testData.user_create_data.get("name")))
                .body("job", is(testData.user_create_data.get("job")))
                .body("$", hasKey("id"))
                .body("$", hasKey("createdAt"));

    }

    @Test
    void successfulGetUserInfoTest() {
        given()
                .log().uri()
                .log().method()
                .log().body()
                .when()
                .get("/users/" + testData.getUserId())
                .then()
                .log().status()
                .log().body()
                .statusCode(200)
                .body("data.id", is(Integer.parseInt(testData.user_get_data.get("id"))))
                .body("data.email", is(testData.user_get_data.get("email")))
                .body("data.first_name", is(testData.user_get_data.get("first_name")))
                .body("data.last_name", is(testData.user_get_data.get("last_name")))
                .body("data.avatar", is(testData.user_get_data.get("avatar")));
    }

    @Test
    void userNotFoundTest() {
        given()
                .log().uri()
                .log().method()
                .log().body()
                .when()
                .get("/users/" + testData.getNonExistedUserId())
                .then()
                .log().status()
                .log().body()
                .statusCode(404);
    }

    @Test
    void validateJSONSchema() {
        InputStream createUserJsonSchema = getClass().getClassLoader()
                .getResourceAsStream ("schemas/schema.json");
        given()
                .log().uri()
                .log().method()
                .log().body()
                .when()
                .get("/users/" + testData.getUserId())
                .then()
                .log().status()
                .log().body()
                .body(JsonSchemaValidator.
                        matchesJsonSchema(new File("src/test/resources/schema.json")));
    }


    @Test
    void successfulUserListOnPageTest() {
        given()
                .log().uri()
                .log().method()
                .log().body()
                .contentType(JSON)
                .when()
                .get("/users" + testData.getPage())
                .then()
                .log().status()
                .log().body()
                .statusCode(200)
                .body("page", is(Integer.parseInt(testData.user_list_data.get("page"))))
                .body("per_page", is(Integer.parseInt(testData.user_list_data.get("per_page"))))
                .body("total", is(Integer.parseInt(testData.user_list_data.get("total"))))
                .body("total_pages", is(Integer.parseInt(testData.user_list_data.get("total_pages"))))
                .body("data.size()", is(Integer.parseInt(testData.user_list_data.get("per_page"))));

    }
}
