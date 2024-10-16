
package UserTest;

import Models.UserModels.*;
import Requests.UserRequest;
import Utilities.constants;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.LinkedHashMap;
import java.util.Map;

import static Requests.UserRequest.CreateUser;

public class UpdateUser {
    String id = "";
    String name = "";
    String gender = "";
    String status = "";
    String email="";
    CreateUserRequestModel createUserRequestModel = new CreateUserRequestModel();
    CreateUserResponseModel createUserResponseModel = new CreateUserResponseModel();

    @BeforeClass
    public void Precondition() throws JsonProcessingException {
        createUserRequestModel.setName(constants.username);
        createUserRequestModel.setEmail(constants.userEmail);
        createUserRequestModel.setGender(constants.userGender);
        createUserRequestModel.setStatus(constants.userStatus);
        createUserResponseModel = CreateUser(createUserRequestModel, 201);
        id = createUserResponseModel.getId();
        name = createUserResponseModel.getName();
        status = createUserResponseModel.getStatus();
        gender = createUserResponseModel.getGender();
        email =createUserResponseModel.getEmail();
    }

    @Test(priority = 1)
    public void Update_with_validID() {
        CreateUserRequestModel updatedUser = new CreateUserRequestModel();
        updatedUser.setName("Hla");
        updatedUser.setEmail(constants.userEmail);
        updatedUser.setGender(constants.userGender);
        updatedUser.setStatus("inactive");
        GetSingleResponseUserModel GetResponseUserModel = UserRequest.updateUserUsingId(updatedUser, id);
        Assert.assertEquals(GetResponseUserModel.getName(), "Hla", "Name was not updated successfully");
        Assert.assertEquals(GetResponseUserModel.getStatus(), "inactive", "Status was not updated successfully");

    }

    @Test(priority = 2)
    public void Update_with_valid_ID_and_invalid_body() throws JsonProcessingException {
        CreateUserRequestModel updatedUser = new CreateUserRequestModel();
        updatedUser.setName("");
        updatedUser.setEmail("");
        updatedUser.setGender("");
        updatedUser.setStatus("");
        CreateUserWithInvalidFieldsResponseBodyModel[] response = UserRequest.updateUserUsingInvalidBody(updatedUser, id);
        Map<String, String> expectedErrors = new LinkedHashMap<>();
        expectedErrors.put("email", "can't be blank");
        expectedErrors.put("name", "can't be blank");
        expectedErrors.put("gender", "can't be blank, can be male of female");
        expectedErrors.put("status", "can't be blank");
        for (int i = 0; i < response.length; i++) {
            String field = response[i].getField();
            String message = response[i].getMessage();

            Assert.assertTrue(expectedErrors.containsKey(field), "Unexpected field: " + field);
            Assert.assertEquals(message, expectedErrors.get(field), "Message does not match for field: " + field);

        }
    }

    @Test (priority = 3)
    public void Update_with_valid_ID_and_invalid_email() throws JsonProcessingException {
        CreateUserRequestModel updatedUser = new CreateUserRequestModel();
        updatedUser.setName(constants.username);
        updatedUser.setEmail(constants.userInvalidEmail);
        updatedUser.setGender(constants.userGender);
        updatedUser.setStatus(constants.userStatus);
        CreateUserWithInvalidFieldsResponseBodyModel[] invalidUpdate = UserRequest.updateUserUsingInvalidBody(updatedUser, id);
        String field=invalidUpdate[0].getField();
        String message=invalidUpdate[0].getMessage();

        Assert.assertEquals(field, "email","Message does not match for field: " + field);
        Assert.assertEquals(message, "is invalid","Message does not match for message: " + message);
    }
    @Test (priority = 4)
    public void Update_with_valid_ID_and_already_existing_email() throws JsonProcessingException {
        CreateUserRequestModel updatedUser = new CreateUserRequestModel();
        updatedUser.setName(constants.username);
        updatedUser.setEmail(constants.emailAlreadyRegistered);
        updatedUser.setGender(constants.userGender);
        updatedUser.setStatus(constants.userStatus);
        CreateUserWithInvalidFieldsResponseBodyModel[] invalidUpdate = UserRequest.updateUserUsingInvalidBody(updatedUser, id);
        String field=invalidUpdate[0].getField();
        String message=invalidUpdate[0].getMessage();

        Assert.assertEquals(field, "email","Message does not match for field: " + field);
        Assert.assertEquals(message, "has already been taken","Message does not match for message: " + message);

        }
    @Test(priority = 5)
    public void update_with_invalid_id() {
        CreateUserRequestModel updatedUser = new CreateUserRequestModel();
        updatedUser.setName("Hla");
        updatedUser.setEmail(constants.userEmail);
        updatedUser.setGender(constants.userGender);
        updatedUser.setStatus("inactive");
        UserRequest.deleteUserUsingId(id);
        InvalidResponseMessageModel GetResponseModel = UserRequest.updateUserUsingInvalidId(updatedUser, id);
        Assert.assertEquals(GetResponseModel.message,"Resource not found");
    }

    @AfterClass
    public void teardown(){
        UserRequest.deleteUserUsingId(id);
    }
}

