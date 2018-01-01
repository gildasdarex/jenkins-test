package com.pej.controllers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.time.LocalDateTime;
import com.pej.domains.Departement;
import com.pej.domains.LoginForm;
import com.pej.domains.UsersRole;
import com.pej.domains.Utilisateur;
import com.pej.domains.Commune;
import com.pej.domains.Agent;
import com.pej.domains.Antenne;
import com.pej.domains.Arrondissement;
import com.pej.repository.*;
import com.pej.services.NotificationService;
import com.pej.services.SecurityService;
import com.pej.services.UserService;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@Controller
public class LoginController {
	@Autowired
    private UserService userService;

    @Autowired
    private SecurityService securityService;
	@Autowired private DepartementRepository departementRepository;
	@Autowired private UsersRepository userRepository;
	@Autowired private NotificationService notifyService;
	BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
	
	//@GetMapping("/pej/login")
	@RequestMapping(value = {"/pej/login", "/pej/"}, method = RequestMethod.GET)
    String index(Model model, String logout,HttpServletRequest  request) {  
		//String pass=passwordEncoder.encode("12");
		//System.out.println("Mot de passe= "+pass);
		model.addAttribute("loginForm", new LoginForm());
		//model.addAttribute("password", "");
		return "login";
		
		/*
		if (error != null)
	            model.addAttribute("error", "Your username and password is invalid.");
	       

	        if (logout != null)
	            model.addAttribute("message", "You have been logged out successfully.");
	        if(userService.authenticate(loginForm.getUsername(),loginForm.getPassword())==false) {
	        	
	            notifyService.addErrorMessage("Echec authentification");
	           
	            request.setAttribute("username", "dfggff");
	            System.out.println("SHOWING LOGIN PAGE");
	            return "login";
	        }
	        else {
	        	
	        	Utilisateur user = userService.getAuthenticatedUser(loginForm.getUsername(),loginForm.getPassword());
	        	System.out.println("Dimension "+user.getUsersRoles().size());
	        	for (UsersRole role : user.getUsersRoles()) {
						System.out.println("ROLE"+role.getRoles().getName());
				}
	        	System.out.println("redirect to agent page");
	            return "redirect:/pej/agents";
	        }*/
    }
	
	@PostMapping("/pej/auth")
	//@PostMapping("/pej/login")
    String authPej(@ModelAttribute(value="loginForm")  LoginForm loginForm, BindingResult result,Model model) {
		System.out.println("authPej start ok");
		/*if(userService.authenticate(loginForm.getUsername(),loginForm.getPassword())==false) {
           notifyService.addErrorMessage("Echec d'authentification");

           return "home";
       }
        else {
           return "home";
       }*/
		return "";
    }


    @GetMapping("/pej/403")
        //@PostMapping("/pej/login")
    String deniedPage() {
        //notifyService.addErrorMessage("Vous n'avez pas les droits necessaires. Connectez-vous en tant que administrateur");
        return "403";
    }


    @RequestMapping(value="pej/logout", method = RequestMethod.GET)
    public String logoutPage (HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null){
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        return "redirect:/login?logout";//You can redirect wherever you want, but generally it's a good practice to show login screen again.
    }

    public static UserDetails currentUserDetails(){
    	System.out.println("currentUserDetails Ok");
        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication authentication = securityContext.getAuthentication();
        if (authentication != null) {
            Object principal = authentication.getPrincipal();
            return principal instanceof UserDetails ? (UserDetails) principal : null;
        }
        return null;
    }
	@RequestMapping(value="/pej/login/connect", method=RequestMethod.GET)
    String addCandidat(Model model) {
		return "redirect:/pej/candidats";
    }

}
