package common;

import com.jayway.restassured.response.Response;
import org.json.JSONObject;
import java.util.Map;
import static common.RestApi.doPost;

public class RestEndPoints {

    public static Response getUserBalanceLogBonus(String userID) {
        return RestApi.doGet(userID + "/balance/log/bonus");
    }

    public static Response getUserBalanceLogBonus(String userID, Map<String, Object> headers) {
        return RestApi.doGet(userID + "/balance/log/bonus", headers);
    }

    public static Response postUserBalanceBonuses(String userID, JSONObject body) {
        return doPost(userID + "/balance/bonuses", body);
    }

    public static Response getUserPhonesPhoneID(String userID, String phoneID) {
        return RestApi.doGet(userID + "/phones/" + phoneID);
    }

    public static Response postUserPhones(String userID, JSONObject body) {
        return doPost(userID + "/phones", body);
    }

    public static Response putUserPhonesPhoneID(String userID, String phoneID, JSONObject body) {
        return RestApi.doPut(userID + "/phones/" + phoneID, body);
    }

    public static Response deleteUserPhonesPphoneID(String userID, String phoneID) {
        return RestApi.doDelete(userID + "/phones/" + phoneID);
    }
}