
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
        throw new UnsupportedOperationException("Metodo sin implementar.");
    }

    /**
     * Inserta un elemento en una lista vacia.
     *
     * @param e E Elemento a insertar.
     */
    protected void addOnEmpty(E e) {
        throw new UnsupportedOperationException("Metodo sin implementar.");
    }

    /**
     * Inserta un elemento al final de la lista. Supone que existe al menos
     * otro elemento dentro de la lista.
     *
     * @param e E Elemento a insertar.
     */
    protected void addLast(E e) {
        throw new UnsupportedOperationException("Metodo sin implementar.");
    }

    /**
     * Obtiene el nodo en el índice dado.
     *
     * @param index int Índice del nodo a buscar.
     *
     * @return Node Devuelve el nodo.
     */
    protected Node<E> getNode(int index) {
        throw new UnsupportedOperationException("Metodo sin implementar.");
    }

    /**
     * Borra el nodo dado.
     *
     * @param aux Node Nodo a borrar.
     */
    protected void removeNode(Node<E> aux) {
        throw new UnsupportedOperationException("Metodo sin implementar.");
    }

    @Override
    public int size() {
        throw new UnsupportedOperationException("Metodo sin implementar.");
    }

    @Override
    public boolean isEmpty() {
        throw new UnsupportedOperationException("Metodo sin implementar.");
    }

    @Override
    public E get(int index) {
        throw new UnsupportedOperationException("Metodo sin implementar.");
    }

    @Override
    public E set(int index, E e) {
        throw new UnsupportedOperationException("Metodo sin implementar.");
    }

    @Override
    public void add(int index, E e) {
        throw new UnsupportedOperationException("Metodo sin implementar.");
    }

    @Override
    public E remove(int index) {
        throw new UnsupportedOperationException("Metodo sin implementar.");
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
