import java.io.BufferedReader;
import java.io.FileReader;

public class StockPriceMgmt {
	static double purchasePrice = 1560.11;
	static int numOfStock = 100;
	static int buypercent = 2;
	static int sellpercent = 2;
	static int profitpercent = 25;
	public static void main(String[] args) {
		AggressivePath();
		//ConservativePath();
	}
	
	public static void AggressivePath() {
		// TODO Auto-generated method stub
				double openPrice = 0.0;
				BufferedReader reader;
				double originalCost = 0.0;
				try {
					System.out.println("Originally purchased "+numOfStock+" stocks for "+ purchasePrice+" = "+  purchasePrice * numOfStock);
					originalCost =  purchasePrice * numOfStock;
					reader = new BufferedReader(new FileReader(
							"AMZN.csv"));
					String line = reader.readLine();
					boolean isSold = false;
					boolean isBuy = true;
					double moneyAvailableForPurchase = 0;
					double oldOpenPrice = 0;
					boolean isProfit = false;
					while (line != null) {
						String[] parseLine = line.split(",");
						openPrice = Double.parseDouble(parseLine[1]);
						double highPrice = Double.parseDouble(parseLine[2]);
						double lowPrice = Double.parseDouble(parseLine[3]);
						double closePrice = Double.parseDouble(parseLine[4]);
						//System.out.println(openPrice +" "+highPrice+" "+lowPrice+" "+closePrice);
						if(isBuy == true) {
							
							//sell stocks
							if(StockDown(openPrice, purchasePrice) == true) {
								//loss
								isProfit = false;
								moneyAvailableForPurchase=(openPrice*numOfStock) + moneyAvailableForPurchase;
								
								isSold = true;
								isBuy = false;
								System.out.println("sold "+numOfStock+" stocks at "+sellpercent+"% loss "+openPrice+" money available for purchase:"+moneyAvailableForPurchase);
								oldOpenPrice = openPrice;
							}
							
							if(StockProfit(openPrice, purchasePrice) == true) {
								//profit
								isProfit = true;
								moneyAvailableForPurchase=(openPrice*numOfStock) + moneyAvailableForPurchase;
								
								isSold = true;
								isBuy = false;
								System.out.println("sold "+numOfStock+" stocks at "+profitpercent+"% profit :"+openPrice+" money available for purchase:"+moneyAvailableForPurchase);
								oldOpenPrice = openPrice;
							}
							
							numOfStock = 0;
							
						}
						
						if(isSold == true) {
							//buy stocks
							if(StockUpAggressive(openPrice, oldOpenPrice, isProfit) == true) {
								
								if((int) (moneyAvailableForPurchase / openPrice)>=1) {
									numOfStock = (int) (moneyAvailableForPurchase / openPrice);
									purchasePrice = openPrice;
									System.out.println("buy at:"+openPrice);
									isSold = false;
									isBuy = true;
									moneyAvailableForPurchase = moneyAvailableForPurchase - (openPrice * numOfStock);
									System.out.println("purchased "+numOfStock+" stocks at:"+buypercent+"% up "+openPrice+" money available for purchase:"+moneyAvailableForPurchase);
									
								}
							}
							
							oldOpenPrice = openPrice;
							
						}
						
						System.out.println("-------------------------------------------------------------");
						
						line = reader.readLine();
					}
					
					double endBalance = (openPrice * numOfStock)+moneyAvailableForPurchase;
					
					System.out.println("Aggressive Ending Balance:"+endBalance+ " with net change of:"+(endBalance-originalCost));
					
				}catch(Exception ex) {
					ex.printStackTrace();
					
				}

	}
	
