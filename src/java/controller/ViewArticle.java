package controller;

import model.Article;
import dao.ArticleDAO;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author hieupnh
 */
public class ViewArticle extends HttpServlet {

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
        try {
            int id = Integer.parseInt(request.getParameter("id"));
            ArticleDAO articleDao = new ArticleDAO();
            // get most recent article
            Article mostRecentArticle = articleDao.getRecentArticle(1).get(0);
            request.setAttribute("mostRecentArticle", mostRecentArticle);
            ArrayList<Article> fiveRecentAticle = articleDao.getRecentArticle(5);
            request.setAttribute("fiveRecentAticle", fiveRecentAticle);

            Article article = articleDao.getArticleById(id);
            // check article exist or not
            if (article == null) {
                request.setAttribute("error", "Not found article");
                request.getRequestDispatcher("/search.jsp").forward(request, response);
            } else {
                request.setAttribute("articleCurrent", article);
                request.getRequestDispatcher("index.jsp").forward(request, response);
            }
        } catch (Exception ex) {
            request.setAttribute("error", "Sorry! Error occurred");
            request.getRequestDispatcher("/search.jsp").forward(request, response);
            Logger.getLogger(Home.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

}
