
public class Q1Test {

    public static void main(String[] args) {

        String result, traversal1, traversal2, traversal3;

        traversal1 = "1-2";
        traversal2 = "2-1-3";
        traversal3 = "1-3-2";
        result = verify(traversal1, traversal2, traversal3);
        System.out.println("Traversal 1 : " + traversal1);
        System.out.println("Traversal 2 : " + traversal2);
        System.out.println("Traversal 3 : " + traversal3);
        System.out.println("Expected Result : Invalid traversals");
        System.out.println("  Actual Result : " + result);
        System.out.println();

        traversal1 = "1-2-3";
        traversal2 = "2-1-3";
        traversal3 = "1-3-2";
        result = verify(traversal1, traversal2, traversal3);
        System.out.println("Traversal 1 : " + traversal1);
        System.out.println("Traversal 2 : " + traversal2);
        System.out.println("Traversal 3 : " + traversal3);
        System.out.println(
                "Expected Result : Traversal 1 - Inorder, Traversal 2 - Preorder, Traversal 3 - Postorder");
        System.out.println("  Actual Result : " + result);
        System.out.println();

        traversal1 = "1-2-3";
        traversal2 = "3-2-1";
        traversal3 = "2-3-1";
        result = verify(traversal1, traversal2, traversal3);
        System.out.println("Traversal 1 : " + traversal1);
        System.out.println("Traversal 2 : " + traversal2);
        System.out.println("Traversal 3 : " + traversal3);
        System.out.println("Expected Result : Invalid traversals");
        System.out.println("  Actual Result : " + result);
        System.out.println();

        traversal1 = "3-1-2-5-4";
        traversal2 = "2-1-4-5-3";
        traversal3 = "1-2-3-4-5";
        result = verify(traversal1, traversal2, traversal3);
        System.out.println("Traversal 1 : " + traversal1);
        System.out.println("Traversal 2 : " + traversal2);
        System.out.println("Traversal 3 : " + traversal3);
        System.out.println(
                "Expected Result : Traversal 1 - Preorder, Traversal 2 - Postorder, Traversal 3 - Inorder");
        System.out.println("  Actual Result : " + result);
        System.out.println();

        traversal1 = "10-20-30-40-50";
        traversal2 = "20-10-30-40-50";
        traversal3 = "10-50-40-30-20";
        result = verify(traversal1, traversal2, traversal3);
        System.out.println("Traversal 1 : " + traversal1);
        System.out.println("Traversal 2 : " + traversal2);
        System.out.println("Traversal 3 : " + traversal3);
        System.out.println(
                "Expected Result : Traversal 1 - Inorder, Traversal 2 - Preorder, Traversal 3 - Postorder");
        System.out.println("  Actual Result : " + result);
        System.out.println();
    }

    private static boolean check(int[] inorder,
            int[] preorder,
            int[] postorder) {

        // BST inorder must be strictly increasing.
        for (int i = 1; i < inorder.length; i++) {
            if (inorder[i] <= inorder[i - 1]) {
                return false;
            }
        }

        return checkSubtree(
                inorder, preorder, postorder,
                0, 0, 0, inorder.length
        );
    }

    private static boolean checkSubtree(
            int[] inorder, int[] preorder, int[] postorder,
            int inStart, int preStart, int postStart, int size) {

        // An empty subtree is valid.
        if (size == 0) {
            return true;
        }

        // Preorder starts with root; postorder ends with root.
        int root = preorder[preStart];

        if (root != postorder[postStart + size - 1]) {
            return false;
        }

        // Find the root within this subtree's inorder section.
        int rootIndex = -1;

        for (int i = inStart; i < inStart + size; i++) {
            if (inorder[i] == root) {
                rootIndex = i;
                break;
            }
        }

        if (rootIndex == -1) {
            return false;
        }

        int leftSize = rootIndex - inStart;
        int rightSize = size - leftSize - 1;

        // Both subtrees must also have matching traversals.
        return checkSubtree(
                inorder, preorder, postorder,
                inStart,
                preStart + 1,
                postStart,
                leftSize
        ) && checkSubtree(
                inorder, preorder, postorder,
                rootIndex + 1,
                preStart + 1 + leftSize,
                postStart + leftSize,
                rightSize
        );
    }

    // write your codes here
    public static String verify(String traversal1, String traversal2, String traversal3) {
        //convert the strings into an int arr
        String[] parts1 = traversal1.split("-");
        int[] num1 = new int[parts1.length];
        for (int i = 0; i < parts1.length; i++) {
            num1[i] = Integer.parseInt(parts1[i]);
        }

        String[] parts2 = traversal2.split("-");
        int[] num2 = new int[parts2.length];
        for (int i = 0; i < parts2.length; i++) {
            num2[i] = Integer.parseInt(parts2[i]);
        }

        String[] parts3 = traversal3.split("-");
        int[] num3 = new int[parts3.length];
        for (int i = 0; i < parts3.length; i++) {
            num3[i] = Integer.parseInt(parts3[i]);
        }

        if (num1.length != num2.length || num2.length != num3.length) {
            return "Invalid traversals";
        }

        // check(inorder, preorder, postorder)
        if (check(num3, num1, num2)) {
            return "Traversal1 - Preorder, Traversal2 - Postorder, Traversal3 - Inorder";
        }

        if (check(num2, num1, num3)) {
            return "Traversal1 - Preorder, Traversal2 - Inorder, Traversal3 - Postorder";
        }

        if (check(num3, num2, num1)) {
            return "Traversal1 - Postorder, Traversal2 - Preorder, Traversal3 - Inorder";
        }

        if (check(num1, num2, num3)) {
            return "Traversal1 - Inorder, Traversal2 - Preorder, Traversal3 - Postorder";
        }

        if (check(num2, num3, num1)) {
            return "Traversal1 - Postorder, Traversal2 - Inorder, Traversal3 - Preorder";
        }

        if (check(num1, num3, num2)) {
            return "Traversal1 - Inorder, Traversal2 - Postorder, Traversal3 - Preorder";
        }

        return "Invalid traversals";
    }
}

