package operators;

import java.io.IOException;
import java.util.Collections;
import java.util.Date;

import util.FileManager;
import util.TupleComparator;
import config.ConfData;

import entities.Block;

/**
 * Classe que implementa o método de ordenação Two-Phase, Multiway Merge-Sort. Ela recebe como parâmetro
 * um bloco e uma string com o nome do atributo pelo qual o bloco será ordenado. Ex.: producaoID.
 * O retorno da classe é um novo bloco com as tuplas ordenadas.
 * 
 * 
 * @author Mariana
 *
 */

public class Sort extends Operator{
	
	private String entry;
	
	private String nameAttribute;
	
	private ConfData confDataSort;
	
	private FileManager fileReader;
	
	private FileManager fileWriter;
	
	public Sort setNameAttribute(String nameAttribute){
		this.nameAttribute = nameAttribute;
		return this;
	}

	public Sort setConfData(ConfData confDataSort){
		this.confDataSort = confDataSort;
		return this;
	}
	
	public ConfData getConfDataSort(){
		return this.confDataSort;
	}
	
	@Override
	protected void validate() {
		if (this.getEntries() == null || this.getEntries().isEmpty()){
			throw new RuntimeException("Operador de ordenação deve possuir um valor de entrada, não foi passado valor");
		}
		if (this.getEntries().size() > 1){
			throw new RuntimeException("Operador de ordenação deve possuir um valor de entrada, foi passado " + this.getEntries().size() + " valores.");
		}
	}

	@Override
	protected void setEntries() {
		this.entry = this.getEntries().get(0);
	}

	@Override
	protected ConfData open() throws IOException {
		
		fileReader = new FileManager(this.entry, this.confDataSort);
		
		fileReader.openFileReader();
		
		String resultSort = this.toString() + new Date().getTime() + ".txt";

		fileWriter = new FileManager(resultSort, this.confDataSort);
		
		fileWriter.openFileWriter();
		
		return new ConfData()
				.setFilePath(resultSort)
				.setConfAttributes(this.confDataSort.getConfAttributes());
		
	}

	@Override
	protected boolean next() throws IOException {
		
		Block block = fileReader.getNextBlock(10); 
        
    	if (block.getTuples().isEmpty()){
			return false;
		}
    	
    	Collections.sort(block.getTuples(), new TupleComparator(nameAttribute));
    	
    	fileWriter.writeFile(block);
    	
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
	
	public String toString(){
		return "SORT";
	}
    
}

