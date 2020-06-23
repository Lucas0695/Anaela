package controle;

import entidade.Perfil;
import entidade.Permissoes;
import facade.PerfilFacade;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

@Named
@SessionScoped
public class PerfilControle implements Serializable {

    @Inject
    private PerfilFacade perfilFacade;
    private Perfil per;
    private Permissoes permissoes;

    public Permissoes getPermissoes() {
        return permissoes;
    }

    public void setPermissoes(Permissoes permissoes) {
        this.permissoes = permissoes;
    }

    public void adicionar() {
//        for (Permissoes pe : per.getPermissoes()) {
        permissoes.setPerfil(per);
        per.getPermissoes().add(permissoes);
        permissoes = new Permissoes();
//        }
    }

    public void novo() {
        per = new Perfil();
        permissoes = new Permissoes();
        per.setPermissoes(new ArrayList<Permissoes>());
    }

    public void salvar() {
        perfilFacade.salvar(per);
    }

    public void excluir(Perfil e) {
        perfilFacade.excluir(e);
    }

    public void editar(Perfil e) {
        per = e;
    }

    public Perfil getPer() {
        return per;
    }

    public void setPer(Perfil per) {
        this.per = per;
    }

    public List<Perfil> getLista() {
        return perfilFacade.listaTodos();
    }

}
