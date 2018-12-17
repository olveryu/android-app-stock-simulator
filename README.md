
Preston Meade
Edwin Yan

Stock Market Simulation App Summary: An app where a user can register/login. And try their luck
at the stock market with virtual money in game. Users can buy and sell stocks at their live real market
price and save progress any time. Users have access to all exchanges and can view important statistics
such as return, cash available to trade, total gain/loss, stock holdings, and more. 

User Stories: 
	
1. As a user, I want to be able to register with an E-mail and password, so that I can use the 
   stock market app. [ Using Firebase ] 

2. As a user, I want to be able login using my E-mail and password, so that I can trade stocks
   and see my progress. [ Using Firebase ] 

3. As a user, I want to use virtual money to be able to buy and sell stocks so that I can 
   practice trading on the stock market. [ Using Firebase ] 

4. As a user, I want to be able to buy and sell stocks at their real time market value, so that I can
   experience fluctuation in market prices. [ Using API ] 

5. As a user, I want to be able to see my portfolio and all my stock holdings, so that I can see my 
   total value, total percentage gain/loss, my stocks, and better understand the stock market.
   [ Using Firebase ] 

6. As a user, I want to be able to sign out, so that I can exit the game, or sign in with a new user.
   [ Using Firebase ] 

7. As a user, I want to be able to look up stocks by their Market Symbol, so that I can see general
   information about the stock and its performance. [ Using API ] 

8. As a user, I want to be able to see a graph of my stocks price and portfolio value over time, so that 
   I can track my progress and see market trends. [ Using Firebase/API ] 

9. As s user, I want a sleek and easy to use User Interface for the app, so that I can navigate and have a better
   experience using the app. 



Priorities:
 
 First Priority: Retrieve live stock data from Iextrading API. This is used to allow users to 
                  buy and sell at real life prices and gather relevant stock information for the user.
 
 Second Priority: Store user stocks and portfolio in the Firebase database to record and save game. 

 Third Priority: Implement a UI that uses live data and displays on the screen simply so its easy to use.

 Fourth Priority: Implement Registration/Login use cases to ensure separate user accounts are created for each game. 


Splash Page 
-	Login page 
o	Go to Home Page 
-	Register Page 	
o	Go to Register Page 

Register Page 
-	Register and go to login page 
 
Home Page
-	Sign out
o	Logs out user 
-	Search
o	Search by symbol and see more info -> Info Stock
  
-	Explore current holdings (List of all holdings with price) 
o	Opens a info activity more detailed about stock -> Prompt to buy 

Info Stock
-	Live details 
-	 Buy/sell 
-	Return to Home 








Diagram of pages 

 








Wire Frame of Layouts 



 Splash 						Login 
 	 
















	Register 					Home Portfolio
 		 

























Stock Detail Info Page

 



















Part 2

API: api.iextrading.com/1.0 
This provides real time stock data and past historical data 
https://iextrading.com/developer/docs/

Database: Firebase 
This allows storage of user accounts and their stock portfolio  

Retrofti2: Used to retrieve JSON from the API and create custom api calls 
JSON data is loaded into POJ and then updated in the firebase database. 














Time Line

Nov 1. Create Idea 
Nov 2. Split work load between team 
Nov 2. Create Use Cases and sketch the UI 
Nov 3. Implement Firebase and API calls 
Nov 5. Use API calls to buy and sell stocks 
Nov 6. Use Firebase to authentication Users and login/register 
Nov 9. Develop UI to display stock data and user portfolio 
Nov 14. Develop Info Detail Page to load data about a single stock
Nov 20. Ability to buy and sell stocks 
Nov 21. Ability to look up a stock
Nov 21. Graph data of day, month, year for stock.
Nov 28. Test with new accounts and bug test. 
Nov 30. Review and Ensure code is ready and improved.
Nov 30. Test to ensure proper data and calculations of stocks.

