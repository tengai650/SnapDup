package data;

public class DupData 
{
	private String[] devices;
	private String[] files;
	private long size;
	private int[] offsets;
	
	public DupData(final String[] devices, final String[] files, final long size, final int[] offsets)
	{
		this.devices = devices;
		this.files = files;
		this.size = size;
		this.offsets = offsets;
	}
	
	public String getDevice1() { return devices[0]; }
	public String getDevice2() { return devices[1]; }
	
	public String getFile1() { return files[0]; }
	public String getFile2() { return files[1]; }
	
	public int getStartOffset() { return offsets[0]; }
	public int getEndOffset() { return offsets[1]; }
	
	public long getSize() { return size; }
}
