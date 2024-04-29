package uub.debug;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;




public class HttpsConnection {

    public static void main(String[] args) {
        try {
        	
        	System.setProperty("javax.net.ssl.trustStore", "/home/sarabhesh-pt7298/Documents/keystore.jks");
        	System.setProperty("javax.net.ssl.trustStorePassword", "Sarabhesh");


            URL url = new URL("https://localhost:8443/UUB/api/customers/1?include=lastModifiedBy");
            HttpsURLConnection.setDefaultHostnameVerifier((a, b) -> true);

            HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();

            connection.setRequestMethod("GET");
            

            connection.setRequestProperty("Authentication", "307325b4655d41b585029cc5445cd6ac"); 
            int responseCode = connection.getResponseCode();
            System.out.println("Response Code : " + responseCode);

            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String inputLine;
            StringBuilder response = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            System.out.println("response : "+response.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}

