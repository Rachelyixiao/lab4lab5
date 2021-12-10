package sample;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@SessionAttributes({"id","desc"})
@RequestMapping
@Controller
public class InventoryController {
    @Autowired
    CategoryService service;


    @RequestMapping(value = "/category",method = RequestMethod.GET)
    public String showCategorypage(ModelMap model)throws SQLException,ClassNotFoundException {
        model.addAttribute("todos", service.retrieveTodos());
        List<Category> filteredTodos;
        filteredTodos = (List)model.get("todos");
        model.put("id",filteredTodos.get(0).getCatcode());
        model.put("desc",filteredTodos.get(0).getCatdesc());
        return "category";
    }
/*
    @RequestMapping(value = "/",method = RequestMethod.GET)
    public String showCategorypage2(ModelMap model){
        model.addAttribute("todos", service.retrieveTodos());
        return "category";
    }
*/
    @RequestMapping(value = "/add-todo",method = RequestMethod.GET)
    public String showtodopage(){
        return "catser";
    }

    @RequestMapping(value = "/add-todo",method = RequestMethod.POST)
    public String addTodo(ModelMap model,@RequestParam String catcode,@RequestParam String catdesc){
        service.addTodo(catcode,catdesc);

        model.clear();
        return "redirect:/category";
    }

    @RequestMapping(value = "/update-todo",method = RequestMethod.GET)
    public String showUpdateTodoPage(ModelMap model,@RequestParam(defaultValue = "") String id){
        model.put("id",id);
        Category x = service.retrieve(id);
        model.put("id",x.getCatcode());
        model.put("desc",x.getCatdesc());
        return "catedit";
    }

    @RequestMapping(value = "/update-todo",method = RequestMethod.POST)
    public String showUpdate(ModelMap model,@RequestParam String catcode,@RequestParam String catdesc){
        String iid=(String)model.get("id");
        service.deleteTodo(iid);
        service.addTodo(catcode,catdesc);
        return "redirect:/";
    }

    @RequestMapping(value = "/delete-todo",method = RequestMethod.GET)
    public String deleteTodo(ModelMap model,@RequestParam String id)throws SQLException,ClassNotFoundException{
        service.deleteTodo(id);
        model.clear();
        return "redirect:/";
    }

    @RequestMapping(value = "/",method = RequestMethod.GET)
    public String showCategoryPage2(ModelMap model)throws SQLException,ClassNotFoundException{
        model.addAttribute("todos",service.retrieveTodos());

        List<Category> filteredTodos = new ArrayList<Category>();
        filteredTodos =(List)model.get("todos");

            model.put("id",filteredTodos.get(0).getCatcode());
            model.put("desc",filteredTodos.get(0).getCatdesc());

        return "category";
    }



}
