package utils;

import java.io.IOException;
import java.math.BigInteger;
import monero.wallet.MoneroWalletJni;
import monero.wallet.model.MoneroTxConfig;

/**
 * Scratchpad for quick scripting.
 */
public class Scratchpad {

  public static void main(String[] args) throws IOException {
    
    // initialize daemon, wallet, and direct rpc interface
    //MoneroDaemon daemon = TestUtils.getDaemonRpc();
    //MoneroWalletRpc walletRpc = TestUtils.getWalletRpc();
    MoneroWalletJni walletJni = TestUtils.getWalletJni();
    
    System.out.println(walletJni.getPrimaryAddress());
    System.out.println(walletJni.getPrivateViewKey());
    
//    MoneroWalletRpc myWalletRpc = new MoneroWalletRpc(Arrays.asList(
//        "/Applications/monero-x86_64-apple-darwin11-v0.17.1.1-rct-zmq/monero-wallet-rpc",
//        "--daemon-address", "http://localhost:38081",
//        "--daemon-login", "superuser:abctesting123",
//        "--stagenet",
//        "--rpc-bind-port", "38085",
//        "--rpc-login", "rpc_user:abc123",
//        "--wallet-dir", "/Applications/monero-x86_64-apple-darwin11-v0.17.1.1-rct-zmq", // TODO: extracted from path to exec
//        "--rpc-access-control-origins", "http://localhost:9100",
//        "--zmq-rpc-bind-port", "38083",
//        "--zmq-pub", "tcp://127.0.0.1:58083"));
//    myWalletRpc.openWallet("test_wallet_1", "supersecretpassword123");
//    System.out.println("My wallet rpc mnemonic: " + myWalletRpc.getMnemonic());
//    myWalletRpc.stopProcess();
    
    
    
//    Process p1 = MoneroUtils.startWalletRpcProcess("/Applications/monero-x86_64-apple-darwin11-v0.17.1.1-rct-zmq/monero-wallet-rpc --daemon-address http://localhost:38081 --daemon-login superuser:abctesting123 --stagenet --rpc-bind-port 38085 --rpc-login rpc_user:abc123 --wallet-dir /Applications/monero-x86_64-apple-darwin11-v0.17.1.1-rct-zmq --rpc-access-control-origins http://localhost:9100 --zmq-rpc-bind-port 38083 --zmq-pub tcp://127.0.0.1:58083");
//    Process p2 = MoneroUtils.startWalletRpcProcess("/Applications/monero-x86_64-apple-darwin11-v0.17.1.1-rct-zmq/monero-wallet-rpc --daemon-address http://localhost:38081 --daemon-login superuser:abctesting123 --stagenet --rpc-bind-port 38086 --rpc-login rpc_user:abc123 --wallet-dir /Applications/monero-x86_64-apple-darwin11-v0.17.1.1-rct-zmq --rpc-access-control-origins http://localhost:9100 --zmq-rpc-bind-port 38084 --zmq-pub tcp://127.0.0.1:58084");
//    
//    // TODO: wait time should be built into startWalletRpcProcess();
//    try { TimeUnit.MILLISECONDS.sleep(5000); }
//    catch (InterruptedException e) {  throw new RuntimeException(e); } 
//    
//    System.out.println(p1);
//    System.out.println(p2);
//    
//    
//    
//    MoneroWalletRpc walletRpc1 = new MoneroWalletRpc(new MoneroRpcConnection("http://127.0.0.1:38085", "rpc_user", "abc123", "tcp://127.0.0.1:58083"));
//    System.out.println("Wallet rpc process version? " + JsonUtils.serialize(walletRpc1.getVersion()));
//    walletRpc1.openWallet("test_wallet_1", "supersecretpassword123");
//    System.out.println("Wallet 1's mnemonic: " + walletRpc1.getMnemonic());
//    
//    MoneroWalletRpc walletRpc2 = new MoneroWalletRpc(new MoneroRpcConnection("http://127.0.0.1:38086", "rpc_user", "abc123", "tcp://127.0.0.1:58084"));
//    System.out.println("Wallet rpc process version? " + JsonUtils.serialize(walletRpc2.getVersion()));
//    walletRpc2.openWallet("468b958d-4905-48bf-ad5d-9973094a3366", "supersecretpassword123");
//    System.out.println("Wallet 2's mnemonic: " + walletRpc2.getMnemonic());
//    
//    MoneroUtils.stopWalletRpcProcess(p1); // TODO: this is not stopping process, probably because monero-wallet-rpc is hanging
//    MoneroUtils.stopWalletRpcProcess(p2);
    
    // -------------------------------- SCRATCHPAD ----------------------------
    
//    // create wallet from mnemonic
//    MoneroWallet walletJni = MoneroWalletJni.createWallet(new MoneroWalletConfig()
//      .setPath("./test_wallets/" + UUID.randomUUID().toString())  // leave blank for in-memory wallet
//      .setPassword("abctesting123")
//      .setNetworkType(MoneroNetworkType.STAGENET)
//      .setServerUri("http://localhost:38081")
//      .setServerUsername("superuser")
//      .setServerPassword("abctesting123")
//      .setMnemonic("tissue uttered addicted vein wrong fossil innocent algebra wickets dwindling layout punch never owls duke lofty oven click verification novelty upbeat zapped cabin calamity calamity")
//      .setRestoreHeight(8360l));
//    walletJni.sync(new WalletSyncPrinter());
//    System.out.println("WASM wallet daemon height: " + walletJni.getDaemonHeight());
//    System.out.println("WASM wallet mnemonic: " + walletJni.getMnemonic());
//    System.out.println(walletJni.getBalance());
    
    walletJni.createTx(new MoneroTxConfig()
            .setAddress("5B5XPBvKZdwFX51wPAYkFFTwraeov4ZVYJZKyyknW3ADC12Z7mwJnTcbh45fYmMaWWY381znFFbYXUEoZLQ83ziYERtQJjA")
            .setAmount(walletJni.getUnlockedBalance(0).divide(new BigInteger("12")).multiply(new BigInteger("1")))
            .setAccountIndex(0)
            .setRelay(true));
    
    // MEASURE LAST 30 DAYS
//    int numBlocks = 30 * 24 * 60 / 2;
//    
//    List<MoneroBlockHeader> headers = daemon.getBlockHeadersByRange(daemon.getHeight() - (numBlocks * 2), daemon.getHeight() - (numBlocks * 1));
//    long totalSize = 0;
//    int numOutputs = 0;
//    int numTxs = 0;
//    for (MoneroBlockHeader header : headers) {
//      totalSize += header.getSize();
//      numTxs += header.getNumTxs();
//    }
//    
//    for (MoneroBlock block : daemon.getBlocksByRange(daemon.getHeight() - numBlocks, daemon.getHeight() - 1)) {
//      for (MoneroTx tx : block.getTxs()) {
//        numOutputs += tx.getOutputs().size();
//      }
//    }
//    
//    System.out.println("Number of blocks: " + numBlocks);
//    System.out.println("Num txs: " + numTxs);
//    System.out.println("Num outputs: " + numOutputs);
//    System.out.println("Total size: " + totalSize);
    
//    // TIMING TEST
//    String path = TestMoneroWalletJni.getRandomWalletPath();
//    MoneroWalletJni myWallet = MoneroWalletJni.createWalletFromMnemonic(path, TestUtils.WALLET_JNI_PW, MoneroNetworkType.STAGENET, TestUtils.MNEMONIC, TestUtils.getDaemonRpc().getRpcConnection());
//    myWallet.save();
//    long now = System.currentTimeMillis();;
//    myWallet.addListener(new MoneroWalletListener());
//    myWallet.sync(new WalletSyncPrinter());
//    long newNow = System.currentTimeMillis();
//    System.out.println("Sync took " + (((double) newNow - (double) now) / (double) 1000) + " seconds");
    
//    // generate 20 random stagenet wallets
//    MoneroRpcConnection daemonConnection = new MoneroRpcConnection(TestUtils.DAEMON_RPC_URI, TestUtils.DAEMON_RPC_USERNAME, TestUtils.DAEMON_RPC_PASSWORD);
//    List<String> mnemonics = new ArrayList<String>();
//    List<String> addresses = new ArrayList<String>();
//    for (int i = 0; i < 20; i++) {
//      String temp = UUID.randomUUID().toString();
//      walletJni = new MoneroWalletJni(TestUtils.TEST_WALLETS_DIR + "/" + temp, TestUtils.WALLET_JNI_PW, MoneroNetworkType.STAGENET, daemonConnection, "English");
//      mnemonics.add(walletJni.getMnemonic());
//      addresses.add(walletJni.getPrimaryAddress());
//      ((MoneroWalletJni) walletJni).close();
//    }
//    for (int i = 0; i < 20; i++) {
//      System.out.println(mnemonics.get(i));
//      System.out.println(addresses.get(i));
//    }
  }
}
