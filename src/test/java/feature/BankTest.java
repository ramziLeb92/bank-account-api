package feature;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.matching.RequestPattern;
import com.github.tomakehurst.wiremock.matching.StringValuePattern;
import com.intuit.karate.junit4.Karate;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;

import java.util.HashMap;
import java.util.Map;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.github.tomakehurst.wiremock.client.WireMock.equalTo;

@RunWith(Karate.class)
public class BankTest {

    private static WireMockServer wireMockServer
            = new WireMockServer();


    @BeforeClass
    public static void setUp() throws Exception {

        wireMockServer.start();
        configureFor("localhost", 8080);

        stubFor(
                post(urlEqualTo("/operations/"))
                        .withRequestBody(equalToJson("{\"accountNumber\":899, \"operationAmount\":-10000}"))
                        .willReturn(aResponse()
                                .withStatus(400)
                                 .withHeader("Content-Type", "application/json")));
        stubFor(
                get(urlEqualTo("/operations/124/status"))
                        .willReturn(aResponse()
                                .withStatus(404)
                                .withHeader("Content-Type", "application/json")));
        stubFor(
                get(urlEqualTo("/operations/123/status"))
                        .willReturn(aResponse()
                                .withStatus(200)
                                .withHeader("Content-Type", "application/json")
                                .withBody("{\"amount\":110.0}")));
        stubFor(
                post(urlEqualTo("/operations/"))
                        .withHeader("content-type", equalTo("application/json"))
                        .withRequestBody(equalToJson("{\"accountNumber\":123, \"operationAmount\":20}"))
                        .willReturn(aResponse()
                                .withStatus(201)
                                .withHeader("Content-Type", "application/json")
                                .withBody("{\"label\":\"deposit\",\"amount\":20.0,\"date\":\"2019-01-18\",\"balance\":110.0,\"accountNumber\":123}")));
        stubFor(
                post(urlEqualTo("/operations/"))
                        .withHeader("content-type", equalTo("application/json"))
                        .withRequestBody(equalToJson("{\"accountNumber\":123, \"operationAmount\":-20}"))
                        .willReturn(aResponse()
                                .withStatus(201)
                                .withHeader("Content-Type", "application/json")
                                .withBody("{\"label\":\"withdrawl\",\"amount\":-20.0,\"date\":\"2019-01-18\",\"balance\":110.0,\"accountNumber\":123}")));

        Map<String, StringValuePattern> queryParameters = new HashMap<>();
        queryParameters.put("accountNumber", equalTo("123"));
        queryParameters.put("startDate",equalTo("2018-01-01"));
        queryParameters.put("endDate", equalTo("2022-01-01"));
        stubFor(
                get(urlPathEqualTo("/operations"))
                        .withQueryParams(queryParameters)
                        .willReturn(aResponse()
                                .withStatus(200)
                                .withHeader("Content-Type", "application/json")
                                .withBody("[{\"label\":\"deposit\",\"amount\":20.0,\"date\":\"2019-01-18\",\"balance\":120.0},{\"label\":\"withdrawl\",\"amount\":-20.0,\"date\":\"2019-01-18\",\"balance\":100.0},{\"label\":\"deposit\",\"amount\":10.0,\"date\":\"2019-01-18\",\"balance\":110.0},{\"label\":\"withdrawl\",\"amount\":-10.0,\"date\":\"2019-01-18\",\"balance\":100.0}]")));
    }

    @AfterClass
    public static void tearDown() throws Exception {
      wireMockServer.stop();
    }
}
