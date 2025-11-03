public class BookApp {
	/**
	 * This class is complete. Please do not change anything other than the student number here. 
	 * Please replace the following number with your student number. 
	 * Student number: 1234567
	 *
	 */
    public static void main(String[] args) {
        // Create a BookManager to handle the books
        BookManager manager = new BookManager(10);
        
        // Create a GUI object 
        BookGUI gui = new BookGUI(manager);
        
        // Initialize and display the GUI
        gui.initGUI();
    }
}
