package models;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Sprite {

    // *************************
    // Attributes
    // *************************

    public Vector position;
    public Vector velocity;
    public double rotation; // en grados
    public Hitbox boundary; //hitbox / perimetro
    public Image image;

    // *************************
    // Constructors
    // *************************

    /**
     * Default constructor, initializes all attributes to 0
     */
    public Sprite(){
        this.position = new Vector();
        this.velocity = new Vector();
        this.rotation = 0;
        this.boundary = new Hitbox();

    }

    /**
     * Contructs a sprite with the given imageURL
     * Calls the default constructor
     * @param imageFileName the url to the image
     */

    public Sprite(String imageFileName){
        this(); //const vacio
        setImage(imageFileName);
    }

    // *************************
    // Public methods
    // *************************

    /**
     * Changes the image and boundaries of the sprite
     * @param imageFileName new image url
     */
    public void setImage(String imageFileName){
        this.image = new Image(imageFileName);
        this.boundary.setSize(image.getWidth(), image.getHeight());
    }

    /**
     * Positions the boundary and returns it
     * @return the boundary
     */
    public Hitbox getBoundary(){
        this.boundary.setPosition(this.position.x, this.position.y);
        return this.boundary;
    }

    /**
     * Checks if the current sprite is overlapping with another one
     * @param other other sprite
     * @return true if overlaps with given sprite or false otherwise
     */
    public boolean overlaps(Sprite other){
        return this.getBoundary().overlaps(other.getBoundary());
    }

    /**
     *
     * @param screenWidth
     * @param screenHeight
     */
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
        this.wrap(1600,900);
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
