package com.generatorimprez.GEN.Controller;

import com.generatorimprez.GEN.Model.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

@Controller
@SessionAttributes("pakiet")
public class PackageDealController {

    @GetMapping("/zorganizuj-impreze")
    public String getOferta(Model model, PackageDeal packageDeal, SubServiceList subServiceList) {
        Service.getServices();
        model.addAttribute("pakiet", new PackageDeal());
        model.addAttribute("subServiceNames", new SubServiceList());
        model.addAttribute("multi", Service.multi);
        model.addAttribute("one", Service.one);
        return "oferta";
    }

    @GetMapping("/konfigurator")
    public String getPakiet(Model model, PackageDeal packageDeal, SubServiceList subServiceList) {
        packageDeal = (PackageDeal) model.getAttribute("pakiet");
        return "pakiet";
    }

    @PostMapping("/zorganizuj-impreze")
    public String newPackageDeal(@ModelAttribute("pakiet") PackageDeal packageDeal, SubServiceList subServiceList,  User user, Model model) {
        String [] tab = subServiceList.getSubServiceName().split(",");
        for (String s : tab) packageDeal.addSubService(SubService.getSubServiceId(s));
         packageDeal.setType(user.isAdmin());
        model.addAttribute("pakiet", packageDeal);
        return "redirect:/konfigurator";
    }

    @PostMapping("/impreza-prawie-gotowa")

    public String newOffer(@ModelAttribute("pakiet") PackageDeal packageDeal, User user, Model model) {
        return "index";
    }
}
