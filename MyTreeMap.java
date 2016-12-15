//Author Tsend-Ayush Batbileg
//CS241 Project 2
//TreeMap implementation of BinarySearchTree
import tree.BinaryTree;
import java.util.*;
public class MyTreeMap<K extends Comparable<K>,V> implements MyMap<K,V>
{
    private BinaryTree<Element> map;
    java.util.Set<K> keys= new java.util.TreeSet<K>();  // to return keys in order
    private int size;
    //check to see if key exists in map
    public boolean containsKey(K key)
    {
        if(search(new Element(key, null), map) != null)
            return true;
        else
            return false;
    };
    //add the key and value
    //calls 
    public V put(K key, V value)
    {
        insert(new Element(key, value), map);
        size += 1;
        return value;
    };
    public V get(K key)
    {
        if(this.containsKey(key))
            return search(new Element(key, null), map).value;
        else
            return null;
    };
    public V remove(K key)
    {
        Element e = delete(map, new Element(key, null), null);
        if(e == null)
            return null;
        else
        {
            size -= 1;
            return e.value;
        }
    };
    public int size()
    {
        return size;
    };
    public int height()
    {
        return height(map);
    };
    public String toString()
    {
        inorder(map);
        return "";
    };
    public java.util.Set<K> keySet()
    {
        if(map != null)
        {
            //clear the current keys in the set before adding keys
            //inorder() adds the keys to the set
            //keys.clear();
            inorder(map);
            return keys;
        }
        return null;
    };
    // Element class
    private class Element
    {
        K key; V value;
        public Element(K key, V value)
        {
            this.key = key;
            this.value = value;
        }
        //if e = f return value = 0
        //if e < f return value is negative
        //if e > f return value is positive
        public int compareTo(Element that)
        {
            return this.key.compareTo(that.key);
        }
        public String toString()
        {
            return (key.toString());
        }
    }
    
    // private methods implementing BST operations search, insert, delete, inorder
    // reference: Wikipedia article on Binary Search Tree
    //
 
    // search for element in tree
    private Element search(Element element, BinaryTree<Element> tree)
    {
        if(tree == null)
            return null;
        else if(element.compareTo(tree.getRoot()) == 0)
            return tree.getRoot();
        else if(element.compareTo(tree.getRoot()) < 0)
            return search(element, tree.getLeft());
        else
            return search(element, tree.getRight());
    }
    /*
     if tree is empty, creates new tree with element
     if tree exists, calls insert(Element e, BinaryTree<E> t)
     */
   private Element insert(Element element)
    {
        if(map == null)
        {
            map = new BinaryTree<Element>(element);
            return null;
        }
        else return insert(element, map);
    }
    /*
     Check to see if the tree is empty, if empty, create new tree.
     */
    private Element insert(Element element, BinaryTree<Element> tree)
    {
        if(tree == null)
        {
            tree = new BinaryTree<Element>(element);
            map = tree;
        }
        Element r = tree.getRoot();
        if(element.compareTo(r) == 0)
        {
            tree = new BinaryTree<Element>(element);
            return r;
        }
        else if(element.compareTo(r) < 0)
        {
            if(tree.getLeft() == null)
            {
                tree.setLeft(new BinaryTree<Element>(element));
                return null;
            }
            else
                return insert(element, tree.getLeft());
        }
            else
            {
                if(tree.getRight() == null)
                {
                    tree.setRight(new BinaryTree<Element>(element));
                    return null;
                }
                else
                    return insert(element, tree.getRight());
            }
    }
    private Element delete(BinaryTree<Element> tree, Element element, BinaryTree<Element> parent)
    {
        //initial checks to avoid NullPointerExceptions
        if(tree == null)
        {
            return null;
        }
        Element r = tree.getRoot();
        if(element.compareTo(r) < 0)
        return delete(tree.getLeft(), element, tree);
        else if(element.compareTo(r) > 0)
        {
            delete(tree.getRight(), element, tree);
        }
        else
        {
            //removing leaves
            if(tree.isLeaf())
            {
                if(parent.getLeft() == tree)
                    parent.setLeft(null);
                else if (parent.getRight() == tree)
                    parent.setRight(null);
            }
            else if(tree.getLeft() != null && tree.getRight() == null)
            {
                Element t = null;
                tree = tree.getRight();
                while(tree.getLeft() != null)
                {
                    tree = tree.getLeft();
                    t = tree.getRoot();
                }
                    delete(tree, t, null);
                    tree.setRoot(t);
            }
            else if(tree.getRight() != null && tree.getLeft() == null)
                    promote(null, parent, tree.getRight());
        }
        return element;
    }
    // make newChild the appropriate (left or right) child of parent, if parent exists
    private void promote(BinaryTree<Element> tree, BinaryTree<Element> parent, BinaryTree<Element> newChild)
    {
        if(parent == null)
            map = newChild;
        else if(tree==parent.getLeft())
            parent.setLeft(newChild);
        else
            parent.setRight(newChild);
    }
    //recursively traverse through the tree
    private void inorder(BinaryTree<Element> tree)
    {
        if(tree.getLeft() != null)
            inorder(tree.getLeft());
        keys.add(tree.getRoot().key);
        if(tree.getRight() != null)
            inorder(tree.getRight());
    }
    private int height(BinaryTree<Element> tree)
    {
        if(tree == null)
            return -1;
        else
        {
            int leftH = height(tree.getLeft());
            int rightH = height(tree.getRight());
            //returns the larger value of leftH and rightH
            return Math.max(leftH, rightH) + 1;
        }
    }
}

