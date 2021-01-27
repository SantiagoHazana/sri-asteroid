package models;

public class Ship extends Sprite {

    private boolean alive;
    private int lives;
    private int points;

    public Ship(){
        super();
        alive = true;
        lives = 3;
    }

    public Ship(String imageFileName){
        super(imageFileName);
        alive = true;
        lives = 3;
    }

    public int getPoints() {
        return points;
    }

    public void addPoints(int points) {
        if (alive)
            this.points += points;
    }

    public void move(String key){
        switch (key){
            case "W":
                this.velocity.setLength(200); //velocidad inicial
                this.velocity.setAngle(this.rotation);
                break;
            case "D":
                this.rotation +=3;
                break;
            case "A":
                this.rotation -=3;
                break;
            default:
                // cuando se suelta la tecla --- meter desaceleracion
                if (this.velocity.getLength()==0) {
                    this.velocity.setLength(0); //velocidad 0 al inicio
                }else {
                    this.velocity.setLength(50);
                }
                break;
        }
    }

    public boolean isAlive() {
        return alive;
    }

    public int getLives(){
        return lives;
    }

    public void die(){
        alive = false;
    }

    public Laser pium(){
        Laser laser = new Laser("Images/Space Shooter Visual Assets/PNG/Lasers/laserBlue02.png");
        laser.position.set(this.position.x, this.position.y);
        laser.velocity.setLength(600);
        laser.velocity.setAngle(this.rotation);
        laser.rotation = this.rotation;
        return laser;
    }

    public void hit() {
        lives--;
        if (lives==0)
            die();
    }
}
