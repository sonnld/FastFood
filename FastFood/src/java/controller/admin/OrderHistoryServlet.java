package controller.admin;

import dao.OrdersDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.Vector;
import model.Orders;

@WebServlet(name = "OrderHistoryServlet", urlPatterns = {"/orderhistory"})

public class OrderHistoryServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        OrdersDAO od = new OrdersDAO();
        Vector<Orders> listO = new Vector<>();

        String strUid = request.getParameter("uid");
        if (strUid.isEmpty() || strUid == null) {
            // lay ra danh sách tat ca order
            listO = od.getAllOrders();
        } else {
            int uid = Integer.parseInt(strUid);
            listO = od.getOrderByUserID(uid);
        }

        request.setAttribute("listO", listO);
        request.getRequestDispatcher("order-history.jsp").forward(request, response);

    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String txt = request.getParameter("txt");
        OrdersDAO od = new OrdersDAO();
        Vector<Orders> list = od.getAllOrders();
        Vector<Orders> listO = new Vector<>();

        for (Orders o : list) {
            if (o.getUsername().contains(txt)) {
                listO.add(o);
            }
        }

        request.setAttribute("listO", listO);
        request.getRequestDispatcher("bill-manager.jsp").forward(request, response);

    }

}
