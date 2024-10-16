package PostTests;

import Models.PostModels.CreatePostRequestModel;
import Models.PostModels.CreatePostResponseModel;
import Models.PostModels.GetPostResponseModel;
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

public class GetPostsTest {
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
   public static void ValidateKeysInPosts() {
        GetPostResponseModel[] getPostResponseModel = PostRequest.GetAllPosts();
        for (GetPostResponseModel post : getPostResponseModel) {
            Assert.assertNotNull(post.getId(), "Missing field: id");
            Assert.assertNotNull(post.getUserId(), "Missing field: user_id");
            Assert.assertNotNull(post.getTitle(), "Missing field: title");
            Assert.assertNotNull(post.getBody(), "Missing field: body");
        }
    }
    @Test(priority = 2)
    public static void getSinglePostUsingId(){
        GetPostResponseModel getPostResponse=PostRequest.getSinglePost(Post_id);
        Assert.assertNotNull(getPostResponse.getId(), "Missing field: id");
        Assert.assertEquals(getPostResponse.getTitle(),Title,"title not Equals");
        Assert.assertEquals(getPostResponse.getBody(),Body,"body not Equals");
    }


    @Test(priority = 3)
    public static void getPostUsingTitle(){
        GetPostResponseModel[] getPostResponseModels= PostRequest.getPostUsingTitle(Title,200);
        boolean titleExists = false;
        for (GetPostResponseModel Post : getPostResponseModels) {
            if (Post.getTitle().equals(Title)) {
                titleExists = true;
                break;
            }
            Assert.assertTrue(titleExists, "The title is not as expected");

        }
    }
    @Test(priority = 4)
    public static void getPostsOfSpecificUser() {
        GetPostResponseModel[] getPostResponseModel = PostRequest.getPostsOfSpecificUser(id,200);
        for (GetPostResponseModel postResponse : getPostResponseModel) {
            Assert.assertEquals(String.valueOf(postResponse.getUserId()),id, "Post is missing the id field");
            Assert.assertNotNull(postResponse.getId(), "Post is missing the id field");
            Assert.assertNotNull(postResponse.getTitle(), "Post is missing the Title field");
            Assert.assertNotNull(postResponse.getBody(), "Post is missing the Body field.");

        }
    }

    @AfterClass
    public void deleteUser(){
        UserRequest.deleteUserUsingId(id);
    }
}



