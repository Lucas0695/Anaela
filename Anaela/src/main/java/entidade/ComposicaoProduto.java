/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entidade;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

/**
 *
 * @author sistemas
 */
@Entity
public class ComposicaoProduto implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Double estoque = 0.0 ;
    private Double precoCompra;
    private Double precoVenda;
    private Double percentual;
    private String codBarras;
    @ManyToOne
    private Cor cor;
    @ManyToOne
    private Produto produto;
    @ManyToOne
    private Tamanho tamanho;
    
    public Double getSubtotal(){
        return precoCompra * estoque;
    }

    public String getCodBarras() {
        return codBarras;
    }

    public void setCodBarras(String codBarras) {
        this.codBarras = codBarras;
    }
    
    

    public Double getPercentual() {
        return percentual;
    }

    public void setPercentual(Double percentual) {
        this.percentual = percentual;
    }
    
    

    public Double getPrecoVenda() {
        return precoVenda;
    }

    public void setPrecoVenda(Double precoVenda) {
        this.precoVenda = precoVenda;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getEstoque() {
        return estoque;
    }

    public void setEstoque(Double estoque) {
        this.estoque = estoque;
    }

    public Double getPrecoCompra() {
        return precoCompra;
    }

    public void setPrecoCompra(Double precoCompra) {
        this.precoCompra = precoCompra;
    }

    public Cor getCor() {
        return cor;
    }

    public void setCor(Cor cor) {
        this.cor = cor;
    }

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    public Tamanho getTamanho() {
        return tamanho;
    }

    public void setTamanho(Tamanho tamanho) {
        this.tamanho = tamanho;
    }
    
   


    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ComposicaoProduto)) {
            return false;
        }
        ComposicaoProduto other = (ComposicaoProduto) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidade.ComposicaoProduto[ id=" + id + " ]";
    }

}
