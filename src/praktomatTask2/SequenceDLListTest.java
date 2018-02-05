package praktomatTwo;

public class SequenceDLListTest {
        public static void main(String[] args) {
            SequenceDLList<Integer> myList = new SequenceDLList<Integer>();

            for (int i=0; i < 26; i++) {
                myList.insertFirst(new Integer(i));
            }

            while (!myList.empty()) {
                try {
                    System.out.println(myList.first());
                    myList.deleteFirst();
                } catch (SequenceException e) {
                    System.out.println(e);
                }
            }
        }
}
