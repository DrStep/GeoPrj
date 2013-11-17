package server.cmd;



import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

enum HttpStatus {
    Ok;
}

public abstract class Command {
    private int status;

    public void onPrepareConnection() {

    }

    public void onExecute() {
        try {
            String response = sendRequest();
            proccessResponse(HttpStatus.Ok, response);
        } catch (IOException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    private String sendRequest() throws IOException {
        String responseData = "";
        String line = "";

        URL url = new URL(createUrl());
        URLConnection urlConnection = url.openConnection();
        urlConnection.connect();
        BufferedReader out = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));

        while ((line =  out.readLine()) != null) {
            responseData += line;
        }
        return responseData;
    }

    public int getStatus() {
        return status;
    }

    public void proccessResponse(HttpStatus status, String data) {
        if (status == HttpStatus.Ok) {
            onResponseOk(data);
        }
    }

    public abstract String createUrl();
    public abstract void onResponseOk(String data);
}
