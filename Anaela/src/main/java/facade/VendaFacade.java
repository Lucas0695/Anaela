package facade;

import entidade.ComposicaoProduto;
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
        }

        return retorno;//To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Venda find(Object id) {
        Venda c = super.find(id);
        c.getItensVenda().size();
        Hibernate.initialize(c.getContasRecebers());
        Hibernate.initialize(c.getItensVenda());
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
            }
            ve.setValorTotal(ve.getValorTotal());
            super.salvar(ve);
        }

    }

    public void editarVendaRetornaEstoque(Venda ve) {
        if (ve.getStatusVenda().equals(ve.getStatusVenda().FATURADA) || ve.getStatusVenda().equals(ve.getStatusVenda().CONDICIONAL)) {
            for (ItensVenda it : ve.getItensVenda()) {
                ComposicaoProduto p = it.getComposicaoProduto();
                p.setEstoque(p.getEstoque() + it.getQuantidade());
                em.merge(p);
            }
        }
    }

    public void cancelaVendaRetornaEstoque(Venda ve) {
        if (ve.getStatusVenda().equals(ve.getStatusVenda().FATURADA) || ve.getStatusVenda().equals(ve.getStatusVenda().CONDICIONAL)) {
            for (ItensVenda it : ve.getItensVenda()) {
                ComposicaoProduto p = it.getComposicaoProduto();
                p.setEstoque(p.getEstoque() - it.getQuantidade());
                em.merge(p);
            }
        }
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

    public void recuperarLista(Venda ve) {
        Hibernate.initialize(ve.getContasRecebers());
//        Hibernate.initialize(ve.getItensVenda().size());
        for (ItensVenda it : ve.getItensVenda()) {
            Hibernate.initialize(it.getQuantidade());
            Hibernate.initialize(it.getComposicaoProduto());
            Hibernate.initialize(it.getComposicaoProduto().getCor());
            Hibernate.initialize(it.getComposicaoProduto().getCor().getNome());
            Hibernate.initialize(it.getComposicaoProduto().getTamanho());
            Hibernate.initialize(it.getComposicaoProduto().getTamanho().getNome());
            Hibernate.initialize(it.getSubtotal());
            Hibernate.initialize(it.getValor());
            Hibernate.initialize(it.getComposicaoProduto().getProduto());
            Hibernate.initialize(it.getId());
            Hibernate.initialize(it.getVenda());
        }
    }
}
