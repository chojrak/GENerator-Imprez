package com.generatorimprez.GEN.Controller;

import com.generatorimprez.GEN.Model.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class Navigation {

    @GetMapping("/")
    public String getHome(Model model) {
        return "index";
    }

    @GetMapping("/zarejestruj")
    public String signUp(Model model) {
        model.addAttribute("newUser", new User());
        return "zarejestruj";
    }

    @GetMapping("/o-nas")
    public String aboutUs(Model model) {
        model.addAttribute("userList", User.getAllUsers());
        return "onas";
    }

    @GetMapping("/zaloguj")
    public String signIn(Model model) {
        return "zaloguj";
    }

    @PostMapping("/new-user")
    public String newUser(@ModelAttribute User user) {
        if(!user.getUsername().equals("") && !user.getPassword().equals("") && !User.chckUsername(user.getUsername()))
         User.addUserToDB(user);
        //System.out.println(user);
        return "redirect:/o-nas";
    }


}
