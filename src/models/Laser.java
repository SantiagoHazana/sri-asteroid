package models;

public class Laser extends Sprite{

    public double maxTimeAlive;
    private double timeAlive;
    private boolean alive;

    public Laser(String imageFileName) {
        super(imageFileName);
        timeAlive = 0;
        maxTimeAlive = 3;
        alive = true;
    }

    public void update(double deltaTime){
        //actualizacion de la posicion acorde a la velocidad
        timeAlive += deltaTime;
        if (timeAlive >= maxTimeAlive)
            die();
        this.position.add(this.velocity.x * deltaTime, this.velocity.y * deltaTime);
        this.wrap(1600,900);
    }

    public boolean isAlive() {
        return alive;
    }

    public void die(){
        alive = false;
    }

}
