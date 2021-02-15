package models;

public class Hitbox {
    //clase hitbox
    double x;
    double y;
    double width;
    double height;

    public Hitbox(){
        //posicion inicial
        this.setPosition(0,0);
        //tamaño inicial
        this.setSize(1,1);
    }

    //posicion nave/rocas
    public void setPosition(double x, double y){
        this.x = x;
        this.y = y;

    }

    public void setSize(double w, double h){
        this.width = w;
        this.height = h;
    }

    //comprueba que no esten uno en la misma posicion que otro
    public boolean overlaps(Hitbox other){

//        4 casos posibles

       boolean noOverlap =  this.x + this.width < other.x ||
               other.x + other.width < this.x ||
               this.y + this.height < other.y ||
               other.y + other.height < this.y;

       //devuelve si dos rectangulos realmente esta superpuestos
       return !noOverlap;

    }

}
