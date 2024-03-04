package models;

public enum IssueType {
    ACCOUNT_EMAIL_VERIFICATION("Account Email Verification"),
    PHONE_NUMBER_VERIFICATION("Phone Number Verification"),
    LOADING_OR_LAG_ISSUES("Loading or Lag Issues"),
    GRADING_ISSUES("Grading Issues"),
    LOST_PROGRESS("Lost Progress"),
    OTHER_ISSUES("OTHER");

    private String value;

    IssueType(String value) {
        this.value = value;
    }

    IssueType() {
        // Enum constructor
    }

    @Override
    public String toString() {
        return value;
    }
}
