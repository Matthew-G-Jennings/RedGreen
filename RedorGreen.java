package RedGreen;

import java.util.*;

/**
 * Class to determine if the numbers within a specified range are Green or Red.
 * 1 is Green.
 * A number is Red if more of it's "near factors" are Green than Red.
 *
 * @author Matthew Jennings
 */

public class RedorGreen {

    // store whether a number is green or red for lookups
    // true is green, false is red
    // if it doesn't exist in the map, we haven't calculated it
    // so calculate it, and put it in the map.
    public static HashMap<Integer, Boolean> memo;

    public static int memohit = 0;
    public static int memomiss = 0;

    /**
     * Reads two integers from stdin separated by a space.
     * Will create a string of Gs and Rs based on the colour of each
     * integer between the two values.
     *
     * @param args Not used.
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        memo = new HashMap<Integer, Boolean>();
        memo.put(1, true);
        String output = "";
        String line;
        while (scanner.hasNextLine()) {
            line = scanner.nextLine();
            Scanner lineScan = new Scanner(line);
            int a = 0;
            int b = 0;
            try {
                a = Integer.parseInt(lineScan.next());
                b = Integer.parseInt(lineScan.next());
            } catch (Exception e) {
                System.out.println("Bad input: " + line);
                continue;
            }
            for (int i = a; i <= b; i++) {
                if (isGreen(i)) {
                    output += "G";
                } else {
                    output += "R";
                }
            }

            System.out.println(a + " " + b + " " + output);
            output = "";

            // For this to work, uncomment the increments to memohit and memomiss in
            // isGreen();
            // Left here and commented as it's potentially useful.
            System.out.println("Hit: " + memohit + " Miss: " + memomiss);
            memohit = 0;
            memomiss = 0;

        }

    }

    /**
     * Memoized recursive algorithm for determining if a given number is Green or
     * Red.
     *
     * @param n
     * @return true if green, false if red
     */
    public static boolean isGreen(int n) {
        if (memo.containsKey(n)) {
            memohit++;
            return memo.get(n);
        } else {
            memomiss++;
            int gcount = 0;
            int rcount = 0;
            Set<Integer> nearFactors = new HashSet<Integer>();
            // figure out what the near factors of this number are, hopefully efficiently.
            int potfact;
            for (int i = 2; i <= n; i++) {
                potfact = n / i;
                if (!nearFactors.contains(potfact)) {
                    nearFactors.add(potfact);
                }
            }
            for (int i : nearFactors) {
                if (isGreen(i)) {
                    gcount++;
                } else {
                    rcount++;
                }
            }
            if (gcount > rcount) {
                // System.out.println(gcount + " : " + rcount + " so returning Red for " + n);
                memo.put(n, false);
                return false;

            } else {
                // System.out.println(gcount + " : " + rcount + " so returning Green for " + n);
                memo.put(n, true);
                return true;
            }
        }
    }
}
