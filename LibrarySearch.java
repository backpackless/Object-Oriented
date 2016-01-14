package librarysearch;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.StringTokenizer;

/** LibrarySearch class
 *
 * @author Ben Walker
 *
 * A class that adds, maintains, and searches for books and journals.
 * ACKNOWLEDGEMENT: Professor Song's code was used as the basis for this code
 */

public class LibrarySearch {

    private final ArrayList<Reference> docs;// A list for maintaining documents
    public static final String[] REFERENCE_TYPES = new String[]{"book", "journal", "b", "j"};
    Map<String, ArrayList<Integer>> hm = new HashMap<>();// A hashmap to store the index of title keywords

    /**
     * Creates an instance of LibrarySearch; creates an array list to
     * hold books and journals.
     */
    public LibrarySearch() {
	docs = new ArrayList<>();
    }
    
    /**
     * Add a doc to the reference list, add its tokens to a hash map
     * @param doc
     * @return true if added successfully, false if not added
     */
    public boolean addDoc( Reference doc) {
        /*Check if the reference already exists*/
        for (Reference doc1 : docs) {
            if (doc1.keyEquals(doc)) {
                System.out.println("Document already present in library");
                return false;
            }
        }
        /*If no matching doc was found, add it to the list*/
        docs.add(doc);
        
        /*adding indexes to the hash map*/
        StringTokenizer tokens = new StringTokenizer(doc.title);
        String temp;
        int index = docs.indexOf(doc);
        
        /*Tokenize the title*/
        while (tokens.hasMoreTokens()) {
            temp = tokens.nextToken();
            /*Convert current token to lower case*/
            temp = temp.toLowerCase();
            
            /*If the key is new, update the value array list*/
            if(!hm.containsKey(temp))
                hm.put(temp, new ArrayList<Integer>());
            /*If the value is new, add it to the value array list*/
            if(!hm.get(temp).contains(index))
                hm.get(temp).add(index);
        }
        
        return true;
    }
	
    /**
     * Create a book/journal from the input and add it to the appropriate list
     * @param input 
     */
    public void add( Scanner input ) {
	String type;
        
        /*Get user input, loop if the input is invalid*/
	do {
            System.out.print( "Enter a reference type (book or journal)> " );
            type = input.nextLine();
	} while( !matchedKeyword(type, REFERENCE_TYPES) );
		
        String callNumber = "";
        do {
            System.out.print( "Enter a call number> " );
            callNumber = input.nextLine();
        } while( callNumber.isEmpty() );
		
        String authors = "";
        if( type.equalsIgnoreCase("book") || type.equalsIgnoreCase("b") ) {
            System.out.print("Enter a list of authors separated by comma> ");
            authors = input.nextLine();
        }

        String title = "";
        do {
            System.out.print( "Enter a title> " );
            title = input.nextLine();
        } while( title.isEmpty() );

        String publisher = "";
	if( type.equalsIgnoreCase("book") || type.equalsIgnoreCase("b") ) {
            System.out.print("Enter a publisher> ");
            publisher = input.nextLine();
        }

	String organizer = "";
	if( type.equalsIgnoreCase("journal") || type.equalsIgnoreCase("j") ) {
            System.out.print("Enter an organizer> ");
            organizer = input.nextLine();
	}
        
        /*Loop for year input while it is outside the desired bounds*/
	int year = 0;
	do {
            System.out.print("Enter a year (between 1000 and 9999)>");
            String yearValue = input.nextLine();
            /*Try to parse the year as an integer, throw an error if it fails*/
            try {
                year = yearValue.isEmpty() ? 0 : Integer.parseInt(yearValue);
            } catch(NumberFormatException e) {
                System.out.println("ERROR: Year entered was not a number");
            }
	} while( year < 1000 || year > 9999 );
	
        /*depending on whether user input 'book' or 'journal', add the 
        corresponding object to the list*/
	boolean result;
	if( type.equalsIgnoreCase("book") || type.equalsIgnoreCase("b") ) {
            result = addDoc( new Book(callNumber, authors, title, publisher, year) );
            System.out.println("Adding Book...");
        }
        else {
            result = addDoc( new Journal(callNumber, title, organizer, year) );
            System.out.println("Adding Journal...");
        }
        /*display an error if the add failed*/
	if( !result ) 
            System.out.println( "Add failed: there is another reference with the same call number and year" );
    }

