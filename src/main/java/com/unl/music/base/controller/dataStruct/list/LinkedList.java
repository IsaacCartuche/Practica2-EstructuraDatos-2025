package com.unl.music.base.controller.dataStruct.list;

import javax.management.RuntimeErrorException;

public class LinkedList<E> {
    private Node<E> head;
    private Node<E> last;
    private Integer length;

    // getters
    // ================================================================================================

    public Integer getLength() {
        return this.length;
    }

    // Inicializador
    // ==========================================================================================

    public LinkedList() {
        head = null;
        last = null;
        length = 0;
    }

    // metodo para verificar si la lista esta vacia
    // ===========================================================

    public boolean isEmpty() {
        return head == null || length == 0;
    }

    // metodos de agregacion modular, solo hay que llamar al metodo " add(E
    // data)/add(E data, Integer pos) " ===

    private void addFirst(E data) {
        if (isEmpty()) {
            Node<E> aux = new Node<>(data);
            head = aux;
            last = aux;
            //System.out.println("se creo en la cabecera");
        } else {
            Node<E> head_old = head;
            Node<E> aux = new Node<>(data, head_old);
            head = aux;
            //System.out.println("se inserto al final");

        }
        length++;
    }

    private void addLast(E data) {
        if (isEmpty()) {
            addFirst(data);

        } else {
            Node<E> aux = new Node<>(data);
            last.setNext(aux);
            last = aux;
            length++;
            //System.out.println("se inserto al final");
        }
    }

    public void add(E data, Integer pos) throws Exception {
        if (pos == 0) {
            addFirst(data);

        } else if (length.intValue() == pos.intValue()) {
            addLast(data);
        } else {
            Node<E> search_preview = getNode(pos - 1);
            Node<E> search = getNode(pos);
            Node<E> aux = new Node<>(data, search);
            search_preview.setNext(aux);
            length++;
            //System.out.println("se inserto en la posicion" + pos);
        }
    }

    public void add(E data) {
        addLast(data);
    }

    // metodos de obtencion de nodos, usar solo get()
    // =================================================

    private Node<E> getNode(Integer pos) {
        if (isEmpty()) {
            throw new ArrayIndexOutOfBoundsException("List Empty");
            // System.out.println("esta vacia");
            // return null;

        } else if (pos < 0 || pos >= length) {
            throw new ArrayIndexOutOfBoundsException("Fuera de rango");

            // System.out.println("fuera de rango");
            // return null;
        } else if (pos == 0) {
            return head;
        } else if ((length.intValue() - 1) == pos.intValue()) {
            return last;
        } else {
            Node<E> search = head;
            Integer cont = 0;
            while (cont < pos) {
                cont++;
                search = search.getNext();
            }
            return search;
        }
    }

    private E getDataFirst() {
        if (isEmpty()) {
            throw new ArrayIndexOutOfBoundsException("List Empty");
            // System.out.println("esta vacia");
            // return null;
        } else {
            return head.getData();
        }

    }

    private E getDataLast() {
        if (isEmpty()) {
            throw new ArrayIndexOutOfBoundsException("List Empty");
            // System.out.println("esta vacia");
            // return null;
        } else {
            return last.getData();
        }

    }

    public E get(Integer pos) throws Exception {
        return getNode(pos).getData();
    }

    // metodo de actualizacion de datos de un nodo
    // ======================================================

    public void update(E data, Integer pos) {
        getNode(pos).setData(data);
    }

    // metodos para eliminar nodos
    // ======================================================================

    // public void deleteNode(Integer pos) {
    //     if (isEmpty()) {
    //         throw new ArrayIndexOutOfBoundsException("List Empty");
    //     } else if (length == 1 & pos == 0) {
    //         clear();
    //     } else if (length == 2 & pos <= 1) {
    //         deleteHeadLast(pos);
    //     } else if (length > 2) {
    //         delete(pos);
    //     } else {
    //         throw new ArrayIndexOutOfBoundsException("Fuera de rango");
    //     }

