package com.library.steps;

import com.library.utility.LibraryAPI_Util;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.junit.Assert;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.notNullValue;


public class ApiStepDefsADAM {

    static RequestSpecification requestSpecification;//global requestSpecification
    String token;
    Response response;

    @Given("I logged Library api as a {string}")
    public void i_logged_library_api_as_a(String userType) {
     token= LibraryAPI_Util.getToken(userType);
    }
    @Given("Accept header is {string}")
    public void accept_header_is(String acceptType) {
        requestSpecification = given()
                .accept(acceptType);
    }
    @When("I send GET request to {string} endpoint")
    public void i_send_get_request_to_endpoint(String endpoint) {

        response = requestSpecification
                .header("x-library-token", token)
                .get(endpoint);

    }

    @Then("status code should be {int}")
    public void status_code_should_be(int expectedStatusCode) {

        response.then().statusCode(expectedStatusCode);

    }
    @Then("Response Content type is {string}")
    public void response_content_type_is(String expectedContentType) {

    }
    @Then("{string} field should not be null")
    public void field_should_not_be_null(String field) {

    }
}
