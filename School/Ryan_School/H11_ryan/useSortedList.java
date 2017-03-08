import java.util.Iterator;

/**
 * A handful of test cases
 * @author Charlie on 3/7/2017.
 */
public class useSortedList {
    public static void main(String[] args) {
        SortedLinkedList<String> list1 = new SortedLinkedList<>();
        SortedLinkedList<String> list2 = new SortedLinkedList<>();
        list1.add("apple");
        list1.add("banana");
        list1.add("peaches");
        list1.add("peaches");
        list1.add("cherries");
        list2.add("bandanna");
        list2.add("youtube");
        list2.add("butts.jpeg");
        list1.add("waffle fries");
        System.out.println("List 1: " + list1 + "\nSize: " + list1.size());
        System.out.println("List 2: " + list2 + "\nSize: " + list2.size());
        System.out.println(list1.remove("peaches"));
        System.out.println("List 1: " + list1 + "\nSize: " + list1.size());
        System.out.println(list1.remove("donuts"));
        System.out.println(list1.remove("waffle fries"));
        System.out.println("List 1: " + list1 + "\nSize: " + list1.size());
        Iterator<String> iter1 = list1.iterator();
        System.out.print(iter1.hasNext()+" ");
        System.out.println(iter1.next());
        iter1.remove();
        System.out.println("List 1: " + list1 + "\nSize: " + list1.size());
        try{
            iter1.remove();
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
        System.out.println("List 1: " + list1 + "\nSize: " + list1.size());
        list1.addAll(list2);
        System.out.println("List 1: " + list1 + "\nSize: " + list1.size());
        System.out.println("Removed Head: " + list1.removeHead());
        System.out.println("List 1: " + list1 + "\nSize: " + list1.size());
        System.out.println("Removed Tail: " + list1.removeTail());
        System.out.println("List 1: " + list1 + "\nSize: " + list1.size());
        list2.clear();
        System.out.println("List 2: " + list2 + "\nSize: " + list2.size());
        try {
            list2.removeHead();
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
}
