package com.arfath.journalApp.entity;

import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Document(collection = "users")
@Data
@Builder //this builder is used in test userdetailsServiceimpl test refer there
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id
    private ObjectId id;
    @Indexed(unique = true)
    @NonNull
    private String userName;
    @NonNull
    private String password;
    private String email;
    private boolean sentimentalAnalysis; //to opt in or out of sentimental analysis
    @DBRef //is used to connect users list<Journalentries> to user's entries stored in the JournalEntry database
    private List<JournalEntry> journalEntries = new ArrayList<>();
    private List<String> roles; //used for authorization
}
