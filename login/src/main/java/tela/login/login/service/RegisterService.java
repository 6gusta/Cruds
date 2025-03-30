package tela.login.login.service;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.logging.Logger;

import tela.login.login.Entitys.Register;
import tela.login.login.repository.RegisterRepository;

@Service
public class RegisterService {

    private static final Logger LOGGER = Logger.getLogger(RegisterService.class.getName());

    private final RegisterRepository registerRepository;

    @Autowired
    public RegisterService(RegisterRepository registerRepository) {
        this.registerRepository = registerRepository;
    }

    private static final SecretKey SECRET_KEY = Keys.secretKeyFor(SignatureAlgorithm.HS512);

    public static SecretKey getSecretKey() {
        return SECRET_KEY;
    }

    private static final long EXPIRATION_TIME = 86400000; // 24 horas em milissegundos

    public Register cadastro(String nome, String senha, String email, String tel, String dataNasc) {
        try {
            Register cadastro = new Register();
            cadastro.setNome(nome);
            cadastro.setSenha(BCrypt.hashpw(senha, BCrypt.gensalt()));
            cadastro.setEmail(email);
            cadastro.setTel(tel);

            // Conversão segura de String para LocalDate
            try {
                LocalDate dataNascimento = LocalDate.parse(dataNasc);
                cadastro.setDataNasc(dataNasc);
            } catch (DateTimeParseException e) {
                LOGGER.severe("Erro ao converter data de nascimento: " + e.getMessage());
                return null;
            }

            return registerRepository.save(cadastro);
        } catch (Exception e) {
            LOGGER.severe("Erro no método de criar login: " + e.getMessage());
            return null;
        }
    }
}
