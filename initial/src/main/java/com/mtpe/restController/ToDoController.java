package com.mtpe.restController;

import com.mtpe.model.JobAnnouncement;
import com.mtpe.model.User;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by precuay on 5/3/15.
 */
@RestController
@RequestMapping("/todo")
public class ToDoController {

    ArrayList<ToDo> todos = new ArrayList<>();

    private class ToDo{
        public String id;
        public String name;
        public String description;
        public String site;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getSite() {
            return site;
        }

        public void setSite(String site) {
            this.site = site;
        }
    }

    @PostConstruct
    void fill(){
        ToDo toDoOne = new ToDo();
        toDoOne.setId("1");
        toDoOne.setName("one name");
        toDoOne.setSite("http://www.one.com");
        toDoOne.setDescription("one description");

        ToDo toDoTwo = new ToDo();
        toDoTwo.setId("2");
        toDoTwo.setName("two name");
        toDoTwo.setSite("http://www.two.com");
        toDoTwo.setDescription("two description");

        todos.add(toDoOne);
        todos.add(toDoTwo);
    }

    @RequestMapping(
            method = RequestMethod.GET
    )
    @ResponseBody
    public ArrayList<ToDo> getAll() {

        System.out.println("SIZE >>> " + todos.size());

        return  todos;
    }

    @RequestMapping(
            value ="/{id}",
            method = RequestMethod.GET
    )
    @ResponseBody
    public ToDo getId(
            @PathVariable(value="id") Integer id
    ) {

        for(ToDo toDo : todos){
            if (Integer.parseInt(toDo.getId())==(id)){
                return toDo;
            }
        }

        return null;
    }

}
