
package UserTest;

import Models.UserModels.CreateUserRequestModel;
import Models.UserModels.CreateUserResponseModel;
import Models.UserModels.GetSingleResponseUserModel;
import Models.UserModels.GetUsersResponseModels;
import Requests.UserRequest;
import Utilities.constants;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static Requests.UserRequest.CreateUser;

public class GetUserTests {

    String id="";
    String name="";
    String status="";
    String gender="";

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
        name=createUserResponseModel.getName();
        status=createUserResponseModel.getStatus();
        gender=createUserResponseModel.getGender();
    }
    @Test(priority = 1)
    public void getSingleUserUsingId() {
        GetSingleResponseUserModel GetSingleResponseUserModel = UserRequest.getSingleUserUsingId(id);
        Assert.assertEquals(GetSingleResponseUserModel.getId(), id, "Id is not as expected");
        Assert.assertEquals(GetSingleResponseUserModel.getName(), createUserRequestModel.getName(), "name is not as expected");
    }
    @Test(priority = 2)
    public void getSingleUserUsingName() {
        GetUsersResponseModels[] getUsersResponseModels = UserRequest.getUserUsingName(name);
        boolean nameExists = false;
        for (GetUsersResponseModels user : getUsersResponseModels) {
            if (user.getName().equals(name)) {
                nameExists = true;
                break;
            }
        }
        Assert.assertTrue(nameExists, "The name is not as expected");
    }
    @Test(priority = 3)
    public void getSingleUserUsingStatus() {
        GetUsersResponseModels[] getUsersResponseModels = UserRequest.getUserUsingStatus(status);
        boolean statusExists = false;
        for (GetUsersResponseModels UserStatus : getUsersResponseModels) {
            if (UserStatus.getStatus().equals(status)) {
                statusExists = true;
                break;
            }
        }
        Assert.assertTrue(statusExists, "The status is not as expected");
    }
    @Test(priority = 4)
    public void getSingleUserUsingGender() {
        GetUsersResponseModels[] getUsersResponseModels = UserRequest.getUserUsingGender(gender);
        boolean genderExists = false;
        for (GetUsersResponseModels UserGender : getUsersResponseModels) {
            if (UserGender.getGender().equals(gender)) {
                genderExists = true;
                break;
            }
        }
        Assert.assertTrue(genderExists, "The Gender is not as expected");
    }

    @AfterClass
    public void teardown(){
        UserRequest.deleteUserUsingId(id);
    }
    }
