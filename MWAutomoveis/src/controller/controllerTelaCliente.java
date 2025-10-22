package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.MaskFormatter;
import model.cliente;
import model.daoCliente;
import view.TelaCliente;
import view.TelaConsultaCliente;
import view.modelClienteConsulta;

public class controllerTelaCliente {

    daoCliente daoCliente;
    cliente cliente;
    TelaCliente telaCliente;
    TelaConsultaCliente telaConsultaCliente;
    modelClienteConsulta modelClienteConsulta;
    cliente clienteSelecionado;

    public controllerTelaCliente() throws ParseException {
        this.telaCliente = new TelaCliente(null, true);
        this.daoCliente = new daoCliente();
        this.cliente = new cliente();
        telaConsultaCliente = new TelaConsultaCliente(null, true);
        modelClienteConsulta = new modelClienteConsulta();
        clienteSelecionado = null;
        inicializarComponentes();
    }

    public void inicializarComponentes() throws ParseException {

        telaConsultaCliente.tableTabelaConsulta.setModel(modelClienteConsulta);

        telaCliente.buttonCadastrar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    cadastrar();
                } catch (ParseException ex) {
                    Logger.getLogger(controllerTelaCliente.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });

        telaCliente.buttonCancelar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                limpar();
                abrirFecharTela(false);
            }
        });

        telaConsultaCliente.buttonSair.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                limpar();
                abrirFecharTelaConsulta(false);
            }
        });
        
        telaConsultaCliente.buttonEditar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                editar();
            }
        });

        telaCliente.formattedTextFieldDataDeNascimento.setFormatterFactory(new DefaultFormatterFactory(new MaskFormatter("##/##/####")));

        telaConsultaCliente.buttonExcluir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                excluir();
            }
        });
    }

    public void cadastrar() throws ParseException {
        if (clienteSelecionado == null) {
            String cpf = telaCliente.textFieldCpf.getText();
            String nome = telaCliente.textFieldNome.getText();
            SimpleDateFormat formatador = new SimpleDateFormat("dd/MM/yyyy");
            Date dataDeNascimento = formatador.parse(telaCliente.formattedTextFieldDataDeNascimento.getText());
            String telefone = telaCliente.textFieldTelefone.getText();
            cliente c = new cliente(cpf, nome, dataDeNascimento, telefone);
            if (daoCliente.cadastrar(c)) {
                limpar();
                JOptionPane.showMessageDialog(null, "Cadastrado");
                telaCliente.setVisible(false);
            } else {
                limpar();
                JOptionPane.showMessageDialog(null, "Erro ao cadastrar");
            }
        } else {
            String cpf = telaCliente.textFieldCpf.getText();
            String nome = telaCliente.textFieldNome.getText();
            SimpleDateFormat formatador = new SimpleDateFormat("dd/MM/yyyy");
            Date dataDeNascimento = formatador.parse(telaCliente.formattedTextFieldDataDeNascimento.getText());
            String telefone = telaCliente.textFieldTelefone.getText();
            clienteSelecionado.setCpf(cpf);
            clienteSelecionado.setDataDeNascimento(dataDeNascimento);
            clienteSelecionado.setNome(nome);
            clienteSelecionado.setTelefone(telefone);
            if (daoCliente.editar(clienteSelecionado)) {
                JOptionPane.showMessageDialog(null, "Alterado");
                carregarClientes();
                telaCliente.setVisible(false);
                limpar();
            } else {
                JOptionPane.showMessageDialog(null, "Erro");
            }
        }
    }

    public void excluir() {
        int linhaSelecionada = telaConsultaCliente.tableTabelaConsulta.getSelectedRow();
        if (linhaSelecionada >= 0) {
            if (JOptionPane.showConfirmDialog(null, "Realmete excluir?", "Confirmação", 0) == 0) {
                cliente c = modelClienteConsulta.pegarCliente(linhaSelecionada);
                if (daoCliente.excluir(c)) {
                    JOptionPane.showMessageDialog(null, "Excluído");
                    carregarClientes();
                } else {
                    JOptionPane.showMessageDialog(null, "Erro");
                }
            }
        } else {
            JOptionPane.showMessageDialog(null, "Selecione uma linha");
        }
    }

    public void editar() {
        int linhaSelecionada = telaConsultaCliente.tableTabelaConsulta.getSelectedRow();
        if (linhaSelecionada >= 0) {
            clienteSelecionado = modelClienteConsulta.pegarCliente(linhaSelecionada);
            telaCliente.textFieldNome.setText(clienteSelecionado.getNome());
            telaCliente.textFieldCpf.setText(clienteSelecionado.getCpf());
            telaCliente.textFieldTelefone.setText(clienteSelecionado.getTelefone());
            SimpleDateFormat formatador = new SimpleDateFormat("dd/MM/yyyy");
            telaCliente.formattedTextFieldDataDeNascimento.setText(formatador.format(clienteSelecionado.getDataDeNascimento()));
            telaConsultaCliente.setVisible(false);
            telaCliente.setVisible(true);
        } else {
            JOptionPane.showMessageDialog(null, "Selecione uma linha");
        }
    }

    public void abrirFecharTela(boolean e) {
        telaCliente.setVisible(e);
    }

    public void carregarClientes() {
        modelClienteConsulta.limparTabela();
        for (cliente c : daoCliente.listar()) {
            modelClienteConsulta.adiconarCliente(c);
        }
    }

    public void abrirFecharTelaConsulta(boolean e) {
        carregarClientes();
        telaConsultaCliente.setVisible(e);
    }

    public void limpar() {
        clienteSelecionado = null;
        telaCliente.textFieldCpf.setText(" ");
        telaCliente.formattedTextFieldDataDeNascimento.setText(null);
        telaCliente.textFieldNome.setText(" ");
        telaCliente.textFieldTelefone.setText(" ");
    }
}
