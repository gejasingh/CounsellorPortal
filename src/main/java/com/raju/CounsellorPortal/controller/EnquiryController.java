package com.raju.CounsellorPortal.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.raju.CounsellorPortal.Dto.ViewEnqFilterRequest;
import com.raju.CounsellorPortal.Service.EnquiryService;
import com.raju.CounsellorPortal.entity.Enquiry;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
public class EnquiryController {
	
	private EnquiryService enqService;

	public EnquiryController(EnquiryService enqService) {
	this.enqService = enqService;
	}
	
	@PostMapping("/filter-enq")
	public String filterEnquiries(@ModelAttribute("viewEnqFilter") ViewEnqFilterRequest viewEnqfilterRequest, HttpServletRequest req, Model model) {
	    HttpSession session = req.getSession(false);

	    if (session == null) {
	        return "redirect:/login"; // Redirect to login if session is null
	    }

	    Integer counsellorId = (Integer) session.getAttribute("counsellorId");
	    List<Enquiry> enqList = enqService.getEnquiriesWithFilter(viewEnqfilterRequest, counsellorId);

	    model.addAttribute("enquiries", enqList);

	    // Reset the filter object if you want to clear the form fields after submission
	    model.addAttribute("viewEnqFilter", new ViewEnqFilterRequest());

	    return "viewEnquiry";
	}

	
	
	
	
	
	
	@GetMapping("/viewEnquiry")
	public String getEnquiries(HttpServletRequest req, Model model) {
	
		HttpSession session=req.getSession(false);
		Integer counsellorId=(Integer) session.getAttribute("counsellorId");

		List<Enquiry> enqList=enqService.getAllEnquiries(counsellorId);
		model.addAttribute("enquiries",enqList);
		
		
		ViewEnqFilterRequest viewEnqFilterRequest=new ViewEnqFilterRequest();
		model.addAttribute("viewEnqFilter",viewEnqFilterRequest);
		
		
		
		
		
		return "viewEnquiry";
	}
	
	@GetMapping("/enquiry")
	public String addEnquiryPage(Model model) {
		//sending empty enquiry object to ui
		Enquiry enqObj=new Enquiry();
		model.addAttribute("enq",enqObj);
		return "enquiryForm";
		
	}
	@GetMapping("/editEnq")
	public String editEnquiry(@RequestParam("enqId") Integer enqId, Model model) {
	    // Sending data to the UI
	    Enquiry enquiry = enqService.getEnquiryById(enqId);
	    model.addAttribute("enq", enquiry); // Update attribute name here
	    return "enquiryForm";
	}

	@PostMapping("/addEnq")
	public String handleAddEnquiry(@ModelAttribute("enq") Enquiry enq,HttpServletRequest req,Model model) throws Exception {
	//get existing session obj so false,if true then new session
		
		HttpSession session=req.getSession(false);
		Integer counsellorId=(Integer) session.getAttribute("counsellorId");
	    
	boolean isSaved=enqService.addEnquiry(enq, counsellorId);		
	if (isSaved) {
		model.addAttribute("successmsg","Enquiry Added");
		
	}else {
		model.addAttribute("errormsg","Failed to add enquiry");
	}
	
	return "enquiryform";
	
	
	
	}
	

}