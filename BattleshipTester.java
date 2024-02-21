/*
Written by:	Maeve Carr
Date:
Desc: 
Filename:
*/

/////YOU WILL NOT NEED THIS CODE - BUT HAVE INCLUDED IT FOR TESTING
/////no proper error checking..


import java.util.*;

public class BattleshipTester
{
	public static void main(String[] args)
	{
		Scanner keyIn = new Scanner(System.in);
		BattleshipsGame myGame = new BattleshipsGame();

	//	myGame.setNoOfShips(6);
	
		System.out.println();
		//just so I can see where ships are - for testing
		myGame.showGrid();

		do{
			System.out.print("Enter location\n\tRow: ");
			int row = keyIn.nextInt();	   // should validate input here so value between 0 and 3
			System.out.print("\tColumn: ");
			int col = keyIn.nextInt();
			
			String message = myGame.shoot(row, col);
			
			System.out.println(message);
		 
       //  System.out.println("No of ships hit: " +myGame.getHits()); //for testing

		}while(myGame.getLives() > 0 &&  myGame.getHits() < 6);	
		
		if(myGame.getLives() == 0)
         System.out.print("Sorry - you lose ");
      else
         System.out.print("WELL DONE - YOU WIN! ");
   
		System.out.println();
		myGame.showGrid();

	}
}

