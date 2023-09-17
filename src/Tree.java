
import java.util.Scanner;

class BinaryTree{
    Node root;
    class Node{
        int value;
        Node left;
        Node right;
        Color color;
    }
    private enum Color {
        RED, BLACK
    }
    private Node balancing(Node node) {
        boolean nodeRebalance;
        do {
            nodeRebalance = false;
            if (node.right != null && node.right.color == Color.RED &&
                    (node.left == null || node.left.color == Color.BLACK)) {
                node = rightSwap(node);
                nodeRebalance = true;
            } else if (node.left != null && node.left.color == Color.RED &&
                    node.left.left != null && node.left.left.color == Color.RED) {
                node = leftSwap(node);

                nodeRebalance = true;
            } else if (node.left != null && node.left.color == Color.RED &&
                    node.right != null && node.right.color == Color.RED) {
                colorSwap(node);
                nodeRebalance = true;
            }
        } while ((nodeRebalance));
        return node;
    }

    private Node leftSwap(Node nodeY) {
        System.out.println("leftSwap " + nodeY);
        Node nodeX = nodeY.left;
        Node rightX = nodeX.right;
        nodeX.right = nodeY;
        nodeY.left = rightX;
        nodeX.color = nodeY.color;
        nodeY.color = Color.RED;
        return nodeX;
    }

    private Node rightSwap(Node nodeX) {
        System.out.println("rightSwap " + nodeX);
        Node nodeY = nodeX.right;
        Node leftY = nodeY.left;
        nodeY.left = nodeX;
        nodeX.right = leftY;
        nodeY.color = nodeX.color;
        nodeX.color = Color.RED;
        return nodeY;
    }

    private void colorSwap(Node node) {
        node.left.color = Color.BLACK;
        node.right.color = Color.BLACK;
        node.color = Color.RED;
    }


    public boolean insert(int value){
        if (root == null) {
            root = new Node();
            root.value = value;
            root.color = Color.BLACK;
            root = balancing(root);
            return true;
        } else {
            root.color = Color.BLACK;
            root = balancing(root);
            return insert(root, value);
        }
    }

    private boolean insert(Node node, int value) {
        if (node.value == value) {
            return false;
        }

        if (node.value < value) {
            if (node.right == null) {
                node.right = new Node();
                node.right.value = value;
                node.right.color = Color.RED;
                return true;
            }
            boolean result = insert(node.right, value);
            node.right = balancing(node.right);
            return result;

        } else {
            if (node.left == null) {
                node.left = new Node();
                node.left.value = value;
                node.left.color = Color.RED;
                return true;
            }
            boolean result = insert(node.left, value);
            node.left = balancing(node.left);
            return result;
        }
    }
    public void print() {
        int leftSize = 0;
        int rightSize = 0;
        Node tempL = root.left;
        Node tempR = root.right;
        System.out.printf("    ROOT %d(%s)\n", root.value, root.color);
        System.out.println("    LEFT ROOT:");
        while (tempL != null) {
            print(tempL);
            if (tempL.right != null) {
                print(tempL.right);
            }
            tempL = tempL.left;
            leftSize++;
        }
        System.out.println("    RIGHT ROOT:");
        while (tempR != null) {
            print(tempR);
            if (tempR.left != null) {
                print(tempR.left);
            }
            tempR = tempR.right;
            rightSize++;
        }
        if(Math.abs(rightSize - leftSize) <= 1){
            System.out.println("BinaryTree is balanced.");
        } else {
            System.out.println("BinaryTree is not balanced.");
        }
    }
    private void print(Node root) {
        System.out.printf("    node %d(%s)\n", root.value, root.color);
        if (root.left != null && root.right != null) {
            System.out.printf("left %d(%s)   right %d(%s)\n",
                    root.left.value,
                    root.left.color,
                    root.right.value,
                    root.right.color);
        } else if (root.left != null && root.right == null) {
            System.out.printf("left %d(%s)   right null\n",
                    root.left.value,
                    root.left.color);
        } else if (root.left == null && root.right != null) {
            System.out.printf("left null   right %d(%s)\n",
                    root.right.value,
                    root.right.color);
        } else {
            System.out.println("left null    right null");
        }


    }
}
public class Tree {
    public static void main(String[] args) {
        String  g;
        Scanner sc = new Scanner(System.in);
        BinaryTree tree = new BinaryTree();
        boolean flag = true;
        while (flag){
            System.out.println("Введите число: ");
            tree.insert(sc.nextInt());
            tree.print();
            System.out.println("Хотите продолжить? (введите y чтобы продолжить или n для выхода)");
            g = sc.next().toString();
            System.out.println(g);
            if (g.equals("n")){
                flag = false;
                tree.print();
            }else {flag = true;}
        }
        sc.close();
    }
}