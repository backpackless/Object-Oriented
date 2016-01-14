/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package librarysearch;

/** Reference Class
 *
 * @author Ben Walker
 * 
 * A superclass to hold common methods for books and journals.
 * ACKNOWLEDGEMENT: Professor Song's code was used as the basis for this code
 */
public class Reference {
    protected String callNumber;
    protected String authors;
    protected String title;
    protected String publisher;
    protected int year;
    protected String organizer;
    
    /**
     * 
     * @param callNumber
     * @param title
     * @param year
     * @return True if input is valid, false if invalid
     */
    public static boolean valid(String callNumber, String title, int year) {
	return callNumber != null && !callNumber.isEmpty() && title != null && !title.isEmpty() && year >= 1000 & year <= 9999;
    }
        
    /**
     * 
     * @param callNumber
     * @return True if the callNumber was set, false otherwise
     */
    public boolean setCallNumber(String callNumber) {
	if( callNumber == null || callNumber.isEmpty() ) {
            return false;
	} else {
            this.callNumber = callNumber;
            return true;
	}
    }
        
    /**
     * 
     * @param title
     * @return  True if title was set, false otherwise
     */
    public boolean setTitle(String title) {
	if( title == null || title.isEmpty() ) {
            return false;
	} else {
            this.title = title;
            return true;
	}
    }
        
    /**
     * 
     * @param year
     * @return True if year was set, false otherwise
     */
    public boolean setYear(int year) {
        if( year <1000 || year > 9999 ) {
            return false;
        } else {
            this.year = year;
            return true;
        }
    }
        
    /**
     * 
     * @return callNumber of referenced doc
     */
    public String getCallNumber() {
	return callNumber;
    }
      
    /**
     * 
     * @return title of referenced doc
     */
    public String getTitle() {
	return title;
    }
        
    /**
     * 
     * @return year of referenced doc
     */
    public int getYear() {
	return year;
    }
    
    /**
     * 
     * @param other
     * @return false if reference is null or not equal to the callNumber, true otherwise
     */
    public boolean keyEquals(Reference other) {
	if( other == null )
            return false;
	else
            return callNumber.equalsIgnoreCase(other.callNumber) &&
                   year == other.year;
    }
}
