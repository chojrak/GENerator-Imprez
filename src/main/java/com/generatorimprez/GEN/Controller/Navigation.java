package com.generatorimprez.GEN.Controller;

import com.generatorimprez.GEN.Model.PackageDeal;
import com.generatorimprez.GEN.Model.Service;
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

    @GetMapping("/zorganizuj-impreze")
    public String getOferta(Model model, PackageDeal packageDeal) {
        Service.getServices();
        model.addAttribute("subServiceNames", packageDeal.getServiceNames());
        model.addAttribute("multi", Service.multi);
        model.addAttribute("one", Service.one);

        return "oferta";
    }

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
            return "redirect:/o-nas";
        }
        return "zarejestruj";
    }

    @PostMapping("/zorganizuj-impreze")
    public String newPackageDeal(@ModelAttribute PackageDeal packageDeal, User user, Model model) {
        System.out.println(packageDeal.getServiceNames());

        return "redirect:/";
    }


}
