package a1prog;

// Simple user class to store user information
public class UserClass {
    
    // User's cell phone number
    String cellNumber;
    
    // User's login username
    String userName;
    
    // User's password
    String password;
    
    // Constructor to create a new user
    public UserClass(String cellNumber, String userName, String password) {
        this.cellNumber = cellNumber;
        this.userName = userName;
        this.password = password;
    }
    
    // Get the user's cell number
    public String getCellNumber() {
        return cellNumber;
    }
    
    // Get the user's username
    public String getUserName() {
        return userName;
    }
    
    // Get the user's password
    public String getPassword() {
        return password;
    }
}