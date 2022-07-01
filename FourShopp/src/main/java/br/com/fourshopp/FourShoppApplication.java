package br.com.fourshopp;

import br.com.fourshopp.Util.UtilMenu;
import br.com.fourshopp.entities.*;
import br.com.fourshopp.repository.ProdutoRepository;
import br.com.fourshopp.service.ClienteService;
import br.com.fourshopp.service.FuncionarioService;
import br.com.fourshopp.service.OperadorService;
import br.com.fourshopp.service.ProdutoService;
import org.hibernate.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import java.io.*;
import java.text.ParseException;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

import static br.com.fourshopp.Util.UtilMenu.*;

@SpringBootApplication
public class FourShoppApplication implements CommandLineRunner {

    Scanner scanner = new Scanner(System.in);

    @Autowired
    private ClienteService clienteService;

    @Autowired
    private OperadorService operadorService;

    @Autowired
    private ProdutoService produtoService;

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private FuncionarioService funcionarioService;


    private Cliente cliente;

    public static void main(String[] args) {
        SpringApplication.run(FourShoppApplication.class, args);
    }

    @Override
    public void run(String[] args) throws Exception {

        System.out.println("====== BEM-VINDO AO FOURSHOPP ======");
        System.out.println("1- Sou cliente \n2- Área do ADM \n3- Seja um Cliente \n4- Login funcionário \n5- Encerrar ");
        int opcao = scanner.nextInt();
        menuInicial(opcao);
    }

