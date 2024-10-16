package org.example;

import org.example.schedulers.CheckPendingRequestsScheduler;
import org.example.schedulers.ResetTokenScheduler;

public class Main {
    public static void main(String[] args) {
//        ResetTokenScheduler resetTokenScheduler = new ResetTokenScheduler();
//
//        resetTokenScheduler.run();
//
//        System.out.println("Token reset executed successfully.");

        CheckPendingRequestsScheduler scheduler = new CheckPendingRequestsScheduler();
        scheduler.run();
        System.out.println("Token double executed successfully.");

    }
}