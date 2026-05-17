package src.university;

import java.io.Serializable;

public class Mark implements Serializable {
    private static final long serialVersionUID = 1L;

    private double firstAttestation;   
    private double secondAttestation;  
    private double finalExam;         

    public Mark(double firstAttestation, double secondAttestation, double finalExam) {
        if (firstAttestation < 0 || firstAttestation > 30)
            throw new IllegalArgumentException("First attestation must be 0-30");
        if (secondAttestation < 0 || secondAttestation > 30)
            throw new IllegalArgumentException("Second attestation must be 0-30");
        if (finalExam < 0 || finalExam > 40)
            throw new IllegalArgumentException("Final exam must be 0-40");

        this.firstAttestation  = firstAttestation;
        this.secondAttestation = secondAttestation;
        this.finalExam         = finalExam;
    }

    public double getTotal() {
        return firstAttestation + secondAttestation + finalExam;
    }

    public boolean isPassed() {
        return getTotal() >= 50.0; 
    }

    public String getLetterGrade() {
        double t = getTotal();
        if (t >= 95) return "A";
        if (t >= 90) return "A-";
        if (t >= 85) return "B+";
        if (t >= 80) return "B";
        if (t >= 75) return "B-";
        if (t >= 70) return "C+";
        if (t >= 65) return "C";
        if (t >= 60) return "C-";
        if (t >= 55) return "D+";
        if (t >= 50) return "D";
        return "F";
    }

    public double getGradePoint() {
        double t = getTotal();
        if (t >= 95) return 4.0;
        if (t >= 90) return 3.67;
        if (t >= 85) return 3.33;
        if (t >= 80) return 3.0;
        if (t >= 75) return 2.67;
        if (t >= 70) return 2.33;
        if (t >= 65) return 2.0;
        if (t >= 60) return 1.67;
        if (t >= 55) return 1.33;
        if (t >= 50) return 1.0;
        return 0.0;
    }

    public double getFirstAttestation()  { return firstAttestation; }
    public double getSecondAttestation() { return secondAttestation; }
    public double getFinalExam()         { return finalExam; }

    @Override
    public String toString() {
        return String.format("Mark[ATT1=%.1f, ATT2=%.1f, Final=%.1f | Total=%.1f | %s | %s]",
                firstAttestation, secondAttestation, finalExam,
                getTotal(), getLetterGrade(), isPassed() ? "PASS" : "FAIL");
    }
}
