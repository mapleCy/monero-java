package test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;

import daemon.MoneroDaemon;
import daemon.model.MoneroBan;
import daemon.model.MoneroBlock;
import daemon.model.MoneroBlockCount;
import daemon.model.MoneroBlockHeader;
import daemon.model.MoneroBlockTemplate;
import daemon.model.MoneroChain;
import daemon.model.MoneroCoinbaseTxSum;
import daemon.model.MoneroDaemonConnection;
import daemon.model.MoneroDaemonConnectionSpan;
import daemon.model.MoneroDaemonInfo;
import daemon.model.MoneroDaemonModel;
import daemon.model.MoneroDaemonSyncInfo;
import daemon.model.MoneroFeeEstimate;
import daemon.model.MoneroHardForkInfo;
import daemon.model.MoneroMinerTx;
import daemon.model.MoneroOutputDistributionEntry;
import daemon.model.MoneroOutputHistogramEntry;
import utils.TestUtils;

/**
 * Tests a Monero daemon.
 * 
 * TODO: test input variations more thoroughly
 */
public class TestMoneroDaemon {
  
  private static MoneroDaemon daemon;

  @BeforeClass
  public static void setUpBeforeClass() throws Exception {
    daemon = TestUtils.getDaemon();
  }

  @Test
  public void testGetBlockCount() {
    MoneroBlockCount blockCount = daemon.getBlockCount();
    testDaemonResponseInfo(blockCount, true, false);
    assertNotNull(blockCount.getCount());
    assertTrue(blockCount.getCount().intValue() > 0);
  }

  @Test
  public void testGetBlockHash() {
    MoneroBlockHeader lastHeader = daemon.getLastBlockHeader();
    String hash = daemon.getBlockHash(lastHeader.getHeight());
    assertNotNull(hash);
    assertEquals(64, hash.length());
  }

  @Test
  public void testGetBlockTemplate() {
    MoneroBlockTemplate template = daemon.getBlockTemplate("49hNury7mhADaH5r4buqRK9Mt5yFSPZdHanYcZzuNUV1fGRoVABHpnd81bUdTSZk5MaCb5ptsVSVAEusrmr61iX83FFTAuF", 2);
    testDaemonResponseInfo(template, true, true);
    testBlockTemplate(template);
  }

  @Test
  public void testGetLastBlockHeader() {
    MoneroBlockHeader lastHeader = daemon.getLastBlockHeader();
    testDaemonResponseInfo(lastHeader, true, true);
    testBlockHeader(lastHeader);
  }

  @Test
  public void testGetBlockHeaderByHash() {
    
    // retrieve by hash of last block
    MoneroBlockHeader lastHeader = daemon.getLastBlockHeader();
    String hash = daemon.getBlockHash(lastHeader.getHeight());
    MoneroBlockHeader header = daemon.getBlockHeader(hash);
    testDaemonResponseInfo(header, true, true);
    testBlockHeader(header);
    assertEquals(lastHeader, header);
    
    // retrieve by hash of previous to last block
    hash = daemon.getBlockHash(lastHeader.getHeight() - 1);
    header = daemon.getBlockHeader(hash);
    testDaemonResponseInfo(header, true, true);
    testBlockHeader(header);
    assertEquals((int) lastHeader.getHeight() - 1, (int) header.getHeight()); 
  }

  @Test
  public void testGetBlockHeaderByHeight() {
    
    // retrieve by height of last block
    MoneroBlockHeader lastHeader = daemon.getLastBlockHeader();
    MoneroBlockHeader header = daemon.getBlockHeader(lastHeader.getHeight());
    testDaemonResponseInfo(header, true, true);
    testBlockHeader(header);
    assertEquals(lastHeader, header);
    
    // retrieve by height of previous to last block
    header = daemon.getBlockHeader(lastHeader.getHeight() - 1);
    testDaemonResponseInfo(header, true, true);
    testBlockHeader(header);
    assertEquals((int) lastHeader.getHeight() - 1, (int) header.getHeight()); 
  }

