/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.mypersonality;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.vk.api.sdk.client.TransportClient;
import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.UserActor;
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import com.vk.api.sdk.httpclient.HttpTransportClient;
import com.vk.api.sdk.objects.UserAuthResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import static org.apache.commons.lang3.CharSetUtils.count;

/**
 *
 * @author krist
 */
public class SocialNetwork {
    public static interface SocialNetworkAdapter{
        
        public void connect(String code);
        public List GetUserLikes();
    }
    
    public static class SNvkAdapter implements SocialNetworkAdapter{
        UserActor user;
        VkApiClient vk;
        
        public SNvkAdapter(String code){
            try {
                TransportClient transportClient = HttpTransportClient.getInstance();
                vk = new VkApiClient(transportClient, new Gson());
                UserAuthResponse authResponse = vk.oauth()
                        .userAuthorizationCodeFlow(5929869, "KWJxg95UxO1g6MtjpnR9", "http://localhost/test.php", code)
                        .execute();
                
                user = new UserActor(authResponse.getUserId(), authResponse.getAccessToken());
            } catch (ApiException | ClientException ex) {
                Logger.getLogger(SocialNetwork.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        @Override
        public void connect(String code){
            try {
                TransportClient transportClient = HttpTransportClient.getInstance();
                VkApiClient vk = new VkApiClient(transportClient, new Gson());
                UserAuthResponse authResponse = vk.oauth()
                        .userAuthorizationCodeFlow(5929869, "KWJxg95UxO1g6MtjpnR9", "http://localhost/test.php", code)
                        .execute();
                
                user = new UserActor(authResponse.getUserId(), authResponse.getAccessToken());
            } catch (ApiException | ClientException ex) {
                Logger.getLogger(SocialNetwork.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        

        @Override
        public List GetUserLikes() {
            List result = new ArrayList();
            try {
                JsonElement response = vk.execute().code(user, "return API.fave.getPosts({'count': 5})").execute();
                JsonArray likes = response.getAsJsonArray();
                for (JsonElement like: likes){
                    result.add(like.getAsJsonObject().toString());   
                }
            } catch (ApiException ex) {
                Logger.getLogger(SocialNetwork.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ClientException ex) {
                Logger.getLogger(SocialNetwork.class.getName()).log(Level.SEVERE, null, ex);
            }
            return result;
        }

        private void ExecuteRequest(String request) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }
        
    }
    
}
