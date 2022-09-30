package cn.ewsd.system.model;


import cn.ewsd.common.model.MCoreBase;
import org.springframework.http.ResponseEntity;

import javax.persistence.Table;


@Table(name="sys_attachment")
public class Attachment extends MCoreBase {
	
	private String module;
	private String category;
	private String puuid;
	private String source;
	private String fileType;
	private String fileName;
	private Long fileSize;
	private String filePath;
	private String fileVersion;
	private String fileStatus;

	//private Article article;
//	private ResponseEntity<byte[]> fileflow;
//
//	public ResponseEntity<byte[]> getFileflow() {
//		return fileflow;
//	}
//
//	public void setFileflow(ResponseEntity<byte[]> fileflow) {
//		this.fileflow = fileflow;
//	}

	public Attachment() {
		super();
	}

	public String getFileType() {
		return fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public Long getFileSize() {
		return fileSize;
	}

	public void setFileSize(Long fileSize2) {
		this.fileSize = fileSize2;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getCategory() {
		return category;
	}


	public void setCategory(String category) {
		this.category = category;
	}


	public String getModule() {
		return module;
	}


	public void setModule(String module) {
		this.module = module;
	}

	public String getPuuid() {
		return puuid;
	}

	public void setPuuid(String puuid) {
		this.puuid = puuid;
	}

	/*@ManyToOne(cascade=CascadeType.ALL,optional=false)
	@JoinColumn(name="pid")
	public Article getArticle() {
		return article;
	}

	public void setArticle(Article article) {
		this.article = article;
	}*/

	public String getFileVersion() {
		return fileVersion;
	}

	public void setFileVersion(String fileVersion) {
		this.fileVersion = fileVersion;
	}

	public String getFileStatus() {
		return fileStatus;
	}

	public void setFileStatus(String fileStatus) {
		this.fileStatus = fileStatus;
	}
}
	