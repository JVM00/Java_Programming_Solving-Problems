public class Part2 {

   public String findSimpleGene(String var1, String var2, String var3) {
      String var4 = var1.toLowerCase();
      String var5 = var2.toLowerCase();
      String var6 = var3.toLowerCase();
      int var7 = var4.indexOf(var5);
      if (var7 == -1) {
         return "";
      } else {
         int var8 = var4.indexOf(var6, var7 + 3);
         if (var8 == -1) {
            return "";
         } else {
            int var9 = var8 + 3 - var7;
            return var9 % 3 == 0 ? var1.substring(var7, var8 + 3) : "";
         }
      }
   }

   public void testSimpleGene() {
      String[] var1 = new String[]{"CCGGTGAAC", "ATGCCCGGG", "CCGGTGAACGTT", "AATGCCCTAACTG", "AATGCCCTAAGG","AAATGCCCTAACTAGATTAAGAAACC"};
      String[] var2 = var1;
      int var3 = var1.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         String var5 = var2[var4];
         System.out.println("DNA: " + var5);
         String var6 = this.findSimpleGene(var5, "ATG", "TAA");
         System.out.println("Gene: " + var6);
      }

   }

   public static void main(String[] var0) {
      Part2 var1 = new Part2();
      var1.testSimpleGene();
   }
}
