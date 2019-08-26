package versionedList;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class VersionedList<T> extends AbstractList<T> implements List<T>{
    private List<Element<T>> activeContent = new LinkedList<>();
    private List<Element<T>> outdatedContent = new LinkedList<>();
    private static SimpleDateFormat dateFormat;

    public VersionedList() {
        dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    }

    public VersionedList(String format) {
        dateFormat = new SimpleDateFormat(format);
    }

    @Override
    public int size() {
        return activeContent.size();
    }

    @Override
    public T get(int i) {
        return activeContent.get(i).getObject();
    }

    @Override
    public boolean add(T object) {

        activeContent.add(new Element<>(object));

        return true;
    }

    @Override
    public void add(int index, T object) {
        activeContent.add(index, new Element<>(object));
    }

    @Override
    public T remove(int index) {
        Element<T> element = activeContent.get(index);
        T oldValue = element.getObject();


        if (element.getDeletionDate() == null) {
            element.setDeletionDate();
        }

        activeContent.remove(element);
        outdatedContent.add(element);

        return oldValue;
    }

    @Override
    public T set(int index, T object) {
        Element<T> element = activeContent.get(index);

        element.setDeletionDate();

        outdatedContent.add(element);

        activeContent.set(index, new Element<>(object));

        return element.getObject();
    }

    @Override
    public void clear() {
        for (Element<T> element:
             activeContent) {
            element.setDeletionDate();
            outdatedContent.add(element);
        }

        activeContent.clear();
    }


    @Override
    public boolean addAll(int index, Collection<? extends T> collection) {
        for (T object:
             collection) {
            activeContent.add(new Element<>(object));
        }

        return true;
    }
    
    /**
     * Method find existing elements, which creates before
     * searchable date and deleted elements, which deleted
     * after searchable date.
     * @param date - searchable date
     * @return - list in searchable date.
     * @throws ParseException - exception of date parsing.
     */
    public List<T> getListForDate(String date) throws ParseException {
        List<T> gettableDatA = new ArrayList<>();
        Date gettableDatE = dateFormat.parse(date);

        for (Element<T> activeElement:
             activeContent) {
            if (activeElement.getCreationDate().before(gettableDatE)) {
                gettableDatA.add(activeElement.getObject());
            }
        }
        for (Element<T> outdatedElement:
                outdatedContent) {
            if (outdatedElement.getCreationDate().before(gettableDatE) &&
                    outdatedElement.getDeletionDate().after(gettableDatE)) {
                gettableDatA.add(outdatedElement.getObject());
            }
        }

        return gettableDatA;
    }

    /**
     * Elements, which stored in list.
     * Field "object" - stored object.
     * Field "creationDate" - date of object creation.
     * Field "deletionDate" - date of object creation.
     * @param <T> - type of objects, which should be stored.
     */
    private static class Element<T> {
        final private T object;
        final private Date creationDate;
        private Date deletionDate;

        private Element(T object) {
            creationDate = new Date();
            this.object = object;
            deletionDate = null;
        }

        private T getObject() {
            return object;
        }

        private Date getCreationDate() {
            return creationDate;
        }

        private Date getDeletionDate() {
            return deletionDate;
        }

        private void setDeletionDate() {
            this.deletionDate = new Date();
        }
    }
}
