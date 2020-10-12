package model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

public class Podcast extends Observable {

    private String titre;
    private String description;
    private String URL;
    private String langue;
    private String img;
    private ListeEmission emissions;
    private List<Emission> itEmissions;

    public Podcast(String requestURL) throws IOException {

        this.emissions = new ListeEmission();
        this.itEmissions = new ArrayList<Emission>();
        try {
			ParserRSS.readStringFromURL(requestURL);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
        List<List<String>> liste = ParserRSS.parse();
        List<String> listeInfo = ParserRSS.getPodcastInfos();

        this.titre = listeInfo.get(0);
        this.description = listeInfo.get(1);
        this.URL=listeInfo.get(2);
        this.langue = listeInfo.get(3);
        if(listeInfo.get(4).equals("")) {
        	this.img = "Inconnu";
        }
        else {
            this.img=listeInfo.get(4);
        }


        for (List<String> i:liste){
            Emission e = new Emission(i);
            emissions.addEmission(e);
            itEmissions.add(e);
        }
    }

    public List<Emission> getItEmissions() {
        return itEmissions;
    }

    public String getTitre() {
        return titre;
    }

    public String getDescription() {
        return description;
    }

    public ListeEmission getEmissions() {
        return emissions;
    }

    public String getLangue() {
        return langue;
    }

    public String getImg(){return img;}

	public Emission getByName(String name) {
		for (Emission i:itEmissions){
            String titre = i.getTitre();
            if (titre == name ) {
                return i;
            }
        }
        return null;
    }
}
