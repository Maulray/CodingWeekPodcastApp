package controller;

import Main.Launcher;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

import javafx.scene.layout.StackPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Callback;
import model.*;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class menuView extends GridPane {

	GridPane lists = new GridPane();
	ListView<CelluleEmission> emissionList = new ListView<CelluleEmission>();
	ListView<CellulePodcast> podcastList= new ListView<CellulePodcast>();
	private GridPane description = new GridPane();

	GridPane titles = new GridPane();
	GridPane playerAudio = new GridPane();
	Image imagePodcast;
	ImageView ivlecteur = new ImageView();
	ImageView iv = new ImageView();
	
	
	

	private Podcast p;
	private Emission e;

	Label titlelbl = new Label();
	Label durationlbl = new Label();
	Label authorlbl = new Label();
	Label publilbl = new Label();
	Label descriptlbl = new Label();
	Label title = new Label();
	Label duration = new Label();
	Label author = new Label();
	Label publi = new Label();
	TextArea descript = new TextArea();
	Label descPoc;
	Label descEm;
	Label desc;
	Button download = new Button("Télécharger");
	Button downloadvids = new Button("Télécharger");
	Button listenEmission = new Button("Jouer");
	Button viewvids = new Button("Jouer");
	Button discover = new Button("Découvrir");
	Button profil = new Button("Profil");

	
	Media sound;
	MediaPlayer mediaPlayer;
	ComboBox<String> userNames;
	private Launcher launcher;
	private ListePodcasts lp;
	private ListeUsers lu;
	private Utilisateur currentU;

	public menuView (Launcher launcher, ListePodcasts lp, ListeUsers lu, Utilisateur defaultU, PlayerAudioView lecteur) {
		this.getStylesheets().add("podcastcss.css");
		this.setStyle("-fx-background-color: #202020");
		descPoc = new Label("Podcasts disponibles");
		descPoc.setAlignment(Pos.CENTER);
		descPoc.setMinSize(315, 20);
		descEm = new Label("Emissions");
		descEm.setAlignment(Pos.CENTER);
		descEm.setMinSize(441, 20);
		desc = new Label("Description");
		desc.setAlignment(Pos.CENTER);
		desc.setMinSize(504, 20);
		podcastList.setMinSize(315, 550);
		emissionList.setMinSize(441, 550);
		description.setMinSize(504, 550);
		lists.setMinSize(1260,580);
		lists.add(descPoc, 0, 0);
		lists.add(descEm, 1, 0);
		lists.add(desc, 2, 0);
		lists.add(podcastList, 0, 1);
		lists.add(emissionList, 1, 1);
		lists.add(description, 2, 1);
		lists.setPadding(new Insets(10, 10, 10, 10));
		titles.setMinSize(1080, 100);
		titles.setHgap(20);
		playerAudio.setMinSize(1080, 100);
		playerAudio.setHgap(20);
		lecteur.add(ivlecteur, 0, 0);
		lecteur.setRowSpan(ivlecteur,2);
		lecteur.add(title, 1, 0);
		lecteur.add(playerAudio, 1, 1);
		lecteur.setMinSize(1280, 200);
		this.add(discover,0,0);
		this.add(profil,0,0);
		profil.setTranslateX(80);
		this.add(lists, 0, 2);
		this.add(lecteur, 0, 3);
		this.setPrefSize(1280, 800);

		GridPane gp = new GridPane();
		Label audio = new Label("Audio");
		Label video = new Label("Video");
		gp.add(audio,0,0);
		gp.add(video,1,0);
		gp.add(download,0,1);
		gp.add(listenEmission,0,1);
		gp.add(downloadvids,1,1);
		gp.add(viewvids,1,1);
		lists.add(gp,2,1);
		gp.setTranslateX(200);
		gp.setTranslateY(400);
		download.setTranslateX(-150);
		listenEmission.setTranslateX(-50);
		audio.setTranslateX(-100);
		viewvids.setTranslateX(100);
		video.setTranslateX(50);
		gp.setHgap(20);
		gp.setVgap(20);
		
		description.setTranslateX(10);
		description.setVisible(false);
		emissionList.setVisible(false);
		gp.setVisible(false);

		this.currentU = defaultU;
		discover.setPrefSize(80,20);
		discover.setOnAction(e->{
			DiscoverView disview = new DiscoverView(launcher, lp,this,this.currentU, lecteur);
			launcher.switchScene(disview,1280,800,launcher.getPrimaryStage());
		});



		this.launcher = launcher;
		this.lp = lp;
		this.lu = lu;
		profil.setOnAction(e -> {
			userView userview = new userView(launcher,this.currentU, lecteur);
			launcher.switchScene(userview,1280, 800, launcher.getPrimaryStage());

		});
//		userNames.setPromptText("Choisissez votre profil:");

		for (Podcast p : lp.getListePodcasts()) {
			podcastList.getItems().add(new CellulePodcast(currentU,p,podcastList));
		}

		podcastList.setCellFactory(new Callback<ListView<CellulePodcast>, ListCell<CellulePodcast>>() {

			@Override public ListCell<CellulePodcast> call(ListView<CellulePodcast> list) {
				return new ListeCellulePodcast();
			}
		});
		podcastList.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<CellulePodcast>() {
			@Override
			public void changed(ObservableValue<? extends CellulePodcast> observable, CellulePodcast oldValue, CellulePodcast newValue) {
				p=lp.getByName(newValue.getP().getTitre());
				emissionList.setVisible(true);
				emissionList.getItems().clear();
				description.setVisible(false);
				gp.setVisible(false);
				for (Emission e : p.getItEmissions()) {
					CelluleEmission cel = new CelluleEmission(currentU, e);
					emissionList.getItems().add(cel);
					if (currentU.emDejaEcout(e)) {
						String style = "-fx-font-weight: bold; -fx-text-fill: skyblue; -fx-underline: true;";
						cel.setStyle(style);
					}
				}

			}
		});


		emissionList.setCellFactory(new Callback<ListView<CelluleEmission>, ListCell<CelluleEmission>>() {

			@Override public ListCell<CelluleEmission> call(ListView<CelluleEmission> list) {
				return new ListeCelluleEmission();
			}
		});
		emissionList.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<CelluleEmission>() {
			@Override
			public void changed(ObservableValue<? extends CelluleEmission> observable, CelluleEmission oldValue, CelluleEmission newValue) {
				if(newValue!=null) {
					e = p.getByName(newValue.getE().getTitre());
					description.setVisible(true);
					listenEmission.setDisable(false);
					gp.setVisible(true);
				}
				if (e != null) {
					setDescription();
				}
			}
		});

		title.setMaxWidth(330);
		title.setWrapText(true);
		descript.setMaxWidth(330);
		descript.setWrapText(true);
		titlelbl.setText("Titre : ");
		titlelbl.setMinSize(45,30);
		description.add(titlelbl, 0, 0);	
		description.add(title, 1, 0);
		durationlbl.setText("Duree : ");
		durationlbl.setMinSize(45,30);
		description.add(durationlbl, 0,1);
		description.add(duration, 1, 1);
		authorlbl.setText("Auteur : ");
		authorlbl.setMinSize(45,30);
		description.add(authorlbl, 0,2);
		description.add(author, 1, 2);
		publilbl.setText("Date de publication : ");
		publilbl.setMinSize(150,30);
		description.add(publilbl, 0,3);
		description.add(publi, 1, 3);
		descriptlbl.setText("Description : ");
		descriptlbl.setMinSize(60,30);
		descriptlbl.setTranslateY(-80);
		description.add(descriptlbl, 0,4);
		description.add(descript, 1, 4);
		description.add(iv, 0, 5);
		iv.setTranslateY(-130);
		
		download.setOnAction(ke -> {
			URLConnection conn;
			try {
				conn = new URL(e.getDownload_url_audio()).openConnection();
				InputStream is = conn.getInputStream();

				OutputStream outstream = new FileOutputStream(new File(e.getTitre()+".mp3"));
				byte[] buffer = new byte[4096];
				int len;
				while ((len = is.read(buffer)) > 0) {
					outstream.write(buffer, 0, len);
				}
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("Telechargement musique");
				alert.setHeaderText("Fichier telecharge !");
				alert.showAndWait();
				outstream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		});
		downloadvids.setOnAction(ke -> {
			URLConnection conn;
			try {
				conn = new URL(e.getDownload_url_video()).openConnection();
				InputStream is = conn.getInputStream();

				OutputStream outstream = new FileOutputStream(new File(e.getTitre()+".mp4"));
				byte[] buffer = new byte[4096];
				int len;
				while ((len = is.read(buffer)) > 0) {
					outstream.write(buffer, 0, len);
				}
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("Telechargement video");
				alert.setHeaderText("Pas fini encore revenez plus tard !");
				alert.showAndWait();
				outstream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		});
		this.listenEmission.setOnAction(ke->{
			lecteur.barre.setDisable(false);
			lecteur.play_pause.setDisable(false);
			lecteur.next.setDisable(false);
			lecteur.previous.setDisable(false);
			lecteur.setPreviousEmission(p.getItEmissions().get(lecteur.getNext(e, p,0)));
			lecteur.setNextEmission(p.getItEmissions().get(lecteur.getNext(e, p,1)));
			lecteur.setDuration(e.getDuration());
			lecteur.listen(e);
			currentU.addEmEcoutee(e);
			listenEmission.setDisable(true);
			lecteur.setImagePodcast(p.getImg());
			for (CelluleEmission cel: emissionList.getItems()){
				if (cel.getE().getTitre().equals(e.getTitre())){
					String style = "-fx-font-weight: bold; -fx-text-fill: skyblue; -fx-underline: true;";
					cel.setStyle(style);
				}
			}

		});

		viewvids.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				PlayerVideoView playerVideoView = new PlayerVideoView(e, currentU);
				StackPane secondaryLayout= new StackPane();
				secondaryLayout.getChildren().add(playerVideoView);
				Scene secondScene = new Scene(secondaryLayout,1280,750);

				Stage newWindow = new Stage();
				newWindow.setTitle("Lecteur vidÃ©o");
				newWindow.setScene(secondScene);

				newWindow.show();


				newWindow.setOnCloseRequest((e -> playerVideoView.mediaPlayer.stop()));
			}
		});



	}

	public Emission getE() {
		return e;
	}

	public void setDescription() {
		title.setText(e.getTitre());
		duration.setText(e.getDuration());
		author.setText(e.getAuteur());
		publi.setText(e.getDate_publi());
		descript.setText(e.getDescription());
		if(p.getImg().equals("Inconnu")) {
			imagePodcast = new Image("brand.png",130,130,true,true);
			
		}
		else{
			
			try {
				URL url = new URL(p.getImg());
				HttpURLConnection huc = (HttpURLConnection) url.openConnection();
				int responseCode = huc.getResponseCode();
				if(responseCode == 403) {
					imagePodcast = new Image("brand.png",130,130,true,true);
				}
				else {
					imagePodcast = new Image(p.getImg(),130,130,true,true);
				}
			} catch (MalformedURLException e1) {
				e1.printStackTrace();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			
		}
		iv.setImage(imagePodcast);

		if (e.getDownload_url_audio().equals("Inconnu")) {
			download.setDisable(true);
		}
		else {
			download.setDisable(false);
		}
		if (e.getDownload_url_video().equals("Inconnu")) {
			downloadvids.setDisable(true);
		}
		else {
			downloadvids.setDisable(false);
		}
		if (e.getWatch_url().substring((e.getWatch_url()).length() -3).equals("mp3")){
			viewvids.setDisable(true);
		}else{
			viewvids.setDisable(false);
		}

	}
	public Launcher getLauncher() {
		return launcher;
	}

	public ListeUsers getLu(){
		return lu;
	}

	public ListePodcasts getLp() {
		return lp;
	}

	public Utilisateur getCU(){
		return currentU;
	}
	public void setUser(Utilisateur user){this.currentU=user;}
}