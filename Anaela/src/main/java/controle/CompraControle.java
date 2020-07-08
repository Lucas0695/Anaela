/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controle;

import converter.ConverterGenerico;
import converter.MoneyConverter;
import entidade.PessoaJuridica;
import entidade.Colaborador;
import entidade.ComposicaoProduto;
import entidade.ContasPagar;
import entidade.ItensCompra;
import entidade.Compra;
import entidade.Produto;
import facade.PessoaJuridicaFacade;
import facade.ColaboradorFacade;
import facade.CompraFacade;
import facade.ProdutoFacade;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.inject.Inject;
import javax.inject.Named;
import org.apache.deltaspike.core.api.scope.ViewAccessScoped;
import org.primefaces.model.DefaultScheduleModel;

/**
 *
 * @author carol
 */
@Named
@ViewAccessScoped
public class CompraControle implements Serializable {

    @Inject
    private CompraFacade compraFacade;
    private Compra compra;
    private ItensCompra itensCompra;
    @Inject
    private PessoaJuridicaFacade pessoaJuridicaFacade;
    @Inject
    private ColaboradorFacade colaboradorFacade;
    @Inject
    private ProdutoFacade produtoFacade;
    private ConverterGenerico converterPessoaJuridica;
    private ConverterGenerico converterComposicaoProduto;
    private ConverterGenerico converterProduto;
    private MoneyConverter moneyConverter;
    private Integer numParcela;
    private Date dtVencimento;
    private ContasPagar contasPagar;
    private PessoaJuridica pessoaJuridica;
    private Produto produto;
    private List<ComposicaoProduto> composicaoProduto;

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

    public PessoaJuridica getPessoaJuridica() {
        return pessoaJuridica;
    }

    public void setPessoaJuridica(PessoaJuridica pessoaJuridica) {
        this.pessoaJuridica = pessoaJuridica;
    }

    public ContasPagar getContasPagar() {
        return contasPagar;
    }

    public void setContasPagar(ContasPagar contasPagar) {
        this.contasPagar = contasPagar;
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
        Double estoque = itensCompra.getComposicaoProduto().getEstoque();
        ItensCompra itemTemp = null;
        for (ItensCompra it : compra.getItensCompra()) {
            if (it.getComposicaoProduto().equals(itensCompra.getComposicaoProduto())) {
                estoque = estoque + it.getQuantidade();
                itemTemp = it;
            }
        }
        if (itemTemp != null) {
            itemTemp.setQuantidade(itemTemp.getQuantidade() + itensCompra.getQuantidade());
        } else {
            itensCompra.setCompra(compra);
            compra.getItensCompra().add(itensCompra);
        }
        itensCompra = new ItensCompra();
    }

    public void removerItemCompra(ItensCompra it) {
        compra.getItensCompra().remove(it);
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

    public void setaValorComposicaoProduto() {
        itensCompra.setValor(itensCompra.getComposicaoProduto().getPreco());
    }

    public ItensCompra getItensCompra() {
        return itensCompra;
    }

    public void setItensCompra(ItensCompra itensCompra) {
        this.itensCompra = itensCompra;
    }

    public List<Produto> listaFiltrandoComposicaoProduto(String parte) {
        return produtoFacade.listaFiltrando(parte, "nome");
    }

    public List<PessoaJuridica> listaFiltrandoPessoaJuridica(String parte) {
        return pessoaJuridicaFacade.listaFiltrando(parte, "nome");
    }

    public List<Colaborador> listaFiltrandoColaborador(String parte) {
        return colaboradorFacade.listaFiltrando(parte, "nome");
    }

    public ConverterGenerico getConverterPessoaJuridica() {
        if (converterPessoaJuridica == null) {
            converterPessoaJuridica = new ConverterGenerico(pessoaJuridicaFacade);
        }
        return converterPessoaJuridica;
    }

    public void setConverterPessoaJuridica(ConverterGenerico converterPessoaJuridica) {
        this.converterPessoaJuridica = converterPessoaJuridica;
    }

    public void novo() {
        compra = new Compra();
        itensCompra = new ItensCompra();
    }

    public void excluir(Compra e) {
        compraFacade.excluir(e);
    }

    public void editar(Compra e) {
        this.compra = e;
    }

    public void salvar() {
        compraFacade.salvar(compra);
    }

    public List<Compra> listaTodos() {
        return compraFacade.listaTodos();
    }
    public List<Produto> listaTodosProdutos() {
        return produtoFacade.listaTodos();
    }

    public Compra getCompra() {
        return compra;
    }

    public void setCompra(Compra compra) {
        this.compra = compra;
    }

//    public void carregaComposicao() {
//
//        composicaoProduto = composicaoProduto.;
//        for (AgendamentoHorario a : agendamentos) {
//            ScheduleEventAgenda ds = new ScheduleEventAgenda(a.getDescricao() + " - Cliente: " + a.getCliente().getNome() + "   -   Colaborador : " + a.getColaborador().getNome(), a.getDataInicial(), a.getDataFinal());
//            ds.setAgendamentoHorario(a);
//            eventModel.addEvent(ds);
//        }
//    }
//    
//    public void geraParcela() {
//        compra.setContasPagars(new ArrayList<ContasPagar>());
//        Double valor = compra.getValorTotal() / numParcela;
//        Date dataVen = compra.getDataCompra();
//        for (Integer i = 1; i <= numParcela; i++) {
//
//            if (compra.getFormapag().equals("Cartão") || compra.getFormapag().equals("Cheque a Vista") || compra.getFormapag().equals("Dinheiro Avista")) {
//                ContasPagar cr = new ContasPagar();
//                cr.setDataLancamento(compra.getDataCompra());
//                cr.setParcela(i.toString() + "/" + numParcela.toString());
//                cr.setValor(valor);
//                cr.setPessoaJuridica(compra.getPessoaJuridica());
//                cr.setDataVencimento(dataVen);
//                cr.setCompra(compra);
//                cr.setFormapag(compra.getFormapag());
//
//                BaixaContasPagar b = new BaixaContasPagar();
//                b.setContasPagar(cr);
//                b.setDataBaixa(compra.getDataCompra());
//                b.setValor(valor);
//                cr.setBaixaContasPagars(new ArrayList<BaixaContasPagar>());
//                cr.getBaixaContasPagars().add(b);
//                if (compra.getPessoaJuridica() != null) {
//                    cr.setPessoaJuridica(compra.getPessoaJuridica());
//                }
//                compra.getContasPagars().add(cr);
//                //Soma 1 mês no compracimento
//                Calendar cal = Calendar.getInstance();
//                cal.setTime(dataVen);
//                cal.add(Calendar.MONTH, 1);
//                dataVen = cal.getTime();
//
//            } else {
//                ContasPagar cr = new ContasPagar();
//                cr.setDataLancamento(compra.getDataCompra());
//                cr.setParcela(i.toString() + "/" + numParcela.toString());
//                cr.setValor(valor);
//                cr.setPessoaJuridica(compra.getPessoaJuridica());
//                cr.setDataVencimento(dataVen);
//                cr.setCompra(compra);
//                cr.setFormapag(compra.getFormapag());
//
//                if (compra.getPessoaJuridica() != null) {
//                    cr.setPessoaJuridica(compra.getPessoaJuridica());
//                }
//                compra.getContasPagars().add(cr);
//                //Soma 1 mês no compracimento
//                Calendar cal = Calendar.getInstance();
//                cal.setTime(dataVen);
//                cal.add(Calendar.MONTH, 1);
//                dataVen = cal.getTime();
//            }
//        }
//    }
}
