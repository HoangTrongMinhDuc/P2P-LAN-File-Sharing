package Model;

import java.io.File;
import java.util.ArrayList;

public class FileSeed extends FileShare {
    private ArrayList<Computer> listComputer;
    private int status;

    public FileSeed(){}

    public FileSeed(ArrayList<Computer> listComputer, int status) {
        this.listComputer = listComputer;
        this.status = status;
    }

    public FileSeed(String name, String md5, int size, String path, ArrayList<Computer> listComputer, int status) {
        super(name, md5, size, path);
        this.listComputer = listComputer;
        this.status = status;
    }

    public ArrayList<Computer> getListComputer() {
        return listComputer;
    }

    public void setListComputer(ArrayList<Computer> listComputer) {
        this.listComputer = listComputer;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public void addComputer(Computer computer){
        for(int i = 0; i < this.listComputer.size(); i++){

        }
    }
}
