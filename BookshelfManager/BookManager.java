/**
 * This class is to store and process (add a new book, delete a book
 * search for a book, sort the books in the database etc.) 
 * the books in the database. 
 * You should fill in the method bodies given below.
 * If necessary, you can add any other helper methods. 
 */

public class BookManager {
    private Book[] books;  // Array to hold books
    private int bookCount;   // Counter to track the number of books
    private int capacity; 

    // Constructor
    public BookManager(int capacity) {
        this.books  = new Book[capacity];  // Initialize array with a given capacity
        this.capacity = capacity; 
        this.bookCount = 0;  // No books initially
    }

    /**
     * It returns the number of books in the database. 
     * @return bookCount
     */
    public int getBookCount() {
    	return this.bookCount;
    }
    
    /**
     * It returns the array of books
     * @return books
     */
    public Book[] getBooks() {
    	return books; 
    }

    /**
     * This method adds a new book to the array. 
     * @param book
     * @return 	0: if another book with the same id exists
     * 			1: if the new book has been successfully added
     * 			2: if there is no available space in the array
     */
  public int addBook(Book book) {
        // Check if array is full
        if (bookCount >= capacity) {
            return 2;
        }
        
        // Check if book with same ID already exists
        for (int i = 0; i < bookCount; i++) {
            if (books[i].getId().equals(book.getId())) {
                return 0;
            }
        }
        
        // Add the book
        books[bookCount] = book;
        bookCount++;
        return 1;
    }
    

    /**
     * This method deletes the book with the given id. 
     * @param book id
     * @return 	true: if the book has been successfully deleted.
     * 			false: if the book has not been found.  
     */
    public boolean deleteBook(String id) {
           for (int i = 0; i < bookCount; i++) {
        if (books[i].getId().equals(id)) {
            // Shift all books after the deleted one to the left
            for (int j = i; j < bookCount - 1; j++) {
                books[j] = books[j + 1];
            }
            // Clear the last slot and decrease count
            books[bookCount - 1] = null;
            bookCount--;
            return true; // Successfully deleted
        }
    }
    return false; // Not found
}
    /**
     * This method searches for a book with the given term (id or name)
     * @param searchTerm (id or name)
     * @return 	book[]: a list of books matching the search criteria is returned
     * 			null: if no book is found.
     */
    public Book[] searchBooks(String searchTerm) {
    	  // Temporary array to store matches (max possible = bookCount)
    Book[] tempResults = new Book[bookCount];
    int matchCount = 0;

    for (int i = 0; i < bookCount; i++) {
        // Match if ID or title contains the search term (case-insensitive)
        if (books[i].getId().toLowerCase().contains(searchTerm.toLowerCase()) ||
            books[i].getTitle().toLowerCase().contains(searchTerm.toLowerCase())) {

            tempResults[matchCount] = books[i];
            matchCount++;
        }
    }

    // No matches found
    if (matchCount == 0) {
        return null;
    }

    // Copy matching results to a new array of correct size
    Book[] results = new Book[matchCount];
    for (int i = 0; i < matchCount; i++) {
        results[i] = tempResults[i];
    }

    return results;
}     
    /**
     * This method sorts the books in ascending alphabetical order
     * based on the book title.
     */
    public void sortBooks()
    {
    for (int i = 0; i < bookCount - 1; i++) {
        for (int j = 0; j < bookCount - i - 1; j++) {
            // Compare titles alphabetically (case-insensitive)
            if (books[j].getTitle().compareToIgnoreCase(books[j + 1].getTitle()) > 0) {
                // Swap books[j] and books[j + 1]
                Book temp = books[j];
                books[j] = books[j + 1];
                books[j + 1] = temp;
             }
        }
    }
}
}

    
