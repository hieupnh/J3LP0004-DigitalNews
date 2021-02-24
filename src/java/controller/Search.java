package controller;

import model.Article;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import dao.ArticleDAO;

/**
 *
 * @author hieupnh
 */
public class Search extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        try {
            final int PAGE = 2;
            HttpSession session = request.getSession();

            int pageCurrent = Integer.parseInt(request.getParameter("page"));
            String keyword = (String) session.getAttribute("keyword");

            // get most and five recent article
            ArticleDAO article = new ArticleDAO();
            Article mostRecentArticle = article.getRecentArticle(1).get(0);
            request.setAttribute("mostRecentArticle", mostRecentArticle);
            ArrayList<Article> fiveRecentAticle = article.getRecentArticle(5);
            request.setAttribute("fiveRecentAticle", fiveRecentAticle);

            // get list article
            ArrayList<Article> articles = article.getListAticleSearch(PAGE, pageCurrent, keyword);
            request.setAttribute("listSearch", articles);

            // get number page to paging
            int numberPage = article.getNumberPage(PAGE, keyword);
            request.setAttribute("numberPage", numberPage);

            // get page current
            request.setAttribute("pageCurrent", pageCurrent);

            if (articles.isEmpty()) {
                request.setAttribute("error", "Not found");
                request.getRequestDispatcher("/search.jsp").forward(request, response);
            }

            request.getRequestDispatcher("/search.jsp").forward(request, response);
        } catch (Exception ex) {
            request.setAttribute("error", "Sorry! Error occurred");
            request.getRequestDispatcher("/search.jsp").forward(request, response);
            Logger.getLogger(Home.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            String keyword = request.getParameter("keyword");
            if (keyword == null || keyword.isEmpty()) {
                // refresh page 
                String servletPrev = request.getHeader("referer");
                String nameServletPrev = servletPrev.substring(servletPrev.lastIndexOf("/") + 1);
                response.sendRedirect(nameServletPrev);
            } else {
                HttpSession session = request.getSession();
                session.setAttribute("keyword", keyword);
                response.sendRedirect("Search?page=1");
            }
        } catch (IOException e) {
            request.setAttribute("error", "Sorry! Error occurred");
            request.getRequestDispatcher("/search.jsp").forward(request, response);
            Logger.getLogger(Home.class.getName()).log(Level.SEVERE, null, e);
        }
    }
}
