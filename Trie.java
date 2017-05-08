/**
 * Created by DJ on 5/6/2017.
 * Trie: A tree based Data structure meant to hold large amount of data that could have the same prefix
 * For example: all numbers beginning with 123 would share the same memory space for 123.
 * <p>
 * This is beneficial for storing objects like names or numbers, quickly finding that name, or even finding out how many
 * names (or numbers) begin with a given prefix
 * <p>
 * This class could be abstracted out to handle different offsets and different node
 * there are also times when you just want to handle the recursive adding in the node class
 */


/*
* notice this class does not have a "data" object
* the actual data type is figured out based on the index the node is in
* for example, the first index of children will represent an 'a' or 0.., the last will be a 'z' or a 9
* therefore having extra memory take up that space is not efficient    *
 */
class Node {
    /* The child nodes could have a size of:
     9 (or 10) representing each number
     2 representing binary (or morse code as i have had to do before)
     26 representing the alphabet
     X for whatever else i missed
     */
    Node children[];
    /*
    indicator that this is the last part of the word "run" the node for 'n' would be marked as the end
    why?
    if we add "run", then add "runner" the first three letters are the same, so we continue on after the first 'n' node
    so instead of saying "there are no more nodes, this must be the end" we just check to see if this is the end of one
    of the added items
     */
    boolean end;

    Node(int size) {
        children = new Node[size];
    }
}

abstract class Trie {
    private final int offset; // set offset to the ascii value of 'a', or '0'
    private final int childSize;
    private final Node root;

    protected Trie(int offset, int childSize) {
        this.offset = offset;
        this.childSize = childSize;
        root = createNode();
    }

    // expects non null and non empty word
    public void addWithRecursion(String word) {
        recursiveAdd(word, 0, root);
    }

    // separate method call because we do not want to expose the nodes to the consumer
    private void recursiveAdd(String word, int index, Node parentNode) {
        Node childNode = getOrCreateNode(parentNode, word.charAt(index));
        if (word.length() - 1 == index) {
            childNode.end = true;
        } else {
            recursiveAdd(word, index + 1, childNode);
        }
    }

    // expects non null and non empty word
    public void addWithIteration(String word) {
        Node currentNode = root;
        int index = 0;
        do {
            currentNode = getOrCreateNode(currentNode, word.charAt(index));
            index++; // this incrementation could be done on the previous line instead
        } while (index < word.length());
        currentNode.end = true;
    }

    private Node getNode(Node currentNode, char letter) {
        /*examples:
         'a' - offset === 0, 'z' - offset === 25
          '0' - offset === 0, '9' - offset === 9 ('1' - offset === 1)
          '-' - offset === 0, '.' - offset === 1
          */
        int index = letter - offset;
        return getNode(currentNode, index);
    }

    private Node getNode(Node currentNode, int index) {
        return currentNode.children[index];
    }

    private Node getOrCreateNode(Node currentNode, char letter) {
        int index = letter - offset;
        Node result = getNode(currentNode, index);
        if (result == null) {
            result = currentNode.children[index] = createNode(); // sets both of these to the created node
        }
        return result;
    }

    private Node getNodeFromPrefix(String prefix) {
        Node currentNode = root;
        int index = 0;
        while (index < prefix.length()) {
            currentNode = getNode(currentNode, prefix.charAt(index++));
            if (currentNode == null) {
                return null;
            }
        }
        return currentNode;
    }

    // another iteration example, this one allows empty strings, still no null
    public boolean contains(String query) {
        Node currentNode = getNodeFromPrefix(query);
        if (currentNode == null) {
            return false;
        }
        // making it this far means that the query is in the trie, OR a word that starts with the query is in the trie
        return currentNode.end; // this makes sure we are saying whether the query was actually added
    }

    public void printAll() {
        printChildren("", root);
    }

    // expects non null, example all words starting with "s" would be printed
    public void printAllStartingWith(String prefix) {
        Node startingNode = getNodeFromPrefix(prefix);
        if (startingNode != null) {
            printChildren(prefix, startingNode);
        }
    }

    // another recursion example
    private void printChildren(String prefix, Node currentNode) {
        if (currentNode.end) {
            System.out.println(prefix);
        }

        for (int x = 0; x < childSize; x++) {
            Node childNode = currentNode.children[x];
            if (childNode != null) {
                printChildren(prefix + (char) (x + offset), childNode); // 0 + offset = 'a', 25 + offset = 'z'
            }
        }
    }

    private Node createNode() {
        return new Node(childSize);
    }
}

class StringTrie extends Trie {
    //for this example we'll only use the lowercase letters a-z
    StringTrie() {
        super('a', 26);
    }
}

class NumberTrie extends Trie {
    //for this example we'll use the digits 0-9
    NumberTrie() {
        super('0', 10);
    }
}

class BinaryTrie extends Trie {
    //for this example we'll use the digits 0 and 2
    BinaryTrie() {
        super('0', 2);
    }
}

class MorseCodeTrie extends Trie {
    public MorseCodeTrie() {
        super('-', 2); //this works because '-' and '.' are next to each other in the ascii chart
    }
}