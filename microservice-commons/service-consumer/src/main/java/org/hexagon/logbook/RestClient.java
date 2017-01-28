package org.hexagon.logbook;

/**
 * Created by mac on 12/27/16.
 */
public class RestClient {


    public String post(String url) {

        if(url.equals("a")) throw new ExternalServiceClientException();
        if(url.equals("b")) throw new ExternalServiceServerException();
        if(url.equals("")) throw new ExternalServiceException();

        return url;
    }
}
