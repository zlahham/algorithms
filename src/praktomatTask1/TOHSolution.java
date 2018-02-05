package praktomatTask1;
import java.util.*;

public class TOHSolution {

    private static int numOfDiscs = 0, numOfMoves = 0, numOfDiscs_Post1 = 0, numOfDiscs_Post2 = 0, numOfDiscs_Post3 = 0;
    private static Stack<Integer> post_1 = new Stack<Integer>();
    private static Stack<Integer> post_2 = new Stack<Integer>();
    private static Stack<Integer> post_3 = new Stack<Integer>();

    public static void main (String[] args) {

        try {
            numOfDiscs = Integer.parseInt(args[0]);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (numOfDiscs <= 1 || numOfDiscs >= 10) {
            System.out.println ("Minimum number of discs to play is 2.  Maximum number of discs to play is 9.  To play, please enter a number between 2 and 9.");
            return;
        }

        int i = numOfDiscs;
        while (i >= 1) {
            post_1.push(i--);
        }

        numOfDiscs_Post1 = post_1.size();
        numOfDiscs_Post2 = post_2.size();
        numOfDiscs_Post3 = post_3.size();
        output();

        play (numOfDiscs, post_1, post_2, post_3);

        System.out.println ( "Number of moves made is " + numOfMoves );

        while (post_3.size() > 0) {
            post_3.pop();
        }
    }

    private static int play (int numOfDiscs, Stack<Integer> from, Stack<Integer> intermediate, Stack<Integer> to) {

        if (numOfDiscs <= 4) {

            if ((numOfDiscs % 2) == 0) {

                simplePlay (from, intermediate, to);
                numOfDiscs = numOfDiscs - 1;

                if (numOfDiscs == 1) {
                    return 1;
                }

                intermediate.push (from.pop());
                numOfMoves++;
                output();

                simplePlay (to, from, intermediate);
                to.push (from.pop());

                numOfMoves++;
                output();
                play (numOfDiscs, intermediate, from, to);

            } else {

                if (numOfDiscs == 1) {
                    return -1;
                }

                simplePlay (from, to, intermediate);
                numOfDiscs = numOfDiscs - 1;

                to.push ( from.pop() );
                numOfMoves++;
                output();

                simplePlay (intermediate, from, to);

            }

            return 1;

        } else if (numOfDiscs >= 5) {

            play(numOfDiscs - 2, from, intermediate, to);

            intermediate.push (from.pop());
            numOfMoves++;
            output();

            play(numOfDiscs - 2, to, from, intermediate);

            to.push (from.pop());
            numOfMoves++;
            output();

            play(numOfDiscs - 1, intermediate, from, to);

        }
        return 1;
    }

    private static void simplePlay (Stack<Integer> from, Stack<Integer> intermediate, Stack<Integer> to) {

        intermediate.push(from.pop());
        numOfMoves++;
        output();

        to.push(from.pop());
        numOfMoves++;
        output();

        to.push(intermediate.pop());
        numOfMoves++;
        output();
    }

    private static void output() {

        if (numOfDiscs_Post1 != post_1.size() || numOfDiscs_Post2 != post_2.size() || numOfDiscs_Post3 != post_3.size()) {

            int delta_1 = post_1.size() - numOfDiscs_Post1;
            int delta_2 = post_2.size() - numOfDiscs_Post2;

            if (delta_1 == 1) {

                if (delta_2 == -1) {
                    System.out.print ("Move Disc " + post_1.peek() + " from Post 2 to Post 1");
                } else {
                    System.out.print ("Move Disc " + post_1.peek() + " from Post 3 to Post 1");
                }
            } else if ( delta_2 == 1 ) {
                if ( delta_1 == -1 ) {
                    System.out.print ("Move Disc " + post_2.peek() + " from Post 1 to Post 2");
                } else {
                    System.out.print ("Move Disc " + post_2.peek() + " from Post 3 to Post 2");
                }
            } else {
                if ( delta_1 == -1 ) {
                    System.out.print ("Move Disc " + post_3.peek() + " from Post 1 to Post 3");
                } else {
                    System.out.print ("Move Disc " + post_3.peek() + " from Post 2 to Post 3");
                }
            }
            numOfDiscs_Post1 = post_1.size();
            numOfDiscs_Post2 = post_2.size();
            numOfDiscs_Post3 = post_3.size();
            System.out.println();
        }

        System.out.print(post_1.toString() + " : ");
        System.out.print(post_2.toString() + " : ");
        System.out.print(post_3.toString() + " -> ");

    }

}