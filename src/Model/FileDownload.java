package Model;

import util.Constant;
import util.Helper;
import util.LocalSetting;

public class FileDownload extends FileShare {
    private long downloadedSize = 0;
    private long oldDownloadedSize = 0;
    private int totalPart;
    private int endPartSize;
    private int status;
    private float speed = 0;

    public FileDownload(String name, String md5, int size, String path){
        this.name = name;
        this.md5 = md5;
        this.size = size;
        this.path = path;
        status = Constant.DOWNLOADING;
    }


    public FileDownload(FileShare fileShare){
        this.name = fileShare.name;
        this.size = fileShare.size;
        endPartSize = (int)(this.size % Constant.PART_SIZE);
        totalPart = (int)(this.size / Constant.PART_SIZE);
        if(endPartSize != 0)   totalPart++;
        this.md5 = fileShare.getMd5();
        this.path = LocalSetting.getInstance().getDownFolderPath() + "\\" + fileShare.getName();
    }

    public void upDateSpeed(){
        this.speed = Helper.round(((int) (this.downloadedSize - this.oldDownloadedSize))/(1024f*1024f), 2);
        this.oldDownloadedSize = this.downloadedSize;
    }

    public long getDownloadedSize() {
        return downloadedSize;
    }

    public int getTotalPart() {
        return totalPart;
    }

    public int getEndPartSize() {
        return endPartSize;
    }

    public void addDownloadedSize(int size){
        downloadedSize += size;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public float getSpeed() {
        return speed;
    }
}
