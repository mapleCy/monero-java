package monero.wallet;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.List;

import monero.wallet.model.MoneroAccount;
import monero.wallet.model.MoneroAddressBookEntry;
import monero.wallet.model.MoneroException;
import monero.wallet.model.MoneroSubaddress;
import monero.wallet.model.MoneroTx;
import monero.wallet.model.MoneroTxConfig;

/**
 * Default implementation of a Monero Wallet.
 */
public abstract class MoneroWalletDefault implements MoneroWallet {

  @Override
  public List<MoneroSubaddress> getSubaddresses(int accountIdx) {
    return getSubaddresses(accountIdx, null);
  }
  
  @Override
  public MoneroSubaddress getSubaddress(int accountIdx, int subaddressIdx) {
    List<MoneroSubaddress> subaddresses = getSubaddresses(accountIdx, Arrays.asList(subaddressIdx));
    if (subaddresses.size() != 1) throw new MoneroException("Subaddress at index " + subaddressIdx + " does not exist");
    return subaddresses.get(0);
  }

  @Override
  public List<MoneroTx> getTxs() {
    return getTxs(null);
  }
  
  @Override
  public MoneroAccount createAccount() {
    return createAccount(null);
  }
  
  @Override
  public MoneroSubaddress createSubaddress(int accountIdx) {
    return createSubaddress(accountIdx,  null);
  }
  
  @Override
  public MoneroTx send(String address, BigInteger amount) {
    return send(new MoneroTxConfig(address, null, amount));
  }
  
  @Override
  public MoneroTx send(String address, String paymentId, BigInteger amount) {
    return send(new MoneroTxConfig(address, paymentId, amount));
  }
  
  @Override
  public List<MoneroTx> sendSplit(String address, BigInteger amount) {
    return sendSplit(new MoneroTxConfig(address, null, amount));
  }
  
  @Override
  public List<MoneroTx> sendSplit(String address, String paymentId, BigInteger amount) {
    return sendSplit(new MoneroTxConfig(address, paymentId, amount));
  }
  
  @Override
  public List<MoneroAddressBookEntry> getAddressBookEntries() {
    return getAddressBookEntries(null);
  }
  
  @Override
  public int addAddressBookEntry(String address, String description) {
    return addAddressBookEntry(address, null, description);
  }
}