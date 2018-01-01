package com.pej.controllers;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import com.pej.domains.*;
import com.pej.repository.*;
import com.pej.services.NotificationService;
import com.pej.services.UtilisateurService;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
public class UsersManagementController {
	Logger logger = LogManager.getLogger(UsersManagementController.class);

	@Autowired private UsersRepository usersRepository;
	@Autowired private UserRoleRepository userRoleRepository;
	@Autowired private UtilisateurService utilisateurService;
	@Autowired private FormateurRepository formateurRepository;
	@Autowired private BCryptPasswordEncoder bCryptPasswordEncoder;
	@Autowired private NotificationService notifyService;



	@GetMapping("/pej/usermamagement")
    String index(Model model,@ModelAttribute("objUser") Utilisateur objUsers) {
    	List<Utilisateur> users = (List<Utilisateur>) usersRepository.findAll();

    	model.addAttribute("users", users);

        return "usermanagement";
    }

	
	
	@GetMapping("/pej/users")
	public String createUser(@ModelAttribute("ObjUsers") Utilisateur ObjUsers){
		return "frmUsers";
	}


	@GetMapping("/pej/users/{id}")
	public String editUsers(@PathVariable Integer id, ModelMap model){
		Utilisateur objUsers=usersRepository.findOne(id);

		UsersRole usersRole = userRoleRepository.findByUtilisateurUsername(objUsers.getUsername());

		objUsers.setRoleName(usersRole.getRoles().getName());

		model.addAttribute("ObjUsers", objUsers);
		return "frmEditUsers";
	}


	@GetMapping("/pej/profile")
	public String setProfile(Model model) {
		User principal = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Utilisateur user = usersRepository.findByUsername(principal.getUsername());
		user.setPassword("");
		model.addAttribute("utilsateur", user);
		return "setprofile";
	}


	@PostMapping("/pej/newProfile")
	public String newProfile(@ModelAttribute(value = "utilisateur") Utilisateur utilisateur) {
		User principal = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Formateur formateur = formateurRepository.findOneByUsername(principal.getUsername());

		String oldPass = principal.getPassword();

		try{
			if (utilisateur.getPassword().equals("") || utilisateur.getPassword() == null)
				utilisateur.setPassword(bCryptPasswordEncoder.encode(oldPass));
			else
				utilisateur.setPassword(bCryptPasswordEncoder.encode(utilisateur.getPassword()));

			usersRepository.save(utilisateur);


			if(formateur != null){
				formateur.setUsername(utilisateur.getUsername());
				formateurRepository.save(formateur);
			}
		}
		catch (Exception ex){
			notifyService.addErrorMessage("ECHEC DE L'ENREGSTREMENT. VERFIER S'IL N'EXISTE PAS D'ENREGISTREMENT AVEC LE MEME USERNAME OU EMAIL");
		}


		return "redirect:/pej/logout";
	}


	@PostMapping("/pej/users")
	public String saveagents(@ModelAttribute(value = "ObjUsers") Utilisateur ObjUsers , BindingResult result) {
		boolean success = true;

		if (result.hasErrors()) {
			List<ObjectError> errors = result.getAllErrors();
			for (ObjectError error : errors) {
				logger.debug("error to create new user " + error.toString());
			}
		}
		else{
			if (ObjUsers.getIdusers()!=null && ObjUsers.getIdusers().intValue() >0 )
				success = utilisateurService.editUtilisateur(ObjUsers);
			else
				success = utilisateurService.createUtilisateur(ObjUsers);
		}

		if(!success)
			notifyService.addErrorMessage("ECHEC DE L'ENREGSTREMENT. VERFIER S'IL N'EXISTE PAS D'ENREGISTREMENT AVEC LE MEME USERNAME OU EMAIL");


		return "redirect:/pej/usermamagement";
	}

}
