import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;


public class SecurityTest {
    private static Response response;

    @Test
    public void getNegativeAccessTest(){
        response = given().get(Consts.URL+Consts.LIVE_ENDPOINT);
        response.then().statusCode(401);
        response.then().body("message",equalTo("No API key found in request"));
        System.out.println(response.asString());
    }

    @Test
    public void getPositiveAccessTest(){
        response = given().get(Consts.URL+Consts.LIVE_ENDPOINT+Consts.TOKEN);
        response.then().statusCode(200);
        System.out.println(response.asString());
    }
    @Test
    public void invalidTokenTest() {
        Response response = given().get(Consts.URL + Consts.LIVE_ENDPOINT+Consts.WRONG_TOKEN);
        System.out.println(response.asString());
        response.then().statusCode(401);
        response.then().body("message",equalTo("No API key found in request"));

    }

}

