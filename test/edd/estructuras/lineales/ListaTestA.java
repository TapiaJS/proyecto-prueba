/**
 * Código utilizado para el curso de Estructuras de Datos.
 *
 * Se permite consultarlo para fines didácticos de forma personal, pero no está
 * permitido transferirlo tal cual a estudiantes actuales o potenciales pues se
 * afectará su realización de los ejercicios.
 */

package edd.estructuras.lineales;

import java.util.Collection;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.NoSuchElementException;

import org.junit.Test;
import static org.junit.Assert.*;

import edd.Calificador;

/**
 * Clase que agrega pruebas unitarias para las clases hijas de la clase List.
 *
 * @author mindahrelfen
 */
public abstract class ListaTestA extends Calificador {

    @Override
    protected void setCategories() {
        defineCategories(new String[] {
            "size",
            "empty",
            "get",
            "set",
            "add",
            "remove"
        }, new double[] {
            0.05,
            0.05,
            0.15,
            0.15,
            0.25,
            0.35
        });
    }

    @Override
    public void init() {}

    /**
     * Devuelve una lista bien construida.
     *
     * @return List Una lista de tipo String vacía.
     */
    protected abstract List<String> getList();

    // size

    @Test
    public void addSizeTest() {
        int index;
        List<String> list;

        startTest("Revisa que la estructura mantenga la cantidad correcta de elementos tras insertar con add(int, E)", 1.0, "size");

        /**
         * Inserta elementos en la estructura de uno en uno y revisa que cada
         * inserción mantenga el tamaño deseado.
         */
        list = getList();
        rsgIt = rsg.iterator();
        index = 1;
        while (rsgIt.hasNext()) {
            list.add(rdm.nextInt(list.size() + 1), rsgIt.next());
            assertEquals(list.size(), index++);
        }

        addUp(1.0);
        passed();
    }

    @Test
    public void removeSizeTest() {
        int index;
        List<String> list;

        startTest("Revisa que la cantidad de elementos tras borrar sea consistente con remove(int)", 1.0, "size");

        /**
         * Inserta elementos en la estructura.
         */
        list = getList();
        rsgIt = rsg.iterator();
        while (rsgIt.hasNext()) {
            list.add(rdm.nextInt(list.size() + 1), rsgIt.next());
        }

        /**
         * Borra los elementos de la estructura de uno en uno y revisa que
         * cada borrado mantenga el tamaño deseado.
         */
        index = 1;
        while (index <= range) {
            list.remove(rdm.nextInt(list.size()));
            assertEquals(list.size(), range - index++);
        }

        addUp(1.0);
        passed();
    }

    // empty

    @Test
    public void addEmptyTest() {
        List<String> list;

        startTest("Revisa que la estructura no este vacía tras insertar con add(int, E)", 1.0, "empty");

        /**
         * Inserta elementos en la estructura de uno en uno y revisa que ninguna
         * inserción vuelva vacía la estructura.
         */
        list = getList();
        rsgIt = rsg.iterator();
        while (rsgIt.hasNext()) {
            list.add(rdm.nextInt(list.size() + 1), rsgIt.next());
            assertFalse(list.isEmpty());
        }

        addUp(1.0);
        passed();
    }

    @Test
    public void removeEmptyTest() {
        int index;
        List<String> list;

        startTest("Revisa que  tras borrar todos los elementos la lista este vacia", 1.0, "empty");

        /**
         * Inserta elementos en la estructura.
         */
        list = getList();
        rsgIt = rsg.iterator();
        while (rsgIt.hasNext()) {
            list.add(rdm.nextInt(list.size() + 1), rsgIt.next());
        }

        /**
         * Borra los elementos de la estructura de uno en uno y revisa que
         * cada borrado mantenga el tamaño deseado.
         */
        index = list.size();
        while (index > 0) {
            assertFalse(list.isEmpty());
            list.remove(rdm.nextInt(list.size()));
            index--;
        }

        /**
         * Revisa que la estructura este vacía al finalizar el borrado de
         * todos sus elementos.
         */
        assertTrue(list.isEmpty());

        addUp(1.0);
        passed();
    }

    // get

    @Test
    public void getIndexOutOfBoundsTest() {
        boolean thrown;
        List<String> list;

        startTest("Revisa que se lance IndexOutOfBoundsException si el parámetro en get(int) esta fuera de rango", 1.0, "get");

        list = getList();
        list.add(0, new String());
        thrown = false;

        try {
            list.get(-1);
        } catch (IndexOutOfBoundsException e) {
            addUp(0.5);
            thrown = true;
        }

        try {
            list.get(list.size());
        } catch (IndexOutOfBoundsException e) {
            addUp(0.5);
            if (thrown) passed();
        }
    }

