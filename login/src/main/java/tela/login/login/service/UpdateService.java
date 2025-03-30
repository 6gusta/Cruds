package tela.login.login.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tela.login.login.Entitys.Register;
import tela.login.login.repository.RegisterRepository;

@Service
public class UpdateService {
    
    @Autowired
    RegisterRepository repository;

    
    public Register atualizar(Long id, Register novoUsuario){

        Optional <Register> usuarioExistente = repository.findById(id);

        if(usuarioExistente.isPresent()){
            Register usuario = usuarioExistente.get();
            usuario.setNome(novoUsuario.getNome());
            usuario.setTel(novoUsuario.getTel());
            usuario.setDataNasc(novoUsuario.getDataNasc());
            usuario.setEmail(novoUsuario.getEmail());
            usuario.setSenha(novoUsuario.getSenha());

            return repository.save(usuario);

        }else{
            return null;
        }

    }
}
