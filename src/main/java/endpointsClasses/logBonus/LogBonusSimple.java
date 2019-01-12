package endpointsClasses.logBonus;

import com.google.gson.JsonObject;
import com.jayway.restassured.response.Response;

/**
 * Класс для атомарного элемента из ответа эндпоинта /{userID}/balance/log/bonus
 */
public class LogBonusSimple {

    private String reason_id;
    /**
     * размер бонуса
     */
    private long sum;

    private String emitter_id;

    private String balance_after;

    private String ip;

    /**
     * Поле ID пользователя
     */
    private String id;

    private String comment;

    private String status;

    /**
     * Дата начисления
     */
    private long created_at;

    private String payment_system;

    /**
     * Конструктор
     */
    public LogBonusSimple(JsonObject jo) {
        this.reason_id = jo.get("reason_id").getAsString();
        this.sum = jo.get("sum").getAsLong();
        this.emitter_id = jo.get("emitter_id").getAsString();
        this.balance_after = jo.get("balance_after").getAsString();
        if(!jo.get("ip").isJsonNull())
            this.ip = jo.get("ip").getAsString();
        else
            this.ip = null;
        this.id = jo.get("id").getAsString();
        if(!jo.get("comment").isJsonNull())
            this.comment = jo.get("comment").getAsString();
        else
            this.comment = null;
        if(!jo.get("status").isJsonNull())
            this.status = jo.get("status").getAsString();
        else
            this.status = null;
        this.created_at = jo.get("created_at").getAsLong();
        if(!jo.get("payment_system").isJsonNull())
            this.payment_system = jo.get("payment_system").getAsString();
        else
            this.payment_system = null;
    }

    /**
     * Получение значения ID пользователя
     *
     * @return айди пользователя
     */
    public String getId() {
        return id;
    }

    public long getCreated_at() {
        return created_at;
    }

    public long getSum() {
        return sum;
    }

    public String getBalance_after() {
        return balance_after;
    }

    public String getEmitter_id() {
        return emitter_id;
    }

    public String getIp() {
        return ip;
    }

    public String getReason_id() {
        return reason_id;
    }

    public String getComment() {
        return comment;
    }

    public String getPayment_system() {
        return payment_system;
    }

    public String getStatus() {
        return status;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("LogBonusSimple [");
        sb.append(" reason_id=").append(reason_id);
        sb.append(" sum=").append(sum);
        sb.append(" emitter_id=").append(emitter_id);
        sb.append(" ip=").append(ip);
        sb.append(" id=").append(id);
        sb.append(" comment=").append(comment);
        sb.append(" status=").append(status);
        sb.append(" created_at=").append(created_at);
        sb.append(" payment_system=").append(payment_system);
        sb.append("]");
        return sb.toString();
    }

    /**
     * Парсинг Response объекта в объект LogBonusSimple класса
     *
     */
    public LogBonusSimple parseLogBonusSimple(Response response) {
        if (response == null) {
            throw new IllegalArgumentException("No LogBonusSimple in response");
        }
        return response.as(LogBonusSimple.class);
    }
}
