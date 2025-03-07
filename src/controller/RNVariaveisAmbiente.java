/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.security.Timestamp;
import java.sql.Connection;
import java.util.ArrayList;
import model.VariaveisAmbienteDao;
import model.VariaveisAmbiente;
import view.DialogTelaVariaveisAmbiente;
/**
 *
 * @author banshee
 */
public class RNVariaveisAmbiente {
    private DialogTelaVariaveisAmbiente telaVariaveisAmbiente;
    private Connection conexao;
    
    private VariaveisAmbienteDao dao;
    private Runnable handleVariaveis;

    public RNVariaveisAmbiente(DialogTelaVariaveisAmbiente telaVariaveisAmbiente, Connection conexao) {
        this.telaVariaveisAmbiente = telaVariaveisAmbiente;
        this.conexao = conexao;
        this.dao = new VariaveisAmbienteDao(this.conexao);
        
        this.handleVariaveis = new Runnable() {
            @Override
            public void run() {
                try {
                    while(true) {
                        pesquisaInsereTabela();
                        Thread.sleep(2000);
                    }
                } catch (Exception e) {}
            }
        };
        
        // Teste da implementação de Threads
        new Thread(handleVariaveis).start();
    }
    
    private void listar() {
        listaDados(dao.listar());
    }
    
    
    private void listaDados(ArrayList<VariaveisAmbiente> listaVA) {
     //limpaTabela();
     for(int i=0;i<listaVA.size();i++){
            adicionaTabela(listaVA.get(i).getDate(),
                           listaVA.get(i).getTemperatura(),
                           listaVA.get(i).getUmidade(),
                           listaVA.get(i).getLuminosidade(),
                           listaVA.get(i).getPh(),
                           listaVA.get(i).getOxigenioDissolvido(),
                           listaVA.get(i).getCondutividadeEletrica());
        }      
    }
    
    private void adicionaTabela(Object... objects){
        this.telaVariaveisAmbiente.getModelo().addRow(objects);
    }
    
    private void limpaTabela(){
        int linhas = this.telaVariaveisAmbiente.getModelo().getRowCount();
        for(int i=0;i<linhas;i++){
            this.telaVariaveisAmbiente.getModelo().removeRow(0);
        }
    }
    
    private void pesquisaInsereTabela() {
        
        ArrayList<VariaveisAmbiente> va = this.dao.listar();
        
        if (this.telaVariaveisAmbiente.getModelo().getRowCount() == 0 || !this.telaVariaveisAmbiente.getModelo().getValueAt(this.telaVariaveisAmbiente.getModelo().getRowCount() - 1, 0).equals(va.get(0).getDate())) {
            listaDados(va);
        }
    }
    
}
