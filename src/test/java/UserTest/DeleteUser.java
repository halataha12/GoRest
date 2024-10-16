
package UserTest;

import Models.UserModels.InvalidResponseMessageModel;
import Models.UserModels.CreateUserRequestModel;
import Models.UserModels.CreateUserResponseModel;
import Requests.UserRequest;
import Utilities.constants;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static Requests.UserRequest.CreateUser;

public class DeleteUser {
    static String id="";
    CreateUserRequestModel createUserRequestModel =new CreateUserRequestModel();
    CreateUserResponseModel createUserResponseModel =new CreateUserResponseModel();
    @BeforeClass
    public void Precondition() throws JsonProcessingException {
        createUserRequestModel.setName(constants.username);
        createUserRequestModel.setEmail(constants.userEmail);
        createUserRequestModel.setGender(constants.userGender);
        createUserRequestModel.setStatus(constants.userStatus);
        createUserResponseModel=CreateUser(createUserRequestModel,201);
        id=createUserResponseModel.getId();
    }

   @Test(priority =1)
    public void deleteUser(){
        Response response=UserRequest.deleteUserUsingId(id);
        response.prettyPrint();
        response.then().statusCode(204);
        Assert.assertTrue(response.body().asString().isEmpty(), "Response body is not empty");
    }
    @Test(priority =2)
    public void delete_user_with_invalid_ID(){
        InvalidResponseMessageModel deleteUsingInvalidIDModel=UserRequest.deleteUserUsingInvalidId(id);
        Assert.assertEquals(deleteUsingInvalidIDModel.message,"Resource not found");
    }
    @AfterClass
    public void teardown(){
        UserRequest.deleteUserUsingId(id);
    }
}