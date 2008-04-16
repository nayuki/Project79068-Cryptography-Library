package p79068.crypto.cipher;


final class Whirlpool1Cipherer extends WhirlpoolCiphererParent{

 Whirlpool1Cipherer(Whirlpool1Cipher cipher,byte[] key){
  super(cipher,key);
  sub=WHIRLPOOL1_SUB;
  subinv=WHIRLPOOL1_SUBINV;
  mul=WHIRLPOOL1_MUL;
  mulinv=WHIRLPOOL1_MULINV;
  rcon=WHIRLPOOL1_RCON;
  setKey(key);}


 private static final byte[] WHIRLPOOL1_SUB;
 private static final byte[] WHIRLPOOL1_SUBINV;
 private static final byte[][] WHIRLPOOL1_MUL;
 private static final byte[][] WHIRLPOOL1_MULINV;
 private static final byte[][] WHIRLPOOL1_RCON;

 static{
  {int[] e={0x1,0xB,0x9,0xC,0xD,0x6,0xF,0x3,0xE,0x8,0x7,0x4,0xA,0x2,0x5,0x0}; // The E mini-box
   int[] r={0x7,0xC,0xB,0xD,0xE,0x4,0x9,0xF,0x6,0x3,0x8,0xA,0x2,0x5,0x1,0x0}; // The R mini-box
   int[] einv=new int[16]; // The inverse of E
   for(int i=0;i<e.length;i++)einv[e[i]]=i;
   WHIRLPOOL1_SUB=new byte[256];
   WHIRLPOOL1_SUBINV=new byte[256];
   for(int i=0;i<WHIRLPOOL1_SUB.length;i++){
    int left=e[i>>>4];
    int right=einv[i&0xF];
    int temp=r[left^right];
    WHIRLPOOL1_SUB[i]=(byte)(e[left^temp]<<4|einv[right^temp]);
    WHIRLPOOL1_SUBINV[WHIRLPOOL1_SUB[i]&0xFF]=(byte)i;}}

  {WHIRLPOOL1_RCON=new byte[ROUNDS][64];
   for(int i=0;i<WHIRLPOOL1_RCON.length;i++){
    int j=0;
    for(;j<8;j++)WHIRLPOOL1_RCON[i][j]=WHIRLPOOL1_SUB[8*i+j];
    for(;j<64;j++)WHIRLPOOL1_RCON[i][j]=0;}}

  {int[] c   ={0x01,0x05,0x09,0x08,0x05,0x01,0x03,0x01};
   int[] cinv={0xB8,0x0E,0x67,0x6C,0x0A,0xD1,0x71,0xE3};
   WHIRLPOOL1_MUL=new byte[8][256];
   WHIRLPOOL1_MULINV=new byte[8][256];
   for(int i=0;i<c.length;i++){
    for(int j=0;j<256;j++){
     WHIRLPOOL1_MUL[i][j]=(byte)multiply(j,c[i]);
     WHIRLPOOL1_MULINV[i][j]=(byte)multiply(j,cinv[i]);}}}}}