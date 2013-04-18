package config;

public class ConfAttribute {
	
	private int position;
	
	@SuppressWarnings("rawtypes")
	private Class clazz;
	
	private String name;
	
	@SuppressWarnings("rawtypes")
	public ConfAttribute(int position, Class clazz, String name){		
		this.position = position;
		this.clazz = clazz;
		this.name = name;
		
	}

	public int getPosition() {
		return position;
	}

	@SuppressWarnings("rawtypes")
	public Class getClazz() {
		return clazz;
	}

	public String getName() {
		return name;
	}

	public void setPosition(int position) {
		this.position = position;
	}
	
}
