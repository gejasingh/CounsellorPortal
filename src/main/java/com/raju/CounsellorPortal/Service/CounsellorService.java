package com.raju.CounsellorPortal.Service;

import com.raju.CounsellorPortal.Dto.DashboardResponse;
import com.raju.CounsellorPortal.entity.Counsellor;

public interface CounsellorService {
	
	public Counsellor findByEmail(String email);
	public Counsellor login(String email, String pwd);
	public boolean register(Counsellor counsellor); 
	public DashboardResponse getDashboardInfo(Integer counsellorId);
	
	

}
