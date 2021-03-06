package Model;

import util.Constant;
import util.LocalSetting;

public class FileDownload extends FileShare {
    private int downloadedSize = 0;
    private int totalPart;
    private int endPartSize;
    private int status;

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
        endPartSize = this.size % Constant.PART_SIZE;
        totalPart = this.size / Constant.PART_SIZE;
        if(endPartSize != 0)   totalPart++;
        this.md5 = fileShare.getMd5();
        this.path = LocalSetting.getInstance().getDownFolderPath() + "\\" + fileShare.getName();
    }

    public int getDownloadedSize() {
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
}
