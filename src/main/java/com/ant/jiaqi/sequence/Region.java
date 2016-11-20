package com.ant.jiaqi.sequence;

public final class Region {
	private long start;
	
	private long end;

	public Region(long start, long end) {
		if(start > end) {
			throw new IllegalArgumentException("region[" + start + "," + end + "]");
		}
		this.start = start;
		this.end = end;
	}
	
	public long getStart() {
		return start;
	}

	public long getEnd() {
		return end;
	}	
	
	public long getSize() {
		return this.end - this.start + 1;
	}
	
	public boolean contains(long number) {
		return number >= this.start && number <= this.end;
	}
	
	@Override
	public String toString() {
		return "[" + start + "," + end + "]";
	}
}
