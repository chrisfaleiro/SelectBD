package operators;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import util.FileManager;
import config.ConfData;
import entities.Block;
import entities.Tuple;
import static entities.Block.BLOCK_SIZE;

public class Projection extends Operator{

	private String entry;
	
	private ArrayList<String> listNameAttribute = new ArrayList<String>();
	
	private FileManager fileReader;
	
	private FileManager fileWriter;
	
	private ConfData confData;
	
	private ConfData confDataProjection;
	
	public Projection addNameAttribute(String listNameAttribute) {
		this.listNameAttribute.add(listNameAttribute);
		return this;
	}
	
	public Projection setConfData(ConfData confData) {
		this.confData = confData;
		return this;
	}

	public ConfData getConfDataProject(){
		return this.confDataProjection;
	}
	
	@Override
	protected void validate() {
		if (this.getEntries() == null || this.getEntries().isEmpty()){
			throw new RuntimeException("Operador de proje��o deve possuir um valor de entrada, n�o foi passado valor");
		}
		if (this.getEntries().size() > 1){
			throw new RuntimeException("Operador de proje��o deve possuir um valor de entrada, foi passado " + this.getEntries().size() + " valores.");
		}		
	}

	@Override
	protected void setEntries() {
		this.entry = this.getEntries().get(0);		
	}

	@Override
	protected ConfData open() throws IOException {
		
		fileReader = new FileManager(this.entry, this.confData);
		
		fileReader.openFileReader();
		
		String resultFilePath = this.toString() + new Date().getTime() + ".txt";		

		this.confDataProjection = ConfData.projectConfData(this.confData, this.listNameAttribute);

		fileWriter = new FileManager(resultFilePath, this.confDataProjection);
		
		fileWriter.openFileWriter();
				
		return new ConfData()
				.setFilePath(resultFilePath)
				.setConfAttributes(this.confDataProjection.getConfAttributes());
	}

	@Override
	protected boolean next() throws IOException {
		
		Block block = fileReader.getNextBlock(BLOCK_SIZE); 
		
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
