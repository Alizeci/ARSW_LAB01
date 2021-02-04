package edu.eci.arsw.threads;

import java.util.LinkedList;
import java.util.concurrent.atomic.AtomicInteger;

import edu.eci.arsw.spamkeywordsdatasource.HostBlacklistsDataSourceFacade;

/**
 * Thread de búsqueda en segmentos de Black lists.
 * @author Angélica Alfaro & Laura Izquierdo
 */
public class SearchThread extends Thread {
	private String ip;
	private int inicioSegmento;
	private int finSegmento;
	private AtomicInteger checkedListsCount;
	private AtomicInteger currentOcurrences;
	private LinkedList<Integer> ocurrenciasMaliciosas;
	private HostBlacklistsDataSourceFacade dataSource;
	
	private static final int BLACK_LIST_ALARM_COUNT = 5;
	
	/**
	 * Realiza la búsqueda de la dirección ip dada, entre el segmento de black lists indicado.
	 * @param ip dirección a revisar en black lists
	 * @param inicioSegmento inicio del segmento de la black list a revisar
	 * @param finSegmento fin del segmento de la black list a revisar
	 * @param dataSource el conjunto de black lists
	 * @param currentOcurrences numero de ocurrencias global
	 * @param blackListOcurrences lista de ocurrencias global
	 * @param checkedListsCount numero de revisiones global
	 */
	public SearchThread(String ip, int inicioSegmento, int finSegmento, HostBlacklistsDataSourceFacade dataSource, AtomicInteger currentOcurrences, LinkedList<Integer> blackListOcurrences, AtomicInteger checkedListsCount){
		this.ip = ip;
		this.inicioSegmento = inicioSegmento;
		this.finSegmento = finSegmento;
		this.dataSource = dataSource;
		this.checkedListsCount = checkedListsCount;
		this.currentOcurrences = currentOcurrences;
		this.ocurrenciasMaliciosas = blackListOcurrences;	
	};
	
	@Override
	public void run() {
		for (int i = inicioSegmento; i < finSegmento & currentOcurrences.get() < BLACK_LIST_ALARM_COUNT; i++) {
			checkedListsCount.getAndIncrement();
			if (dataSource.isInBlackListServer(i, ip)) {
				ocurrenciasMaliciosas.add(i);
				currentOcurrences.getAndIncrement();
			}
		}
	}
	
	public LinkedList<Integer> getOcurrenciasMaliciosas() {
		return ocurrenciasMaliciosas;
	}

	public AtomicInteger getCheckedListsCount() {
		return checkedListsCount;
	}
}
