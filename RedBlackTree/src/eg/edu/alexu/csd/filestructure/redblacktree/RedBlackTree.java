package eg.edu.alexu.csd.filestructure.redblacktree;

import javax.management.RuntimeErrorException;
import javax.swing.*;

public class RedBlackTree<T extends Comparable<T>, V> implements IRedBlackTree<T, V> {
    private static boolean red = true;
    private static boolean black = false;

    private INode<T, V> nil = new Node<>();


    private INode<T, V> root = nil;


    @Override
    public INode<T, V> getRoot() {

        return root;
    }

    @Override
    public boolean isEmpty() {
        return root.isNull();
    }

    @Override
    public void clear() {
        root = nil;
    }

    @Override
    public V search(T key) {
        if (key == null)
            throw new RuntimeErrorException(null);
        INode<T, V> flag = root;
        while (flag != nil) {
            if (flag.getKey().compareTo(key) == 0)
                return flag.getValue();
            if (flag.getKey().compareTo(key) > 0)
                flag = flag.getLeftChild();
            else
                flag = flag.getRightChild();
        }
        return null;
    }

    @Override
    public boolean contains(T key) {
        if (key == null)
            throw new RuntimeErrorException(null);
        if (search(key) != null)
            return true;
        return false;
    }

    @Override
    public void insert(T key, V value) {
        if (key == null || value == null)
            throw new RuntimeErrorException(null);
        INode<T, V> Z = new Node<>();
        Z.setKey(key);
        Z.setValue(value);
        INode<T, V> Y = nil;
        INode<T, V> X = root;
        while (!X.isNull()) {
            Y = X;
            if (Z.getKey().compareTo(X.getKey()) == 0) {
                X.setValue(value);
                return;
            } if (Z.getKey().compareTo(X.getKey()) < 0) {
                X = X.getLeftChild();
            } else {
                X = X.getRightChild();
            }
        }
        Z.setParent(Y);
        if (Y == nil) {
            root = Z;
        } else if (Z.getKey().compareTo(Y.getKey()) < 0) {
            Y.setLeftChild(Z);
        } else
            Y.setRightChild(Z);
        Z.setLeftChild(nil);
        Z.setRightChild(nil);
        Z.setColor(true);
        InsertFixup(Z);

    }

    private void InsertFixup(INode<T, V> Z) {
        if (Z.getParent() == null) {
            Z.setColor(false);
        }
        while (Z.getParent().getColor()) {
            if (!Z.getParent().isNull() && !Z.getParent().getParent().isNull()) {
                if (Z.getParent() == Z.getParent().getParent().getLeftChild()) {
                    INode<T, V> Y = Z.getParent().getParent().getRightChild();
                    if (Y.getColor()) {
                        Z.getParent().setColor(false);
                        Y.setColor(false);
                        Z.getParent().getParent().setColor(true);
                        Z = Z.getParent().getParent();
                    } else if (Z == Z.getParent().getRightChild()) {
                        Z = Z.getParent();
                        LeftRotate(Z);
                    } else {
                        Z.getParent().setColor(false);
                        if (!Z.getParent().isNull() && !Z.getParent().getParent().isNull()) {
                            Z.getParent().getParent().setColor(true);
                            RightRotate(Z.getParent().getParent());
                        }
                    }
                } else {
                    INode<T, V> Y = Z.getParent().getParent().getLeftChild();
                    if (Y.getColor()) {
                        Z.getParent().setColor(false);
                        Y.setColor(false);
                        Z.getParent().getParent().setColor(true);
                        Z = Z.getParent().getParent();
                    } else if (Z == Z.getParent().getLeftChild()) {
                        Z = Z.getParent();
                        RightRotate(Z);
                    } else {
                        Z.getParent().setColor(false);
                        if (!Z.getParent().isNull() && !Z.getParent().getParent().isNull()) {
                            Z.getParent().getParent().setColor(true);
                            LeftRotate(Z.getParent().getParent());
                        }
                    }
                }

            } else
                break;
        }
        root.setColor(false);

    }

    private void LeftRotate(INode<T, V> P) {
        INode<T, V> X = P.getRightChild();
        P.setRightChild(X.getLeftChild());
        if (X==nil) return;
        if (!X.getLeftChild().isNull()) {
            X.getLeftChild().setParent(P);
        }
        X.setParent(P.getParent());
        if (P.getParent().isNull())
            root = X;
        else if (P == P.getParent().getLeftChild())
            P.getParent().setLeftChild(X);
        else
            P.getParent().setRightChild(X);

        X.setLeftChild(P);
        P.setParent(X);
    }

    private void RightRotate(INode<T, V> G) {
        INode<T, V> Y = G.getLeftChild();
        G.setLeftChild(Y.getRightChild());
        if (!Y.getRightChild().isNull())
            Y.getRightChild().setParent(G);
        Y.setParent(G.getParent());
        if (G.getParent().isNull())
            root = Y;
        else if (G == G.getParent().getRightChild())
            G.getParent().setRightChild(Y);
        else
            G.getParent().setLeftChild(Y);
        Y.setRightChild(G);
        G.setParent(Y);
    }