  @Test
  public void testGetBlockHeaders() {
    
    // retrieve X blocks Y blocks ago
    int NUM_BLOCKS = 25;
    int NUM_BLOCKS_AGO = 100;
    int currentHeight = daemon.getInfo().getHeight();
    int startHeight = currentHeight - NUM_BLOCKS_AGO;
    int endHeight = currentHeight - (NUM_BLOCKS_AGO - NUM_BLOCKS);
    List<MoneroBlockHeader> headers = daemon.getBlockHeaders(startHeight, endHeight);
    
    // test blocks
    assertEquals(NUM_BLOCKS, headers.size());
    for (int i = 0; i < NUM_BLOCKS; i++) {
      MoneroBlockHeader header = headers.get(startHeight + i);
      assertEquals((int) startHeight + i, (int) header.getHeight());
      testDaemonResponseInfo(header, true, true);
      testBlockHeader(header);
    }
  }

  @Test
  public void testGetBlockByHash() {
    
    // retrieve by hash of last block
    MoneroBlockHeader lastHeader = daemon.getLastBlockHeader();
    String hash = daemon.getBlockHash(lastHeader.getHeight());
    MoneroBlock block = daemon.getBlock(hash);
    testDaemonResponseInfo(block, true, true);
    testBlock(block);
    assertEquals(daemon.getBlock(block.getHeader().getHeight()), block);
    
    // retrieve by hash of previous to last block
    hash = daemon.getBlockHash(lastHeader.getHeight() - 1);
    block = daemon.getBlock(hash);
    testDaemonResponseInfo(block, true, true);
    testBlock(block);
    assertEquals(daemon.getBlock(block.getHeader().getHeight() - 1), block);
  }

  @Test
  public void testGetBlockByHeight() {
    
    // retrieve by height of last block
    MoneroBlockHeader lastHeader = daemon.getLastBlockHeader();
    MoneroBlock block = daemon.getBlock(lastHeader.getHeight());
    testDaemonResponseInfo(block, true, true);
    testBlock(block);
    assertEquals(daemon.getBlock(block.getHeader().getHeight()), block);
    
    // retrieve by height of previous to last block
    block = daemon.getBlock(lastHeader.getHeight() - 1);
    testDaemonResponseInfo(block, true, true);
    testBlock(block);
    assertEquals(daemon.getBlock(block.getHeader().getHeight() - 1), block);
  }

  @Test
  public void testGetConnections() {
    List<MoneroDaemonConnection> connections = daemon.getConnections();
    for (MoneroDaemonConnection connection : connections) {
      testDaemonResponseInfo(connection, true, false);
      testDaemonConnection(connection);
    }
  }

  @Test
  public void testGetInfo() {
    MoneroDaemonInfo info = daemon.getInfo();
    testDaemonResponseInfo(info, true, true);
    testDaemonInfo(info);
  }

  @Test
  public void testGetSyncInfo() {
    MoneroDaemonSyncInfo syncInfo = daemon.getSyncInfo();
    testDaemonResponseInfo(syncInfo, true, true);
    testDaemonSyncInfo(syncInfo);
  }

  @Test
  public void testGetHardForkInfo() {
    MoneroHardForkInfo hardForkInfo = daemon.getHardForkInfo();
    testDaemonResponseInfo(hardForkInfo, true, true);
    testHardForkInfo(hardForkInfo);
  }

  @Test
  public void testSetBan() {
    
    // set ban
    MoneroBan ban = new MoneroBan();
    ban.setHost("192.168.1.51");
    ban.setIsBanned(true);
    ban.setSeconds(60);
    MoneroDaemonModel model = daemon.setBan(ban);
    testDaemonResponseInfo(model, true, false);
    
    // test ban
    Collection<MoneroBan> bans = daemon.getBans();
    boolean found = false;
    for (MoneroBan aBan : bans) {
      testDaemonResponseInfo(aBan, true, false);
      testMoneroBan(aBan);
      if (aBan.getHost().equals("192.168.1.51")) found = true;
    }
    assertTrue(found);
  }

