package tests;

import solution.AffineSequenceAligner;
import solution.CharPair;
import solution.MatrixParser;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Map;
import java.util.Random;

/**
 * Created by Thomas on 11-09-2015.
 */
public class RuntimeTester {

    public static void main(String[] args) throws FileNotFoundException {
            MatrixParser matrixParser = new MatrixParser();
            matrixParser.parseFile("costMatrix.txt");
            Map<CharPair, Integer> seqMatrix = matrixParser.getCostMatrix();
            int gapCostAlpha = matrixParser.getGapCostAlpha();
            int gapCostBeta = matrixParser.getGapCostBeta();

            AffineSequenceAligner seqAligner = new AffineSequenceAligner(seqMatrix, gapCostAlpha, gapCostBeta);
            PrintWriter writer = new PrintWriter("runtimeResults.txt");
            PrintWriter writer2 = new PrintWriter("seqSizes.txt");
            char[] seq1, seq2;
            double startTime, timeDifference;
            int seqLength;
        try{
            for (int i = 100; i < 7000; i = i + 20) {
                seqLength = i;
                seq1 = generateRandomString(seqLength);
                seq2 = generateRandomString(seqLength);

                startTime = System.currentTimeMillis();
                seqAligner.calculateMinCost(seq1, seq2);
                timeDifference = System.currentTimeMillis() - startTime;
                System.out.println("Calculating min cost with seq length " + seqLength + " took " + timeDifference + " ms");
                writer.println(timeDifference / (seqLength * seqLength));
                writer2.println(seqLength);
            }
        } catch(Exception e){
            e.printStackTrace();
        } finally {
            writer.close();
            writer2.close();
        }
    }

    public static char[] generateRandomString(int length){
        char[] chars = "ACGT".toCharArray();
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            char c = chars[random.nextInt(chars.length)];
            sb.append(c);
        }
        return sb.toString().toCharArray();
    }
}