package Blackjack;

public class Options {
		String handWinner;
		boolean doubleDown, split, endRound, bust, checked, stand;

		public Options(String handWinner, boolean doubleDown, boolean split, boolean endRound, boolean bust, boolean checked, boolean stand) {
			this.handWinner = handWinner;
			this.doubleDown = doubleDown;
			this.split = split;
			this.endRound = endRound;
			this.bust = bust;
			this.checked = checked;
			this.stand = stand;
		}
		
		//methods to recall object values
		public String getWinner() {
			return this.handWinner;
		}
		
		public boolean getDoubleDown() {
			return this.doubleDown;
		}
		
		public boolean getSplit() {
			return this.split;
		}		
		
		public boolean getEndRound() {
			return this.endRound;
		}
		
		public boolean getBust() {
			return this.bust;
		}
		
		public boolean getChecked() {
			return this.checked;
		}
		
		public boolean getStand() {
			return this.stand;
		}
		
		//methods to set object values
		
		public void setWinner(String hW) {
			this.handWinner = hW;
		}
		
		public void setDoubleDown(boolean dD) {
			this.doubleDown = dD;
		}

		public void setSplit(boolean sp) {
			this.split = sp;
		}

		public void setEndRound(boolean eR) {
			this.endRound = eR;
		}
		
		public void setBust(boolean b) {
			this.bust = b;
		}

		public void setChecked(boolean c) {
			this.doubleDown = c;
		}
		
		public void setStand(boolean st) {
			this.doubleDown = st;
		}
}
