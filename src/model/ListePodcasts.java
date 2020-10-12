package model;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Observable;

public class ListePodcasts extends Observable {

    public ArrayList<Podcast> listePodcasts;

    public ListePodcasts(String vide){
        this.listePodcasts = new ArrayList<Podcast>();
    }

	public ListePodcasts(){
        this.listePodcasts = new ArrayList<Podcast>();
        BufferedReader br = null;
        String line = "";
        try{
            br = new BufferedReader(new FileReader("./src/model/test.txt"));
            line = br.readLine();
            while (line!=null){
                Podcast pod=new Podcast(line);
                this.listePodcasts.add(pod);
                line=br.readLine();
            }
        } catch (FileNotFoundException e){
            e.printStackTrace();
        } catch (IOException e){
            e.printStackTrace();
        } finally {
            if (br!=null){
                try{
                    br.close();
                } catch (IOException e){
                    e.printStackTrace();
                }
            }
        }
    }

    public ArrayList<Podcast> getListePodcasts() {
        return this.listePodcasts;
    }

    public Podcast getByName(String name){

        for (Podcast i:listePodcasts){
            String titre = i.getTitre();
            if (titre == name ) {
                return i;
            }
        }
        return null;
    }

    public boolean isEmpty(){
        return (this.listePodcasts == null);
    }


}
