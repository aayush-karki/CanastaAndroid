package edu.ramapo.akarki.canasta.model;

final class ValidRankSuit {
    // p_rankList lists all the rank of card in a standard deck
    // of card and last J for Joker
    static final String S_RANK_LIST = "23456789XJQKAJ";

    // p_suitList lists all the suit of card in a standard deck
    // of card and 1 and 2 for Joker
    // S = spade, C = club, H = heart, and D = diamond
    static final String S_SUIT_LIST = "SCHD12";

    /**
     * Contructor for ValidRankSuit Class.
     *
     
     *
     
     */
    private ValidRankSuit()
    {}

    /**
     * Checks if the passed rank is valid rank or not.
     *
     * @param aRank, a String. It holds the rank that needs to be validated
     *
     * @return boolean value, true if a_rank is valid
     */
    public static boolean isRankValid(String aRank)
    {
        if (aRank.length() != 1)
        {
            throw new IllegalArgumentException(
                    "Arugment length does not equal to 1");
        }

        return S_RANK_LIST.indexOf(aRank) != -1;
    }

    /**
     * Checks if the passed suit is valid suit or not.
     *
     * @param aSuit, a String. It holds the suit that needs to be validated
     *
     * @return boolean value, true if a_suit is valid
     */
    public static boolean isSuitValid(String aSuit)
    {
        if (aSuit.length() != 1)
        {
            throw new IllegalArgumentException(
                    "Arugment length does not equal to 1");
        }

        return S_SUIT_LIST.indexOf(aSuit) != -1;
    }

    /**
     * Checks if the passed a_rankSuit is valid or not.
     *
     * @param aRankSuit, a string. It holds the rank and suit that needs to be
     *                       validated. The rank is the 0th character and suit
     *                       is the 1th character
     *
     * @return boolean value, true if a_suit is valid
     */
    public static boolean isRankSuitValid(String aRankSuit)
    {
        if (aRankSuit.length() != 2)
        {
            throw new IllegalArgumentException(
                    "Arugment length does not equal to 2");
        }

        return aRankSuit.length() == 2
                && isRankValid(Character.toString(aRankSuit.charAt(0)))
                && isSuitValid(Character.toString(aRankSuit.charAt(1)));
    }
}

