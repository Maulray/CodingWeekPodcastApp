package Main;

import controller.OpeningView;
import controller.PlayerAudioView;
import controller.menuView;
import controller.userView;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.ListePodcasts;
import model.ListeUsers;
import model.Utilisateur;

public class Launcher extends Application {

	private Stage primaryStage;
	menuView menu;
	private ListePodcasts lp = new ListePodcasts();
	private ListeUsers lu = new ListeUsers();
	private Utilisateur utilisateur=new Utilisateur("", "poule.png");
	private PlayerAudioView player = new PlayerAudioView();
	@Override
	public void start(Stage primaryStage) throws Exception{

		this.setPrimaryStage(primaryStage);
		ListePodcasts lp= new ListePodcasts();
		ListeUsers lu = new ListeUsers();
		PlayerAudioView player = new PlayerAudioView();
		OpeningView opening = new OpeningView(this, primaryStage);
		userView user = new userView(this, utilisateur, player);
		menu = new menuView(this,lp, lu, utilisateur, player);

		Scene scene = new Scene(opening,1265,785);

    	primaryStage.setTitle("CastHub");
		//primaryStage.setResizable(false);
    	primaryStage.setScene(scene);
    	primaryStage.show();

	

		primaryStage.setOnCloseRequest((e -> Platform.exit()));
	}


	public Stage getPrimaryStage() {
		return primaryStage;
	}


	public void setPrimaryStage(Stage primaryStage) {
		this.primaryStage = primaryStage;
	}


	public static void main(String[] args) {
		launch(args);
	}

	public void switchScene(Parent node, double width, double height, Stage primaryStage) {
		Scene scene = new Scene(node, width, height);
		primaryStage.setScene(scene);
		primaryStage.show();
	}
	public menuView getMenu(){return this.menu;};
	public  void setUser(Utilisateur user) {
		this.utilisateur=user;
	}

	public Utilisateur getUtilisateur() {
		return utilisateur;
	}

	public ListeUsers getLu() {
		return lu;
	}

	public PlayerAudioView getPlayer() {
		return player;
	}
}
