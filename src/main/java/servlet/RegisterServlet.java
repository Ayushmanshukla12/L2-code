package servlet;

import com.FlightCustomerPojo;
import com.FlightDaoImpl;
import com.FlightDetailsPojo;

import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.stream.Collectors;

@WebServlet("/RegisterServlet")
public class RegisterServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        resp.setContentType("text/html");
        PrintWriter printWriter = resp.getWriter();

        String name = req.getParameter("username").trim().toLowerCase();
        String email = req.getParameter("useremail").trim().toLowerCase();
        String password = req.getParameter("userpassword").trim().toLowerCase();

        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("ayushman");
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<FlightCustomerPojo> criteriaQuery = criteriaBuilder.createQuery(FlightCustomerPojo.class);
        Root<FlightCustomerPojo> root = criteriaQuery.from(FlightCustomerPojo.class);
        CriteriaQuery<FlightCustomerPojo> criteriaQuery1 = criteriaQuery.select(root);
        TypedQuery<FlightCustomerPojo> typedQuery = entityManager.createQuery(criteriaQuery1);

        List<FlightCustomerPojo> list = typedQuery.getResultList();
        if (list.stream().filter(s -> s.getCustomerEmail().equals(email)).collect(Collectors.toList()).size() == 0) {
            FlightCustomerPojo flightCustomerPojo = new FlightCustomerPojo();
            flightCustomerPojo.setCustomerName(name);
            flightCustomerPojo.setCustomerEmail(email);
            flightCustomerPojo.setCustomerPassword(password);

            FlightDaoImpl dao = new FlightDaoImpl();
            dao.registerCustomer(flightCustomerPojo);

            RequestDispatcher requestDispatcher = req.getRequestDispatcher("index.jsp");
            requestDispatcher.include(req, resp);
            printWriter.println(("custome added successfully"));

        } else {
            RequestDispatcher requestDispatcher = req.getRequestDispatcher("registerCustomer.jsp");
            requestDispatcher.include(req, resp);
            printWriter.println(("custome already present"));
        }
    }
}
