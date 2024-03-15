
package edd.estructuras.lineales;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Implementación de Lista Doblemente Ligada.
 *
 * @author mindahrelfen
 */
public class LinkedList<E> implements List<E> {

    /**
     * Referencia al inicio de la lista.
     */
    protected Node<E> head;

    /**
     * Referencia al final de la lista.
     */
    protected Node<E> tail;

    /**
     * Cantidad de elementos dentro de esta Lista.
     */
    protected int size;

    /**
     * Construye una lista vacía.
     */
    public LinkedList() {
        head = null;
        tail = null;
        size = 0;
    }

    /**
     * Inserta un elemento antes del nodo dado.
     *
     * @param node Node Nodo referencia.
     * @param e E Elemento a insertar.
     */
    protected void addBefore(Node<E> node, E e) {
        Node<E> newNode = new Node<>();
        newNode.elem = e;
        newNode.next = node;
        
        newNode.previous = node.previous;
        if (node.previous != null) {
            node.previous.next = newNode;
            node.previous = newNode;
        } else {
            head = newNode;
        }
        node.previous = newNode;
        size++;
    }

    /**
     * Inserta un elemento en una lista vacia.
     *
     * @param e E Elemento a insertar.
     */
    protected void addOnEmpty(E e) {
        Node<E> newNode = new Node<>();
        newNode.elem = e;
        newNode.previous = head;
        newNode.next = tail;
        head.next = newNode;
        tail.previous = newNode;
        size = 1;
    }

    /**
     * Inserta un elemento al final de la lista. Supone que existe al menos
     * otro elemento dentro de la lista.
     *
     * @param e E Elemento a insertar.
     */
    protected void addLast(E e) {
        Node<E> newNode = new Node<>();
        newNode.elem = e;
        newNode.previous = tail;
        if (tail != null) {
            tail.next = newNode;
        }
        tail = newNode;
        if (head == null) {
            head = newNode;
        }
        size++;
    }

    /**
     * Obtiene el nodo en el índice dado.
     *
     * @param index int Índice del nodo a buscar.
     *
     * @return Node Devuelve el nodo.
     */
    protected Node<E> getNode(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }
        Node<E> current;
        if (index < size / 2) {
            current = head;
            for (int i = 0; i < index; i++) {
                current = current.next;
            }
        } else {
            current = tail;
            for (int i = size - 1; i > index; i--) {
                current = current.previous;
            }
        }
        return current;
    }

    /**
     * Borra el nodo dado.
     *
     * @param aux Node Nodo a borrar.
     */
    protected void removeNode(Node<E> node) {
        if (node.previous != null) {
            node.previous.next = node.next;
        } else {
            head = node.next;
        }
        if (node.next != null) {
            node.next.previous = node.previous;
        } else {
            tail = node.previous;
        }
        size--;
    }

    @Override
    public int size() {
        return size;
    }    

    @Override
    public boolean isEmpty() {
        return size == 0;
    }    

    @Override
    public E get(int index) {
        Node<E> node = getNode(index);
        return node.elem;
    }    

    @Override
    public E set(int index, E e) {
        Node<E> node = getNode(index);
        E oldVal = node.elem;
        node.elem = e;
        return oldVal;
    }    

    @Override
    public void add(int index, E e) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException();
        }
        if (index == size) {
            addLast(e);
        } else if (index == 0) {
            addBefore(head, e);
        } else {
            Node<E> node = getNode(index);
            addBefore(node, e);
        }
    }    

    @Override
    public E remove(int index) {
        Node<E> node = getNode(index);
        E element = node.elem;
        removeNode(node);
        return element;
    }    

    @Override
    public Iterator<E> iterator() {
        return new LinkedListIterator();
    }

    /**
     * Implementación de un nodo doblemente ligado.
     */
    protected class Node<E> {

        /**
         * Referencia al nodo anterior.
         */
        protected Node<E> previous;

        /**
         * Referencia al nodo siguiente.
         */
        protected Node<E> next;

        /**
         * Referencia al valor que este nodo guarda.
         */
        protected E elem;

        @Override
        public String toString() {
            return "<" + elem + ">";
        }
    }

    /**
     * Implementación tipo Wrapper de un iterador. Invierte el orden de los
     * datos de otro iterador, es decir, cambia los métodos next por previous.
     */
    protected class LinkedListIterator implements Iterator<E> {

        /**
         * Referencia al nodo que contiene el valor resultante de invocar next.
         */
        protected Node<E> siguiente;

        /**
         * Bandera que dice si se puede borrar un elemento o no.
         */
        protected boolean canRemove;

        /**
         * Referencia al índice al cual el iterador actualmente apunta.
         */
        protected int index;

        /**
         * Crea un iterador que apunta al primer
         * elemento.
         */
        public LinkedListIterator() {
            index = 0;
            siguiente = getNode(index);
            canRemove = false;
        }

        @Override
        public boolean hasNext() {
            return index < size;
        }

        @Override
        public E next() {
            E e;

            if (!hasNext()) throw new NoSuchElementException();

            e = siguiente.elem;
            siguiente = siguiente.next;
            index++;
            canRemove = true;

            return e;
        }


        @Override
        public void remove() {
            if (!canRemove) throw new IllegalStateException();

            if (siguiente != null) {
                removeNode(siguiente.previous);
            } else {
                removeNode(tail);
            }
            index--;
            canRemove = false;
        }
    }

    @Override
    public String toString() {
        StringBuilder sb;
        Node<E> aux;

        if (isEmpty()) return "[]";

        sb = new StringBuilder();
        aux = head;

        sb.append("[");
        while (aux != null) {
            sb.append(aux.toString());
            aux = aux.next;
            if (aux != tail) sb.append(" ");
        }
        sb.append("]");

        return sb.toString();
    }
}
