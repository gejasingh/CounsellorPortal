package com.raju.CounsellorPortal.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.raju.CounsellorPortal.entity.Enquiry;

public interface EnquiryRepo extends JpaRepository<Enquiry,Integer>{

	@Query(value="select * from enq_tbl where counsellor_id=:counsellorId", nativeQuery=true)
public List<Enquiry> getEnquiriesByCounsellorId(Integer counsellorId); 
}
