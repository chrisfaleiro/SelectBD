package config;

public enum Data {

	PESSOA("C:\\pessoa-sample.txt",
			new ConfData()
				.addConfAttributes(new ConfAttribute(0, Long.class, "pessoaID"))
				.addConfAttributes(new ConfAttribute(1, String.class, "nome"))),
	
	PRODUCAO("C:\\producao.txt",
			new ConfData()
				.addConfAttributes(new ConfAttribute(0, Long.class, "producaoID"))
				.addConfAttributes(new ConfAttribute(1, String.class, "titulo"))
				.addConfAttributes(new ConfAttribute(2, String.class, "ano_producao"))
				.addConfAttributes(new ConfAttribute(3, String.class, "tipoID"))),
	
	EQUIPE("C:\\equipe.txt",
			new ConfData()
				.addConfAttributes(new ConfAttribute(0, Long.class, "equipeID"))
				.addConfAttributes(new ConfAttribute(1, Long.class, "pessoaID"))
				.addConfAttributes(new ConfAttribute(2, Long.class, "producaoID"))
				.addConfAttributes(new ConfAttribute(3, String.class, "papel"))); 
	
	private String filePath;
	private ConfData conf;
	
	private Data(String filePath, ConfData conf){
		this.filePath = filePath;
		this.conf = conf;
	}

	public String getFilePath() {
		return filePath;
	}

	public ConfData getConf() {
		return conf;
	}
}
