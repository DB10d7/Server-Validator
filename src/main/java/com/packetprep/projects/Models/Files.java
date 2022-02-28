package com.packetprep.projects.Models;

//import lombok.Getter;
//import lombok.NoArgsConstructor;
//import lombok.Setter;


public class Files {

    private String filename;
    private String url;
    private Long size;

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Long getSize() {
        return size;
    }

    public void setSize(Long size) {
        this.size = size;
    }
}
