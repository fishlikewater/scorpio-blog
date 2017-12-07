package scorpio.fileter;

import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter(urlPatterns = {"*.css","*.js","*.png"})
public class CorsFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        //response.setHeader("Access-Control-Allow-Origin",request.getHeader("origin"));
        response.setHeader("Access-Control-Allow-Origin","*");  //允许跨域访问的域
        response.setHeader("Access-Control-Allow-Methods","POST,GET,OPTIONS,DELETE,PUT");  //允许使用的请求方法
        //response.setHeader("Access-Control-Expose-Headers","*");
        response.setHeader("Cache-Control","max-age=691200");
        //response.setHeader("Access-Control-Allow-Headers", "x-requested-with,Cache-Control,Pragma,Content-Type,Authorization");  //允许使用的请求方法
        response.setHeader("Access-Control-Allow-Credentials","true");//是否允许请求带有验证信息

        request.setAttribute("baseUrl",request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath());
        filterChain.doFilter(request, response);

    }
}
