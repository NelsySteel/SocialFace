/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package diploma.socialfaceapp;

import java.util.List;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 *
 * @author krist
 */
public class LikesForm {
    public TextField likes;
    private Stage window;
    
    public void add_text(){
        
        
    }
    public void start(List<String> posts){
        window = new Stage();
        GridPane grid = new GridPane();
        likes =  new TextField();
        likes.setText("Likes");
        for (String post : posts){
            likes.appendText(post);
        }
        GridPane.setConstraints(likes, 0, 0);
        grid.getChildren().add(likes);
        window.initModality(Modality.WINDOW_MODAL);
        window.show();
    }
    
}
