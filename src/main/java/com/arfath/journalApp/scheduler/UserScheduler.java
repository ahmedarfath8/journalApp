package com.arfath.journalApp.scheduler;

import com.arfath.journalApp.cache.AppCache;
import com.arfath.journalApp.entity.JournalEntry;
import com.arfath.journalApp.entity.User;
import com.arfath.journalApp.enums.Sentiments;
import com.arfath.journalApp.repository.UserRepositoryImpl;
import com.arfath.journalApp.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class UserScheduler {

    @Autowired
    private EmailService emailService;

    @Autowired
    private UserRepositoryImpl userRepository;

    @Autowired
    private AppCache appCache;



    @Scheduled(cron = "0 0/10 * ? * *")
    public void clearAppCache(){
        //since we are also using app cache instead of manually hitting  app cache using api we can schedule it to run every  10 min
        appCache.init();
    }


    //@Scheduled(cron = "0 0 9 * * SUN") //this cron expression is for once in a week on sunday
    //@Scheduled(cron = "0 * * ? * *") //this is for testing purpose //commenting for testing purposes just so that it will run once
    public void fetchUserAndSendSaEmail(){
        List<User> users = userRepository.getUserForSA();
        for(User user : users){
            List<JournalEntry> journalEntries = user.getJournalEntries();
            List<Sentiments> sentiments  = journalEntries.stream().filter(x -> x.getDate().isAfter(LocalDateTime.now().minus(7, ChronoUnit.DAYS))).map(x -> x.getSentiments()).collect(Collectors.toList());
            Map<Sentiments,Integer> sentimentCount = new HashMap<>();
            for(Sentiments sentiment : sentiments){
                if(sentiment != null){
                    sentimentCount.put(sentiment, sentimentCount.getOrDefault(sentiment,0)+1);
                }
            }
            Sentiments mostFrequentSentiment = null;
            int maxCount = 0;
            for(Map.Entry<Sentiments,Integer> entry : sentimentCount.entrySet()){
                if(entry.getValue() > maxCount){
                    maxCount = entry.getValue();
                    mostFrequentSentiment = entry.getKey();
                }
            }
            if(mostFrequentSentiment != null){
                emailService.sendEmail(user.getEmail(),"sentimental analysis of past seven days",mostFrequentSentiment.toString());
            }
        }
    }
}
