package com.library.steps;

import com.library.pages.BookPage;
import com.library.pages.LoginPage;
import com.library.utility.ConfigurationReader;
import com.library.utility.LibraryAPI_Util;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.internal.common.assertion.Assertion;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import junit.framework.TestCase;
import org.apache.velocity.runtime.resource.util.StringResourceRepositoryImpl;
import org.asynchttpclient.util.Assertions;
import org.hamcrest.Matcher;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Random;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;



public class ApiStepDefs {


    String token;
    Response response;
    String globalPathParamValue;
    private RequestSpecification requestSpec;
    Map<String,Object> randomBookFromApi;
    Map<String,Object> randomUserFromApi;
    LoginPage loginPage=new LoginPage();
    BookPage bookPage=new BookPage();

    //US01
    @Given("I logged Library api as a {string}")
    public void i_logged_library_api_as_a(String role) {
        token= LibraryAPI_Util.getToken(role);
    }
    @Given("Accept header is {string}")
    public void accept_header_is(String acceptHeader) {

      requestSpec=  given()
              .baseUri("https://library2.cydeo.com/rest/v1")
              .accept(acceptHeader)
              .header("x-library-token",token);
    }
    @When("I send GET request to {string} endpoint")
    public void i_send_get_request_to_endpoint(String endpoint) {

      response =given().spec(requestSpec)
              .when().get(endpoint);
    }

    @Then("status code should be {int}")
    public void status_code_should_be(int expectedStatusCode) {

        Assert.assertEquals(expectedStatusCode,response.statusCode());
    }
    @Then("Response Content type is {string}")
    public void response_content_type_is(String expectedContentType) {
        Assert.assertEquals(expectedContentType,response.contentType());
        System.out.println("expectedContentType = " + expectedContentType);
    }
    @Then("{string} field should not be null")
    public void field_should_not_be_null(String field) {
        response
                .then()
                .body(field,notNullValue());
    }



        //US02
        @Given("Path param {string} is {string}")
        public void path_param_is(String pathParam,String pathParamValue) {

            globalPathParamValue=pathParamValue;
            requestSpec = requestSpec.pathParam(pathParam,Integer.parseInt(pathParamValue));
        }

        @Then("{string} field should be same with path param")
        public void field_should_be_same_with_path_param(String BodyPathParam) {
       // Assert.assertEquals(BodyPathParam,globalPathParamValue);

            response.then().body(BodyPathParam,is(globalPathParamValue));
        }

        @Then("following fields should not be null")
        public void following_fields_should_not_be_null(List<String> fields) {

            for (String eachFIELD : fields) {
                requestSpec.then().body(eachFIELD,is(notNullValue()));
            }
        }

//US03
   @Given("Request Content Type header is {string}")
   public void request_content_type_header_is(String contentType) {

        requestSpec=requestSpec.contentType(contentType);

   }
    @Given("I create a random {string} as request body")
    public void i_create_a_random_as_request_body(String itemTypeReqBody) {

        switch (itemTypeReqBody){
            case "book":
                randomBookFromApi = LibraryAPI_Util.getRandomBookMap();
                requestSpec = requestSpec.formParams(randomBookFromApi);
                break;
            case "user":
                randomUserFromApi = LibraryAPI_Util.getRandomUserMap();
                requestSpec = requestSpec.formParams(randomUserFromApi);
                break;
            default:
                throw new IllegalArgumentException("Given contentItemType: "
                        + itemTypeReqBody
                        + ". But must be \"book\" or \"user\"");
        }


    }
    @When("I send POST request to {string} endpoint")
    public void i_send_post_request_to_endpoint(String postRequest) {
      //  requestSpec.when().post(postRequest);
        response = RestAssured
                .given()
                .spec(requestSpec)
                .when()
                .post(postRequest);
    }
    @Then("the field value for {string} path should be equal to {string}")
    public void the_field_value_for_path_should_be_equal_to(String message, String textMessage) {
       response.then().body(message,is(textMessage));

      //  System.out.println("message = " + message);
      // System.out.println("textMessage = " + textMessage);
    }


    @Given("I logged in Library UI as {string}")
    public void i_logged_in_library_ui_as(String user) {
        loginPage.login(user);
    }
    @Given("I navigate to {string} page")
    public void i_navigate_to_page(String pageToNavigate) {
        bookPage.navigateModule(pageToNavigate);
    }

    ///////////////Look at answers/////////////////////////
    @Then("UI, Database and API created book information must match")
    public void ui_database_and_api_created_book_information_must_match() {

        /*
        //UI
        bookPage.addBook.click();
        bookPage.bookName.sendKeys("Feuer unter dem Eis");
        bookPage.author.sendKeys("Joseph Wellighman");
        bookPage.isbn.sendKeys("45612");
        bookPage.year.sendKeys("1985");
       // bookPage.categoryDropdown.sendKeys("Classic");
        bookPage.saveChanges.click();
         */
    }

    ////US04
    @Then("created user information should match with Database")
    public void created_user_information_should_match_with_database() {

    }
    @Then("created user should be able to login Library UI")
    public void created_user_should_be_able_to_login_library_ui() {

    }
    @Then("created user name should appear in Dashboard Page")
    public void created_user_name_should_appear_in_dashboard_page() {

    }

//US05

    @Given("I logged Library api with credentials {string} and {string}")
    public void i_logged_library_api_with_credentials_and(String string, String string2) {

    }
    @Given("I send token information as request body")
    public void i_send_token_information_as_request_body() {

    }




}

