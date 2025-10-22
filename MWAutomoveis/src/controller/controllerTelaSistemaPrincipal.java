package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import view.TelaCliente;
import view.TelaRevisao;
import view.TelaSistemaPrincipal;

public class controllerTelaSistemaPrincipal {
    private TelaSistemaPrincipal telaSistemaPrincipal;
    private controllerTelaCliente controllerCliente;
    private TelaCliente telaCliente;
    private controllerTelaAutomovel controllerTelaAutomovel;
    private controllerTelaRevisao controllerTelaRevisao;

    public controllerTelaSistemaPrincipal() throws ParseException {
        this.telaSistemaPrincipal = new TelaSistemaPrincipal();
        this.controllerCliente = new controllerTelaCliente();
        this.controllerTelaAutomovel = new controllerTelaAutomovel();
        this.telaCliente = new TelaCliente(null, true);
        this.controllerTelaRevisao = new controllerTelaRevisao();
        inicalizarComponentes();
    }
    
    public void executar() {
        telaSistemaPrincipal.setVisible(true);
    }

    public void inicalizarComponentes(){
        telaSistemaPrincipal.menuItemCadastroCliente.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controllerCliente.abrirFecharTela(true);
            }
        });
        
        telaSistemaPrincipal.buttonSair.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                telaSistemaPrincipal.dispose();
                System.exit(0);
            }
        });
        
        telaSistemaPrincipal.menuItemConsultaCliente.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controllerCliente.abrirFecharTelaConsulta(true);
            }
        });
        
        telaSistemaPrincipal.menuItemCadastroAutomovel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                 controllerTelaAutomovel.abrirFecharTela(true);
            }
        });
        
        telaSistemaPrincipal.menuItemConsultaAutomoveis.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controllerTelaAutomovel.abrirFecharTelaConsulta(true);
            }
        });
        
        telaSistemaPrincipal.menuItemCadastroRevisao.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controllerTelaRevisao.abrirFecharTela(true);
            }
        });
        
        telaSistemaPrincipal.menuItemConsultaRevisao.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controllerTelaRevisao.abrirFecharTelaConsulta(true);
            }
        });
    }
}
