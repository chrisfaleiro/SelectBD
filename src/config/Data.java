package config;

public enum Data {

	PESSOA(new ConfData()
				.setFilePath("D:\\Jesse\\Workspaces\\Workspace-github\\_data\\pessoa.txt")
				.addConfAttributes(new ConfAttribute(0, Long.class, "pessoaID"))
				.addConfAttributes(new ConfAttribute(1, String.class, "nome"))),
	
	PRODUCAO(new ConfData()
				.setFilePath("D:\\Jesse\\Workspaces\\Workspace-github\\_data\\producao.txt")
				.addConfAttributes(new ConfAttribute(0, Long.class, "producaoID"))
				.addConfAttributes(new ConfAttribute(1, String.class, "titulo"))
				.addConfAttributes(new ConfAttribute(2, String.class, "ano_producao"))
				.addConfAttributes(new ConfAttribute(3, String.class, "tipoID"))),
	
	EQUIPE(new ConfData()
				.setFilePath("D:\\Jesse\\Workspaces\\Workspace-github\\_data\\equipe.txt")
				.addConfAttributes(new ConfAttribute(0, Long.class, "equipeID"))
				.addConfAttributes(new ConfAttribute(1, Long.class, "pessoaID"))
				.addConfAttributes(new ConfAttribute(2, Long.class, "producaoID"))
				.addConfAttributes(new ConfAttribute(3, String.class, "papel"))); 
	
	private ConfData conf;
	
	private Data(ConfData conf){
		this.conf = conf;
	}

	public ConfData getConf() {
		return conf;
	}
	
	public String getFilePath() {
		return conf.getFilePath();
	}
}
