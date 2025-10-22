package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.MaskFormatter;
import model.automovel;
import model.cliente;
import model.daoAutomovel;
import model.daoCliente;
import model.daoRevisao;
import model.revisao;
import view.TelaConsultaRevisao;
import view.TelaRevisao;
import view.modelRevisaoConsulta;

public class controllerTelaRevisao {

    private daoRevisao daoRevisao;
    private TelaRevisao telaRevisao;
    private daoCliente daoCliente;
    private daoAutomovel daoAutomovel;
    private TelaConsultaRevisao telaConsultaRevisao;
    private modelRevisaoConsulta modelRevisaoConsulta;
    private automovel a;

    public controllerTelaRevisao() throws ParseException {
        this.daoRevisao = new daoRevisao();
        this.telaRevisao = new TelaRevisao(null, true);
        this.daoCliente = new daoCliente();
        this.daoAutomovel = new daoAutomovel();
        this.telaConsultaRevisao = new TelaConsultaRevisao(null, true);
        this.modelRevisaoConsulta = new modelRevisaoConsulta();
        this.a = new automovel();
        inicializarComponenetes();
    }

    public void inicializarComponenetes() throws ParseException {
        telaRevisao.FormattedTextFieldData.setFormatterFactory(new DefaultFormatterFactory(new MaskFormatter("##/##/####")));
        telaRevisao.buttonCancelar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                abrirFecharTela(false);
            }
        });

        telaRevisao.comboBoxClientes.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {

            }

            @Override
            public void focusLost(FocusEvent e) {
                carregarAutomoveis();
            }
        });

        telaRevisao.buttonCadastrar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    inserir();
                    abrirFecharTela(false);
                } catch (ParseException ex) {
                    Logger.getLogger(controllerTelaRevisao.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });

        telaConsultaRevisao.tableRevisao.setModel(modelRevisaoConsulta);

        telaConsultaRevisao.buttonSair.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                abrirFecharTelaConsulta(false);
            }
        });

        telaConsultaRevisao.buttonPesquisar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                carregarRevisoes();
            }
        });
    }

    public void inserir() throws ParseException {
        SimpleDateFormat formatador = new SimpleDateFormat("dd/mm/yyyy");
        Date data = formatador.parse(telaRevisao.FormattedTextFieldData.getText());
        double km = Double.parseDouble(telaRevisao.textFieldKm.getText());
        String servicosRealizados = telaRevisao.textFieldServicoRealizado.getText();
        automovel a = (automovel) telaRevisao.comboBoxAutomoveis.getSelectedItem();
        revisao r = new revisao(data, km, servicosRealizados, a);
        revisao ult = daoRevisao.pegarUltimaRevisao(a);

        if (ult != null) {
            long datault = ult.getData().getTime();
            long datarevisao = r.getData().getTime();
            double dataf = (((datarevisao - datault) / 3600000) / 24);

            if (r.getKm() - ult.getKm() > 10000) {
                JOptionPane.showMessageDialog(null, "Kilometragem ultrapassada");
            }
            if (dataf >= 365) {
                JOptionPane.showMessageDialog(null, "Atrasado");
            }
        }
        if (daoRevisao.cadastrar(r)) {
            JOptionPane.showMessageDialog(null, "Cadastrado");
        } else {
            JOptionPane.showMessageDialog(null, "Erro");
        }
    }

    public void carregarClientes() {
        telaRevisao.comboBoxClientes.removeAllItems();
        for (cliente c : daoCliente.listar()) {
            telaRevisao.comboBoxClientes.addItem(c);
        }
    }

    public void carregarAutomoveis() {
        telaRevisao.comboBoxAutomoveis.removeAllItems();
        cliente c = (cliente) telaRevisao.comboBoxClientes.getSelectedItem();
        for (automovel automovel : daoAutomovel.listarAutomoveisDeCliente(c)) {
            telaRevisao.comboBoxAutomoveis.addItem(automovel);
        }
    }

    public void carregarAutomoveisConsulta() {
        telaConsultaRevisao.comboBoxAutomoveis.removeAllItems();
        for (automovel a : daoAutomovel.listar()) {
            telaConsultaRevisao.comboBoxAutomoveis.addItem(a);
        }
    }

    public void abrirFecharTela(boolean e) {
        carregarClientes();
        telaRevisao.setVisible(e);
    }

    public void abrirFecharTelaConsulta(boolean e) {
        carregarAutomoveisConsulta();
        limpar();
        telaConsultaRevisao.setVisible(e);
    }

    public void carregarRevisoes() {
        modelRevisaoConsulta.limparTabela();
        a = (automovel) telaConsultaRevisao.comboBoxAutomoveis.getSelectedItem();
        for (revisao r : daoRevisao.pegarRevisaoAutomovel(a)) {
            modelRevisaoConsulta.adiconarRevisao(r);
        }
    }

    public void limpar() {
        telaRevisao.textFieldKm.setText("");
        telaRevisao.textFieldServicoRealizado.setText("");
        telaRevisao.FormattedTextFieldData.setText("");
    }

}
