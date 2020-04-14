package eg.edu.alexu.csd.filestructure.redblacktree;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.management.RuntimeErrorException;

public class TreeMap<T extends Comparable<T>, V> implements ITreeMap<T, V> {

    private IRedBlackTree<T, V> RBTree = new RedBlackTree<>();
    private int size = 0;

    @Override
    public Entry<T, V> ceilingEntry(T key) {
        if (key == null)
            throw new RuntimeErrorException(null);
        Set<Map.Entry<T, V>> result = new LinkedHashSet<Map.Entry<T, V>>();
        inorderTraversal(RBTree.getRoot(), result);
        Iterator<Map.Entry<T, V>> it = entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<T, V> entry = it.next();
            if (entry.getKey().compareTo(key) > 0 || entry.getKey().compareTo(key) == 0) {
                return entry;
            }
        }
        return null;
    }

    private void inorderTraversal(INode<T, V> root, Set<Map.Entry<T, V>> result) {
        if (root.isNull())
            return;
        inorderTraversal(root.getLeftChild(), result);
        result.add(new AbstractMap.SimpleEntry<T, V>(root.getKey(), root.getValue()));
        inorderTraversal(root.getRightChild(), result);
    }

    @Override
    public T ceilingKey(T key) {
        if (key == null) {
            throw new RuntimeErrorException(null);
        }
        Map.Entry<T, V> entry = ceilingEntry(key);
        return (T) entry.getKey();
    }

    @Override
    public void clear() {
        RBTree.clear();
        size = 0;
    }

    @Override
    public boolean containsKey(T key) {
        if (key == null)
            throw new RuntimeErrorException(null);
        return RBTree.contains(key);
    }

    @Override
    public boolean containsValue(V value) {
        if (value == null)
            throw new RuntimeErrorException(null);
        Set<Map.Entry<T, V>> result = new LinkedHashSet<Map.Entry<T, V>>();
        inorderTraversal(RBTree.getRoot(), result);
        Iterator<Map.Entry<T, V>> it = entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<T, V> entry = it.next();
            if (entry.getValue().equals(value)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public Set<Entry<T, V>> entrySet() {
        Set<Map.Entry<T, V>> result = new LinkedHashSet<Map.Entry<T, V>>();
        inorderTraversal(RBTree.getRoot(), result);

        return result;
    }

    @Override
    public Entry<T, V> firstEntry() {
        if (RBTree.isEmpty())
            return null;
        Iterator<Map.Entry<T, V>> it = entrySet().iterator();
        return it.next();
    }

    @Override
    public T firstKey() {
        if (RBTree.isEmpty())
            return null;
        Map.Entry<T, V> entry = firstEntry();
        return entry.getKey();
    }

    @Override
    public Entry<T, V> floorEntry(T key) {
        if (key == null) {
            throw new RuntimeErrorException(null);
        }
        if (RBTree.isEmpty())
            return null;
        Set<Map.Entry<T, V>> result = new LinkedHashSet<Map.Entry<T, V>>();
        inorderTraversal(RBTree.getRoot(), result);
        Map.Entry<T, V> current = null;
        Map.Entry<T, V> prev = null;
        Iterator<Map.Entry<T, V>> it = entrySet().iterator();
        /*
        Iterator<Map.Entry<T, V>> t = it;
        while (t.hasNext()) {
            entry = t.next();
            it = t;
            if (it.hasNext()) {
                if ((entry.getKey().compareTo(key) < 0 || entry.getKey().compareTo(key) == 0)
                        && it.next().getKey().compareTo(key) > 0)
                    return entry;
            }
            if (!it.hasNext())
                break;
        }

         */
        while (it.hasNext()) {
            current = it.next();
                if (current.getKey().compareTo(key) >0 )
                       break;
                prev=current;
    }
        return prev;
    }

    @Override
    public T floorKey(T key) {
        if (key == null)
            throw new RuntimeErrorException(null);
        if (RBTree.isEmpty())
            return null;
        Map.Entry<T, V> entry = floorEntry(key);
        return (T) entry.getKey();
    }

    @Override
    public V get(T key) {
        if (key == null)
            throw new RuntimeErrorException(null);
        if (RBTree.isEmpty())
            return null;
        V n = RBTree.search(key);
        return n;
    }

    @Override
    public ArrayList<Entry<T, V>> headMap(T toKey) {
        if (toKey == null)
            throw new RuntimeErrorException(null);
        ArrayList<Entry<T, V>> arr = new ArrayList<>();
        Set<Map.Entry<T, V>> result = new LinkedHashSet<Map.Entry<T, V>>();
        inorderTraversal(RBTree.getRoot(), result);
        Iterator<Map.Entry<T, V>> it = entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<T, V> entry = it.next();
            if (entry.getKey().compareTo(toKey) < 0) {
                arr.add(entry);
            }
        }
        return arr;
    }

    @Override
    public ArrayList<Entry<T, V>> headMap(T toKey, boolean inclusive) {
        if (toKey == null) {
            throw new RuntimeErrorException(null);
        }
        if (!inclusive) {
            return headMap(toKey);
        }
        ArrayList<Map.Entry<T, V>> returned = new ArrayList<>();

        Iterator<Map.Entry<T, V>> i = entrySet().iterator();
        while (i.hasNext()) {
            Map.Entry<T, V> entry = i.next();
            if (entry.getKey().compareTo(toKey) <= 0) {
                returned.add(entry);
            } else
                break;
        }

        if (returned.size() == 0)
            return null;

        return returned;
    }

    @Override
    public Set<T> keySet() {
        if (RBTree.isEmpty()) {
            throw new RuntimeErrorException(null);
        }

        Iterator<Map.Entry<T, V>> i = entrySet().iterator();
        Set<T> set = new LinkedHashSet<>();
        while (i.hasNext()) {
            Map.Entry<T, V> entry = i.next();
            set.add(entry.getKey());
        }
        return set;
    }

    @Override
    public Entry<T, V> lastEntry() {
        Iterator<Map.Entry<T, V>> i = entrySet().iterator();
        Map.Entry<T, V> last = null;
        while (i.hasNext()) {
            last = i.next();

        }
        return last;
    }

    @Override
    public T lastKey() {
        Map.Entry<T, V> entry = lastEntry();
        if (entry == null) {
            return null;
        }
        return entry.getKey();
    }

    @Override
    public Entry<T, V> pollFirstEntry() {
        Map.Entry<T, V> entry = firstEntry();
        if (entry == null) {
            return null;
        }
        remove(entry.getKey());
        return entry;
    }

    @Override
    public Entry<T, V> pollLastEntry() {
        Map.Entry<T, V> entry = lastEntry();
        if (entry == null) {
            return null;
        }
        remove(entry.getKey());
        return entry;
    }

    @Override
    public void put(T key, V value) {
        if (key == null || value == null) {
            throw new RuntimeErrorException(null);
        }
        if (!RBTree.contains(key)) {
            size++;
        }
        RBTree.insert(key, value);
    }

    @Override
    public void putAll(Map<T, V> map) {
        if (map == null)
            throw new RuntimeErrorException(null);
        for (Map.Entry<T, V> entry : map.entrySet()) {
            RBTree.insert(entry.getKey(), entry.getValue());
            size++;
        }
    }

    @Override
    public boolean remove(T key) {
        if (key == null)
            throw new RuntimeErrorException(null);
        boolean deleted = RBTree.delete(key);
        if (deleted)
            size--;
        return deleted;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public Collection<V> values() {
        Collection<V> collection = new ArrayList<V>();
        Iterator<Map.Entry<T, V>> i = entrySet().iterator();
        Map.Entry<T, V> last;
        while (i.hasNext()) {
            last = i.next();
            collection.add(last.getValue());
        }
        return collection;
    }

}