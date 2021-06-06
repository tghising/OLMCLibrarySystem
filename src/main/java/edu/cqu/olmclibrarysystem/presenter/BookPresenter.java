package edu.cqu.olmclibrarysystem.presenter;

import edu.cqu.olmclibrarysystem.model.Author;
import edu.cqu.olmclibrarysystem.model.Book;
import edu.cqu.olmclibrarysystem.model.Member;
import java.util.Date;
import java.util.List;

/**
 * IViewDashboard: Presenter for User
 *
 * @author Tikaraj Ghising
 */
public class BookPresenter {

    private BookPersister persister;

    public BookPresenter() {
        this.persister = new BookPersister();
    }

    public void addBook(Book book) {
        persister.add(book);
    }

    public void addAuthor(Author author) {
        persister.addAuthor(author);
    }

    public String deleteBook(int id) {
        return persister.deleteBook(id);
    }

    public List<Book> getAllBooks() {
        return persister.findAllBooks();
    }

    public List<Author> getAllAuthors() {
        return persister.findAllAuthors();
    }

    public Author findAuthorByAuthorId(int id) {
        return persister.findAuthorByAuthorId(id);
    }

    public List<Book> findBooksByTitle(String keyword) {
        return persister.searchBooksByTitle(keyword);
    }

    public List<Book> findBooksByAuthor(String keyword) {
        return persister.searchBooksByAuthor(keyword);
    }
    
    public List<Member> getMembersWithIssuedBooks() {
        return persister.findAllMembersWithIssuedBooks();
    }
    
    public List<Book> getBooksIssuedToMember(Integer selectedMemberID) {
        return persister.findBooksIssuedToMember(selectedMemberID);
    }
    
    public void returnBook(Date currentDate, Member selectedMember, Book selectedBook) {
        persister.returnBook(currentDate, selectedMember, selectedBook);
    }

}
