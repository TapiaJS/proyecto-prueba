/**
 * Código utilizado para el curso de Estructuras de Datos.
 *
 * Se permite consultarlo para fines didácticos de forma personal, pero no está
 * permitido transferirlo tal cual a estudiantes actuales o potenciales pues se
 * afectará su realización de los ejercicios.
 */

package edd.estructuras.lineales;

/**
 * Clase que inicia el uso de pruebas unitarias para la clase Lista Doblemente
 * Ligada.
 *
 * @author mindahrelfen
 */
public class LinkedListTest extends ListaTestA {

    @Override
    protected List<String> getList() {
        return new LinkedList<>();
    }
}
