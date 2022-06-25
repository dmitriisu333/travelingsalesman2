package bbw.ch.ds;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.Scanner;

public class TravelingSalesmanProblem {
    public static void main(String[] args) {
        /**
         * Distanz Matrix 4x4:
         *      0  1  2  3
         *   0  0 10 20 13
         *   1  5  0  9 17
         *   2 23  3  0  8
         *   3 12 15  7  0
         **/

        int anzahlStaedte = 4;

        //FileReader liest Zahlen aus ein File und speichert sie in ein 2d Array (Matrize)
        double[][] distanzMatrix = null;
        File file = new File("F:/411/AbschlussProjekt_TSP/TSP_DmitriiS/src/bbw/ch/ds/matrix.txt");
        try {
            Scanner sizeScanner = new Scanner(file);
            String[] temp = sizeScanner.nextLine().split(" ");
            sizeScanner.close();
            int nMatrix = temp.length;

            Scanner scanner = new Scanner(file);
            distanzMatrix = new double[nMatrix][nMatrix];
            for (int i = 0; i < nMatrix; i++) {
                String[] numbers = scanner.nextLine().split(" ");
                for (int j = 0; j < nMatrix; j++) {
                    distanzMatrix[i][j] = Integer.parseInt(numbers[j]);
                }
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        /**
        double distanzMatrix[][] = {
                { 0, 10, 20, 13 },
                { 5, 0, 9, 17 },
                { 23, 3, 0, 8 },
                { 12, 15, 7, 0 }
        };
         **/

        //Mithilfe von TeePrintStream output auf Konsole und in ein separates file ausgeben
        try {
            FileOutputStream output = new FileOutputStream("F:/411/AbschlussProjekt_TSP/TSP_DmitriiS/src/bbw/ch/ds/output.txt");
            TeePrintStream tee = new TeePrintStream(output, System.out);
            System.setOut(tee);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        System.out.println("Alle Routen durch rekursiven Brute-force Algorithm");
        //beste Route zeigen
        int[] besteRoute = globalMinimum(distanzMatrix);
        System.out.println("Die Route des globalen Minimums: ");
        ausgeben(besteRoute);
        //Distanz der beste Route zeigen
        System.out.println("Distanz der Route mit globalen Minimum: ");
        System.out.println(distanzRoute(besteRoute, distanzMatrix));

    }

    
    public static int[] globalMinimum(double[][] distanzMatrix){
        int anzahlStaedte = distanzMatrix.length;
        int[] kurzesteRoute = new int[anzahlStaedte+1]; 
        for (int i = 0; i<kurzesteRoute.length; i++){
            kurzesteRoute[i] = i;
        }
        kurzesteRoute[anzahlStaedte] = 0;
        int[] aktuelleRoute = new int[anzahlStaedte+1];
        aktuelleRoute[0] = 0;
        boolean[] stadtBereitsBesucht = new boolean[anzahlStaedte];
        stadtBereitsBesucht[0] = true;
        globalMinimum(0, anzahlStaedte, aktuelleRoute, kurzesteRoute, stadtBereitsBesucht, distanzMatrix);
        return kurzesteRoute;
    }

    private static void globalMinimum(int aktuellePosition, int anzahlStaedte, int[] aktuelleRoute, int[] kurzesteRoute, boolean[] stadtBereitsBesucht, double[][] distanzMatrix){
        if(anzahlStaedteBesucht(stadtBereitsBesucht)==anzahlStaedte){
            aktuelleRoute[anzahlStaedte] = 0;
            if(distanzRoute(aktuelleRoute, distanzMatrix) < distanzRoute(kurzesteRoute, distanzMatrix)){
                kopiere(aktuelleRoute, kurzesteRoute);
            }
            ausgeben(aktuelleRoute);
            return;
        }
        
        //rekursiver Aufruf
        for(int i=1; i<anzahlStaedte; i++){
            if(!stadtBereitsBesucht[i]){
                stadtBereitsBesucht[i] = true;
                aktuelleRoute[anzahlStaedteBesucht(stadtBereitsBesucht)-1] = i;
                globalMinimum(i, anzahlStaedte, aktuelleRoute, kurzesteRoute, stadtBereitsBesucht, distanzMatrix);
                stadtBereitsBesucht[i] = false;
            }
        }
        return;
    }

    private static int anzahlStaedteBesucht(boolean[] stadtBereitsBesucht){
        int counter = 0;
        for(int i=0; i<stadtBereitsBesucht.length; i++){
            if(stadtBereitsBesucht[i]){
                counter++;
            }
        }
        return counter;
    }

    private static double distanzRoute(int[] route, double[][] distanzMatrix){
        double distanz = 0;
        for(int i=1; i<route.length; i++){
            distanz += distanzMatrix[route[i-1]][route[i]];
        }
        distanz += distanzMatrix [route[route.length-1]][route[0]];
        return distanz;
    }

    private static void kopiere( int[] aktuelleRoute, int[] kurzesteRoute){
        for(int i=0; i<aktuelleRoute.length; i++){
            kurzesteRoute[i] = aktuelleRoute[i];
        }
    }

    private static void ausgeben(int[] route) {
        System.out.print("[ ");
        for(int i=0; i<route.length; i++) {
            System.out.print(route[i]+" ");
        }
        System.out.println("]");
    }
}
