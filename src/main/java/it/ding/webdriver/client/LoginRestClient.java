package it.ding.webdriver.client;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;

public class LoginRestClient {

    private static final String URI = "http://the-internet.herokuapp.com/authenticate";

    public LoginRestClient() {

    }

    public void login(String username, String password) {
        given()
                .log()
                .all()
                .formParam("username", username)
                .formParam("password", password);

        when()
                .post(URI)
                .then()
                .statusCode(200);
    }
}
