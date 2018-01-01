package com.pej.filtres;

import java.util.Date;

import com.pej.domains.Commune;
import com.pej.domains.Statutcandidat;

public class FCandidat {
	 /*Propriété de recherche*/
    private Commune searchcommune;
    private Statutcandidat searchstatut;
    private String searchnom;
    private String searchordre;
    private Date searchdate;
    private String searchagent;
    
    
    public Commune getSearchcommune() {
		return searchcommune;
	}

	public void setSearchcommune(Commune searchcommune) {
		this.searchcommune = searchcommune;
	}

	public Statutcandidat getSearchstatut() {
		return searchstatut;
	}

	public void setSearchstatut(Statutcandidat
			searchstatut) {
		this.searchstatut = searchstatut;
	}

	public String getSearchnom() {
		return searchnom;
	}

	public void setSearchnom(String searchnom) {
		this.searchnom = searchnom;
	}

	public String getSearchordre() {
		return searchordre;
	}

	public void setSearchordre(String searchordre) {
		this.searchordre = searchordre;
	}

	public Date getSearchdate() {
		return searchdate;
	}

	public void setSearchdate(Date searchdate) {
		this.searchdate = searchdate;
	}

	public String getSearchagent() {
		return searchagent;
	}

	public void setSearchagent(String searchagent) {
		this.searchagent = searchagent;
	}

	/*Fin propriété de recherche*/

}
