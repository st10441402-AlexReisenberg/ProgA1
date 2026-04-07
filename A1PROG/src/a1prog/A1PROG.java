package a1prog;

import javax.swing.JOptionPane;

// Main application class for user registration, login, and messaging
public class A1PROG {
    
    public static void main(String[] args) {
        // Variables for user input
        String userName, passWord, cellNumber;
        boolean isRegistered = false;
        UserClass registeredUser = null;
        
        // === Registration Phase ===
        // Keep asking for registration until valid inputs are provided
        while (!isRegistered) {
            JOptionPane.showMessageDialog(null, "=== User Registration ===");
            
            // Get username and check if user cancelled
            userName = JOptionPane.showInputDialog("Enter your Username:");
            if (userName == null) return;
            boolean isUsernameValid = LoginClass.checkUserName(userName);
            
            // Get password and check if user cancelled
            passWord = JOptionPane.showInputDialog("Enter your Password:");
            if (passWord == null) return;
            boolean isPasswordValid = LoginClass.checkPasswordComplexity(passWord);
            
            // Get cell number and check if user cancelled
            cellNumber = JOptionPane.showInputDialog("Enter your Cellphone Number:");
            if (cellNumber == null) return;
            boolean isCellValid = LoginClass.checkCellNumber(cellNumber);
            
            // If all inputs are valid, create user and complete registration
            if (isUsernameValid && isPasswordValid && isCellValid) {
                registeredUser = new UserClass(cellNumber, userName, passWord);
                isRegistered = true;
                JOptionPane.showMessageDialog(null, "Registration successful!");
            } else {
                JOptionPane.showMessageDialog(null, "Invalid input(s). Please try again.");
            }
        }
        
        // === Login Phase ===
        boolean loginSuccessful = false;
        int attemptCount = 0;
        final int MAX_ATTEMPTS = 3; // Maximum number of login attempts allowed
        
        // Allow up to 3 login attempts
        while (!loginSuccessful && attemptCount < MAX_ATTEMPTS) {
            // Get login credentials
            String loginUser = JOptionPane.showInputDialog("Enter username to login:");
            if (loginUser == null) return;
            
            String loginPass = JOptionPane.showInputDialog("Enter password to login:");
            if (loginPass == null) return;
            
            // Check if login credentials match registered user
            loginSuccessful = LoginClass.loginUser(loginUser, loginPass, registeredUser);
            attemptCount++;
            
            // Handle login result
            if (!loginSuccessful && attemptCount < MAX_ATTEMPTS) {
                JOptionPane.showMessageDialog(null, "Incorrect. Attempts left: " + (MAX_ATTEMPTS - attemptCount));
            } else if (!loginSuccessful) {
                JOptionPane.showMessageDialog(null, "Access denied.");
                return; // Exit program after failed attempts
            }
        }
        
        // === Messaging Phase ===
        // Welcome user and start messaging session
        JOptionPane.showMessageDialog(null, "Welcome " + registeredUser.getUserName());
        
        // Get number of messages to send
        String numStr = JOptionPane.showInputDialog("How many messages would you like to send?");
        
        try {
            int numMessages = Integer.parseInt(numStr);
            
            // Start messaging session if valid number entered
            if (numMessages > 0) {
                Message.startMessagingSession(numMessages);
            } else {
                JOptionPane.showMessageDialog(null, "Invalid number.");
            }
        } catch (Exception e) {
            // Handle invalid number input
            JOptionPane.showMessageDialog(null, "Error: Please enter a valid number.");
        }
    }
}