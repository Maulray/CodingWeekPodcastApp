package model;

import com.sun.xml.internal.bind.v2.runtime.output.StAXExStreamWriterOutput;

import java.io.*;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.nio.file.Files.delete;


public class ParserRSS {

  public static void readStringFromURL(String requestURL) throws IOException {
    try (Scanner scanner = new Scanner(new URL(requestURL).openStream(),
            StandardCharsets.UTF_8.toString())) {
      scanner.useDelimiter("\\A");
      String res = scanner.hasNext() ? scanner.next() : "";
      BufferedWriter writer = new BufferedWriter(new FileWriter("temp.txt"));
      writer.write(res);

      writer.close();
    } catch (IOException e) {
      e.printStackTrace();
    }

  }



  public static List<List<String>> parse() {
    //Renvoie une liste de lignes au format : Nom du podcast, Durée, Créateurs, Date_Publi, Description, URL pour regarder, URL pour télécharger l'audio, URL pour télécharger la vidéo.
    //Attention certaines emissions n'ont pas de lien de téléchargement vidéo et/ou audio!!
    BufferedReader br = null;
    String line = "";
    List<List<String>> lines = new ArrayList<>();


    try {
      br = new BufferedReader(new FileReader("temp.txt"));
      line = br.readLine();
      while (line != null) {
        if (line.contains("<item>")) {
          String titre = "";
          String duration = "";
          String auteurs = "";
          String date_publi = "";
          String watch_url = "";
          String download_url_audio = "";
          String download_url_video = "";
          String description = "";
          List<String> ligne = new ArrayList<>();

          while (!(line.contains("</item>"))) {

            line = br.readLine();
            int len = line.length() - 1;
            if (line.contains("<title>")) {
              String[] titre_tab = line.split("<title>");
              String titre_temp = titre_tab[1];
              if (titre_temp.contains("CDATA")){
                String[] titre_tab2= titre_temp.split("CDATA");
                titre_temp= titre_tab2[1].substring(1);
                String[] temp=titre_temp.split(">");
                titre=temp[0].substring(0,temp[0].length()-2);
              }
              else{
                String[] temp = titre_temp.split("</title>");
                titre = temp[0];
              }
            } else if (line.contains("<itunes:duration>")) {
              String[] duration_tab = line.split("<itunes:duration>");
              String duration_temp = duration_tab[1];
              String[] temp = duration_temp.split("</itunes:duration>");
              if (temp[0].contains(":")){
                duration=temp[0];
              }
              else{
                int duree_temp=Integer.parseInt(temp[0]);
                int heures= duree_temp/3600;
                String heures_p = heures+"";
                if (heures<10){
                  heures_p="0"+heures;
                }
                int minutes= (duree_temp%3600)/60;
                String minutes_p= minutes+"";
                if (minutes<10){
                  minutes_p="0"+minutes;
                }
                int sec= (duree_temp%3600)%60;
                String sec_p=sec+"";
                if (sec<10){
                  sec_p="0"+sec;
                }
                duration = heures_p + ":" + minutes_p + ":" + sec_p;
              }

            } else if (line.contains("dc:creator")) {
              String[] auteurs_tab = line.split("<dc:creator>");
              String auteurs_temp = auteurs_tab[1];
              String[] temp = auteurs_temp.split("</dc:creator>");
              auteurs = temp[0];
            } else if (line.contains("pubDate")) {
              String[] date_publi_tab = line.split("<pubDate>");
              String date_publi_temp = date_publi_tab[1];
              String[] temp = date_publi_temp.split("</pubDate>");
              date_publi = temp[0];
            } else if (line.contains("<guid isPermaLink=\"false\">")) {
              String[] watch_url_tab = line.split("<guid isPermaLink=\"false\">");
              String watch_url_temp = watch_url_tab[1];
              String[] temp = watch_url_temp.split("</guid>");
              watch_url = temp[0];
            } else if (line.contains("type=\"audio/mp3\"") || line.contains("type=\"audio/mpeg\"")) {
              //choix du format MP3 par défaut pour le téléchargement du podcast en format audio
              String[] download_url_audio_tab = line.split("\"");
              download_url_audio = download_url_audio_tab[1];
            } else if (line.contains("type=\"video/mp4\"")) {
              String[] download_url_video_tab = line.split("\"");
              download_url_video = download_url_video_tab[1];
            } else if (line.contains("type=\"video/x-ms-wmv\"")||line.contains("type=\"video/wmv\"")){
                if (download_url_video=="") {
                  String[] download_url_video_tab = line.split("\"");
                  download_url_video = download_url_video_tab[1];
                }
            } else if (line.contains("<description>")) {
              if(line.contains("CDATA")){
                String[] description_tab = line.split("CDATA");
                String description_temp = description_tab[1];

                String[] temp = description_temp.split("<img");
                description=temp[0];
                description=description.replace("[","");
              }


              description=description.replaceAll("<img(.*?)</img>","");

              description = description.replaceAll("<p>","");
              description = description.replaceAll("</p>","");

              String[] temp2=description.split("</description>");
              description=temp2[0];
              description=description.replaceAll("]]>","");



              description=description.replaceAll("<span(.*?)>","");
              description=description.replaceAll("</span>","");

              //List handling
              description=description.replaceAll("<ul>","");
              description=description.replaceAll("</ul>"," ");

              description=description.replaceAll("<li>","");
              description=description.replaceAll("</li>",",");

              //Traitement des balises parasites restantes

              description=description.replaceAll("<strong>"," ");
              description=description.replaceAll("</strong>"," ");

              description=description.replaceAll("<p>","");
              description=description.replaceAll("</p>","");


              description=description.replaceAll("<div>","");
              description=description.replaceAll("</div>","");


              description=description.replaceAll("<br>","");
              description=description.replaceAll("</br>","");


              description=description.replaceAll("<a href=\"(.*?)\">","");


              description=description.replaceAll("</a>","");

              description=description.replaceAll("&(.*?);","");

            }
          }
          ligne.add(titre);
          ligne.add(duration);
          ligne.add(auteurs);
          ligne.add(date_publi);
          ligne.add(description);
          ligne.add(watch_url);
          ligne.add(download_url_audio);
          ligne.add(download_url_video);
          lines.add(ligne);
        }
        line = br.readLine();
      }
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      if (br != null) {
        try {
          br.close();
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    }
    return lines;
  }

  public static List<String> getPodcastInfos() {
    BufferedReader br = null;
    String line = "";
    List<String> infos = new ArrayList<>();

    try {
      br = new BufferedReader(new FileReader("temp.txt"));
      line = br.readLine();
      String titre = "";
      String description = "";
      String URL = "";
      String langue = "";
      String img_url="";

      while (!(line.contains("<item>"))) {

        if (line.contains("<title>")) {
          String[] titre_tab = line.split("<title>");
          String titre_temp = titre_tab[1];
          if (titre_temp.contains("CDATA")){
            String[] titre_tab2= titre_temp.split("CDATA");
            titre_temp= titre_tab2[1].substring(1);
            String[] temp=titre_temp.split(">");
            titre=temp[0].substring(0,temp[0].length()-2);
          }
          else{
            String[] temp = titre_temp.split("</title>");
            titre = temp[0];
          }

        } else if (line.contains("<description>")) {
          String[] description_tab = line.split("<description>");
          String description_temp = description_tab[1];
          if (description_temp.contains("CDATA")){
            String[] description_tab2= description_temp.split("CDATA");
            description_temp= description_tab2[1].substring(1);
            String[] temp=description_temp.split(">");
            description=temp[0].substring(0,temp[0].length()-2);
          }
          else {
            String[] temp = description_temp.split("</description>");
            description = temp[0];
          }

        } else if (line.contains("<link>")) {
          String[] URL_tab = line.split("<link>");
          String URL_temp = URL_tab[1];
          String[] temp = URL_temp.split("</link>");
          URL = temp[0];
        } else if (line.contains("<language>")) {
          String[] langue_tab = line.split("<language>");
          String langue_temp = langue_tab[1];
          String[] temp = langue_temp.split("</language>");
          langue = temp[0];
        } else if (line.contains("jpg")){
          Pattern p= Pattern.compile("http(.*?)jpg");
          Matcher m =p.matcher(line);
          if (m.find()){
            img_url=m.group();
          }
        } else if (line.contains("jpeg")){
          Pattern p= Pattern.compile("http(.*?)jpeg");
          Matcher m =p.matcher(line);
          if (m.find()){
            img_url=m.group();
          }
        } else if (line.contains("gif")){
          Pattern p= Pattern.compile("http(.*?)gif");
          Matcher m =p.matcher(line);
          if (m.find()){
            img_url=m.group();
          }
        } else if (line.contains("bmp")){
          Pattern p= Pattern.compile("http(.*?)bmp");
          Matcher m =p.matcher(line);
          if (m.find()){
            img_url=m.group();
          }
        } else if (line.contains("png")){
          Pattern p= Pattern.compile("http(.*?)png");
          Matcher m =p.matcher(line);
          if (m.find()){
            img_url=m.group();
          }
        }
        line = br.readLine();
      }
      infos.add(titre);
      infos.add(description);
      infos.add(URL);
      infos.add(langue);
      infos.add(img_url);

    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      if (br != null) {
        try {
          br.close();
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    }
    try{
      delete(Paths.get("temp.txt"));
    }
    catch (IOException e){
      e.printStackTrace();
    }

    return infos;
  }

  public static List<String> getTags(String Titre_Emission) {
    BufferedReader br = null;
    String line = "";
    List<String> Tags = new ArrayList<>();

    try {
      br = new BufferedReader(new FileReader("temp.txt"));
      line = br.readLine();
      while (!(line.contains(Titre_Emission))) {
        line = br.readLine();
      }
      String tag = "";


      while (!(line.contains("</item>"))) {
        line = br.readLine();
        if (line.contains("<category>")) {
          String[] tag_tab = line.split("<category>");
          String tag_temp = tag_tab[1];
          String[] temp = tag_temp.split("</category>");
          tag = temp[0];
          Tags.add(tag);
        }
      }
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      if (br != null) {
        try {
          br.close();
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    }
    return Tags;
  }

}


