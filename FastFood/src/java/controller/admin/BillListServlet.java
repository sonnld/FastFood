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

@WebServlet(name = "BillListServlet", urlPatterns = {"/billlist"})

public class BillListServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        OrdersDAO od = new OrdersDAO();
        Vector<Orders> listO = new Vector<>();

        String strUid = request.getParameter("uid");
        if (strUid == null) {
            // lay ra danh sách tat ca order
            listO = od.getAllOrders();
            request.setAttribute("listO", listO);
            request.getRequestDispatcher("bill-manager.jsp").forward(request, response);
        } else {
            // lay ra danh sách  order theo customer
            int uid = Integer.parseInt(strUid);
            listO = od.getOrderByUserID(uid);
            request.setAttribute("listO", listO);
            request.getRequestDispatcher("order-history.jsp").forward(request, response);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        OrdersDAO od = new OrdersDAO();
        Vector<Orders> list = od.getAllOrders();
        Vector<Orders> listO = new Vector<>();

        String strOdid = request.getParameter("odid");
        if (strOdid == null) {
            String check = request.getParameter("check");
            if (check.equalsIgnoreCase("wait")) {
                for (Orders o : list) {
                    if (o.getStatus() == 1) {
                        listO.add(o);
                    }
                }
            } else if (check.equalsIgnoreCase("process")) {
                for (Orders o : list) {
                    if (o.getStatus() == 2) {
                        listO.add(o);
                    }
                }
            } else if (check.equalsIgnoreCase("done")) {
                for (Orders o : list) {
                    if (o.getStatus() == 3) {
                        listO.add(o);
                    }
                }
            }
        } else {
            int txt = Integer.parseInt(strOdid);

            for (Orders o : list) {
                if (o.getID() == txt) {
                    listO.add(o);
                }
            }
        }

        request.setAttribute("listO", listO);
        request.getRequestDispatcher("bill-manager.jsp").forward(request, response);

    }

}
