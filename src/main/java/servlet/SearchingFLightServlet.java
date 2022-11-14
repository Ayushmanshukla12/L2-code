package servlet;

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
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@WebServlet("/SearchingFLightServlet")
public class SearchingFLightServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        PrintWriter printWriter = resp.getWriter();

        String flightSource= req.getParameter("flightSource").trim().toLowerCase();
        String flightDestination= req.getParameter("flightDestination").trim().toLowerCase();
//        Time flightTakeOffTime= Time.valueOf(req.getParameter("flightTakeOffTime")+":00");
//        Time flightLandingTime= Time.valueOf(req.getParameter("flightLandingTime")+":00");
//        int flightPrice= Integer.parseInt((req.getParameter("flightPrice")));

        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("ayushman");
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<FlightDetailsPojo> criteriaQuery = criteriaBuilder.createQuery(FlightDetailsPojo.class);
        Root<FlightDetailsPojo> root = criteriaQuery.from(FlightDetailsPojo.class);
        CriteriaQuery<FlightDetailsPojo> criteriaQuery1 = criteriaQuery.select(root);
        TypedQuery<FlightDetailsPojo> typedQuery = entityManager.createQuery(criteriaQuery1);

        List<FlightDetailsPojo> list = typedQuery.getResultList();
        if(!(flightSource.equals(flightDestination))){
            List<FlightDetailsPojo> list1 = list.stream().sorted(Comparator.comparing(FlightDetailsPojo::getFlightPrice).
                    thenComparing(FlightDetailsPojo::getFlightTakeOff)).collect(Collectors.toList());
            printWriter.println(list1+"<br><br>");

            printWriter.println("<a href='bookingFlight.html'>Click to Book Flight</a><br><br>");

        }
        else {
            RequestDispatcher requestDispatcher = req.getRequestDispatcher("searchingFlight.jsp");
            requestDispatcher.include(req, resp);
            printWriter.println(("source and destination cannot be same"));
        }
    }
}
