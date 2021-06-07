/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.cqu.olmclibrarysystem.presenter;

import edu.cqu.olmclibrarysystem.model.Author;
import edu.cqu.olmclibrarysystem.model.Book;
import edu.cqu.olmclibrarysystem.model.Donor;
import edu.cqu.olmclibrarysystem.model.DonorDonatedBook;
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
public class DonorPersisterTest {
    
    public DonorPersisterTest() {
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
     * Test of findAllDonorDonatedBookByDonorId method, of class DonorPersister.
     */
    @Test
    public void testFindAllDonorDonatedBookByDonorId() {
        System.out.println("findAllDonorDonatedBookByDonorId");
        int id = 0;
        DonorPersister instance = new DonorPersister();
        List<DonorDonatedBook> expResult = instance.findAllDonorDonatedBookByDonorId(2);
        List<DonorDonatedBook> result = instance.findAllDonorDonatedBookByDonorId(id);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    
}
