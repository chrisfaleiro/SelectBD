package operators;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

import util.FileManager;
import util.TupleComparator;

import config.ConfData;
import entities.Block;
import entities.Tuple;

public class MergeSort extends Operator{

	private String entry;
	
	private String nameAttribute;
	
	private String filePath;
	
	private ConfData confDataMerge;
	
	private FileManager fileWriter;
	
	private FileManager fileReader;
	
	private PriorityQueue<Tuple> priorityQueue;
	
	private List<FileManager> listOfRuns;
	
	private Map<String, Object> mapTuplesForRuns;
	
	private Block resultBlock;
	
	private static final int SIZE_BLOCK = 10;
	
	private int count;
	
	private int blockSize;
	
	public MergeSort(int count){
		this.count = count;
	}
	
	public MergeSort setNameAttribute(String nameAttribute){
		this.nameAttribute = nameAttribute;
		return this;
	}

	public MergeSort setConfData(ConfData confDataSort){
		this.confDataMerge = confDataSort;
		return this;
	}
	
	public ConfData getConfDataSort(){
		return this.confDataMerge;
	}
	
	public void setFileSort(String filePath){
		this.filePath = filePath;
	}
	
	@Override
	protected void validate() {
		if (this.getEntries() == null || this.getEntries().isEmpty()){
			throw new RuntimeException("Operador de junção deve possuir dois valores de entrada, não foi passado valor");
		}
		if (this.getEntries().size() != count){
			throw new RuntimeException("Operador de Merge deve possuir" + count + " valores de entrada, foi passado " + this.getEntries().size() + " valor(es).");
		}	
	}

	@Override
	protected void setEntries() {
		this.entry = this.getEntries().get(0);
	}

	@Override
	protected ConfData open() throws IOException {
		
		blockSize = 0;
		listOfRuns = new ArrayList<FileManager>();
		
		inicializeRuns(count);
		
		fileWriter = new FileManager(this.filePath, this.confDataMerge);
		
		fileWriter.openFileWriter();

		resultBlock = new Block(SIZE_BLOCK);
		
		mapTuplesForRuns = new HashMap<String, Object>();
		
		priorityQueue = new PriorityQueue<Tuple>(24000, new TupleComparator(nameAttribute));
		
		for(int i=0; i< listOfRuns.size(); i++){
			
			fileReader = listOfRuns.get(i);
			fileReader.openFileReader();
				
			Block block = fileReader.getNextBlock(SIZE_BLOCK);
			
			Tuple tuple;
			for (int j=0; j<block.getTuples().size(); j++){
			
				tuple = block.getTuples().get(j);
				tuple.setRun(i);
				priorityQueue.add(tuple);
			}
			
			mapTuplesForRuns.put(String.valueOf(i), block.getTuples().size());
		}
		
		return new ConfData()
		.setFilePath(this.filePath)
		.setConfAttributes(this.confDataMerge.getConfAttributes());
	}

	@Override
	protected boolean next() throws IOException {

		Tuple tuple = priorityQueue.poll();
		
		if (tuple == null){
			return false;
		}
		
		if (resultBlock == null){
			resultBlock = new Block(SIZE_BLOCK);
		}
		
		Block block;
		
		int size = (int) mapTuplesForRuns.get(String.valueOf(tuple.getRun()));
		mapTuplesForRuns.remove(tuple.getRun());
		size = size - 1;
		mapTuplesForRuns.put(String.valueOf(tuple.getRun()), size);
		
		/* Se o tamanho das tuplas recuperado em cada run for 1, significa que 
		 * o bloco foi percorrido por completo e é necessário pegar outro bloco naquele run*/
		if(size == 1){
			block = listOfRuns.get(tuple.getRun()).getNextBlock(SIZE_BLOCK);
			
			for (int j=0; j<block.getTuples().size(); j++){
				Tuple newTupleOnQueue = block.getTuples().get(j);
				newTupleOnQueue.setRun(tuple.getRun());
				priorityQueue.add(newTupleOnQueue);
			}
			
			mapTuplesForRuns.remove(tuple.getRun());
			mapTuplesForRuns.put(String.valueOf(tuple.getRun()), block.getTuples().size());
			
		}
		
		if(blockSize < resultBlock.getSize()){
			resultBlock.addTuple(tuple);
			blockSize++;
		}else{
			//TODO criar um bloco com tamanho fixo e add as tuplas até estourar... 
			//com o bloco cheio, eu escrevo no arquivo.
			fileWriter.writeFile(resultBlock);
			resultBlock = null;
		}
		
		return true;
	}

	@Override
	protected void close() {
		try {
			
			fileWriter.closeFileWriter();
			
			for(int i=0; i< listOfRuns.size(); i++){
				fileReader.closeFileReader();
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void inicializeRuns(int count){
		
		for(int i=0; i<count; i++){
			
			try{
				FileManager runFile = new FileManager(getEntries().get(i), this.confDataMerge);
				runFile.openFileReader();
				listOfRuns.add(runFile);
			}catch (Exception e){
				e.printStackTrace();
			}
		}
	}

}
