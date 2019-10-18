package berwin.StockHandler;

import org.junit.BeforeClass;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import berwin.StockHandler.DataLayer.Model.Kiszedes.Beles;
import berwin.StockHandler.DataLayer.Model.BeolvasottModel.Beolvasott;
import berwin.StockHandler.LogicLayer.Main.ControllerMain;

import static org.junit.Assert.*;

public class ControllerMainTest {

    private static ControllerMain controllerMain;
    private static List<Beolvasott> beolvasottak;
    private static List<Beles> belesek;

    @BeforeClass
    public static void setUpClass() {
        controllerMain = ControllerMain.getInstance();

        beolvasottak = new ArrayList<>();
        beolvasottak.add(new Beolvasott()
        {{
            setID("1234567");
            setCikkszam("ASD123");
            setLokacio("IRODA");
            setBeolvasottHossz(7);
            setSzelesseg(17);
        }});
        beolvasottak.add(new Beolvasott()
        {{
            setID("7654321");
            setCikkszam("WER321");
            setLokacio("GYAR");
            setBeolvasottHossz(9);
            setSzelesseg(19);
        }});

        belesek = new ArrayList<>();
        belesek.add(new Beles()
        {{
            setKeszletenHossz(500);
            setAnyagKod("ASDASD123");
            setLokacio("IRODA");
            setSzuksegesHossz(200);
            setBelesBeolvasottak(beolvasottak);
        }});
        belesek.add(new Beles()
        {{
            setKeszletenHossz(300);
            setAnyagKod("WERWER321");
            setLokacio("GYAR");
            setSzuksegesHossz(100);
        }});

    }

    @Test
    public void testGetInstanceNotNull() {
        assertNotNull(controllerMain);
    }

    @Test
    public void testGetInstanceAlwaysSameValue() {
        assertEquals(ControllerMain.getInstance(), controllerMain);
        assertEquals(ControllerMain.getInstance(), controllerMain);
        assertEquals(ControllerMain.getInstance(), controllerMain);
    }

    @Test
    public void testGetCikkszamBeolvasott() {

        // ARRANGE
        Beolvasott result = null;

        // ACT
        //result = controllerMain.getBeolvasott("1234567", beolvasottak);

        // ASSERT
        assertEquals(result.getCikkszam(), "ASD123");
        assertEquals(result.getLokacio(), "IRODA");
    }

    @Test
    public void testVoltBeolvasvaCikkszam() {

        // ARRANGE
        boolean pozitiveResult;
        boolean negativeResult;

        // ACT
        //pozitiveResult = controllerMain.VoltBeolvasvaCikkszam("1234567", beolvasottak);
        //negativeResult = controllerMain.VoltBeolvasvaCikkszam("5959595", beolvasottak);

        // ASSERT
        //assertEquals(pozitiveResult, true);
        //assertEquals(negativeResult, false);
    }

    @Test
    public void testGetEgyenloBeles() {

        // ARRANGE
        Beles result = null;

        // ACT
        //result = controllerMain.getEgyenloBeles("WERWER321", belesek);

        // ASSERT
        //assertEquals(result.getLokacio(), "GYAR");
    }

    @Test
    public void testGetBelesBeolvasott() {

        // ARRANGE
        Beolvasott result = null;

        // ACT
        //result = controllerMain.getBelesBeolvasottak(belesek.get(0), "1234567");

        // ASSERT
//        assertEquals(result.getLokacio(), "IRODA");
//        assertEquals(result.getCikkszam(), "ASD123");
    }

//    @Test
//    public void testVirtualisBeolvasvE()
//    {
//
//    }


}