package models;

import javafx.collections.ObservableList;

import java.util.Objects;
import models.IssueType;

public class reclamation {

    private int idrec;
    private String fullname;
    private String emailrec;
    private int pnrec;
    private IssueType issue;
    private String subject;
    private String message;
    private int stater = 0;

    public reclamation() {
    }

    public reclamation(int idrec, String fullname, String emailrec, int pnrec, IssueType issue, String subject, String message, int stater) {
        this.idrec = idrec;
        this.fullname = fullname;
        this.emailrec = emailrec;
        this.pnrec = pnrec;
        this.issue = issue;
        this.subject = subject;
        this.message = message;
        this.stater = stater;
    }

    public reclamation(String fullname, String emailrec, int pnrec, IssueType issue, String subject, String message, int stater) {
        this.fullname = fullname;
        this.emailrec = emailrec;
        this.pnrec = pnrec;
        this.issue = issue;
        this.subject = subject;
        this.message = message;
        this.stater = stater;
    }

    public reclamation(String fullname, String emailrec, int pnrec, IssueType issue, String subject, String message) {
        this.fullname = fullname;
        this.emailrec = emailrec;
        this.pnrec = pnrec;
        this.issue = issue;
        this.subject = subject;
        this.message = message;
    }

    public int getIdrec() {
        return idrec;
    }

    public void setIdrec(int idrec) {
        this.idrec = idrec;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public int getPnrec() {
        return pnrec;
    }

    public void setPnrec(int pnrec) {
        this.pnrec = pnrec;
    }

    public String getEmailrec() {
        return emailrec;
    }

    public void setEmailrec(String emailrec) {
        this.emailrec = emailrec;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getStater() {
        return stater;
    }

    public void setStater(int stater) {
        this.stater = stater;
    }

    public IssueType getIssue() {
        return issue;
    }

    public void setIssue(IssueType issue) {
        this.issue = issue;
    }

    @Override
    public String toString() {
        return "reclamation{" +
                "idrec=" + idrec +
                ", fullname='" + fullname + '\'' +
                ", emailrec='" + emailrec + '\'' +
                ", pnrec=" + pnrec +
                ", issue=" + issue +
                ", subject='" + subject + '\'' +
                ", message='" + message + '\'' +
                ", stater=" + stater +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof reclamation that)) return false;
        return getPnrec() == that.getPnrec() && Objects.equals(getFullname(), that.getFullname()) && Objects.equals(getEmailrec(), that.getEmailrec()) && Objects.equals(getIssue(), that.getIssue()) && Objects.equals(getSubject(), that.getSubject()) && Objects.equals(getMessage(), that.getMessage());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getFullname(), getEmailrec(), getPnrec(), getIssue(), getSubject(), getMessage());
    }
}
