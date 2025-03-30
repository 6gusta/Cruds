package tela.login.login.service;

import java.util.Optional;
import java.util.logging.Logger;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tela.login.login.Entitys.Register;
import tela.login.login.repository.RegisterRepository;
  
@Service
public class DeleteService {

    @Autowired
     RegisterRepository repository;
     private static final Logger LOGGER = Logger.getLogger(DeleteService.class.getName() );

    public boolean ecluiruser(Long id ){
     try {
        Register register = repository.findById(id).orElse(null);

        if( register == null){

            LOGGER.info(" O USER NÃO EXISTE");



        }

        repository.delete(register);

        LOGGER.info(" USER EXCLUIDO COM SUCESSO ");

        return true;
     } catch (Exception e) {

        LOGGER.severe("erro ao tenta executar o metodo ");
        e.printStackTrace();
        return false;
        // TODO: handle exception
     }
    }
    

   
}
