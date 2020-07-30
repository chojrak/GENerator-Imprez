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
    public String getHome(Model model, User user) {
        return "index";
    }

    @GetMapping("/zarejestruj")
    public String signUp(Model model) {
        model.addAttribute("newUser", new User());
        return "zarejestruj";
    }

    @GetMapping("/o-nas")
    public String aboutUs(Model model) {
        model.addAttribute("newUser", new User());
        model.addAttribute("userList", User.getAllUsers());
        return "onas";
    }

    @GetMapping("/zaloguj")
    public String signIn(Model model) {
        model.addAttribute("newUser", new User());
        return "zaloguj";
    }



 /*   @PostMapping("/zaloguj")
    public String chckPass(@ModelAttribute(name = "newUser") User user, Model model) {
        model.addAttribute("newUser", new User());
        if (user.chckuser(user))
        return "/index";
        else model.addAttribute("incorrectData", true);
        return "zaloguj";
    }*/

    @PostMapping(value = "/zarejestruj")
    public String newUser(@ModelAttribute(name = "newUser") User user, Model model) {

        if (!user.getPassword2().equals(user.getPassword()))
            model.addAttribute("incorrectPassword", true);
        else model.addAttribute("incorrectPassword", false);
        if (user.getUsername().isEmpty())
            model.addAttribute("noUserName", true);
        else model.addAttribute("noUserName", false);
        if (user.getReminder().isEmpty())
            model.addAttribute("noReminder", true);
        else model.addAttribute("noReminder", false);
        if (user.getAnswer().isEmpty())
            model.addAttribute("noAnswer", true);
        else model.addAttribute("noAnswer", false);
        if (User.chckUsername(user.getUsername()) && !user.getUsername().isEmpty())
            model.addAttribute("userExists", true);
        else model.addAttribute("userExists", false);
        if (user.getPassword2().equals(user.getPassword()) && !user.getUsername().isEmpty() && !user.getReminder().isEmpty() && !user.getAnswer().isEmpty() && !User.chckUsername(user.getUsername())) {
            User.addUserToDB(user);
           // return "redirect:/o-nas";
            return "zaloguj";
        }
        return "zarejestruj";
    }


}
