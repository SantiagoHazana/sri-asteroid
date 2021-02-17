package models;

public class Vector {
    // *************************
    // Attributes
    // *************************
    public double x;
    public double y;

    // *************************
    // Constructors
    // *************************

    /**
     * Default contructor
     * Sets x and y values to 0
     */
    public Vector(){
        // vector inician a 0
        this.set(0,0);
    }

    // *************************
    // Public methods
    // *************************

    /**
     * Sets the values of x and y
     * @param x changes the value of the attribute x
     * @param y changes the value of the attribute y
     */

    public void set(double x, double y){
        this.x = x;
        this.y = y;
    }

    /**
     * Add to the x and y attributes
     * @param dx amount to be added to attribute x
     * @param dy amount to be added to attribute y
     */

    public void add(double dx, double dy){
        this.x += dx;
        this.y += dy;
    }

    /**
     * Multiplies the attributes x and y by the given number
     * @param m number that multiplies the attributes x and y
     */

    public void multiply(double m){
        this.x *= m;
        this.y *=m;
    }

    /**
     * Returns the length of the vector
     * @return length of vector
     */

    public double getLength(){
        return Math.sqrt(this.x * this.x + this.y * this.y);
    }

    /**
     * Sets the length of the vector
     * @param l new length of the vector
     */

    public void setLength(double l){
        double currentLength = this.getLength();
        //si la longitud actual es 0, el angulo actual no esta definido;
        //se asume que el angulo actual es 0
        if (currentLength == 0) {
            this.set(l,0);
            return;
        }
            //escala el vector a longitud 1/x
        this.multiply(1/currentLength);
        this.multiply(l);
    }

    /**
     * Sets the angle of the vector
     * @param angleDegrees new angle of the vector
     */

    public void setAngle(double angleDegrees){
        double l = this.getLength();
        double angleRadians = Math.toRadians(angleDegrees);
        this.x = l*Math.cos(angleRadians);
        this.y = l* Math.sin(angleRadians);
    }

}
