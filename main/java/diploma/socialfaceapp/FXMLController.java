package diploma.socialfaceapp;

import java.awt.TextArea;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class FXMLController implements Initializable {
    private AuthForm AuthForm;
    private LikesForm LikesForm;
    static public List<String> posts;
    @FXML
    private Label post_text;
    
    @FXML
    private Label label;
    
    @FXML
    private Button button;
    
    @FXML
    private Button button2;
    
    @FXML
    private void handleButtonAction(ActionEvent event) throws Exception {
        Stage stage = (Stage)button.getScene().getWindow();
        AuthForm.start();
    }
    @FXML
    private void handleButton2Action(ActionEvent event){
        label.setText("А тут меняет!");
        String liked_posts = "Likes: \n";
        for (String post: posts){
            liked_posts+=post;
        }
        post_text.setText(liked_posts);
        System.out.println("Text added");
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        AuthForm = new AuthForm();
        LikesForm = new LikesForm();
        posts = new ArrayList<>();
    }    
}
