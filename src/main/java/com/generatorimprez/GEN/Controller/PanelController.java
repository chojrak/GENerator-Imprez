package com.generatorimprez.GEN.Controller;

import com.generatorimprez.GEN.Model.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class PanelController {

    @GetMapping("/panel")
    public String getPanel(Model model, User user, Service service, SubService subService, PromoCode promoCode) {
        model.addAttribute("newUser", new User());
        model.addAttribute("newService", new Service());
        model.addAttribute("newSubService", new SubService());
        model.addAttribute("serviceNames", Service.getServiceNames());
        model.addAttribute("newPromoCode", new PromoCode());
        return "panel";
    }


    @PostMapping("/panel-user")
    public String adminUser(@ModelAttribute("newUser") User user, Model model) {
        return "panel";
    }

    @PostMapping("/panel-service")
    public String adminService( @ModelAttribute("newService")Service service,  Model model) {
        return "panel";
    }

    @PostMapping("/panel-subService")
    public String adminSubService(@ModelAttribute("newSubService")SubService subService, Model model) {
        return "panel";
    }

    @PostMapping("/panel-promo-code")
    public String adminPromoCode( @ModelAttribute("newPromoCode")PromoCode promoCode, Model model) {
        return "panel";
    }

}
