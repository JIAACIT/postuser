package view;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.Part;

import exception.ParentalException;

import controller.UserController;
import controller.ImageController;
import model.Image;
import model.User;

@Named
public class CreateUserMb {

	@Inject
	private UserController userController;

	@Inject
	private ImageController imgController;

	private User user = new User();
	private String emailErrorMessage;
	private String passErrorMessage;
	private Part file;

	public String create() throws ParentalException {
		// System.out.println("USUARIO: " + user.toString());
		try {
			if (user.getAge() < 18) {
				throw new ParentalException("Error");
			} else {

				Image img = null;
				if (file != null && file.getSize() > 0 && file.getContentType().startsWith("image/")) {
					img = imgController.upload(file);
					user.setAvatar(img);
					userController.create(user);
					user = null;
					FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Usuario registrado con exito",
							null);
					FacesContext.getCurrentInstance().addMessage(null, msg);
					return "login?faces-redirect=true";
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error interno", null);
			FacesContext.getCurrentInstance().addMessage(null, msg);
			return null;

		}
		return "login?faces-redirect=true";

		// mensaje "Usuario creado con exito
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public void validateemail(AjaxBehaviorEvent evento) {
		if (user.getEmail().length() < 4) {
			setEmailErrorMessage("El email tiene que tener como minimo 4 caracteres");
			FacesContext.getCurrentInstance().addMessage("ERROR", null);
		} else {
			if (user.getEmail().length() > 50) {
				FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
						"El email puede tener como maximo 50 caracteres", null);
				FacesContext.getCurrentInstance().addMessage(null, msg);
				// setEmailErrorMessage(msg);
			} else {
				setEmailErrorMessage("");
			}
		}
	}

	public void validatepassword(AjaxBehaviorEvent evento) {
		if (user.getPwd().length() < 4) {
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"La contraseña puede tener como maximo 15 caracteres", null);
			FacesContext.getCurrentInstance().addMessage(null, msg);
		} else {
			if (user.getPwd().length() > 15) {
				FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
						"La contraseña puede tener como maximo 15 caracteres", null);
				FacesContext.getCurrentInstance().addMessage(null, msg);
			} else {
				passErrorMessage = "";
			}
		}
	}

	public String getEmailErrorMessage() {
		return emailErrorMessage;
	}

	public void setEmailErrorMessage(String mensajeErrorNombre) {
		this.emailErrorMessage = mensajeErrorNombre;
	}

	public String getPasswordErrorMessage() {
		return passErrorMessage;
	}

	public void setPasswordErrorMessage(String mensajeErrorPassword) {
		this.passErrorMessage = mensajeErrorPassword;
	}

	public Part getFile() {
		return file;
	}

	public void setFile(Part file) {
		this.file = file;
	}

}