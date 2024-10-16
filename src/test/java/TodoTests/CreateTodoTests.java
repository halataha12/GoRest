package TodoTests;
import Models.PostModels.InvalidResponseMessage;
import Models.TodoModels.CreateTodoRequestModel;
import Models.TodoModels.GetTodoResponseModel;
import Models.UserModels.CreateUserRequestModel;
import Models.UserModels.CreateUserResponseModel;
import Requests.TodoRequests;
import Requests.UserRequest;
import Utilities.constants;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import static Requests.UserRequest.CreateUser;

public class CreateTodoTests {
    String id = "";
    CreateUserRequestModel createUserRequestModel = new CreateUserRequestModel();
    CreateUserResponseModel createUserResponseModel = new CreateUserResponseModel();
    CreateTodoRequestModel createTodoRequestModel=new CreateTodoRequestModel();
    @BeforeClass
    public void Precondition() throws JsonProcessingException {
        createUserRequestModel.setName(constants.username);
        createUserRequestModel.setEmail(constants.userEmail);
        createUserRequestModel.setGender(constants.userGender);
        createUserRequestModel.setStatus(constants.userStatus);
        createUserResponseModel=CreateUser(createUserRequestModel,201);
        id=createUserResponseModel.getId();
    }
    @Test(priority = 1)
    public void CreateTodo(){
        createTodoRequestModel.setTitle("Check emails/messages for urgent tasks");
        createTodoRequestModel.setDueOn("2024-10-28");
        createTodoRequestModel.setStatus("pending");
        GetTodoResponseModel getTodo=TodoRequests.createTodo(createTodoRequestModel,id,201);
        Assert.assertNotNull(getTodo.getId());
        Assert.assertEquals(getTodo.getTitle(),"Check emails/messages for urgent tasks");
        Assert.assertEquals(getTodo.getDueOn(),"2024-10-28T00:00:00.000+05:30");
        Assert.assertEquals(getTodo.getStatus(),"pending");
    }
    @Test (priority = 2)
    public void CreateTodoWithEmptyBody() {
        createTodoRequestModel.setTitle("");
        createTodoRequestModel.setDueOn("");
        createTodoRequestModel.setStatus("");
        InvalidResponseMessage[] MessageResponseModel = TodoRequests.InvalidCreateTodo(createTodoRequestModel,id,422);
        for (InvalidResponseMessage TodoResponse :MessageResponseModel){
            if (TodoResponse.getField().equals("title")){
                Assert.assertEquals(TodoResponse.getMessage(),"can't be blank","Title validation message mismatch");
            }
            if (TodoResponse.getField().equals("status")){
                Assert.assertEquals(TodoResponse.getMessage(),"can't be blank, can be pending or completed","Status validation message mismatch");
            }
        }
  }
    @Test(priority =3)
    public void CreatePostWithDoesNotExistedUser(){
        UserRequest.deleteUserUsingId(id);
        createTodoRequestModel.setTitle("Check emails/messages for urgent tasks");
        createTodoRequestModel.setDueOn("2024-10-28");
        createTodoRequestModel.setStatus("pending");
        InvalidResponseMessage[] MessageResponseModel =TodoRequests.InvalidCreateTodo(createTodoRequestModel,id,422);
        Assert.assertEquals(MessageResponseModel[0].getField(),"user");
        Assert.assertEquals(MessageResponseModel[0].getMessage(),"must exist");
    }

    @AfterClass
    public void teardown(){
        UserRequest.deleteUserUsingId(id);
    }


}
