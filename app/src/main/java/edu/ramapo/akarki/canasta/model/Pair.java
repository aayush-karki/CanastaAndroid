package edu.ramapo.akarki.canasta.model;

public class Pair<K, V> {

    private K mFirst;
    private V mSecond;

    /**
     * Default Contructor for Pair Class.
     * 
     * @param none
     * 
     * @return none
     */
    public Pair(K aFirst, V aSecond)
    {
        this.mFirst = aFirst;
        this.mSecond = aSecond;
    }

    /**
     * Overriding the hashcode method of the Object Class
     * 
     * @param none
     * 
     * @return int, the hascode for the object
     */
    @Override
    public int hashCode()
    {
        final int prime = 31;
        int hash = 3;

        hash = prime * hash
                + ((this.mFirst == null) ? 0 : this.mFirst.hashCode());
        hash = prime * hash
                + ((this.mSecond == null) ? 0 : this.mSecond.hashCode());
        return hash;
    }

    /**
     * to check if this pair is equal to the passed pair
     * 
     * @param o object of Object class, it holds the object to check that it is
     *              equal to this pair object or not.
     * 
     * @return boolean value. true if this pair is equal to the other pair.
     *         false other wise.
     */
    @Override
    public boolean equals(Object o)
    {
        // checking for self reference
        if (this == o)
        {
            return true;
        }

        // checking for null pointer passing
        if (o == null)
        {
            return false;
        }

        // checking if the passed Object is a instance of this class or not
        if (getClass() != o.getClass())
        {
            return false;
        }

        final Pair<K, V> otherCard = (Pair<K, V>) o;

        return this.mFirst.equals(otherCard.mFirst)
                && this.mSecond.equals(otherCard.mSecond);
    }

    /**
     * Gets the first item of the pair
     * 
     * @param none
     * 
     * @return object of K by value returns the first item of the pair
     */
    public K getFirst()
    {
        return mFirst;
    }

    /**
     * Gets the second item of the pair
     * 
     * @param none
     * 
     * @return object of K by value returns the second item of the pair
     */
    public V getSecond()
    {
        return mSecond;
    }

    /**
     * Sets the first item of the pair
     * 
     * @param object of type K, sets the passed variable to m_first
     * 
     * @return true to indicate successful setting of the first variable of the
     *         pair
     */
    public boolean setFirst(K aFirst)
    {
        mFirst = aFirst;
        return true;
    }

    /**
     * Sets the second item of the pair
     * 
     * @param object of type V, sets the passed variable to m_second
     * 
     * @return true to indicate a successful setting of the second variable of
     *         the pair
     */
    public boolean setSecond(V aSecond)
    {
        mSecond = aSecond;
        return true;
    }
}