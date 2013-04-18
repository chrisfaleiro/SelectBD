package operators;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import util.FileManager;
import util.TupleComparator;
import config.ConfData;
import config.Data;

import entities.Block;

/**
 * Classe que implementa o método de ordenação Two-Phase, Multiway Merge-Sort. Ela recebe como parâmetro
 * um bloco e uma string com o nome do atributo pelo qual o bloco será ordenado. Ex.: producaoID.
 * O retorno da classe é um novo bloco com as tuplas ordenadas.
 * 
 * 
 * @author Mariana Azevedo
 * 
 */

public class TwoPhaseMultiwayMergeSort extends Operator{
	
	private String entry;
	
	private String nameAttribute;
	
	private String resultSort;
	
	private ConfData confDataSort;
	
	private FileManager fileReader;
	
	private FileManager fileWriter;
	
	private List<String> resultFirstStepTPMMS;
	
	private static final int SIZE_BLOCK = 24000;
	
	//private static final int SIZE_MEMORY = 24000;
	
	public TwoPhaseMultiwayMergeSort setNameAttribute(String nameAttribute){
		this.nameAttribute = nameAttribute;
		return this;
	}

	public TwoPhaseMultiwayMergeSort setConfData(ConfData confDataSort){
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
		
		resultSort = this.toString() + new Date().getTime() + ".txt";
		
		fileWriter = new FileManager(resultSort, this.confDataSort);
		
		fileWriter.openFileWriter();
		
		resultFirstStepTPMMS = new ArrayList<String>();
		
		return new ConfData()
			.setFilePath(resultSort)
			.setConfAttributes(this.confDataSort.getConfAttributes());
		
	}

	@Override
	protected boolean next() throws IOException {
		
		String resultSortRun = this.toString() + "RUN" + new Date().getTime() + ".txt";
		
		FileManager runFileWriter = new FileManager(resultSortRun, this.confDataSort);
		
		runFileWriter.openFileWriter();
		
		Block block = fileReader.getNextBlock(SIZE_BLOCK); 
    
		if (block.getTuples().isEmpty()){
			return false;
		}
		    	
    	Collections.sort(block.getTuples(), new TupleComparator(nameAttribute));
    	
    	runFileWriter.writeFile(block);
    	runFileWriter.closeFileWriter();
    	
    	resultFirstStepTPMMS.add(resultSortRun);
    	
        return true;
	}

	@Override
	protected void close() {
		try {
			
			fileWriter.closeFileWriter();
			fileReader.closeFileReader();
			
			MergeSort mergeSort = new MergeSort(resultFirstStepTPMMS.size());
			mergeSort.setConfData(Data.PESSOA.getConf());
			mergeSort.setNameAttribute("pessoaID");
			mergeSort.setFileSort(resultSort);
			
			for (int i=0; i<resultFirstStepTPMMS.size(); i++){
				mergeSort.addEntry(FileManager.TEMP_FOLDER_PATH + resultFirstStepTPMMS.get(i));
			}
			
			mergeSort.run();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public String toString(){
		return "SORT";
	}
    
}

