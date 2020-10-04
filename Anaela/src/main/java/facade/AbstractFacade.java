package facade;

import entidade.ComposicaoProduto;
import entidade.Produto;
import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;

public abstract class AbstractFacade<T> implements Serializable {

    private Class<T> entityClass;

    protected abstract EntityManager getEntityManager();

    public AbstractFacade(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    public T buscar(Long id) {
        return getEntityManager().find(entityClass, id);
    }

//    public T buscar(Object id) {
//        return getEntityManager().find(entityClass, id);
//    }

//    adicionado para buscar 
    public T find(Object id) {
        return getEntityManager().find(entityClass, id);
    }

    public void salvar(T objeto) {
        getEntityManager().merge(objeto);
    }

    public void excluir(T objeto) {
        getEntityManager().remove(getEntityManager().merge(objeto));
    }

    public List<T> listaTodos() {
        Query q = getEntityManager().createQuery("from " + entityClass.getSimpleName());
        return q.getResultList();
    }

    public List<T> listaFiltrando(String filtro, String... atributos) {
        String hql = "from " + entityClass.getSimpleName() + " obj where ";
        for (String atributo : atributos) {
            hql += "lower(obj." + atributo + ") like :filtro OR ";
        }
        hql = hql.substring(0, hql.length() - 3);
        Query q = getEntityManager().createQuery(hql);
        q.setParameter("filtro", "%" + filtro.toLowerCase() + "%");
        return q.getResultList();
    }

//    Filtro de Composição de Produto
    public List<T> listaFiltrandoComposicao(String filtro, String... atributos) {
        String hql = "from " + "composicaoproduto" + " obj where ";
        for (String atributo : atributos) {
            hql += "lower(obj." + atributo + ") like :filtro OR ";
        }
        hql = hql.substring(0, hql.length() - 3);
        Query q = getEntityManager().createQuery(hql);
        q.setParameter("filtro", "%" + filtro.toLowerCase() + "%");
        return q.getResultList();
    }
    
    public void somaEstoqueTotalProduto(Produto p) {
        Double estoque = 0d;
        Double estoqueTotal = 0d;
        for (ComposicaoProduto it : p.getComposicaoProduto()) {
            estoque = it.getEstoque();
            estoqueTotal = estoqueTotal + estoque;
            p.setEstoqueTotal(estoqueTotal);   
        }
    }

}
