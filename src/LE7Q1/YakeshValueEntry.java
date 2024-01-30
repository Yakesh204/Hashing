package LE7Q1;

public class YakeshValueEntry {

    private int key;

    public YakeshValueEntry(){
        key = -1;
    }//constructor without parameters

    public YakeshValueEntry(int x){
        key = x;
    }//constructor with parameters

    public void setKey(int x){
        key = x;
    }//setter method
    public int getKey(){
        return key;
    }//getter method

}
