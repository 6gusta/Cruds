package tela.login.login.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tela.login.login.Entitys.Register;
import tela.login.login.repository.RegisterRepository;

@Service
public class GetService {

    @Autowired
    RegisterRepository repository;


    public Optional<Register> buscarid(Long id){

        Optional <Register>  register = repository.findById(id);

        return register;

    }

    
    
}
