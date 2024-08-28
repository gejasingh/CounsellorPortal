package com.raju.CounsellorPortal.Service;

import java.util.List;

import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import com.raju.CounsellorPortal.Dto.ViewEnqFilterRequest;
import com.raju.CounsellorPortal.entity.Counsellor;
import com.raju.CounsellorPortal.entity.Enquiry;
import com.raju.CounsellorPortal.repo.CounsellorRepo;
import com.raju.CounsellorPortal.repo.EnquiryRepo;

import io.micrometer.common.util.StringUtils;

@Service
public class EnquiryServiceImpl implements EnquiryService {
 
	
	private EnquiryRepo enqRepo;
	private CounsellorRepo counsellorRepo;
	
	public EnquiryServiceImpl(EnquiryRepo enqRepo,CounsellorRepo counsellorrepo) {
		this.enqRepo = enqRepo;
		this.counsellorRepo=counsellorrepo;
	
	}

	@Override
	public boolean addEnquiry(Enquiry enq, Integer counsellorId) throws Exception {
		Counsellor counsellor=counsellorRepo.findById(counsellorId).orElse(null);
		
		if(counsellor==null) {
			throw new Exception("No Counsellor Found");
		}
		
		enq.setCounsellor(counsellor);
		
		Enquiry save=enqRepo.save(enq);
		if(save.getEnqId() !=null) {
			return true;
			}
		 return false;
	}

	@Override
	public List<Enquiry> getAllEnquiries(Integer counsellorId) {
		return enqRepo.getEnquiriesByCounsellorId(counsellorId);
	}

	@Override
	public List<Enquiry> getEnquiriesWithFilter(ViewEnqFilterRequest filterReq, Integer counsellorId) {
	
		//QBE implements Dynamic Query preparation
				Enquiry enq= new Enquiry();
		
		if (StringUtils.isNotEmpty(filterReq.getClassMode())) {
		enq.setClassMode(filterReq.getClassMode());	
		}
		
		if (StringUtils.isNotEmpty(filterReq.getCourseName())) {
			enq.setCourseName(filterReq.getCourseName());	
			}
			
		if (StringUtils.isNotEmpty(filterReq.getEnqStatus())) {
			enq.setEnqStatus(filterReq.getEnqStatus());	
			}
		
		Counsellor c=counsellorRepo.findById(counsellorId).orElse(null);
		enq.setCounsellor(c);
		
		Example<Enquiry> of=Example.of(enq);
		
		List<Enquiry> enqList=enqRepo.findAll(of);
		
		return enqList;
	}
			
		
	

	@Override
	public Enquiry getEnquiryById(Integer enqId) {
		
	return enqRepo.findById(enqId).orElse(null) ;
	}

}
