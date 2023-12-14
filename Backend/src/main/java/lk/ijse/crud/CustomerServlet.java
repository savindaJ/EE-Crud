package lk.ijse.crud;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObjectBuilder;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.*;

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
        resp.addHeader("Access-Control-Allow-Origin", "*");
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
                resp.setStatus(200);
                resp.getWriter().print(obj.build());
                System.out.println("saved !");
            }

        } catch (ClassNotFoundException | SQLException e) {
            JsonObjectBuilder obj = Json.createObjectBuilder();
            obj.add("state", "Error");
            obj.add("message", "Not Successfully saved..!");
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().print(obj.build());
            System.out.println("not !");
            e.printStackTrace();
        }

    }
}
