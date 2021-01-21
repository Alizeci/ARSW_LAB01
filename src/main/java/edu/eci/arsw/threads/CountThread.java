/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.threads;

/**
 * Imprime por pantalla los números entre A y B.
 * @author Angélica Alfaro & Laura Izquierdo
 */
public class CountThread implements Runnable{
	private int A;
	private int B;
	
	public CountThread(int A, int B) {
		this.A = A;
		this.B = B;
	}
	
	@Override
	public void run() {
		for(int i = A+1; i < B; i++) {
			System.out.print(i+" ");
		}
	}
}
