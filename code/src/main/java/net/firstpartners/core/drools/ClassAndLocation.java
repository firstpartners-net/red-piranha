package net.firstpartners.core.drools;

import java.io.File;

/**
 * Just a simple class to hold our class and location together
 */
public class ClassAndLocation {

    // data holders    
    public Class<?> classHolder = null;
    public String locationText = null;
    public File fileLocation =null;


    public ClassAndLocation(){
    }

    public ClassAndLocation(String location){
        this.locationText = location;
    }



    public ClassAndLocation(Class<?> classHolder,String location ){
        this.classHolder = classHolder;
        this.locationText = location;
    }

    public ClassAndLocation(Class<?> classHolder,String locationText,File fileLocation ){
        this.classHolder = classHolder;
        this.fileLocation = fileLocation;
        this.locationText = locationText;
    }

     public ClassAndLocation(File fileLocation ){
        this.fileLocation = fileLocation;
    }

    
    @Override
    public String toString() {
        return "ClassAndLocation [classHolder=" + classHolder + ", locationText=" + locationText + ", fileLocation="
                + fileLocation + "]";
    }

   
   
}
