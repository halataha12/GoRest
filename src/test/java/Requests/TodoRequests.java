package Requests;
import Models.PostModels.InvalidResponseMessage;
import Models.TodoModels.CreateTodoRequestModel;
import Models.TodoModels.GetTodoResponseModel;
import Utilities.constants;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;

public class TodoRequests {
    public static GetTodoResponseModel[] GetAllTodos(Integer statusCode){
        return RestAssured.given().log().all()
                .headers(constants.generalHeaders())
                .contentType(ContentType.JSON)
                .get(constants.base_url + constants.TodoEndPoint)
                .then().statusCode(statusCode).extract()
                .as(GetTodoResponseModel[].class);
    }


    public static GetTodoResponseModel createTodo(CreateTodoRequestModel createTodoRequestModel,String Id,Integer StatusCode){
        return RestAssured.given().log().all()
                .headers(constants.generalHeaders())
                .contentType(ContentType.JSON)
                .body(createTodoRequestModel)
                .post(constants.base_url + constants.userEndPoint+Id+constants.TodoEndPoint)
                .then().statusCode(StatusCode)
                .extract().as(GetTodoResponseModel.class);
    }

    public static InvalidResponseMessage[] InvalidCreateTodo(CreateTodoRequestModel createTodoRequestModel, String Id, Integer StatusCode){
        return RestAssured.given().log().all()
                .headers(constants.generalHeaders())
                .contentType(ContentType.JSON)
                .body(createTodoRequestModel)
                .post(constants.base_url + constants.userEndPoint+Id+constants.TodoEndPoint)
                .then().statusCode(StatusCode)
                .extract().as(InvalidResponseMessage[].class);
    }
    public static GetTodoResponseModel GetSingleTodo(String id,Integer StatusCode){
        return RestAssured.given().log().all()
                .headers(constants.generalHeaders())
                .contentType(ContentType.JSON)
                .get(constants.base_url +constants.TodoEndPoint+id)
                .then().statusCode(StatusCode)
                .extract().as(GetTodoResponseModel.class);
    }





}
