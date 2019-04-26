package utils;

import monero.daemon.MoneroDaemon;
import monero.daemon.model.MoneroKeyImage;
import monero.daemon.model.MoneroKeyImageSpentStatus;
import monero.wallet.MoneroWallet;
import monero.wallet.config.MoneroVoutFilter;
import monero.wallet.model.MoneroOutputWallet;
import monero.wallet.model.MoneroTxWallet;

/**
 * Scratchpad for quick scripting.
 */
public class Scratchpad {

  public static void main(String[] args) {
    
    // initialize daemon, wallet, and direct rpc interface
    MoneroDaemon daemon = TestUtils.getDaemonRpc();
    MoneroWallet wallet = TestUtils.getWalletRpc();
    //MoneroRpc rpc = new MoneroRpc(TestUtils.WALLET_RPC_CONFIG);
    
//    // common variables
//    List<MoneroTx> txs = null;
//    List<MoneroTransfer> transfers = null;
//    String txId = null;
//    List<MoneroOutput> vouts = null;
    
    // -------------------------------- SCRATCHPAD ----------------------------
        
//    wallet.getAccounts(true);
    
    String keyImage = "f4c6123ea4daa831d850785fd5f72d8f34fcd3606ef94431ea2cae7a3dbd03be";
    MoneroKeyImageSpentStatus status = daemon.getKeyImageSpentStatus(keyImage);
    System.out.println("Spent: " + status);
    
    MoneroTxWallet tx = wallet.sweepOutput(wallet.getPrimaryAddress(), keyImage, null);
    System.out.println(tx);

    
//    for (MoneroOutputWallet vout : wallet.getVouts(new MoneroVoutFilter().setKeyImage(new MoneroKeyImage().setHex(keyImage)))) {
//      System.out.println(vout);
//      wallet.sweepOutput(wallet.getPrimaryAddress(), keyImage, null);
//    }
//    
//    MoneroOutputWallet vout = wallet.getVouts(new MoneroVoutFilter().setKeyImage(new MoneroKeyImage("e0a0e86f63ef489ce6163298a531c584add551698e7381a789a4e506023027f6"))).get(0);
//    MoneroTxWallet tx = wallet.getTxs(new MoneroTxFilter().setTxIds(Arrays.asList(vout.getTx().getId())).setIncludeVouts(true)).get(0);
//    System.out.println(tx);
//    System.out.println(tx.getHeight() + " | " + (wallet.getHeight() - tx.getHeight()));
    
//    List<MoneroKeyImage> keyImages = wallet.getKeyImages();
//    for (MoneroKeyImage keyImage : keyImages) {
//      if (!keyImage.getHex().equals("358e263f307958b64db2f4ddd02df6bb5a21260448d06020c5cf274a68c5f6d1")) continue;
//      System.out.println("We found it!");
//      System.out.println(keyImage);
//      break;
//    }
    
//    wallet.getVouts();
//    
//    MoneroTx tx = daemon.getTx("d16e603efed00a3d2e14085d0ebe96734145d9eba851f6afd02ab76f9d5b62c0");
//    System.out.println(tx);
//    tx.merge(tx.copy());
    
//    MoneroTxWallet tx = wallet.getTxs(new MoneroTxFilter().setTxId("d16e603efed00a3d2e14085d0ebe96734145d9eba851f6afd02ab76f9d5b62c0")).get(0);
//    System.out.println(tx.toString());
//    assertTrue(tx.getBlock().getTxs().contains(tx));
    
//    List<MoneroAccount> accounts = wallet.getAccounts(true);
//    for (MoneroAccount account : accounts) {
//      System.out.println(account.getUnlockedBalance());
//      for (MoneroSubaddress subaddress : account.getSubaddresses()) {
//        System.out.println("\t" + subaddress.getUnlockedBalance());
//      }
//    }
  }
}
