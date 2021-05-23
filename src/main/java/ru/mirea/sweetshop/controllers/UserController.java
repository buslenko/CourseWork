package ru.mirea.sweetshop.controllers;

import lombok.RequiredArgsConstructor;
import org.dom4j.rule.Mode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.mirea.sweetshop.models.Product;
import ru.mirea.sweetshop.models.Purchase;
import ru.mirea.sweetshop.models.User;
import ru.mirea.sweetshop.services.ProductService;
import ru.mirea.sweetshop.services.ProductTypeService;
import ru.mirea.sweetshop.services.PurchaseService;
import ru.mirea.sweetshop.services.UserService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/")
public class UserController {
    private final UserService userService;
    private final ProductService productService;
    private final ProductTypeService productTypeService;
    private final PurchaseService purchaseService;

    @Autowired
    public UserController(
                          ProductService productService,
                          ProductTypeService productTypeService,
                          UserService userService,
                          PurchaseService purchaseService) {
        this.productService = productService;
        this.productTypeService = productTypeService;
        this.userService = userService;
        this.purchaseService = purchaseService;
    }

    @GetMapping("/")
    public String index(){
        return "redirect:/home";
    }

    @GetMapping("/register")
    public String getRegister(Model model){
        model.addAttribute("user", new User());
        return "registration";
    }

    @PostMapping("/register")
    public String postRegister(@ModelAttribute("user") User userForm, Map<String, Object> model){
        if(!userService.addUser(userForm)){
            model.put("message", "User exists");
            return "/registration";
        }
        return "redirect:/login";
    }

    @GetMapping("/home")
    public String index(Authentication authentication, Model model){

        model.addAttribute("productList", productService.getAllProducts());
        model.addAttribute("productTypeList", productTypeService.getAllProductTypes());
        return "home";
    }

    @PostMapping("/cart/addProduct/{productId}")
    public String addPurchase(Authentication authentication, @PathVariable("productId")Long id, Model model){
        User user = ((User)userService.loadUserByUsername(authentication.getName()));
        if(!authentication.isAuthenticated()){
            model.addAttribute("Error", "Not login");
        } else {
            long userId = user.getId();
            Purchase purchase = purchaseService.getPurchaseByUserIdAndProductId(userId, (long) id);
            if (purchase == null){
                Purchase newPurchase = new Purchase();
                newPurchase.setUserId(userId);
                newPurchase.setProductId((long) id);
                newPurchase.setProductCount(1);
                purchaseService.addPurchase(newPurchase);
            } else{
                purchase.setProductCount(purchase.getProductCount() + 1);
                purchaseService.addPurchase(purchase);
            }
        }
        return "redirect:/home";
    }

    @GetMapping("/cart")
    public String getCart(Authentication authentication, Model model){
        User user = ((User) userService.loadUserByUsername(authentication.getName()));
        int sum = 0;
        List<Purchase> purchaseList = purchaseService.getAllPurchaseUser(user.getId());
        for (Purchase purchase: purchaseList){
            sum += productService.getProductById(purchase.getProductId()).getPrice() * purchase.getProductCount();
        }
        model.addAttribute("total", sum);
        model.addAttribute("purchaseList", purchaseList);
        model.addAttribute("productService", productService);
        return "/cart";
    }

    @GetMapping("/cart/delete/{purchaseId}")
    public String deletePurchase(@PathVariable("purchaseId") Long id){
        purchaseService.deletePurchase(id);
        return "redirect:/cart";
    }
}
