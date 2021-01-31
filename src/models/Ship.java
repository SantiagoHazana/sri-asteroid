package models;


import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Ship extends Sprite {

    private boolean alive;
    private int lives;
    private int points;
    private boolean activeShield;
    private double shieldTimer;
    private Image shieldImage;
    private boolean rapidFire;
    private double rapidFireTimer;

    public Ship(String imageFileName){
        super(imageFileName);
        alive = true;
        lives = 3;
        shieldImage = new Image("Images/spr_shield.png");
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

    @Override
    public void update(double deltaTime){
        if (activeShield)
            shieldTimer += deltaTime;
        if (shieldTimer >= 10){
            activeShield = false;
            shieldTimer = 0;
        }

        if (rapidFire)
            rapidFireTimer += deltaTime;
        if (rapidFireTimer >= 5){
            rapidFire = false;
            rapidFireTimer = 0;
        }

        //actualizacion de la posicion acorde a la velocidad
        this.position.add(this.velocity.x * deltaTime, this.velocity.y * deltaTime);
        this.wrap(1600,900);
    }

    @Override
    public void render(GraphicsContext context){
        context.save();

        context.translate(this.position.x, this.position.y);
        context.rotate(this.rotation);
        //rotacion desde el centro del sprite -- default rotacion desde el top-left de la pantalla
        context.translate(-this.image.getHeight()/2, -this.image.getHeight()/2);
        context.drawImage(this.image, 0,0);
        if (activeShield)
            context.drawImage(shieldImage, -20, 0);

        context.restore();
    }

    public void powerUp(PowerUp p) {
        if (p.getPowerUpType().equals(PowerUp.PowerUpType.ExtraLife)){
            lives++;
        }else if (p.getPowerUpType().equals(PowerUp.PowerUpType.Shield)){
            activeShield = true;
        }else if (p.getPowerUpType().equals(PowerUp.PowerUpType.RapidFire)){
            rapidFire = true;
        }
    }

    public boolean isShieldActive() {
        return activeShield;
    }

    public boolean rapidFireActive() {
        return rapidFire;
    }
}
