package util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import config.ConfData;
import entities.Block;
import entities.Tuple;

public class FileManager {
	
	public static final String TEMP_FOLDER_PATH = "E:\\git\\SelectBD\\temp\\";
	public static final String SEPARATOR = "##";

	private String path;
	private File file;
	private BufferedReader bufferedReader;
	private FileWriter fileWriter;
	private ConfData confData;
	private Tuple lastTuple;
	
	public FileManager (String path, ConfData confData){
		this.path = path;
		this.confData = confData;
		this.lastTuple = null;
	}
	
	public static void cleanTempFolder() {
		
		File folder = new File(TEMP_FOLDER_PATH);
		
	    File[] files = folder.listFiles();
	    
	    if (files != null) {
	        for (File f : files) {
	        	
	            if (f.isFile()) {
	                f.delete();
	            }
	        }
	    }
	}
	
	public void openFileReader() throws IOException{
		
		file = new File(path);

		FileInputStream inputFile = new FileInputStream(file);
		
		InputStreamReader reader = new InputStreamReader(inputFile);
		
		bufferedReader = new BufferedReader(reader);
		
	}
	
	public Block getNextBlock(int size) throws IOException{
		
		String line = "";
		Long sizeBlock = 0L;
		
		Block resultBlock = new Block();
		
		if (lastTuple != null){
			resultBlock.addTuple(lastTuple);
		}
		
		while (sizeBlock < size){
			line = bufferedReader.readLine();
			
			if (line == null){
				return resultBlock;
			}
			
			Tuple tuple = new Tuple().generateTuple(line, confData);
		
			sizeBlock += tuple.getSize();
			
			resultBlock.addTuple(tuple);
		}
		
		boolean overrideBlockSize = resultBlock.getTuples().size() > size;
		
		if (overrideBlockSize) {
			resultBlock.removeLastTuple();
		}
		
		return resultBlock;
	}
	
	public void closeFileReader () throws IOException{
		bufferedReader.close();
	}
	
	public File openFileWriter() throws IOException{
		
		File tempFile = new File(TEMP_FOLDER_PATH + path);
		fileWriter = new FileWriter(tempFile, true);
			
		try{
			if (!tempFile.exists()){
				tempFile.createNewFile();
			}
			
		}catch (IOException e){
			e.printStackTrace();
			System.out.println("Erro ao gerar arquivo.");
		}
		
		return tempFile;
	}
	
	public void writeFile(Block block) throws IOException{
				
		int i=0;
		
		List<Tuple> tupleList = block.getTuples();
		
		while (i < tupleList.size()){
			fileWriter.write(tupleList.get(i).generateLine(confData) + "\n");
			i++;
		}
	}
	

	public void closeFileWriter() throws IOException{
		fileWriter.close();
	}
}
