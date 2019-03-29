package app.beans;

import app.helpers.Choices;

public class Case {

    public Case(int x, int y, Choices value) {
        this.positionX =x;
        this.positionY =y;
        this.value = value;
    }

    private  Choices value;
    private  boolean isOpen;
    private  boolean isFlag;
    private  int neerBomb;
    private  int positionX;
    private  int positionY;

    public Choices getValue() {
        return value;
    }

    public void setValue(Choices value) {
        this.value = value;
    }

    public boolean isOpen() {
        return isOpen;
    }

    public void setOpen(boolean open) {
        isOpen = open;
    }

    public boolean isFlag() {
        return isFlag;
    }

    public void setFlag(boolean flag) {
        isFlag = flag;
    }

    public int getNeerBomb() {
        return neerBomb;
    }

    public void setNeerBomb(int neerBomb) {
        this.neerBomb = neerBomb;
    }

    public int getPositionX() {
        return positionX;
    }

    public void setPositionX(int positionX) {
        this.positionX = positionX;
    }

    public int getPositionY() {
        return positionY;
    }

    public void setPositionY(int positionY) {
        this.positionY = positionY;
    }
}
