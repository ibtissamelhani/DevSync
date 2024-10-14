package org.example;

import org.example.schedulers.ResetTokenScheduler;

public class Main {
    public static void main(String[] args) {
        // Create an instance of the ResetTokenScheduler
        ResetTokenScheduler resetTokenScheduler = new ResetTokenScheduler();

        // Execute the run method to test the token reset functionality
        resetTokenScheduler.run();

        // Optional: You could log or print out information to confirm tokens were reset
        System.out.println("Token reset executed successfully.");
    }
}