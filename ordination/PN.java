package ordination;

import java.time.LocalDate;
import java.util.ArrayList;

public class PN extends Ordination {

    private double antalEnheder;
    private ArrayList<LocalDate> antalGangeGivet = new ArrayList<>();

    /**
     * Registrerer at der er givet en dosis paa dagen givesDen
     * Returnerer true hvis givesDen er inden for ordinationens gyldighedsperiode og datoen huskes
     * Retrurner false ellers og datoen givesDen ignoreres
     * @paramgivesDen
     * @return
     */
    public PN(LocalDate startDate, LocalDate endDate, Laegemiddel laegemiddel) {
        super(startDate, endDate, laegemiddel);
    }

    public boolean givDosis(LocalDate givesDen) {

        return false;   
    }

    @Override
    public double doegnDosis() {
        return 0;
    }

    @Override
    public double samletDosis() {
        return 0;
    }

    /**
     * Returnerer antal gange ordinationen er anvendt
     * @return
     */
    public int getAntalGangeGivet() {
        return antalGangeGivet.size();
    }

    public double getAntalEnheder() {
        return antalEnheder;
    }


    }

    @Override
    public String getType() {
        return "DagligFast";
    }

}
