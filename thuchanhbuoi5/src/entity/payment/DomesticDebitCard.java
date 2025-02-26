package entity.payment;


// Nguyen Manh Duc - 20173039


public class DomesticDebitCard extends PaymentCard{	
	protected String owner;
	protected String dateExpired;
	private String bank;
	private String cardNumber;
	
	public DomesticDebitCard(String bank, String owner, String cardNumber, String dateExpired) {
		this.owner = owner;
		this.dateExpired = dateExpired;
		this.bank = bank;
		this.cardNumber = cardNumber;
	}

	public String getBank() {
		return bank;
	}

	public void setBank(String bank) {
		this.bank = bank;
	}

	public String getCardNumber() {
		return cardNumber;
	}

	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}
}