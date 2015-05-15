package com.mtpe.business;

import com.mtpe.model.JobAnnouncement;
import com.mtpe.model.JobAnnouncementNotMatch;
import com.mtpe.repository.JobAnnouncementDao;
import com.mtpe.repository.JobAnnouncementNotMatchDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by precuay on 4/29/15.
 */
@Service
public class JobAnnouncementService {

    public static final String URL_MINTRA = "http://silnet.trabajo.gob.pe:8080/silnet/ListadoVacantesPublicas.jsp";
    public static final String URL_ONE_RECORD = "http://silnet.trabajo.gob.pe:8080/silnet/PreviewVacantesPublicas.jsp?tarea=cargar&V_NUMREG=RRRRRRRRRR&V_CODINST=IIIIIIIIII";


    public static final String USER_PATTERN_ONE = "ABOGADO";
    public static final String USER_PATTERN_TWO = "DERECHO";
    public static final String USER_PATTERN_THREE = "LEGAL";


    @Autowired
    HTMLParserService _htmlParserService;

    @Autowired
    JobAnnouncementDao _jobAnnouncementDao;

    @Autowired
    JobAnnouncementNotMatchDao _jobAnnouncementNotMatchDao;

    public ArrayList<JobAnnouncement> process(){

        ArrayList<JobAnnouncement> announcements  = new ArrayList<>();

        try{

            //get all mintra concatenates IDS
            ArrayList<String> concatenatedIdsScanned =  new ArrayList<String>(_htmlParserService.getMintraConcatenatesIds(URL_MINTRA));
            System.out.println("COUNT MINTRA LINKS " + concatenatedIdsScanned.size());

            ArrayList<String> dbAnnouncements = getDbAnnouncements();

            //subtraction new links = concatenatedIdsScanned - matchingconcatenatedIdsDb - noMatchingconcatenatedIdsDb
            concatenatedIdsScanned.removeAll(dbAnnouncements);
            System.out.println("COUNT NEW MINTRA LINKS " + concatenatedIdsScanned.size());

            System.gc();

            //search matching user 'seach words'
            HashMap<String,ArrayList<JobAnnouncement>> results =

                    getMachingWordsNewsAnnouncements(
                            URL_ONE_RECORD,
                            concatenatedIdsScanned,
                            USER_PATTERN_ONE,
                            USER_PATTERN_TWO,
                            USER_PATTERN_THREE
                    );
            System.out.println("MATCHING ANNOUNMENTS " + results.get("newMatches").size());
            System.out.println("NO MATCHING ANNOUNMENTS " + results.get("newNoMatches").size());

            persistMatchingNewAnnouncements(results.get("newMatches"));
            persistNoMatchingNewAnnouncements(results.get("newNoMatches"));

            //show all persisted announcements
            announcements = new ArrayList<JobAnnouncement>(_jobAnnouncementDao.getAll());


        }catch (Exception e){
            e.printStackTrace();
        }

        return announcements;

    }

    private ArrayList<String> getDbAnnouncements() {
        //Get DB announsments matching concatenates IDS
        ArrayList<String> matchingconcatenatedIdsDb = getMatchingDb();
        System.out.println("COUNT MATCHING DBS " + matchingconcatenatedIdsDb.size());

        //Get DB announsments NO matching concatenates IDS
        ArrayList<String> noMatchingconcatenatedIdsDb = getNoMatchingDb();
        System.out.println("COUNT NO MATCHING DBS " + noMatchingconcatenatedIdsDb.size());

        matchingconcatenatedIdsDb.addAll(noMatchingconcatenatedIdsDb);

        return matchingconcatenatedIdsDb;

    }

    private HashMap<String,ArrayList<JobAnnouncement>> getMachingWordsNewsAnnouncements(
            final String url,
            final ArrayList<String> newRecords,
            String ... userSearchWords
    ){

        //Each newRecods check if contains user search words
        HashMap<String,ArrayList<JobAnnouncement>> results = new HashMap<>();
        ArrayList<JobAnnouncement> newMatches = new ArrayList<>();
        ArrayList<JobAnnouncement> newNoMatches = new ArrayList<>();

        int i = 0;
        for(String newRecord : newRecords){

            /*
            if (i==5){
                break;
            }
            */
            JobAnnouncement jobAnnouncement =  _htmlParserService.getMintraMatchingAnnouncement(url, newRecord, userSearchWords);

            if(jobAnnouncement.getStatus() != null){
                //Contains search-word
                newMatches.add(jobAnnouncement);
            }else{
                //not contains search-words
                newNoMatches.add(jobAnnouncement);
            }
            i++;

        }

        results.put("newMatches",   newMatches);
        results.put("newNoMatches", newNoMatches);
        return results;

    }

    private void persistMatchingNewAnnouncements(ArrayList<JobAnnouncement> newMatchingJob) {
        for(JobAnnouncement jobAnnouncement : newMatchingJob){
            _jobAnnouncementDao.create(jobAnnouncement);
            System.out.println("PERSISTED JobAnnouncement");
        }
    }

    private void persistNoMatchingNewAnnouncements(ArrayList<JobAnnouncement> newNoMatchingJob) {
        for(JobAnnouncement jobAnnouncement : newNoMatchingJob){
            JobAnnouncementNotMatch jobAnnouncementNotMatch = new JobAnnouncementNotMatch();
            jobAnnouncementNotMatch.setCodinst(jobAnnouncement.getCodinst());
            jobAnnouncementNotMatch.setNumreg(jobAnnouncement.getNumreg());
            _jobAnnouncementNotMatchDao.create(jobAnnouncementNotMatch);
            jobAnnouncementNotMatch = null;
            System.out.println("PERSISTED JobAnnouncementNotMatch");
        }
    }

    private ArrayList<String> getMatchingDb(){
        Collection<JobAnnouncement> jobAnnouncements = _jobAnnouncementDao.getAll();
        ArrayList<String> formatedJobAnnouncements = new ArrayList<>();
        for(JobAnnouncement announcement : jobAnnouncements){
            if(announcement.getCodinst()!=null){    //only for mintra announcements
                formatedJobAnnouncements.add(
                        announcement.getNumreg()+
                                HTMLParserService.SPLIT_URL_AND+
                                announcement.getCodinst());
            }
        }
        return formatedJobAnnouncements;
    }

    private ArrayList<String> getNoMatchingDb() {
        Collection<JobAnnouncementNotMatch> jobAnnouncementNotMatch =  _jobAnnouncementNotMatchDao.getAll();
        ArrayList<String> formatedJobAnnouncementNotMatch = new ArrayList<>();

        for(JobAnnouncementNotMatch announcement : jobAnnouncementNotMatch){
            formatedJobAnnouncementNotMatch.add(
                    announcement.getNumreg()+
                            HTMLParserService.SPLIT_URL_AND+
                            announcement.getCodinst()
            );
        }

        return formatedJobAnnouncementNotMatch;
    }
}
