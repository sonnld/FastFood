package controller.guest;

import dao.CategoriesDAO;
import dao.ProductsDAO;
import java.io.*;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.util.Vector;
import model.*;

@WebServlet(name = "ProductDetailServlet", urlPatterns = {"/productdetail"})

public class ProductDetailServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // lay ra thong tin cua 1 san pham
        int id = Integer.parseInt(request.getParameter("ID"));
        ProductsDAO pd = new ProductsDAO();
        Products p = pd.getProductByID(id);
        request.setAttribute("p", p);

        CategoriesDAO cd = new CategoriesDAO();
        String categoryName = cd.getCategoryByProductId(id);
        request.setAttribute("c", categoryName);
        
         // lay ra list category
        Vector<Categories> listC = cd.getCategories();
        request.setAttribute("listC", listC);

        request.getRequestDispatcher("product-detail.jsp").forward(request, response);

    }

}