  @Test
  public void testSetBans() {
    
    // set bans
    MoneroBan ban1 = new MoneroBan();
    ban1.setHost("192.168.1.52");
    ban1.setIsBanned(true);
    ban1.setSeconds(60);
    MoneroBan ban2 = new MoneroBan();
    ban2.setHost("192.168.1.53");
    ban2.setIsBanned(true);
    ban2.setSeconds(60);
    Collection<MoneroBan> bans = new ArrayList<MoneroBan>();
    bans.add(ban1);
    bans.add(ban2);
    MoneroDaemonModel model = daemon.setBans(bans);
    testDaemonResponseInfo(model, true, false);
    
    // test bans
    bans = daemon.getBans();
    boolean found1 = false;
    boolean found2 = false;
    for (MoneroBan aBan : bans) {
      testDaemonResponseInfo(aBan, true, false);
      testMoneroBan(aBan);
      if (aBan.getHost().equals("192.168.1.52")) found1 = true;
      if (aBan.getHost().equals("192.168.1.53")) found2 = true;
    }
    assertTrue(found1);
    assertTrue(found2);
  }

  @Test
  public void testFlushTxPool() {
    MoneroDaemonModel model = daemon.flushTxPool();
    testDaemonResponseInfo(model, true, false);
  }

  @Test
  public void testFlushTxPoolCollectionOfString() {
    fail("Not yet implemented");
  }

  @Test
  public void testGetOutputHistogram() {
    List<MoneroOutputHistogramEntry> entries = daemon.getOutputHistogram(null, null, null, null, null);
    assertFalse(entries.isEmpty());
    for (MoneroOutputHistogramEntry entry : entries) {
      testDaemonResponseInfo(entry, true, true);
      testOutputHistogramEntry(entry);
    }
  }

  @Test
  public void testGetOutputDistribution() {
    List<MoneroOutputDistributionEntry> entries = daemon.getOutputDistribution(null, null, null, null);
    assertFalse(entries.isEmpty());
    for (MoneroOutputDistributionEntry entry : entries) {
      testDaemonResponseInfo(entry, true, true);
      testOutputDistributionEntry(entry);
    }
  }

  @Test
  public void testGetCoinbaseTxSum() {
    MoneroCoinbaseTxSum sum = daemon.getCoinbaseTxSum(null, null);
    testDaemonResponseInfo(sum, true, true);
    testCoinbaseTxSum(sum);
  }

  @Test
  public void testGetFeeEstimate() {
    MoneroFeeEstimate estimate = daemon.getFeeEstimate(null);
    testDaemonResponseInfo(estimate, true, true);
    assertNotNull(estimate.getFeeEstimate());
  }

  @Test
  public void testGetAlternativeChains() {
    Collection<MoneroChain> chains = daemon.getAlternativeChains();
    assertFalse(chains.isEmpty());
    for (MoneroChain chain : chains) {
      testDaemonResponseInfo(chain, true, true);
      testMoneroChain(chain);
    }
    
    fail("Not yet implemented");
  }

  @Test
  public void testRelayTx() {
    fail("Not yet implemented");
  }

  @Test
  public void testRelayTxs() {
    fail("Not yet implemented");
  }

  @Test
  public void testGetTxPoolBacklog() {
    fail("Not yet implemented");
  }

  @Test
  public void testGetAltBlockHashes() {
    fail("Not yet implemented");
  }

  @Test
  public void testIsKeyImageSpent() {
    fail("Not yet implemented");
  }

  @Test
  public void testGetTxs() {
    fail("Not yet implemented");
  }

  @Test
  public void testStartMining() {
    fail("Not yet implemented");
  }

  @Test
  public void testStopMining() {
    fail("Not yet implemented");
  }

  @Test
  public void testGetMiningStatus() {
    fail("Not yet implemented");
  }

