
import io.restassured.response.Response;

import org.hamcrest.core.IsNull;
import org.junit.jupiter.api.Test;


import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;


public class HistoricalConversionTest {

    private static Response response;

    @Test
    public void getHistoricalConversionRateTest() throws InterruptedException {
        response = given().get(Consts.URL + Consts.HISTORICAL_ENDPOINT + Consts.DATE + Consts.TOKEN + "&currencies=CAD,EUR,RUB,NIS");
        response.then().statusCode(200);
        response.then().body("source", equalTo("USD"));
       response.then().body("historical", equalTo(true));
        response.then().body("timestamp", equalTo(1577923199));
        response.then().body("quotes.USDCAD", equalTo(1.29755f));
        response.then().body("quotes.USDEUR", equalTo(0.891401f));
        response.then().body("quotes.USDRUB", equalTo(61.865021f));
        System.out.println(response.asString());
        Thread.sleep(5000);
    }

    @Test
    public void getHistoricalInvalidCurrencyCodeTest() throws InterruptedException {
        response = given().get(Consts.URL + Consts.HISTORICAL_ENDPOINT + Consts.TOKEN + Consts.DATE + "&currencies=CAR");
        response.then().statusCode(200);
        response.then().body("error.info", equalTo("You have provided one or more invalid Currency Codes. [Required format: currencies=EUR,USD,GBP,...]"));
        response.then().body("error.code", equalTo(202));
        System.out.println(response.asString());
        Thread.sleep(3000);
    }
    @Test
    public void getHistoricalMissedNISCurrencyCodeTest() throws InterruptedException {
        response = given().get(Consts.URL + Consts.HISTORICAL_ENDPOINT + Consts.TOKEN + Consts.DATE + "&currencies=NIS");
        response.then().statusCode(200);
        response.then().body("NIS",notNullValue());
    }
    @Test
    public void getHistoricalNoCurrencyTest() throws InterruptedException {
        response = given().get(Consts.URL + Consts.HISTORICAL_ENDPOINT + Consts.TOKEN + Consts.DATE );
        response.then().statusCode(200);
        response.then().body("source", equalTo("USD"));
        System.out.println(response.asString());
        Thread.sleep(3000);
    }

    @Test
    public void getHistoricalNotSpecifyDateTest() throws InterruptedException {
        response = given().get(Consts.URL + Consts.HISTORICAL_ENDPOINT + Consts.TOKEN + "&currencies=CAD,EUR,RUB");
        response.then().statusCode(200);

        response.then().body("error.info", equalTo("You have not specified a date. [Required format: date=YYYY-MM-DD]"));
        response.then().body("error.code", equalTo(301));
        System.out.println(response.asString());
        Thread.sleep(3000);
    }

    @Test
    public void getHistoricalInvalidDateConversionRateTest() throws InterruptedException {
        response = given().get(Consts.URL + Consts.HISTORICAL_ENDPOINT + Consts.TOKEN + Consts.INVALID_DATE +"&currencies=CAD");
        response.then().statusCode(200);
        response.then().body("error.info", equalTo("You have entered an invalid date. [Required format: date=YYYY-MM-DD]"));
        response.then().body("error.code", equalTo(302));
        System.out.println(response.asString());
        Thread.sleep(3000);
    }
}
