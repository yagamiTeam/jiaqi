package com.ant.jiaqi.scan;

import java.util.Map;

import com.ant.jiaqi.scan.impl.SwitchInfo;


public interface SwitchManager {
	
	public void register(String id);
	
	public void turnon(String id);
	
	public void turnoff(String id);
	
	public void turnon();
	
	public void turnoff();
	
	public SwitchInfo getSwitch(String id);
	
	public Map<String, SwitchInfo> getSwitch();
	
}
