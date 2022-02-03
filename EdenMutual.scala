import java.util.Scanner
import java.sql.DriverManager
import java.sql.Connection
object EdenMutual{
    def main(args: Array[String]): Unit = {
        var driver = "com.mysql.jdbc.Driver"
        var url = "jdbc:mysql://localhost:3306/project0"
        var username = "root"
        val password = "000B@nk@i000"

        var connection:Connection = null

        try{
          Class.forName(driver)
          connection = DriverManager.getConnection(url, username, password)

          val statement = connection.createStatement()
          
          val resultSet = statement.executeQuery("SELECT * FROM accounts")
          while(resultSet.next()){
            print(" " + resultSet.getString(1) + " " + resultSet.getString(2) + " " +
            resultSet.getString(3) + " " + resultSet.getString(4))
            println()
          }
        } catch {
          case e: Exception => e.printStackTrace
        }
       
        var scan = new Scanner(System.in)
        var mainOpt = 0
        var acctStr = ""
        println("Hello and welcome to Eden Mutual Bank!")
        println("Please select an option to begin:")
        println("[1]-[Open an account] " + "[2]-[Access an account] " +
        "[0]-[Exit]")
        mainOpt = scan.nextInt()
        scan.nextLine()
        while (mainOpt != 0){
            if (mainOpt == 1) {
                println("You chose to open an account.")
                acctStr = newAccount()
             
                 try{
                    Class.forName(driver)
                    connection = DriverManager.getConnection(url, username, password)
                    val statement = connection.createStatement()
                    val resultSet = statement.executeUpdate("INSERT INTO Accounts (FirstName, LastName, " + 
                    " AccountType," + " Balance, Pin) VALUES (" + acctStr + ");")
                    } catch {
                      case e: Exception => e.printStackTrace
                    }
                mainOpt == 0
            } else if (mainOpt == 2) {
                println("You chose to access your account.")
                acctAccess()
                mainOpt == 0
            } 
            println("Hello and welcome to Eden Mutual Bank!")
            println("Please select an option to begin:")
            println("[1]-[Open an account] " + "[2]-[Access an account] " +
            "[0]-[Exit]")
            mainOpt = scan.nextInt()
            scan.nextLine()
        }
        println("Thank you for banking with us. Well then, good bye...")
        

    
   def newAccount(): String = {
      //*********New Account*********
        var acctOpt = 0
        var accountType = ""
        var acctBal = 0.00
        var fName = ""
        var lName = ""
        var pinNum = 0
        var scan = new Scanner(System.in)
        
        val statement = connection.createStatement()
        var resultStr = ""
        

        println("Please select the type of account you would like to open:")
        println("[1]-[Checking Account] " + "[2]-[Savings Account] ")
        acctOpt = scan.nextInt()
        scan.nextLine()
        if (acctOpt == 1) {
            println("You chose to open a Checking Account.")
            accountType = "Checking"
        } else if (acctOpt == 2) {
            println("You chose to open a Savings Account.")
            accountType = "Savings"
        } 
  

        println("Please enter your first name:")
        fName = scan.nextLine();        
        println("Please enter your last name:")
        lName = scan.nextLine()        
        println("Please enter a 4-digit pin number: ")
        pinNum = scan.nextInt()
        println("How much would you like to open your acccount with?")
        acctBal = scan.nextDouble()
        scan.nextLine()
       
        println("Alright  just a moment, " + fName + "...")
       
        return "'" + fName + "' , '" + lName + "', '" + accountType + "', " + acctBal +
           ", " + pinNum
    }

    def acctAccess(): Unit = {
      //********Access Account*******
        var myAcctOpt = 0
        var accountType = ""
        var accountId = 0
        var fName = ""
        var lName = ""
        var scan = new Scanner(System.in)
        var pin = 1234
        var deposit = 0.00
        var withdraw = 0.00
        val statement = connection.createStatement()

        println("Please enter your first name:")
        fName = scan.nextLine()
        println("Please enter your last name:")
        lName = scan.nextLine()
        println("Please enter your pin")
        pin = scan.nextInt()
       
        if (pin != 1234){
          println("Sorry, the name and pin number you entered do not match our records. Please try again...")
          
          println( "...or consider opening an account with us.")
        } else {

            println( "Welcome " + fName + " " + lName + "!")
            println("Please select an account to access:")
            println("[1]-[Checking Account] " + "[2]-[Savings Account]")
            myAcctOpt = scan.nextInt()

            if (myAcctOpt == 1) {
                println("You chose to access your Checking Account.")
                accountType = "Checking"
            } else if (myAcctOpt == 2) {
                println("You chose to access your Savings Account.");
                accountType = "Savings"
            }

            /*try{
                Class.forName(driver)
                connection = DriverManager.getConnection(url, username, password)

                val statement = connection.createStatement()
                
                val resultSet = statement.executeQuery("SELECT AccountID FROM accounts WHERE FirstName = " + 
                  "'" + fName + "'" + " AND " +"LastName = " + "'" + lName + "'" + " AND AccountType = " + 
                  "'" + accountType + "'" + ";")
                  println("Here's the result set: " + resultSet)
                accountId = resultSet.getInt("AccountId")
                } catch {
                        case e: Exception => e.printStackTrace
                        }*/

            println("What would you like to do?")
            println("[1]-[Make a deposit] " + "[2]-[Make a withdrawal] " +
            "[3]-[Close account] ")
            myAcctOpt = scan.nextInt()

            if (myAcctOpt == 1) {
                println("You chose to make a deposit.")
                println("How much would you like to deposit?")
                deposit = scan.nextFloat()
                println("Account ID?: ")
                accountId = scan.nextInt()
                
                statement.executeUpdate("UPDATE Accounts SET Balance = Balance + '" + deposit + "' WHERE AccountID = "+ accountId + ";")
                
                  /*tatement.executeUpdate("UPDATE Accounts SET Balance " +
                  " = Balance +" + "'" + deposit + "'" + " WHERE AccountID = " +accountId"/* + "'" + fName + "'" + " AND " +
                  "LastName = " + "'" + lName + "'" + " AND AccountType = " + "'" + accountType + "'"*/ + ";")
                */
            } else if (myAcctOpt == 2) {
                println("You chose to make a withdrawal.");
                println("How much would you like to withdraw?")
                withdraw = scan.nextDouble()
                
            } else if (myAcctOpt == 3) {
                println("You chose to close your account. ");
                println("AccountId: ")
                accountId = scan.nextInt()

                statement.executeUpdate("DELETE FROM Accounts WHERE AccountID = "+ accountId + ";")
                

                /*println("Are you sure you would like to close your account?")
                println("[1]-[Yes] " + "[2]-[No] ")
                myAcctOpt = scan.nextInt()
                if (myAcctOpt == 1) {*/
                  
                println("Well, we're sad to see you go, but we really appreciate your business" +
                  " and we thank you for banking with us!")
                println("Your all set. This account is now closed. Take care!")                
                /*} else {
                  println("No worries. No worries. Now back to the Main Menu.")
                }*/
            }
        }
    }
    connection.close()
        
  }
}