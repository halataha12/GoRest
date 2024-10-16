package Requests;
import Models.PostModels.CreatePostRequestModel;
import Models.PostModels.CreatePostResponseModel;
import Models.PostModels.GetPostResponseModel;
import Models.PostModels.InvalidResponseMessage;
import Models.UserModels.InvalidResponseMessageModel;
import Utilities.constants;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class PostRequest {
    public static GetPostResponseModel[] GetAllPosts(){
        return RestAssured.given().log().all()
                .headers(constants.generalHeaders())
                .contentType(ContentType.JSON)
                .get(constants.base_url + constants.PostEndPoint).as(GetPostResponseModel[].class);
    }

    public static GetPostResponseModel getSinglePost(String id){
        return  RestAssured.given().log().all()
                .headers(constants.generalHeaders())
                .contentType(ContentType.JSON)
                .get(constants.base_url+ constants.PostEndPoint+id)
                .then().statusCode(200).extract().as(GetPostResponseModel.class);
    }
    public static  GetPostResponseModel[] getPostsOfSpecificUser(String id,Integer statusCode){
       return RestAssured.given().log().all()
                .headers(constants.generalHeaders())
                .contentType(ContentType.JSON)
                .get(constants.base_url+constants.userEndPoint+id+ constants.PostEndPoint)
                .then().statusCode(statusCode).extract().as(GetPostResponseModel[].class);
    }
    public static GetPostResponseModel[]  getPostUsingTitle(String title,int StatusCode){
        return  RestAssured.given().log().all()
                .headers(constants.generalHeaders())
                .contentType(ContentType.JSON)
                .queryParam("title", title)
                .get(constants.base_url+ constants.PostEndPoint).then().statusCode(StatusCode).extract().as(GetPostResponseModel[].class);
    }
    public static CreatePostResponseModel CreatePosts(CreatePostRequestModel createPostRequestModel, String id,int statusCode) {
        return RestAssured.given().log().all()
                .headers(constants.generalHeaders())
                .contentType(ContentType.JSON)
                .body(createPostRequestModel)
                .post(constants.base_url + constants.userEndPoint+id+constants.PostEndPoint)
                .then().statusCode(statusCode)
                .extract().as(CreatePostResponseModel.class);
    }
    public static InvalidResponseMessage[] InvalidCreatePosts(CreatePostRequestModel createPostRequestModel, String id, int statusCode) {
        return RestAssured.given().log().all()
                .headers(constants.generalHeaders())
                .contentType(ContentType.JSON)
                .body(createPostRequestModel)
                .post(constants.base_url + constants.userEndPoint+id+constants.PostEndPoint)
                .then().statusCode(statusCode)
                .extract().as(InvalidResponseMessage[].class);
    }
    public static Response deletePostWithValidId(String id){
        return  RestAssured.given().log().all()
                .headers(constants.generalHeaders())
                .contentType(ContentType.JSON)
                .delete(constants.base_url+ constants.PostEndPoint+id);
    }
    public static InvalidResponseMessageModel deletePostWithInvalidId(String id, int StatusCode){
        return   deletePostWithValidId(id)
                .then().statusCode(StatusCode)
                .extract().as(InvalidResponseMessageModel.class);
    }




    public static GetPostResponseModel UpdatePost(CreatePostRequestModel updatePost,String id,Integer StatusCode)  {
        return RestAssured.given().log().all()
                .headers(constants.generalHeaders())
                .contentType(ContentType.JSON)
                .body(updatePost)
                .patch(constants.base_url + constants.PostEndPoint+ id)
                .then().statusCode(StatusCode)
                .extract().as(GetPostResponseModel.class);
    }
    public static InvalidResponseMessage[] InvalidUpdatePost(CreatePostRequestModel updatePost,String id,Integer StatusCode) throws JsonProcessingException {
        return RestAssured.given().log().all()
                .headers(constants.generalHeaders())
                .contentType(ContentType.JSON)
                .body(updatePost)
                .patch(constants.base_url + constants.PostEndPoint+ id)
                .then().statusCode(StatusCode)
                .extract().as(InvalidResponseMessage[].class);
    }
   public static InvalidResponseMessage InvalidIDUpdatePost(CreatePostRequestModel updatePost,String id,Integer StatusCode)  {
        return RestAssured.given().log().all()
                .headers(constants.generalHeaders())
                .contentType(ContentType.JSON)
                .body(updatePost)
                .patch(constants.base_url + constants.PostEndPoint+ id)
                .then().statusCode(StatusCode)
                .extract().as(InvalidResponseMessage.class);
    }
}
