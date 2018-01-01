package com.pej.domains;

import org.springframework.web.multipart.MultipartFile;

public class FileBucket {
 
    MultipartFile file;
    private Integer id;
    private String nature;
    public MultipartFile getFile() {
        return file;
    }
 
    public void setFile(MultipartFile file) {
        this.file = file;
    }

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNature() {
		return nature;
	}

	public void setNature(String nature) {
		this.nature = nature;
	}
    
    
}