package controllers;

import models.Doctor;
import play.*;
import play.data.Form;
import play.mvc.*;

import views.html.*;

import javax.print.Doc;

public class Application extends Controller {

    public static class Login{
        String username , password;

    }

    public static class SignUp {

        public String name, username, password, confirmpassword, degree, clinic, bio;

        public String validate() {
            return Doctor.validate(name, username, password, confirmpassword, degree, clinic, bio);
        }
    }

    private static Form<Login> loginForm = Form.form(Login.class);
    private static Form<SignUp> signForm = Form.form(SignUp.class);

    public static Result index() {
        return ok(index.render("AskHere"));
    }

    public static Result Enter() {
        return ok(enter.render(loginForm,signForm));
    }

    public static Result anonymousUserChat() {
        session().put("username", "anonymous");
        return ok(views.html.chat.render("aa"));
    }

    public static Result doctorChat() {
        session().put("username", "doctor");
        return TODO;
    }

    public static Result authenticateLogin() {
        Form<Login> filledForm = loginForm.bindFromRequest();
        if (filledForm.hasErrors()) {
            return badRequest(enter.render(filledForm, signForm));
        }

        session().clear();
        session("handle","doctor");
        return redirect(controllers.routes.Application.index());
    }

    public static Result logout() {
        session().clear();
        return redirect(routes.Application.index());
    }

    public static Result authenticateSignUp() {
        Form<SignUp> filledForm = signForm.bindFromRequest();
        if (filledForm.hasErrors())
            return badRequest(enter.render(loginForm,filledForm));

        String name = signForm.get().name;
        String username = signForm.get().username;
        String password = signForm.get().password;
        String degree = signForm.get().degree;
        String clinic = signForm.get().clinic;
        String bio = signForm.get().bio;

        Doctor.insertDoctorDetails(name, username, password, degree, clinic, bio);
        session().clear();
        session("handle","doctor");
        return redirect(controllers.routes.Application.index());
    }
}
