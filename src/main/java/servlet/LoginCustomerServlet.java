package servlet;

import com.FlightCustomerPojo;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
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

@WebServlet("/LoginServlet")
public class LoginCustomerServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        PrintWriter printWriter = resp.getWriter();

        String email = req.getParameter("useremail");
        String password = req.getParameter("userpassword");

        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("ayushman");
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<FlightCustomerPojo> criteriaQuery = criteriaBuilder.createQuery(FlightCustomerPojo.class);
        Root<FlightCustomerPojo> root = criteriaQuery.from(FlightCustomerPojo.class);
        CriteriaQuery<FlightCustomerPojo> criteriaQuery1 = criteriaQuery.select(root);
        TypedQuery<FlightCustomerPojo> typedQuery = entityManager.createQuery(criteriaQuery1);

        List<FlightCustomerPojo> list = typedQuery.getResultList();

       List<FlightCustomerPojo> list1= list.stream().filter(s->s.getCustomerEmail().equals(email)).collect(Collectors.toList());
       List<FlightCustomerPojo> list2=list.stream().filter(s->s.getCustomerPassword().equals(password)).collect(Collectors.toList());

       if(list1.size()!=0 && list2.size()!=0){
           RequestDispatcher requestDispatcher = req.getRequestDispatcher("searchingFlight.jsp");
           requestDispatcher.include(req, resp);
           printWriter.println(("login successfully"));
       }
       else {
           RequestDispatcher requestDispatcher = req.getRequestDispatcher("loginCustomer.jsp");
           requestDispatcher.include(req, resp);
           printWriter.println(("invalid email or password"));
       }

    }
}
