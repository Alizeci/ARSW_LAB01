/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.threads;

/**
 * Creación de hilos. Recibe los rangos de los números necesarios.
 * @author Angélica Alfaro & Laura Izquierdo
 */
public class CountThreadsMain {  
    public static void main(String a[]){
        Thread t0 = new Thread(new CountThread(0,99));
        Thread t1 = new Thread(new CountThread(99,199));
        Thread t2 = new Thread(new CountThread(200,299));
        t0.run();
        t1.run();
        t2.run();
        System.out.println();
    }
}