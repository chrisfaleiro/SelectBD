import java.util.ArrayList;

import operators.Join;
import operators.Operator;
import operators.Projection;
import operators.Selection;
import operators.TwoPhaseMultiwayMergeSort;
import util.FileManager;
import config.ConfData;
import config.Data;

public class Main {

	public static void main(String[] args) {
	
		FileManager.cleanTempFolder();
		
		ConfData resultSelection = new Selection()
			.setConfData(Data.PRODUCAO.getConf())
			.setNameAttribute("tipoID")
			.setValueAttribute("4")
			.addEntry(Data.PRODUCAO.getFilePath())
			.run();
		
		new Selection()
			.setConfData(Data.PRODUCAO.getConf())
			.setNameAttribute("ano_producao")
			.setValueAttribute("2001")
			.addEntry(FileManager.TEMP_FOLDER_PATH + resultSelection.getFilePath())
			.run();
		
		/*ConfData resultJoin = new Join()
			.setConfDataR(Data.EQUIPE.getConf())
			.setNameAttributeR("pessoaID")
			.setConfDataS(Data.PESSOA.getConf())
			.setNameAttributeS("pessoaID")			
			.addEntry(Data.EQUIPE.getFilePath())	
			.addEntry(Data.PESSOA.getFilePath())
			.run();*/
		
//		ConfData resultJoin = new Join()
//			.setConfDataR(Data.PESSOA.getConf())
//			.setNameAttributeR("pessoaID")
//			.setConfDataS(Data.EQUIPE.getConf())
//			.setNameAttributeS("pessoaID")
//			.addEntry(Data.PESSOA.getFilePath())
//			.addEntry(Data.EQUIPE.getFilePath())			
//			.run();
		
		/*ConfData resultProjection = new Projection()
			.setConfData(Data.PRODUCAO.getConf())
			.addNameAttribute("titulo")
			.addNameAttribute("ano_producao")
			.addEntry(Data.PRODUCAO.getFilePath())
			.run();*/
		
		new TwoPhaseMultiwayMergeSort()
		.setConfData(Data.PESSOA.getConf())
		.setNameAttribute("pessoaID")
		.addEntry(Data.PESSOA.getFilePath())
		.run();

	}

}
