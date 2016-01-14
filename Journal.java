package librarysearch;

/** Journal class
 *
 * @author Ben Walker
 *
 * A class for representing and comparing journals.
 * ACKNOWLEDGEMENT: Professor Song's code was used as the basis for this code
 */

public class Journal extends Reference {	
    
    /**
     * Creates a new journal object with the required fields.
     * @param callNumber
     * @param title
     * @param organizer
     * @param year 
     */
    public Journal(String callNumber, String title, String organizer, int year) {
	if( valid(callNumber, title, year) ) {
            this.callNumber = callNumber;
            this.title = title;
            this.organizer = organizer;
            this.year = year;
	} else {			
            System.out.println("Invalid values for creating a journal");
            System.exit(0);
	}
    }
	
     /**
     * Create a journal if only the callNumber, title, and year were given.
     * @param callNumber
     * @param title
     * @param year 
     */
    public Journal(String callNumber, String title, int year) {
	this(callNumber, title, "", year);
    }

    /**
     * Sets the value of organizer to current value.
     * @param organizer 
     */
    public void setOrganizer(String organizer) {
	this.organizer = organizer;
    }
	
    /**
     * 
     * @return organizer of referenced doc
     */
    public String getOrganizer() {
	return organizer;
    }
	
    /**
     * Compare two objects of type journal
     * @param other
     * @return true if equal, false if not equal 
     */
    public boolean equals(Journal other) {
	if (other == null)
            return false;
	else 
            return callNumber.equalsIgnoreCase(other.callNumber) &&
	           title.equalsIgnoreCase(other.title) &&
                   organizer.equalsIgnoreCase(other.organizer) &&
	           year == other.year;
	}
	
    /**
     * 
     * @return A printable string displaying journal information 
     */
    @Override
    public String toString() {
	return "Journal: " + callNumber + ". \"" +
	       title + "\". " +
	       organizer + ", " + year + ".";
    }
    
    /**
     * Creates a new journal, prints it
     * @param args 
     */
    public static void main(String[] args) {
        Journal aJournal = new Journal( "P98.C6116", "Computational Linguistics", 
                           "Association for Computational Linguistics", 2008 );
	System.out.println(aJournal);
    }
}

