package edu.ramapo.akarki.canasta.exceptions;

public class ImproperMeldException extends Exception {
    /**
     * default constructor
     * 
     * @param none
     * 
     * @return none
     */
    public ImproperMeldException(String str)
    {
        // calling the constructor of parent Exception
        super(str);
    }
}
