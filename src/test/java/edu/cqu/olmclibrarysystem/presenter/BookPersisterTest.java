/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.cqu.olmclibrarysystem.presenter;

import edu.cqu.olmclibrarysystem.model.Author;
import edu.cqu.olmclibrarysystem.model.Book;
import edu.cqu.olmclibrarysystem.model.Member;
import java.util.Date;
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
public class BookPersisterTest {
    
    public BookPersisterTest() {
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
     * Test of add method, of class BookPersister.
     */
    /*
    @Test
    public void testAdd() {
        System.out.println("add");
        Book book = null;
        BookPersister instance = new BookPersister();
        instance.add(book);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    */
    
    /**
     * Test of findAuthorByAuthorId method, of class BookPersister.
     */
    @Test
    public void testFindAuthorByAuthorId() {
        System.out.println("findAuthorByAuthorId");
        int id = 3;
        BookPersister instance = new BookPersister();
        String expResult = instance.findAuthorByAuthorId(3).getFullName();
        String result = instance.findAuthorByAuthorId(id).getFullName();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }
    /**
     * Test of getBookByBookId method, of class BookPersister.
     */
    @Test
    public void testGetBookByBookId() {
        System.out.println("getBookByBookId");
        int id = 6;
        BookPersister instance = new BookPersister();
        String expResult = instance.getBookByBookId(6).getTitle();
        String result = instance.getBookByBookId(id).getTitle();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }
    
     /**
     * Test of searchBooksByTitle method, of class BookPersister.
     */
    @Test
    public void testSearchBooksByTitle() {
        System.out.println("searchBooksByTitle");
        String bookTitle = "Games";
        BookPersister instance = new BookPersister();
        String expResult = instance.searchBooksByTitle("Games").toString();
        String result = instance.searchBooksByTitle(bookTitle).toString();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }
    
     /**
     * Test of findBooksIssuedToMember method, of class BookPersister.
     */
    @Test
    public void testFindBooksIssuedToMember() {
        System.out.println("findBooksIssuedToMember");
        Integer selectedMemberID = 2;
        BookPersister instance = new BookPersister();
        String expResult = instance.findBooksIssuedToMember(2).toString();
        String result = instance.findBooksIssuedToMember(selectedMemberID).toString();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }
    
}
