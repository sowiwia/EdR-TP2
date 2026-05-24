package aed;

import java.util.ArrayList;

public class Heap<T extends Comparable<T>>{
    private ArrayList<HandleHeap> colaDePrioridad;
    private int heapSize;

    public class HandleHeap {
        private int _posicion;
        private T _valor;

        private HandleHeap(int p, T val){
            this._posicion = p;
            this._valor = val;
        }

        public T valor(){
            return _valor;
        }
        private void actualizarPosicion(int p) {
            _posicion = p;
        }

        public void actualizarValor(T elem) {
            this._valor = elem;
            siftUp(_posicion);
            siftDown(_posicion);
        }
    }

    public Heap(){
        
        heapSize = 0;
        colaDePrioridad = new ArrayList<>(0);
    }

    public int longitud(){

        return heapSize;
    }

    private int hijoDer (int posicionPadre){

        int res = (2 * posicionPadre) + 2;
        return res;
    }

    private int hijoIzq (int posicionPadre){

        int res = (2 * posicionPadre) + 1;
        return res;
    }

    private int padre (int hijo){

        int res =  (hijo - 1) / 2;
        return res;
    }

    private void intercambiar (int pos1 , int pos2){

        HandleHeap elem1 = colaDePrioridad.get(pos1);
        HandleHeap elem2 = colaDePrioridad.get(pos2);
        
        colaDePrioridad.set(pos1, elem2);
        colaDePrioridad.set(pos2, elem1);

        elem1.actualizarPosicion(pos2);
        elem2.actualizarPosicion(pos1);

    }

    private void siftUp(int posicion){
        while (posicion > 0 && colaDePrioridad.get(posicion)._valor.compareTo(colaDePrioridad.get(padre(posicion))._valor) < 0) {
            int posicionPadre = padre(posicion);
            intercambiar(posicion, posicionPadre);
            posicion = posicionPadre;
        }
    }
    

    private void siftDown(int posicion){

        int indice = posicion;

        while(hijoIzq(indice) < heapSize ){
            int derecho = (hijoDer(indice));
            int izquierdo = (hijoIzq(indice));

            int hijoMenor = izquierdo;

            if (derecho < heapSize) {
                T valorDerecho = colaDePrioridad.get(derecho)._valor;
                T valorIzquierdo = colaDePrioridad.get(izquierdo)._valor;
                if (valorDerecho.compareTo(valorIzquierdo) < 0){
                    hijoMenor = derecho;
                }
            }

            T valorIndice = colaDePrioridad.get(indice)._valor;
            T hijoMenorValor = colaDePrioridad.get(hijoMenor)._valor;
            if (valorIndice.compareTo(hijoMenorValor) > 0){
                intercambiar(indice, hijoMenor);
                indice = hijoMenor;
            } else {
                return;
            }
        }
    }
    public HandleHeap encolar(T elem){
        HandleHeap res = new HandleHeap(heapSize, elem);

        colaDePrioridad.add(res);
        heapSize++;
        siftUp(heapSize - 1);

        return res;

    }

    public T desencolar (){
        
        if (heapSize == 0) {
            return null;
        }

        T res = colaDePrioridad.get(0)._valor;
        intercambiar(0, heapSize - 1);
        colaDePrioridad.remove(heapSize -1);
        heapSize = heapSize -1;

        if(heapSize > 0){
            siftDown(0);
        }

        return res;
    }   

}
