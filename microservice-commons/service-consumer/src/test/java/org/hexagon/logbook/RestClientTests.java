package org.hexagon.logbook;

import org.junit.Assert;
import org.junit.Test;

/**
 * Created by mac on 12/27/16.
 */
public class RestClientTests {

    @Test
    public void rest() {
        try {
            String res = new RestClient().post("a");
            Assert.assertEquals(res, "a");
        } catch (ExternalServiceException e) {
            handle(e);
        }

    }

    private void handle(ExternalServiceClientException e) {

        System.out.println("Client exception");
        Assert.assertTrue(true);
    }

    private void handle(ExternalServiceServerException e) {
        System.out.println("Server exception");
        Assert.assertTrue(true);
    }

    private void handle(ExternalServiceException e) {
        System.out.println("Gen exception");
        Assert.assertTrue(true);
    }

}
