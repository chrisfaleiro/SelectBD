package operators;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import util.FileManager;
import config.ConfData;
import entities.Attribute;
import entities.Block;
import entities.Tuple;
import static entities.Block.BLOCK_SIZE;
import static config.ConfData.MEMORY_TOTAL;

public class Join extends Operator{

	private String entryR;
	
	private String entryS;
	
	private String nameAttributeR;
	
	private String nameAttributeS;
	
	private ConfData confDataR;

	private ConfData confDataS;
	
	private ConfData confDataJoin;
	
	private FileManager fileReaderR;
	
	private FileManager fileReaderS;
	
	private FileManager fileWriter;
	
	private static final int totalBlock = MEMORY_TOTAL / BLOCK_SIZE;
	
	private static final int SIZE_BLOCK_R = (totalBlock - 1) * BLOCK_SIZE;
	
	private static final int SIZE_BLOCK_S = BLOCK_SIZE;
	
	private Block blockR;
	
	private HashMap<Object, Tuple> hashR;
	
	public Join setNameAttributeR(String nameAttributeR){
		this.nameAttributeR = nameAttributeR;
		return this;
	}

	public Join setNameAttributeS(String nameAttributeS){
		this.nameAttributeS = nameAttributeS;
		return this;
	}
	
	public Join setConfDataR(ConfData confData){
		this.confDataR = confData;
		return this;
	}
	
	public Join setConfDataS(ConfData confData){
		this.confDataS = confData;
		return this;
	}
	
	public ConfData getConfDataJoin(){
		return this.confDataJoin;
	}
	
	@Override
	protected void validate() {
		if (this.getEntries() == null || this.getEntries().isEmpty()){
			throw new RuntimeException("Operador de junção deve possuir dois valores de entrada, não foi passado valor");
		}
		if (this.getEntries().size() != 2){
			throw new RuntimeException("Operador de junção deve possuir dois valores de entrada, foi passado " + this.getEntries().size() + " valor(es).");
		}		
	}

	@Override
	protected void setEntries() {
		this.entryR = this.getEntries().get(0);
		this.entryS = this.getEntries().get(1);
	}

	@Override
	protected ConfData open() throws IOException {
		
		fileReaderR = new FileManager(this.entryR, this.confDataR);
		fileReaderR.openFileReader();
		this.blockR = fileReaderR.getNextBlock(SIZE_BLOCK_R);
		this.hashR = new HashMap<Object, Tuple>();
				
		Attribute attributeR;
		
		for (Tuple tupleR : blockR.getTuples()){
			attributeR = tupleR.getAttributeByName(this.nameAttributeR);
			this.hashR.put(attributeR.getValue(), tupleR);
		}
		
		fileReaderS = new FileManager(this.entryS, this.confDataS);
		fileReaderS.openFileReader();
		
		String resultFilePath = this.toString() + new Date().getTime() + ".txt";
		
		this.confDataJoin = ConfData.joinConfData(this.confDataR, this.confDataS, this.nameAttributeS);
		
		fileWriter = new FileManager(resultFilePath, confDataJoin);
		fileWriter.openFileWriter();
		
		return new ConfData()
				.setFilePath(resultFilePath)
				.setConfAttributes(this.confDataJoin.getConfAttributes());		
	}

	@Override
	protected boolean next() throws IOException {
		
		Block blockS = fileReaderS.getNextBlock(SIZE_BLOCK_S);
		
		if(blockS.getTuples().isEmpty()){		
			
			this.blockR = fileReaderR.getNextBlock(SIZE_BLOCK_R);
			
			this.hashR = new HashMap<Object, Tuple>();
			
			Attribute attributeR;
			
			for (Tuple tupleR : blockR.getTuples()){
				attributeR = tupleR.getAttributeByName(this.nameAttributeR);
				this.hashR.put(attributeR.getValue(), tupleR);
			}
			
			this.restartS();
			blockS = fileReaderS.getNextBlock(SIZE_BLOCK_S);
			
		}
		if(blockR.getTuples().isEmpty()){
			return false;
		}
		
		Block blockResult = new Block(); 	
		Attribute attributeS;
		Tuple tupleR;
		
		for (Tuple tupleS : blockS.getTuples()){
			
			attributeS = tupleS.getAttributeByName(this.nameAttributeS);
			
			tupleR = hashR.get(attributeS.getValue());
			
			if (tupleR != null){					
				Tuple tuple = Tuple.joinTuples(tupleR, tupleS, attributeS);
				blockResult.addTuple(tuple);
			}
				
		}
		fileWriter.writeFile(blockResult);
		
		return true;
	}
	
	private void restartS() throws IOException{
		fileReaderS.closeFileReader();
		fileReaderS.openFileReader();
	}

	@Override
	protected void close() {
		try {
			fileReaderR.closeFileReader();
			fileReaderS.closeFileReader();
			fileWriter.closeFileWriter();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	@Override
	public String toString() {
		return "JOIN";
	}
}
