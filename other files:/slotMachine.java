import java.util.Scanner;

class Scratch {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);

        String[]slots = {"Cherry","Bell","Bar","7","Diamond","Lemon","Horseshoe","Grape","Watermelon","Gold Coin"};
        int points = 5;
        int wordnum;
        int[]matcher = {0,0,0};
        boolean match;

        System.out.println("Do you want to spin? You have 5 points");
        String answer = scan.next();

        while((answer.equals("yes") || answer.equals("Yes") || answer.equals("YES")) && (points!=0)){
            System.out.print("|");
            for (int i = 0; i<3; i++ ){
                wordnum = (int)(Math.random()*10);
                System.out.print(slots[wordnum] + "|");
                matcher[i] = wordnum;
            }
            if(matcher[0]==matcher[1] && matcher[0]==matcher[2]){
                points += 2;
                match = true;
            }else if(matcher[0]!=matcher[1] && matcher[0]!=matcher[2] && matcher[1]!=matcher[2]){
                points -= 1;
                match = false;
            }else{
                points += 1;
                match = true;
            }
            System.out.println();
            if(match){
                System.out.println("Match!! Points = " + points);
            }else{
                System.out.println("Points = " + points);
            }
            if(points == 0){
                System.out.println();
                System.out.println("You ran out of points");
            }else {
                System.out.println();
                System.out.println("Do you want to spin again? You have " + points + " points.");
                answer = scan.next();
            }
        }
        System.out.println("Thanks for playing! Goodbye.");
    }
}
