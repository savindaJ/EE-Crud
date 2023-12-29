package lk.ijse.crud;

import java.util.ArrayList;

/**
 * @author : savindaJ
 * @date : 12/29/2023
 * @since : 0.1.0
 **/
public class RespMessage <T> {
    private String state;
    private String message;
    private ArrayList<T> data;

    public RespMessage(String state, String message, ArrayList<T> data) {
        this.state = state;
        this.message = message;
        this.data = data;
    }
}
