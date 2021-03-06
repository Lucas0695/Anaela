package facade;

import entidade.ComposicaoProduto;
import entidade.Produto;
import java.util.List;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import org.hibernate.Hibernate;
import util.Transacional;

@Transacional
public class ProdutoFacade extends AbstractFacade<Produto>{

    @Inject
    private EntityManager em;

    public ProdutoFacade() {
        super(Produto.class);
    }
    
 @Override
    public List<Produto> listaTodos() {
        List<Produto> retorno = super.listaTodos();
        for (Produto c : retorno) {
            c.getComposicaoProduto().size();
//            Hibernate.initialize(c.getComposicaoProduto());
        }
        
        return retorno;//To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    @Override
    public void salvar(Produto pr) {  
        super.salvar(pr); 
//        baixaEstoque(pr);
    }
    
    public void recuperarComposicaoProduto(Produto p){
        for(ComposicaoProduto cp : p.getComposicaoProduto()){
             Hibernate.initialize(cp.getCor());
             Hibernate.initialize(cp.getCor().getNome());
             Hibernate.initialize(cp.getEstoque());
             Hibernate.initialize(cp.getPercentual());
             Hibernate.initialize(cp.getPrecoCompra());
             Hibernate.initialize(cp.getPrecoVenda());
             Hibernate.initialize(cp.getTamanho());
             Hibernate.initialize(cp.getTamanho().getNome());
        }        
    }
   
}
