package ru.javawebinar.topjava.web;

import ru.javawebinar.topjava.dao.MealDAO;
import ru.javawebinar.topjava.dao.MealDAOImpl;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class MealServlet extends HttpServlet {
    private final MealDAO MEAL_DAO = new MealDAOImpl();
    private static final String INSERT_OR_EDIT = "meal.jsp";
    private static final String MEALS_LIST = "meals.jsp";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String forward = "";
        String action = request.getParameter("action");

        if (action != null) {
            if (action.equalsIgnoreCase("delete")) {
                int mealId = Integer.parseInt(request.getParameter("mealId"));
                MEAL_DAO.delete(mealId);

                request.setAttribute("meals",
                        MealsUtil.filteredByStreams(MEAL_DAO.getMealsList(), LocalTime.MIN, LocalTime.MAX));
                response.sendRedirect("meals");
            } else if (action.equalsIgnoreCase("edit")) {
                forward = INSERT_OR_EDIT;
                int mealId = Integer.parseInt(request.getParameter("mealId"));
                Meal meal = MEAL_DAO.getById(mealId);

                request.setAttribute("meal", meal);
                request.getRequestDispatcher(forward).forward(request, response);
            } else if (action.equalsIgnoreCase("insert")) {
                forward = INSERT_OR_EDIT;
                request.getRequestDispatcher(forward).forward(request, response);
            }
        } else {
            forward = MEALS_LIST;

            request.setAttribute("meals",
                    MealsUtil.filteredByStreams(MEAL_DAO.getMealsList(), LocalTime.MIN, LocalTime.MAX));
            request.getRequestDispatcher(forward).forward(request, response);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String description = request.getParameter("description");
        String calories = request.getParameter("calories");
        String date = request.getParameter("dateTime");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
        LocalDateTime dateTime = LocalDateTime.parse(date, formatter);

        Meal meal = new Meal(dateTime, description, Integer.parseInt(calories));

        String mealId = request.getParameter("mealId");
        if (request.getParameter("Save") != null){
            if (mealId == null || mealId.isEmpty()) {
                MEAL_DAO.add(meal);
            } else {
                meal.setId(Integer.parseInt(mealId));
                MEAL_DAO.update(meal);
            }
        }

        RequestDispatcher view = request.getRequestDispatcher(MEALS_LIST);
        request.setAttribute("meals",
                MealsUtil.filteredByStreams(MEAL_DAO.getMealsList(), LocalTime.MIN, LocalTime.MAX));
        view.forward(request, response);
    }
}
