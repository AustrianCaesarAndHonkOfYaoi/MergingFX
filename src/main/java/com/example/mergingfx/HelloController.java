package com.example.mergingfx;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class HelloController {
    @FXML
    private TextField dest1;
    @FXML
    private TextField dest2;

    @FXML
    private TextField outputf;
    @FXML
    private TextField outputfi;
    @FXML
    Button button;

    public void run(){
        SolutionF s = new SolutionF();
        s.setExplorer(dest1.getText());
        s.setExplorer2(dest2.getText());
        s.setOutputfolder(outputf.getText());

        s.start();

    }
}