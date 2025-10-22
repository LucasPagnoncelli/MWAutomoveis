package view;

import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;
import model.cliente;
import model.revisao;

public class modelRevisaoConsulta extends AbstractTableModel{

    private List<revisao> revisoes = new ArrayList<revisao>();

    @Override
    public int getRowCount() {
        return revisoes.size();
    }

    @Override
    public int getColumnCount() {
        return 5;
    }

    @Override
    public String getColumnName(int column) {
        switch (column) {
            case 0 -> {
                return "IdRevisao";
            }

            case 1 -> {
                return "Data";
            }
            case 2 -> {
                return "Km";
            }
            case 3 -> {
                return "Serviços Realizados";
            }
            case 4 -> {
                return "idAutomovel";
            }
        }
        return "";
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        switch (columnIndex) {
            case 0 -> {
                return revisoes.get(rowIndex).getIdRevisao();
            }
            case 1 -> {
                return revisoes.get(rowIndex).getData();
            }
            case 2 -> {
                return revisoes.get(rowIndex).getKm();
            }
            case 3 -> {
                return revisoes.get(rowIndex).getServicosRealizados();
            }
            case 4 -> {
                return revisoes.get(rowIndex).getAutomovel();
            }
        }
        return "";
    }

    public void adiconarRevisao(revisao r) {
        revisoes.add(r);
        fireTableRowsInserted(revisoes.size() - 1, revisoes.size() - 1);
    }

    public void limparTabela() {
        if(!revisoes.isEmpty()){
        fireTableRowsDeleted(0, revisoes.size()-1);
        }
        revisoes.clear();
    }

    public revisao pegarRevisao(int indice) {
        return revisoes.get(indice);
    }

    public void excluir(int indice) {
        revisoes.remove(indice);
        fireTableRowsDeleted(indice, indice);
    }
    
}
