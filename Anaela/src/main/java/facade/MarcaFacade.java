package facade;

import entidade.Marca;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import util.Transacional;

@Transacional
public class MarcaFacade extends AbstractFacade<Marca>{

    @Inject
    private EntityManager em;

    public MarcaFacade() {
        super(Marca.class);
    }
    
    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
}