    // }

    // private void delete(Integer pos) {
    //     if (pos == 0) {
    //         deleteFirst();
    //     } else if (pos == (length - 1)) {
    //         deleteLast();
    //     } else {
    //         getNode(pos - 1).setNext(getNode(pos + 1));
    //     }
    // }

    // private void deleteHeadLast(Integer pos) {
    //     if (pos == 0) {
    //         deleteFirst();
    //     } else if (pos == 1) {
    //         deleteLast();
    //     }
    // }

    // private void deleteFirst() {
    //     Node aux = getNode(1);
    //     head = aux;
    //     last = aux;
    // }

    // private void deleteLast() {
    //     getNode(length - 2).setNext(null);
    // }

    // metodos de eliminacion vistos en clase
    protected E deleteFirst() throws Exception {
        if (isEmpty()) {
            throw new Exception("List empty");
        } else {
            E element = head.getData();
            Node<E> aux = head.getNext();
            head = aux;
            if (length.intValue() == 1)
                last = null;
            length--;
            return element;
        }
    }

    protected E deleteLast() throws Exception {
        if (isEmpty()) {
            throw new Exception("List empty");
        } else {
            E element = last.getData();
            Node<E> aux = getNode(length - 2);
            if (aux == null) {
                last = null;
                if (length == 2) {
                    last = head;
                } else {
                    head = null;
                }
            } else {
                last = null;
                last = aux;
                last.setNext(null);
            }
            length--;
            return element;
        }
    }

    public E delete(Integer pos) throws Exception {
        if (isEmpty()) {
            throw new ArrayIndexOutOfBoundsException("List empty");

        } else if (pos < 0 || pos >= length) {
            throw new ArrayIndexOutOfBoundsException("Index out range");
        } else if (pos == 0) {
            return deleteFirst();
        } else if ((length.intValue() - 1) == pos.intValue()) {
            return deleteLast();
        } else {
            Node<E> preview = getNode(pos - 1);
            Node<E> actualy = getNode(pos);
            E element = preview.getData();
            Node<E> next = actualy.getNext();
            actualy = null;
            preview.setNext(next);
            length--;
            return element;
        }
    }

    // metodo para vaciar la lista
    // ======================================================================

    public void clear() {
        head = null;
        last = null;
        length = 0;
    }

    // metodo para imprimir la lista
    // ===================================================================

    public String print() {
        if (isEmpty()) {
            return "esta vacia";
        } else {
            StringBuilder resp = new StringBuilder();
            Node<E> help = head;
            while (help != null) {
                resp.append(help.getData()).append(" - ");
                help = help.getNext();
            }
            resp.append("\n");
            return resp.toString();
        }
    }

    // metodo para convertir la lista enlazada a un arreglo para poder ser guardado
    // en archivo .json
    public E[] toArray() {
        Class clazz = null;
        E[] matriz = null;

        if (this.length > 0) {
            clazz = head.getData().getClass();
            matriz = (E[]) java.lang.reflect.Array.newInstance(clazz, this.length);

            Node<E> aux = head;

            for (int i = 0; i < length; i++) {
                matriz[i] = aux.getData();
                aux = aux.getNext();
            }

        }

        return matriz;
    }

    // meetodo que convierte un arreglo a una lista enlazada

    public LinkedList<E> toList(E[] matriz) {
        clear();
        for (int i = 0; i < matriz.length; i++) {
            this.add(matriz[i]);

        }
        return this;
    }

    public static void main(String[] args) {
        LinkedList lista = new LinkedList<>();

        // try {

        //     lista.add("0");
        //     lista.add("1");
        //     lista.add("2");
        //     lista.add("3");

        //     System.out.println(lista.print());
        //     lista.deleteNode(2);
        //     System.out.println(lista.print());
        // } catch (Exception e) {
        //     System.out.println("Erro" + e);
        // }

    }

}
