package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
public class HomeController {
    @Autowired
    DepartmentRepository departmentRepository;

    @Autowired
    EmployeeRepository employeeRepository;

    //==============Employee==============
    @RequestMapping("/")
    public String listEmployees(Model model){
        model.addAttribute("employees", employeeRepository.findAll());
        model.addAttribute("departments", departmentRepository.findAll());
        return "index";
    }

    @GetMapping("/add")
    public String employeeForm(Model model){
        model.addAttribute("departments", departmentRepository.findAll());
        model.addAttribute("employee", new Employee());
        return "employeeForm";
    }

    @PostMapping("/process")
    public String processForm(@Valid Employee employee, BindingResult result, Model model){
        if(result.hasErrors()){
            model.addAttribute("departments", departmentRepository.findAll());
            return "employeeForm";
        }
        employeeRepository.save(employee);
        return  "redirect:/";
    }
    @RequestMapping("/detail/{id}")
    public String showEmployee(@PathVariable("id") long id, Model model){
        model.addAttribute("employee", employeeRepository.findById(id).get());
        return "show";
    }

    @RequestMapping("/update/{id}")
    public String updateEmployee(@PathVariable("id") long id, Model model){
        model.addAttribute("departments", departmentRepository.findAll());
        model.addAttribute("employee", employeeRepository.findById(id));
        return "employeeForm";
    }

    @RequestMapping("/delete/{id}")
    public String delSong(@PathVariable("id") long id){
        employeeRepository.deleteById(id);
        return "redirect:/";
    }

    //========================= Department================
    @GetMapping("/addDepartment")
    public String departmentForm(Model model){
        model.addAttribute("department", new Department());
        return "departmentForm";
    }
    @PostMapping("/processDepartment")
    public String processDepartment(@Valid Department department, BindingResult result){
        if(result.hasErrors()){
            return "departmentForm";
        }
        departmentRepository.save(department);
        return "redirect:/";
    }
}
