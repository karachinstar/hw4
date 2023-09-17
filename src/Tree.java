import java.util.Date;
import java.util.Iterator;
import java.util.Scanner;

//class HashMap{
//    class Entity{
//        int key;
//        int value;
//    }
//    class Basket{
//        Node head;
//        class Node{
//            Entity entity;
//            Node next;
//        }
//
//        public Integer find(int key){
//            Node node = head;
//            while(node != null){
//                if(node.entity.key == key){
//                    return node.entity.value;
//                }
//                node = node.next;
//            }
//            return null;
//        }
//
//        public boolean insert(Entity entity){
//            Node node = new Node();
//            node.entity = entity;
//
//            if(head == null){
//                head = node;
//                return true;
//            }else {
//                Node cur = head;
//                while (cur != null){
//                    if(cur.entity.key == entity.key){
//                        return false;
//                    }
//                    if(cur.next == null){
//                        cur.next = node;
//                        return true;
//                    }
//                    cur = cur.next;
//                }
//                return false;
//            }
//        }
//    }
//
//    public static int INIT_SIZE = 16;
//    Basket[] baskets;
//
//    public HashMap(){
//        this(INIT_SIZE);
//    }
//    public HashMap(int size){
//        baskets = new Basket[size];
//    }
//
//    public int getIndex(int key){
//        //key.HashCode()
//        return key % baskets.length;
//    }
//
//    public Integer find(int key){
//        int index = getIndex(key);
//        Basket basket = baskets[index];
//        if(basket != null){
//            return basket.find(key);
//        }
//        return null;
//    }
//
//    public boolean insert(int key, int value){
//        Entity entity = new Entity();
//        entity.key = key;
//        entity.value = value;
//
//        int index = getIndex(key);
//        Basket basket = baskets[index];
//
//        if(basket == null){
//            basket = new Basket();
//            baskets[index] = basket;
//        }
//
//        return basket.insert(entity);
//
//    }
//}

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


//    public boolean find(int value){
//        return find(root, value);
//    }

//    public boolean find(Node node, int value){
//        if(node == null){
//            return false;
//        }
//        if(node.value == value){
//            return true;
//        }
//        if(node.value < value){
//            return find(node.right, value);
//        }else{
//            return find(node.left, value);
//        }
//    }

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
//        HashMap map = new HashMap();
//
//        map.insert(1, 2);
//        map.insert(17, 4);
//        map.insert(5, 6);
//
//        System.out.println(map.find(1));
//        System.out.println(map.find(17));
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