	public static void ConservativePath() {
		// TODO Auto-generated method stub
				double openPrice = 0.0;
				BufferedReader reader;
				double originalCost = 0.0;
				try {
					System.out.println("Originally purchased "+numOfStock+" stocks for "+ purchasePrice+" = "+  purchasePrice * numOfStock);
					originalCost =  purchasePrice * numOfStock;
					reader = new BufferedReader(new FileReader(
							"AMZN.csv"));
					String line = reader.readLine();
					boolean isSold = false;
					boolean isBuy = true;
					double moneyAvailableForPurchase = 0;
					while (line != null) {
						String[] parseLine = line.split(",");
						openPrice = Double.parseDouble(parseLine[1]);
						double highPrice = Double.parseDouble(parseLine[2]);
						double lowPrice = Double.parseDouble(parseLine[3]);
						double closePrice = Double.parseDouble(parseLine[4]);
						System.out.println(openPrice +" "+highPrice+" "+lowPrice+" "+closePrice);
						if(isBuy == true) {
							//sell stocks
							if(StockDown(openPrice, purchasePrice) == true) {
								
								moneyAvailableForPurchase=(openPrice*numOfStock) + moneyAvailableForPurchase;
								purchasePrice = openPrice;
								isSold = true;
								isBuy = false;
								System.out.println("sold "+numOfStock+" stocks at:"+openPrice+" money available for purchase:"+moneyAvailableForPurchase);
							}
						}
						
						if(isSold == true) {
							//buy stocks
							if(StockUp(openPrice, purchasePrice) == true) {
								
								if((int) (moneyAvailableForPurchase / openPrice)>=1) {
									numOfStock = (int) (moneyAvailableForPurchase / openPrice);
									purchasePrice = openPrice;
									System.out.println("buy at:"+openPrice);
									isSold = false;
									isBuy = true;
									moneyAvailableForPurchase = moneyAvailableForPurchase - (openPrice * numOfStock);
									System.out.println("purchased "+numOfStock+" stocks at:"+openPrice+" money available for purchase:"+moneyAvailableForPurchase);
									
								}
							}
						}
						
						
						line = reader.readLine();
					}
					
					double endBalance = (openPrice * numOfStock)+moneyAvailableForPurchase;
					
					System.out.println("Conservative Ending Balance:"+endBalance+ " with net change of:"+(endBalance-originalCost));
					
				}catch(Exception ex) {
					ex.printStackTrace();
					
				}

	}
	public static boolean StockUp(double currentPrice, double purchasePrice) {
		boolean isBuy = false;
		double percentMore = purchasePrice*(1 + (buypercent/100.00));
		if(currentPrice > percentMore) {
			isBuy = true;
			System.out.println("BUY Current Price:"+currentPrice+" > "+percentMore);
		}
		
		return isBuy;
	}
	
	public static boolean StockUpAggressive(double currentPrice, double purchasePrice, boolean isProfit) {
		boolean isBuy = false;
		double percentMore = 0;
		if(isProfit == true) {
			percentMore = purchasePrice*(1 + (buypercent/100.00));
		}else {
			percentMore = purchasePrice*(1 + (buypercent/200.00));
		}
		
		if(currentPrice > percentMore) {
			isBuy = true;
			System.out.println("BUY Current Price:"+currentPrice+" > "+percentMore);
		}
		return isBuy;
	}
	
	public static boolean StockProfit(double currentPrice, double purchasePrice) {
		boolean isBuy = false;
		double percentMore = purchasePrice*(1 + (profitpercent/100.00));
		if(currentPrice > percentMore) {
			isBuy = true;
			System.out.println("BUY Current Price:"+currentPrice+" > "+percentMore);
		}
		return isBuy;
	}
	
	public static boolean StockDown(double currentPrice, double purchasePrice) {
		boolean isSell = false;
		
		double percentMore = purchasePrice*(1.00 - (sellpercent/100.00));
		System.out.println(percentMore+"="+purchasePrice+"*("+1+"-("+sellpercent+"/"+100.00+"))");
		if(currentPrice < percentMore) {
			isSell = true;
			System.out.println("SELL Current Price:"+currentPrice+" < "+percentMore);
		}
		return isSell;
	}

}
