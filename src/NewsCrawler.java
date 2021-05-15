import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.InputStreamReader;

import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.net.URLEncoder;

import javax.swing.JOptionPane;

/**
 * Crawling the NAVER News.
 * 
 * @author Son Heegwan
 * @since 2021.05.10
 */

public class NewsCrawler {
    final private String baseURL = "https://openapi.naver.com/v1/search";
    private String authID;
    private String authPW;
    private String searchResult = "";

    NewsCrawler() {
        try { 
            authenticate("./auth");
        }
        catch (java.io.FileNotFoundException e) {
            JOptionPane.showMessageDialog(null, 
                                          "File Not Found",
                                          e.getMessage() + e.getStackTrace(),
                                          JOptionPane.ERROR_MESSAGE);
        }
    }

    public void authenticate(String authFileName) throws java.io.FileNotFoundException {
        // authentication of NAVER open api. need api auth file.
        File authFile = new File(authFileName);
        try (final BufferedReader fReader = new BufferedReader(new FileReader(authFile));) {
            authID = fReader.readLine();
            authPW = fReader.readLine();
        } catch (java.io.IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    
    public void authenticate(URI resource) throws java.io.FileNotFoundException {
        // authentication of NAVER open api. need api auth file.
        File authFile = new File(resource);
        try (final BufferedReader fReader = new BufferedReader(new FileReader(authFile));) {
            authID = fReader.readLine();
            authPW = fReader.readLine();
        } catch (java.io.IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    public String searchNews(String keyword, String sort) {
        // search the news based on keyword, and sort it.
        try {
            keyword = URLEncoder.encode(keyword, "utf-8");
            String serviceURL = baseURL + "/news.json?" + "query=" + keyword + "&sort=" + sort;
            URL url = new URL(serviceURL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            readResponse(conn);
            conn.disconnect();
        } catch (java.io.IOException e) {
            JOptionPane.showMessageDialog(null, "searchNews Error", e.getMessage(), JOptionPane.ERROR_MESSAGE);
            System.exit(1);
        }
        return searchResult;
    }

    private void readResponse(HttpURLConnection conn) throws java.io.IOException {
        // read response out of http connection.
        conn.setRequestMethod("GET");
        conn.setRequestProperty("X-Naver-Client-Id", authID);
        conn.setRequestProperty("X-Naver-Client-Secret", authPW);
        int responseCode = conn.getResponseCode();
        BufferedReader responseReader = (responseCode == 200)
                ? new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"))
                : new BufferedReader(new InputStreamReader(conn.getErrorStream(), "UTF-8"));
        StringBuilder sb = new StringBuilder();
        String buf;

        while ((buf = responseReader.readLine()) != null) {
            sb.append(buf + "\n");
        }
        searchResult = sb.toString();
        responseReader.close();
    }
}