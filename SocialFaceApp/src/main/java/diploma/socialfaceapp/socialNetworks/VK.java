package diploma.socialfaceapp.socialNetworks;

import com.google.gson.Gson;
import com.vk.api.sdk.client.TransportClient;
import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.UserActor;
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import com.vk.api.sdk.httpclient.HttpTransportClient;
import com.vk.api.sdk.objects.UserAuthResponse;
import com.vk.api.sdk.objects.fave.responses.GetPostsResponse;
import com.vk.api.sdk.objects.wall.WallpostFull;
import com.vk.api.sdk.queries.fave.FaveGetPostsQuery;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.http.client.utils.URIBuilder;

public class VK implements SocialNetwork{
    private VkApiClient vk;
    private UserActor user;
    private final String CLIENT_SECRET = "e07YfNd8AiAnOzzTlTUD";
    private final Integer CLIENT_ID = 5937914;
    private final String REDIRECTION_URI = "https://oauth.vk.com/blank.html";
    private final String display = "popup";
    private final String scope = "friends";
    private final String response_type = "code";
    private final String version = "5.63";
    private String request;
   
    @Override
    public void connect() {
        URIBuilder uriBuilder = new URIBuilder();
        uriBuilder.setScheme("https").setHost("oauth.vk.com").setPath("/authorize")
                .setParameter("client_id", CLIENT_ID.toString())
                .setParameter("display", display)
                .setParameter("redirect_uri", REDIRECTION_URI)
                .setParameter("scope", scope)
                .setParameter("response_type", response_type)
                .setParameter("v", version);
        
        request = uriBuilder.toString();
    }

    @Override
    public List GetLikes() {
        List result = new ArrayList();
            try {
                FaveGetPostsQuery posts = vk.fave().getPosts(user);
                GetPostsResponse response = posts.execute();
                
                for (WallpostFull post: response.getItems()){
                    result.add(post.getText());   
                }
            } catch (ApiException | ClientException ex) {
                Logger.getLogger(SocialNetwork.class.getName()).log(Level.SEVERE, null, ex);
            }
            return result;
    }

    @Override
    public String GetRequest() {
        return request;
    }

    @Override
    public void connectUser(String code) {
        try {
            TransportClient transportClient = HttpTransportClient.getInstance();
            vk = new VkApiClient(transportClient, new Gson());
            UserAuthResponse authResponse = vk.oauth()
                    .userAuthorizationCodeFlow(CLIENT_ID, CLIENT_SECRET, REDIRECTION_URI, code)
                    .execute();
            
            user = new UserActor(authResponse.getUserId(), authResponse.getAccessToken());
        } catch (ApiException | ClientException ex) {
            Logger.getLogger(VK.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public Object GetUser() {
        return user;
    }
    
}
