package cdp.ui;

public enum Action {
	LIST_COMPUTER, LIST_COMPANIES, SHOW_COMPUTER, CREATE_COMPUTER, UPDATE_COMPUTER, DELETE_COMPUTER, QUIT;
	
	public static Action getAction(int i) {
		Action answer = null;
		switch(i) {
			case 1: 
				answer = LIST_COMPUTER;
				break;
			case 2:
				answer = LIST_COMPANIES;
				break;
			case 3:
				answer = SHOW_COMPUTER;
				break;
			case 4:
				answer = CREATE_COMPUTER;
				break;
			case 5:
				answer = UPDATE_COMPUTER;
				break;
			case 6:
				answer = DELETE_COMPUTER;
				break;
			case 7:
				answer = QUIT;
				break;
			default: 
				break;
		}
		return answer;
	}
	
}
