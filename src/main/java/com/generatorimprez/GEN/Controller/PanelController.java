package com.generatorimprez.GEN.Controller;

import com.generatorimprez.GEN.Model.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

@Controller
@SessionAttributes("serviceNames")
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


    @PostMapping("/panel-user-delete")
    public String adminDeleteUser(@ModelAttribute("newUser") User user, @ModelAttribute("newService") Service service, @ModelAttribute("newSubService") SubService subService, @ModelAttribute("newPromoCode") PromoCode promoCode, Model model) {
        if (User.chckUsername(user.getUsername())) {
            user.deleteUser();
            model.addAttribute("userDeleted", true);
            model.addAttribute("window", true);
        } else model.addAttribute("wrongUsername", true);
        return "panel";
    }

    @PostMapping("/panel-user-admin")
    public String setAdmin(@ModelAttribute("newUser") User user, @ModelAttribute("newService") Service service, @ModelAttribute("newSubService") SubService subService, @ModelAttribute("newPromoCode") PromoCode promoCode, Model model) {
        if (User.chckUsername(user.getUsername())) {
            user.setAdmin();
            model.addAttribute("isAdmin", true);
            model.addAttribute("window", true);
        } else {
            model.addAttribute("wrongUsername", true);
            model.addAttribute("window", true);
        }
        return "panel";
    }

    @PostMapping("/panel-user-user")
    public String adminUser(@ModelAttribute("newUser") User user, @ModelAttribute("newService") Service service, @ModelAttribute("newSubService") SubService subService, @ModelAttribute("newPromoCode") PromoCode promoCode, Model model) {
        if (User.chckUsername(user.getUsername())) {
            user.setUser();
            model.addAttribute("isUser", true);
            model.addAttribute("window", true);
        } else {
            model.addAttribute("wrongUsername", true);
            model.addAttribute("window", true);
        }
        return "panel";
    }

    @PostMapping("/panel-service-delete")
    public String adminDeleteService(@ModelAttribute("newUser") User user, @ModelAttribute("newService") Service service, @ModelAttribute("newSubService") SubService subService, @ModelAttribute("newPromoCode") PromoCode promoCode, Model model) {
        if (!service.chckServiceName()) {
            model.addAttribute("wrongServiceName", true);
            model.addAttribute("window", true);
        } else if (service.chckSubServices()) {
            model.addAttribute("activeSubServices", true);
            model.addAttribute("window", true);
        } else {
            service.deleteService();
            model.addAttribute("serviceDeleted", true);
            model.addAttribute("window", true);
        }
        return "panel";
    }

    @PostMapping("/panel-service-add")
    public String adminAddService(@ModelAttribute("newUser") User user, @ModelAttribute("newService") Service service, @ModelAttribute("newSubService") SubService subService, @ModelAttribute("newPromoCode") PromoCode promoCode, Model model) {
        if (service.chckServiceName()) {
            model.addAttribute("serviceNameExists", true);
            model.addAttribute("window", true);
        } else {
            service.addService();
            model.addAttribute("serviceAdded", true);
            model.addAttribute("window", true);
        }
        return "panel";
    }

    @PostMapping("/panel-subService-delete")
    public String adminDeleteSubService(@ModelAttribute("newUser") User user, @ModelAttribute("newService") Service service, @ModelAttribute("newSubService") SubService subService, @ModelAttribute("newPromoCode") PromoCode promoCode, Model model) {
        if (!subService.chckSubServiceName()) {
            model.addAttribute("wrongSubServiceName", true);
            model.addAttribute("window", true);
        } else {
            subService.deleteSubService();
            model.addAttribute("subServiceDeleted", true);
            model.addAttribute("window", true);
        }
        return "panel";
    }

    @PostMapping("/panel-subService-add")
    public String adminAddSubService(@ModelAttribute("newUser") User user, @ModelAttribute("newService") Service service, @ModelAttribute("newSubService") SubService subService, @ModelAttribute("newPromoCode") PromoCode promoCode, Model model) {
        if (subService.chckSubServiceName()) {
            model.addAttribute("wrongSubServiceName2", true);
            model.addAttribute("window", true);
        }
        if (subService.getTax() > 100 || subService.getTax() < 0) {
            model.addAttribute("wrongTaxValue", true);
            model.addAttribute("window", true);
        }
        if (subService.getPrice() < 0) {
            model.addAttribute("wrongPrice", true);
            model.addAttribute("window", true);
        }
        if (!subService.chckSubServiceName() && subService.getTax() <= 100 && subService.getTax() >= 0 && subService.getPrice() >= 0) {
            subService.addSubService();
            model.addAttribute("subServiceAdded", true);
            model.addAttribute("window", true);
        }
        return "panel";
    }

    @PostMapping("/panel-subService-price")
    public String adminPriceSubService(@ModelAttribute("newUser") User user, @ModelAttribute("newService") Service service, @ModelAttribute("newSubService") SubService subService, @ModelAttribute("newPromoCode") PromoCode promoCode, Model model) {
        if (!subService.chckSubServiceName()) {
            model.addAttribute("wrongSubServiceName", true);
            model.addAttribute("window", true);
        } else if (subService.getPrice() < 0) {
            model.addAttribute("wrongPrice", true);
            model.addAttribute("window", true);
        } else {
            subService.changePrice();
            model.addAttribute("priceChanged", true);
            model.addAttribute("window", true);
        }
        return "panel";
    }

    @PostMapping("/panel-subService-tax")
    public String adminTaxSubService(@ModelAttribute("newUser") User user, @ModelAttribute("newService") Service service, @ModelAttribute("newSubService") SubService subService, @ModelAttribute("newPromoCode") PromoCode promoCode, Model model) {
        if (!subService.chckSubServiceName()) {
            model.addAttribute("wrongSubServiceName", true);
            model.addAttribute("window", true);
        } else if (subService.getTax() > 100 || subService.getTax() < 0) {
            model.addAttribute("wrongTaxValue", true);
            model.addAttribute("window", true);
        } else {
            subService.changeTax();
            model.addAttribute("taxChanged", true);
            model.addAttribute("window", true);
        }
        return "panel";
    }

    @PostMapping("/panel-subService-description")
    public String adminDescriptionSubService(@ModelAttribute("newUser") User user, @ModelAttribute("newService") Service service, @ModelAttribute("newSubService") SubService subService, @ModelAttribute("newPromoCode") PromoCode promoCode, Model model) {
        if (!subService.chckSubServiceName()) {
            model.addAttribute("wrongSubServiceName", true);
            model.addAttribute("window", true);
        } else {
            subService.changeDescription();
            model.addAttribute("descriptionChanged", true);
            model.addAttribute("window", true);
        }
        return "panel";
    }

    @PostMapping("/panel-promo-code-add")
    public String adminAddPromoCode(@ModelAttribute("newUser") User user, @ModelAttribute("newService") Service service, @ModelAttribute("newSubService") SubService subService, @ModelAttribute("newPromoCode") PromoCode promoCode, Model model) {
        if (promoCode.getCode().isEmpty()) {
            model.addAttribute("emptyCode", true);
            model.addAttribute("window", true);
        } else if (promoCode.getAmount() <= 0) {
            model.addAttribute("noCodes", true);
            model.addAttribute("window", true);
        } else {
            promoCode.addPromoCodes();
            model.addAttribute("codesAdded", true);
            model.addAttribute("window", true);
        }
        return "panel";
    }

    @PostMapping("/panel-promo-code-delete")
    public String adminPromoCode(@ModelAttribute("newUser") User user, @ModelAttribute("newService") Service service, @ModelAttribute("newSubService") SubService subService, @ModelAttribute("newPromoCode") PromoCode promoCode, Model model) {
        if (!promoCode.chckPromoCode()) {
            model.addAttribute("noCodesInDB", true);
            model.addAttribute("window", true);
        } else {
            promoCode.deletePromoCodes();
            model.addAttribute("promoCodesDeleted", true);
            model.addAttribute("window", true);
        }
        return "panel";
    }

}
