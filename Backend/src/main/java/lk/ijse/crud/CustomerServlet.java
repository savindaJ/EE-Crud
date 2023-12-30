package lk.ijse.crud;

import com.google.gson.Gson;

import javax.json.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;

/**
 * @author : savindaJ
 * @date : 12/14/2023
 * @since : 0.1.0
 **/
@WebServlet(urlPatterns = "/customer")
public class CustomerServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("come !");
//        resp.addHeader("Access-Control-Allow-Origin", "*");
        JsonArrayBuilder jsonArr = Json.createArrayBuilder();

        try {
            Class.forName("com.mysql.jdbc.Driver");
            System.out.println("register driver !");
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/thogakade", "root", "80221474");
            System.out.println("connection :"+connection);
            PreparedStatement pstm = connection.prepareStatement("SELECT*from customer");
            ResultSet set = pstm.executeQuery();
            while (set.next()){
                JsonObjectBuilder customer = Json.createObjectBuilder();
                customer.add("id",set.getString(1));
                customer.add("name",set.getString(2));
                customer.add("address",set.getString(3));
                customer.add("salary",set.getString(4));

                jsonArr.add(customer.build());
            }

            JsonObjectBuilder obj = Json.createObjectBuilder();

            obj.add("state", "OK");
            obj.add("message", "Successfully Loaded..!");
            obj.add("data", jsonArr);
            resp.setStatus(200);

            resp.getWriter().print(obj.build());

            connection.close();

        } catch (ClassNotFoundException | SQLException e) {
            JsonObjectBuilder obj = Json.createObjectBuilder();

            obj.add("state", "Error");
            obj.add("message", e.getLocalizedMessage());
            obj.add("data","");
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);

            resp.getWriter().print(obj.build());
            e.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        resp.addHeader("Access-Control-Allow-Origin", "*");
        System.out.println("come post !");
        String id = req.getParameter("id");
        System.out.println(id);
        String name = req.getParameter("name");
        System.out.println(name);
        String address = req.getParameter("address");
        Double salary = Double.parseDouble(req.getParameter("salary"));
        System.out.println(salary);

        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/thogakade", "root", "80221474");
            PreparedStatement pstm = connection.prepareStatement("INSERT INTO customer VALUES (?,?,?,?)");
            pstm.setString(1,id);
            pstm.setString(2,name);
            pstm.setString(3,address);
            pstm.setDouble(4,salary);

            if ( pstm.executeUpdate()>0){
                JsonObjectBuilder obj = Json.createObjectBuilder();
                obj.add("state", "OK");
                obj.add("message", "Successfully saved..!");
                obj.add("data","");
                resp.setStatus(200);
                resp.getWriter().print(obj.build());
                System.out.println("saved !");
            }

        } catch (ClassNotFoundException | SQLException e) {
            JsonObjectBuilder obj = Json.createObjectBuilder();
            obj.add("state", "err");
            obj.add("message", e.getLocalizedMessage());
            obj.add("data", "");
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().print(obj.build());
        }

    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/thogakade", "root", "80221474");
            PreparedStatement pstm = connection.prepareStatement("DELETE FROM customer WHERE customerId=?");
            pstm.setString(1,req.getParameter("id"));

            if (pstm.executeUpdate()>0){
                RespMessage<Customer> message = new RespMessage<>();
                String json = message.createMassage("ok", "Successfully Deleted !", null);
                res.setStatus(200);
                res.getWriter().println(json);
            }

            connection.close();

        } catch (Exception e) {
            RespMessage<Customer> message = new RespMessage<>();
            String json = message.createMassage("ok", e.getLocalizedMessage(), null);
            res.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            res.getWriter().println(json);
        }


    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        /*Customer customer = new Customer("C001", "kamalanath", "Galle", 125040.8);
        Customer customer1= new Customer("C002", "savinda", "Dickwalla", 135000.8);
        Customer customer2 = new Customer("C003", "surath", "india", 125000.8);
        ArrayList<Customer> customers = new ArrayList<>();
        customers.add(customer);
        customers.add(customer1);
        customers.add(customer2);
        String json = new Gson().toJson(customers);
        resp.getWriter().println(json);*/

        Customer customer = new Customer("C001", "kamalanath", "Galle", 125040.8);
        Customer customer1= new Customer("C002", "savinda", "Dickwalla", 135000.8);
        Customer customer2 = new Customer("C003", "surath", "india", 125000.8);
        ArrayList<Customer> customers = new ArrayList<>();
        customers.add(customer);
        customers.add(customer1);
        customers.add(customer2);

        /*RespMessage<Customer> jsonObj = new RespMessage<>("ok", "Successfully", customers);

        String json = new Gson().toJson(jsonObj);*/

        RespMessage<Customer> msg = new RespMessage<>();

        String json = msg.createMassage("ok", "Successfully", customers);

        JsonReader reader = Json.createReader(req.getReader());

        JsonObject cus = reader.readObject();

        String cusId = cus.getString("id");

        System.out.println(cusId);

        resp.getWriter().println(json);
    }
}
