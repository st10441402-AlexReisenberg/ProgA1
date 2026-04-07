import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import a1prog.LoginClass;
import a1prog.UserClass;
import a1prog.Message;
import java.util.List;

public class UnitTest {
    private UserClass testUser;
    
    @Before
    public void setUp() {
        // Reset counters and clear any existing messages first
        Message.resetCounters();
        
        // Create test user
        testUser = new UserClass("0821234567", "user_name", "Passw0rd!");
        
        // Populate test data - ensure consistent phone number formats
        new Message("+27834557896", "Did you get the cake?", "Sent");
        new Message("+27838884567", "Where are you? You are late! I have asked you to be on time.", "Stored");
        new Message("+27834484567", "Yohoooo, I am at your gate.", "Disregard");
        new Message("+27838884567", "It is dinner time!", "Sent"); // Changed from "0838884567" to "+27838884567"
        new Message("+27838884567", "Ok, I am leaving without you.", "Stored");
    }
    
    // ------------------- PART 1: VALIDATION TESTS -------------------
    
    @Test
    public void testCheckUserName_Valid() {
        assertTrue("Username validation should pass for 'kyl_1'", 
                  LoginClass.checkUserName("kyl_1"));
    }
    
    @Test
    public void testCheckPasswordComplexity_Valid() {
        assertTrue("Password complexity validation should pass for 'Ch&&sec@ke99!'", 
                  LoginClass.checkPasswordComplexity("Ch&&sec@ke99!"));
    }
    
    @Test
    public void testCheckCellNumber_ValidInternational() {
        assertTrue("Cell number validation should pass for '+27821234567'", 
                  LoginClass.checkCellNumber("+27821234567"));
    }
    
    @Test
    public void testLoginUser_Success() {
        assertTrue("Login should succeed with correct credentials", 
                  LoginClass.loginUser("user_name", "Passw0rd!", testUser));
    }
    
    // ------------------- PART 2 & 3: FUNCTIONAL TESTS -------------------
    
    @Test
    public void testSentMessagesArrayCorrectlyPopulated() {
        List<Message> sent = Message.getSentMessages();
        assertNotNull("Sent messages list should not be null", sent);
        assertEquals("Should have exactly 2 sent messages", 2, sent.size());
        
        // Check messages exist (not checking order since it might vary)
        boolean foundCakeMessage = false;
        boolean foundDinnerMessage = false;
        
        for (Message msg : sent) {
            if ("Did you get the cake?".equals(msg.getMessage())) {
                foundCakeMessage = true;
            } else if ("It is dinner time!".equals(msg.getMessage())) {
                foundDinnerMessage = true;
            }
        }
        
        assertTrue("Should contain cake message", foundCakeMessage);
        assertTrue("Should contain dinner message", foundDinnerMessage);
    }
    
    @Test
    public void testLongestMessageDetection() {
        String longest = Message.getLongestMessage();
        assertNotNull("Longest message should not be null", longest);
        assertEquals("Should return the longest message", 
                    "Where are you? You are late! I have asked you to be on time.", longest);
    }
    
    @Test
    public void testSearchMessageByID() {
        List<Message> allMessages = Message.getAllMessages();
        assertNotNull("All messages list should not be null", allMessages);
        assertFalse("Should have messages to test with", allMessages.isEmpty());
        
        String targetID = allMessages.get(0).getMessageID();
        assertNotNull("Message ID should not be null", targetID);
        
        Message result = Message.findMessageByID(targetID);
        assertNotNull("Should find message by ID", result);
        assertEquals("Should return correct message", 
                    allMessages.get(0).getMessage(), result.getMessage());
    }
    
    @Test
    public void testSearchMessagesByRecipient() {
        List<Message> results = Message.searchMessagesByRecipient("+27838884567");
        assertNotNull("Search results should not be null", results);
        assertEquals("Should find exactly 3 messages for this recipient", 3, results.size());
        
        // Verify the messages contain expected content
        boolean foundLateMessage = false;
        boolean foundDinnerMessage = false;
        boolean foundLeavingMessage = false;
        
        for (Message msg : results) {
            String content = msg.getMessage();
            if (content.contains("Where are you? You are late!")) {
                foundLateMessage = true;
            } else if (content.equals("It is dinner time!")) {
                foundDinnerMessage = true;
            } else if (content.equals("Ok, I am leaving without you.")) {
                foundLeavingMessage = true;
            }
        }
        
        assertTrue("Should contain late message", foundLateMessage);
        assertTrue("Should contain dinner message", foundDinnerMessage);
        assertTrue("Should contain leaving message", foundLeavingMessage);
    }
    
    @Test
    public void testDeleteMessageByHash() {
        List<Message> allMessages = Message.getAllMessages();
        assertNotNull("All messages should not be null", allMessages);
        assertTrue("Should have at least 2 messages", allMessages.size() >= 2);
        
        int originalCount = allMessages.size();
        String hash = allMessages.get(1).getMessageHash(); // second message
        assertNotNull("Message hash should not be null", hash);
        
        boolean deleted = Message.deleteMessageByHash(hash);
        assertTrue("Deletion should succeed", deleted);
        
        int newCount = Message.getAllMessages().size();
        assertEquals("Should have one less message after deletion", 
                    originalCount - 1, newCount);
    }
    
    @Test
    public void testReportGeneration() {
        String report = Message.generateSentMessageReport();
        assertNotNull("Report should not be null", report);
        assertFalse("Report should not be empty", report.trim().isEmpty());
        
        // Check for expected report headers/content
        assertTrue("Report should contain 'Message Hash'", 
                  report.contains("Message Hash"));
        assertTrue("Report should contain 'Recipient'", 
                  report.contains("Recipient"));
        
        // Check for specific message content
        assertTrue("Report should contain cake message", 
                  report.contains("Did you get the cake?"));
        assertTrue("Report should contain dinner message", 
                  report.contains("It is dinner time!"));
    }
    
    @Test
    public void testResetCounters() {
        // Ensure we have messages before reset
        assertTrue("Should have messages before reset", 
                  Message.getAllMessages().size() > 0);
        
        Message.resetCounters();
        
        assertEquals("Total messages should be 0 after reset", 
                    0, Message.returnTotalMessages());
        assertTrue("All messages list should be empty after reset", 
                  Message.getAllMessages().isEmpty());
    }
}