  @Test
  public void testSetBandwidthLimit() {
    fail("Not yet implemented");
  }

  @Test
  public void testGetBandwidthLimit() {
    fail("Not yet implemented");
  }

  @Test
  public void testSetNumOutgoingLimit() {
    fail("Not yet implemented");
  }

  @Test
  public void testSetNumIncomingLimit() {
    fail("Not yet implemented");
  }
  
  @Test
  public void testSubmitBlock() {
    fail("Not yet implemented");
  }

  private static void testDaemonResponseInfo(MoneroDaemonModel model, boolean initializedStatus, boolean initializedIsUntrusted) {
    assertNotNull(model.getResponseInfo());
    if (initializedStatus) {
      assertEquals("OK", model.getResponseInfo().getStatus());
    } else {
      assertNull(model.getResponseInfo().getStatus());
    }
    if (initializedIsUntrusted) {
      assertNotNull(model.getResponseInfo().getIsTrusted());
    } else {
      assertNull(model.getResponseInfo().getIsTrusted());
    }
  }
  
  private static void testBlockHeader(MoneroBlockHeader header) {
    assertNotNull(header);
    assertNotNull(header.getBlockSize());
    assertNotNull(header.getDepth());
    assertNotNull(header.getDepth());
    assertNotNull(header.getDifficulty());
    assertNotNull(header.getHash());
    assertNotNull(header.getHeight());
    assertNotNull(header.getMajorVersion());
    assertNotNull(header.getMinorVersion());
    assertNotNull(header.getNonce());
    assertNotNull(header.getNumTxs());
    assertNotNull(header.getOrphanStatus());
    assertNotNull(header.getPrevHash());
    assertNotNull(header.getReward());
    assertNotNull(header.getTimestamp()); 
  }
  
  private static void testBlockTemplate(MoneroBlockTemplate template) {
    assertNotNull(template);
    assertNotNull(template.getTemplateBlob());
    assertNotNull(template.getHashBlob());
    assertNotNull(template.getDifficulty());
    assertNotNull(template.getExpectedReward());
    assertNotNull(template.getHeight());
    assertNotNull(template.getPrevHash());
    assertNotNull(template.getReservedOffset());
  }
  
  private static void testBlock(MoneroBlock block) {
    assertNotNull(block);
    assertNotNull(block.getBlob());
    assertTrue(block.getBlob().length() > 1);
    testBlockHeader(block.getHeader());
    testMinerTx(block.getMinerTx());
    assertNotNull(block.getTxHashes());
    throw new RuntimeException("Not implemented");
  }
  
  private static void testMinerTx(MoneroMinerTx minerTx) {
    throw new RuntimeException("Not implemented");
  }
  
  private static void testDaemonConnection(MoneroDaemonConnection connection) {
    assertNotNull(connection);
    assertNotNull(connection.getId());
    assertNotNull(connection.getAddress());
    assertNotNull(connection.getAvgDownload());
    assertNotNull(connection.getAvgUpload());
    assertNotNull(connection.getCurrentDownload());
    assertNotNull(connection.getCurrentUpload());
    assertNotNull(connection.getHeight());
    assertNotNull(connection.getHost());
    assertNotNull(connection.getIp());
    assertNotNull(connection.getLiveTime());
    assertNotNull(connection.getIsLocalIp());
    assertNotNull(connection.getIsLocalhost());
    assertNotNull(connection.getPeerId());
    assertNotNull(connection.getPort());
    assertNotNull(connection.getReceiveCount());
    assertNotNull(connection.getReceiveIdleTime());
    assertNotNull(connection.getSendCount());
    assertNotNull(connection.getSendIdleTime());
    assertNotNull(connection.getState());
    assertNotNull(connection.getSupportFlags()); 
  }
  
