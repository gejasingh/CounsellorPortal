package com.raju.CounsellorPortal.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.raju.CounsellorPortal.Dto.DashboardResponse;
import com.raju.CounsellorPortal.Service.CounsellorService;
import com.raju.CounsellorPortal.entity.Counsellor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
public class CounsellorController {
	
	private CounsellorService counsellorService;
	
	public CounsellorController(CounsellorService counsellorService) {
	this.counsellorService=counsellorService;	
	}

	@GetMapping("/")
	public String index(Model model) {
		Counsellor cobj=new Counsellor();
		//sending data from controller to ui
		model.addAttribute("counsellor", cobj);
		
		
		
		return "index";
		}
	
	@PostMapping("/login")
	public String login(Counsellor counsellor,HttpServletRequest request,Model model){
	Counsellor c=counsellorService.login(counsellor.getEmail(),counsellor.getPwd());
 if(c ==null) {
	 model.addAttribute("errormsg","Invalid Credentials");
		return "index";
	}else {
		//valid login, store counsellorId in session for future purpose.
		HttpSession session=request.getSession(true);
		session.setAttribute("counsellorId", c.getCounsellorId());
		
		DashboardResponse dbobj=counsellorService.getDashboardInfo(c.getCounsellorId());
	model.addAttribute("dashboardInfo",dbobj);
	return "dashboard";
	}
	}
	
	@GetMapping("/dashboard")
	public String displayDashboard(HttpServletRequest req,Model model) {
	
		HttpSession session=req.getSession(false);
		Integer counsellorId=(Integer) session.getAttribute("counsellorId");
		
		DashboardResponse dbobj=counsellorService.getDashboardInfo(counsellorId);
		model.addAttribute("dashboardInfo",dbobj);
		return "dashboard";
		}
	
	
	
	@GetMapping("/register")
	public String registerPage(Model model) {
		Counsellor cobj=new Counsellor();
		model.addAttribute("counsellor",cobj);
		return "register";
	}
	@PostMapping("/register")
	public String handleRegistration(Counsellor counsellor,Model model) {
		Counsellor byEmail=counsellorService.findByEmail(counsellor.getEmail());
		if (byEmail !=null) {
			model.addAttribute("errormsg","Duplicate Email");
			return "register";
		}
		
		
		boolean isRegistered=counsellorService.register(counsellor);
		if (isRegistered) {
		
	model.addAttribute("successmsg","Registration Success..!!");
	}else {
		model.addAttribute("errormsg","Registration Failed");
	}
	return "register";
	}
	
	
	
	
	
	
	
	@GetMapping("/logout")
	public String logout(HttpServletRequest req) {
		//get existing session and invalidate it
		HttpSession session=req.getSession(false);
		 if (session != null) {
		        session.invalidate(); // Invalidate the session if it exists
		    }
		//redirect to login page
		return "redirect:/";
	
	}
}


