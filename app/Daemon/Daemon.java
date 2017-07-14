package Daemon;

import play.Logger;

import java.net.HttpURLConnection;
import java.net.URL;

import static play.mvc.Controller.session;

public class Daemon implements Runnable
{
    private static boolean running = false;

    public static boolean getRunning()
    {
        return running;
    }

    //this function is called in the DaemonController
    @Override
    public void run()
    {
        try {
            URL url = new URL("http://localhost:9000/sendEmails");

            HttpURLConnection request = (HttpURLConnection) url.openConnection();
            request.setRequestMethod("POST");
            request.connect();
            request.getResponseCode();

        } catch (Exception e)
        {
            Logger.error("Error starting daemon: " + e.getMessage());
        }
        running = true;
    }
}
