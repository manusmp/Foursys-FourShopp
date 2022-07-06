package br.com.fourshopp.Util;

import br.com.fourshopp.entities.*;
import br.com.fourshopp.service.FuncionarioService;
import br.com.fourshopp.service.OperadorService;
import com.lowagie.text.*;
import com.lowagie.text.Font;
import com.lowagie.text.Image;
import com.lowagie.text.pdf.PdfWriter;

import java.awt.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;

public class UtilMenu {

    private static double valorTotalCompra;

    private static Scanner scanner;

    public static Cliente menuCadastroCliente(Scanner scanner) {

        System.out.println("Insira o nome: ");
        String nome = scanner.next();

        String email = null;
        boolean em = false;
        while (!em) {
            System.out.println("Insira o email: ");
            email = scanner.next();
            if (!Validacoes.isEmail(email)) {
                System.out.println("Email inválido!");
                em = false;
            } else {
                em = true;
            }
        }
        String celular = null;
        boolean ce = false;
        while (!ce) {
            System.out.println("Insira o celular: ");
            celular = scanner.next();
            if (!Validacoes.isCellPhone(celular)) {
                System.out.println("Celular inválido!");
                ce = false;
            } else {
                ce = true;
            }
        }

        String cpf = null;
        boolean c = false;
        while (!c) {
            System.out.println("Insira o cpf: ");
            cpf = scanner.next();
            if (!Validacoes.isCpf(cpf)) {
                System.out.println("Cpf inválido!");
                c = false;
            } else {
                c = true;
            }
        }

        Boolean p = false;
        String password = null;
        while (!p) {
            System.out.println("Insira a senha: ");
            password = scanner.next();
            if (password.length() < 8) {
                System.out.println("Digite uma senha correta(Acima ou igual a 8)");
                p = false;
            } else {
                p = true;
            }
        }

        System.out.println("Insira a rua: ");
        String rua = scanner.next();
        scanner.nextLine();

        System.out.println("Insira o número: ");
        int numero = scanner.nextInt();

        System.out.println("Insira o bairro: ");
        String bairro = scanner.next();

        System.out.println("Insira a cidade: ");
        String cidade = scanner.next();

        Date data;
        while (true) {
            try {
                System.out.println("Insira sua data de nascimento (dd/MM/yyyy): ");
                String dataNascimento = scanner.next();
                SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
                data = formato.parse(dataNascimento);
                break;
            } catch(ParseException e){
                System.out.println("Por favor, informe a data no formato: dd/MM/yyyy ");
            }
        }

        Endereco endereco = new Endereco(rua, numero, bairro, cidade);
        Cliente cliente = new Cliente(nome, email, celular, password, cpf, endereco, new Date());

        return cliente;

    }

    public static int menuSetor(Scanner scanner) {
        System.out.println("Digite a opção desejada: " +
                "\n1- MERCEARIA \n2- BAZAR \n3- ELETRÔNICOS");
        int opcao = scanner.nextInt();
        return opcao;
    }

    public static void gerarCupomFiscal(Cliente cliente) throws IOException {
        List<Produto> produtos = cliente.getProdutoList();
        Document document = new Document(PageSize.A4);
        File file = new File("CupomFiscal_" + new Random().nextInt() + ".pdf");
        String absolutePath = file.getAbsolutePath();
        PdfWriter.getInstance(document, new FileOutputStream(absolutePath));
        document.open();

        Font fontTitle = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
        fontTitle.setSize(18);

        Image image1 = Image.getInstance("src/main/java/br/com/fourshopp/service/fourshopp.png");
        image1.scaleAbsolute(140f, 140f);
        image1.setAlignment(Element.ALIGN_CENTER);


        Font fontParagraph = FontFactory.getFont(FontFactory.HELVETICA);
        fontParagraph.setSize(12);

        Font total = FontFactory.getFont(FontFactory.HELVETICA);
        total.setSize(12);
        total.setColor(Color.blue);

        Font header = FontFactory.getFont(FontFactory.HELVETICA);
        header.setSize(12);
        header.setFamily("bold");

        document.add(image1);

        DecimalFormat df = new DecimalFormat();
        df.setMaximumFractionDigits(2);
        ListItem item1 = new ListItem();
        produtos.forEach(produto -> {

            System.out.println(produto.toString());
            Chunk nome = new Chunk("\n" + produto.getNome() + " (" + produto.getQuantidade() + ") \nPreço unidade : R$" + df.format(produto.getPreco() / produto.getQuantidade()));
            Phrase frase = new Phrase();
            frase.add(nome);

            Paragraph x = new Paragraph(frase);

            String preco = "............................................................................................................................R$ " + df.format(produto.getPreco());
            Paragraph y = new Paragraph(preco);
            y.setAlignment(Paragraph.ALIGN_RIGHT);
            item1.add(x);
            item1.add(y);

            valorTotalCompra = valorTotalCompra + produto.getPreco();
        });

        Paragraph paragraph = new Paragraph("\n\nTOTAL: R$" + df.format(valorTotalCompra), total);
        paragraph.setAlignment(Paragraph.ALIGN_RIGHT);


        document.add(item1);
        document.add(paragraph);


        document.close();
    }

