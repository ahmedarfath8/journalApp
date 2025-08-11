package com.arfath.journalApp.entity;

import com.arfath.journalApp.enums.Sentiments;
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
    private Sentiments sentiments; //tough datatype is enum it will be stored as string in the db and treated as sentiment (enum)
}
