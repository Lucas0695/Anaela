/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entidade;

/**
 *
 * @author lucas
 */
public enum StatusVenda {
    
    CONDICIONAL("Condicional"),
    FATURADA("Faturada"),
    ORCAMENTO("Orcamento");
    
    private final String descricao;

    private StatusVenda(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
    
}
