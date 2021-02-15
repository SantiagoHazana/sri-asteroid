package models;

public class Vector {
    public double x;
    public double y;

    public Vector(){
        // vector inician a 0
        this.set(0,0);
    }

    //añade valor a los vectores
    public void set(double x, double y){
        this.x = x;
        this.y = y;
    }

    public void add(double dx, double dy){
        this.x += dx;
        this.y += dy;
    }

    //para multiplar vector por una constante
    public void multiply(double m){
        this.x *= m;
        this.y *=m;
    }

    //sacar la longitud del vector
    public double getLength(){
        return Math.sqrt(this.x * this.x + this.y * this.y);
    }

   //saca el tamaño correcto del vector recur
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

    public void setAngle(double angleDegrees){
        double l = this.getLength();
        double angleRadians = Math.toRadians(angleDegrees);
        this.x = l*Math.cos(angleRadians);
        this.y = l* Math.sin(angleRadians);
    }

}
