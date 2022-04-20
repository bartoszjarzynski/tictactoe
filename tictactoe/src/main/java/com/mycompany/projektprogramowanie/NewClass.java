import java.util.*;

public class NewClass {

    private static final String KOMPUTER = "Komputer", GRACZ = "Gracz";
    private static List<List> pozycjeWygrywajace;
    private static List<Integer> userPoz;
    private static List<Integer> kompPoz;
    private static Random random;
    private static char[][] plansza;

    public static void main(String[] args) {
        ustawPlansze();
        decyzja();
        startGry();
    }

    private static void ustawPlansze() {
        plansza = new char[][]{{' ', '|', ' ', '|', ' '},
                {'-', '+', '-', '+', '-'},
                {' ', '|', ' ', '|', ' '},
                {'-', '+', '-', '+', '-'},
                {' ', '|', ' ', '|', ' '}
        };
    }

    private static void decyzja() {

        pozycjeWygrywajace = new ArrayList<>();
        userPoz = new ArrayList<>();
        kompPoz = new ArrayList<>();
        random = new Random();

        List p1 = Arrays.asList(1, 2, 3);
        List p2 = Arrays.asList(4, 5, 6);
        List p3 = Arrays.asList(7, 8, 9);
        List p4 = Arrays.asList(1, 4, 7);
        List p5 = Arrays.asList(2, 5, 8);
        List p6 = Arrays.asList(3, 6, 9);
        List p7 = Arrays.asList(1, 5, 9);
        List p8 = Arrays.asList(3, 5, 7);

        pozycjeWygrywajace.add(p1);
        pozycjeWygrywajace.add(p2);
        pozycjeWygrywajace.add(p3);
        pozycjeWygrywajace.add(p4);
        pozycjeWygrywajace.add(p5);
        pozycjeWygrywajace.add(p6);
        pozycjeWygrywajace.add(p7);
        pozycjeWygrywajace.add(p8);
    }

    private static void startGry() {

        int ruchGracza= random.nextInt(2) + 1;
        char pRuch;
        if (ruchGracza== 1) {
            pRuch = 'P';
            ruchGracza();
        } else {
            pRuch = 'C';
            ruchKomputera();
        }
        
        while (true) {
            if (pRuch == 'P') {
                ruchKomputera();
                ruchGracza();
            } else {
                ruchGracza();
                ruchKomputera();
            }
        }
    }

    private static void ruchGracza() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Jaki chcesz wykonać ruch (1-9)? :");

        int pozycjaGracza = scanner.nextInt();
        while (userPoz.contains(pozycjaGracza) || kompPoz.contains(pozycjaGracza) ||
                !(pozycjaGracza >= 1 && pozycjaGracza <= 9)) {
            System.out.println("Wprowadz poprawna, niezajeta pozycje!");
            pozycjaGracza = scanner.nextInt();
        }
        postawRuch(pozycjaGracza, GRACZ, plansza);
        drukujPlansze(plansza, GRACZ);

        int wynik = czyKoniec();
        if (wynik != -1) {
            printWynik(wynik);
        }
    }

    private static void ruchKomputera() {
        int pozycjaKomputera = random.nextInt(9) + 1;
        while (userPoz.contains(pozycjaKomputera) || kompPoz.contains(pozycjaKomputera)) {
            pozycjaKomputera = random.nextInt(9) + 1;
        }
        postawRuch(pozycjaKomputera, KOMPUTER, plansza);
        drukujPlansze(plansza, KOMPUTER);

        int wynik = czyKoniec();
        if (wynik != -1) {
            printWynik(wynik);
        }
    }

    private static void drukujPlansze(char[][] plansza, String user) {
        if (user.equalsIgnoreCase("Gracz")) {
            System.out.println("Twoj ruch: ");
        } else {
            System.out.println("Ruch komputera: ");
        }

        System.out.println();

        for (char[] row : plansza) {
            for (char c : row) {
                System.out.print(c);
            }
            System.out.println();
        }

        System.out.println();
        System.out.println("#-#-#-#");
        System.out.println();
    }

    private static void postawRuch(int position, String user, char[][] plansza) {
        char symbol;

        if (user.equalsIgnoreCase("Gracz")) {
            symbol = 'X';
            userPoz.add(position);
        } else {
            symbol = 'O';
            kompPoz.add(position);
        }

        switch (position) {
            case 1:
                plansza[0][0] = symbol;
                break;
            case 2:
                plansza[0][2] = symbol;
                break;
            case 3:
                plansza[0][4] = symbol;
                break;
            case 4:
                plansza[2][0] = symbol;
                break;
            case 5:
                plansza[2][2] = symbol;
                break;
            case 6:
                plansza[2][4] = symbol;
                break;
            case 7:
                plansza[4][0] = symbol;
                break;
            case 8:
                plansza[4][2] = symbol;
                break;
            case 9:
                plansza[4][4] = symbol;
                break;
            default:
                System.out.println("Wprowadz poprawna, niezajeta pozycje!");
                break;
        }
    }

    private static int czyKoniec() {
        for (List poz : pozycjeWygrywajace) {
            if (userPoz.containsAll(poz)) {
                return 1;
            } else if (kompPoz.containsAll(poz)) {
                return 2;
            }
        }
        if (userPoz.size() + kompPoz.size() == 9)
            return 0;
        return -1;
    }

    private static void printWynik(int wynik) {
        switch (wynik) {
            case 1 -> System.out.println("Wygrales!");
            case 2 -> System.out.println("Przegrales!");
            case 0 -> System.out.println("Remis!");
            default -> {
            }
        }
        System.out.println("Czy chcesz grac dalej? (T/N)");
        Scanner scanner = new Scanner(System.in);
        String i = scanner.next();
        if (i.equalsIgnoreCase("T")) {
            resetPlansza();
        } else {
            System.exit(0);
        }
    }

    private static void resetPlansza() {
        ustawPlansze();
        userPoz.clear();
        kompPoz.clear();
    }
}
