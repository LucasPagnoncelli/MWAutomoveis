package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.text.ParseException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import model.automovel;
import model.cliente;
import model.daoAutomovel;
import model.daoCliente;
import view.TelaAutomovel;
import view.TelaConsultaAutomovel;
import view.modelAutomovelConsulta;

public class controllerTelaAutomovel {

    daoAutomovel daoAutomovel;
    daoCliente daoCliente;
    TelaAutomovel telaAutomovel;
    automovel automovelSelecionado;
    TelaConsultaAutomovel telaConsultaAutomovel;
    modelAutomovelConsulta modelAutomovelConsulta;

    public controllerTelaAutomovel() throws ParseException {
        this.daoAutomovel = new daoAutomovel();
        this.telaAutomovel = new TelaAutomovel(null, true);
        this.automovelSelecionado = null;
        this.daoCliente = new daoCliente();
        this.telaConsultaAutomovel = new TelaConsultaAutomovel(null, true);
        this.modelAutomovelConsulta = new modelAutomovelConsulta();
        inicializarComponentes();
    }

    public void inicializarComponentes() throws ParseException {
        telaAutomovel.buttonCancelar.addActionListener(new ActionListener() {
            
            @Override
            public void actionPerformed(ActionEvent e) {
                limpar();
                abrirFecharTela(false);
            }
        });
        telaAutomovel.buttonCadastrar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    inserir();
                } catch (ParseException ex) {
                    Logger.getLogger(controllerTelaAutomovel.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        
        telaConsultaAutomovel.tableTabelaAutomoveis.setModel(modelAutomovelConsulta);
        
        telaConsultaAutomovel.buttonCancelar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                telaConsultaAutomovel.setVisible(false);
            }
        });
        
        telaConsultaAutomovel.buttonEditar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                editar();
            }
        });
        
        telaConsultaAutomovel.buttonExcluir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                excluir();
            }
        });
    }

    public void abrirFecharTela(boolean e) {
        if (e) {
            carregarClientes();
        }
        telaAutomovel.setVisible(e);
    }
    public void abrirFecharTelaConsulta(boolean e) {
        carregarAutomoveis();
        telaConsultaAutomovel.setVisible(e);
    }

    public void inserir() throws ParseException {
        if (automovelSelecionado == null) {
            String placa = telaAutomovel.textFieldPlaca.getText();
            String marca = telaAutomovel.textFieldMarca.getText();
            String modelo = telaAutomovel.textFieldModelo.getText();
            int ano = Integer.parseInt(telaAutomovel.textFieldAno.getText());
            cliente c = (cliente) telaAutomovel.comboBoxProprietario.getSelectedItem();
            automovel a = new automovel(c, placa, marca, modelo, ano);
            if (daoAutomovel.cadastrar(a)) {
                limpar();
                JOptionPane.showMessageDialog(null, "Cadastrado");
                abrirFecharTela(false);
            } else {
                limpar();
                JOptionPane.showMessageDialog(null, "Erro");
            }
        } else {
            String placa = telaAutomovel.textFieldPlaca.getText();
            String marca = telaAutomovel.textFieldMarca.getText();
            String modelo = telaAutomovel.textFieldModelo.getText();
            int ano = Integer.parseInt(telaAutomovel.textFieldAno.getText());
            cliente c = (cliente) telaAutomovel.comboBoxProprietario.getSelectedItem();
            automovelSelecionado.setPlaca(placa);
            automovelSelecionado.setMarca(marca);
            automovelSelecionado.setModelo(modelo);
            automovelSelecionado.setAno(ano);
            automovelSelecionado.setCliente(c);
            if (daoAutomovel.editar(automovelSelecionado)) {
                limpar();
                JOptionPane.showMessageDialog(null, "Alterado");
            } else {
                JOptionPane.showMessageDialog(null, "Erro");
            }

        }
    }
    
    public void editar () {
        int linhaselecionada = telaConsultaAutomovel.tableTabelaAutomoveis.getSelectedRow();
        if (linhaselecionada >= 0) {
            carregarClientes();
            automovelSelecionado = modelAutomovelConsulta.pegarAutomovel(linhaselecionada);
            telaAutomovel.textFieldPlaca.setText(automovelSelecionado.getPlaca());
            telaAutomovel.textFieldAno.setText(Integer.toString(automovelSelecionado.getAno()));
            telaAutomovel.textFieldMarca.setText(automovelSelecionado.getMarca());
            telaAutomovel.textFieldModelo.setText(automovelSelecionado.getModelo());
            telaConsultaAutomovel.setVisible(false);
            telaAutomovel.setVisible(true);
        }else{
            JOptionPane.showMessageDialog(null, "Selecione uma linha");
        }
    }
    
    public void excluir() {
        int linhaSelecionada = telaConsultaAutomovel.tableTabelaAutomoveis.getSelectedRow();
        if (linhaSelecionada >= 0) {
            if (JOptionPane.showConfirmDialog(null, "Realmete excluir?", "Confirmação", 0) == 0) {
                automovel a = modelAutomovelConsulta.pegarAutomovel(linhaSelecionada);
                if (daoAutomovel.excluir(a)) {
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

    public void carregarClientes() {
        telaAutomovel.comboBoxProprietario.removeAllItems();
        for (cliente c : daoCliente.listar()) {
            telaAutomovel.comboBoxProprietario.addItem(c);
        }
    }
    public void carregarAutomoveis() {
        modelAutomovelConsulta.limparTabela();
        for (automovel a : daoAutomovel.listar()) {
            modelAutomovelConsulta.adiconarAutomovel(a);
        }
    }

    public void limpar() {
        automovelSelecionado = null;
        telaAutomovel.textFieldPlaca.setText(" ");
        telaAutomovel.textFieldMarca.setText(" ");
        telaAutomovel.textFieldModelo.setText(" ");
        telaAutomovel.textFieldAno.setText(" ");
    }
}
