package edu.cqu.olmclibrarysystem.presenter;

import edu.cqu.olmclibrarysystem.model.Member;
import edu.cqu.olmclibrarysystem.model.MemberBorrowedBook;

import java.util.List;

/**
 * MemberPresenter: Presenter for Member
 *
 * @author Tikaraj Ghising
 */
public class MemberPresenter {

    private MemberPersister persister;

    public MemberPresenter() {
        this.persister = new MemberPersister();
    }

    public void add(Member member) {
        persister.addMember(member);
    }

    public List<Member> getAllMembers() {
        return persister.findAllMembers();
    }

    public Member findMemberByMemberId(int id) {
        return persister.findMemberByMemberId(id);
    }

    public void addMemberBorrowedBook(MemberBorrowedBook borrowedBook) {
        persister.addMemberBorrowedBook(borrowedBook);
    }

    public void returnMemberBorrowedBook(MemberBorrowedBook borrowedBook) {
        persister.returnMemberBorrowedBook(borrowedBook);
    }

    public List<MemberBorrowedBook> getAllIssuedBooks() {
        return persister.getAllMemberBorrowedBooks();
    }

    public List<MemberBorrowedBook> getAllMemberBorrowedBooks(int memberId) {
        return persister.getAllMemberBorrowedBookByMemberId(memberId);
    }

    public List<MemberBorrowedBook> getAllOverDueBooks() {
        return persister.getAllMemberBorrowedOverDueBooks();
    }

    public List<MemberBorrowedBook> getAllOverDueReturnsBorrowedBooks() {
        return persister.getAllOverDueReturnsBorrowedBooks();
    }
}
