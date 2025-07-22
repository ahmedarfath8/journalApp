package com.arfath.journalApp.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDateTime;


@Document(collection = "journal_entries")
@Data
@NoArgsConstructor//this is very crucial for deserialization ie json to pojo
public class JournalEntry {
    @Id
    private ObjectId id; //changing id type to ObjectId for uniform handling
    @NonNull
    private String title;
    private String content;
    private LocalDateTime date;
}
