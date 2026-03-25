package io.quarkiverse.solr.it;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

@QuarkusTest
class SolrResourceTest {

    @Test
    void testHelloEndpoint() {
        given()
                .when().get("/solr")
                .then()
                .statusCode(200);
    }
}
