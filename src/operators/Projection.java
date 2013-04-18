package operators;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import util.FileManager;
import config.ConfData;
import entities.Block;
import entities.Tuple;

public class Projection extends Operator{

	private String entry;
	
	private ArrayList<String> listNameAttribute = new ArrayList<String>();
	
	private FileManager fileReader;
	
	private FileManager fileWriter;
	
	private ConfData confData;
	
	private ConfData confDataProject;
	
	public Projection addNameAttribute(String listNameAttribute) {
		this.listNameAttribute.add(listNameAttribute);
		return this;
	}
	
	public Projection setConfData(ConfData confData) {
		this.confData = confData;
		return this;
	}

	public ConfData getConfDataProject(){
		return this.confDataProject;
	}
	
	@Override
	protected void validate() {
		if (this.getEntries() == null || this.getEntries().isEmpty()){
			throw new RuntimeException("Operador de projeção deve possuir um valor de entrada, não foi passado valor");
		}
		if (this.getEntries().size() > 1){
			throw new RuntimeException("Operador de projeção deve possuir um valor de entrada, foi passado " + this.getEntries().size() + " valores.");
		}		
	}

	@Override
	protected void setEntries() {
		this.entry = this.getEntries().get(0);		
	}

	@Override
	protected String open() throws IOException {
		
		fileReader = new FileManager(this.entry, this.confData);
		
		fileReader.openFileReader();
		
		String resultProjection = this.toString() + new Date().getTime() + ".txt";		

		this.confDataProject = ConfData.projectConfData(this.confData, this.listNameAttribute);

		fileWriter = new FileManager(resultProjection, this.confDataProject);
		
		fileWriter.openFileWriter();
				
		return resultProjection;
	}

	@Override
	protected boolean next() throws IOException {
		
		Block block = fileReader.getNextBlock(100); 
		
		if (block.getTuples().isEmpty()){
			return false;
		}
		
		Block blockResult = new Block(); 
		Tuple tupleProject;
		
		for (Tuple tuple : block.getTuples()){
			
			tupleProject = Tuple.projectTuple(tuple, this.listNameAttribute);				
			blockResult.addTuple(tupleProject);			
		}

		fileWriter.writeFile(blockResult);
		
		return true;
	}

	@Override
	protected void close() {
		try {
			
			fileWriter.closeFileWriter();
			fileReader.closeFileReader();
			
		} catch (IOException e) {
			e.printStackTrace();
		}		
	}
	
	@Override
	public String toString() {
		return "PROJECTION";
	}

}
