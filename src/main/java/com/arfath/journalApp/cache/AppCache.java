package com.arfath.journalApp.cache;

import com.arfath.journalApp.entity.ConfigJournalAppEntity;
import com.arfath.journalApp.repository.ConfigJournalAppRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class AppCache {

    public enum keys{
        WEATHER_API
    }

    @Autowired
    private ConfigJournalAppRepository configJournalAppRepository;

    public Map<String,String> appCache;//just declaration here //public cuz we'll be using this in other classes

    @PostConstruct
    public void init(){
        appCache = new HashMap<>(); //initialization here // cuz  when ever we update new or old keys/value in the  config collection we dont want to restart the project again we  will just run  init method which will fetch all key/value updated by find all and store it in memory ie cache// without need to restart assume we update api to apI this will be considered new key
        List<ConfigJournalAppEntity> all = configJournalAppRepository.findAll(); //this will get us all the keys and values stored in the config collection in db
        for(ConfigJournalAppEntity configJournalAppEntity : all){
            appCache.put(configJournalAppEntity.getKey(),configJournalAppEntity.getValue());
        }
    }
}
