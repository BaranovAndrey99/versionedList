package versionedList;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class VersionedListTestImpl implements VersionedListTest<String> {
    private VersionedList<String> testVersionedList;
    private String dateFormat = "yyyy-MM-dd HH:mm:ss";
    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat(dateFormat);

    /**
     * Method, which initialize versioned list before each test method.
     */
    @Before
    @Override
    public void beforeEachTest() {
        testVersionedList = new VersionedList<>();
    }

    /**
     * Method, which tests getListForDate() method.
     * 1. Add/remove elements and remember Date of this actions.
     * 2. Checking of size of List after all actions by remembered dates.
     * @throws ParseException - exception of parsing string with date
     *          for getListForDate().
     * @throws InterruptedException - exception in Thread.sleep().
     */
    @Test
    public void getListForDate() throws ParseException, InterruptedException {
        Date empty = new Date();
        Thread.sleep(2000);

        testVersionedList.add("Example 1");
        Thread.sleep(2000);
        Date addFirst = new Date();

        testVersionedList.add("Example 2");
        Thread.sleep(2000);
        Date addSecond = new Date();

        testVersionedList.add("Example 3");
        Thread.sleep(2000);
        Date addThird = new Date();

        testVersionedList.remove("Example 3");
        Thread.sleep(2000);
        Date removeThird = new Date();

        testVersionedList.add("Example 3");
        Thread.sleep(2000);

        List<String> testList;

        testList = testVersionedList.getListForDate(simpleDateFormat.format(empty));
        Assert.assertTrue(testList.isEmpty());

        testList = testVersionedList.getListForDate(simpleDateFormat.format(addFirst));
        Assert.assertEquals(1, testList.size());

        testList = testVersionedList.getListForDate(simpleDateFormat.format(addSecond));
        Assert.assertEquals(2, testList.size());

        testList = testVersionedList.getListForDate(simpleDateFormat.format(addThird));
        Assert.assertEquals(3, testList.size());

        testList = testVersionedList.getListForDate(simpleDateFormat.format(removeThird));
        Assert.assertEquals(2, testList.size());

        testList = testVersionedList.getListForDate(simpleDateFormat.format(new Date()));
        Assert.assertEquals(3, testList.size());

    }

    /**
     * Method, which tests size() method.
     * size() for empty list - must return 0;
     * size() after add() - must return 1;
     * size() after remove() - must return 1;
     */
    @Test
    @Override
    public void size() {
        int sizeExample = testVersionedList.size();
        Assert.assertEquals(0, sizeExample);

        testVersionedList.add("Example");

        sizeExample = testVersionedList.size();
        Assert.assertEquals(1, sizeExample);

        testVersionedList.remove("Example");

        sizeExample = testVersionedList.size();
        Assert.assertEquals(0, sizeExample);
    }

    /**
     * Method, which tests isEmpty() method.
     * isEmpty() for empty list - must return true;
     * isEmpty() after add() - must return false;
     */
    @Test
    @Override
    public void isEmpty() {
        boolean exampleEmpty = testVersionedList.isEmpty();
        Assert.assertTrue(exampleEmpty);

        testVersionedList.add("Example");

        exampleEmpty = testVersionedList.isEmpty();
        Assert.assertFalse(exampleEmpty);
    }

    /**
     * Method, which tests get() method.
     * Firstly, method adds element to empty list.
     * Secondly, method gets element with "0" index.
     * Check identity of added and getted strings.
     */
    @Test
    @Override
    public void get() {
        testVersionedList.add("Example");

        String actual = testVersionedList.get(0);

        Assert.assertEquals("Example", actual);
    }

    /**
     * Method, which tests add(Object obj) and add(int index, Object obj) methods.
     * Add element 1, add element 2, add element 3 to 0 position.
     * Check expected result: elements 3-1-2.
     */
    @Test
    @Override
    public void add() {
        testVersionedList.add("Example 1");
        testVersionedList.add("Example 2");
        testVersionedList.add(0,"Example 3");

        String[] stringArray = {"Example 3", "Example 1", "Example 2"};
        List<String> expected = Arrays.asList(stringArray);

        Assert.assertEquals(expected, testVersionedList);
    }

    /**
     * Method, which tests subList(int a, int b) method.
     * 1. Addition elements.
     * 2. Comparing expected result with sublist.
     */
    @Test
    @Override
    public void subList() {
        testVersionedList.add("Example 1");
        testVersionedList.add("Example 2");
        testVersionedList.add("Example 3");

        String[] stringArray = {"Example 1", "Example 2"};
        List<String> expected = Arrays.asList(stringArray);

        Assert.assertEquals(expected, testVersionedList.subList(0,2));
    }

    /**
     * Method, which tests addAll() method.
     * Perform add all method for collections of String started by 0 position.
     * Check identity between addable list and sub list between 0 and 3 positions.
     */
    @Test
    @Override
    public void addAll() {
        String[] stringArray = {"Example 3", "Example 1", "Example 2"};
        List<String> expected = Arrays.asList(stringArray);

        testVersionedList.addAll(0, expected);

        List<String> actual = testVersionedList.subList(0,3);

        Assert.assertEquals(expected, actual);
    }

    /**
     * Method, which tests set() method.
     * Method adds 2 element in list and change it's values.
     * After check actuality of list.
     */
    @Test
    @Override
    public void set() {
        testVersionedList.add("Example 1");
        testVersionedList.add("Example 2");
        testVersionedList.set(0,"Example 3");
        testVersionedList.set(1,"Example 4");

        String[] stringArray = {"Example 3", "Example 4"};
        List<String> expected = Arrays.asList(stringArray);

        Assert.assertEquals(expected, testVersionedList);
    }

    /**
     * Method, which tests remove() method.
     * 1. Add element.
     * 2. Remove element.
     * 3. Check emptiness of list. List must be empty.
     */
    @Test
    @Override
    public void remove() {
        testVersionedList.add("Example 1");
        testVersionedList.remove("Example 1");

        Assert.assertTrue(testVersionedList.isEmpty());
    }

    /**
     * Method, which tests removeAll() method.
     * 1. Add 3 elements.
     * 2. Remove 2 first elements by removeAll().
     * 3. Check list: it's have one element - element number 3 in 0 position.
     */
    @Test
    @Override
    public void removeAll() {
        testVersionedList.add("Example 1");
        testVersionedList.add("Example 2");
        testVersionedList.add("Example 3");

        String[] stringArray = {"Example 1", "Example 2"};
        List<String> removable = Arrays.asList(stringArray);

        testVersionedList.removeAll(removable);

        Assert.assertEquals(1, testVersionedList.size());
        Assert.assertEquals("Example 3", testVersionedList.get(0));
    }

    @Test
    @Override
    public void clear() {
        testVersionedList.add("Example 1");
        testVersionedList.add("Example 2");
        testVersionedList.add("Example 3");

        testVersionedList.clear();

        Assert.assertTrue(testVersionedList.isEmpty());
    }

    @Test
    @Override
    public void indexOf() {
        testVersionedList.add("Example 1");
        testVersionedList.add("Example 2");
        testVersionedList.add("Example 2");

        Assert.assertEquals(0, testVersionedList.indexOf("Example 1"));
        Assert.assertEquals(1, testVersionedList.indexOf("Example 2"));
    }

    @Test
    @Override
    public void lastIndexOf() {
        testVersionedList.add("Example 1");
        testVersionedList.add("Example 2");
        testVersionedList.add("Example 2");

        Assert.assertEquals(0, testVersionedList.lastIndexOf("Example 1"));
        Assert.assertEquals(2, testVersionedList.lastIndexOf("Example 2"));
    }

    @Test
    @Override
    public void contains() {
        testVersionedList.add("Example 1");
        testVersionedList.add("Example 2");

        Assert.assertTrue(testVersionedList.contains("Example 1"));
        Assert.assertTrue(testVersionedList.contains("Example 2"));
        Assert.assertFalse(testVersionedList.contains("Example 3"));


    }

    /**
     * Method, which tests containsAll() method.
     * 1. Add 3 elements.
     * Create array with existing elements.
     * Create array with noi existing elements.
     * Checking their presence in the list.
     */
    @Test
    @Override
    public void containsAll() {
        testVersionedList.add("Example 1");
        testVersionedList.add("Example 2");
        testVersionedList.add("Example 3");

        List<String> searchable;

        String[] goodArray = {"Example 1", "Example 2"};
        searchable = Arrays.asList(goodArray);
        Assert.assertTrue(testVersionedList.containsAll(searchable));

        String[] badArray = {"Example 4", "Example 5"};
        searchable = Arrays.asList(badArray);
        Assert.assertFalse(testVersionedList.containsAll(searchable));
    }

    /**
     * Method, which tests toArray() methods.
     * 1. Add 3 elements.
     * 2. toArray() - convert in array of Objects.
     * 3. toArray(new String[3]) - convert to array of Strings.
     * 4. Compare received arrays with expected arrays.
     */
    @Test
    @Override
    public void toArray() {
        testVersionedList.add("Example 1");
        testVersionedList.add("Example 2");
        testVersionedList.add("Example 3");

        Object[] objectsFromList = testVersionedList.toArray();
        Object[] expectedObjects = {"Example 1", "Example 2", "Example 3"};

        String[] stringsFromList = testVersionedList.toArray(new String[3]);
        String[] expectedStrings = {"Example 1", "Example 2", "Example 3"};

        Assert.assertArrayEquals(expectedObjects, objectsFromList);
        Assert.assertArrayEquals(expectedStrings, stringsFromList);
    }
}
