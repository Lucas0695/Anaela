package facade;

import entidade.IncrementoBKP;
import entidade.IncrementoBKP;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import util.Transacional;

@Transacional
public class BackupFacade extends AbstractFacade<IncrementoBKP>{

    @Inject
    private EntityManager em;

    public BackupFacade() {
        super(IncrementoBKP.class);
    }
    
    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    

    @Override
    public void salvar(IncrementoBKP co) {        
        super.salvar(co); 
//        somaEstoque(co);
    }

    
    
    
    
//    private void somaEstoque(IncrementoBKP co) {
//        for(ItensIncrementoBKP it : co.getItensIncrementoBKPs()){
//            Produto p = it.getProduto();
//            p.setEstoque(p.getEstoque() - it.getQuantidade());
//            em.merge(p);
//        }
//    }
//    
//   
//       @Override
//    public void excluir(IncrementoBKP ve) {
//        for(ItensIncrementoBKP it : ve.getItensIncrementoBKPs()){
//            Produto p = it.getProduto();
//            p.setEstoque(p.getEstoque() + it.getQuantidade());
//            em.merge(p);
//        }
//        super.excluir(ve);
//    }
    
    }
    
    
    
    


/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
//package facade;
//
//import entidades.IncrementoBKP;
//import javax.ejb.Stateless;
//import javax.persistence.EntityManager;
//import javax.persistence.PersistenceContext;
//
///**
// *
// * @author marcos-ladeira
// */
//@Stateless
//public class BackupFacade extends AbstractFacade<IncrementoBKP>{
//    @PersistenceContext(unitName = "estagioPU")
//    private EntityManager em;
//
//    protected EntityManager getEntityManager() {
//        return em;
//    }
//    
//    public BackupFacade(){
//        super(IncrementoBKP.class);
//    }
//
//    
//}
