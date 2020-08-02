package com.generatorimprez.GEN.Controller;

import com.generatorimprez.GEN.Model.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import javax.servlet.http.HttpServletRequest;

@Controller
@SessionAttributes("offer")
public class PackageDealController {

    @GetMapping("/")
    public String getHome(Model model, User user, PackageDeal packageDeal) {
        model.addAttribute("block", 0);
        model.addAttribute("offer", new FinalOffer(packageDeal));
        return "index";
    }

    @GetMapping("/zorganizuj-impreze")
    public String getPakiet(@ModelAttribute("offer") FinalOffer finalOffer, Model model, PackageDeal packageDeal, SubServiceList subServiceList, HttpServletRequest request) {
        Service.getServices();
        model.addAttribute("offer", new FinalOffer(packageDeal));
        model.addAttribute("subServiceNames", new SubServiceList());
        model.addAttribute("multi", Service.multi);
        model.addAttribute("one", Service.one);
        return "oferta";
    }

    @GetMapping("/impreza-prawie-gotowa")
    public String getPakiet2(@ModelAttribute("offer") FinalOffer finalOffer, Model model) {

        return "oferta2";
    }


    @GetMapping("/podsumowanie")
    public String getOferta(@ModelAttribute("offer") FinalOffer finalOffer, SubServiceList subServiceList, Model model) {
        model.addAttribute("descriptions", finalOffer.getDescriptions());
        model.addAttribute("prices", finalOffer.getPrices());
        return "pakiet";
    }


    @PostMapping("/zorganizuj-impreze")
    public String newPackageDeal(@ModelAttribute("offer") FinalOffer finalOffer, SubServiceList subServiceList, User user, Model model, HttpServletRequest request) {
        String[] tab = subServiceList.getSubServiceName().split(",");
        for (String s : tab) finalOffer.addSubService(SubService.getSubServiceId(s));
        if (request.getUserPrincipal() != null) {
            finalOffer.setType(User.isAdmin(request.getUserPrincipal().getName()));
            finalOffer.setIdUser(User.chckIdUser(request.getUserPrincipal().getName()));
            finalOffer.savePackageDeal();
        } else finalOffer.setType("private");
        return "redirect:/impreza-prawie-gotowa";
    }

    @PostMapping("/impreza-prawie-gotowa")
    public String newOffer(@ModelAttribute("offer") FinalOffer finalOffer, @ModelAttribute("pakiet") PackageDeal packageDeal, User user, Model model) {

        return "redirect:/podsumowanie";
    }

    @PostMapping("/konfiguracja")
    public String getPackageDeal(@ModelAttribute("offer") FinalOffer finalOffer, Model model, User user, PackageDeal packageDeal) {
        finalOffer.getOfferById();
        return "redirect:/impreza-prawie-gotowa";
    }
}