    public void cadastrarAdministrador(){
        Funcionario administrador = new Funcionario();
        administrador.setCargo(Cargo.ADMINISTRADOR);
        administrador.setCpf("adm");
        administrador.setPassword("adm");
        Optional<Funcionario> adm = funcionarioService.loadByCpfAndPassword(administrador.getCpf(), administrador.getPassword());
        if(adm.isEmpty()) {
            funcionarioService.criarAdministrador(administrador);
        }
    }
    public void menuInicial(int opcao) throws CloneNotSupportedException, IOException, ParseException {
        if (opcao == 1) {
            System.out.println("Insira seu cpf: ");
            String cpf = scanner.next();
            System.out.println("Insira sua senha: ");
            String password = scanner.next();
            this.cliente = clienteService.loadByCpfAndPassword(cpf, password).orElseThrow(() -> new ObjectNotFoundException(1L, "Cliente"));
            if (cliente == null) {
                System.err.println("Usuario não encontrado!");
                menuInicial(4);
            }

            int contador = 1;
            while (contador == 1) {
                System.out.println("Escolha o setor: ");
                int setor = menuSetor(scanner);


                List<Produto> collect = produtoService.listaProdutosPorSetor(setor).stream().filter(x -> x.getSetor() == setor).collect(Collectors.toList());
                collect.forEach(produto -> {
                    System.out.println(produto.getId() + "- " + produto.getNome() + " Preço: " + produto.getPreco() + " Estoque - " + produto.getQuantidade());
                });

                System.out.println("Informe o número do produto desejado: ");
                Long produto = scanner.nextLong();

                System.out.println("Escolha a quantidade");
                int quantidade = scanner.nextInt();

                Produto foundById = produtoService.findById(produto);
                if (quantidade > foundById.getQuantidade()) {
                    System.out.println("Quantidade não disponível no estoque");
                } else {
                    produtoService.diminuirEstoque(quantidade, foundById);

                    Produto clone = foundById.clone();
                    System.out.println(clone.toString());
                    clone.getCalculaValor(quantidade, clone);
                    cliente.getProdutoList().add(clone);
                    System.out.println("Deseja outro produto S/N ?");
                    String escolha = scanner.next();


                    if (!escolha.equalsIgnoreCase("S")) {
                        contador = 2;
                        gerarCupomFiscal(cliente);
                        System.out.println("Gerando nota fiscal...");
                        System.err.println("Fechando a aplicação...");
                    }
                }
            }
        }
            if (opcao == 2) {
                cadastrarAdministrador();
                System.out.println("INTRANET FOURSHOPP....");

                System.out.println("Insira as credenciais do usuário administrador: ");

                System.out.println("Insira seu cpf: ");
                String cpf1 = scanner.next();

                System.out.println("Insira sua password: ");
                String password1 = scanner.next();

                Optional<Funcionario> admnistrador = this.funcionarioService.loadByCpfAndPassword(cpf1, password1);

                if (admnistrador.get().getCargo() != Cargo.ADMINISTRADOR) {
                    System.out.println("Administrador nao encontrado");
                    menuInicial(2);
                } else {
                    System.out.println("1- Cadastrar funcionários \n2- Cadastrar Operador \n3- Cadastrar chefe \n4- Demitir funcionário");
                    int escolhaAdm = scanner.nextInt();
                    if (escolhaAdm == 1) {
                        Funcionario funcionario = cadastrarFuncionario(scanner);
                        funcionarioService.create(funcionario);
                        System.out.println("Funcionário cadastrado com sucesso");
                    } else if (escolhaAdm == 2) {
                        Operador operador = menuCadastrarOperador(scanner);
                        operadorService.create(operador);
                        System.out.println("Operador cadastrado com sucesso");
                    } else if (escolhaAdm == 3) {
                        Funcionario funcionario = menucadastrarChefe(scanner);
                        funcionarioService.create(funcionario);
                        System.out.println("Chefe cadastrado com sucesso");
                    } else if (escolhaAdm == 4){
                        System.out.println("1- Demitir operador \n2- Demitir funcionário");
                        int option = scanner.nextInt();
                        switch (option) {
                            case 1:
                               demitirOperador(scanner, operadorService);
                               break;
                            case 2:
                                demitirFuncionario(scanner, funcionarioService);
                                break;
                            default:
                                System.out.println("Opção inválida!");
                        }

                    } else
                        System.out.println("Opção inválida!");

                }

            }

            if (opcao == 3) {
                Cliente cliente = menuCadastroCliente(scanner);
                this.clienteService.create(cliente);
                System.out.println("Bem-vindo, " + cliente.getNome());
                menuInicial(1);
            }

            if (opcao == 4) {
        System.out.println("Área do funcionário");

        System.out.println("1- Chefe  \n2- Operador ");
        int escolhaCargo = scanner.nextInt();

        System.out.println("Insira seu cpf: ");
        String cpf1 = scanner.next();

        System.out.println("Insira sua password: ");
        String password1 = scanner.next();

        if (escolhaCargo == 1) {
            this.funcionarioService.loadByCpfAndPassword(cpf1, password1);
            System.out.println("Menu Chefe" +
                    "\n 1- Cadastrar produto" +
                    "\n 2- Atualizar preço" +
                    "\n 3- Alterar estoque" +
                    "\n 4- Excluir produto" +
                    "\n 5- Cadastrar Operadores");
            int option = scanner.nextInt();
            switch (option) {
                case 1:
                    Produto produto1 = cadastrarProduto(scanner);
                    produtoService.create(produto1);
                    System.out.println("Produto cadastrado com sucesso!");
                    break;
                case 2:
                    System.out.println("Em qual setor você deseja alterar o preço?:");
                    int setor = menuSetor(scanner);
                    List<Produto> collect = produtoService.listaProdutosPorSetor(setor).stream().filter(x -> x.getSetor() == setor).collect(Collectors.toList());
                    collect.forEach(produto -> {
                        System.out.println(produto.getId() + "->" + produto.getNome() + "->" + produto.getPreco() + "->" + produto.getQuantidade());
                        System.out.println("Digite o numero do produto");
                        Long produto2 = scanner.nextLong();
                        System.out.println("Digite o novo preço");
                        double preco = scanner.nextDouble();
                        Produto foundById = produtoService.findById(produto2);
                        produtoService.atualizarPreco(produto, preco);
                        System.out.println("Preço atualizado com sucesso!");
                    });
                    break;
                case 3:
                    System.out.println("Qual setor você deseja alterar o estoque:");
                    int setor1 = menuSetor(scanner);
                    List<Produto> collect1 = produtoService.listaProdutosPorSetor(setor1).stream().filter(x -> x.getSetor() == setor1).collect(Collectors.toList());
                    collect1.forEach(produto -> {
                        System.out.println(produto.getId() + "->" + produto.getNome() + "->" + produto.getPreco() + "->" + produto.getQuantidade());
                        System.out.println("Digite o numero do produto");
                        Long produto2 = scanner.nextLong();
                        System.out.println("Digite a nova quantidade");
                        Integer quantidade = scanner.nextInt();
                        produtoService.altualizarEstoque(quantidade, produto2);
                        System.out.println("Estoque alterado com sucesso!");

                    });
                    break;
                case 4:
                    System.out.println("De onde você quer excluir o produto?:");
                    int setor2 = menuSetor(scanner);
                    List<Produto> collect2 = produtoService.listaProdutosPorSetor(setor2).stream().filter(x -> x.getSetor() == setor2).collect(Collectors.toList());
                    collect2.forEach(produto -> {
                        System.out.println(produto.getId() + "->" + produto.getNome() + "->" + produto.getPreco() + "->" + produto.getQuantidade());
                        System.out.println("Digite o id do produto que deseja excluir");
                        Long id = scanner.nextLong();
                        Produto foundById = produtoService.findById(id);
                        produtoService.remove(id);
                        System.out.println("Produto deletado com sucesso!");
                    });
                    break;
                case 5:
                    Operador operador = menuCadastrarOperador(scanner);
                    operadorService.create(operador);
                    System.out.println("Operador cadastrado!");
                    break;

            }

        }
    }


}
    }

