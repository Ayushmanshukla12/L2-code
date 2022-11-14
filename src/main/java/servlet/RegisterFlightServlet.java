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
import java.sql.Time;
import java.util.List;
import java.util.stream.Collectors;

@WebServlet("/RegisterFLightServlet")
public class RegisterFlightServlet extends HttpServlet {

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        resp.setContentType("text/html");
        PrintWriter printWriter = resp.getWriter();

        String flightSource = req.getParameter("flightSource").trim().toLowerCase();
        String flightDestination = req.getParameter("flightDestination").trim().toLowerCase();
        Time flightTakeOffTime = Time.valueOf(req.getParameter("flightTakeOffTime") + ":00");
        Time flightLandingTime = Time.valueOf(req.getParameter("flightLandingTime") + ":00");
        int flightPrice = Integer.parseInt((req.getParameter("flightPrice")));

        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("ayushman");
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<FlightDetailsPojo> criteriaQuery = criteriaBuilder.createQuery(FlightDetailsPojo.class);
        Root<FlightDetailsPojo> root = criteriaQuery.from(FlightDetailsPojo.class);
        CriteriaQuery<FlightDetailsPojo> criteriaQuery1 = criteriaQuery.select(root);
        TypedQuery<FlightDetailsPojo> typedQuery = entityManager.createQuery(criteriaQuery1);

        List<FlightDetailsPojo> list = typedQuery.getResultList();

        if (!(flightSource.equals(flightDestination)))
        {
            List<FlightDetailsPojo> list1 =list.stream().filter(s -> s.getFlightTakeOff().after(flightLandingTime)).
                    collect(Collectors.toList());

            List<FlightDetailsPojo> list2 =list.stream().filter(s -> s.getFlightLanding().
                    before(flightTakeOffTime)).collect(Collectors.toList());
            if (list1.size() == 0 || list2.size() == 0)
            {
                FlightDetailsPojo flightDetailsPojo = new FlightDetailsPojo();
                flightDetailsPojo.setFlightDestination(flightDestination);
                flightDetailsPojo.setFlightSource(flightSource);
                flightDetailsPojo.setFlightLanding(flightLandingTime);
                flightDetailsPojo.setFlightTakeOff(flightTakeOffTime);
                flightDetailsPojo.setFlightPrice(flightPrice);

                FlightDaoImpl dao = new FlightDaoImpl();
                dao.registerFLight(flightDetailsPojo);

                RequestDispatcher requestDispatcher = req.getRequestDispatcher("index.jsp");
                requestDispatcher.include(req, resp);
                printWriter.println(("flight added successfully"));
            } else {
                RequestDispatcher requestDispatcher = req.getRequestDispatcher("registerFlight.jsp");
                requestDispatcher.include(req, resp);
                printWriter.println(("takeoff time and landing time should not match"));
            }
        }else {
            RequestDispatcher requestDispatcher = req.getRequestDispatcher("registerFlight.jsp");
            requestDispatcher.include(req, resp);
            printWriter.println(("source and destination cant be same"));
        }

    }
}