import java.util.*;
import java.io.*;

public class MathWorksheetGenerator{
   static char[] operands;
   
   static String filePath = "";
   static String filePathAnswers = "";
   static String fastMathSavePath = "";
   static BufferedWriter mathWriter;
   static BufferedWriter answerWriter;
   static String date = "";
   
   static int total;
   static int correct;
   static boolean playing = true;

   public static void main(String[] args){
      Scanner input = new Scanner(System.in);
      Random rand = new Random();
      
      System.out.println("[p]rint worksheet or [f]ast math?");
      char choice = input.next().charAt(0);
      System.out.println("\nToday's date?");
      String useless = input.nextLine();
      date = input.nextLine();
      
      // Trig option has not been implemented yet
      System.out.println("[s]ingle operation or [m]ixed operations or [t]rig?");
      char letter = input.next().charAt(0);
      fillOperands(letter, input);   
      
      if(choice == 'p'){
         nameFile(input);
         printFile(input, rand);     
      } else {
         System.out.print("Maximum # for 1st digit: ");
         int max1 = input.nextInt();
         System.out.print("\nMinimum # for 1st digit: ");
         int min1 = input.nextInt();
         System.out.print("\nMaximum # for 2nd digit: ");
         int max2 = input.nextInt();
         System.out.print("\nMinimum # for 2nd digit: ");
         int min2 = input.nextInt();
         System.out.println("\nCool! Type [x] to exit.\n");
         
         while(playing){
            playing = printMath(input, rand, max1, min1, max2, min2);
         }
         
         int percent = (int)((((double) correct) / ((double) total)) * 100);
         
         System.out.println("STATS\n" + percent + "%\n# Problems Answered: " + total + "\n# Correct: " + correct);
         String save = "\n" + date + "\n" + percent + "%\n" + correct + "/" + total;
         
         File file = new File(fastMathSavePath);
         try(FileWriter og = new FileWriter(file, true)){
            BufferedWriter writer = new BufferedWriter(og);
            writer.write(save);
            writer.newLine();
            writer.close();
            
         }catch (IOException ex){
            System.out.println("Save Failed :(");
         }
         
      }
      
   }
   
   public static boolean printMath(Scanner input, Random rand, int max1, int min1, int max2, int min2){
      
      
      int first = rand.nextInt(max1 - min1 + 1) + min1;
      int second = rand.nextInt(max2 - min2 + 1) + min2;
      int opFinder = rand.nextInt(operands.length);
      char operand = operands[opFinder];
      int answer = findAnswer(first, second, operand);
      System.out.print((total+1) + ") " + first + " " + operand + " " + second + " = ");
      String next = input.next();
      char charVal = next.charAt(0);
      int uAnswer = -42;
      
      
      if(charVal == 'x'){
         return false;
      } else{
         try{
            uAnswer = Integer.parseInt(next);
         }
         catch(NumberFormatException ex){
            System.out.println("Invalid answer.");
            return true;
         }
         
      }
      
      if(answer == uAnswer){
         System.out.println("\nCorrect!\n");
         correct++;
      } else{
         System.out.println("\nWRONG :( " + answer + "\n");
      }
      
      total++;
      
      return true;
   }
   
   public static Map<String, String> loadTrigMap() {
      Map<String, String> trigMap = new HashMap<>();
      Scanner trigFile = new Scanner();
   }
   
   public static void printFile(Scanner input, Random rand){
      System.out.print("\nMaximum # for 1st digit: ");
      int max1 = input.nextInt();
      System.out.print("\nMinimum # for 1st digit: ");
      int min1 = input.nextInt();
      System.out.print("\nMaximum # for 2nd digit: ");
      int max2 = input.nextInt();
      System.out.print("\nMinimum # for 2nd digit: ");
      int min2 = input.nextInt();
      System.out.println();
      
      for(int i = 0; i < 50; i++){
         for(int j = 0; j < 4; j++){
            int first = rand.nextInt(max1 - min1 + 1) + min1;
            int second = rand.nextInt(max2 - min2 + 1) + min2;
            int opFinder = rand.nextInt(operands.length);
            char operand = operands[opFinder];
            int answer = findAnswer(first, second, operand);
            
            String buffer1 = " ";
            String buffer2 = " ";
            String buffer3 = " ";
            if(first < 10){
               buffer1 += " ";
            }
            if(second < 10){
               buffer2 += " ";
            }
            if(answer < 10) {
               buffer3 += "  ";
            } else if (answer < 100) {
               buffer3 += " ";
            }
            
            try {
               mathWriter.write(first + buffer1 + operand + buffer2 + second + " = _____       ");
               answerWriter.write(first + buffer1 + operand + buffer2 + second + " =" + buffer3 + answer + "       ");
            } catch (IOException e) {
               e.printStackTrace();
            }
         }
         
         try {
               mathWriter.newLine();
               answerWriter.newLine();
            } catch (IOException e) {
               e.printStackTrace();
            }
      }
      
      try {
         mathWriter.close();
         answerWriter.close();  
      } catch (IOException e) {
         e.printStackTrace();
      }
   }
   
   public static int findAnswer(int first, int second, char operand){
      if(operand == '+'){
         return first + second;
      } else if(operand == '-'){
         return first - second;
      } else if(operand == '/'){
         return first / second;
      } else if(operand == '*'){
         return first * second;
      } else if(operand == '%'){
         return first % second;
      } else{
         System.out.println("TACOS");
         return -42;
      }
   }
   
   public static void fillOperands(char letter, Scanner input){
      if(letter == 'm'){
         System.out.println("How many operands?");
         int length = input.nextInt();
         operands = new char[length];
         
         for(int i = 0; i < length; i++){
            System.out.print("Operand " + (i+1) + ": ");
            char next = input.next().charAt(0);
            operands[i] = next;
            System.out.println();
         }
      } else{
         operands = new char[1];
         System.out.print("Operand: ");
         char next = input.next().charAt(0);
         operands[0] = next;
         System.out.println();
      }
   }
   
   public static void nameFile(Scanner input){
      System.out.println("What would you like to name your file?");
      String help = input.nextLine();
      String fileName = input.nextLine();
      filePath += "\\" + fileName + ".txt";
      filePathAnswers += "\\" + fileName + ".txt";
      
      try{
         mathWriter = new BufferedWriter(new FileWriter(filePath, true));
         answerWriter = new BufferedWriter(new FileWriter(filePathAnswers, true));
      } catch(IOException e) {
         e.printStackTrace();
      }
   }
}