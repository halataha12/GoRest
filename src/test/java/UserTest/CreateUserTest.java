package UserTest;

import Models.UserModels.InvalidResponseMessageModel;
import Models.UserModels.CreateUserRequestModel;
import Models.UserModels.CreateUserResponseModel;
import Models.UserModels.CreateUserWithInvalidFieldsResponseBodyModel;
import Requests.UserRequest;
import Utilities.constants;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import java.util.LinkedHashMap;
import java.util.Map;

import static Requests.UserRequest.*;

public class CreateUserTest {
    String id="";
    SoftAssert softAssert=new SoftAssert();
    CreateUserRequestModel createUserRequestModel =new CreateUserRequestModel();
    CreateUserResponseModel createUserResponseModel =new CreateUserResponseModel();
    InvalidResponseMessageModel invalidResponseMessageModel=new InvalidResponseMessageModel();
    CreateUserWithInvalidFieldsResponseBodyModel[] createUserWithInvalidFieldsResponseBodyModel= new CreateUserWithInvalidFieldsResponseBodyModel[]{new CreateUserWithInvalidFieldsResponseBodyModel()};
   @Test (priority = 1)
    public void createUser() throws JsonProcessingException {
       createUserRequestModel.setName(constants.username);
       createUserRequestModel.setEmail(constants.userEmail);
       createUserRequestModel.setGender(constants.userGender);
       createUserRequestModel.setStatus(constants.userStatus);
       createUserResponseModel=CreateUser(createUserRequestModel,201);
       id=createUserResponseModel.getId();
       softAssert.assertEquals(createUserResponseModel.getName(),constants.username,"Name is not as expected");
       softAssert.assertNotNull(createUserResponseModel.getId(),"id is null");
       softAssert.assertEquals(createUserResponseModel.getGender(),constants.userGender,"Gender is not as expected");
       softAssert.assertAll();


   }

   @Test(priority = 2)
    public void createUserWithExistedEmail(){
       createUserRequestModel.setName(constants.username);
       createUserRequestModel.setEmail(constants.userEmail);
       createUserRequestModel.setGender(constants.userGender);
       createUserRequestModel.setStatus(constants.userStatus);
       createUserWithInvalidFieldsResponseBodyModel =createUserWithInvalidFields(createUserRequestModel,422);
       String field=createUserWithInvalidFieldsResponseBodyModel[0].getField();
       String message =createUserWithInvalidFieldsResponseBodyModel[0].getMessage();
       softAssert.assertEquals(field, "email", "Field does not match");
       softAssert.assertEquals(message, "has already been taken", "Message does not match");
   }
   @Test(priority = 3)
    public void createUserWithEmptyData() throws JsonProcessingException {
        createUserRequestModel.setName("");
        createUserRequestModel.setEmail("");
        createUserRequestModel.setGender("");
        createUserRequestModel.setStatus("");
        CreateUserWithInvalidFieldsResponseBodyModel[] response = createUserWithInvalidFields(createUserRequestModel, 422);
        Map<String, String> expectedErrors = new LinkedHashMap<>();
        expectedErrors.put("email", "can't be blank");
        expectedErrors.put("name", "can't be blank");
        expectedErrors.put("gender", "can't be blank, can be male of female");
        expectedErrors.put("status", "can't be blank");
        for (int i = 0; i < response.length; i++) {
            String field = response[i].getField();
            String message = response[i].getMessage();
            softAssert.assertTrue(expectedErrors.containsKey(field), "Unexpected field: " + field);
            softAssert.assertEquals(message, expectedErrors.get(field), "Message does not match for field: " + field);
        }
    }

    @Test(priority = 4)
    public void createUserWithoutAuth() {
        createUserRequestModel.setName(constants.username);
        createUserRequestModel.setEmail(constants.userEmail);
        createUserRequestModel.setGender(constants.userGender);
        createUserRequestModel.setStatus(constants.userStatus);
        invalidResponseMessageModel=CreateUserWithOutAuth(createUserRequestModel,401);
        String Message=invalidResponseMessageModel.message;
        softAssert.assertEquals(Message, "Authentication failed", "Message does not match");
    }

    @AfterClass
    public void teardown(){
        UserRequest.deleteUserUsingId(id);
    }

}
