package diploma.socialfaceapp.Utils;

import java.awt.Desktop;
import java.io.IOException;
import java.io.StringWriter;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.HttpClientBuilder;

public class InternetUtils {
    public static void openWebpage(URI uri) {
        Desktop desktop = Desktop.isDesktopSupported() ? Desktop.getDesktop() : null;
        if (desktop != null && desktop.isSupported(Desktop.Action.BROWSE)) {
            try {
                desktop.browse(uri);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    
    public static HttpResponse connectResponse(URIBuilder uriBuilder) {

        URI uri = null;

        try {
            uri = uriBuilder.build();
        } catch (URISyntaxException e) {
            e.printStackTrace();
            System.exit(-1);
        }

        HttpClient   client     = HttpClientBuilder.create().build();
        HttpGet      request    = new HttpGet(uri);
        HttpResponse response   = null;

        try {
            response = client.execute(request);
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(-1);
        }

        return response;
    }
    
    public static String GetResponse(URIBuilder uriBuilder){
        
        HttpResponse response = connectResponse(uriBuilder);
        Integer status = response.getStatusLine().getStatusCode();

        if (status == 200) {
            StringWriter content = new StringWriter();

            try {
                IOUtils.copy(response.getEntity().getContent(), content);
                return content.toString();
            } catch (IOException e) {
                e.printStackTrace();
                System.exit(-1);
            }
        }
        return null;
    }
    
}
