/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entidade;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import org.hibernate.Hibernate;

/**
 *
 * @author sistemas
 */
@Entity
public class Produto implements Serializable, EntidadePai {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(length = 255, nullable = false)
    private String nome;
    private Double estoqueTotal;
    @ManyToOne
    private GrupoProduto grupoProduto;
    @ManyToOne
    private Marca marca;
    @OneToMany(cascade = CascadeType.ALL,
            fetch = FetchType.LAZY,
            mappedBy = "produto")
    private List<ComposicaoProduto> composicaoProduto;

    public Produto() {
        this.composicaoProduto = new ArrayList<ComposicaoProduto>();
        Hibernate.initialize(composicaoProduto);
    }

    public List<ComposicaoProduto> getComposicaoProduto() {
        return composicaoProduto;
    }

    public void setComposicaoProduto(List<ComposicaoProduto> composicaoProduto) {
        this.composicaoProduto = composicaoProduto;
    }

    @Override
    public Long getId() {
        return id;
    }
   
    public void setId(Long id) {
        this.id = id;
    }

    public Double getEstoqueTotal() {
        return estoqueTotal;
    }

    public void setEstoqueTotal(Double estoqueTotal) {
        this.estoqueTotal = estoqueTotal;
    }

    
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public GrupoProduto getGrupoProduto() {
        return grupoProduto;
    }

    public void setGrupoProduto(GrupoProduto grupoProduto) {
        this.grupoProduto = grupoProduto;
    }

    public Marca getMarca() {
        return marca;
    }

    public void setMarca(Marca marca) {
        this.marca = marca;
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
        if (!(object instanceof Produto)) {
            return false;
        }
        Produto other = (Produto) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidade.Produto[ id=" + id + " ]";
    }

}
