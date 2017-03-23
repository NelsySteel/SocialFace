package diploma.socialfaceapp.socialNetworks;

import java.util.List;


public interface SocialNetwork {
    public String GetRequest();
    public Object GetUser();
    public void connect();
    public void connectUser(String code);
    public List GetLikes();
    
}
