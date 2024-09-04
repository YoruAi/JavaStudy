package AdvancedFeatures.Chapter4;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.*;

public class HttpClientTest {
    public static void main(String[] args) throws URISyntaxException, IOException, InterruptedException {
        HttpClient client = HttpClient.newBuilder().followRedirects(HttpClient.Redirect.ALWAYS).build();
        HttpRequest requestGet = HttpRequest.newBuilder()
                .uri(new URI("https://www.baidu.com/"))
                .GET()
                .build();
        HttpRequest requestPost = HttpRequest.newBuilder()
                .uri(new URI("https://www.baidu.com/s"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString("{wd=1}"))
                .build();
        HttpResponse<String> response = client.send(requestGet, HttpResponse.BodyHandlers.ofString());
        String bodyString = response.body();
        int status = response.statusCode();
        var headerMao = response.headers().map();

        System.out.println(bodyString);
    }
}
