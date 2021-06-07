/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.cqu.olmclibrarysystem.presenter;

import edu.cqu.olmclibrarysystem.model.Member;
import edu.cqu.olmclibrarysystem.model.MemberBorrowedBook;
import java.util.List;
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
public class MemberPersisterTest {
    
    public MemberPersisterTest() {
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
     * Test of findMemberByMemberId method, of class MemberPersister.
     */
    @Test
    public void testFindMemberByMemberId() {
        System.out.println("findMemberByMemberId");
        int id = 2;
        MemberPersister instance = new MemberPersister();
        String expResult = instance.findMemberByMemberId(2).getFullName();
        String result = instance.findMemberByMemberId(id).getFullName();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

   
    
}
