package Model;

import util.Helper;

public class FileShare {
    protected String name;
    protected String md5;
    protected int size;
    protected String path;

    public FileShare(){}

    public FileShare(String name, String md5, int size, String path){
        this.name = name;
        this.md5 = md5;
        this.size = size;
        this.path = path;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMd5() {
        return md5;
    }

    public void setMd5(String md5) {
        this.md5 = md5;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getExtension(){
        return this.name.split("\\.")[this.name.split("\\.").length-1];
    }

    public float getMegaSize(){
        return Helper.round((float)(this.size/(1024.0*1024.0)), 2);
    }

    public String getObjectString(){
        return  "{ \"name\":\"" + this.name + "\",\"md5\":\"" + this.md5 + "\", \"size\":" + this.size + "}";
    }

    public void print(){
        System.out.println(this.name + "|" + this.md5 + "|" + this.size + "|" + this.path);
    }
}
