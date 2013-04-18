package operators;

import java.io.IOException;
import java.util.Date;

import config.ConfData;

import util.FileManager;
import entities.Attribute;
import entities.Block;
import entities.Tuple;

public class Selection extends Operator{
	
	private String entry;
	
	private String nameAttribute;
	
	private String valueAttribute;
	
	private FileManager fileReader;
	
	private FileManager fileWriter;
	
	private ConfData confData;
	
	public Selection setNameAttribute(String nameAttribute) {
		this.nameAttribute = nameAttribute;
		return this;
	}

	public Selection setValueAttribute(String valueAttribute) {
		this.valueAttribute = valueAttribute;
		return this;
	}

	public Selection setConfData(ConfData confData) {
		this.confData = confData;
		return this;
	}
	
	@Override
	public void validate() {
		if (this.getEntries() == null || this.getEntries().isEmpty()){
			throw new RuntimeException("Operador de sele��o deve possuir um valor de entrada, n�o foi passado valor");
		}
		if (this.getEntries().size() > 1){
			throw new RuntimeException("Operador de sele��o deve possuir um valor de entrada, foi passado " + this.getEntries().size() + " valores.");
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
		
		String resultSelection = this.toString() + new Date().getTime() + ".txt";

		fileWriter = new FileManager(resultSelection, this.confData);
		
		fileWriter.openFileWriter();
		
		return resultSelection;
	}	

	@Override
	protected boolean next() throws IOException {
		
		Block block = fileReader.getNextBlock(100); 
		
		if (block.getTuples().isEmpty()){
			return false;
		}
		
		Block blockResult = new Block(); 
		Attribute attribute;
		
		for (Tuple tuple : block.getTuples()){
			
			attribute = tuple.getAttributeByName(this.nameAttribute);
			
			if (attribute == null){
				throw new RuntimeException("Rela��o '" + this.entry + "' n�o possui atributo '" + this.nameAttribute + "'.");
			}
			
			boolean tupleSelected = attribute.getValue().toString().equals(this.valueAttribute);
			
			if (tupleSelected){
				blockResult.addTuple(tuple);
			}
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
		return "SELECTION";
	}
}
