package com.generatorimprez.GEN.Controller;

import com.generatorimprez.GEN.Model.Mail;
import com.generatorimprez.GEN.Model.ResetToken;
import com.generatorimprez.GEN.Model.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.UUID;

@Controller
@SessionAttributes("newUser")
public class ReminderController {


    @GetMapping("/przypomnij")
    public String remind(Model model) {
        model.addAttribute("newUser", new User());
        return "przypomnij";
    }

    @GetMapping("/przypomnij2")
    public String reminder(@ModelAttribute(name = "newUser") User user, Model model) {
        return "przypomnij2";
    }


    @PostMapping("/przypomnij")
    public String chckPass(@ModelAttribute(name = "newUser") User user, Model model) {
        if (User.chckUsername(user.getUsername())) {
            user.getReminderFromDB();
            return "przypomnij2";
        } else model.addAttribute("incorrectData", true);
        return "przypomnij";
    }

    @PostMapping("/przypomnij2")
    public String sendMail(@ModelAttribute(name = "newUser") User user, Model model, HttpServletRequest request) {
        if (user.chckAnswer()) {
            String token = UUID.randomUUID().toString();
            ResetToken resetToken = new ResetToken(token, user);
            String message = "W celu zmiany hasła wklej poniższy link do okna przeglądarki: \nlocalhost:8080" + request.getContextPath() + "/user/changePassword?token=" + token;
            Mail.sendMail("reset hasła", message, user);
            model.addAttribute("linkSent", true);
            return "przypomnij2";
        } else model.addAttribute("incorrectData", true);
        return "przypomnij2";
    }


}
