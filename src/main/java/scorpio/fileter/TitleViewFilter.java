package scorpio.fileter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.filter.OncePerRequestFilter;
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
    private TitleDTO tDto;

    @Override
    protected void doFilterInternal(HttpServletRequest servletRequest, HttpServletResponse servletResponse, FilterChain filterChain) throws ServletException, IOException {
        /** 获取板块 */
        List<TitleDTO> list = tDto.findAll();
        servletRequest.setAttribute("titleList", list);
        servletRequest.setAttribute("baseUrl",servletRequest.getScheme()+"://"+servletRequest.getServerName()+":"+servletRequest.getServerPort()+servletRequest.getContextPath());

        filterChain.doFilter(servletRequest,servletResponse);
    }

    @Override
    public void destroy() {

    }
}
