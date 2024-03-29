/**
 * Created by IntelliJ Idea.
 * User: Якимов В.Н.
 * E-mail: yakimovvn@bk.ru
 */

package ru.yakimov;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

import java.io.IOException;

public class SceneAssets {

    private static SceneAssets instance = null;


    private static int windowWeight = 618;
    private static int windowHeight = 640;

    private Controller controller;

    private Scene sampleScene;



    public static SceneAssets getInstance(){
        SceneAssets localInstance = instance;
        if(localInstance == null){
            synchronized (SceneAssets.class){
                localInstance = instance;
                if(localInstance == null){
                    localInstance = instance = new SceneAssets();
                }
            }
        }
        return  localInstance;
    }

    private SceneAssets() {
        try {

            FXMLLoader sampleLoader = new FXMLLoader();
            Parent sampleRoot = sampleLoader.load(getClass().getResourceAsStream("/sample.fxml"));
            sampleScene = new Scene(sampleRoot, windowWeight, windowHeight);
            controller = sampleLoader.getController();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public Controller getController() {
        return controller;
    }


    public Scene getSampleScene() {
        return sampleScene;
    }


}
