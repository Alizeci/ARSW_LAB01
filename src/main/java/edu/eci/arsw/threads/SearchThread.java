package edu.eci.arsw.threads;

import java.util.LinkedList;
import edu.eci.arsw.spamkeywordsdatasource.HostBlacklistsDataSourceFacade;

/**
 * Thread de búsqueda en segmentos de Black lists.
 * @author Angélica Alfaro & Laura Izquierdo
 */
public class SearchThread extends Thread {
	private String ip;
	private int inicioSegmento;
	private int finSegmento;
	private int checkedListsCount;
	private LinkedList<Integer> ocurrenciasMaliciosas;
	private HostBlacklistsDataSourceFacade dataSource;
	
	/**
	 * Realiza la búsqueda de la dirección ip dada, entre el segmento de black lists indicado.
	 * @param ip dirección a revisar en black lists
	 * @param inicioSegmento inicio del segmento de la black list a revisar
	 * @param finSegmento fin del segmento de la black list a revisar
	 * @param dataSource el conjunto de black lists
	 */
	public SearchThread(String ip, int inicioSegmento, int finSegmento, HostBlacklistsDataSourceFacade dataSource){
		this.ip = ip;
		this.inicioSegmento = inicioSegmento;
		this.finSegmento = finSegmento;
		this.dataSource = dataSource;
		this.checkedListsCount = 0;
		ocurrenciasMaliciosas = new LinkedList<>();
	};
	
	@Override
	public void run() {
		for (int i = inicioSegmento; i < finSegmento; i++) {
			checkedListsCount++;
			if (dataSource.isInBlackListServer(i, ip)) {
				ocurrenciasMaliciosas.add(i);
			}
		}
	}
	
	public LinkedList<Integer> getOcurrenciasMaliciosas() {
		return ocurrenciasMaliciosas;
	}

	public int getCheckedListsCount() {
		return checkedListsCount;
	}
}
