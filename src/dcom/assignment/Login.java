import java.util.Scanner;


public class Login{
    private String password;

    String ICnum = 0123456789012;

    public string input(){
        Scanner scan = new Scanner(System.in);
       
        while(true)
        {   System.out.println("Please enter your password: ");
            this.password = scan.nextLine();
            if (password.matches(regex:"\\d{12}")){
                if (password == ICnum)
                {
                    break;
                }
                else 
                {
                    System.out.println("Password invalid");
                }
            }
            else {
                System.out.println("Invalid validation!");
            }
        }
        
    }

}
