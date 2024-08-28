package com.raju.CounsellorPortal.Service;

import java.util.List;

import com.raju.CounsellorPortal.Dto.ViewEnqFilterRequest;
import com.raju.CounsellorPortal.entity.Enquiry;

public interface EnquiryService {
	
	public boolean addEnquiry(Enquiry enq, Integer CounsellorId) throws Exception;
	
	public List<Enquiry> getAllEnquiries(Integer CounsellorId);
	
	public List<Enquiry> getEnquiriesWithFilter(ViewEnqFilterRequest filterReq, Integer CounsellorId);
	
	public Enquiry getEnquiryById(Integer enqId);

}
