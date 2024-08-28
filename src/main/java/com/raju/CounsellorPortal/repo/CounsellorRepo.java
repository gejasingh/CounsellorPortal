package com.raju.CounsellorPortal.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.raju.CounsellorPortal.entity.Counsellor;

public interface CounsellorRepo extends JpaRepository<Counsellor,Integer> {

	
	//select * from counsellor_tbl where email=:email
	public Counsellor findByEmail(String Email);
	
	
	
	
	
	
	
	
	//this method will generate query like select * from counsellor_tbl where email=:email and pwd=:pwd
	public Counsellor findByEmailAndPwd(String email, String pwd);
}
