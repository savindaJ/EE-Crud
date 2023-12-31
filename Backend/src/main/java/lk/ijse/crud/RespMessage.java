package lk.ijse.crud;

import com.google.gson.Gson;

import java.util.ArrayList;

/**
 * @author : savindaJ
 * @date : 12/29/2023
 * @since : 0.1.0
 **/
public class RespMessage<T> {
    private String state;
    private String message;
    private ArrayList<T> data;

    public RespMessage() {
    }

    public String createMassage(String state, String message, ArrayList<T> list) {
        this.state = state;
        this.message = message;
        this.data = list;
        return new Gson().toJson(this);
    }
}