  private static void testDaemonInfo(MoneroDaemonInfo info) {
    assertNotNull(info.getAltBlocksCount());
    assertNotNull(info.getBlockSizeLimit());
    assertNotNull(info.getBlockSizeMedian());
    assertNotNull(info.getBootstrapDaemonAddress());
    assertNotNull(info.getCumulativeDifficulty());
    assertNotNull(info.getFreeSpace());
    assertNotNull(info.getGreyPeerlistSize());
    assertNotNull(info.getWhitePeerlistSize());
    assertNotNull(info.getHeight());
    assertNotNull(info.getHeightWithoutBootstrap());
    assertNotNull(info.getIncomingConnectionsCount());
    assertNotNull(info.getNetworkType());
    assertNotNull(info.getIsOffline());
    assertNotNull(info.getOutgoingConnectionsCount());
    assertNotNull(info.getRpcConnectionsCount());
    assertNotNull(info.getStartTime());
    assertNotNull(info.getTarget());
    assertNotNull(info.getTargetHeight());
    assertNotNull(info.getTopBlockHash());
    assertNotNull(info.getTxCount());
    assertNotNull(info.getTxPoolSize());
    assertNotNull(info.getWasBootstrapEverUsed()); 
  }
  
  private static void testDaemonSyncInfo(MoneroDaemonSyncInfo syncInfo) {
    assertNotNull(syncInfo.getHeight());
    assertNotNull(syncInfo.getPeers());
    assertFalse(syncInfo.getPeers().isEmpty());
    for (MoneroDaemonConnection peer : syncInfo.getPeers()) {
      testDaemonResponseInfo(peer, true, true);
      testDaemonConnection(peer);
    }
    assertNotNull(syncInfo.getSpans());
    assertFalse(syncInfo.getSpans().isEmpty());
    for (MoneroDaemonConnectionSpan span : syncInfo.getSpans()) {
      testDaemonResponseInfo(span, true, true);
      testDaemonConnectionSpan(span);
    }
  }
  
  private static void testDaemonConnectionSpan(MoneroDaemonConnectionSpan span) {
    assertNotNull(span.getConnectionId());
    assertNotNull(span.getNumBlocks());
    assertNotNull(span.getConnectionRate());
    assertNotNull(span.getRemoteAddress());
    assertNotNull(span.getSize());
    assertNotNull(span.getSpeed());
    assertNotNull(span.getStartBlockHeight());
  }
  
  private static void testHardForkInfo(MoneroHardForkInfo hardForkInfo) {
    assertNotNull(hardForkInfo.getEarliestHeight());
    assertNotNull(hardForkInfo.getIsEnabled());
    assertNotNull(hardForkInfo.getState());
    assertNotNull(hardForkInfo.getThreshold());
    assertNotNull(hardForkInfo.getVersion());
    assertNotNull(hardForkInfo.getVotes());
    assertNotNull(hardForkInfo.getVoting());
    assertNotNull(hardForkInfo.getWindow());
  }
  
  private static void testMoneroBan(MoneroBan ban) {
    assertNotNull(ban.getHost());
    assertNotNull(ban.getIp());
    assertNotNull(ban.getSeconds());
  }
  
  private static void testOutputHistogramEntry(MoneroOutputHistogramEntry entry) {
    assertNotNull(entry.getAmount());
    assertNotNull(entry.getNumTotalInstances());
    assertNotNull(entry.getNumUnlockedInstances());
    assertNotNull(entry.getNumRecentInstances());
  }
  
  private static void testOutputDistributionEntry(MoneroOutputDistributionEntry entry) {
    assertNotNull(entry.getAmount());
    assertNotNull(entry.getBase());
    assertNotNull(entry.getDistribution());
    assertNotNull(entry.getStartHeight());
  }
  
  private static void testCoinbaseTxSum(MoneroCoinbaseTxSum sum) {
    assertNotNull(sum.getEmissionSum());
    assertNotNull(sum.getFeeSum());
  }
  
  private static void testMoneroChain(MoneroChain chain) {
    assertNotNull(chain.getBlockHash());
    assertNotNull(chain.getDifficulty());
    assertNotNull(chain.getHeight());
    assertNotNull(chain.getLength());
  }
}