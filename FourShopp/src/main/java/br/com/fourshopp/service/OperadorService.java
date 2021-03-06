package br.com.fourshopp.service;

import br.com.fourshopp.entities.Funcionario;
import br.com.fourshopp.entities.Operador;
import br.com.fourshopp.repository.EnderecoRepository;
import br.com.fourshopp.repository.OperadorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OperadorService {

    @Autowired
    private OperadorRepository operadorRepository;

    @Autowired
    private EnderecoRepository enderecoRepository;

    @Autowired
    private FuncionarioService funcionarioService;

    public Operador create(Operador operador){
        enderecoRepository.save(operador.getEndereco());
        return operadorRepository.save(operador);
    }

    public Operador findById(Long id){
        return operadorRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Objeto não encontrado"));
    }

    public List<Operador> listAll(){
        return operadorRepository.findAll();
    }

    public void remove(Long id){
        operadorRepository.deleteById(id);
    }

    public Operador update(Operador operador, Long id){
        Operador found = findById(id);
        found.setCargo(operador.getCargo());
        found.setSalario(operador.getSalario());
        found.setCelular(operador.getCelular());
        found.setEmail(operador.getEmail());
        found.setPassword(operador.getPassword());
        return operadorRepository.save(found);
    }

    public Optional<Operador> loadByCpfAndPassword(String cpf, String password) {
        return operadorRepository.findByCpfAndPassword(cpf,password);
    }

    public void deleteBycpf(String cpf){
        Operador operador = operadorRepository.findOperadorByCpf(cpf);
        operadorRepository.deleteById(operador.getId());


    }
    public String deleteByCpf (String Cpf){
        Operador operador = operadorRepository.findOperadorByCpf(Cpf);
        try{
            if (operador.getNome() == null){
                return "CPF inválido";
            }
        } catch (NullPointerException e){
            return "CPF inválido";
        }
        List<Funcionario>  funcionarios = funcionarioService.listAll();
        for (Funcionario funcionario : funcionarios){
            List<Operador> operadores = funcionario.getOperadores();
            for (int i = 0; i < operadores.size(); i++){
                if (operadores.get(i).getCpf().equals(operador.getCpf())){
                    operadores.remove(i);
                    funcionario.setOperadores(operadores);
                    funcionarioService.create(funcionario);
                    break;
                }
            }
        }
        deleteBycpf(operador.getCpf());
        enderecoRepository.deleteById(operador.getEndereco().getId());

        return "Operador deletado com sucesso!";
    }
}
