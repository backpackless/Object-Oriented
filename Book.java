package librarysearch;

/** Book class
 *
 * @author Ben Walker
 *
 * A class for representing and comparing books.
 * ACKNOWLEDGEMENT: Professor Song's code was used as the basis for this code
 */

public class Book extends Reference {
    
    /**
     * Creates a new book object with the required fields.
     * @param callNumber
     * @param authors
     * @param title
     * @param publisher
     * @param year 
     */
    public Book(String callNumber, String authors, String title, String publisher, int year) {
	if( valid(callNumber, title, year) ) {
            this.callNumber = callNumber;
            this.authors = authors;
            this.title = title;
            this.publisher = publisher;
            this.year = year;
	} else {			
            System.out.println("Invalid values for creating a book");
            System.exit(0);
	}
    }
	
    /**
     * Creates a new book object if only the callNumber, title, and year were entered
     * @param callNumber
     * @param title
     * @param year 
     */
    public Book(String callNumber, String title, int year) {
	this(callNumber, "", title, "", year);
    }
	
    /**
     * Sets the value of author to the current value
     * @param authors 
     */
    public void setAuthors(String authors) {
	this.authors = authors;
    }

    /**
     * Sets the value of publisher to the current value
     * @param publisher 
     */
    public void setPublisher(String publisher) {
	this.publisher = publisher;
    }
	
    /**
     * 
     * @return authors of referenced doc
     */
    public String getAuthors() {
	return authors;
    }
	
    /**
     * 
     * @return publisher of referenced doc
     */
    public String getPublisher() {
	return publisher;
    }
	
    /**
     * Compares two book objects
     * @param other
     * @return true if equal, false if not equal
     */
    public boolean equals(Book other) {
	if (other == null)
            return false;
	else 
            return callNumber.equalsIgnoreCase(other.callNumber) &&
                   authors.equalsIgnoreCase(other.authors) &&
                   title.equalsIgnoreCase(other.title) &&
		   publisher.equalsIgnoreCase(other.publisher) &&
                   year == other.year;
    }
    
    /**
     * 
     * @return A printable string displaying book information
     */
    @Override
    public String toString() {
	return "Book: " + callNumber + ". " +
                authors + ". \"" +
                title + "\". " +
		publisher + ", " + year + ".";
    }
	
    /**
     * Create a new book object, print it
     * @param args 
     */
    public static void main(String[] args) {
        Book aBook = new Book( "QA76.73.J38S265", "Walter Savitch, Kenrich Mock", "Absolute Java", 
                     "Addison-Wesley", 2009 );
	System.out.println(aBook);
    }
}
