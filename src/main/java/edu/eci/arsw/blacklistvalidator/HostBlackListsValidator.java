/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.blacklistvalidator;

import edu.eci.arsw.spamkeywordsdatasource.HostBlacklistsDataSourceFacade;
import edu.eci.arsw.threads.SearchThread;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Validación de la ip dada en las black lists.
 * @author Angélica Alfaro & Laura Izquierdo
 */
public class HostBlackListsValidator {

    private static final int BLACK_LIST_ALARM_COUNT = 5;
    
    /**
     * Check the given host's IP address in all the available black lists,
     * and report it as NOT Trustworthy when such IP was reported in at least
     * BLACK_LIST_ALARM_COUNT lists, or as Trustworthy in any other case.
     * The search is not exhaustive: When the number of occurrences is equal to
     * BLACK_LIST_ALARM_COUNT, the search is finished, the host reported as
     * NOT Trustworthy, and the list of the five blacklists returned.
     * @param ipaddress suspicious host's IP address.
     * @return  Blacklists numbers where the given host's IP address was found.
     */
    public List<Integer> checkHost(String ipaddress, int n){
        
        LinkedList<Integer> blackListOcurrences=new LinkedList<>();
        ArrayList<SearchThread> threads = new ArrayList<>();
        
        int checkedListsCount = 0;
        
        HostBlacklistsDataSourceFacade skds=HostBlacklistsDataSourceFacade.getInstance();
        
        for (int i = 1; i < n + 1; i++) {
        	int inicioSegmento = (skds.getRegisteredServersCount() / n ) * (i - 1);
        	int finSegmento;
        	
            if (n == i) {
            	finSegmento = skds.getRegisteredServersCount();
            }else {
            	finSegmento = skds.getRegisteredServersCount() / n * i;
            }
            
            SearchThread busqueda = new SearchThread(ipaddress, inicioSegmento, finSegmento, skds);
            threads.add(busqueda);
            busqueda.start();
        }
        
        for (SearchThread pauseThread : threads) {
            try {
            	pauseThread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        
        for (SearchThread ocurrences : threads) {
        	blackListOcurrences.addAll(ocurrences.getOcurrenciasMaliciosas());
        	checkedListsCount += ocurrences.getCheckedListsCount();
        }
        
        if (blackListOcurrences.size() >= BLACK_LIST_ALARM_COUNT){
            skds.reportAsNotTrustworthy(ipaddress);
        }
        else{
            skds.reportAsTrustworthy(ipaddress);
        }                
        
        LOG.log(Level.INFO, "Checked Black Lists:{0} of {1}", new Object[]{checkedListsCount, skds.getRegisteredServersCount()});
        
        return blackListOcurrences;
    }
    
    private static final Logger LOG = Logger.getLogger(HostBlackListsValidator.class.getName());
}
