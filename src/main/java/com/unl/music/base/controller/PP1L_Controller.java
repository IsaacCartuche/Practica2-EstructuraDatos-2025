package com.unl.music.base.controller;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import com.unl.music.base.controller.dataStruct.list.LinkedList;

public class PP1L_Controller {
    public LinkedList lista = new LinkedList<>();
    
    public static String[] Contenido(String nombreArchivo) throws IOException {
        FileReader fr = new FileReader(nombreArchivo);
        BufferedReader br = new BufferedReader(fr);
        StringBuilder sb = new StringBuilder();
        String linea;
        while ((linea = br.readLine()) != null) {
            sb.append(linea).append("\n");
        }
        br.close();
        return sb.toString().split("\n");
    }

    public static LinkedList procesarArchivo(String rutaArchivo) throws Exception {
        String[] datos = Contenido(rutaArchivo);
        LinkedList lista = new LinkedList<>();
        for (int i = 0; i < datos.length; i++) {
            if (!datos[i].isEmpty()) {
                lista.add(Integer.parseInt(datos[i]));
            } else {
                throw new ArrayIndexOutOfBoundsException("List Empty");
            }
        }
        return lista;
    }

    public static LinkedList BuscarRepetidos(LinkedList lista) throws Exception {
        String aux;
        
        LinkedList result = new LinkedList<String>();
        if(!lista.isEmpty()){
            for(int i = 0 ; i< lista.getLength() ; i++){
                aux = lista.get(i).toString();
                for(int j = i+1; j < lista.getLength() - 1; j++){
                    if (aux.equals(lista.get(j).toString())) {
                        result.add(aux);
                        return result;
                    }
                }
            } 
        }
        
        return result;
    }

    public static void mostrarTiempoEjecucion(long startTime, long endTime) {
        long duration = endTime - startTime;
        System.out.println("Tiempo de ejecución: " + duration + " nanosegundos");
    }
}
