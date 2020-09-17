/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controle;

import converter.ConverterGenerico;
import converter.MoneyConverter;
import entidade.Cor;
import entidade.GrupoProduto;
import entidade.ComposicaoProduto;
import entidade.Marca;
import entidade.Produto;
import entidade.Tamanho;
import facade.CorFacade;
import facade.GrupoProdutoFacade;
import facade.MarcaFacade;
import facade.ProdutoFacade;
import facade.TamanhoFacade;
import java.io.Serializable;
import java.util.List;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author carol
 */
@Named
@SessionScoped
public class ProdutoControle implements Serializable {

    @Inject
    private ProdutoFacade produtoFacade;
    @Inject
    private MarcaFacade marcaFacade;
    @Inject
    private GrupoProdutoFacade grupoProdutoFacade;
    @Inject
    private CorFacade corFacade;
    @Inject
    private TamanhoFacade tamanhoFacade;
    private Produto produto;
    private Cor cor;
    private Tamanho tamanho;
    private MoneyConverter moneyConverter;
    private ConverterGenerico marcaConverter;
    private ConverterGenerico grupoProduroConverter;
    private ConverterGenerico corConverter;
    private ConverterGenerico tamanhoConverter;
    private ComposicaoProduto composicaoProduto = new ComposicaoProduto();

    public void setCalculaPercentual() {
        Double precComp = composicaoProduto.getPrecoCompra();
        Double per = composicaoProduto.getPercentual() + 100;
        Double PrecVenda = (precComp * per) / 100;
        composicaoProduto.setPrecoVenda(PrecVenda);
    }

    public void setCalculaPercentualValor() {
        Double precComp = composicaoProduto.getPrecoCompra();
        Double precVenda = composicaoProduto.getPrecoVenda();
        Double p1 = precVenda - precComp;
        Double p2 = p1 / precComp;
        Double perc = p2 * 100;
        composicaoProduto.setPercentual(perc);
    }

    public ConverterGenerico getTamanoConverter() {
        if (tamanhoConverter == null) {
            tamanhoConverter = new ConverterGenerico(tamanhoFacade);
        }
        return tamanhoConverter;
    }

    public void setTamanhoConverter(ConverterGenerico tamanhoConverter) {
        this.tamanhoConverter = tamanhoConverter;
    }

    public ConverterGenerico getCorConverter() {
        if (corConverter == null) {
            corConverter = new ConverterGenerico(corFacade);
        }
        return corConverter;
    }

    public void setCorConverter(ConverterGenerico corConverter) {
        this.corConverter = corConverter;
    }

    public ConverterGenerico getMarcaConverter() {
        if (marcaConverter == null) {
            marcaConverter = new ConverterGenerico(marcaFacade);
        }
        return marcaConverter;
    }

    public void setMarcaConverter(ConverterGenerico marcaConverter) {
        this.marcaConverter = marcaConverter;
    }

    public ConverterGenerico getGrupoProdutoConverter() {
        if (grupoProduroConverter == null) {
            grupoProduroConverter = new ConverterGenerico(grupoProdutoFacade);
        }
        return grupoProduroConverter;
    }

    public void setGrupoProdutoConverter(ConverterGenerico grupoProduroConverter) {
        this.grupoProduroConverter = grupoProduroConverter;
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

    public ComposicaoProduto getComposicaoProduto() {
        return composicaoProduto;
    }

    public void setComposicaoProduto(ComposicaoProduto composicaoProduto) {
        this.composicaoProduto = composicaoProduto;
    }

    public Tamanho getTamanho() {
        return tamanho;
    }

    public void setTamanho(Tamanho tamanho) {
        this.tamanho = tamanho;
    }

    public void novo() {
        produto = new Produto();
        composicaoProduto = new ComposicaoProduto();
    }

    public void excluir(Produto e) {
        produtoFacade.excluir(e);
    }

    public void editar(Produto e) {
        this.produto = e;
    }

    public void salvar() {
        produtoFacade.salvar(produto);
    }

    public List<Produto> listaTodos() {
        return produtoFacade.listaTodos();
    }

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    public Cor getCor() {
        return cor;
    }

    public void setCor(Cor cor) {
        this.cor = cor;
    }

// Metodo de adicionar    
    public void addEstoque() {
//        ComposicaoProduto itemTemp = null;
//        Cor cores = null;
//        Tamanho tamanhos = null;
//        for (ComposicaoProduto it : produto.getComposicaoProduto()) {
//            cores = it.getCor();
//            tamanhos = it.getTamanho();
//            if (it.getCor().equals(cores) || it.getTamanho().equals(tamanhos)) {
//                FacesContext.getCurrentInstance().addMessage(
//                        null, new FacesMessage(
//                                FacesMessage.SEVERITY_ERROR,
//                                "Produto já existente na base de dados",
//                                "se deseja alterar o estoque faça pelo movimento de ajuste de estoque"));
//  
//            }
//            }
        if(composicaoProduto.getPercentual()!= null){
              produto.getComposicaoProduto().add(composicaoProduto);
        composicaoProduto.setProduto(produto);
        composicaoProduto = new ComposicaoProduto();
        }else{
            
        setCalculaPercentualValor();
        produto.getComposicaoProduto().add(composicaoProduto);
        composicaoProduto.setProduto(produto);
        composicaoProduto = new ComposicaoProduto();
    }
    }

    public void removerItemEstoque(ComposicaoProduto it) {
//        diminuiEstoque();
        produto.getComposicaoProduto().remove(it);
    }

    public List<GrupoProduto> listaFiltrandoGrupoProduto(String parte) {
        return grupoProdutoFacade.listaFiltrando(parte, "nome");
    }

    public List<Marca> listaFiltrandoMarca(String parte) {
        return marcaFacade.listaFiltrando(parte, "nome");
    }

    public List<Cor> listaFiltrandoCor(String parte) {
        return corFacade.listaFiltrando(parte, "nome");
    }

    public List<Tamanho> listaFiltrandoTamanho(String parte) {
        return tamanhoFacade.listaFiltrando(parte, "nome");
    }

}
