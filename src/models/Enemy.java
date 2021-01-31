package models;

import javafx.scene.canvas.GraphicsContext;

import java.util.ArrayList;

public class Enemy extends Sprite{
    private boolean alive;
    private double x = (int)(Math.random()*1600);
    private double y = (int)(Math.random()*900);
    private double angle = Math.random()*360;
    private ArrayList<Laser> lasers;
    private double timer;
    private double timeToShoot;

    public Enemy(String imageFileName) {
        super(imageFileName);
        this.alive = true;
        this.position.set(this.x,this.y);
        this.velocity.setLength(100);
        this.velocity.setAngle(this.angle);
        this.rotation = this.angle;
        lasers = new ArrayList<>();
        timeToShoot = 2.5;
    }

    public boolean isAlive() {
        return alive;
    }

    public void die(){
        this.alive = false;
    }

    public void pium(){
        Laser laser = new Laser("Images/Space Shooter Visual Assets/PNG/Lasers/laserGreen02.png");
        laser.position.set(this.position.x, this.position.y);
        laser.velocity.setLength(400);
        laser.velocity.setAngle(this.rotation);
        laser.rotation = this.rotation;
        lasers.add(laser);
    }

    public void newPosition(){
        x = (int)(Math.random()*1600);
        y = (int)(Math.random()*900);
        this.position.set(x,y);
    }

    public void update(double deltaTime){
        timer += deltaTime;
        if (timer >= timeToShoot){
            pium();
            timer = 0;
        }
        //actualizacion de la posicion acorde a la velocidad
        this.position.add(this.velocity.x * deltaTime, this.velocity.y * deltaTime);
        this.wrap(1600,900);
    }

    public void checkIfLaserHitShip(Ship ship){
        for (Laser l : lasers) {
            if (l.overlaps(ship)) {
                if (!ship.isShieldActive())
                    ship.hit();
                l.die();
            }
        }
    }

    public void render(GraphicsContext context){

        for (Laser l : lasers) {
            if (l.isAlive()) {
                l.render(context);
                l.update(1 / 60f);
            } else {
                lasers.remove(l);
                l = null;
                break;
            }
        }

        context.save();

        context.translate(this.position.x, this.position.y);
        context.rotate(this.rotation);
        //rotacion desde el centro del sprite -- default rotacion desde el top-left de la pantalla
        context.translate(-this.image.getHeight()/2, -this.image.getHeight()/2);
        context.drawImage(this.image, 0,0);

        context.restore();
    }

}
