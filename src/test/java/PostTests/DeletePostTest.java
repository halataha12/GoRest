
package PostTests;
import Models.PostModels.CreatePostRequestModel;
import Models.PostModels.CreatePostResponseModel;
import Models.UserModels.CreateUserRequestModel;
import Models.UserModels.CreateUserResponseModel;
import Models.UserModels.InvalidResponseMessageModel;
import Requests.PostRequest;
import Requests.UserRequest;
import Utilities.constants;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import static Requests.UserRequest.CreateUser;

public class DeletePostTest {
    static String id="";
    String Post_Id="";
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
        createPostRequestModel.setTitle("The Programmer's Best Friend");
        createPostRequestModel.setBody("Every great coder knows that a cup of coffee fuels productivity. Here’s how to brew the perfect cup!");
        CreatePostResponseModel PostResponseModel = PostRequest.CreatePosts(createPostRequestModel,id,201);
        Post_Id=PostResponseModel.getId();

    }
    @Test(priority =1)
    public void deletePost(){
        Response response=PostRequest.deletePostWithValidId(Post_Id);
        response.prettyPrint();
        response.then().statusCode(204);
        Assert.assertTrue(response.body().asString().isEmpty(), "Response body is not empty");
    }

    @Test(priority =2)
    public void delete_Post_with_invalid_ID(){
        InvalidResponseMessageModel deleteUsingInvalidIDModel=PostRequest.deletePostWithInvalidId(Post_Id,404);
        Assert.assertEquals(deleteUsingInvalidIDModel.message,"Resource not found");
    }
    @AfterClass
    public void teardown(){
        UserRequest.deleteUserUsingId(id);
    }

}
