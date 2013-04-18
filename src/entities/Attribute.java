package entities;

public class Attribute{
	
	private String name;
	
	private Object value;
	
	@SuppressWarnings("rawtypes")
	private Class clazz;
	
	@SuppressWarnings("rawtypes")
	public Attribute(String name, Object value, Class clazz){
		this.name = name;
		this.value = value;
		this.clazz = clazz;
	}

	public String getName() {
		return name;
	}

	public Object getValue() {
		return value;
	}
	
	public String toString(){
		return name + "=" + value.toString(); 
	}
	
	@SuppressWarnings("rawtypes")
	public Class getClazz(){
		return clazz;
	}
}
