package edu.eci.arsw.threads;

import java.util.List;

import edu.eci.arsw.spamkeywordsdatasource.HostBlacklistsDataSourceFacade;

/**
 * Thread de búsqueda de segmentos.
 * @author Angélica Alfaro & Laura Izquierdo
 */
public class SearchThread extends Thread {
	private static final int BLACK_LIST_ALARM_COUNT=5;
	
	private String ip;
	private int inicioSegmento;
	private int FinSegmento;
	private List<Integer> ocurrenciasMaliciosas;
	private int checkedListsCount;
	private int ocurrencesListsCount;
	private HostBlacklistsDataSourceFacade source;
	
	
	public SearchThread(String ip, int inicioSegmento, int FinSegmento, HostBlacklistsDataSourceFacade source){
		this.ip = ip;
		this.inicioSegmento = inicioSegmento;
		this.FinSegmento = FinSegmento;
		this.source = source;
		this.checkedListsCount = 0;
	};
	
	@Override
	public void run() {
		
		for (int i = inicioSegmento; i < FinSegmento; i++) {
			checkedListsCount++;
			if (source.isInBlackListServer(i, ip)) {
				ocurrenciasMaliciosas.add(i);
				ocurrencesListsCount++;
			}
		}
	}
	
	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public int getInicioSegmento() {
		return inicioSegmento;
	}

	public void setInicioSegmento(int inicioSegmento) {
		this.inicioSegmento = inicioSegmento;
	}

	public int getFinSegmento() {
		return FinSegmento;
	}

	public void setFinSegmento(int finSegmento) {
		FinSegmento = finSegmento;
	}

	public List<Integer> getOcurrenciasMaliciosas() {
		return ocurrenciasMaliciosas;
	}

	public void setOcurrenciasMaliciosas(List<Integer> ocurrenciasMaliciosas) {
		this.ocurrenciasMaliciosas = ocurrenciasMaliciosas;
	}

	public int getCheckedListsCount() {
		return checkedListsCount;
	}

	public void setCheckedListsCount(int checkedListsCount) {
		this.checkedListsCount = checkedListsCount;
	}

	public int getOcurrencesListsCount() {
		return ocurrencesListsCount;
	}

	public void setOcurrencesListsCount(int ocurrencesListsCount) {
		this.ocurrencesListsCount = ocurrencesListsCount;
	}

	public HostBlacklistsDataSourceFacade getSource() {
		return source;
	}

	public void setSource(HostBlacklistsDataSourceFacade source) {
		this.source = source;
	}

}
