package common;

import com.jayway.restassured.RestAssured;
import com.jayway.restassured.config.EncoderConfig;
import com.jayway.restassured.response.Response;
import org.apache.commons.codec.binary.Hex;
import org.json.JSONObject;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;
import static com.jayway.restassured.RestAssured.given;

public final class RestApi {

    /**
     * Приватный ключ проекта для работы по REST
     */
    private static String privateKey;

    /**
     * Ключ клиента на проекте для работы по REST
     */
    private static String clientId;

    /**
     * Установка базовых параметров для работы по REST
     *
     * @param baseURI    default URL
     * @param privateKey Приватный ключ проекта
     * @param client_Id  Ключ клиента на проекте
     */
    public static void setupREST_URL(String baseURI, String privateKey, String client_Id) {
        RestAssured.baseURI = baseURI;
        RestAssured.basePath = "/api";
        RestAssured.config().encoderConfig(EncoderConfig.encoderConfig().defaultContentCharset(StandardCharsets.UTF_8));
        RestApi.privateKey = privateKey;
        RestApi.clientId = client_Id;
    }

    private static Map<String, Object> setHeaders(Map<String, Object> headers, Object body) {
        Map<String, Object> listHeaders = new HashMap<>();
        listHeaders.put("Rest-Public-Client", clientId);
        if (headers != null) {
            listHeaders.putAll(headers);
        }
        listHeaders.put("Rest-Security-Token", body == null ? generateToken("") : generateToken(body.toString()));
        return listHeaders;
    }

    private static Map<String, Object> setHeaders(Object body) {
        return setHeaders(null, body);
    }

    private static Map<String, Object> setHeaders() {
        return setHeaders(null, null);
    }

    /**
     * GET запрос
     *
     * @param path путь к эндпоинту
     * @return ответ запроса
     */
    public static Response doGet(String path) {
        return doGet(path, null);
    }

    /**
     * GET запрос с параметрами в заголовках
     * @param path путь к эндпоинту
     * @param headers заголовки запроса
     * @return ответ запроса
     */
    public static Response doGet(String path, Map<String, Object> headers) {
        return given().headers(setHeaders(headers, null)).get(path);
    }

    /**
     * POST запрос
     *
     * @param path путь к эндпоинту
     * @param body тело запроса
     * @return ответ запроса
     */
    public static Response doPost(String path, Object body) {
        return given().headers(setHeaders(body)).body(body.toString()).post(path);
    }

    /**
     * PUT запрос
     *
     * @param path путь к эндпоинту
     * @param body тело запроса
     * @return ответ запроса
     */
    public static Response doPut(String path, JSONObject body) {
        return given().headers(setHeaders(body)).body(body.toString()).put(path);
    }

    /**
     * DELETE запрос
     *
     * @param path путь к эндпоинту
     * @return ответ запроса
     */
    public static Response doDelete(String path) {
        return given().headers(setHeaders()).delete(path);
    }

    /**
     * @param body Тело запроса
     * @return String Хэш
     */
    private static String generateToken(String body) {
        try {
            Mac sha256_HMAC = Mac.getInstance("HmacSHA256");
            SecretKeySpec secret_key = new SecretKeySpec(privateKey.getBytes(StandardCharsets.UTF_8), "HmacSHA256");
            sha256_HMAC.init(secret_key);
            return Hex.encodeHexString(sha256_HMAC.doFinal(body.getBytes(StandardCharsets.UTF_8)));
        } catch (NoSuchAlgorithmException | InvalidKeyException e) {
            throw new IllegalArgumentException(e);
        }
    }
}