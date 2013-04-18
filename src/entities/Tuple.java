package entities;

import java.util.ArrayList;
import java.util.List;

import util.FileManager;

import config.ConfAttribute;
import config.ConfData;

public class Tuple{
	
	private List<Attribute> attributes = new ArrayList<Attribute>();
	
	private Long size = 0L;
	
	public Tuple addAttribute(Attribute attribute){
		attributes.add(attribute);
		size += attribute.getSize();
		return this;
	}
	
	public List<Attribute> getAttributes(){
		return this.attributes;
	}
	
	public Long getSize() {
		return size;
	}

	public Attribute getAttributeByName(String nameAtribute){
		for (Attribute attribute : attributes){
			if(attribute.getName().equals(nameAtribute)){
				return attribute;
			}
		}
		return null;
	}
	
	public static Tuple joinTuples(Tuple tupleR, Tuple tupleS, Attribute attributeJoin){
		
		Tuple tuple = new Tuple();
		
		for (Attribute attribute : tupleR.getAttributes()){
			tuple.addAttribute(attribute);
		}	
		
		for (Attribute attribute : tupleS.getAttributes()){
			if (!attribute.getName().equals(attributeJoin.getName())){
				tuple.addAttribute(attribute);				
			}
		}
		
		return tuple;
	}
	
	public static Tuple projectTuple(Tuple tuple, ArrayList<String> listNameAttribute){
		
		Tuple tupleProject = new Tuple();
		
		@SuppressWarnings("unused")
		int position = 0;
		
		Attribute attribute;
		
		for (String nameAttribute : listNameAttribute){
			
			attribute = tuple.getAttributeByName(nameAttribute);
			
			tupleProject.addAttribute(attribute);	

			position++;
			
		}
		
		return tupleProject;
	}
	
	public Tuple generateTuple(String line, ConfData confData){
		
		if (line == null || line.isEmpty()){
			return null;
		}
		String[] attributeList = line.split(FileManager.SEPARATOR);
		
		for (ConfAttribute confAttribute : confData.getConfAttributes()){
			
			@SuppressWarnings("rawtypes")
			Class clazz = confAttribute.getClazz();
			
			String nome = confAttribute.getName();
			
			Attribute attribute = new Attribute(nome, attributeList[confAttribute.getPosition()], clazz);

			this.addAttribute(attribute);
		}
		
		return this;
		
	}
	
	public String generateLine(ConfData confData){
		
		String str = "";
		
		for (ConfAttribute confAttribute : confData.getConfAttributes()){
			
			String nome = confAttribute.getName(); 
			
			Attribute attribute = this.attributes.get(confAttribute.getPosition());
			
			if (attribute == null){
				throw new RuntimeException("Relação não possui atributo.");
			}
				
			if (nome.equals(attribute.getName())){
				if(str.equals("")){
					str += attribute.getValue();
				}
				else{
					str += FileManager.SEPARATOR + attribute.getValue();
				}
			}
		}
		
		return str;
	}
	
}