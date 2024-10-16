package Utilities;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.github.javafaker.Faker;
import io.restassured.mapper.ObjectMapperDeserializationContext;
import io.restassured.mapper.ObjectMapperSerializationContext;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.HashMap;
import java.util.Map;

public class constants {
    static Faker faker=new Faker();
    public static String base_url="https://gorest.co.in/public/v2";
    public static String userEndPoint="/users/";
    public static String PostEndPoint="/posts/";
    public static String TodoEndPoint="/todos/";

    public static   String username="Hala";
    public static   String userEmail=faker.internet().emailAddress();
    public static   String userGender="female";
    public static   String userStatus="active";
    public static   String userInvalidEmail="invalidEmail";
    public static   String emailAlreadyRegistered="halataha@gmail.com";

    public static Map<String,String> generalHeaders(){
        Map<String,String> headers=new HashMap<>();
        headers.put("Authorization","Bearer d620902352d1eb58b15493c2818c9dc11fe841231c052e7e7bf65b7b4c488384\t");
        return headers;
    }

}
