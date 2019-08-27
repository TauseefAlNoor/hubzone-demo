package com.hubzone.dao;

/*
 * This class is for interface of State Service
 * 
 * */
import java.util.List;

import com.hubzone.model.States;

public interface StatesService {
	public void  saveState(States state);
	public List<States> getStateList();
}
