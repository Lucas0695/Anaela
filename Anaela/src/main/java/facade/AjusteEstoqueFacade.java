package facade;

import entidade.AjusteEstoque;
import entidade.ComposicaoProduto;
import entidade.ItensAjusteEstoque;
import entidade.Produto;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import util.Transacional;

@Transacional
public class AjusteEstoqueFacade extends AbstractFacade<AjusteEstoque> {

    @Inject
    private EntityManager em;

    public AjusteEstoqueFacade() {
        super(AjusteEstoque.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    @Override
    public void salvar(AjusteEstoque aj) { 
        for (ItensAjusteEstoque it : aj.getItensAjusteEstoque()) {
            ComposicaoProduto p = it.getComposicaoProduto();
            it.setEstoqueAnterior(p.getEstoque());
            p.setEstoque(it.getEstoqueAntual());
            em.merge(p); 
            somaEstoqueTotal(p.getProduto());
        }
        super.salvar(aj);
    }
    
    public void somaEstoqueTotal(Produto pr){
         super.somaEstoqueTotalProduto(pr);
            em.merge(pr);
    }

    @Override
    public void excluir(AjusteEstoque aj) {
        for (ItensAjusteEstoque it : aj.getItensAjusteEstoque()) {
            ComposicaoProduto p = it.getComposicaoProduto();
            p.setEstoque(it.getEstoqueAnterior());
            em.merge(p);
        }
        super.excluir(aj);
    }
}
