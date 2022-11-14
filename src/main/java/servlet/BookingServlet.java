package servlet;

import com.FlightCustomerPojo;
import com.FlightDaoImpl;
import com.FlightDetailsPojo;

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
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@WebServlet("/BookingServlet")
public class BookingServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
resp.setContentType("text/html");
        PrintWriter printWriter = resp.getWriter();

    int userId= Integer.parseInt(req.getParameter("userId"));
    int flightNumber= Integer.parseInt(req.getParameter("flightNumber"));
    String flightSource=req.getParameter("flightSource").trim().toLowerCase();
    String flightDestination=req.getParameter("flightDestination").trim().toLowerCase();

    if(!(flightSource.equals(flightDestination))){

        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("ayushman");
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<FlightDetailsPojo> criteriaQuery = criteriaBuilder.createQuery(FlightDetailsPojo.class);
        Root<FlightDetailsPojo> root = criteriaQuery.from(FlightDetailsPojo.class);
        CriteriaQuery<FlightDetailsPojo> criteriaQuery1 = criteriaQuery.select(root);
        TypedQuery<FlightDetailsPojo> typedQuery = entityManager.createQuery(criteriaQuery1);

        List<FlightDetailsPojo> list = typedQuery.getResultList();

        FlightCustomerPojo flightCustomerPojo = entityManager.find(FlightCustomerPojo.class,userId);

        List<FlightDetailsPojo> flightDetailsPojos = list.stream().filter(s->s.getFlightSource().equals(flightSource))
                .collect(Collectors.toList());

        List<FlightDetailsPojo> flightDetailsPojos1 = list.stream().filter(s->s.getFlightDestination().equals(flightDestination))
                .collect(Collectors.toList());

        if(flightCustomerPojo.getCustomerbookings()==0 || flightDetailsPojos.size()==0 || flightDetailsPojos1.size()==0) {

            flightCustomerPojo.setCustomerbookings(flightCustomerPojo.getCustomerbookings() + 1);
            List<FlightCustomerPojo> list1 = new ArrayList<>();
            list1.add(flightCustomerPojo);
            FlightDetailsPojo flightDetailsPojo = entityManager.find(FlightDetailsPojo.class, flightNumber);
            flightDetailsPojo.setFlightCustomerPojos(list1);

            FlightDaoImpl dao = new FlightDaoImpl();
            dao.bookingFLight(flightDetailsPojo, flightCustomerPojo);

            RequestDispatcher requestDispatcher = req.getRequestDispatcher("loginCustomer.jsp");
            requestDispatcher.include(req, resp);
            printWriter.println(("booking done succesfully"));
        }
        else {
            RequestDispatcher requestDispatcher = req.getRequestDispatcher("bookingFlight.jsp");
            requestDispatcher.include(req, resp);
            printWriter.println(("customer has already booked flight"));
        }
    }
    else {
        RequestDispatcher requestDispatcher = req.getRequestDispatcher("bookingFlight.jsp");
        requestDispatcher.include(req, resp);
        printWriter.println(("source and destination cannot be same"));
    }
    }
}
