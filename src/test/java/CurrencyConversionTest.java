import io.restassured.response.Response;

import org.junit.jupiter.api.Test;

import java.util.List;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class CurrencyConversionTest {

    private static Response response;

    @Test
    public void getConversionRateTest() throws InterruptedException {
        response = given().get(Consts.URL + Consts.LIVE_ENDPOINT + Consts.TOKEN);
        response.then().statusCode(200);
        response.then().body("source", equalTo("USD"));
        System.out.println(response.asString());
        Thread.sleep(3000);
    }
    @Test
    public void getCurrencyConversionRateTest() throws InterruptedException {
        response = given().get(Consts.URL + Consts.LIVE_ENDPOINT + Consts.TOKEN + "&currencies=CAD,EUR,RUB");
        response.then().statusCode(200);
        response.then().body("source", equalTo("USD"));
        System.out.println(response.asString());
        Thread.sleep(3000);
    }


    @Test
    public void validateJSONResponse(){
        response = given().get(Consts.URL+Consts.LIVE_ENDPOINT+Consts.TOKEN);
        response.then().statusCode(200);
        response.then().body("source", equalTo("USD"));
        response.then().body("success", equalTo(true));

    }
}



