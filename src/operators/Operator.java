package operators;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import config.ConfData;

public abstract class Operator {
	
	private List<String> entries = new ArrayList<String>();
	private ConfData resultConfData;
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
	
	protected abstract ConfData open() throws IOException;
	
	protected abstract boolean next() throws IOException;
	
	protected abstract void close();	
	
	public ConfData run() {
		
		start();
		validate();
		setEntries();
		
		try {
			resultConfData = open();
			
			while (next());
			
		} catch (IOException e) {
			
			e.printStackTrace();
			
		} finally {
			
			close();
		}
		
		finish();
		
		return resultConfData;
	}
	
	private void start() {
		
		System.out.println("Iniciando execução do operador: " + this.toString());
		iniTime = new Date();
	}

	private void finish() {
		
		endTime = new Date();
		
		System.out.println(
				"Arquivo de Saída: " + resultConfData.getFilePath() + "\n" +
				"Operador executado em " + (endTime.getTime() - iniTime.getTime()) / 1000 + " segundos.\n");
	}
}
