package com.test;

import org.testng.annotations.Test;

// the lesson class has 3 test cases: login, admin link and logout
// I do not open a browser yet (Selenium starts on Day 5), so each test only prints its step
public class OrangeTest {

    @Test(priority = 1)
    public void loginTest() {
        System.out.println("Login to the application");
    }

    @Test(priority = 2)
    public void adminLinkTest() {
        System.out.println("Verify the admin link");
    }

    @Test(priority = 3)
    public void logoutTest() {
        System.out.println("Verify logout");
    }
}
