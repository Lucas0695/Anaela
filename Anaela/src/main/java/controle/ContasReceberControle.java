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
import entidade.ContasReceber;
import facade.PessoaFacade;
import facade.ContasReceberFacade;
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
public class ContasReceberControle implements Serializable {
   @Inject
    private ContasReceberFacade contasReceberFacade;
   @Inject
    private PessoaFacade pessoaFacade;
    private ContasReceber cp;
    private ConverterGenerico pessoaConverter;
    private BaixaContasReceber baixaContasReceber;
    private MoneyConverter moneyConverter;
    
    public void baixar(){
        if(cp.getValor()>=(cp.getTotalBaixado()+baixaContasReceber.getValor())){
        baixaContasReceber.setContasReceber(cp);
        cp.getBaixaContasRecebers().add(baixaContasReceber);  
        baixaContasReceber = new BaixaContasReceber();
        }else{
            FacesContext.getCurrentInstance().addMessage(
                    null, new FacesMessage(
                            FacesMessage.SEVERITY_ERROR,
                            "O valor baixado é maior que o valor do contas a receber!",
                            null));
        }
    }
    
    public void novaBaixa(ContasReceber cp){
        this.cp = cp;
        baixaContasReceber = new BaixaContasReceber();
    }
    
    public void removerBaixa(BaixaContasReceber b){
        cp.getBaixaContasRecebers().remove(b);
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
    

    public BaixaContasReceber getBaixaContasReceber() {
        return baixaContasReceber;
    }

    public void setBaixaContasReceber(BaixaContasReceber baixaContasReceber) {
        this.baixaContasReceber = baixaContasReceber;
    }

    public ConverterGenerico getPessoaConverter() {
        if (pessoaConverter == null) {
            pessoaConverter = new ConverterGenerico(pessoaFacade);
        }
        return pessoaConverter;
    }

    public void setPessoaConverter(ConverterGenerico pessoaConverter) {
        this.pessoaConverter = pessoaConverter;
    }

    public void novo() {
        cp = new ContasReceber();
        cp.setBaixaContasRecebers(new ArrayList<BaixaContasReceber>());
    }

    public void salvar() {
        contasReceberFacade.salvar(cp);
    }

    public void excluir(ContasReceber e) {
        contasReceberFacade.excluir(e);
    }

    public void editar(ContasReceber e) {
        cp = e;
    }

    public ContasReceber getCp() {
        return cp;
    }

    public void setCp(ContasReceber cp) {
        this.cp = cp;
    }

    public List<ContasReceber> getLista() {
        return contasReceberFacade.listaTodos();
    }

    public List<Pessoa> getListaPessoasFiltrando(String filtro) {
        return pessoaFacade.listaFiltrando(filtro, "nome");
    }

}
