package prototypes;

import common.RestApi;
import org.testng.ITestContext;
import org.testng.annotations.AfterClass;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;

/**
 * Базовый класс для тестов
 */
public abstract class RestTest {

    static {
        //Инициализация данных для работы по REST
        RestApi.setupREST_URL(getProperty("rest_url"), getProperty("private_key"), getProperty("client_id"));
    }

    static {
        TrustManager[] trustAllCerts = new TrustManager[]{
                new X509TrustManager() {
                    public X509Certificate[] getAcceptedIssuers() {
                        return null;
                    }
                    public void checkClientTrusted(X509Certificate[] certs,
                                                   String authType) {
                    }
                    public void checkServerTrusted(X509Certificate[] certs,
                                                   String authType) {
                    }
                }
        };
        // Install the all-trusting trust manager
        try {
            SSLContext sc = SSLContext.getInstance("TLS");
            sc.init(null, trustAllCerts, new SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
        } catch (Exception e) {
        }
    }

    /**
     * Получение значения для требуемого свойства
     *
     * @param name Имя свойства
     * @return Значение свойства
     */
    protected static String getProperty(String name){
        return null;
    }

    /**
     * Корректное завершение работы класса тестов
     */
    @AfterClass(alwaysRun = true)
    public void afterClass(ITestContext context) {
        //
    }
}