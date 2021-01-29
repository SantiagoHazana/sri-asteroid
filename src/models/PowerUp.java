package models;

import java.util.ArrayList;

public class PowerUp extends Sprite{

    enum PowerUpType {
        RapidFire,
        ExtraLife,
        Shield
    }

    private PowerUpType powerUpType;
    private boolean active;
    private double x = Math.random()*1300+300;
    private double y = Math.random()*800+100;
    private double angle = Math.random()*360;

    public PowerUp(String imageFileName, int type) {
        super(imageFileName);
        if (type == 0)
            powerUpType = PowerUpType.RapidFire;
        else if (type == 1)
            powerUpType = PowerUpType.ExtraLife;
        else
            powerUpType = PowerUpType.Shield;

        active = true;
        this.position.set(this.x,this.y);
        this.velocity.setLength(100);
        this.velocity.setAngle(this.angle);
        this.rotation = this.angle;
    }

    public boolean isActive() {
        return active;
    }

    public void die(){
        active = false;
    }

    public PowerUpType getPowerUpType() {
        return powerUpType;
    }

    public static String getImages(int i){
        ArrayList<String> imageFileNames = new ArrayList<>();
        imageFileNames.add("Images/Space Shooter Visual Assets/PNG/Power-ups/powerupBlue_bolt.png");
        imageFileNames.add("Images/Space Shooter Visual Assets/PNG/Power-ups/pill_blue.png");
        imageFileNames.add("Images/Space Shooter Visual Assets/PNG/Power-ups/powerupBlue_shield.png");
        return imageFileNames.get(i);
    }
}
