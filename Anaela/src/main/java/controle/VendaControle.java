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
import entidade.ItensVenda;
import entidade.Venda;
import entidade.Produto;
import facade.PessoaFacade;
import facade.ColaboradorFacade;
import facade.VendaFacade;
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
import org.hibernate.Hibernate;

/**
 *
 * @author carol
 */
@Named
@ViewAccessScoped
public class VendaControle implements Serializable {

    @Inject
    private VendaFacade vendaFacade;
    private Venda venda;
    private ItensVenda itensVenda;
    @Inject
    private PessoaFacade pessoaFacade;
    @Inject
    private ColaboradorFacade colaboradorFacade;
    @Inject
    private ProdutoFacade produtoFacade;
    private ConverterGenerico converterPessoa;
    private ConverterGenerico converterColaborador;
    private ConverterGenerico converterComposicaoProduto;
    private ConverterGenerico converterProduto;
    private MoneyConverter moneyConverter;
    private Integer numParcela;
    private Date dtVencimento;
    private ContasReceber contasReceber;
    private Pessoa pessoa;
    private Produto produto;
    private String teste = "old";
    private List<ComposicaoProduto> composicaoProduto;

    public String getSituacao() {
        return "Baixado";
    }

    public Boolean isSituacao() {
        if (venda.getStatusVenda().getDescricao().equals("Faturada")) {
            return true;
        }
        return false;
    }

    public void setarComposicao(ComposicaoProduto cp) {
//        if (itensVenda.getQuantidade().equals(0d)) {
//            FacesContext.getCurrentInstance().addMessage(
//                    null, new FacesMessage(
//                            FacesMessage.SEVERITY_ERROR,
//                            "Obrigatório adicionar a quantidade de produto",
//                            ""));
//        } else {

        itensVenda.setComposicaoProduto(cp);
        itensVenda.setValor(cp.getPrecoVenda());
        setTeste(cp.toString());
//        }
    }

    public String getTeste() {
        return teste;
    }

