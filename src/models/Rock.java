package models;

import javafx.scene.canvas.GraphicsContext;

import java.util.ArrayList;

public class Rock extends Sprite{
    private double x = (int)(Math.random()*1600);;
    private double y = (int)(Math.random()*900);;
    private double angle = Math.random()*360;
    private double initialRockVelocity = (int)(Math.random()*50+125);
    private double initialTinyRockVelocity = (int)(Math.random()*75+150);
    private boolean alive;
    private boolean isTiny;

    public Rock(){
        this.position.set(x,y);
        this.velocity.setLength(this.initialTinyRockVelocity);
        this.velocity.setAngle(this.angle);
        alive =  true;
        isTiny = true;
        setImage(getTinyRockImage());

    }

    public Rock(String imageFileName){
        this.velocity.setLength(this.initialRockVelocity);
        this.velocity.setAngle(this.angle);
        isTiny = false;
        alive =  true;
        setImage(imageFileName);
    }

    public void newPosition(){
        x = (int)(Math.random()*1600);
        y = (int)(Math.random()*900);
        this.position.set(x,y);
    }


    @Override
    public void render(GraphicsContext context) {
        this.rotation +=1;
        super.render(context);
    }

    @Override
    public void update(double deltaTime){
        //actualizacion de la posicion acorde a la velocidad
        this.position.add(this.velocity.x * deltaTime, this.velocity.y * deltaTime);
        this.wrap(1600,900);
    }

    public boolean isAlive() {
        return alive;
    }
    public boolean isTiny(){ return isTiny; }

    public void die(){
        alive = false;
    }

    public static String getRockImage(){
        ArrayList<String> rockImagesList = new ArrayList<String>();
        //solo rocas grandes
        rockImagesList.add("Images/Space Shooter Visual Assets/PNG/Meteors/meteorBrown_big1.png");
        rockImagesList.add("Images/Space Shooter Visual Assets/PNG/Meteors/meteorBrown_big2.png");
        rockImagesList.add("Images/Space Shooter Visual Assets/PNG/Meteors/meteorBrown_big3.png");
        rockImagesList.add("Images/Space Shooter Visual Assets/PNG/Meteors/meteorBrown_big4.png");
        return rockImagesList.get((int)(Math.random()*4));
    }
    public static String getTinyRockImage(){
        ArrayList<String> tinyRockImagesList = new ArrayList<String>();
        tinyRockImagesList.add("Images/Space Shooter Visual Assets/PNG/Meteors/meteorBrown_med1.png");
        tinyRockImagesList.add("Images/Space Shooter Visual Assets/PNG/Meteors/meteorBrown_med3.png");

        return tinyRockImagesList.get((int)(Math.random()*2));
    }
}
