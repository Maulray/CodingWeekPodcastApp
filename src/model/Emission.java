package model;

import java.util.List;

public class Emission {

    @Override
	public String toString() {
		return "Emission [titre=" + titre + ", duration=" + duration + ", auteur=" + auteur + ", date_publi="
				+ date_publi + ", description=" + description + ", watch_url=" + watch_url + ", download_url_audio="
				+ download_url_audio + ", download_url_video=" + download_url_video + "]";
	}

	private String titre;
    private String duration;
    private String auteur;
    private String date_publi;
    private String description;
    private String watch_url;
    private String download_url_audio;
    private String download_url_video;

    public Emission(List<String> infos) {

    	if(infos.get(0).equals("")) {
    		this.titre ="Inconnu";	
    	}
    	else {
            this.titre = infos.get(0);
    	}
    	if(infos.get(1).equals("")) {
    		this.duration ="Inconnu";	
    	}
    	else {
    		this.duration = infos.get(1);
    	}
    	if(infos.get(2).equals("")) {
    		this.auteur ="Inconnu";	
    	}
    	else {
    		this.auteur = infos.get(2);
    	}
    	if(infos.get(3).equals("")) {
    		this.date_publi ="Inconnu";	
    	}
    	else {
    		this.date_publi = infos.get(3);
    	}
    	if(infos.get(4).equals("")) {
    		this.description ="Inconnu";	
    	}
    	else {
    		this.description = infos.get(4);
    	}
    	if(infos.get(5).equals("")) {
    		this.watch_url ="Inconnu";	
    	}
    	else {
    		this.watch_url = infos.get(5);
    	}
    	if(infos.get(6).equals("")) {
    		this.download_url_audio ="Inconnu";	
    	}
    	else {
            this.download_url_audio = infos.get(6);
    	}
    	if(infos.get(7).equals("")) {
    		this.download_url_video ="Inconnu";	
    	}
    	else {
    		this.download_url_video = infos.get(7);
    	}
        
        
        
        
        
        
    }

    public String getDate_publi() {
        return date_publi;
    }

    public String getWatch_url() {
        return watch_url;
    }

    public String getDescription() {
        return description;
    }

    public String getDownload_url_audio() {
        return download_url_audio;
    }

    public String getDownload_url_video() {
        return download_url_video;
    }

    public String getTitre() {
        return titre;
    }

    public String getDuration() {
        return duration;
    }

    public String getAuteur() {
        return auteur;
    }


}
