package com.arfath.journalApp.repository;

import com.arfath.journalApp.entity.JournalEntry;
import com.arfath.journalApp.entity.User;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface UserRepository extends MongoRepository<User, ObjectId> {

  User findByUserName(String username);//creating custom method to be used in service(this method return user where we will use fields which is username & password

  void deleteByUserName(String name);

}
