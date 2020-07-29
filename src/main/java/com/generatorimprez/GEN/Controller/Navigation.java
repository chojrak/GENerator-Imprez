package com.generatorimprez.GEN.Controller;

import com.generatorimprez.GEN.Model.*;
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
        model.addAttribute("newUser", new User());
        model.addAttribute("userList", User.getAllUsers());
        return "onas";
    }

    @GetMapping("/zaloguj")
    public String signIn(Model model) {
        model.addAttribute("newUser", new User());
        return "zaloguj";
    }

    @GetMapping("/zorganizuj-impreze")
    public String getOferta(Model model, PackageDeal packageDeal, SubServiceList subServiceList) {
        Service.getServices();

        model.addAttribute("subServiceNames", new SubServiceList());
        model.addAttribute("multi", Service.multi);
        model.addAttribute("one", Service.one);

        return "oferta";
    }

    @PostMapping("/zaloguj")
    public String chckPass(@ModelAttribute(name = "newUser") User user, Model model) {
        model.addAttribute("newUser", new User());
        System.out.println(user.getPassword());
        if (user.chckuser(user))
        return "onas";
        else model.addAttribute("incorrectData", true);
        return "zaloguj";
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
            System.out.println(user.getPassword());
            User.addUserToDB(user);
            return "redirect:/o-nas";
        }
        return "zarejestruj";
    }

    @PostMapping("/zorganizuj-impreze")
    public String newPackageDeal(@ModelAttribute SubServiceList subServiceList, PackageDeal packageDeal, User user, Model model) {
        for (String str : subServiceList.getSubServiceNames()) packageDeal.addSubService(SubService.getSubServiceId(str));
        return "redirect:/";
    }




}