    private INode find(Comparable key) {
        if (key == null) {
            throw new RuntimeErrorException(null);
        }
        INode<T, V> node = root;
        while ( node != nil) {
            if (node.getKey().compareTo((T) key) == 0) {
                return node;
            }
            if (key.compareTo(node.getKey()) > 0) {
                node = node.getRightChild();
            } else {
                node = node.getLeftChild();
            }
        }
       // if (node == nil)
            return nil;
       // return null;

    }

    @Override
    public boolean delete(Comparable key) {
        if (key == null)
            throw new RuntimeErrorException(null);
        INode<T, V> z = find(key);
        if (z.getKey()==null)  return false;
        INode y = z, x;
        boolean yOrigin = y.getColor();
        int flag=0;
        if (z.getLeftChild().isNull()) {
            x = z.getRightChild();
            transplant(z, z.getRightChild());
            flag=0;
        } //System.out.println(z==nil);
         else if (z.getRightChild()==nil) {
            x = z.getLeftChild();
            transplant(z, z.getLeftChild());
            flag=0;
        } else {
            flag=1;
            y = minChild(z.getRightChild());
            yOrigin = y.getColor();
            x = y.getRightChild();
            if (y.getParent() == z) {
                x.setParent(y);
            } else {
                transplant(y, y.getRightChild());
                y.setRightChild(z.getRightChild());
                y.getRightChild().setParent(y);
            }
            transplant(z, y);
            y.setLeftChild(z.getLeftChild());
            y.getLeftChild().setParent(y);
            y.setColor(z.getColor());
        }
        if (yOrigin == black&&x!=nil&&x!=null) {
            if (flag==0) deleteFixup(x, z.getParent());
            else  deleteFixup(x, y.getParent());
        }
        return true; }

    private INode<T, V> successor(INode<T, V> x) {
        if (x.getRightChild() != nil) {
            x = x.getRightChild();
            while (x.getLeftChild() != nil) {
                x = x.getLeftChild();
            }
            return x;
        } else {
            INode y = x.getParent();
            while (y != nil && x == y.getRightChild()) {
                x = y;
                y = y.getParent();
            }
            return y;
        }
    }

    private INode<T, V> minChild(INode<T, V> x) {
        //if (x==nil||x==null) return nil;
        INode<T, V> y = new Node<>();
        y = x;
        while (y.getLeftChild()!=null ) {
            // System.out.println("1");
            y = y.getLeftChild();
        }
        return y;

    }


    private void transplant(INode<T, V> u, INode<T, V> v) {

        if (u.getParent() == nil) {
            root = v;

        } else if (u!=nil&&u == u.getParent().getLeftChild()) {
            u.getParent().setLeftChild(v);
        } else if (u!=nil) {
            u.getParent().setRightChild(v);
        }
     if(v!=nil&&u!=nil)

        v.setParent(u.getParent());

    }


    private void deleteFixup(INode<T, V> x,INode<T, V>  xParent) {
        while (x != root && x.getColor() == black) {
            INode w;
                if (x == xParent.getLeftChild()) {
                    w = xParent.getRightChild();
                    if (w != nil) {
                        if (w.getColor() == red) {
                            w.setColor(black);
                            xParent.setColor(red);
                            LeftRotate(xParent);
                            w = xParent.getRightChild();
                        }
                        if (w.getLeftChild().getColor() == black && w.getRightChild().getColor() == black) {
                            w.setColor(red);
                            x = xParent;
                            //continue;
                        } else if (w.getRightChild().getColor() == black) {
                            w.getLeftChild().setColor(black);
                            w.setColor(red);
                            RightRotate(w);
                            w = xParent.getRightChild();
                        }
                        if (w.getRightChild().getColor() == INode.RED) {
                            w.setColor(xParent.getColor());
                            xParent.setColor(black);
                            w.getRightChild().setColor(black);
                            LeftRotate(xParent);
                            x = getRoot();

                        }

                    }
                } else {

                    w = xParent.getLeftChild();
                    if (w != nil) {

                        if (w.getColor() == red) {
                            w.setColor(black);
                            xParent.setColor(red);
                            LeftRotate(xParent);
                            w =xParent.getLeftChild();
                        }
                        if (w.getRightChild().getColor() == black && w.getLeftChild().getColor() == black) {
                            w.setColor(red);
                            x = xParent;
                            //continue;
                        } else if (w.getLeftChild().getColor() == black) {
                            w.getLeftChild().setColor(black);
                            w.setColor(red);
                            LeftRotate(w);
                            w = xParent.getLeftChild();
                        }
                        if (w.getLeftChild().getColor() == INode.RED) {
                            w.setColor(xParent.getColor());
                            x.getParent().setColor(black);
                            w.getLeftChild().setColor(black);
                            RightRotate(xParent);
                            x = getRoot();
                        }

                    }
                }

        }
        x.setColor(black);
    }
}