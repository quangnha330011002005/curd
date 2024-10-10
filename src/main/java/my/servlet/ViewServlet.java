/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package my.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author ADMIN
 */
public class ViewServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            //b1.lấy yêu cầu từ client
            String id = request.getParameter("id");
            //b2.Xử lý yêu cầu
            Connection conn = null;
            PreparedStatement ps = null;
            ResultSet rs = null;

            String kq = "";
            try {
                Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
                conn = DriverManager.getConnection("jdbc:sqlserver://PC330;databaseName=demodb", "sa", "sa");
                //Tạo đối tượng thi hành truy vấn
                ps = conn.prepareStatement("select * from users");
                //4.Thi hành truy vấn 
                rs = ps.executeQuery();
                //Xử lý kết quả trả về
                kq += "<table border=1>";
                kq += "<tr>";
                kq += "<td>Id</td><td>Name</td><td>Password</td><td>Email</td><td>Country</td><td>Edit</td><td>Delete</td>";
                kq += "</tr>";
                while (rs.next()) {
                    kq += "<tr>";
                    kq += "<td>" + rs.getInt(1) + "</td><td>"
                            + rs.getString(2) + "</td><td>" + rs.getString(3) + "</td><td>"
                            + rs.getString(4) + "</td><td>" + rs.getString(5) + "</td><td>"
                            + "<a href=EditServlet?id=" + rs.getInt(1) + ">Edit</a>"
                            + "</td><td><a href=DeleteServlet?id=" + rs.getInt(1) + ">Delete</a></td>";
                    kq += "</tr>";
                }
                kq += "</table>";
                //đóng kết nối
                conn.close();
            } catch (Exception e) {
                System.out.println("Loi:" + e.toString());
            }

            out.println("<html>");
            out.println("<body>");
            out.println("<a href='index.html'>Add New Users</a>");
            out.println("<h1>Users List</h1>");
            out.print(kq);
            out.println("</body>");
            out.println("</html>");
        }

    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
