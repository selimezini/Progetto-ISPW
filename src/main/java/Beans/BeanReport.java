package Beans;

import Model.ProblemType;
import Model.UrgencyType;
import javafx.scene.image.Image;

import java.util.Date;

public class BeanReport {
    private String reportId;
    private String title;
    private String description;
    private String problemType;    // es. "Problema urbano"
    private String urgencyType;    // es. "Bassa urgenza"
    private String status;         // es. "Open"
    private String imagePath;      // percorso o URL dell’immagine
    private Image  image;          // l’oggetto Image vero e proprio
    private String viaDelProblema; // la via dove si trova il problema
    private String authorUsername; // username del cittadino
    private String municipalityName;
    private String municipalityProvince;
    private String municipalityCode;
    private Date date;
    public  BeanReport() { }

    public BeanReport(
            String title,
            String description,
            String problemType,
            String urgencyType,
            String status,
            String imagePath,
            Image image,
            String viaDelProblema
    ) {
        this.title = title;
        this.description = description;
        this.problemType = problemType;
        this.urgencyType = urgencyType;
        this.status = status;
        this.imagePath = imagePath;
        this.image = image;
        this.viaDelProblema = viaDelProblema;
    }

    // getters & setters per tutti i campi, incluso image

    public String getReportId() { return reportId; }
    public void setReportId(String reportId) { this.reportId = reportId; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getProblemType() { return problemType; }
    public void setProblemType(String problemType) { this.problemType = problemType; }

    public String getUrgencyType() { return urgencyType; }
    public void setUrgencyType(String urgencyType) { this.urgencyType = urgencyType; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getImagePath() { return imagePath; }
    public void setImagePath(String imagePath) { this.imagePath = imagePath; }

    public Image getImage() { return image; }
    public void setImage(Image image) { this.image = image; }

    public String getViaDelProblema() { return viaDelProblema; }
    public void setViaDelProblema(String viaDelProblema) { this.viaDelProblema = viaDelProblema; }

    public String getAuthorUsername() { return authorUsername; }
    public void setAuthorUsername(String authorUsername) { this.authorUsername = authorUsername; }

    public String getMunicipalityName() { return municipalityName; }
    public void setMunicipalityName(String municipalityName) { this.municipalityName = municipalityName; }

    public String getMunicipalityProvince() { return municipalityProvince; }
    public void setMunicipalityProvince(String municipalityProvince) { this.municipalityProvince = municipalityProvince; }

    public String getMunicipalityCode() { return municipalityCode; }
    public void setMunicipalityCode(String municipalityCode) { this.municipalityCode = municipalityCode; }







    @Override
    public String toString() {
        return "ReportBean{" +
                "id='" + reportId + '\'' +
                ", title='" + title + '\'' +
                ", desc='" + description + '\'' +
                ", problem='" + problemType + '\'' +
                ", urgency='" + urgencyType + '\'' +
                ", status='" + status + '\'' +
                ", via='" + viaDelProblema + '\'' +
                ", author='" + authorUsername + '\'' +
                ", muni='" + municipalityName + ", " + municipalityProvince +
                "' (" + municipalityCode + ")" +
                ", imagePath='" + imagePath + '\'' +
                '}';
    }




    /**
     * Converte la descrizione in ProblemType
     */
    public ProblemType getProblemTypeEnum() {
        for (ProblemType pt : ProblemType.values()) {
            if (pt.getDescription().equals(this.problemType)) {
                return pt;
            }
        }
        return null;
    }

    /**
     * Converte la descrizione in UrgencyType
     */
    public UrgencyType getUrgencyTypeEnum() {
        for (UrgencyType ut : UrgencyType.values()) {
            if (ut.getDescription().equals(this.urgencyType)) {
                return ut;
            }
        }
        return null;
    }
}











