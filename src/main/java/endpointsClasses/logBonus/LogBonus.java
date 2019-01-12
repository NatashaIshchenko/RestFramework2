package endpointsClasses.logBonus;


import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import common.RestEndPoints;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Класс для ответа эндпоинта /{userID}/balance/log/bonus
 */
public class LogBonus {
    /**
     * Список объектов LogBonusSimple
     */
    private Set<LogBonusSimple> log_bonus;
    /**
     * Конструктор
     */
    public LogBonus() {
        log_bonus = null;
    }

    public Set<LogBonusSimple> getLog_bonus() {
        return log_bonus;
    }

    public void setLogBonus(String userID) {
        log_bonus = new HashSet<>();
        JsonArray response = new JsonParser().parse(RestEndPoints.getUserBalanceLogBonus(userID).asString()).getAsJsonArray();
        for (JsonElement je : response) {
            log_bonus.add(new LogBonusSimple(je.getAsJsonObject()));
        }
    }

    public void setLogBonus(String userID, Map<String, Object> headers) {
        log_bonus = new HashSet<>();
        JsonArray response = new JsonParser().parse(RestEndPoints.getUserBalanceLogBonus(userID, headers).asString()).getAsJsonArray();
        for (JsonElement je : response) {
            log_bonus.add(new LogBonusSimple(je.getAsJsonObject()));
        }
    }
}