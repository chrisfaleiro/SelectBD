package operators;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public abstract class Operator {
	
	private List<String> entries = new ArrayList<String>();
	private String resultFileName;
	private Date iniTime;
	private Date endTime;
	
	public List<String> getEntries() {
		return entries;
	}

	public Operator addEntry(String entry) {
		this.entries.add(entry);
		return this;
	}
	
	protected abstract void validate();
	
	protected abstract void setEntries();
	
	protected abstract String open() throws IOException;
	
	protected abstract boolean next() throws IOException;
	
	protected abstract void close();	
	
	public String run() {
		
		start();
		validate();
		setEntries();
		
		try {
			resultFileName = open();
			
			while (next());
			
		} catch (IOException e) {
			
			e.printStackTrace();
			
		} finally {
			
			close();
		}
		
		finish();
		
		return resultFileName;
	}
	
	private void start() {
		
		System.out.println("Iniciando execução do operador: " + this.toString());
		iniTime = new Date();
	}

	private void finish() {
		
		endTime = new Date();
		
		System.out.println(
				"Arquivo de Saída: " + resultFileName + "\n" +
				"Operador executado em " + (endTime.getTime() - iniTime.getTime()) / 1000 + " segundos.\n");
	}
}
