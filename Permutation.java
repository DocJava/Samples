public class Permutation {
    public static void printPermutations(String word) {
        if (word != null && !word.isEmpty()) {
            permute(word, "");
        }
    }

    private static void permute(String pre, String post) {
        if (pre.isEmpty()) {
            System.out.println(post);
            return;
        }

        for (int x = 0; x < pre.length(); x++) {
            permute(removeIndex(x, pre), post + pre.charAt(x));
        }
    }

    private static String removeIndex(int index, String string) {
        return string.substring(0, index) + string.substring(index + 1);
    }
}