    public void setTeste(String teste) {
        this.teste = teste;
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

    public Pessoa getPessoa() {
        return pessoa;
    }

    public void setPessoa(Pessoa pessoa) {
        this.pessoa = pessoa;
    }

    public ContasReceber getContasReceber() {
        return contasReceber;
    }

    public void setContasReceber(ContasReceber contasReceber) {
        this.contasReceber = contasReceber;
    }

    public Integer getNumParcela() {
        return numParcela;
    }

    public void setNumParcela(Integer numParcela) {
        this.numParcela = numParcela;
    }

    public Date getDtVencimento() {
        return dtVencimento;
    }

    public void setDtVencimento(Date dtVencimento) {
        this.dtVencimento = dtVencimento;
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
        if (itensVenda.getQuantidade().equals(0d)) {
            FacesContext.getCurrentInstance().addMessage(
                    null, new FacesMessage(
                            FacesMessage.SEVERITY_ERROR,
                            "Obrigatório adicionar a quantidade de produto",
                            ""));
        } else {
            if (itensVenda.getQuantidade() > itensVenda.getComposicaoProduto().getEstoque()) {
                FacesContext.getCurrentInstance().addMessage(
                        null, new FacesMessage(
                                FacesMessage.SEVERITY_ERROR,
                                "A quantidade inicada é maior que o estoque atual: ",
                                "" + itensVenda.getComposicaoProduto().getEstoque()));
            } else {

                Double estoque = itensVenda.getComposicaoProduto().getEstoque();
                ItensVenda itemTemp = null;
                for (ItensVenda it : venda.getItensVenda()) {
                    if (it.getComposicaoProduto().equals(itensVenda.getComposicaoProduto())) {
                        estoque = estoque - it.getQuantidade();
                        itemTemp = it;
                    }
                }
                if (itemTemp != null) {
                    itemTemp.setQuantidade(itemTemp.getQuantidade() + itensVenda.getQuantidade());
                } else {
                    itensVenda.setVenda(venda);
                    venda.getItensVenda().add(itensVenda);
                }
                itensVenda = new ItensVenda();
                setTeste("old");
            }
        }

    }

    public void removerItemVenda(ItensVenda it) {
        venda.getItensVenda().remove(it);
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

    public ItensVenda getItensVenda() {
        return itensVenda;
    }

    public void setItensVenda(ItensVenda itensVenda) {
        this.itensVenda = itensVenda;
    }

    public List<Produto> listaFiltrandoComposicaoProduto(String parte) {
        return produtoFacade.listaFiltrando(parte, "nome");
    }

    public List<Pessoa> listaFiltrandoPessoa(String parte) {
        return pessoaFacade.listaFiltrando(parte, "nome");
    }

    public List<Colaborador> listaFiltrandoColaborador(String parte) {
        return colaboradorFacade.listaFiltrando(parte, "nome");
    }

    public ConverterGenerico getConverterPessoa() {
        if (converterPessoa == null) {
            converterPessoa = new ConverterGenerico(pessoaFacade);
        }
        return converterPessoa;
    }

    public void setConverterPessoa(ConverterGenerico converterPessoa) {
        this.converterPessoa = converterPessoa;
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
        venda = new Venda();
        itensVenda = new ItensVenda();
    }

    public void excluir(Venda e) {
        vendaFacade.excluir(e);
    }

    public void editar(Venda e) {
        this.venda = e;
    }

    public void salvar() {
        vendaFacade.salvar(venda);
    }

    public List<Venda> listaTodos() {
        return vendaFacade.listaTodos();
    }

    public List<Produto> listaTodosProdutos() {
        return produtoFacade.listaTodos();
    }

    public Venda getVenda() {
        return venda;
    }

    public void setVenda(Venda venda) {
        this.venda = venda;
    }

    public void geraParcela() {
        venda.setContasRecebers(new ArrayList<ContasReceber>());
        Double valor = venda.getValorTotal() / numParcela;
        Date dataVen = venda.getDataVenda();
        for (Integer i = 1; i <= numParcela; i++) {

            if (venda.getFormaPag().equals("Cartão Avista") || venda.getFormaPag().equals("Cartão Parcelado") || venda.getFormaPag().equals("Cheque a Vista") || venda.getFormaPag().equals("Dinheiro Avista")) {
                ContasReceber cr = new ContasReceber();
                cr.setDataLancamento(venda.getDataVenda());
                cr.setParcela(i.toString() + "/" + numParcela.toString());
                cr.setValor(valor);
                cr.setPessoa(venda.getPessoa());
                cr.setDataVencimento(dataVen);
                cr.setVenda(venda);
                cr.setFormapag(venda.getFormaPag());

                BaixaContasReceber b = new BaixaContasReceber();
                b.setContasReceber(cr);
                b.setDataBaixa(venda.getDataVenda());
                b.setValor(valor);
                cr.setBaixaContasRecebers(new ArrayList<BaixaContasReceber>());
                cr.getBaixaContasRecebers().add(b);
                if (venda.getPessoa() != null) {
                    cr.setPessoa(venda.getPessoa());
                }
                venda.getContasRecebers().add(cr);
                //Soma 1 mês no vendacimento
                Calendar cal = Calendar.getInstance();
                cal.setTime(dataVen);
                cal.add(Calendar.MONTH, 1);
                dataVen = cal.getTime();

            } else {
                ContasReceber cr = new ContasReceber();
                cr.setDataLancamento(venda.getDataVenda());
                cr.setParcela(i.toString() + "/" + numParcela.toString());
                cr.setValor(valor);
                cr.setPessoa(venda.getPessoa());
                cr.setDataVencimento(dataVen);
                cr.setVenda(venda);
                cr.setFormapag(venda.getFormaPag());

                if (venda.getPessoa() != null) {
                    cr.setPessoa(venda.getPessoa());
                }
                venda.getContasRecebers().add(cr);
                //Soma 1 mês no vendacimento
                Calendar cal = Calendar.getInstance();
                cal.setTime(dataVen);
                cal.add(Calendar.MONTH, 1);
                dataVen = cal.getTime();
            }
        }
    }
}
