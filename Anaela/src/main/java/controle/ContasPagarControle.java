/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controle;

import converter.ConverterGenerico;
import converter.MoneyConverter;
import entidade.BaixaContasPagar;
import entidade.PessoaJuridica;
import entidade.ContasPagar;
import facade.PessoaJuridicaFacade;
import facade.ContasPagarFacade;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
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
public class ContasPagarControle implements Serializable {
   @Inject
    private ContasPagarFacade contasPagarFacade;
   @Inject
    private PessoaJuridicaFacade pessoaJuridicaFacade;
    private ContasPagar cp;
    private ConverterGenerico pessoaJuridicaConverter;
    private BaixaContasPagar baixaContasPagar;
    private MoneyConverter moneyConverter;
    
    public void baixar(){
        if(cp.getValor()>=(cp.getTotalBaixado()+baixaContasPagar.getValor())){
        baixaContasPagar.setContasPagar(cp);
        cp.getBaixaContasPagars().add(baixaContasPagar);  
        baixaContasPagar = new BaixaContasPagar();
        }else{
            FacesContext.getCurrentInstance().addMessage(
                    null, new FacesMessage(
                            FacesMessage.SEVERITY_ERROR,
                            "O valor baixado é maior que o valor do contas a pagar!",
                            null));
        }
    }
    
    public void novaBaixa(ContasPagar cp){
        this.cp = cp;
        baixaContasPagar = new BaixaContasPagar();
    }
    
    public void removerBaixa(BaixaContasPagar b){
        cp.getBaixaContasPagars().remove(b);
    }
    
    public String getCorValor(){        
        if(Objects.equals(cp.getTotalBaixado(), cp.getValor())){
            return "green";
        }else{
            return "red";
        }
    }
    
    public MoneyConverter getMoneyConverter() {
        if(moneyConverter == null){
            moneyConverter = new MoneyConverter();
        }
        return moneyConverter;
    }

    public void setMoneyConverter(MoneyConverter moneyConverter) {
        this.moneyConverter = moneyConverter;
    }
    

    public BaixaContasPagar getBaixaContasPagar() {
        return baixaContasPagar;
    }

    public void setBaixaContasPagar(BaixaContasPagar baixaContasPagar) {
        this.baixaContasPagar = baixaContasPagar;
    }

    public ConverterGenerico getPessoaJuridicaConverter() {
        if (pessoaJuridicaConverter == null) {
            pessoaJuridicaConverter = new ConverterGenerico(pessoaJuridicaFacade);
        }
        return pessoaJuridicaConverter;
    }

    public void setPessoaJuridicaConverter(ConverterGenerico pessoaJuridicaConverter) {
        this.pessoaJuridicaConverter = pessoaJuridicaConverter;
    }

    public void novo() {
        cp = new ContasPagar();
        cp.setBaixaContasPagars(new ArrayList<BaixaContasPagar>());
    }

    public void salvar() {
        contasPagarFacade.salvar(cp);
    }

    public void excluir(ContasPagar e) {
        contasPagarFacade.excluir(e);
    }

    public void editar(ContasPagar e) {
        cp = e;
    }

    public ContasPagar getCp() {
        return cp;
    }

    public void setCp(ContasPagar cp) {
        this.cp = cp;
    }

    public List<ContasPagar> getLista() {
        return contasPagarFacade.listaTodos();
    }

    public List<PessoaJuridica> getListaPessoaJuridicasFiltrando(String filtro) {
        return pessoaJuridicaFacade.listaFiltrando(filtro, "nome");
    }

}
