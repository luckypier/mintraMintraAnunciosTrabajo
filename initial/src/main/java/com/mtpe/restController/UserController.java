package com.mtpe.restController;

import com.mtpe.model.JobAnnouncement;
import com.mtpe.model.User;
import com.mtpe.repository.JobAnnouncementDao;
import com.mtpe.repository.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;

/**
 * Created by precuay on 4/28/15.
 */
@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserDao _userDao;

    @Autowired
    private JobAnnouncementDao _jobAnnouncementDao;

    // http://localhost:8080/users/greeting?name=Pierre
    @RequestMapping(
            value ="/greeting",
            method = RequestMethod.GET
    )
    public JobAnnouncement greeting(
            @RequestParam(value="name", defaultValue="World") String name
    ) {
        JobAnnouncement jobAnnouncement = new JobAnnouncement();
        jobAnnouncement.setCargo(name.toUpperCase());
        jobAnnouncement.setCodinst("COD");
        jobAnnouncement.setLink("LINK");
        return jobAnnouncement;
    }


    // http://localhost:8080/users/2
    @RequestMapping(
            value ="/{id}",
            method = RequestMethod.GET
    )
    @ResponseBody
    public User getId(
            @PathVariable(value="id") Integer id
    ) {
        return  _userDao.getById(id);
    }

    @RequestMapping(
            method = RequestMethod.GET
    )
    @ResponseBody
    public Collection<JobAnnouncement> getAll() {

        Collection<JobAnnouncement> jobAnnouncements = _jobAnnouncementDao.getAll();

        System.out.println("SIZE >>> "+jobAnnouncements.size());

        return  jobAnnouncements;
    }
}
