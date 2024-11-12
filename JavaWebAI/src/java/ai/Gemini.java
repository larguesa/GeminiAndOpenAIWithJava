package ai;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author rlarg
 */
public class Gemini {
    private static final String API_KEY = "[SUA CHAVE]";

    public static String getCompletion(String prompt) throws Exception {
        JSONObject data = new JSONObject();
        JSONArray partsArray = new JSONArray()
            .put(new JSONObject().put("text", prompt));
        data.put("contents", new JSONArray()
            .put(new JSONObject().put("parts", partsArray)));
        System.out.println("Parâmetros do envio: " + data.toString());
        HttpClient client = SelfCertificatedServer.getHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
            .uri(new URI("https://generativelanguage.googleapis.com/v1beta/models/gemini-1.5-flash-latest:generateContent?key=" + API_KEY))
            .header("Content-Type", "application/json")
            .POST(HttpRequest.BodyPublishers.ofString(data.toString()))
            .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        if (response.statusCode() != 200) {
            throw new RuntimeException("Erro na requisição: " + response.statusCode() + " - " + response.body());
        } else {
            System.out.println("Resposta da API: " + response.body());
            return new JSONObject(response.body())
                    .getJSONArray("candidates")
                    .getJSONObject(0)
                    .getJSONObject("content")
                    .getJSONArray("parts")
                    .getJSONObject(0)
                    .getString("text");
        }
    }

    public static void main(String[] args) {
        try {
            System.out.println(Gemini.getCompletion("Em uma frase, o palmeiras tem mundial?"));
        } catch (Exception ex) {
            System.out.println("ERRO: " + ex.getLocalizedMessage());
        }
    }
}