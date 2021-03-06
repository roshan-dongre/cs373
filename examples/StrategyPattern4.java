// ---------------------
// StrategyPattern4.java
// ---------------------

/*
Move Method (142)
Move Rental.getCharge()               to Movie.getCharge()
Move Rental.getFrequentRenterPoints() to Movie.getFrequentRenterPoints()
*/

import java.util.Enumeration;
import java.util.Vector;

class Movie {
    public static final int REGULAR     = 0;
    public static final int NEW_RELEASE = 1;
    public static final int CHILDRENS   = 2;

    private String _title;
    private int    _priceCode;

    public Movie (String title, int priceCode) {
        _title = title;
        setPriceCode(priceCode);}

    public int getPriceCode () { // const
        return _priceCode;}

    public String getTitle () { // const
        return _title;}

    public void setPriceCode (int priceCode) {
        _priceCode = priceCode;}}

class Rental {
    private Movie _movie;
    private int   _daysRented;

    public Rental (Movie movie, int daysRented) {
        _movie      = movie;
        _daysRented = daysRented;}

    public double getCharge () { // const
        double result = 0;
        switch (getMovie().getPriceCode()) {               // why not _movie?
            case Movie.REGULAR:
                result += 2;
                if (getDaysRented() > 2)                   // why not _daysRented?
                    result += (getDaysRented() - 2) * 1.5;
                break;
            case Movie.NEW_RELEASE:
                result += getDaysRented() * 3;
                break;
            case Movie.CHILDRENS:
                result += 1.5;
                if (getDaysRented() > 3)
                    result += (getDaysRented() - 3) * 1.5;
                break;}
        return result;}

    public int getDaysRented () { // const
        return _daysRented;}

    public int getFrequentRenterPoints () { // const
        if ((getMovie().getPriceCode() == Movie.NEW_RELEASE) && // why not _movie?
            (getDaysRented()           >  1))                   // why not _daysRented?
            return 2;
        return 1;}

    public Movie getMovie () { // const
        return _movie;}}

class Customer {
    private String         _name;
    private Vector<Rental> _rentals = new Vector<Rental>();

    public Customer (String name) {
        _name = name;}

    public void addRental (Rental rental) {
        _rentals.addElement(rental);}

    public String getName () { // const
        return _name;}

    private double getTotalCharge () { // const, O(n)
        double              result  = 0;
        Enumeration<Rental> rentals = _rentals.elements();
        while (rentals.hasMoreElements()) {
            Rental each  = rentals.nextElement();
            result      += each.getCharge();}
        return result;}

    private int getTotalFrequentRenterPoints () { // const, O(n)
        int                 result  = 0;
        Enumeration<Rental> rentals = _rentals.elements();
        while (rentals.hasMoreElements()) {
            Rental each  = rentals.nextElement();
            result      += each.getFrequentRenterPoints();}
        return result;}

    public String statement () { // O(3n)
        String              result  = "Rental Record for " + getName() + "\n";
        Enumeration<Rental> rentals = _rentals.elements();
        while (rentals.hasMoreElements()) {
            Rental each  = rentals.nextElement();
            result      +=
                "\t" + each.getMovie().getTitle()       +
                "\t" + String.valueOf(each.getCharge()) + "\n";}
        result +=
            "Amount owed is "                +
            String.valueOf(getTotalCharge()) + "\n";
        result +=
            "You earned "                                  +
            String.valueOf(getTotalFrequentRenterPoints()) +
            " frequent renter points";
        return result;}}

final class StrategyPattern4 {
    public static void main (String[] args) {
        System.out.println("StrategyPattern4.java");

        Customer x = new Customer("Penelope");
        assert x.statement().equals(
            "Rental Record for Penelope\n" +
            "Amount owed is 0.0\n"         +
            "You earned 0 frequent renter points");

        x.addRental(new Rental(new Movie("Shane", Movie.REGULAR), 2));
        assert x.statement().equals(
            "Rental Record for Penelope\n" +
            "\tShane\t2.0\n"               +
            "Amount owed is 2.0\n"         +
            "You earned 1 frequent renter points");

        x.addRental(new Rental(new Movie("Red River", Movie.REGULAR), 5));
        assert x.statement().equals(
            "Rental Record for Penelope\n" +
            "\tShane\t2.0\n"               +
            "\tRed River\t6.5\n"           +
            "Amount owed is 8.5\n"         +
            "You earned 2 frequent renter points");

        x.addRental(new Rental(new Movie("Giant", Movie.NEW_RELEASE), 1));
        assert x.statement().equals(
            "Rental Record for Penelope\n" +
            "\tShane\t2.0\n"               +
            "\tRed River\t6.5\n"           +
            "\tGiant\t3.0\n"               +
            "Amount owed is 11.5\n"        +
            "You earned 3 frequent renter points");

        x.addRental(new Rental(new Movie("2001", Movie.NEW_RELEASE), 3));
        assert x.statement().equals(
            "Rental Record for Penelope\n" +
            "\tShane\t2.0\n"               +
            "\tRed River\t6.5\n"           +
            "\tGiant\t3.0\n"               +
            "\t2001\t9.0\n"                +
            "Amount owed is 20.5\n"        +
            "You earned 5 frequent renter points");

        x.addRental(new Rental(new Movie("Big Country", Movie.CHILDRENS), 3));
        assert x.statement().equals(
            "Rental Record for Penelope\n" +
            "\tShane\t2.0\n"               +
            "\tRed River\t6.5\n"           +
            "\tGiant\t3.0\n"               +
            "\t2001\t9.0\n"                +
            "\tBig Country\t1.5\n"         +
            "Amount owed is 22.0\n"        +
            "You earned 6 frequent renter points");

        x.addRental(new Rental(new Movie("Spartacus", Movie.CHILDRENS), 5));
        assert x.statement().equals(
            "Rental Record for Penelope\n" +
            "\tShane\t2.0\n"               +
            "\tRed River\t6.5\n"           +
            "\tGiant\t3.0\n"               +
            "\t2001\t9.0\n"                +
            "\tBig Country\t1.5\n"         +
            "\tSpartacus\t4.5\n"           +
            "Amount owed is 26.5\n"        +
            "You earned 7 frequent renter points");

        System.out.println("Done.");}}
