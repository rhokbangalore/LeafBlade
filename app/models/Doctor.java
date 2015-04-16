package models;

import net.vz.mongodb.jackson.Id;
import net.vz.mongodb.jackson.JacksonDBCollection;
import play.modules.mongodb.jackson.MongoDB;

import javax.print.Doc;

/**
 * Created by Darshan on 3/28/2015.
 */
public class Doctor {
    @Id
    private String username;

    String name, password, degree, clinic, bio;
    private boolean flag;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDegree() {
        return degree;
    }

    public void setDegree(String degree) {
        this.degree = degree;
    }

    public String getClinic() {
        return clinic;
    }

    public void setClinic(String clinic) {
        this.clinic = clinic;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    private static JacksonDBCollection<Doctor, String> doctorCollection = MongoDB.getCollection("doctors", Doctor.class, String.class);

    public Doctor() {

    }

    public Doctor(String name, String username, String password, String degree, String clinic, String bio) {
        this.name = name;
        this.username = username;
        this.password = password;
        this.degree = degree;
        this.clinic = clinic;
        this.bio = bio;
    }

    public static String validate(String name, String username, String password, String confirmPassword, String degree, String clinic, String bio) {
        if (username.length() < 4 || Doctor.checkUniqueUsername(username)) {
            System.out.println(username);
            return "Username must be unique and exceed 3 characters length";
        }

        if (password.length() < 6) {
            return "Password must be atleast 6 characters in length";
        }
        if (password.compareTo(confirmPassword) != 0) {
            return "Passwords don't match";
        }

        return null;
    }

    public static String validate(String username, String password){
        if(username.length()==1){
            return null;
        }
        return "error";
    }

    public static boolean checkUniqueUsername(String username) {
        Doctor doctor = Doctor.doctorCollection.findOneById(username);
        if (username == null) {
            return true;
        } else {
            return false;
        }
    }

    public static void insertDoctorDetails(String name, String username, String password, String degree, String clinic, String bio) {
        Doctor doctor = new Doctor(name, username, password, degree, clinic, bio);
        doctor.setFlag(true);

        Doctor.doctorCollection.save(doctor);
    }
}
