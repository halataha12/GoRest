package PostTests;
import Models.PostModels.CreatePostRequestModel;
import Models.PostModels.CreatePostResponseModel;
import Models.PostModels.GetPostResponseModel;
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

public class updatePost {
    static String id ="";
    static String Title="";
    static String Body="";
    static String Post_id="";
    CreateUserRequestModel createUserRequestModel =new CreateUserRequestModel();
    CreateUserResponseModel createUserResponseModel =new CreateUserResponseModel();
    CreatePostRequestModel createPostRequestModel = new CreatePostRequestModel();

    @BeforeClass
    public void Precondition() throws JsonProcessingException {
        createUserRequestModel.setName(constants.username);
        createUserRequestModel.setEmail(constants.userEmail);
        createUserRequestModel.setGender(constants.userGender);
        createUserRequestModel.setStatus(constants.userStatus);
        createUserResponseModel=CreateUser(createUserRequestModel,201);
        id=createUserResponseModel.getId();
        createPostRequestModel.setTitle("The Programmer's Best Friend");
        createPostRequestModel.setBody("Every great coder knows that a cup of coffee fuels productivity. Here’s how to brew the perfect cup!");
        CreatePostResponseModel PostResponseModel = PostRequest.CreatePosts(createPostRequestModel,id,201);
        Post_id=PostResponseModel.getId();
        Title =PostResponseModel.getTitle();
        Body =PostResponseModel.getBody();
    }
    @Test(priority = 1)
    public void update_post_with_valid_id(){
        CreatePostRequestModel updatePost =new CreatePostRequestModel();
        updatePost.setTitle("New Title:Why Learning a New Skill is Easier Than You Think");
        updatePost.setBody("New Body : With so many online resources available, learning a new skill is more accessible than ever. Pick something you're passionate about and dedicate just 30 minutes a day – you'll be surprised at how quickly you improve!");
        GetPostResponseModel getPostResponseModel=PostRequest.UpdatePost(updatePost,Post_id,200);
        Assert.assertEquals(getPostResponseModel.getTitle(),"New Title:Why Learning a New Skill is Easier Than You Think","Title was not updated successfully");
        Assert.assertEquals(getPostResponseModel.getBody(),"New Body : With so many online resources available, learning a new skill is more accessible than ever. Pick something you're passionate about and dedicate just 30 minutes a day – you'll be surprised at how quickly you improve!");
    }

    @Test (priority = 2)
    public void Update_with_valid_ID_and_empty_Data() throws JsonProcessingException {
        CreatePostRequestModel updatePost = new CreatePostRequestModel();
        updatePost.setTitle("");
        updatePost.setBody("");
        InvalidResponseMessage[] getPostResponseModel = PostRequest.InvalidUpdatePost(updatePost, Post_id, 422);
        for (InvalidResponseMessage postResponse : getPostResponseModel) {
            if (postResponse.getField().equals("title")) {
                Assert.assertEquals(postResponse.getMessage(), "can't be blank", "Title validation message mismatch");
            }
            if (postResponse.getField().equals("body")) {
                Assert.assertEquals(postResponse.getMessage(), "can't be blank", "Body validation message mismatch");
            }

        }

    }
    @Test(priority = 3)
    public void update_post_with_invalidId() {
        PostRequest.deletePostWithValidId(Post_id);
        CreatePostRequestModel updatePost =new CreatePostRequestModel();
        updatePost.setTitle("New Title: Why Learning a New Skill is Easier Than You Think");
        updatePost.setBody("New Body : With so many online resources available, learning a new skill is more accessible than ever. Pick something you're passionate about and dedicate just 30 minutes a day – you'll be surprised at how quickly you improve!");
        InvalidResponseMessage getPostResponseModel = PostRequest.InvalidIDUpdatePost(updatePost, Post_id, 404);
        Assert.assertEquals(getPostResponseModel.getMessage(),"Resource not found");

    }
    @AfterClass
    public void teardown(){
        UserRequest.deleteUserUsingId(id);
    }
}
