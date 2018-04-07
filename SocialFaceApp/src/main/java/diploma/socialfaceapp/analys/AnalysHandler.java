package diploma.socialfaceapp.analys;

import diploma.socialfaceapp.FXMLController;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
public class AnalysHandler {
    private List results;
    private List input;
    
    public AnalysHandler(){
        //not yet implemented
    }
    
    public void analyse(){
        List<String> posts = FXMLController.vk.GetLikes();
        Set<String> tokens = new HashSet<String>();
        for (String post : posts){
            tokens.addAll(parse(post));
        }
        
        
    }
    
    private List parse(String post){
        String[] tokens = post.split("\\W+");
        List<String> result = Arrays.asList(tokens);
        return result;
        
    }
    
}
