package com.rentalsystem.swp.controllers;

import com.rentalsystem.swp.dao.ItemProfile;
import com.rentalsystem.swp.POSTResponds.ItemProfileData;
import com.rentalsystem.swp.Repositories.ItemRepository;
import com.rentalsystem.swp.security.ParserToken;
import com.rentalsystem.swp.security.TokenAuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Controller
public class AddItemController {
    @Autowired
    private final ItemRepository itemRepository;

    public AddItemController(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    @GetMapping("/menu")
    public String menu(){
        return "headerMenu";
    }


    @RequestMapping(value = "/additem", method = RequestMethod.GET)
    public String showItem(Model model, HttpServletRequest request){
        ParserToken token = TokenAuthenticationService.getAuthentication(request);
        if(token == null) return "login";

        model.addAttribute("itemProfileData", new ItemProfileData());
        return "additem";
    }

    @RequestMapping(value = "/additem", method = RequestMethod.POST)
    public String addNewItem(@ModelAttribute("itemProfileData") ItemProfileData itemProfileData, Model model, HttpServletRequest request){
        ParserToken token = TokenAuthenticationService.getAuthentication(request);
        if(token == null) return "login";

        model.addAttribute("ItemProfileData",  itemProfileData);
        ItemProfile item = new ItemProfile(itemProfileData.getName(), itemProfileData.getDescription(),
                                            itemProfileData.getTimeSlots(),itemProfileData.getCategory(), itemProfileData.getOwner());
//        ItemProfile newItem = new ItemProfile();
//        newItem.setName(item.getName());
//        newItem.setDescription(item.getDescription());
//        newItem.setTimeSlots(item.getTimeSlots());
//        newItem.setCategory(item.getCategory()).;

        itemRepository.save(item);
        return "login";
    }


    @GetMapping(path="/allitems")
    public @ResponseBody
    Iterable<ItemProfile> getAllItems() {
        // This returns a JSON or XML with the users
        return itemRepository.findAll();
    }



}
