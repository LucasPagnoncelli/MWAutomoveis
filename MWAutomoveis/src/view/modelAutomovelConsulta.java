package view;

import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;
import model.automovel;
import model.cliente;

public class modelAutomovelConsulta extends AbstractTableModel {

    private List<automovel> automoveis = new ArrayList<automovel>();

    @Override
    public int getRowCount() {
        return automoveis.size();
    }

    @Override
    public int getColumnCount() {
        return 6;
    }

    @Override
    public String getColumnName(int column) {
        switch (column) {
            case 0 -> {
                return "IdAutomovel";
            }

            case 1 -> {
                return "Cliente";
            }
            case 2 -> {
                return "Placa";
            }
            case 3 -> {
                return "Marca";
            }
            case 4 -> {
                return "Modelo";
            }
            case 5 -> {
                return "Ano";
            }
        }
        return "";
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        switch (columnIndex) {
            case 0 -> {
                return automoveis.get(rowIndex).getIdAutomovel();
            }
            case 1 -> {
                return automoveis.get(rowIndex).getCliente();
            }
            case 2 -> {
                return automoveis.get(rowIndex).getPlaca();
            }
            case 3 -> {
                return automoveis.get(rowIndex).getMarca();
            }
            case 4 -> {
                return automoveis.get(rowIndex).getModelo();
            }
            case 5 -> {
                return automoveis.get(rowIndex).getAno();
            }
        }
        return "";
    }

    public void adiconarAutomovel(automovel a) {
        automoveis.add(a);
        fireTableRowsInserted(automoveis.size() - 1, automoveis.size() - 1);
    }

    public void limparTabela() {
        if (!automoveis.isEmpty()) {
            fireTableRowsDeleted(0, automoveis.size() - 1);
        }
        automoveis.clear();
    }

    public automovel pegarAutomovel(int indice) {
        return automoveis.get(indice);
    }

    public void excluir(int indice) {
        automoveis.remove(indice);
        fireTableRowsDeleted(indice, indice);
    }
}
