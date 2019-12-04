package ru.yakimov;


import com.google.common.primitives.Longs;
import javafx.application.Platform;
import javafx.fxml.FXML;

import javafx.scene.control.*;

import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import ru.yakimov.handlers.InProtocolHandler;
import ru.yakimov.utils.MyPackage;

import java.io.*;
import java.util.Optional;


public class Controller {

    @FXML
    private void sendFile() {
        File file  = new File("/Users/vladimir/java/projects/YaClaudTest/client/src/main/resources/big_file.MP4");

        String startCommand = "big_file.MP4" + InProtocolHandler.DATA_DELIMITER + "root/";
        System.out.println(startCommand);

        Connector.getInstance().setAndSendFile(Commands.START_FILE, startCommand.getBytes());

        MyPackage myPackage = Connector.getInstance().getPackage();

        try(BufferedInputStream in = new BufferedInputStream(new FileInputStream(file))){
            int i = -1;
            int packNumber = 0;

            while ((i = in.read(myPackage.getDataArrForWrite())) != -1){
                packNumber++;
                System.err.println("Send package "+ i + " num# "+packNumber);

                Connector.getInstance()
                        .addToQueue(
                                myPackage
                                    .trimDataArr(i)
                                    .setType(ProtocolDataType.FILE)
                                    .setCommandWithLength(Commands.FILE)
                );
                myPackage = Connector.getInstance().getPackage();

                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        myPackage.disable();


        Connector.getInstance().setAndSendFile(Commands.END_FILE, Longs.toByteArray(file.length()));
    }
    public void showAlertError(String headerText, String contentText){

        Platform.runLater(()->{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Client");
            alert.setHeaderText( headerText);
            alert.setContentText( contentText);
            alert.showAndWait();
        });
    }

}
