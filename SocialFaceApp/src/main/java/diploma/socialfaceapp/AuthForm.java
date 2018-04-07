package diploma.socialfaceapp;

import diploma.socialfaceapp.socialNetworks.SocialNetwork;
import diploma.socialfaceapp.socialNetworks.VK;
import java.util.List;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Worker.State;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class AuthForm {
    public List results;
    public void start() throws Exception {
        FXMLController.vk.connect();
        
        final Stage window = new Stage();
        final Browser browser = new Browser(FXMLController.vk.GetRequest());
        browser.webEngine.getLoadWorker().stateProperty().addListener(
            new ChangeListener<State>() {
              @Override public void changed(ObservableValue ov, State oldState, State newState) {
                  String link = browser.webEngine.getLocation();
                  System.out.println(link.substring(0, 30));
                  if (link.substring(0, 30).equals("https://oauth.vk.com/authorize")) {
                    window.setTitle("Авторизация");
                    System.out.println("called");
                } else if (link.substring(0, 31).equals("https://oauth.vk.com/blank.html")){
                      String delims = "=";
                      String[] tokens = link.split(delims);
                      String code = tokens[1];
                      System.out.println(code);
                      FXMLController.vk.connectUser(code);
                      window.hide();
                  }
                  
                  
                }
            });
        Scene scene =  new Scene(browser, 800, 500);
        window.setScene(scene);
        window.initModality(Modality.WINDOW_MODAL);
        window.showAndWait();
    }
    
    class Browser extends Region {
 
        final WebView browser = new WebView();
        final WebEngine webEngine = browser.getEngine();

        public Browser(String request) {
            //apply the styles
            getStyleClass().add("browser");
            // load the web page
            webEngine.load(request);
            //add the web view to the scene
            getChildren().add(browser);

        }
        private Node createSpacer() {
            Region spacer = new Region();
            HBox.setHgrow(spacer, Priority.ALWAYS);
            return spacer;
        }

        @Override protected void layoutChildren() {
            double w = getWidth();
            double h = getHeight();
            layoutInArea(browser,0,0,w,h,0, HPos.CENTER, VPos.CENTER);
        }

        @Override protected double computePrefWidth(double height) {
            return 750;
        }

        @Override protected double computePrefHeight(double width) {
            return 500;
        }
}
}   

