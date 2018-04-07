package diploma.socialfaceapp;

import diploma.socialfaceapp.Utils.dbUtils;
import diploma.socialfaceapp.socialNetworks.SocialNetwork;
import diploma.socialfaceapp.socialNetworks.VK;
import diploma.socialfaceapp.learning.Regression;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
//import tml.vectorspace.operations.results.PassageSimilarityResult;

public class FXMLController implements Initializable {
    private AuthForm AuthForm;
    private LikesForm LikesForm;
    static public List<String> posts;
    static public SocialNetwork vk;
    @FXML
    private Label post_text;
    
    @FXML
    private Label label;
    
    @FXML
    private Button button;
    
    @FXML
    private Button button2;
    
    @FXML
    private Button buttonLSA;
    
    @FXML
    private void handleButtonAction(ActionEvent event) throws Exception {
        AuthForm.start();
    }
    @FXML
    private void handleButton2Action(ActionEvent event){
        String liked_posts = "Likes: \n";
        for (String post: posts){
            liked_posts+=post;
        }
        post_text.setText(liked_posts);
        System.out.println("Text added");
        try {
            dbUtils.connect();
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(FXMLController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    @FXML
    private void handleButtonLSAAction(ActionEvent event) throws Exception{
    }
    @FXML
    private void handleButtonREGAction(ActionEvent event){
        Regression reg = new Regression();
        for (Double parameter:reg.RegressionHandler("trait")){
            System.out.print(parameter+", ");
        }
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        AuthForm = new AuthForm();
        LikesForm = new LikesForm();
        posts = new ArrayList<>();
        vk = new VK();
    }    
}
