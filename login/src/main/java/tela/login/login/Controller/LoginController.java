package tela.login.login.Controller;

import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import tela.login.login.Entitys.Register;
import tela.login.login.service.DeleteService;
import tela.login.login.service.GetService;
import tela.login.login.service.RegisterService;
import tela.login.login.service.UpdateService;
import tela.login.login.service.loginService;

@RestController
@RequestMapping("/api")
public class LoginController {

    private final loginService loginService;
    private final RegisterService registerService;
    private final DeleteService deleteService;
    private  final GetService  getService;
    private final UpdateService updateService;

    public LoginController(loginService loginService, RegisterService registerService, DeleteService deleteService, GetService getService, UpdateService updatService) {
        this.loginService = loginService;
        this.registerService = registerService;
        this.deleteService = deleteService;
        this.getService = getService;
        this.updateService = updatService;
    }

    @PostMapping("/login")
    public ResponseEntity<String> send(@RequestBody Register auth) {
        Register user = loginService.auntenticao(auth.getNome(), auth.getSenha());
                      

        if (user != null) {
            return ResponseEntity.ok("Login bem-sucedido");
        }

        return ResponseEntity.status(401).body("Credenciais inválidas");
    }

    @PostMapping("/register")
    public ResponseEntity<String> cadastro(@RequestBody Register register) {
        try {
            Register cadastro = registerService.cadastro(
                register.getNome(),
                register.getSenha(),
                register.getEmail(),
                register.getTel(),
                register.getDataNasc()
            );

            if (cadastro != null) {
                return ResponseEntity.ok("Cadastro bem-sucedido");
            } else {
                return ResponseEntity.status(400).body("Erro ao realizar cadastro.");
            }
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Erro interno no servidor: " + e.getMessage());
        }
    }

    @DeleteMapping("/excluir/{id}")

    public ResponseEntity<?> excluiruser(@PathVariable("id") Long id){


        boolean sucesso = deleteService.ecluiruser(id);

        if(sucesso){
            return ResponseEntity.ok(" pedido canclado com sucesso ");
        }else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("user não econtrado");
        }
    }


    @GetMapping("/usarios/{id}")

    public ResponseEntity<?> buscauser(@PathVariable("id") Long id){
         
    Optional<Register> usuuario = getService.buscarid(id);

        if(usuuario != null){
            return ResponseEntity.ok(usuuario);
        }else{
            return  ResponseEntity.status(404).body("erro em buscar o id ");
        }
    }

    @PutMapping("/user/{id}")
    public ResponseEntity<?> atualizaUser(@PathVariable("id") Long id, @RequestBody Register novoUsuario) {
    
        Register usuarioAtualizado = updateService.atualizar(id, novoUsuario);
    
        if (usuarioAtualizado != null) {
            return ResponseEntity.ok(usuarioAtualizado);

        }else{
            return ResponseEntity.status(404).body("erro ao atualiza o user ");
        }

    }





}