    public static Funcionario cadastrarFuncionario(Scanner scanner) throws ParseException {

        System.out.println("Insira o nome: ");
        String nome = scanner.next();

        String email = null;
        boolean em = false;
        while (!em) {
            System.out.println("Insira o email: ");
            email = scanner.next();
            if (!Validacoes.isEmail(email)) {
                System.out.println("Email inválido!");
                em = false;
            } else {
                em = true;
            }
        }


       String celular = null;
        boolean ce = false;
        while (!ce) {
            System.out.println("Insira o celular: ");
            celular = scanner.next();
            if (!Validacoes.isCellPhone(celular)) {
                System.out.println("Celular inválido!");
                ce = false;
            } else {
                ce = true;
            }
        }


        String cpf = null;
        boolean c = false;
        while (!c) {
            System.out.println("Insira o cpf: ");
            cpf = scanner.next();
            if (!Validacoes.isCpf(cpf)) {
                System.out.println("Cpf inválido!");
                c = false;
            } else {
                c = true;
            }
        }

        Boolean p = false;
        String password = null;
        while (!p) {
            System.out.println("Insira a senha: ");
            password = scanner.next();
            if (password.length() < 8) {
                System.out.println("Digite uma senha correta(Acima ou igual a 8)");
                p = false;
            } else {
                p = true;
            }
        }

        System.out.println("Insira a rua: ");
        String rua = scanner.next();
        scanner.nextLine();

        System.out.println("Insira o número: ");
        int numero = scanner.nextInt();

        System.out.println("Insira o bairro: ");
        String bairro = scanner.next();

        System.out.println("Insira a cidade: ");
        String cidade = scanner.next();

        Date data;
        while (true) {
            try {
                System.out.println("Data de contratação (dd/MM/yyyy): ");
                String hireDate = scanner.next();
                SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
                data = formato.parse(hireDate);
                break;
            } catch(ParseException e){
                System.out.println("Por favor, informe a data no formato: dd/MM/yyyy ");
            }
        }

        System.out.println("Insira o salário CLT bruto: ");
        double salario = scanner.nextDouble();

        Endereco endereco = new Endereco(rua, numero, bairro, cidade);
        return new Funcionario(nome, email, celular, password, cpf, endereco, data, Cargo.CHEFE_SECAO, Setor.MERCEARIA, salario, new ArrayList<>(), new ArrayList<>());
    }

    public static Operador menuCadastrarOperador(Scanner scanner) throws ParseException {

        System.out.println("Insira o nome: ");
        String nome = scanner.next();

        String email = null;
        boolean em = false;
        while (!em) {
            System.out.println("Insira o email: ");
            email = scanner.next();
            if (!Validacoes.isEmail(email)) {
                System.out.println("Email inválido!");
                em = false;
            } else {
                em = true;
            }
        }

        String celular = null;
        boolean ce = false;
        while (!ce) {
            System.out.println("Insira o celular: ");
            celular = scanner.next();
            if (!Validacoes.isCellPhone(celular)) {
                System.out.println("Celular inválido!");
                ce = false;
            } else {
                ce = true;
            }
        }

        String cpf = null;
        boolean c = false;
        while (!c) {
            System.out.println("Insira o cpf: ");
            cpf = scanner.next();
            if (!Validacoes.isCpf(cpf)) {
                System.out.println("Cpf inválido!");
                c = false;
            } else {
                c = true;
            }
        }

        Boolean p = false;
        String password = null;
        while (!p) {
            System.out.println("Insira a senha: ");
            password = scanner.next();
            if (password.length() < 8) {
                System.out.println("Digite uma senha correta(Acima ou igual a 8)");
                p = false;
            } else {
                p = true;
            }
        }

        System.out.println("Insira a rua: ");
        String rua = scanner.next();
        scanner.nextLine();

        System.out.println("Insira o número: ");
        int numero = scanner.nextInt();

        System.out.println("Insira o bairro: ");
        String bairro = scanner.next();

        System.out.println("Insira a cidade: ");
        String cidade = scanner.next();

        Date data;
        while (true) {
            try {
                System.out.println("Data de contratação (dd/MM/yyyy): ");
                String hireDate = scanner.next();
                SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
                data = formato.parse(hireDate);
                break;
            } catch(ParseException e){
                System.out.println("Por favor, informe a data no formato: dd/MM/yyyy ");
            }
        }

        System.out.println("Insira o salário CLT bruto: ");
        double salario = scanner.nextDouble();

        return new Operador(nome, email, celular, password, cpf, new Endereco(), data, Cargo.OPERADOR, salario);
    }

