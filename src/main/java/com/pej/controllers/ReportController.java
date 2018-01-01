package com.pej.controllers;

import com.pej.domains.Agent;
import com.pej.domains.Antenne;
import com.pej.domains.Candidat;
import com.pej.pojo.ResumeCandidat;
import com.pej.repository.AgentRepository;
import com.pej.repository.AntenneRepository;
import com.pej.repository.CandidatRepository;
import com.pej.services.NotificationService;
import com.pej.services.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class ReportController {
	@Autowired private ReportService reportService;
	@Autowired private CandidatRepository candidatRepository;

	@GetMapping("/pej/report/formations")
    String getFormationFollowByCandidat(Model model, @RequestParam(value = "idcandidat", required = true) Integer idcandidat) {
		List<ResumeCandidat>  resumeCandidats = reportService.getFormationResume(idcandidat);
		Candidat candidat = candidatRepository.findOne(idcandidat);

    	model.addAttribute("resumeCandidats", resumeCandidats);
		model.addAttribute("candidatIdentite", candidat.getIdentite());

        return "resumeCandidats";
    }
	

}
