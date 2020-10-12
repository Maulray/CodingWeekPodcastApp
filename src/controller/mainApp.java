package controller;

import com.sun.media.sound.UlawCodec;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaPlayer.Status;
import javafx.scene.media.MediaView;
import model.Ecoute;
import model.Emission;
import model.Utilisateur;

public class mainApp {

	//Il faut que mainApp récupère l'utilisateur et la dernière émission cliquée
	//et les passer en paramètre du contructeur Ecoute
	private Ecoute e = new Ecoute();
	@FXML
	private MediaView soundbar;
	@FXML
	private Button go;
	@FXML
	public void initialize() {
		String source = e.getEm().getWatch_url();
		//String source = "http://video.ch9.ms/ch9/ef12/5121143b-effa-490b-83f7-9ec400b1ef12/MIX11KEY02_high_ch9.mp4"
		Media sound = new Media(source);
		MediaPlayer mediaPlayer = new MediaPlayer(sound);
		this.soundbar.setMediaPlayer(mediaPlayer);
		this.go.setText("Play the sound");
		this.go.setOnAction(ke -> {
			Status status = mediaPlayer.getStatus();
			if (status == Status.PAUSED || status == Status.READY || status == Status.STOPPED) {
				mediaPlayer.play();

			} else {
				mediaPlayer.pause();
			}
			e.getUser().addEmEcoutee(e.getEm());
		});
	}
}