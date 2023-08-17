package bg.softuni.mobilele.web;

import bg.softuni.mobilele.models.dtos.AddOfferDTO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/offers")
public class OfferController {

    @GetMapping("/all")
    public String allOffers() {
        return "offers";
    }

    @GetMapping("/add")
    public String addOffer(Model model) {
        if (!model.containsAttribute("addOfferModel")) {
            model.addAttribute("addOfferModel", new AddOfferDTO());
        }
        return "offer-add";
    }

    @PostMapping("/add")
    public String addOffer(AddOfferDTO addOfferModel) {


        return "offer-add";
    }
}
