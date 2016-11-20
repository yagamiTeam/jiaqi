package com.ant.jiaqi.scan;

import java.util.List;

public interface ScanDos {
	
	public List<Object> queryForList(int startRow, int offset);
	
	public boolean lockRecord(Object record);
	
	public boolean unlockRecord(Object record);
	
	public boolean resetRecord(Object record);
}
