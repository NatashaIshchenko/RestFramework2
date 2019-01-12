package tests;

import com.jayway.restassured.response.Response;
import common.RestEndPoints;
import endpointsClasses.logBonus.LogBonus;
import endpointsClasses.logBonus.LogBonusSimple;
import org.json.JSONObject;
import org.testng.SkipException;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import prototypes.RestTest;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Класс автотестов, проверяющих ответ ендпоинта /{userID}/balance/log/bonus
 */
public class LogBonusTest extends RestTest {

    private JSONObject body_for_bonus =
            new JSONObject(
                    "{\"amount\":" + ThreadLocalRandom.current().nextInt(1, 5) + "," +
                            "\"user_id\":"+"555555555"+", " +
                            "\"ip\":\"127.0.0.1\", " +
                            "\"comment\":\""+"random777777"+"\" " +
                            "}");
    String user_id = "555555555";

    /**
     * Тело теста
     */
    @Test(groups = {"rest"})
    public void logBonusTest() {
        //начисление бонуса
        Response result = RestEndPoints.postUserBalanceBonuses(user_id, body_for_bonus);
        if (result == null) {
            throw new SkipException("Functional is disabled");
        }
        //Проверка что бонус был выдан без ошибки
        result.then().statusCode(201);

        // отправка запроса /log/bonus и получение ответа
        LogBonus lb = new LogBonus();
        lb.setLogBonus(user_id);
        //получение последенего выданного бонуса
        LogBonusSimple last_bonus = null;
        long date = 0;
        for(LogBonusSimple lbs: lb.getLog_bonus()){
            if (lbs.getCreated_at() > date) {
                date = lbs.getCreated_at();
                last_bonus = lbs;
            }
        }
        //проверки
        SoftAssert sa = new SoftAssert();
        sa.assertEquals(last_bonus.getReason_id(), "bonus.add.rest", "Проверка emitter_id");
        sa.assertEquals(last_bonus.getSum(), body_for_bonus.getInt("amount")*100, "Проверка sum");
        sa.assertEquals(last_bonus.getEmitter_id(), body_for_bonus.getString("user_id"), "Проверка emitter_id");
        sa.assertEquals(last_bonus.getComment(), body_for_bonus.getString("comment"), "Проверка comment");
        sa.assertEquals(last_bonus.getIp(), body_for_bonus.getString("ip"), "Проверка ip");
        sa.assertAll();
    }
}