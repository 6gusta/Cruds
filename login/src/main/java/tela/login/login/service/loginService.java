package tela.login.login.service;

import java.time.format.DateTimeParseException;
import java.util.Date;

import javax.crypto.SecretKey;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import tela.login.login.Entitys.LoginDto;
import tela.login.login.Entitys.Register;
import tela.login.login.Util.JwtUtil;

import java.util.logging.Logger;


import tela.login.login.repository.RegisterRepository;

@Service
public class loginService {
  @Autowired
  RegisterRepository repository;

  @Autowired
  JwtUtil jwtUtil;




  private static final Logger LOGGER = Logger.getLogger(loginService.class.getName());



  @Autowired
  private PasswordEncoder passwordEncoder;
  
  public String  auntenticao(LoginDto loginDTO ){


    Register auth = repository.findByNome(loginDTO.getNome());
    if (auth != null && passwordEncoder.matches(loginDTO.getSenha(), auth.getSenha())) {



        return jwtUtil.GerarToken(auth.getNome(),  auth.getRole());

        

    }

    return null;
    
}


    /*public String GerarToken(String nome){
        try {
            String token = Jwts.builder()
            .setSubject(nome)
            .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
            .signWith(SECRET_KEY)
            .compact();
            System.out.println("TOKEN GERADO COM SUCESSO "+ token);
            LOGGER.info("TOKEN GERADO COM SUCESSO");
            return token;
        
        } catch (DateTimeParseException e) {

            LOGGER.severe("Erro ao converter data de nascimento: " + e.getMessage());
             return null;
        }

    }/* */
    
}
