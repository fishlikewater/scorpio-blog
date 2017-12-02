package scorpio.fileter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.filter.OncePerRequestFilter;
import scorpio.scorpioblog.mBlog.dao.TitleDAO;
import scorpio.scorpioblog.mBlog.dto.TitleDTO;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebFilter(urlPatterns = "/*")
public class TitleViewFilter extends OncePerRequestFilter {
    @Autowired
    private TitleDAO titleDAO;

    @Override
    protected void doFilterInternal(HttpServletRequest servletRequest, HttpServletResponse servletResponse, FilterChain filterChain) throws ServletException, IOException {

        servletRequest.setAttribute("baseUrl",servletRequest.getScheme()+"://"+servletRequest.getServerName()+":"+servletRequest.getServerPort()+servletRequest.getContextPath());

        filterChain.doFilter(servletRequest,servletResponse);
    }

    @Override
    public void destroy() {

    }
}
