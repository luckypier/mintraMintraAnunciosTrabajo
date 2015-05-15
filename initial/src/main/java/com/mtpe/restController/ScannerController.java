package com.mtpe.restController;

/**
 * Created by precuay on 4/18/15.
 */
import com.mtpe.business.HTMLParserService;
import com.mtpe.business.JobAnnouncementService;
import com.mtpe.model.JobAnnouncement;
import com.mtpe.repository.JobAnnouncementDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Set;

@Controller
public class ScannerController {


    private static final SimpleDateFormat SDF = new SimpleDateFormat("dd-mm-yyyy hh:mm:ss");

    @Autowired
    JobAnnouncementService _jobAnnouncementService;


    //http://localhost:8080/scanRecords
    @RequestMapping("/scanRecords")
    public String searchRecors(Model model) throws Exception {

        ArrayList<JobAnnouncement> newJobAnnouncements  = _jobAnnouncementService.process();

        model.addAttribute("updated", SDF.format(new Date()));
        model.addAttribute("count", newJobAnnouncements.size());
        model.addAttribute("newJobAnnouncements", newJobAnnouncements);

        System.out.println("DONE!!!");

        return "listado";
    }

    //http://localhost:8080/greeting100?name=User
    @RequestMapping("/greeting100")
    public String greeting(@RequestParam(value="name", required=false, defaultValue="World") String name, Model model) {
        model.addAttribute("name", name);
        return "greeting";
    }


    @RequestMapping("/projects")
    public String projects(Model model) {
        model.addAttribute("name", "nombre");
        return "projects";
    }





}