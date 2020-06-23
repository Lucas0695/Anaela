/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controle;

import entidade.Pessoa;
import entidade.PessoaFisica;
import entidade.Colaborador;
import entidade.PessoaJuridica;
import facade.PessoaFacade;
import java.io.Serializable;
import java.util.List;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author carol
 */
@Named
@SessionScoped
public class PessoaControle implements Serializable {

    @Inject
    private PessoaFacade pessoaFacade;
    private Pessoa pessoa;
    @Inject
    private PessoaFisicaControle pessoaFisicaControle;
    @Inject
    private PessoaJuridicaControle pessoaJuridicaControle;
    @Inject
    private ColaboradorControle colaboradorControle;

    public PessoaFisicaControle getPessoaFisicaControle() {
        return pessoaFisicaControle;
    }

    public void setPessoaFisicaControle(PessoaFisicaControle pessoaFisicaControle) {
        this.pessoaFisicaControle = pessoaFisicaControle;
    }

    public PessoaJuridicaControle getPessoaJuridicaControle() {
        return pessoaJuridicaControle;
    }

    public void setPessoaJuridicaControle(PessoaJuridicaControle pessoaJuridicaControle) {
        this.pessoaJuridicaControle = pessoaJuridicaControle;
    }
    

    public void excluir(Pessoa e){
        if(e instanceof PessoaFisica){
            pessoaFisicaControle.excluir((PessoaFisica)e);
        }else{
            pessoaJuridicaControle.excluir((PessoaJuridica)e);
        }
    }
    
    public String editar(Pessoa p){
        if(p instanceof PessoaFisica){
            pessoaFisicaControle.editar((PessoaFisica)p);
            return "editapessoaFisica";
        }else if(p instanceof Colaborador){
            colaboradorControle.editar((Colaborador)p);
            return "editacolaborador";
        }else{
            pessoaJuridicaControle.editar((PessoaJuridica)p);
            return "editapessoaJuridica";
        }
    }   

    public List<Pessoa> listaTodos() {
        return pessoaFacade.listaTodos();
    }

    public Pessoa getPessoa() {
        return pessoa;
    }

    public void setPessoa(Pessoa pessoa) {
        this.pessoa = pessoa;
    }

}
