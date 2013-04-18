package config;

import java.util.ArrayList;
import java.util.List;


public class ConfData {
	
	public static final int MEMORY_TOTAL = 25165824;
	
	private String filePath;
	
	private List<String> filePathList;
	
	private List<ConfAttribute> confAttributes = new ArrayList<ConfAttribute>();
	
	public ConfData setFilePath(String filePath) {
		this.filePath = filePath;
		return this;
	}
	
	public ConfData setFilePath(List<String> filePathList){
		
		for (String filePath : filePathList){
			this.filePathList.add(filePath);
		}
		
		return this;
	}
	
	public String getFilePath() {
		return filePath;
	}
	
	public ConfData addConfAttributes(ConfAttribute confAttribute){
		confAttributes.add(confAttribute);
		return this;
	}
	
	public ConfData setConfAttributes(List<ConfAttribute> confAttributes) {
		this.confAttributes = confAttributes;
		return this;
	}
	
	public List<ConfAttribute> getConfAttributes(){
		return this.confAttributes;
	}
	
	public static ConfData joinConfData(ConfData confDataR, ConfData confDataS, String nameAttributeS){
		ConfData confDataJoin = new ConfData();
		ConfAttribute confAttribute;
		int position = 0;
		for (ConfAttribute confAttributeR : confDataR.getConfAttributes()){
			confAttribute = new ConfAttribute(position, confAttributeR.getClass(), confAttributeR.getName());
			confDataJoin.addConfAttributes(confAttribute);
			position++;
		}
		for (ConfAttribute confAttributeS : confDataS.getConfAttributes()){
			if (!confAttributeS.getName().equals(nameAttributeS)){
				confAttribute = new ConfAttribute(position, confAttributeS.getClass(), confAttributeS.getName());
				confDataJoin.addConfAttributes(confAttribute);
				position++;
			}
		}
		return confDataJoin;
	}
	
	public static ConfData projectConfData(ConfData confData, ArrayList<String> listNameAttribute){
		
		ConfData confDataProject = new ConfData();
		ConfAttribute confAttribute;
		int position = 0;
		
		for (String nameAttribute : listNameAttribute){
			for (ConfAttribute confAttributeOrigem : confData.getConfAttributes()){
				if(confAttributeOrigem.getName().equals(nameAttribute)){
					confAttribute = new ConfAttribute(position, confAttributeOrigem.getClass(), confAttributeOrigem.getName());
					confDataProject.addConfAttributes(confAttribute);
					position++;
					break;
				}
			}
		}
		return confDataProject;
	}
	
}
