
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
        // Se crean los nodos centinelas, cuya referencia es muta
        head = new Node<E>(null, tail, null);
        tail = new Node<E>(null, null, head);
    }

    /**
     * Inserta un elemento antes del nodo dado.
     *
     * @param node Node Nodo referencia.
     * @param e E Elemento a insertar.
     */
    protected void addBefore(Node<E> node, E e) {
        Node<E> newNode = new Node<E>(e, node, node.previous);
        // Se actualizan las referencias de los nodos apuntados por el nuevo nodo
        node.previous.next = newNode;
        node.previous = newNode;
        size++;
    }

    /**
     * Inserta un elemento en una lista vacia.
     *
     * @param e E Elemento a insertar.
     */
    protected void addOnEmpty(E e) {
        Node<E> newNode = new Node<E>(e,tail,head);
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
        Node<E> newNode = new Node<E>(e,tail,tail.previous);
        tail.previous.next = newNode;
        tail.previous = newNode;
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
        if (index < size/2) {
            current = head.next;
            for (int i = 0; i < index; i++) {
                current = current.next;
            }
        } else {
            current = tail.previous;
            for (int i = size-1; i > index; i--) {
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
        node.previous.next = node.next;
        node.next.previous = node.previous;
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
        E oldValue = node.elem;
        node.elem = e;
        return oldValue;
    }    

    @Override
    public void add(int index, E e) {
        if(size == 0 && index == 0){
            addOnEmpty(e);
        } else if(index == size) {
            addLast(e);
        } else {
            Node<E> node = getNode(index);
            addBefore(node, e);
        }
    }    

    @Override
    public E remove(int index) {
        Node<E> node = getNode(index);
        E elem = node.elem;
        removeNode(node);
        return elem;
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

        /*
         * Constructor de un nodo que almacena:
         * Un elemento de tipo genérico
         * Una referencia al siguiente nodo
         * Una referencia al nodo previo
         */
        public Node(E elem, Node<E> next, Node<E> previous) {
            this.elem = elem;
            this.next = next;
            this.previous = previous;
        }

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
            /*En lugar de tomar el primer nodo real de la lista:
             * getNode(index)
             * Se toma el siguiente de la cabeza, es lo mismo pero queda más claro que head es el nodo centinela por el que se empieza
             */
            siguiente = head.next;
            canRemove = false;
        }

        @Override
        public boolean hasNext() {
            /*En vez de saber si el índice es válido para la siguiente posición:
             * index < size
             * Se hace una valoración si el nodo siguiente es diferente a la cola
             */
            return siguiente != tail;
        }

        @Override
        public E next() {
            if (!hasNext()) throw new NoSuchElementException();
            /*No cambia nada, ya que el método regresa un objeto almacenado en el nodo y el siguiente se actualiza el siguiente nodo, además de activar la bandera*/
            E e = siguiente.elem;
            siguiente = siguiente.next;
            index++;
            canRemove = true;

            return e;
        }


        @Override
        public void remove() {
            if (!canRemove) throw new IllegalStateException();

            Node<E> toRemove = siguiente.previous;
            removeNode(toRemove);
            index--;
            canRemove = false;
        }
    }

    @Override
    public String toString() {
        if (isEmpty()) return "[]";

        StringBuilder sb = new StringBuilder();
        Node<E> aux = head.next;
        
        sb.append("[");
        while (aux != tail) {
            sb.append(aux.elem);
            aux = aux.next;
            if (aux != tail) sb.append(" ");
        }
        sb.append("]");
        
        return sb.toString();
    }
}
