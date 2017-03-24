package diploma.socialfaceapp;

import diploma.socialfaceapp.analys.AnalysHandler;
import java.io.IOException;
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
    }
    @FXML
    private void handleButtonLSAAction(ActionEvent event){
        try {
            Path currentRelativePath = Paths.get("");
            String s = currentRelativePath.toAbsolutePath().toString();
            AnalysHandler.LSA.AddingFilesToRepository(s+"/src/main/resources/lsa");
            //List<PassageSimilarityResult> result = AnalysHandler.LSA.execute();
            //String liked_posts = "Similarity: \n";
            //for (PassageSimilarityResult similarity: result){
            //    liked_posts+=similarity.getDocumentA()+", ";
            //    liked_posts+=similarity.getDocumentB()+": ";
            //    liked_posts+=similarity.getSimilarity()+"\n ";
            //}
        post_text.setText(AnalysHandler.LSA.execute());
        } catch (IOException | SQLException ex) {
            Logger.getLogger(FXMLController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        AuthForm = new AuthForm();
        LikesForm = new LikesForm();
        posts = new ArrayList<>();
    }    
}
