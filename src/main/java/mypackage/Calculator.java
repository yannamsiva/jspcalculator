package mypackage;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Calculator extends HttpServlet {

    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/calculator_db?useSSL=false&serverTimezone=UTC";
    private static final String JDBC_USER = "root";  // your DB username
    private static final String JDBC_PASSWORD = "root";  // your DB password

    public long addFunc(long first, long second) { return first + second; }
    public long subFunc(long first, long second) { return first - second; }
    public long mulFunc(long first, long second) { return first * second; }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        try {
            long a1 = Long.parseLong(request.getParameter("n1"));
            long a2 = Long.parseLong(request.getParameter("n2"));
            String operation = request.getParameter("operation");

            long result = 0;
            String opName = "";

            switch (operation) {
                case "add": result = addFunc(a1, a2); opName = "Addition"; break;
                case "sub": result = subFunc(a1, a2); opName = "Subtraction"; break;
                case "mul": result = mulFunc(a1, a2); opName = "Multiplication"; break;
                default: opName = "Invalid operation";
            }

            out.println("<h1>" + opName + " Result: " + result + "</h1>");

            // 1️⃣ Store into MySQL
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                Connection conn = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);

                String insertSQL = "INSERT INTO calculations (num1, num2, operation, result) VALUES (?, ?, ?, ?)";
                PreparedStatement ps = conn.prepareStatement(insertSQL);
                ps.setLong(1, a1);
                ps.setLong(2, a2);
                ps.setString(3, operation);
                ps.setLong(4, result);
                ps.executeUpdate();
                ps.close();

                // 2️⃣ Retrieve all calculations
                String selectSQL = "SELECT * FROM calculations ORDER BY created_at DESC";
                PreparedStatement psSelect = conn.prepareStatement(selectSQL);
                ResultSet rs = psSelect.executeQuery();

                out.println("<h2>Calculation History:</h2>");
                out.println("<table border='1'><tr><th>ID</th><th>Num1</th><th>Num2</th><th>Operation</th><th>Result</th><th>Time</th></tr>");
                while (rs.next()) {
                    out.println("<tr>");
                    out.println("<td>" + rs.getInt("id") + "</td>");
                    out.println("<td>" + rs.getLong("num1") + "</td>");
                    out.println("<td>" + rs.getLong("num2") + "</td>");
                    out.println("<td>" + rs.getString("operation") + "</td>");
                    out.println("<td>" + rs.getLong("result") + "</td>");
                    out.println("<td>" + rs.getTimestamp("created_at") + "</td>");
                    out.println("</tr>");
                }
                out.println("</table>");

                rs.close();
                psSelect.close();
                conn.close();

            } catch (Exception dbEx) {
                out.println("<p style='color:red;'>Database Error: " + dbEx.getMessage() + "</p>");
            }

            RequestDispatcher rd = request.getRequestDispatcher("/index.jsp");
            rd.include(request, response);

        } catch (Exception e) {
            out.println("<h3 style='color:red;'>Error: " + e.getMessage() + "</h3>");
        }
    }
}
