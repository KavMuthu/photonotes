package com.example.kavi.photonotes;

/**
 * Created by Kavi on 2/10/16.
 */
public class PhotoCaption {

    private String caption;
    private String filePath;


    public PhotoCaption(String caption,String filePath){
        this.caption = caption;
        this.filePath = filePath;

    }
    public PhotoCaption(){}
    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }
    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

}
