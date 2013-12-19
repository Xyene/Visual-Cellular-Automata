package tk.ivybits.automata;

import java.util.Arrays;

/**
 * @version 1.0
 * @since 1.0
 */
public class AutomataFactory {
    public static IAutomata create(String rule) {
        String[] tokens = rule.split("/");
        String born = tokens[0];
        String survive = tokens[1];

        final int[] birth = new int[born.length() - 1];
        final int[] survival = new int[survive.length() - 1];
        copy(born, 1, birth);
        copy(survive, 1, survival);
        return new IAutomata() {
            @Override
            public boolean willLive(boolean alive, int neighbours) {
                if (alive) {
                    if (Arrays.binarySearch(survival, neighbours) > -1) {
                        return true;
                    }
                } else if (Arrays.binarySearch(birth, neighbours) > -1) {
                    return true;
                }
                return false;
            }
        };
    }

    private static void copy(String rule, int off, int[] dst) {
        char[] chars = rule.toCharArray();
        for (int idx = off; idx != dst.length + off; idx++) {
            dst[idx - off] = chars[idx] - 48;
        }
        Arrays.sort(dst);
    }
}
