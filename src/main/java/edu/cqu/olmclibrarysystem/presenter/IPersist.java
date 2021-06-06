package edu.cqu.olmclibrarysystem.presenter;

import java.util.Date;
import java.util.List;

/**
 * IPersist: Interface for Database Persist
 *
 * @author Tikaraj Ghising
 */
public interface IPersist<T> {

    void add(T t);

    void issue(T t1, T t2);

    List<T> getAll();

    List<T> getAllByUserId(int id);

    List<T> searchByUserIdAndItem(int id, String name);

    List<T> generateExpireNotification(int id, Date expireDate);

    List<T> generateReport(int id, Date date);
}
