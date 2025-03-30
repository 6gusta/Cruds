package tela.login.login.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import tela.login.login.Entitys.Register;

public interface RegisterRepository extends JpaRepository<Register, Long> {



   Register  findByNome(String nome);
    
}
