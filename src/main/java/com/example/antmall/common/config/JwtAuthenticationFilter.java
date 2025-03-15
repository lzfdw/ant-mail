package com.example.antmall.common.config;

import com.example.antmall.common.util.JwtUtil;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.DatatypeConverter;
import java.io.IOException;

/**
 * 自定义过滤器：从请求头中获取JWT并解析，若验证通过，则将用户信息存入SecurityContext。
 */
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    public JwtAuthenticationFilter(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    /**
     * 每个请求都会进入此过滤器
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        try {
            // 1. 从请求头获取 token
            //    约定： Authorization: Bearer xxxxx
            String authHeader = request.getHeader("Authorization");
            if (StringUtils.hasText(authHeader) && authHeader.startsWith("Bearer ")) {
                String token = authHeader.substring(7); // 去掉"Bearer "

                // 2. 校验 token (有效性 & 过期)
                boolean isValid = jwtUtil.validateToken(token);
                if (isValid) {
                    // 3. 解析 claims
                    Claims claims = jwtUtil.getClaimsFromToken(token);
                    String username = claims.getSubject();   // 在 generateToken 时 setSubject(username)

                    // 如果你需要在 token 中存更多信息，比如角色/权限，可以：
                    String role = "ROLE_" + claims.get("role", String.class);
                    // 这里简单写：如果 username="admin"，就设个 ROLE_ADMIN，否则 ROLE_USER
//                    String role = "ROLE_USER";
//                    if ("admin".equals(username)) {
//                        role = "ROLE_ADMIN";
//                    }

                    // 4. 将用户信息放入 Spring Security 的上下文
                    //    这里演示给一个简单的 Authority
                    UsernamePasswordAuthenticationToken authentication =
                            new UsernamePasswordAuthenticationToken(
                                    username,
                                    null,
                                    // 绑定一个权限列表
                                    org.springframework.security.core.authority.AuthorityUtils.createAuthorityList(role)
                            );

                    // 设置一些细节（如IP、SessionId等）
                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                    // 将认证对象放入SecurityContext
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            }
        } catch (Exception e) {
            // 如果出错，你可以选择清空上下文或抛异常
            SecurityContextHolder.clearContext();
        }

        // 调用后续的过滤器
        filterChain.doFilter(request, response);
    }
}
