/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controle;

import entidade.Marca;
import facade.MarcaFacade;
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
public class MarcaControle implements Serializable {

    @Inject
    private MarcaFacade marcaFacade;
    private Marca marca;

    public void novo(){
        marca = new Marca();
    }
    
    public void excluir(Marca m){
        marcaFacade.excluir(m);
    }
    
    public void editar(Marca m){
        this.marca = m;
    }
    
    public void salvar() {
        marcaFacade.salvar(marca);
    }

    public List<Marca> listaTodos() {
        return marcaFacade.listaTodos();
    }

    public Marca getMarca() {
        return marca;
    }

    public void setMarca(Marca marca) {
        this.marca = marca;
    }

}
