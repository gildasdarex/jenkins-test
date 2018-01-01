package com.pej.controllers;

import com.pej.domains.*;
import com.pej.pojo.OdkAgent;
import com.pej.repository.AgentRepository;
import com.pej.repository.AntenneRepository;
import com.pej.repository.TypeformationRepository;
import com.pej.services.AgentService;
import com.pej.services.NotificationService;
import com.poiji.internal.Poiji;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

@Controller
public class StatistiqueController {
	@Autowired
	private TypeformationRepository typeformationRepository;

	@Value("${tmp.location}")
	String tempLocation;

	@GetMapping("/pej/statistiques")
    String index(Model model) {

		List<Typeformation> typeformations = (List<Typeformation>) typeformationRepository.findAll();

		model.addAttribute("typeFormations", typeformations);

		return "statistiques_index";
    }


	@PostMapping("/pej/statistiques")
	String getStatistique(HttpServletRequest request, Model model) {

		List<Typeformation> typeformations = (List<Typeformation>) typeformationRepository.findAll();

		System.out.println("where_formation " + request.getParameter("where_formation"));

		model.addAttribute("typeFormations", typeformations);

		return "redirect:/pej/statistiques";
	}
	

}
