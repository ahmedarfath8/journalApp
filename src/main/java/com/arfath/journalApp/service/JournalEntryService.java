package com.arfath.journalApp.service;

import com.arfath.journalApp.entity.JournalEntry;
import com.arfath.journalApp.entity.User;
import com.arfath.journalApp.repository.JournalEntryRepository;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;



@Component
public class JournalEntryService {

    @Autowired
    private JournalEntryRepository journalEntryRepository;

    @Autowired
    private UserService userService;


    @Transactional
    public void saveEntry(JournalEntry journalEntry,String userName){
        try {
            User user = userService.findByUserName(userName);
            journalEntry.setDate(LocalDateTime.now());
            JournalEntry saved = journalEntryRepository.save(journalEntry);
            user.getJournalEntries().add(saved);
            userService.saveUser(user);
        } catch (Exception e) {
            System.out.println(e);
            throw new RuntimeException("an error occurred while saving the entry",e);
        }
    }
    public void saveEntry(JournalEntry journalEntry){
        journalEntryRepository.save(journalEntry);
    }

    public List<JournalEntry> getAll(){
        return journalEntryRepository.findAll();
    }
    
    public Optional<JournalEntry> findById(ObjectId id){
        return journalEntryRepository.findById(id);
    }

    @Transactional
    public boolean deleteById(ObjectId id,String userName){
        boolean removed = false;
        try {
            User user = userService.findByUserName(userName);
             removed = user.getJournalEntries().removeIf(entry -> entry.getId().equals(id));//user.getJournalEntries().removeIf(entry -> entry.getId().equals(id)); if you comment this line  only journal entry will be deleted but the reference will be there in user's list which will be pointing to null to avoid that and delete @ once in both places ie journal_entries and journalEntreis(user list which stores ref) one more thing when next post operation is done on same user spring will identify that the ref is pointing to null then  spring will update ref to point to new journal entry
            if(removed){
                userService.saveUser(user);
                journalEntryRepository.deleteById(id);
            }
        } catch (Exception e) {
            System.out.println(e);
            throw new RuntimeException("a error occurred during deletion ",e);
        }
        return removed;
    }

//    //mycode
//    public void deleteAllEntriesOfUser(String userName){
//        try {
//            User user = userService.findByUserName(userName);
//            List<JournalEntry> journalEntries = user.getJournalEntries();
//            for (JournalEntry items :journalEntries){
//                journalEntryRepository.deleteById(items.getId());
//            }
//            userService.saveNewUser(user);
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
//    }


}
