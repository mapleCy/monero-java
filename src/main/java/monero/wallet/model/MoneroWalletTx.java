package monero.wallet.model;

import java.math.BigInteger;
import java.util.List;

import monero.daemon.model.MoneroOutput;
import monero.daemon.model.MoneroTx;
import monero.utils.MoneroException;

/**
 * Models a Monero transaction with wallet extensions.
 */
public class MoneroWalletTx extends MoneroTx {

  private BigInteger incomingAmount;
  private BigInteger outgoingAmount;
  private List<MoneroTransfer> incomingTransfers;
  private MoneroTransfer outgoingTransfer;
  private String note;
  
  public boolean getIsOutgoing() {
    return getOutgoingTransfer() != null;
  }
  
  public boolean getIsIncoming() {
    return getIncomingTransfers() != null;
  }
  
  public BigInteger getIncomingAmount() {
    return incomingAmount;
  }
  
  public void setIncomingAmount(BigInteger incomingAmount) {
    this.incomingAmount = incomingAmount;
  }
  
  public BigInteger getOutgoingAmount() {
    return outgoingAmount;
  }
  
  public void setOutgoingAmount(BigInteger outgoingAmount) {
    this.outgoingAmount = outgoingAmount;
  }
  
  public List<MoneroTransfer> getIncomingTransfers() {
    return incomingTransfers;
  }
  
  public void setIncomingTransfers(List<MoneroTransfer> incomingTransfers) {
    this.incomingTransfers = incomingTransfers;
  }
  
  public MoneroTransfer getOutgoingTransfer() {
    return outgoingTransfer;
  }
  
  public void setOutgoingTransfer(MoneroTransfer outgoingTransfer) {
    this.outgoingTransfer = outgoingTransfer;
  }
  
  @SuppressWarnings("unchecked")
  public List<MoneroWalletOutput> getVouts() {
    return (List<MoneroWalletOutput>) super.getVouts();
  }
  
  public void setVouts(List<? extends MoneroOutput> vouts) {
    
    // validate that all vouts are wallet outputs
    if (vouts != null) {
      for (MoneroOutput vout : vouts) {
        if (!(vout instanceof MoneroWalletOutput)) throw new MoneroException("Wallet transaction vouts must be of type MoneroWalletOutput");
      }
    }
    super.setVouts(vouts);
  }
  
  public String getNote() {
    return note;
  }
  
  public void setNote(String note) {
    this.note = note;
  }
  
  public MoneroWalletTx copy() {
    throw new RuntimeException("Not implemented");
  }
  
  public MoneroWalletTx merge(MoneroTx tx) {
    if (tx != null && !(tx instanceof MoneroWalletTx)) throw new MoneroException("Wallet transaction must be merged with type MoneroWalletTx");
    return merge((MoneroWalletTx) tx);
  }
  
  public MoneroWalletTx merge(MoneroWalletTx tx) {
    throw new RuntimeException("Not implemented");
  }
}
