package PostTests;
import Models.PostModels.CreatePostRequestModel;
import Models.PostModels.CreatePostResponseModel;
import Models.PostModels.InvalidResponseMessage;
import Models.UserModels.CreateUserRequestModel;
import Models.UserModels.CreateUserResponseModel;
import Requests.PostRequest;
import Requests.UserRequest;
import Utilities.constants;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import static Requests.UserRequest.CreateUser;

public class CreatePostTests {
    String id = "";
    CreateUserRequestModel createUserRequestModel = new CreateUserRequestModel();
    CreateUserResponseModel createUserResponseModel = new CreateUserResponseModel();
    CreatePostRequestModel createPostRequestModel = new CreatePostRequestModel();

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
    public void createNewPost() {

        createPostRequestModel.setTitle("The Programmer's Best Friend");
        createPostRequestModel.setBody("Every great coder knows that a cup of coffee fuels productivity. Here’s how to brew the perfect cup!");
        CreatePostResponseModel PostResponseModel = PostRequest.CreatePosts(createPostRequestModel,id,201);
        Assert.assertNotNull(PostResponseModel.getId(), "Id is not As Expected");
        Assert.assertEquals(PostResponseModel.getTitle(),"The Programmer's Best Friend");
        Assert.assertEquals(PostResponseModel.getBody(),"Every great coder knows that a cup of coffee fuels productivity. Here’s how to brew the perfect cup!");

    }
    @Test (priority = 2)
    public void CreatePostWithEmptyBody() {
        createPostRequestModel.setTitle("");
        createPostRequestModel.setBody("");
        InvalidResponseMessage[] MessageResponseModel = PostRequest.InvalidCreatePosts(createPostRequestModel,id,422);
        Assert.assertEquals(MessageResponseModel[0].getField(),"title");
        Assert.assertEquals(MessageResponseModel[0].getMessage(),"can't be blank");
        Assert.assertEquals(MessageResponseModel[1].getField(),"body");
        Assert.assertEquals(MessageResponseModel[1].getMessage(),"can't be blank");
    }
    @Test (priority = 3)
    public void CreatePostWithDoesNotExistedUser() {
        UserRequest.deleteUserUsingId(id);
        createPostRequestModel.setTitle("Stay Organized");
        createPostRequestModel.setBody("Use a to-do list or a planner to keep track of tasks and deadlines. Organization boosts efficiency!");
        InvalidResponseMessage[] MessageResponseModel = PostRequest.InvalidCreatePosts(createPostRequestModel,id,422);
        Assert.assertEquals(MessageResponseModel[0].getField(),"user");
        Assert.assertEquals(MessageResponseModel[0].getMessage(),"must exist");
    }
    @AfterClass
    public void teardown(){
        UserRequest.deleteUserUsingId(id);
    }
}
