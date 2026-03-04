package ordination;


import java.time.LocalDate;
import java.time.LocalTime;

public class DagligFast extends Ordination {

    // 0 = morgen, 1 = middag, 2 = aften, 3 = nat
    private Dosis[] doser = new Dosis[4];

    public DagligFast(LocalDate startDate, LocalDate endDate, Laegemiddel laegemiddel) {
        super(startDate, endDate, laegemiddel);
    }
    public Dosis[] getDoser() {
        return doser;
    }

    public void setDosis(int index, Dosis dosis) {
        doser[index] = dosis;
    }

    public Dosis getDosis(int index) {
        return doser[index];
    }

    @Override
    public double doegnDosis() {
        double sum = 0;

        for (Dosis dosis : doser) {
            if (dosis != null) {
                sum += dosis.getAntal();
            }
        }
        return sum;
    }

    @Override
    public double samletDosis() {
        return doegnDosis() * antalDage();
    }

    @Override
    public String getType() {
        return "DagligFast";
    }




}
