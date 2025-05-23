package jp.co.tsuno.data.entity;

public enum ReadingStatus {
	//未読
	UNREAD("未読"),
	//読書中
	READING("読書中"),
	//読了
	COMPLETED("読了");
	
	private final String label;
	
	private ReadingStatus(String label) {
		this.label = label;
	}
	
	public String getLabel() {
		return label;
	}
	
	
}
