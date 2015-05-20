package com.mtpe.restController;

import com.mtpe.model.JobAnnouncement;
import com.mtpe.model.ToDo;
import com.mtpe.model.User;
import org.springframework.http.HttpStatus;
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

    public void create(
            @RequestBody ToDo toDo
    ){
        //not necesarry now
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
    public ToDo get(
            @PathVariable(value="id") Integer id
    ) {

        for(ToDo toDo : todos){
            if (Integer.parseInt(toDo.getId())==(id)){
                return toDo;
            }
        }

        return null;
    }

    @RequestMapping(
            value = "/{id}",
            method = RequestMethod.PUT
    )
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void update(
            @RequestBody ToDo toDo,
            @PathVariable(value = "id") Integer id
    ) {

        try{
            System.out.println("PUT >>>>"+toDo.getName());
        }catch (Exception e){
            System.out.println("ERROR PUT >>>>"+id);
        }
    }

    public void remove(
            @PathVariable(value = "id") Integer id
    ){
        //not necesarry now
    }

}
