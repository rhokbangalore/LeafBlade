package controllers;

import models.Doctor;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.profile;

/**
 * Created by dhiresh on 28/3/15.
 */
public class Profile extends Controller {

    public static Result profile(String doctor){

        Doctor doc = new Doctor("1","2","3","4","5","6");

        return ok(profile.render(doc));
    }

}
