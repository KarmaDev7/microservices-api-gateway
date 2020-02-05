//package com.karma.apigateway.security;
//
//import java.io.IOException;
//import java.util.ArrayList;
//
//import javax.servlet.FilterChain;
//import javax.servlet.ServletException;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//import org.springframework.core.env.Environment;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
//
//import io.jsonwebtoken.Jwts;
//
//public class AuthenticationFilter extends BasicAuthenticationFilter {
//    
//	Environment environment;
//
//    public AuthenticationFilter(AuthenticationManager authManager, Environment environment) {
//        super(authManager);
//        this.environment = environment;
//    }
//    
//    
//    @Override
//    protected void doFilterInternal(HttpServletRequest req,
//            HttpServletResponse res,
//            FilterChain chain) throws IOException, ServletException {
//
//        String authorizationHeader = req.getHeader(environment.getProperty("authorization.token.header.name"));
//
//        if (authorizationHeader == null || !authorizationHeader.startsWith(environment.getProperty("authorization.token.header.prefix"))) {
//            chain.doFilter(req, res);
//            return;
//        }
//
//        UsernamePasswordAuthenticationToken authentication = getAuthentication(req);
//        
//        /**
//         *  Spring Framework will look for a UsernamePasswordAuthenticationToken in the SecurityContext 
//         *  and if it is not there then authentication was not successful and the request will be denied. 
//         */
//        SecurityContextHolder.getContext().setAuthentication(authentication);
//        chain.doFilter(req, res);
//    }  
//    
//    private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest req) {
//        String authorizationHeader = req.getHeader(environment.getProperty("authorization.token.header.name"));
//   
//         if (authorizationHeader == null) {
//             return null;
//         }
//
//         String token = authorizationHeader.replace(environment.getProperty("authorization.token.header.prefix"), "");
//
//         String userId = Jwts.parser()
//                 .setSigningKey(environment.getProperty("token.secret"))
//                 .parseClaimsJws(token)
//                 .getBody()
//                 .getSubject();
//
//         if (userId == null) {
//             return null;
//         }
//   
//         return new UsernamePasswordAuthenticationToken(userId, null, new ArrayList<>());
//
//     }
//}