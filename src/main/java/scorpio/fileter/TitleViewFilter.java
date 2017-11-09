package scorpio.fileter;

import org.springframework.beans.factory.annotation.Autowired;
import scorpio.scorpioblog.mBlog.dto.TitleDTO;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;
import java.util.List;

@WebFilter(urlPatterns = "/*")
public class TitleViewFilter implements Filter {
    @Autowired
    private TitleDTO tDto;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        /** 获取板块 */
        List<TitleDTO> list = tDto.findAll();
        servletRequest.setAttribute("titleList", list);
        filterChain.doFilter(servletRequest,servletResponse);
    }

    @Override
    public void destroy() {

    }
}
