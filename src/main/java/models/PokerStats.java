package models;

public class PokerStats {
	
	private int highMoney, totalMoney, handsPlayed, handsWon, royalFlush, straightFlush, fourKind, flush, straight, threeKind;
	
	public PokerStats() {
		this.highMoney = 0;
		this.totalMoney = 0;
		this.handsPlayed = 0;
		this.handsWon = 0;
		this.royalFlush = 0;
		this.straightFlush = 0;
		this.fourKind = 0;
		this.flush = 0;
		this.straight = 0;
		this.threeKind = 0;
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
	
	public int getRoyalFlush() {
		return this.royalFlush;
	}
	
	public void setRoyalFlush(int rF) {
		this.handsWon = rF;
	}
	
	public int getStraightFlush() {
		return this.straightFlush;
	}
	
	public void setStraightFlush(int sF) {
		this.straightFlush = sF;
	}
	
	public int getFourKind() {
		return this.fourKind;
	}
	
	public void setFourKind(int fK) {
		this.fourKind = fK;
	}
	
	public int getFlush() {
		return this.flush;
	}
	
	public void setFlush(int f) {
		this.flush = f;
	}
	
	public int getStraight() {
		return this.straight;
	}
	
	public void setStraight(int s) {
		this.straight = s;
	}
	
	public int getThreeKind() {
		return this.threeKind;
	}
	
	public void setThreeKind(int tK) {
		this.threeKind = tK;
	}
}
