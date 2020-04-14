package eg.edu.alexu.csd.filestructure.redblacktree;

import org.junit.Assert;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;

public class myMain {
    public static void main(String[] args) {

/*
        IRedBlackTree<Integer, String> redBlackTree = new RedBlackTree<>();
        Random r = new Random();
        HashSet<Integer> list = new HashSet<>();
        for (int i = 0; i < 12; i++) {
            int key = r.nextInt(100);
            if (r.nextInt(5) % 4 == 0)
                list.add(key);
            redBlackTree.insert(key, "soso" + key);

        }
        print(redBlackTree.getRoot());
        for (Integer elem : list) {
            boolean x= redBlackTree.delete(elem);
            print(redBlackTree.getRoot());

            System.out.println( x);
        }

 */
        IRedBlackTree<Integer, Integer> redBlackTree = new RedBlackTree<>();
        redBlackTree.insert(8,8);
        redBlackTree.insert(18,18);
        redBlackTree.insert(5,5);
        redBlackTree.insert(15,15);

        redBlackTree.insert(17,17);
        redBlackTree.insert(25,25);
        redBlackTree.insert(40,40);
        redBlackTree.insert(80,80);

        boolean x= redBlackTree.delete(5);
        print(redBlackTree.getRoot());

        System.out.println(redBlackTree.getRoot().getValue() );



    }


    public static void print(INode root) {
        List<List<String>> lines = new ArrayList<List<String>>();
        List<INode> level = new ArrayList<INode>();
        List<INode> next = new ArrayList<INode>();

        level.add(root);
        int nn = 1;

        int widest = 0;

        while (nn != 0) {
            List<String> line = new ArrayList<String>();

            nn = 0;

            for (INode n : level) {
                if (n == null || n.isNull()) {
                    line.add(null);

                    next.add(null);
                    next.add(null);
                } else {
                    System.out.println("=======================");
                    System.out.println(n.getValue());
                    System.out.println("parent  " + n.getParent());
                    System.out.println("left  " + n.getLeftChild());
                    System.out.println("right  " + n.getRightChild());
                    System.out.println("================");
                    String aa = (n.getValue().toString()) + (n.getColor() ? "(R)" : "(B)");
                    line.add(aa);
                    if (aa.length() > widest) widest = aa.length();

                    next.add(n.getLeftChild());
                    next.add(n.getRightChild());

                    if (n.getLeftChild() != null) nn++;
                    if (n.getRightChild() != null) nn++;
                }
            }

            if (widest % 2 == 1) widest++;

            lines.add(line);

            List<INode> tmp = level;
            level = next;
            next = tmp;
            next.clear();
        }

        int perpiece = lines.get(lines.size() - 1).size() * (widest + 4);
        for (int i = 0; i < lines.size(); i++) {
            List<String> line = lines.get(i);
            int hpw = (int) Math.floor(perpiece / 2f) - 1;

            if (i > 0) {
                for (int j = 0; j < line.size(); j++) {

                    // split node
                    char c = ' ';
                    if (j % 2 == 1) {
                        if (line.get(j - 1) != null) {
                            c = (line.get(j) != null) ? '┴' : '┘';
                        } else {
                            if (j < line.size() && line.get(j) != null) c = '└';
                        }
                    }
                    System.out.print(c);

                    // lines and spaces
                    if (line.get(j) == null) {
                        for (int k = 0; k < perpiece - 1; k++) {
                            System.out.print(" ");
                        }
                    } else {

                        for (int k = 0; k < hpw; k++) {
                            System.out.print(j % 2 == 0 ? " " : "─");
                        }
                        System.out.print(j % 2 == 0 ? "┌" : "┐");
                        for (int k = 0; k < hpw; k++) {
                            System.out.print(j % 2 == 0 ? "─" : " ");
                        }
                    }
                }
                System.out.println();
            }

            // print line of numbers
            for (int j = 0; j < line.size(); j++) {

                String f = line.get(j);
                if (f == null) f = "";
                int gap1 = (int) Math.ceil(perpiece / 2f - f.length() / 2f);
                int gap2 = (int) Math.floor(perpiece / 2f - f.length() / 2f);

                // a number
                for (int k = 0; k < gap1; k++) {
                    System.out.print(" ");
                }
                System.out.print(f);
                for (int k = 0; k < gap2; k++) {
                    System.out.print(" ");
                }
            }
            System.out.println();

            perpiece /= 2;
        }
    }
}



 /*




    /**
     * this code was copied and modified for
     * @author MightyPork
     * https://stackoverflow.com/users/2180189/mightypork
     */


/*
        public static void print(INode  root)
        {
            List<List<String>> lines = new ArrayList<List<String>>();
            List<INode > level = new ArrayList<INode >();
            List<INode > next = new ArrayList<INode >();

            level.add(root);
            int nn = 1;

            int widest = 0;

            while (nn != 0) {
                List<String> line = new ArrayList<String>();

                nn = 0;

                for (INode  n : level) {
                    if (n == null) {
                        line.add(null);

                        next.add(null);
                        next.add(null);
                    } else {
                        String aa = (n.getValue().toString())+(n.getColor()?"(R)":"(B)");
                        line.add(aa);
                        if (aa.length() > widest) widest = aa.length();

                        next.add(n.getLeftChild());
                        next.add(n.getRightChild());

                        if (n.getLeftChild() != null) nn++;
                        if (n.getRightChild() != null) nn++;
                    }
                }

                if (widest % 2 == 1) widest++;

                lines.add(line);

                List<INode > tmp = level;
                level = next;
                next = tmp;
                next.clear();
            }

            int perpiece = lines.get(lines.size() - 1).size() * (widest + 4);
            for (int i = 0; i < lines.size(); i++) {
                List<String> line = lines.get(i);
                int hpw = (int) Math.floor(perpiece / 2f) - 1;

                if (i > 0) {
                    for (int j = 0; j < line.size(); j++) {

                        // split node
                        char c = ' ';
                        if (j % 2 == 1) {
                            if (line.get(j - 1) != null) {
                                c = (line.get(j) != null) ? '┴' : '┘';
                            } else {
                                if (j < line.size() && line.get(j) != null) c = '└';
                            }
                        }
                        System.out.print(c);

                        // lines and spaces
                        if (line.get(j) == null) {
                            for (int k = 0; k < perpiece - 1; k++) {
                                System.out.print(" ");
                            }
                        } else {

                            for (int k = 0; k < hpw; k++) {
                                System.out.print(j % 2 == 0 ? " " : "─");
                            }
                            System.out.print(j % 2 == 0 ? "┌" : "┐");
                            for (int k = 0; k < hpw; k++) {
                                System.out.print(j % 2 == 0 ? "─" : " ");
                            }
                        }
                    }
                    System.out.println();
                }

                // print line of numbers
                for (int j = 0; j < line.size(); j++) {

                    String f = line.get(j);
                    if (f == null) f = "";
                    int gap1 = (int) Math.ceil(perpiece / 2f - f.length() / 2f);
                    int gap2 = (int) Math.floor(perpiece / 2f - f.length() / 2f);

                    // a number
                    for (int k = 0; k < gap1; k++) {
                        System.out.print(" ");
                    }
                    System.out.print(f);
                    for (int k = 0; k < gap2; k++) {
                        System.out.print(" ");
                    }
                }
                System.out.println();

                perpiece /= 2;
            }
        }


    }

 */



