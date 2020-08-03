package com.generatorimprez.GEN.Controller;

import com.generatorimprez.GEN.Model.ResetToken;
import com.generatorimprez.GEN.Model.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@SessionAttributes("token")
public class ResetController {

    @GetMapping("/user/changePassword")
    public String resetPassword(Model model, @RequestParam("token") String token) {
        if (ResetToken.isTokenValid(token)) {
            model.addAttribute("token", token);
            return "redirect:/reset";
        } else {
            model.addAttribute("token", token);
            return "redirect:/";
        }
    }


    @GetMapping("/reset")
    public String resetPass(Model model, @ModelAttribute("token") String token) {
        model.addAttribute("newUser", new User());
        return "reset";
    }

    @PostMapping("/reset")
    public String newUser(@ModelAttribute(name = "newUser") User user, Model model, @ModelAttribute(name = "token") String token) {
        if (!user.getPassword2().equals(user.getPassword()))
            model.addAttribute("incorrectPassword", true);
        else model.addAttribute("incorrectPassword", false);
        if (user.getUsername().isEmpty())
            model.addAttribute("noUserName", true);
        else model.addAttribute("noUserName", false);
        if (!ResetToken.isTokenMatchingUser(token, User.chckIdUser(user.getUsername())))
            model.addAttribute("wrongUser", true);
        else model.addAttribute("wrongUser", false);
        if (user.getPassword2().equals(user.getPassword()) && !user.getUsername().isEmpty() && ResetToken.isTokenMatchingUser(token, User.chckIdUser(user.getUsername()))) {
            user.updatePassword();
            return "zaloguj";
        }
        return "reset";
    }

}
