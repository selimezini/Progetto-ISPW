package model;


import model.users.Citizen;
import javafx.scene.image.Image;

import java.util.Date;

public class Report {

    private String reportId;
    private String title;
    private Municipality municipality;
    private ProblemType problemType;
    private UrgencyType urgencyType;
    private Image image;
    private String description;
    private String status;
    private Citizen author;
    private String imagePath;
    private String viaDelProblema;
    private Date date;

    public void setMunicipality(Municipality municipality) {

        this.municipality = municipality;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setReportId(String reportId) {

        this.reportId = reportId;
    }

    public void setStatus(String status) {

        this.status = status;
    }

    public void setProblemType(ProblemType problemType) {

        this.problemType = problemType;
    }

    public void setUrgencyType(UrgencyType urgencyType) {

        this.urgencyType = urgencyType;
    }

    public void setImage(Image image) {

        this.image = image;
    }

    public void setTitle(String title) {

        this.title = title;
    }


    public String getReportId() {

        return reportId;
    }

    public Municipality getMunicipality() {

        return municipality;
    }

    public String getDescription() {
        return description;
    }


    public ProblemType getProblemType() {
        return problemType;
    }


    public String getTitle() {
        return title;
    }

    public Image getImage() {
        return image;
    }

    public UrgencyType getUrgencyType() {
        return urgencyType;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String  getImagePath() {
        return imagePath;
    }


    public String getStatus() {
        return status;}

    public Citizen getAuthor() {
        return author;}
    public void setAuthor(Citizen author) {

        this.author = author;
    }

    public String getViaDelProblema() {

        return viaDelProblema;
    }

    public void setViaDelProblema(String viaDelProblema) {

        this.viaDelProblema = viaDelProblema;
    }

    public Date getDate() {

        return date;
    }

    public void setDate(Date date) {

        this.date = date;
    }

}
