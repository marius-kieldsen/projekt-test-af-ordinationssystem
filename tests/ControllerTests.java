package tests;


import com.sun.source.tree.AssertTree;
import controller.Controller;
import ordination.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class ControllerTests {

    private Patient patient;
    private Laegemiddel laegemiddel;
    private PN pn;
    private DagligFast fast;
    private IllegalArgumentException exception;

    @BeforeEach
    void setUp() {
        patient = new Patient("123456-7890", "Peter Ingemann", 160);
        laegemiddel = new Laegemiddel(
                "Acetylsalicylsyre",
                0.1,
                0.2,
                0.3,
                "Styk");

        pn = Controller.opretPNOrdination(
                LocalDate.of(2026,1,01),
                LocalDate.of(2026,10,01),
                patient,
                laegemiddel,
                4
        );

        fast = Controller.opretDagligFastOrdination(
                LocalDate.of(2026,1,01),
                LocalDate.of(2026,10,01),
                patient,
                laegemiddel,
                1,
                1,
                1,
                1
        );
    }

    @AfterEach
    void printException() {
        if (exception != null) {
            System.out.println("Printet fejlmeddelelse: " + "'" + exception.getMessage() + "'");
            exception = null;
        }
    }

    // opretPNOrdination
    // Gyldige data
    @Test
    public void opretPNOrdination_gyldigInput_returnererPN() {
        assertNotNull(pn);
        assertTrue(patient.getOrdinationer().contains(pn));
    }

    @Test
    public void opretPNOrdination_nulAntalOgSammeDato_returnererPN() {
        PN edgeCase = Controller.opretPNOrdination(
                LocalDate.of(2026,1,01),
                LocalDate.of(2026,1,01),
                patient,
                laegemiddel,
                0
        );

        assertNotNull(edgeCase);
        assertTrue(patient.getOrdinationer().contains(edgeCase));
        assertEquals(0, edgeCase.getAntalEnheder());
    }

    // Ugyldige data
    @Test
    public void opretPNOrdination_negativAntal_kasterException() {
        exception = assertThrows(IllegalArgumentException.class, () -> {
            Controller.opretPNOrdination(
                LocalDate.of(2026,1,01),
                LocalDate.of(2026,10,01),
                patient,
                laegemiddel,
                -4
            );
        });

        assertTrue(exception.getMessage().contains("Antal må ikke være negativ"));
    }

    @Test
    public void opretPNOrdination_startDatoEfterSlutDato_kasterException() {
        exception = assertThrows(IllegalArgumentException.class, () -> {
            Controller.opretPNOrdination(
                LocalDate.of(2026,11,01),
                LocalDate.of(2026,10,01),
                patient,
                laegemiddel,
                4
            );
        });

        assertTrue(exception.getMessage().contains("Slutdato kan ikke være før startdato"));
    }

    @Test
    public void opretPNOrdination_nullPatient_kasterException() {
        exception = assertThrows(IllegalArgumentException.class, () -> {
            Controller.opretPNOrdination(
                    LocalDate.of(2026,1,01),
                    LocalDate.of(2026,10,01),
                    null,
                    laegemiddel,
                    4
            );
        });

        assertTrue(exception.getMessage().contains("Patient og lægemiddel må ikke være null"));
    }

    @Test
    public void opretPNOrdination_nullLægemiddel_kasterException() {
        exception = assertThrows(IllegalArgumentException.class, () -> {
            Controller.opretPNOrdination(
                    LocalDate.of(2026,1,01),
                    LocalDate.of(2026,10,01),
                    patient,
                    null,
                    4
            );
        });

        assertTrue(exception.getMessage().contains("Patient og lægemiddel må ikke være null"));
    }

    // opretDagligFastOrdination
    @Test
    public void opretDagligFastOrdination_gyldigInput_returnererOrdination() {
        assertNotNull(fast);
        assertTrue(patient.getOrdinationer().contains(fast));
    }

    @Test
    public void opretDagligFastOrdination_nulAntal_returnererOrdination() {
        DagligFast edgeCase = Controller.opretDagligFastOrdination(
                LocalDate.of(2026, 1, 1),
                LocalDate.of(2026, 1, 1),
                patient,
                laegemiddel,
                0,
                0,
                0,
                0
        );

        assertNotNull(edgeCase);
        assertTrue(patient.getOrdinationer().contains(edgeCase));
        assertEquals(0, edgeCase.doegnDosis());
    }

    // Bare for at verificere, den gør som den skal
    @Test
    public void opretDagligFastOrdination_gyldigInput_korrektDoegnDosis() {
        assertEquals(4, fast.doegnDosis());
    }

    // Ugyldige data
    @Test
    public void opretDagligFastOrdination_startDatoEfterSlutDato_kasterException() {
        exception = assertThrows(IllegalArgumentException.class, () -> {
            Controller.opretDagligFastOrdination(
                    LocalDate.of(2026, 11, 1),
                    LocalDate.of(2026, 10, 1),
                    patient,
                    laegemiddel,
                    1,
                    1,
                    1,
                    1
            );
        });

        assertTrue(exception.getMessage().contains("Slutdato kan ikke være før startdato"));
    }

    @Test
    public void opretDagligFastOrdination_nullPatient_kasterException() {
        exception = assertThrows(IllegalArgumentException.class, () -> {
            Controller.opretDagligFastOrdination(
                    LocalDate.of(2026, 1, 1),
                    LocalDate.of(2026, 10, 1),
                    null,
                    laegemiddel,
                    1,
                    1,
                    1,
                    1
            );
        });

        assertTrue(exception.getMessage().contains("Patient og lægemiddel må ikke være null"));
    }

    @Test
    public void opretDagligFastOrdination_nullLægemiddel_kasterException() {
        exception = assertThrows(IllegalArgumentException.class, () -> {
            Controller.opretDagligFastOrdination(
                    LocalDate.of(2026, 1, 1),
                    LocalDate.of(2026, 10, 1),
                    patient,
                    null,
                    1,
                    1,
                    1,
                    1
            );
        });

        assertTrue(exception.getMessage().contains("Patient og lægemiddel må ikke være null"));
    }

    @Test
    public void opretDagligFastOrdination_negativAntal_kasterException() {
        exception = assertThrows(IllegalArgumentException.class, () -> {
            Controller.opretDagligFastOrdination(
                    LocalDate.of(2026, 1, 1),
                    LocalDate.of(2026, 10, 1),
                    patient,
                    laegemiddel,
                    -1,
                    0,
                    0,
                    0
            );
        });

        assertTrue(exception.getMessage().contains("Antal må ikke være negativ"));
    }

    @Test
    public void opretDagligFastOrdination_negativMiddagAntal_kasterException() {
        exception = assertThrows(IllegalArgumentException.class, () -> {
            Controller.opretDagligFastOrdination(
                    LocalDate.of(2026, 1, 1),
                    LocalDate.of(2026, 10, 1),
                    patient,
                    laegemiddel,
                    0,
                    -1,
                    0,
                    0
            );
        });

        assertTrue(exception.getMessage().contains("Antal må ikke være negativ"));
    }

    @Test
    public void opretDagligFastOrdination_negativAftenAntal_kasterException() {
        exception = assertThrows(IllegalArgumentException.class, () -> {
            Controller.opretDagligFastOrdination(
                    LocalDate.of(2026, 1, 1),
                    LocalDate.of(2026, 10, 1),
                    patient,
                    laegemiddel,
                    0,
                    0,
                    -1,
                    0
            );
        });

        assertTrue(exception.getMessage().contains("Antal må ikke være negativ"));
    }

    @Test
    public void opretDagligFastOrdination_negativNatAntal_kasterException() {
        exception = assertThrows(IllegalArgumentException.class, () -> {
            Controller.opretDagligFastOrdination(
                    LocalDate.of(2026, 1, 1),
                    LocalDate.of(2026, 10, 1),
                    patient,
                    laegemiddel,
                    0,
                    0,
                    0,
                    -1
            );
        });

        assertTrue(exception.getMessage().contains("Antal må ikke være negativ"));
    }










}
