package facade;

import entidade.ComposicaoProduto;
import entidade.Compra;
import entidade.ItensCompra;
import entidade.Produto;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import util.Transacional;

@Transacional
public class CompraFacade extends AbstractFacade<Compra> {

    @Inject
    private EntityManager em;

    public CompraFacade() {
        super(Compra.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    @Override
    public void salvar(Compra co) {
        for (ItensCompra it : co.getItensCompra()) {
            ComposicaoProduto p = it.getComposicaoProduto();
            p.setEstoque(p.getEstoque() + it.getQuantidade());
            em.merge(p);
            somaEstoqueTotal(p.getProduto());
        }
        super.salvar(co);
        atualizaPrecoCompra(co);
    }
    
     public void somaEstoqueTotal(Produto pr){
         super.somaEstoqueTotalProduto(pr);
            em.merge(pr);
    }

    private void atualizaPrecoCompra(Compra co) {
        for (ItensCompra it : co.getItensCompra()) {
            ComposicaoProduto p = it.getComposicaoProduto();
            p.setPrecoCompra(it.getValor());

            Double precComp = it.getValor();
            Double precVenda = p.getPrecoVenda();
            Double p1 = precVenda - precComp;
            Double p2 = p1 / precComp;
            Double perc = p2 * 100;
            p.setPercentual(perc);
            em.merge(p);
        }
    }

    @Override
    public void excluir(Compra ve) {
        for (ItensCompra it : ve.getItensCompra()) {
            ComposicaoProduto p = it.getComposicaoProduto();
            p.setEstoque(p.getEstoque() - it.getQuantidade());
            em.merge(p);
        }
        super.excluir(ve);
    }

}
