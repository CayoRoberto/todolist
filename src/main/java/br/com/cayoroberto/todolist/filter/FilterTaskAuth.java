package br.com.cayoroberto.todolist.filter;

import at.favre.lib.crypto.bcrypt.BCrypt;
import br.com.cayoroberto.todolist.repository.IUserRepository;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Base64;

@Component
public class FilterTaskAuth extends OncePerRequestFilter {

    @Autowired
    private IUserRepository userRepository;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

       var servletPath = request.getServletPath();

       if(servletPath.startsWith("/tasks/")){

           // Pegar a autenticação (usuário e senha)
           var authorization = request.getHeader("Authorization");
           var authEncoded = authorization.substring("Basic".length()).trim();
           byte[] authDecode = Base64.getDecoder().decode(authEncoded);
           var authString = new String(authDecode);
           String[] credentials = authString.split(":");
           // ["username", "password"]
           String username = credentials[0];
           String password = credentials[1];

           //Validar usuário
           var user = this.userRepository.findUserByUsername(username);
           if(user == null){
               response.sendError(401, "Usuário sem autorização");
           }else{
               //Validar senha
               var passwordVerify = BCrypt.verifyer().verify(password.toCharArray(), user.getPassword());
               if(passwordVerify.verified){
                   request.setAttribute("idUser",user.getId());
                   //Segue viagem
                   filterChain.doFilter(request, response);
               }else{
                   response.sendError(401, "Senha inválida!");
               }

           }
       }else{
           filterChain.doFilter(request, response);
       }

    }
}

