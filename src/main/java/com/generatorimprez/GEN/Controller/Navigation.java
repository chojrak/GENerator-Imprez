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
        return "zarejestruj";
    }

    @GetMapping("/zaloguj")
    public String signIn(Model model) {
        return "zaloguj";
    }

    @PostMapping("/zarejestruj")
    public String getKontakt(@ModelAttribute User user) {
            return "redirect:/zaloguj";
        }
}
