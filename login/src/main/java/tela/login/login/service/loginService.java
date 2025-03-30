package tela.login.login.service;

import java.time.format.DateTimeParseException;
import java.util.Date;

import javax.crypto.SecretKey;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import tela.login.login.Entitys.Register;
import java.util.logging.Logger;


import tela.login.login.repository.RegisterRepository;

@Service
public class loginService {
  @Autowired
  RegisterRepository repository;

  private static final SecretKey SECRET_KEY = Keys.secretKeyFor(SignatureAlgorithm.HS512);
  private static final Logger LOGGER = Logger.getLogger(loginService.class.getName());
  public static SecretKey getSecretKey() {
      return SECRET_KEY;
  }

  private static final long EXPIRATION_TIME = 86400000;

    public Register auntenticao( String nome, String senha ){


        Register auth = repository.findByNome(nome);
        if( auth != null && BCrypt.checkpw( senha , auth.getSenha())){
            return repository.findByNome(nome);

            

        }

        return null;
        
    }


    public String GerarToken(String nome){
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

    }
    
}
