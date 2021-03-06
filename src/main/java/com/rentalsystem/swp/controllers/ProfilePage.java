package com.rentalsystem.swp.controllers;

import com.rentalsystem.swp.POSTResponds.LoginData;
import com.rentalsystem.swp.Repositories.ItemRepository;
import com.rentalsystem.swp.Repositories.UserRepository;
import com.rentalsystem.swp.models.ItemProfile;
import com.rentalsystem.swp.models.UserProfile;
import com.rentalsystem.swp.security.UserPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Optional;



@Controller
public class ProfilePage {

    private UserRepository userRepository;
    private ItemRepository itemRepository;

    @Autowired
    public ProfilePage(UserRepository userRepository, ItemRepository itemRepository) {

        this.userRepository = userRepository;
        this.itemRepository = itemRepository;
    }


    @RequestMapping(value = "/profile", method = RequestMethod.GET)
    public String showProfile(Model model, HttpSession session) {
        String currentUser = (String)session.getAttribute("currentUser");
        if (currentUser == null) {
            return "redirect:/login";
        }
        else {
            model.addAttribute("auth", "true");
        }
        UserProfile userProfile = userRepository.findByEmail(currentUser).get();
        model.addAttribute("userProfile", userProfile);

        List<ItemProfile> items = itemRepository.findAllByOwnerIs(userProfile.getEmail());
        Integer id = 0;

        model.addAttribute("items", items);
        model.addAttribute("id", id);

        return "profile";

    }

    @RequestMapping(value = "/profile", method = RequestMethod.POST)
    public String showAd(@ModelAttribute("id") Integer id, Model model, HttpSession session) {
        model.addAttribute("id", id);
        String currentUser = (String)session.getAttribute("currentUser");
        if (currentUser == null) {
            return "redirect:/login";
        }
        else {
            model.addAttribute("auth", "true");
        }
        ItemProfile itemProfile = itemRepository.getOne(id);
        model.addAttribute("itemProfile", itemProfile);
        return "profile";
    }
}