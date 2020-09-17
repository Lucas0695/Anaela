/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controle;

import converter.ConverterGenerico;
import converter.MoneyConverter;
import entidade.BaixaContasReceber;
import entidade.Pessoa;
import entidade.Colaborador;
import entidade.ComposicaoProduto;
import entidade.ContasReceber;
import entidade.ItensAjusteEstoque;
import entidade.AjusteEstoque;
import entidade.Produto;
import facade.PessoaFacade;
import facade.ColaboradorFacade;
import facade.AjusteEstoqueFacade;
import facade.ProdutoFacade;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import org.apache.deltaspike.core.api.scope.ViewAccessScoped;

/**
 *
 * @author carol
 */
@Named
@ViewAccessScoped
public class AjusteEstoqueControle implements Serializable {

    @Inject
    private AjusteEstoqueFacade ajusteEstoqueFacade;
    private AjusteEstoque ajusteEstoque;
    private ItensAjusteEstoque itensAjusteEstoque;
    @Inject
    private ColaboradorFacade colaboradorFacade;
    @Inject
    private ProdutoFacade produtoFacade;
    private ConverterGenerico converterColaborador;
    private ConverterGenerico converterComposicaoProduto;
    private ConverterGenerico converterProduto;
    private MoneyConverter moneyConverter;
    private Produto produto;
    private List<ComposicaoProduto> composicaoProduto;

    
    public void setarComposicao(ComposicaoProduto cp) {
            itensAjusteEstoque.setComposicaoProduto(cp);
//            itensAjusteEstoque.setEstoqueAntual(itensAjusteEstoque.getEstoqueAntual());
//            itensAjusteEstoque.setEstoqueAnterior(cp.getEstoque());
    }

    
    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    public List<ComposicaoProduto> getComposicaoProduto() {
        return composicaoProduto;
    }

    public void setComposicaoProduto(List<ComposicaoProduto> composicaoProduto) {
        this.composicaoProduto = composicaoProduto;
    }

    public ConverterGenerico getConverterComposicaoProduto() {
        if (converterComposicaoProduto == null) {
            converterComposicaoProduto = new ConverterGenerico(produtoFacade);
        }
        return converterComposicaoProduto;
    }

    public void setConverterComposicaoProduto(ConverterGenerico converterComposicaoProduto) {
        this.converterComposicaoProduto = converterComposicaoProduto;
    }

    public ConverterGenerico getConverterProduto() {
        if (converterProduto == null) {
            converterProduto = new ConverterGenerico(produtoFacade);
        }
        return converterProduto;
    }

    public void setConverterProduto(ConverterGenerico converterProduto) {
        this.converterProduto = converterProduto;
    }

    public void addItemComposicaoProduto() {

        {
            itensAjusteEstoque.setAjusteEstoque(ajusteEstoque);
            ajusteEstoque.getItensAjusteEstoque().add(itensAjusteEstoque);
        }
                
                itensAjusteEstoque = new ItensAjusteEstoque();
        
    }

    public void removerItemAjusteEstoque(ItensAjusteEstoque it) {
        ajusteEstoque.getItensAjusteEstoque().remove(it);
    }

    public MoneyConverter getMoneyConverter() {
        if (moneyConverter == null) {
            moneyConverter = new MoneyConverter();
        }
        return moneyConverter;
    }

    public void setMoneyConverter(MoneyConverter moneyConverter) {
        this.moneyConverter = moneyConverter;
    }

    public ItensAjusteEstoque getItensAjusteEstoque() {
        return itensAjusteEstoque;
    }

    public void setItensAjusteEstoque(ItensAjusteEstoque itensAjusteEstoque) {
        this.itensAjusteEstoque = itensAjusteEstoque;
    }

    public List<Produto> listaFiltrandoComposicaoProduto(String parte) {
        return produtoFacade.listaFiltrando(parte, "nome");
    }
    
    public List<Colaborador> listaFiltrandoColaborador(String parte) {
        return colaboradorFacade.listaFiltrando(parte, "nome");
    }

    public ConverterGenerico getConverterColaborador() {
        if (converterColaborador == null) {
            converterColaborador = new ConverterGenerico(colaboradorFacade);
        }
        return converterColaborador;
    }

    public void setConverterColaborador(ConverterGenerico converterColaborador) {
        this.converterColaborador = converterColaborador;
    }

    public void novo() {
        ajusteEstoque = new AjusteEstoque();
        itensAjusteEstoque = new ItensAjusteEstoque();
    }

    public void excluir(AjusteEstoque e) {
        ajusteEstoqueFacade.excluir(e);
    }

    public void editar(AjusteEstoque e) {
        this.ajusteEstoque = e;
    }
    
    public void salvar() {
        ajusteEstoqueFacade.salvar(ajusteEstoque);
    }

    public List<AjusteEstoque> listaTodos() {
        return ajusteEstoqueFacade.listaTodos();
    }

    public List<Produto> listaTodosProdutos() {
        return produtoFacade.listaTodos();
    }

    public AjusteEstoque getAjusteEstoque() {
        return ajusteEstoque;
    }

    public void setAjusteEstoque(AjusteEstoque ajusteEstoque) {
        this.ajusteEstoque = ajusteEstoque;
    }

    
}
