package com.arfath.journalApp.repository;

import com.arfath.journalApp.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;


import java.util.List;

public class UserRepositoryImpl {


    @Autowired
    private MongoTemplate mongoTemplate; //this we need to run query

    public List<User> getUserForSA(){ //this will get all users opted for sentimental analysis
        Query query = new Query();
        //query.addCriteria(Criteria.where("userName").is("olivia")); //this is the simple syntax of the criteria
        /*Criteria criteria = new Criteria(); //this instance is used to create custom and / or
        query.addCriteria(criteria.andOperator(
                Criteria.where("email").ne("null"),
                Criteria.where("email").ne(""),
                Criteria.where("sentimentalAnalysis").is(true)
                ));*/ //this does the same thing as below but below one has better readability so // for or opreator just change and  to or

        //query.addCriteria(Criteria.where("email").ne("null").ne(""));
        //query.addCriteria(Criteria.where("email").ne("")); //ne chaining can be done above
        //this both statement are fine but what we can do is use regular expression to check if email is valid
        //we can do this using regex
        query.addCriteria(Criteria.where("email").regex(".+@.+\\..+"));//this regular expression was too strict .regex("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$"));cuz this regex needs real emails only so this is bit relax one .regex(".+@.+\\..+"));
        query.addCriteria(Criteria.where("sentimentalAnalysis").is(true));
        //query.addCriteria(Criteria.where("UserName").in("olivia","oggy")); //this syntax is to play with array ("olivia","oggy") csv format
        //query.addCriteria(Criteria.where("roles").in("USER","ADMIN")); //though here its csv string here and array in mongo it will still compare it will consider it to be string only to compare
        //query.addCriteria(Criteria.where("sentimentalAnalysis").type(JsonSchemaObject.Type.BsonType.BOOLEAN));//you can also play with type (datatype) also //this will check if its boolean datatype or not
        List<User> usersInSa = mongoTemplate.find(query, User.class); //this is important for query's to run
        return usersInSa; //to test the o/p instead of going for postman we will simply create a test file
    }

}
