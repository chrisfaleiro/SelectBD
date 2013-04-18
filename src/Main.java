import java.util.ArrayList;

import operators.Join;
import operators.Operator;
import operators.Projection;
import operators.Selection;
import util.FileManager;
import config.Data;

public class Main {

	public static void main(String[] args) {
	
		FileManager.cleanTempFolder();
		
		String resultSelection = new Selection()
			.setConfData(Data.PRODUCAO.getConf())
			.setNameAttribute("tipoID")
			.setValueAttribute("4")
			.addEntry(Data.PRODUCAO.getFilePath())
			.run();
		
		new Selection()
			.setConfData(Data.PRODUCAO.getConf())
			.setNameAttribute("ano_producao")
			.setValueAttribute("2001")
			.addEntry(FileManager.TEMP_FOLDER_PATH + resultSelection)
			.run();
		
		/*String resultJoin = new Join()
			.setConfDataR(Data.EQUIPE.getConf())
			.setNameAttributeR("pessoaID")
			.setConfDataS(Data.PESSOA.getConf())
			.setNameAttributeS("pessoaID")			
			.addEntry(Data.EQUIPE.getFilePath())	
			.addEntry(Data.PESSOA.getFilePath())
			.run();*/
		
		/*String resultProjection = new Projection()
			.setConfData(Data.PRODUCAO.getConf())
			.addNameAttribute("titulo")
			.addNameAttribute("ano_producao")
			.addEntry(Data.PRODUCAO.getFilePath())
			.run();*/

		//falta retornar confData do resultado do Join para ser passado para os outros operadores.
			
	}

}
