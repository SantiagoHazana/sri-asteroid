package models;

public class Ship extends Sprite {

    private boolean alive;

    public Ship(){
        super();
        alive = true;
    }

    public Ship(String imageFileName){
        super(imageFileName);
        alive = true;
    }

    public boolean isAlive() {
        return alive;
    }

    public void die(){
        alive = false;
    }

    public void pium(){

    }

}
