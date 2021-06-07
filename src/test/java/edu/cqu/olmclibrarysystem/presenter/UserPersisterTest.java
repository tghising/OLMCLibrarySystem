/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.cqu.olmclibrarysystem.presenter;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author apostol
 */
public class UserPersisterTest {
    
    public UserPersisterTest() {
    }
    
    @BeforeAll
    public static void setUpClass() {
    }
    
    @AfterAll
    public static void tearDownClass() {
    }
    
    @BeforeEach
    public void setUp() {
    }
    
    @AfterEach
    public void tearDown() {
    }

    /**
     * Test of findUserByEmail method, of class UserPersister.
     */
    @Test
    public void testFindUserByEmail() {
        System.out.println("findUserByEmail");
        String email = "admin@gmail.com";
        UserPersister instance = new UserPersister();
        String expResult = instance.findUserByEmail("admin@gmail.com").toString() ;
        String result = instance.findUserByEmail(email).toString();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }


    /**
     * Test of isUserExist method, of class UserPersister.
     */
    @Test
    public void testIsUserExist() {
        System.out.println("isUserExist");
        String email = "admin@gmail.com";
        String password = "admin12345";
        UserPersister instance = new UserPersister();
        boolean expResult = true;
        boolean result = instance.isUserExist(email, password);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

  
    /**
     * Test of registerUser method, of class UserPersister.
     */
    //@Test
    /*
    public void testRegisterUser() {
        System.out.println("registerUser");
        User user = "";
        UserPersister instance = new UserPersister();
        instance.registerUser(user);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    */

   
}
