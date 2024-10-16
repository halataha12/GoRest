package Requests;
import Models.UserModels.InvalidResponseMessageModel;
import Models.UserModels.*;
import Utilities.constants;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;


import static io.restassured.RestAssured.given;

public class UserRequest {

    public static CreateUserResponseModel CreateUser(CreateUserRequestModel createUserRequestModel, int statusCode) throws JsonProcessingException {
        return given().log().all()
                .headers(constants.generalHeaders())
                .contentType(ContentType.JSON)
                .body(createUserRequestModel)
                .post(constants.base_url + constants.userEndPoint)
                .then().statusCode(statusCode)
                .extract().as(CreateUserResponseModel.class);
    }

    public static CreateUserWithInvalidFieldsResponseBodyModel[] createUserWithInvalidFields(CreateUserRequestModel createUserRequestModel, int statusCode) {
        return given().log().all()
                .contentType(ContentType.JSON)
                .headers(constants.generalHeaders())
                .body(createUserRequestModel)
                .post(constants.base_url + constants.userEndPoint)
                .then().statusCode(statusCode)
                .extract().as(CreateUserWithInvalidFieldsResponseBodyModel[].class);
    }

    public static InvalidResponseMessageModel CreateUserWithOutAuth(CreateUserRequestModel createUserRequestModel, int statuscode) {
        return given().log().all()
                .contentType(ContentType.JSON)
                .body(createUserRequestModel)
                .post(constants.base_url + constants.userEndPoint)
                .then().statusCode(statuscode)
                .extract().as(InvalidResponseMessageModel.class);
    }

    public static GetSingleResponseUserModel getSingleUserUsingId(String id) {
        return RestAssured.given().log().all()
                .headers(constants.generalHeaders())
                .get(constants.base_url + constants.userEndPoint + id)
                .then().statusCode(200).extract().as(GetSingleResponseUserModel.class);
    }

    public static GetUsersResponseModels[] getUserUsingName(String name) {
        return RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .headers(constants.generalHeaders())
                .queryParam("name", name)
                .get(constants.base_url + constants.userEndPoint).as(GetUsersResponseModels[].class);}
    public static GetUsersResponseModels[] getUserUsingStatus(String status) {
        return RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .headers(constants.generalHeaders())
                .queryParam("status", status)
                .get(constants.base_url + constants.userEndPoint)
                .as(GetUsersResponseModels[].class);}
    public static GetUsersResponseModels[] getUserUsingGender(String gender) {
        return RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .headers(constants.generalHeaders())
                .queryParam("gender", gender)
                .get(constants.base_url + constants.userEndPoint)
                .as(GetUsersResponseModels[].class);
    }
    public static Response deleteUserUsingId(String id){
        return  RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .headers(constants.generalHeaders())
                .delete(constants.base_url+ constants.userEndPoint+id);

    }
    public static InvalidResponseMessageModel deleteUserUsingInvalidId(String id){
        return  RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .headers(constants.generalHeaders())
                .delete(constants.base_url+ constants.userEndPoint+id)
                .then().statusCode(404)
                .extract()
                .as(InvalidResponseMessageModel.class);
    }
    public static InvalidResponseMessageModel deleteUserWithOutAuth(String id){
        return  RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .delete(constants.base_url+ constants.userEndPoint+id)
                .then().statusCode(401)
                .extract()
                .as(InvalidResponseMessageModel.class);

    }
    public static GetSingleResponseUserModel updateUserUsingId(CreateUserRequestModel updatedUser,String id){
        return  RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .headers(constants.generalHeaders())
                .body(updatedUser)
                .put(constants.base_url+ constants.userEndPoint+id)
                .then().statusCode(200)
                .extract().as(GetSingleResponseUserModel.class);

    }
    public static CreateUserWithInvalidFieldsResponseBodyModel[] updateUserUsingInvalidBody(CreateUserRequestModel updatedUser,String id){
        return  RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .headers(constants.generalHeaders())
                .body(updatedUser)
                .put(constants.base_url+ constants.userEndPoint+id)
                .then().statusCode(422)
                .extract().as(CreateUserWithInvalidFieldsResponseBodyModel[].class);

    }
    public static InvalidResponseMessageModel updateUserUsingInvalidId(CreateUserRequestModel updatedUser,String id){
        return  RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .headers(constants.generalHeaders())
                .body(updatedUser)
                .put(constants.base_url+ constants.userEndPoint+id)
                .then().statusCode(404)
                .extract().as(InvalidResponseMessageModel.class);
    }

}