    // set

    @Test
    public void setGetTest() {
        int index;
        List<String> list;

        startTest("Revisa que la estructura contenga los elementos intercambiados con set(int, E)", 1.0, "set");

        /**
         * Agrega elementos a la estructura.
         */
        index = 0;
        list = getList();
        rsgIt = rsg.iterator();
        while (rsgIt.hasNext()) {
            list.add(index++, rsgIt.next());
        }

        /**
         * En un índice dado modifica a la estructura.
         */
        index = rdm.nextInt(list.size());
        list.set(index, new String());

        /**
         * Revisa que el elemento se haya modificado correctamente.
         */
        assertEquals(list.get(index), new String());

        addUp(1.0);
        passed();
    }

    @Test
    public void setIndexOutOfBoundsTest() {
        boolean thrown;
        List<String> list;

        startTest("Revisa que se lance IndexOutOfBoundsException si el parámetro int en set(int, E) esta fuera de rango", 1.0, "set");

        list = getList();
        thrown = false;

        try {
            list.set(-1, new String());
        } catch (IndexOutOfBoundsException e) {
            addUp(0.5);
            thrown = true;
        }

        list.add(0, new String());
        try {
            list.set(list.size(),new String());
        } catch (IndexOutOfBoundsException e) {
            addUp(0.5);
            if (thrown) passed();
        }
    }

    // add

    @Test
    public void addTest() {
        int index;
        String s;
        String[] array;
        List<String> list;

        startTest("Revisa que la estructura contenga los elementos insertados con add(int, E)", 1.0, "add");

        /**
         * Inserta elementos en la estructura.
         */
        list = getList();
        rsgIt = rsg.iterator();
        while (rsgIt.hasNext()) {
            index = rdm.nextInt(list.size() + 1);
            s = rsgIt.next();
            list.add(index, s);
            assertEquals(list.get(index), s);
        }

        addUp(1.0);
        passed();
    }
    @Test
    public void addIndexOutOfBoundsTest() {
        boolean thrown;
        List<String> list;

        startTest("Revisa que se lance IndexOutOfBoundsException si el parámetro int de add(int, E) esta fuera de rango", 1.0, "add");

        list = getList();
        list.add(0, new String());
        thrown = false;

        try {
            list.add(-1, new String());
        } catch (IndexOutOfBoundsException e) {
            addUp(0.5);
            thrown = true;
        }

        try {
            list.add(list.size() + 1, new String());
        } catch (IndexOutOfBoundsException e) {
            addUp(0.5);
            if (thrown) passed();
        }
    }

    // remove

    @Test
    public void removeTest() {
        String s;
        List<String> list;
        Iterator<String> it;

        startTest("Prueba que la estructura no contenga elementos después de borrarlos con remove(int)", 1.0, "remove");

        /**
         * Inserta elementos en la estructura.
         */
        list = getList();
        rsgIt = rsg.iterator();
        while (rsgIt.hasNext()) {
            list.add(rdm.nextInt(list.size() + 1), rsgIt.next());
        }

        /**
         * Luego los expulsa hasta que la estructura este vacía.
         */
        rsgIt = rsg.iterator();
        while (!list.isEmpty()) {
            s = list.remove(rdm.nextInt(list.size()));

            /**
             * Revisa que ningún elemento eliminado se encuentre en la
             * estructura.
             */
            it = list.iterator();
            if (s == null) {
                while (it.hasNext()) {
                    assertFalse(it.next() == null);
                }
            } else {
                while (it.hasNext()) {
                    assertFalse(s.equals(it.next()));
                }
            }
        }

        addUp(1.0);
        passed();
    }

    @Test
    public void removeIndexOutOfBoundsTest() {
        boolean thrown;
        List<String> list;

        startTest("Revisa que se lance IndexOutOfBoundsException si el parámetro de remove(int) esta fuera de rango", 1.0, "remove");

        list = getList();
        list.add(0, new String());
        thrown = false;

        try {
            list.remove(-1);
        } catch (IndexOutOfBoundsException e) {
            addUp(0.5);
            thrown = true;
        }

        try {
            list.remove(list.size());
        } catch (IndexOutOfBoundsException e) {
            addUp(0.5);
            if (thrown) passed();
        }
    }
}
