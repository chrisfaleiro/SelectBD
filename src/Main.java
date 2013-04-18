import operators.Join;
import operators.Projection;
import operators.Selection;
import util.FileManager;
import config.ConfData;
import config.Data;
import static util.FileManager.TEMP_FOLDER_PATH;

public class Main {

	public static void main(String[] args) {
	
		FileManager.cleanTempFolder();
		
		/*ConfData resultJoin1 = new Join()
			.setConfDataR(Data.PESSOA.getConf())
			.setNameAttributeR("pessoaID")
			.setConfDataS(Data.EQUIPE.getConf())
			.setNameAttributeS("pessoaID")
			.addEntry(Data.PESSOA.getFilePath())
			.addEntry(Data.EQUIPE.getFilePath())			
			.run();
		
		ConfData resultSelection = new Selection()
			.setConfData(Data.PRODUCAO.getConf())
			.setNameAttribute("tipoID")
			.setValueAttribute("1")
			.addEntry(Data.PRODUCAO.getFilePath())
			.run();
		
		ConfData resultJoin2 = new Join()
			.setConfDataR(resultSelection)
			.setNameAttributeR("producaoID")
			.setConfDataS(resultJoin1)
			.setNameAttributeS("producaoID")
			.addEntry(TEMP_FOLDER_PATH + resultSelection.getFilePath())
			.addEntry(TEMP_FOLDER_PATH + resultJoin1.getFilePath())			
			.run();
		
		ConfData resultProjection = new Projection()
			.setConfData(resultJoin2)
			.addNameAttribute("titulo")
			.addNameAttribute("ano_producao")
			.addNameAttribute("nome")
			.addNameAttribute("papel")
			.addEntry(TEMP_FOLDER_PATH + resultJoin2.getFilePath())
			.run();*/
		
		/*ConfData resultJoin1 = new Join()
			.setConfDataR(Data.PESSOA.getConf())
			.setNameAttributeR("pessoaID")
			.setConfDataS(Data.EQUIPE.getConf())
			.setNameAttributeS("pessoaID")
			.addEntry(Data.PESSOA.getFilePath())
			.addEntry(Data.EQUIPE.getFilePath())			
			.run();
	
		ConfData resultJoin2 = new Join()
			.setConfDataR(Data.PRODUCAO.getConf())
			.setNameAttributeR("producaoID")
			.setConfDataS(resultJoin1)
			.setNameAttributeS("producaoID")
			.addEntry(Data.PRODUCAO.getFilePath())
			.addEntry(TEMP_FOLDER_PATH + resultJoin1.getFilePath())			
			.run();

		ConfData resultSelection = new Selection()
			.setConfData(resultJoin2)
			.setNameAttribute("tipoID")
			.setValueAttribute("1")
			.addEntry(TEMP_FOLDER_PATH + resultJoin2.getFilePath())
			.run();
		
		ConfData resultProjection = new Projection()
			.setConfData(resultSelection)
			.addNameAttribute("titulo")
			.addNameAttribute("ano_producao")
			.addNameAttribute("nome")
			.addNameAttribute("papel")
			.addEntry(TEMP_FOLDER_PATH + resultSelection.getFilePath())
			.run();*/
		
		ConfData resultSelection = new Selection()
			.setConfData(Data.PRODUCAO.getConf())
			.setNameAttribute("tipoID")
			.setValueAttribute("1")
			.addEntry(Data.PRODUCAO.getFilePath())
			.run();
		
		ConfData resultJoin1 = new Join()
			.setConfDataR(resultSelection)
			.setNameAttributeR("producaoID")
			.setConfDataS(Data.EQUIPE.getConf())
			.setNameAttributeS("producaoID")
			.addEntry(TEMP_FOLDER_PATH + resultSelection.getFilePath())
			.addEntry(Data.EQUIPE.getFilePath())			
			.run();
		
		
		/*new Sort()
		.setConfData(Data.PESSOA.getConf())
		.setNameAttribute("pessoaID")
		.addEntry(Data.PESSOA.getFilePath())
		.run();*/

	}

}
