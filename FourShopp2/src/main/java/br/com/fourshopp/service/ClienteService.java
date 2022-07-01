package br.com.fourshopp.service;

import br.com.fourshopp.entities.Cliente;
import br.com.fourshopp.repository.ClienteRepository;

import br.com.fourshopp.repository.EnderecoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;
    @Autowired
    EnderecoRepository enderecoRepository;

    public Cliente create(Cliente cliente) {
        enderecoRepository.save(cliente.getEndereco());
        return clienteRepository.save(cliente);
    }

    public Cliente findById(Long id){
        return clienteRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Objeto não encontrado"));
    }

    public List<Cliente> listAll(){
        return clienteRepository.findAll();
    }

    public void remove(Long id){
        clienteRepository.deleteById(id);
    }

    private Cliente update(Cliente operador, Long id){
        Cliente found = findById(id);
        found.setCelular(operador.getCelular());
        found.setEmail(operador.getEmail());
        found.setPassword(operador.getPassword());
        return clienteRepository.save(found);
    }

    public Optional<Cliente> loadByCpfAndPassword(String cpf, String password){
        return clienteRepository.findByCpfAndPassword(cpf,password);
    }
}
