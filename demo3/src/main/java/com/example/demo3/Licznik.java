package com.example.demo3;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.util.Calendar;




public class Licznik extends Application {
    private static final int SEKUNDY_W_DNIU = 24 * 60 * 60;
    private static final int INTERWAL_AKTUALIZACJI = 1;

    private Label etykietaOdliczania;
    private Calendar nowyRok;

    @Override
    public void start(Stage primaryStage) {
        nowyRok = Calendar.getInstance();
        nowyRok.set(Calendar.YEAR, 2024);
        nowyRok.set(Calendar.MONTH, 0);
        nowyRok.set(Calendar.DAY_OF_MONTH, 1);
        nowyRok.set(Calendar.HOUR_OF_DAY, 0);
        nowyRok.set(Calendar.MINUTE, 0);
        nowyRok.set(Calendar.SECOND, 0);

        etykietaOdliczania = new Label();
        etykietaOdliczania.setStyle("-fx-font-size: 40;");
        aktualizujEtykieteOdliczania();

        Timeline timeline = new Timeline(
                new KeyFrame(Duration.seconds(INTERWAL_AKTUALIZACJI), new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        aktualizujEtykieteOdliczania();
                    }
                })
        );
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();

        StackPane root = new StackPane();
        root.getChildren().add(etykietaOdliczania);

        Scene scene = new Scene(root, 400, 300);

        primaryStage.setTitle("Odliczanie do Nowego Roku");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void aktualizujEtykieteOdliczania() {
        Calendar teraz = Calendar.getInstance();
        long koniec = nowyRok.getTimeInMillis() - teraz.getTimeInMillis();
        if (koniec <= 0) {
            etykietaOdliczania.setText("Szczęśliwego Nowego Roku 2024! - By \t\uD83D\uDC1F ");
            return;
        }
        long sekundyDoNowegoRoku = koniec / 1000;
        long dni = sekundyDoNowegoRoku / SEKUNDY_W_DNIU;
        long sekundyWDniu = sekundyDoNowegoRoku % SEKUNDY_W_DNIU;
        long godziny = sekundyWDniu / 3600;
        long minuty = (sekundyWDniu % 3600) / 60;
        long sekundy = sekundyWDniu % 60;


        String tekstOdliczania = String.format("%d dni, %02d:%02d:%02d", dni, godziny, minuty, sekundy);
        etykietaOdliczania.setText(tekstOdliczania);
    }
}
