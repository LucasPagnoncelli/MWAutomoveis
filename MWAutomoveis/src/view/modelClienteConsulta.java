package view;

import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;
import model.cliente;

public class modelClienteConsulta extends AbstractTableModel {

    private List<cliente> clientes = new ArrayList<cliente>();

    @Override
    public int getRowCount() {
        return clientes.size();
    }

    @Override
    public int getColumnCount() {
        return 5;
    }

    @Override
    public String getColumnName(int column) {
        switch (column) {
            case 0 -> {
                return "Id";
            }

            case 1 -> {
                return "Cpf";
            }
            case 2 -> {
                return "Nome";
            }
            case 3 -> {
                return "Telefone";
            }
            case 4 -> {
                return "Data De Nascimento";
            }
        }
        return "";
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        switch (columnIndex) {
            case 0 -> {
                return clientes.get(rowIndex).getId();
            }
            case 1 -> {
                return clientes.get(rowIndex).getCpf();
            }
            case 2 -> {
                return clientes.get(rowIndex).getNome();
            }
            case 3 -> {
                return clientes.get(rowIndex).getTelefone();
            }
            case 4 -> {
                return clientes.get(rowIndex).getDataDeNascimento();
            }
        }
        return "";
    }

    public void adiconarCliente(cliente c) {
        clientes.add(c);
        fireTableRowsInserted(clientes.size() - 1, clientes.size() - 1);
    }

    public void limparTabela() {
        if(!clientes.isEmpty()){
        fireTableRowsDeleted(0, clientes.size()-1);
        }
        clientes.clear();
    }

    public cliente pegarCliente(int indice) {
        return clientes.get(indice);
    }

    public void excluir(int indice) {
        clientes.remove(indice);
        fireTableRowsDeleted(indice, indice);
    }
}
