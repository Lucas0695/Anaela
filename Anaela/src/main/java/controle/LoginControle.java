package controle;

import entidade.Permissoes;
import entidade.Usuario;
import facade.UsuarioFacade;
import java.io.Serializable;
import javax.faces.application.FacesMessage;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;

@Named
@SessionScoped
public class LoginControle implements Serializable {

//    private UsuarioFacade usuarioFacade;
//    private Usuario usuario = new Usuario();
//    
//    public Boolean validaMenu(String menu){
//        for (Permissoes p : usuario.getPerfil().getPermissoes()) {
//            if(menu.equals(p.getNome())){
//                return true;
//            }
//        }        
//        return false;
//    }
//    
//    public String logar(){
//        usuario = usuarioFacade.validaUsuario(usuario);
//        if(usuario.getPerfil() != null){
//            return "index.xhtml";
//        }else{
//            FacesContext.getCurrentInstance().addMessage(
//                    null, new FacesMessage(
//                            FacesMessage.SEVERITY_ERROR,
//                            "Usuário e/ou senha inválidos!",
//                            null));
//            return "login.xhtml";
//        }
//    }
//
//    public Usuario getUsuario() {
//        return usuario;
//    }
//
//    public void setUsuario(Usuario usuario) {
//        this.usuario = usuario;
//    }
//
//}
    
@Inject
    private UsuarioFacade usuarioService;
    private Usuario usuario = new Usuario();

    public Boolean validaMenu(String menu){
        for (Permissoes p : usuario.getPerfil().getPermissoes()) {
            if(menu.equals(p.getNome())){
                return true;
            } else {
            }
        }
        return false;
    }

    public String logar(){
        System.out.println("Esta passando aqui");
        usuario = usuarioService.validaUsuario(usuario);
        if(usuario.getPerfil() != null){
            System.out.println("Encontrou usuario");
            return "index.xhtml";
        }else{
            System.out.println("no encontrou user");
            FacesContext.getCurrentInstance().addMessage(
                    null, new FacesMessage(
                            FacesMessage.SEVERITY_ERROR,
                            "Usuário e/ou senha inválidos!",
                            null));
            return "login.xhtml";
        }
    }
    public String logoff() {
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        request.getSession().invalidate();
        return "/login?faces-redirect=true";
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

}