/**
 * This class is for the GUI of the book manager.
 * The class is almost complete.
 * You must only add the code to add a new sort button along with its event handler.
 * You must not change anything else in the class.  
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

public class BookGUI {
    private BookManager manager; // Reference to the BookManager
    private JFrame frame;          // Main frame for the GUI
    private JTextField idField, titleField, authorField, notesField, searchField; // Input fields
    private JTable bookTable;
    private DefaultTableModel tableModel;

    // Constructor
    public BookGUI(BookManager manager) {
        this.manager = manager; // Initialize the BookManager
    }

    // Initialize and setup the GUI
    public void initGUI() {
        // Main Frame
        frame = new JFrame("Book Manager");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 500);
        frame.setLayout(new BorderLayout());

        // Panel for Input Fields and Buttons using GridBagLayout
        JPanel inputPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5); // Space between components

        // Input Fields
        idField = createLargerTextField();
        titleField = createLargerTextField();
        authorField = createLargerTextField();
        notesField = createLargerTextField();
        searchField = createLargerTextField();

        // Add Labels and Input Fields to the Panel using GridBagLayout
        addToPanel(inputPanel, new JLabel("Book ID:"), gbc, 0, 0);
        addToPanel(inputPanel, idField, gbc, 1, 0);
        addToPanel(inputPanel, new JLabel("Book Title:"), gbc, 0, 1);
        addToPanel(inputPanel, titleField, gbc, 1, 1);
        addToPanel(inputPanel, new JLabel("Author:"), gbc, 0, 2);
        addToPanel(inputPanel, authorField, gbc, 1, 2);
        addToPanel(inputPanel, new JLabel("Notes:"), gbc, 0, 3);
        addToPanel(inputPanel, notesField, gbc, 1, 3);
        addToPanel(inputPanel, new JLabel("Search by Name/ID:"), gbc, 0, 4);
        addToPanel(inputPanel, searchField, gbc, 1, 4);

        // Buttons
        JButton addButton = new JButton("Add Book");
        JButton deleteButton = new JButton("Delete Book");
        JButton searchButton = new JButton("Search Books");
        JButton displayButton = new JButton("Display All Books");
        JButton clearButton = new JButton("Clear Fields");
        JButton sortButton = new JButton("Sort");

        // Add Buttons to the Panel
        addToPanel(inputPanel, addButton, gbc, 0, 5);
        addToPanel(inputPanel, deleteButton, gbc, 1, 5);
        addToPanel(inputPanel, searchButton, gbc, 0, 6);
        addToPanel(inputPanel, displayButton, gbc, 1, 6);
        addToPanel(inputPanel, clearButton, gbc, 0, 7);
        addToPanel(inputPanel, sortButton, gbc, 1, 7);
        
        // Table Panel
        tableModel = new DefaultTableModel(new String[]{"ID", "Title", "Author", "Notes"}, 0){
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // All cells are not editable
            }
        };
        bookTable = new JTable(tableModel);
        //bookTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF); // Disable auto-resizing of columns
        bookTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);


        // Set column widths
        TableColumn column = bookTable.getColumnModel().getColumn(0);
        column.setPreferredWidth(50); // ID column
        column = bookTable.getColumnModel().getColumn(1);
        column.setPreferredWidth(150); // Title column
        column = bookTable.getColumnModel().getColumn(2);
        column.setPreferredWidth(250); // Author column
        column = bookTable.getColumnModel().getColumn(3);
        column.setPreferredWidth(400); // Notes column

        // Wrap the table in a scroll pane
        JScrollPane scrollPane = new JScrollPane(bookTable);

        // Add the scroll pane to the GUI
        frame.add(scrollPane, BorderLayout.CENTER);
        
        // Add Components to Frame
        frame.add(inputPanel, BorderLayout.NORTH);
        frame.add(scrollPane, BorderLayout.CENTER);

        // Add action listeners for the buttons
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addBook();
            }
        });

        //Event Handlers
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteBook();
            }
        });

        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                searchBooks();
            }
        });

        displayButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                displayBooks();
            }
        });

        clearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clearFields();
            }
        });

        // Make the frame visible
        frame.setVisible(true);
    }

    // Method to add components to the panel with GridBagLayout
    //x: column, y: row (e.g. clearButton is in 0th column, 7th row)
    //sortButton will be placed in 1st column, 7th row.
    private void addToPanel(JPanel panel, Component comp, GridBagConstraints gbc, int x, int y) {
        gbc.gridx = x;
        gbc.gridy = y;
        gbc.gridwidth = 1;  // Ensure each component occupies one column
        gbc.fill = GridBagConstraints.HORIZONTAL; // Make components fill horizontally
        panel.add(comp, gbc);
    }

    // Method to create a larger JTextField
    private JTextField createLargerTextField() {
        JTextField textField = new JTextField();
        textField.setPreferredSize(new Dimension(250, 30));  // Set the size of the text field (width, height)
        return textField;
    }

    // Method to add a new book to the database
    private void addBook() {
        String id = idField.getText();
        String title = titleField.getText();
        String author = authorField.getText();
        String notes = notesField.getText();

        if (id.isEmpty() || title.isEmpty() || author.isEmpty() || notes.isEmpty()) {
            JOptionPane.showMessageDialog(frame, "All fields must be filled!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int newBookResult = manager.addBook(new Book(id, title, author, notes));
        if (newBookResult==1) {
            JOptionPane.showMessageDialog(frame, "Book added successfully!");
            clearFields();
            displayBooks(); // Refresh the list to show the new book
        } else if(newBookResult==0) {
        	clearFields();
            JOptionPane.showMessageDialog(frame, "Book with this ID already exists!", "Error", JOptionPane.ERROR_MESSAGE);
        }
        else {
        	clearFields();
        	JOptionPane.showMessageDialog(frame, "There is no space left in the array for a new Book! ", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Method to delete a book
    private void deleteBook() {
        String id = idField.getText();

        if (id.isEmpty()) {
            JOptionPane.showMessageDialog(frame, "Please enter a Book ID to delete!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        boolean success = manager.deleteBook(id);
        if (success) {
            JOptionPane.showMessageDialog(frame, "Book deleted successfully!");
            clearFields();
            displayBooks(); // Refresh the list to show the updated books
        } else {
            JOptionPane.showMessageDialog(frame, "Book not found!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Method to search for a Book
    private void searchBooks() {
        String query = searchField.getText();

        if (query.isEmpty()) {
            JOptionPane.showMessageDialog(frame, "Please enter a Book Name or ID to search!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String searchTerm = searchField.getText();
        Book[] results = manager.searchBooks(searchTerm);

        if (results == null || results.length == 0) {
            JOptionPane.showMessageDialog(null, "No Books found!");
        } else {
            updateTable(results);
        }
        clearFields();
    }
    
    // Method to update the table with books
    private void updateTable(Book[] books) {
        tableModel.setRowCount(0); // Clear existing rows
        for (Book book : books) {
            if (book != null) {
                tableModel.addRow(new Object[]{
                    book.getId(),
                    book.getTitle(),
                    book.getAuthor(),
                    book.getNotes()
                });
            }
        }
    }
    
    // Method to display all books in the database. 
    private void displayBooks() {
    	
    	tableModel.setRowCount(0); // Clear existing rows
        for (int i=0; i<manager.getBookCount(); i++) {
        	Book nextBook = manager.getBooks()[i];
            tableModel.addRow(new Object[]{nextBook.getId(), nextBook.getTitle(), nextBook.getAuthor(), nextBook.getNotes()});
        }
    }

    // Method to clear all input fields. 
    private void clearFields() {
        idField.setText("");
        titleField.setText("");
        authorField.setText("");
        notesField.setText("");
        searchField.setText("");
    }
}
