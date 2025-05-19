package com.unl.music.base.controller;

import java.io.File;

import com.unl.music.base.controller.dataStruct.list.LinkedList;

public class PruebaEjec {
    public static void main(String[] args) throws Exception {
        // prueba de ejecución de la practica 2 =================================================================================================
        //con arreglos
        PP1A_Controller arregloPrueba = new PP1A_Controller();
        long startTime = System.nanoTime();
        String rutaArchivo = "data/data.txt" + File.separatorChar;
        int[] array = arregloPrueba.procesarArchivo(rutaArchivo);
        String result = arregloPrueba.BuscarRepetidos(array);
        System.out.println("El numero repetido es: " + result);
        long endTime = System.nanoTime();
        arregloPrueba.mostrarTiempoEjecucion(startTime, endTime);






        // con listas enlazadas
        // PP1L_Controller ListaPrueba = new PP1L_Controller();

        // long startTime = System.nanoTime();
        // String rutaArchivo = "data/data.txt" + File.separatorChar;
        // LinkedList array = ListaPrueba.procesarArchivo(rutaArchivo);
        // LinkedList result = ListaPrueba.BuscarRepetidos(array);
        // System.out.println("El numero repetido es:");
        // System.out.println(result.print());
        // long endTime = System.nanoTime();
        // ListaPrueba.mostrarTiempoEjecucion(startTime, endTime);
    }
}