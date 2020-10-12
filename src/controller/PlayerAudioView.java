package controller;


import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaPlayer.Status;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import model.Emission;
import model.Podcast;


public class PlayerAudioView extends GridPane {

	Button play_pause;
	Button next ;
	Button previous;
	GridPane barre;
	Image img_play;
	Image img_pause;
	Label timerAvant;
	Label timerApres;
	ImageView image_bouton;
	public Emission emission;
	public Status mediaPlayerStatus;
	Media son;
	MediaPlayer mediaPlayer;
	Rectangle barre_lecture;
	Rectangle barre_fond;
	Image imagePodcast;
	ImageView ivPodcast = new ImageView();
	GridPane titles;
	ImageView image_next;
	ImageView image_prev;
	Label titre;
	Image img_next;
	Image img_prev;
	Emission PreviousEmission;
	Emission NextEmission;

	public PlayerAudioView() {
		imagePodcast = new Image("brand.png",130,130,true,true);
		img_play = new Image("play.png",50,50,true,true);
		img_pause = new Image("pause.png",50,50,true,true);
		img_next = new Image("end.png",50,50,true,true);
		img_prev = new Image("prev.png",50,50,true,true);
		ivPodcast.setImage(imagePodcast);
		ivPodcast.setTranslateX(10);
		ivPodcast.setTranslateY(-10);
		
		titles = new GridPane();
		image_bouton = new ImageView();
		image_bouton.setImage(img_play);

		previous =new Button();
		previous.setDisable(true);
		previous.setMinSize(50, 50);
		previous.setTranslateX(410);
		previous.setTranslateY(25);
		image_prev = new ImageView();
		image_prev.setImage(img_prev);
		previous.setGraphic(image_prev);
		previous.setOnAction(ke ->{
			listen(PreviousEmission);
		});
		
		
		play_pause = new Button();
		play_pause.setDisable(true);
		play_pause.setGraphic(image_bouton);
		play_pause.setPrefSize(50, 50);
		play_pause.setTranslateY(25);
		play_pause.setOnAction(ke ->{
			if(mediaPlayerStatus == Status.PLAYING) {
				mediaPlayer.pause();
				image_bouton.setImage(img_play);
			}
			if(mediaPlayerStatus == Status.PAUSED) {
				mediaPlayer.play();
				image_bouton.setImage(img_pause);
			}
		});
		
		next = new Button();
		next.setDisable(true);
		next.setPrefSize(50, 50);
		next.setTranslateX(-380);
		next.setTranslateY(25);
		image_next = new ImageView();
		image_next.setImage(img_next);
		next.setGraphic(image_next);
		next.setOnAction(ke ->{
			listen(NextEmission);
		});
	
		timerAvant= new Label("00:00:00");
		timerAvant.setMinSize(100,50);
		
		barre = new GridPane();
		barre.setDisable(true);
		barre.setMaxSize(800, 20);
		barre.setCursor(Cursor.HAND);
		barre.setOnMouseClicked(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent me) {
				mediaPlayer.seek(Duration.seconds(me.getX()/800 *son.getDuration().toSeconds()));
			}
		});
		barre_fond = new Rectangle(800, 20, Color.web("#111111"));
		barre_fond.maxWidth(800);
		barre_lecture = new Rectangle(0, 20, Color.web("#0078ff"));//0078ff
		barre_lecture.maxWidth(800);
		
		barre.add(barre_fond,0,0);
		barre.add(barre_lecture,0,0);
		
		
		timerApres = new Label("00:00:00");
		timerApres.setMinSize(100,50);

		titre = new Label();
		titre.setTranslateY(-30);
		titles.add(previous, 0, 0);
		titles.add(play_pause, 1, 0);
		titles.add(next, 2, 0);
		titles.add(timerAvant, 0, 1);
		titles.add(barre, 1, 1);
		titles.add(timerApres, 2, 1);
		titles.add(titre, 1, 2);
		titles.setHgap(20);
		titles.setVgap(30);
		titles.setPadding(new Insets(0,20,0,20));
		titles.setMinSize(1080, 200);
		titles.setMaxSize(1080, 200);
		titles.setHalignment(titre, HPos.CENTER);
		titles.setHalignment(play_pause, HPos.CENTER);
		this.add(ivPodcast, 0, 0);
		this.add(titles, 1, 0);
		this.setMaxSize(1280, 200);

		
		//this.setBackground(new Background(new BackgroundFill(Color.DARKGRAY, CornerRadii.EMPTY, Insets.EMPTY)));
		
	}
	public void listen(Emission e) {
		this.titre.setText(e.getTitre());
		if(mediaPlayerStatus == Status.PAUSED || mediaPlayerStatus == Status.PLAYING || mediaPlayerStatus == Status.UNKNOWN) {
			mediaPlayer.stop();
		}
		son = new Media(e.getDownload_url_audio());
		mediaPlayer = new MediaPlayer(son);
		mediaPlayer.play();
		image_bouton.setImage(img_pause);
		mediaPlayer.statusProperty().addListener((observable, oldValue, newValue) ->
		mediaPlayerStatus = newValue);
		mediaPlayer.currentTimeProperty().addListener(new ChangeListener(){
			@Override public void changed(ObservableValue o, Object oldVal, Object newVal){
				updatetimer((int)mediaPlayer.getCurrentTime().toSeconds());
				double currentTime = mediaPlayer.getCurrentTime().toSeconds();
				double totalDuration = mediaPlayer.getTotalDuration().toSeconds();
				barre_lecture.setWidth(currentTime/totalDuration*800);
			}
		});
	}
	protected void updatetimer(int seconds) {
		
		int hours = seconds / 3600;
		int minutes = (seconds % 3600) / 60;
		String timer="00:00:00";
		if(seconds<10)
		{
			timer ="00:00:0"+seconds;
		}
		else if(seconds < 60)
		{
			timer ="00:00:"+seconds;
		}
		else if(seconds < 3600)
		{
			seconds= seconds%60;
			if(minutes<10) {
				if(seconds<10)
				{
					timer ="00:0"+minutes+":0"+seconds;
				}
				else if(seconds < 60)
				{
					timer ="00:0"+minutes+":"+seconds;
				}
			}
			else {
				if(seconds<10)
				{
					timer ="00:"+minutes+":0"+seconds;
				}
				else if(seconds < 60)
				{
					timer ="00:"+minutes+":"+seconds;
				}
			}
		}
		else {
			seconds= seconds%60;
			minutes= minutes%60;
			if(hours<10) {
				if(minutes<10) {
					if(seconds<10)
					{
						timer ="0"+hours+":0"+minutes+":0"+seconds;
					}
					else if(seconds < 60)
					{
						timer ="0"+hours+":0"+minutes+":"+seconds;
					}
				}
				else {
					if(seconds<9)
					{
						timer ="0"+hours+":"+minutes+":0"+seconds;
					}
					else if(seconds < 60)
					{
						timer ="0"+hours+":"+minutes+":"+seconds;
					}
				}
			}
			else {
				if(minutes<10) {
					if(seconds<9)
					{
						timer =hours+":0"+minutes+":0"+seconds;
					}
					else if(seconds < 60)
					{
						timer =hours+":0"+minutes+":"+seconds;
					}
				}
				else {
					if(seconds<9)
					{
						timer =hours+":"+minutes+":0"+seconds;
					}
					else if(seconds < 60)
					{
						timer =hours+":"+minutes+":"+seconds;
					}
				}
			}
		}
		timerAvant.setText(timer);
	}
	public void setDuration(String duration) {
		timerApres.setText(duration);
	}
	public void setImagePodcast(String img) {

		if(img.equals("Inconnu")) {
			imagePodcast = new Image("brand.png",150,150,true,true);
			
		}
		else{
			
			try {
				URL url = new URL(img);
				HttpURLConnection huc = (HttpURLConnection) url.openConnection();
				int responseCode = huc.getResponseCode();
				if(responseCode == 403) {
					imagePodcast = new Image("brand.png",150,150,true,true);
				}
				else {
					imagePodcast = new Image(img,150,150,true,true);
				}
			} catch (MalformedURLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
		}
		ivPodcast.setImage(imagePodcast);
	}
	public int getNext(Emission e, Podcast p, int nbr) {
		int cpt = 0;
		for (Emission e1 : p.getItEmissions()) {
			cpt++;
			if(e.equals(e1))
				if(nbr==0 && cpt>1) {
					return cpt-2;
				}
				else if(nbr==1){
					return cpt;
				}
			}
		return 0;
		
	}
	public void setPreviousEmission(Emission e) {
		this.PreviousEmission=e;
		
	}
	public void setNextEmission(Emission e) {
		this.NextEmission=e;
	}
}