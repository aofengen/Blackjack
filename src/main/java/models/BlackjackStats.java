package models;

public class BlackjackStats {
	private int handsPlayed, handsWon, blackjacks, highMoney, totalMoney;
	
	public BlackjackStats() {
		this.handsPlayed = 0;
		this.handsWon = 0;
		this.blackjacks = 0;
		this.highMoney = 0;
		this.totalMoney = 0;
	}
	
	public int getHandsPlayed() {
		return this.handsPlayed;
	}
	
	public void setHandsPlayed(int hP) {
		this.handsPlayed = hP;
	}
	
	public int getHandsWon() {
		return this.handsWon;
	}
	
	public void setHandsWon(int hW) {
		this.handsWon = hW;
	}
	
	public int getBlackjacks() {
		return this.blackjacks;
	}
	
	public void setBlackjacks(int b) {
		this.blackjacks = b;
	}
	
	public int getHighMoney() {
		return this.highMoney;
	}
	
	public void setHighMoney(int hM) {
		this.highMoney = hM;
	}
	
	public int getTotalMoney() {
		return this.totalMoney;
	}
	
	public void setTotalMoney(int tM) {
		this.totalMoney = tM;
	}
}
