package com.cg.hms.app.service;

import com.cg.hms.app.model.NurseModel;

import java.util.List;
 
public interface NurseService {
	
    public NurseModel createNurse(NurseModel nurseModel);
    
    public List<NurseModel> getAllNurses();
    
    public NurseModel getNurseById(int id);
    
    public NurseModel updateNurse(int id, NurseModel nurseModel);
    
    public String deleteNurse(int id);
    
    public List<NurseModel> getNursesByShift(String shiftType);    
 
    public List<NurseModel> getAvailableNurses();
 
	public List<NurseModel> getNurseByName(String name);
	public int getTotalNurses();

}
 