    public static Funcionario menucadastrarChefe(Scanner scanner) throws ParseException {

        Setor setor = null;
        scanner.nextLine();
        System.out.println("Qual setor?: " +
                        "\n1 - Mercearia" +
                        "\n2 - Bazar" +
                        "\n3 - Eletronicos" +
                        "\n4 - Comercial");
        Integer opcao = scanner.nextInt();

        if (opcao == 1) {
            setor = Setor.MERCEARIA;
        } else if (opcao == 2){
            setor = Setor.BAZAR;
        } else if (opcao == 3){
            setor = Setor.ELETRONICOS;
        } else if (opcao == 4){
            setor = Setor.COMERCIAL;
        }

        System.out.println("Insira o nome: ");
        String nome = scanner.next();

        String email = null;
        boolean em = false;
        while (!em) {
            System.out.println("Insira o email: ");
            email = scanner.next();
            if (!Validacoes.isEmail(email)) {
                System.out.println("Email inválido!");
                em = false;
            } else {
                em = true;
            }
        }
        System.out.println("Insira o celular: ");
        String celular = scanner.next();

        String cpf = null;
        boolean c = false;
        while (!c) {
            System.out.println("Insira o cpf: ");
            cpf = scanner.next();
            if (!Validacoes.isCpf(cpf)) {
                System.out.println("Cpf inválido!");
                c = false;
            } else {
                c = true;
            }
        }

        Boolean p = false;
        String password = null;
        while (!p) {
            System.out.println("Insira a senha: ");
            password = scanner.next();
            if (password.length() < 8) {
                System.out.println("Digite uma senha correta(Acima ou igual a 8)");
                p = false;
            } else {
                p = true;
            }
        }

        System.out.println("Insira a rua: ");
        String rua = scanner.next();
        scanner.nextLine();

        System.out.println("Insira o número: ");
        int numero = scanner.nextInt();

        System.out.println("Insira o bairro: ");
        String bairro = scanner.next();

        System.out.println("Insira a cidade: ");
        String cidade = scanner.next();

        Date data;
        while (true) {
            try {
                System.out.println("Data de contratação (dd/MM/yyyy): ");
                String hireDate = scanner.next();
                SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
                data = formato.parse(hireDate);
                break;
            } catch(ParseException e){
                System.out.println("Por favor, informe a data no formato: dd/MM/yyyy ");
            }
        }

        System.out.println("Insira o salário CLT bruto: ");
        double salario = scanner.nextDouble();

        Endereco endereco = new Endereco(rua, numero, bairro, cidade);

        return new Funcionario(nome, email, celular, password, cpf, endereco, data, Cargo.CHEFE_SECAO, setor, salario, new ArrayList<>(), new ArrayList<>());

    }

    public static Produto cadastrarProduto(Scanner scanner) throws ParseException {
        System.out.println("Digite o nome do produto: ");
        String nome = scanner.next();

        System.out.println("Digite a quantidade: ");
        int quantidade = scanner.nextInt();

        System.out.println("Digite o preço: ");
        double preco = scanner.nextDouble();

        System.out.println("Digite o setor: " +
                "\n1-Mercearia" +
                "\n2-Bazar" +
                "\n3-Eletrônicos" +
                "\n4-Comercial");
        int opcao = scanner.nextInt();
        Setor setor = null;
        switch (opcao) {
            case 1:
                setor = Setor.MERCEARIA;
                break;
            case 2:
                setor = Setor.BAZAR;
                break;
            case 3:
                setor = Setor.ELETRONICOS;
                break;
            case 4:
                setor = Setor.COMERCIAL;
                break;
            default:
                System.out.println("Opção inválida!");
        }

        Date data;
        while (true) {
            try {
                System.out.println("Digite a data de vencimento do produto: ");
                String DataVencimento = scanner.next();
                SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
                data = formato.parse(DataVencimento);
                break;
            } catch (ParseException e) {
                System.out.println("Por favor, informe a data no formato: dd/MM/yyyy ");
            }
        }


        System.out.println("Produto cadastrado com sucesso!");

        return new Produto(nome, quantidade, preco, setor, data);

    }
    public static void demitirOperador (Scanner scanner, OperadorService operadorService){
        List<Operador> operadores = operadorService.listAll();

        System.out.println("Escolha o operador pelo CPF");

        for (Operador operador : operadores){
            System.out.println(operador.getNome() + " CPF: " + operador.getCpf());
        }
        String cpf = scanner.next();

        System.out.println(operadorService.deleteByCpf(cpf));
    }

    public static void demitirFuncionario (Scanner scanner, FuncionarioService funcionarioService){
        List<Funcionario> funcionarios = funcionarioService.listAll();

        System.out.println("Escolha o funcionário pelo CPF");

        for (Funcionario funcionario : funcionarios){
            System.out.println(funcionario.getNome() + " CPF: " + funcionario.getCpf());
        }
        String cpf = scanner.next();

        System.out.println(funcionarioService.deleteByCpf(cpf));
    }


    }



