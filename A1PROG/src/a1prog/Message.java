package a1prog;

import javax.swing.JOptionPane;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
//Used chat to make corrections for certain problems
// Class for creating and managing text messages
public class Message {
    
    // Static variables to track messages
    private static int messageCounter = 0; // Counter for total messages created
    private static List<Message> allMessages = new ArrayList<>(); // List of all messages
    
    // Methods for message operations (not yet implemented and its only added for the unittest)
    public static List<Message> getSentMessages() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
    public static String getLongestMessage() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
    public static Message findMessageByID(String targetID) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
    public static List<Message> searchMessagesByRecipient(String string) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
    public static boolean deleteMessageByHash(String hash) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
    public static String generateSentMessageReport() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
    // Instance variables for each message
    private String recipient; // Phone number of message recipient
    private String messageText; // The actual message content
    private String messageID; // Unique ID for the message
    private String messageHash; // Hash combining ID and message text
    private String flag; // Status flag (Sent/Stored/Disregard)
    
    // Constructor to create a new message
    public Message(String recipient, String messageText, String flag) {
        this.recipient = recipient;
        this.messageText = messageText;
        this.messageID = createMessageID(); // Generate unique ID
        this.messageHash = createMessageHash(); // Create hash value
        this.flag = flag;
        allMessages.add(this); // Add to global message list
        messageCounter++; // Increment total message count
    }
    
    // Create unique message ID (format: MSG01, MSG02, etc.)
    private String createMessageID() {
        return "MSG" + String.format("%02d", messageCounter + 1);
    }
    
    // Create message hash by combining ID and uppercase message text
    private String createMessageHash() {
        return messageID + ": " + messageText.toUpperCase();
    }
    
    // Start interactive messaging session
    public static void startMessagingSession(int numMessages) {
        // Loop to create specified number of messages
        for (int i = 0; i < numMessages; i++) {
            // Get recipient phone number
            String recipient = JOptionPane.showInputDialog("Enter recipient number:");
            
            // Validate South African phone number format
            if (!Pattern.matches("^(\\+27|0)[6-8][0-9]{8}$", recipient)) {
                JOptionPane.showMessageDialog(null, "Invalid cell number. Try again.");
                i--; // Retry this message
                continue;
            }
            
            // Get message text and flag
            String text = JOptionPane.showInputDialog("Enter your message:");
            String flag = JOptionPane.showInputDialog("Enter message flag (Sent / Stored / Disregard):");
            
            // Create new message
            new Message(recipient, text, flag);
        }
        
        // Show completion message
        JOptionPane.showMessageDialog(null, "Messages sent: " + messageCounter);
    }
    
    // Reset message counter and clear all messages
    public static void resetCounters() {
        messageCounter = 0;
        allMessages.clear();
    }
    
    // Get total number of messages created
    public static int returnTotalMessages() {
        return messageCounter;
    }
    
    // Get list of all messages
    public static List<Message> getAllMessages() {
        return allMessages;
    }
    
    // Getter methods for message properties
    public String getRecipient() {
        return recipient;
    }
    
    public String getMessage() {
        return messageText;
    }
    
    public String getMessageID() {
        return messageID;
    }
    
    public String getMessageHash() {
        return messageHash;
    }
    
    public String getFlag() {
        return flag;
    }
    
    // Create formatted string with message information
    public String printMessageInfo() {
        return "ID: " + messageID + ", To: " + recipient + ", Message: " + messageText + ", Flag: " + flag;
    }
}