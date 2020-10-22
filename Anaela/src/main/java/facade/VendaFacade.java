package facade;

import entidade.ComposicaoProduto;
import entidade.ContasReceber;
import entidade.ItensVenda;
import entidade.Produto;
import entidade.Venda;
import java.util.List;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import org.hibernate.Hibernate;
import util.Transacional;

@Transacional
public class VendaFacade extends AbstractFacade<Venda> {

    @Inject
    private EntityManager em;

    public VendaFacade() {
        super(Venda.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    @Override
    public List<Venda> listaTodos() {
        List<Venda> retorno = super.listaTodos();
        for (Venda c : retorno) {
            c.getItensVenda().size();
            Hibernate.initialize(c.getContasRecebers());
            Hibernate.initialize(c.getItensVenda());
        }
        
        return retorno;//To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Venda find(Object id) {
        Venda c = super.find(id);
        c.getItensVenda().size();
        return c;//To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void salvar(Venda ve) {
        if (ve.getStatusVenda().getDescricao().equals("Orcamento")) {
            super.salvar(ve);

        } else {
            for (ItensVenda it : ve.getItensVenda()) {
                ComposicaoProduto p = it.getComposicaoProduto();
                p.setEstoque(p.getEstoque() - it.getQuantidade());
                em.merge(p);
                somaEstoqueTotal(p.getProduto());
            }
            ve.setValorTotal(ve.getValorTotal());
            super.salvar(ve);
        }
    }
    
      public void somaEstoqueTotal(Produto pr){
         super.somaEstoqueTotalProduto(pr);
            em.merge(pr);
    }

    @Override
    public void excluir(Venda ve) {
        if (ve.getStatusVenda().getDescricao().equals("Orcamento")) {
            super.excluir(ve);
        } else {
            for (ItensVenda it : ve.getItensVenda()) {
                ComposicaoProduto p = it.getComposicaoProduto();
                p.setEstoque(p.getEstoque() + it.getQuantidade());
                em.merge(p);
            }
            super.excluir(ve);
        }
    }

}
