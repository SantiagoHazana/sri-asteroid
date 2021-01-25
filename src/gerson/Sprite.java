package gerson;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

//clase nave y rock en una
public class Sprite {
    public Vector position;
    public Vector velocity;
    public double rotation; // en grados
    public Rectangle boundary; //hitbox / perimetro
    public Image image;

    public Sprite(){
        this.position = new Vector();
        this.velocity = new Vector();
        this.rotation = 0;
        this.boundary = new Rectangle();

    }

    public Sprite(String imageFileName){
        this(); //const vacio
        setImage(imageFileName);
    }

    //creacion de nave/roca
    public void setImage(String imageFileName){
        this.image = new Image(imageFileName);
        this.boundary.setSize(image.getWidth(), image.getHeight());
    }

    //posicion
    public Rectangle getBoundary(){
        this.boundary.setPosition(this.position.x, this.position.y);
        return this.boundary;
    }

    //comprueba superposicion de Sprites
    public boolean overlaps(Sprite other){
        return this.getBoundary().overlaps(other.getBoundary());
    }


    //cuando la nave sale de la pantalla aparece en el lado opuesto
    public void wrap(double screenWidth, double screenHeight){

        double halfWidth = this.image.getWidth()/2;
        double halfHeight = this.image.getWidth()/2;

        if (this.position.x + halfWidth< 0){
            this.position.x = screenWidth;
        }
        if (this.position.x > screenWidth) {
            this.position.x = -halfWidth;
        }
        if (this.position.y + halfHeight < 0) {
            this.position.y = screenHeight;
        }
        if (this.position.y > screenHeight){
            this.position.y = -halfHeight;
        }
    }

    //tiempo entre actualizaciones -- 1/fps
    public void update(double deltaTime){
        //actualizacion de la posicion acorde a la velocidad
        this.position.add(this.velocity.x * deltaTime, this.velocity.y * deltaTime);
        this.wrap(800,600);
    }

    //render de los sprites
    public void render(GraphicsContext context){
        context.save();

        context.translate(this.position.x, this.position.y);
        context.rotate(this.rotation);
        //rotacion desde el centro del sprite -- default rotacion desde el top-left de la pantalla
        context.translate(-this.image.getHeight()/2, -this.image.getHeight()/2);
        context.drawImage(this.image, 0,0);

        context.restore();
    }
}