    /**
     * Checks if a keyword is in a list of tokens
     * @param keyword
     * @param tokens
     * @return true if keyword matches a token, false if no match
     */
    private boolean matchedKeyword( String keyword, String[] tokens ) {
        /*check if the current keyword matches any tokens*/
        for (String token : tokens) {
            if (keyword.equalsIgnoreCase(token)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Check if all the keywords are in a string
     * @param keywords
     * @param title
     * @return true if all keywords present in title, false otherwise
     */
    private boolean matchedKeywords( String[] keywords, String title ) {
	/*split the title of the doc, check if all keywords match a word in the
        title*/
        String[] tokens = title.split( "[ ,\n]+" );
        for (String keyword : keywords) {
            if (!matchedKeyword(keyword, tokens)) {
                return false;
            }
        }
        return true;
    }
    
    /**
     * Searches the list of docs for a match; uses the hash map if any keywords 
     * were entered
     * @param callNumber
     * @param keywords
     * @param startYear
     * @param endYear 
     */
    private void searchDocs( String callNumber, String[] keywords, int startYear, int endYear ) {
        //find the list on the hashmap for each keyword
        //find the intersection of these lists
        //turn intersection into the new list to search through
        ArrayList<Integer> master = new ArrayList<>();
       int check = 0;
       
        /*find intersection of hashmaps, if keywords were provided*/
        if(keywords != null) {
            master = Intersection(keywords);
            /*search only those records contained in master (the intersection of the hashmaps)*/
            for (int j = 0; j < master.size(); j++) {
                Reference doc = docs.get(master.get(j));
                if ((callNumber.equals("") || doc.getCallNumber().equalsIgnoreCase(callNumber)) && (doc.getYear() >= startYear && doc.getYear() <= endYear)) {
                    System.out.println(doc);
                    check = 1;
                }
            }
            
        /*if no keywords were provided (i.e. no master list) search all the records as usual*/    
        } else {
            for (Reference doc : docs) {
                if ((callNumber.equals("") || doc.getCallNumber().equalsIgnoreCase(callNumber)) && (keywords == null || matchedKeywords(keywords, doc.getTitle())) && (doc.getYear() >= startYear && doc.getYear() <= endYear)) {
                    System.out.println(doc);
                    check = 1;
                }
            }
        }
        
        if(check == 0)
            System.out.println("No matching records found");
    }
    
    /**
     * 
     * @param keywords
     * @return An array list of the intersection between all the hash map values 
     */
    private ArrayList Intersection (String[] keywords) {
        ArrayList<Integer> master = new ArrayList<>();
        ArrayList<Integer> temp = new ArrayList<>();
        
        for (String keyword : keywords) {
            temp = hm.get(keyword);

            if(temp.isEmpty())
                continue;
            /*if the master list hasn't been created, copy the hashmap values for current
            keyword to it*/
            if(master.isEmpty()) 
                master = temp;

            /*for each value for the current key, check for it in the master key.
            If the temp list has the value, but the master key doesn't, remove it from the master*/
            else {
                for (int j = 0; j < master.size(); j++) {
                    if(!temp.contains(master.get(j)))
                        master.remove(master.get(j));
                    }
            }
        }
        
        return master;
    }

    /**
     * Gather a search request and find the matched books and journals in the related list(s)
     * @param input 
     */
    public void search( Scanner input ) {
	
        /*get user input to be used in search*/
        System.out.print( "Enter a call number> " );
	String callNumber = input.nextLine();

	System.out.print( "Enter title keywords> " );
	String[] keywords = null;
	String line = input.nextLine();
	if( !line.isEmpty() )
            keywords = line.split( "[ ,\n]+" );
        
        /*set the keywords to lowercase so they match the hashmap*/
        if(keywords != null) {
            for(int i = 0; i < keywords.length; i++) {
                keywords[i] = keywords[i].toLowerCase();
            }
        }
        
        
	int startYear = Integer.MIN_VALUE, endYear = Integer.MAX_VALUE;
	boolean valid;
	do {
            valid = true;
            System.out.print("Enter a time period as startYear-endYear, or year> ");
            /*parse the start and end years based on the hyphen*/
            line = input.nextLine();
            if( !line.equals("") ) {
		int hyphen = line.indexOf('-');
		if( hyphen < 0 ) 
                    /*If no hyphen is present, attempt to parse the line as an
                    integer, and set start year equal to end year*/
                    try {
                        startYear = endYear = Integer.parseInt(line);
                    } catch (NumberFormatException e) {
                        System.out.println("ERROR: Year entered was not a number");
                        valid = false;
                    }
		else {
                    /*else, parse placement of hyphen, and set start/end years
                    accordingly*/
                    String startValue = line.substring(0, hyphen);
                    startYear = startValue.equals("") ? Integer.MIN_VALUE : Integer.parseInt(startValue);
                    String endValue = line.substring(hyphen + 1);
                    endYear = endValue.equals("") ? Integer.MAX_VALUE : Integer.parseInt(endValue);
                    if( startYear > endYear ) 
                        valid = false;
		}
            }
	} while( !valid );

	// search for matched references
	System.out.println( "Matched references: " );
	searchDocs( callNumber, keywords, startYear, endYear );
    }
    
    /**
     * Takes the name of an input file, parses the data from the file,
     * creates either a book or a journal object and adds it to the library
     * @param in 
     */
    public void LoadRecords (String in) {
        /*variable initialization*/
        Scanner inputStream = null;
        String type, line, callnumber = null, authors = "", title = null, publisher = null,
                year = null, organization = null;
        int iYear = 0, count = 0; //this variable holds the year converted from a string to int
        boolean result;
        
        /*try to open the file supplied by the user, fatal error if failed*/
        try {
            inputStream = new Scanner(new FileInputStream(in));
        } catch(FileNotFoundException e) {
            System.out.println("Error: File (" + in + ") does not exist");
            System.exit(0);
        }
        
        /*perform loop while the file has more lines present*/
        while(inputStream.hasNextLine()) {            
            StringTokenizer tokens = new StringTokenizer(inputStream.nextLine(), "=");
            
            /*get the type of data entry*/
            tokens.nextToken();
            type = tokens.nextToken();
            type = type.substring(2, type.length() - 1);

            /*parse other info based on data type (i.e. authors, call number etc.)*/
            /*add a book from the file*/
            if(type.equals("book")) {

                line = inputStream.nextLine();
                
                /*loop through the supplied book info in the file, parsing each piece of info*/
                while(!line.equals("") && inputStream.hasNextLine()) {
                    tokens = new StringTokenizer(line, "=");
                    
                    switch(tokens.nextToken()) {
                        case "callnumber ":
                            callnumber = tokens.nextToken();
                            /*the below calculation removes the quotations from the callnumber*/
                            callnumber = callnumber.substring(2, callnumber.length() - 1);
                            System.out.println(callnumber);
                            break;
                        case "authors ":
                            authors = tokens.nextToken();
                            authors = authors.substring(2, authors.length() - 1);
                            System.out.println(authors);
                            break;
                        case "title ":
                            title = tokens.nextToken();
                            title = title.substring(2, title.length() - 1);
                            System.out.println(title);
                            break;
                        case "publisher ":
                            publisher = tokens.nextToken();
                            publisher = publisher.substring(2, publisher.length() - 1);
                            System.out.println(publisher);
                            break;
                        case "year ":
                            year = tokens.nextToken();
                            year = year.substring(2, year.length() - 1);
                            iYear = Integer.parseInt(year);
                            System.out.println(year);
                            break;
                        default:
                            System.out.println("Error Reading File");
                            System.exit(0);
                    }
                    line = inputStream.nextLine();
                }
                
                /*try to create a new book object and add it to the list of docs*/
                /*report success and failure*/
                result = addDoc( new Book(callnumber, authors, title, publisher, iYear) );
                if(!result)
                    System.out.println("Error Adding Book");
                else {
                    System.out.println("Book Added\n");
                    count++;
                }
                
            /*add a journal from the file*/    
            } else if(type.equals("journal")) {
                line = inputStream.nextLine();

                while(!line.equals("") && inputStream.hasNextLine()) {
                    tokens = new StringTokenizer(line, "=");

                    switch(tokens.nextToken()) {
                        case "callnumber ":
                            callnumber = tokens.nextToken();
                            callnumber = callnumber.substring(2, callnumber.length() - 1);
                            System.out.println(callnumber);
                            break;
                        case "title ":
                            title = tokens.nextToken();
                            title = title.substring(2, title.length() - 1);
                            System.out.println(title);
                            break;
                        case "organization ":
                            organization = tokens.nextToken();
                            organization = organization.substring(2, organization.length() - 1);
                            System.out.println(organization);
                            break;
                        case "year ":
                            year = tokens.nextToken();
                            year = year.substring(2, year.length() - 1);
                            iYear = Integer.parseInt(year);
                            System.out.println(year);
                            break;
                        default:
                            System.out.println("Error Reading File");
                            System.exit(0);
                    }
                    line = inputStream.nextLine();
                }
                
                /*try to create a new journal object and add it to the list of docs*/
                /*report success and failure*/
                result = addDoc( new Journal(callnumber, title, organization, iYear) );
                if(!result)
                    System.out.println("Error Adding Journal");
                else {
                    System.out.println("Journal Added\n");
                    count++;
                }
            }
        }
        
        /*close the input file*/
        inputStream.close();
        System.out.println(count + " records added from file: " + in);
    }
    
    /**
     * Writes all docs in the library to a text file when the program closes.
     * The text file is overwritten each time the method is called
     * @param out 
     */
    public void WriteToFile (String out) {
        PrintWriter outputStream = null;
        int i;
        
        /*try opening the file for writing, if it fails, display error and exit*/
        try {
            outputStream = new PrintWriter(new FileOutputStream(out));
        } catch(FileNotFoundException e) {
            System.out.println("Error Opening File: " + out);
            System.exit(0);
        }
        
        /*loop through all docs*/
        for(i = 0; i < docs.size(); i++) {
            
            /*check if current doc is a book object*/
            if(docs.get(i) instanceof Book) {
                /*print all data in the correct format*/
                outputStream.println("type = \"book\"");
                outputStream.println("callnumber = \"" + docs.get(i).callNumber + "\"");
                outputStream.println("authors = \"" + docs.get(i).authors + "\"");
                outputStream.println("title = \"" + docs.get(i).title + "\"");
                outputStream.println("publisher = \"" + docs.get(i).publisher + "\"");
                outputStream.println("year = \"" + docs.get(i).year + "\"");
                outputStream.println("");
            
            /*check if current doc is a journal object*/
            } else if(docs.get(i) instanceof Journal) {
                /*print all data in correct format*/
                outputStream.println("type = \"journal\"");
                outputStream.println("callnumber = \"" + docs.get(i).callNumber + "\"");
                outputStream.println("title = \"" + docs.get(i).title + "\"");
                outputStream.println("organization = \"" + docs.get(i).organizer + "\"");
                outputStream.println("year = \"" + docs.get(i).year + "\"");
                outputStream.println("");
            }
            
        }
        
        /*close the file for writing*/
        outputStream.close();
        System.out.println(docs.size() + " records written to file: " + out);
    }
	
    /**
     * Asks for user input, runs program
     * @param args 
     */
    public static void main( String[] args ) {
	Scanner input = new Scanner( System.in );
	LibrarySearch library = new LibrarySearch();
	String command;
	do {
            System.out.print( "Enter add, search, or quit> " );
            command = input.nextLine();
            if( command.equalsIgnoreCase("add") || command.equalsIgnoreCase("a") )
		library.add( input );
            else if( command.equalsIgnoreCase("search") || command.equalsIgnoreCase("s") )
		library.search( input );			
	} while( !command.equalsIgnoreCase("quit") && !command.equalsIgnoreCase("q") );
    }
}
