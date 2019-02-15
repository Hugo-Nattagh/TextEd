package sample;

import javafx.application.Platform;
import javafx.event.ActionEvent;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import javafx.stage.FileChooser;

import java.io.*;

public class MyController {

    @FXML
    public TextArea txtAr;

    public void openFile(ActionEvent event) throws IOException {
        FileChooser chooser = new FileChooser();
        FileChooser.ExtensionFilter filter = new FileChooser.ExtensionFilter("Text File", "*.txt");
        chooser.getExtensionFilters().add(filter);
        File selectedFile = chooser.showOpenDialog(null);
        if (selectedFile != null) {
            String content = new String(Files.readAllBytes(Paths.get(selectedFile.getAbsolutePath())), StandardCharsets.UTF_8);
            txtAr.setText(content);
            txtAr.end();
        }
    }

    public void saveFile(ActionEvent event) {
        String content = txtAr.getText().replaceAll("\n", System.getProperty("line.separator"));
        FileChooser chooser = new FileChooser();
        FileChooser.ExtensionFilter filter = new FileChooser.ExtensionFilter("Text File", "*.txt");
        chooser.getExtensionFilters().add(filter);
        File selectedFile = chooser.showSaveDialog(null);
        if (selectedFile != null) {
            try (BufferedWriter bw = new BufferedWriter(new FileWriter(selectedFile, true))) {
                bw.write(content);
                bw.flush();
                System.out.println("Your file has been written");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void exit(ActionEvent event) {
        Platform.exit();
    }

    public void cut(ActionEvent event) {
        txtAr.cut();
    }

    public void copy(ActionEvent event) {
        txtAr.copy();
    }

    public void paste(ActionEvent event) {
        txtAr.paste();
    }

    public void undo(ActionEvent event) {
        txtAr.undo();
    }

    public void redo(ActionEvent event) {
        txtAr.redo();
    }

    public void delete(ActionEvent event) {
        try {
            txtAr.deleteText(txtAr.getAnchor(), txtAr.getCaretPosition());
        }
        catch(Exception e) {
            txtAr.deleteText(txtAr.getCaretPosition(), txtAr.getAnchor());
        }
    }

    public void newText(ActionEvent event) {
        txtAr.clear();
    }

    public void printLastWord(KeyEvent evt) {
        if (evt.getCharacter() == "a") {
            txtAr.selectPreviousWord();
            String selection = txtAr.getSelectedText();
            txtAr.forward();
            System.out.println(selection);
        }
    }

    public void about(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("about.fxml"));
        Parent root1 = fxmlLoader.load();
        Stage secondStage = new Stage();
        secondStage.setTitle("About");
        secondStage.setScene(new Scene(root1));
        secondStage.show();
    }
}
