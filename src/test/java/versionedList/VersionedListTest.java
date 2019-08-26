package versionedList;

import java.text.ParseException;

public interface VersionedListTest<T> {
    void beforeEachTest();

    void getListForDate() throws ParseException, InterruptedException;

    void size();
    void isEmpty();

    void get();
    void subList();

    void add();
    void addAll();
    void set();

    void remove();
    void removeAll();
    void clear();

    void indexOf();
    void lastIndexOf();
    void contains();
    void containsAll();

    void toArray();

}
