package entities;

public class Attribute{
	
	private String name;
	
	private Object value;
	
	@SuppressWarnings("rawtypes")
	private Class clazz;
	
	private Long size;
	
	@SuppressWarnings("rawtypes")
	public Attribute(String name, Object value, Class clazz){
		this.name = name;
		this.value = value;
		this.clazz = clazz;
		if (this.value.getClass().equals(Long.class)){
			this.size = 4L;
		}
		else{
			this.size = (((String)this.value).length() * 2L);
		}
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
	
	public Long getSize(){
		return this.size;
	}
}
