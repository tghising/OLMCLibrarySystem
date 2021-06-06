package edu.cqu.olmclibrarysystem.presenter;

import edu.cqu.olmclibrarysystem.model.Author;
import edu.cqu.olmclibrarysystem.model.Book;
import edu.cqu.olmclibrarysystem.model.Donor;
import edu.cqu.olmclibrarysystem.model.DonorDonatedBook;
import java.util.List;

/**
 * DonorPresenter: Presenter for Donor
 *
 * @author Tikaraj Ghising
 */
public class DonorPresenter {

    private DonorPersister persister;

    public DonorPresenter() {
        this.persister = new DonorPersister();
    }

    public void add(Donor donor) {
        persister.addDonor(donor);
    }

    public List<Donor> getAllDonors() {
        return persister.findAllDonors();
    }

    public List<Author> getAllAuthors() {
        return persister.findAllAuthors();
    }

    public void addDonatedBook(Book book) {
        persister.addDonorDonatedBook(book);
    }

    public List<Book> getAllBooks() {
        return persister.findAllBooks();
    }

    public void donateBook(DonorDonatedBook donatedBookFromDonor) {
        persister.addDonorDonatedBook(donatedBookFromDonor);
    }

    public List<DonorDonatedBook> getAllDonatedBooks() {
        return persister.findAllDonorDonatedBook();
    }

}
