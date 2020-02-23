package mif04.gdw.tp1.itf;

import com.github.mustachejava.DefaultMustacheFactory;
import com.github.mustachejava.Mustache;
import com.github.mustachejava.MustacheResolver;
import mif04.gdw.tp1.metier.Blog;
import mif04.gdw.tp1.modele.Billet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by ecoquery on 25/09/2016.
 */
public class BlogServlet extends HttpServlet {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final static Logger LOG = LoggerFactory.getLogger(BlogServlet.class);
    private EntityManager em;
    private Blog blog;
    Map<String, Mustache> templates;
    private DefaultMustacheFactory mf;

    private static class MyMustacheResolver implements MustacheResolver {

        @Override
        public Reader getReader(String resourceName) {
            InputStream is = BlogServlet.class.getResourceAsStream("/views/" + resourceName);
            if (is == null) {
                LOG.error("template {} not found", resourceName);
                return null;
            } else {
                return new InputStreamReader(is);
            }
        }
    }

    @Override
    public void destroy() {
        super.destroy();
        em.close();
    }

    @Override
    public void init() throws ServletException {
        super.init();
        em = Persistence.createEntityManagerFactory("pu-microblog").createEntityManager();
        blog = new Blog(em);
        mf = new DefaultMustacheFactory(new MyMustacheResolver());
        templates = new HashMap<>();
    }

    private void render(Writer output, String tplName, ViewContext ctx) {
        if (!templates.containsKey(tplName)) {
            templates.put(tplName, mf.compile(tplName + ".mustache"));
        }
        templates.get(tplName).execute(output, ctx);
    }

    private ViewContext initViewContext(HttpServletRequest request) {
        ViewContext ctx = new ViewContext();
        ctx.setTitre("Bienvenu(e)");
        ctx.setUser(blog.getUser(request.getParameter("user")));
        Billet billet = blog.getBillet(
                request.getParameter("titre"),
                request.getParameter("categorie"));
        ctx.setBillet(billet);
        if (billet != null) {
            ctx.setTitre(billet.getCategorie().getNom() + ":" + billet.getTitre());
        }
        ctx.setCategories(blog.getCategories());
        return ctx;
    }

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response) throws ServletException,
            IOException {
        view(response, initViewContext(request));
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ViewContext ctx = initViewContext(request);
        String action = request.getParameter("action");
        switch (action) {
            case "login":
                ctx.setUser(blog.getUser(request.getParameter("email")));
                break;
            case "logout":
                ctx.setUser(null);
                ctx.setTitre("Bienvenu(e)");
                ctx.setBillet(null);
                break;
            case "create_user":
                ctx.setUser(blog.newUser(request.getParameter("email"), request.getParameter("pseudo")));
                break;
            case "create": {
                Billet billet =
                        blog.nouveauBillet(
                                request.getParameter("titre"),
                                request.getParameter("categorie"),
                                request.getParameter("contenu"),
                                ctx.getUser());
                if (!ctx.getCategories().contains(billet.getCategorie())) {
                    ctx.getCategories().add(billet.getCategorie());
                }
                ctx.setBillet(billet);
            }
            break;
            case "edit":
                blog.changeBillet(ctx.getBillet(), request.getParameter("contenu"));
                break;
        }
        view(response, ctx);
    }

    protected void view(HttpServletResponse response, ViewContext context) throws IOException {
        response.setContentType("text/html");
        response.setStatus(HttpServletResponse.SC_OK);
        PrintWriter output = response.getWriter();
        output.println("<html><head><title>" + context.getTitre() + "</title></head><body>");
        output.println("<div class='login'>");
        viewLogin(output, context);
        output.println("</div>");
        output.println("<div class='categories' style='float:right'>");
        viewCategorieList(output, context);
        output.println("</div>");
        output.println("<div class='main'><h1>" + context.getTitre() + "</h1>");
        viewBody(output, context);
        output.println("</div>");
        output.println("</body></html>");
    }

    private void viewLogin(PrintWriter output, ViewContext context) {
        if (context.getUser() == null) {
            render(output, "login", context);
        } else {
            render(output, "logout", context);
        }
    }

    private void viewBody(PrintWriter output, ViewContext context) {
        if (context.getBillet() != null) {
            render(output, "billet", context);
            if (context.getUser() != null &&
                    context.getUser().equals(context.getBillet().getUser())) {
                render(output, "edit", context);
            }
        }
        if (context.getUser() != null) {
            render(output, "create", context);
        }
    }

    private void viewCategorieList(PrintWriter output, ViewContext context) {
        render(output, "categories", context);
    }
}
