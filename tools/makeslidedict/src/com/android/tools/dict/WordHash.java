package com.android.tools.dict;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import cs.jwave.Transform;
import cs.jwave.handlers.FastWaveletTransform;
import cs.jwave.handlers.wavelets.Haar02;

public class WordHash {
        
    public static double[] getSlideParameters(String word, Map<String, Point> keys) {
        String norm = normalizeWord(word);
        if (norm.length() == 0) {
           return null; 
        }
        List<Point> points = getPoints(keys, norm);
        if (points.size() < 2) {
            return null; // FIXME: we can't handle single-letter words (yet)
        }
        
        double[]x = new double[points.size()];
        double[]y = new double[points.size()];
        
        for (int i = 0; i < points.size(); i++) {
            x[i] = points.get(i).x;
            y[i] = points.get(i).y;
        }
        return getSlideParameters(x, y);
    }

    public static double[] getSlideParameters(double[] x, double[] y) {
        return getWaveletSlideParameters(x,y);
    }
    
    public static double[] getNormalSlideParameters(double[] x, double[] y) {
        double totalLength = getPolygonLength(x, y);
        double[] segmentLengths = getSegmentLengths(x, y);

        x = resample(x, segmentLengths);
        y = resample(y, segmentLengths);
        
        double[] result = new double[x.length + y.length];
        
        System.arraycopy(x, 0, result, 0, x.length);
        System.arraycopy(y, 0, result, x.length, y.length);
        
        return result;
    }
        
    public static double[] getWaveletSlideParameters(double[] x, double[] y) {
                double totalLength = getPolygonLength(x, y);
                double[] segmentLengths = getSegmentLengths(x, y);
                
                double[]result = new double[17];
                //Log.i("WordHash", "Input polygon length " + totalLength);
        
                x = resample(x, segmentLengths);
                y = resample(y, segmentLengths);
                
                Transform t = new Transform( new FastWaveletTransform( new Haar02( ) ) );
        
                double[] arrHilbX = t.forward( x ); // 1-D AED FWT Haar forward
                double[] arrHilbY = t.forward( y ); // 1-D AED FWT Haar forward
        
                result[0] = totalLength * 0; //meh
                System.arraycopy(arrHilbX, 0, result, 1, 8);
                System.arraycopy(arrHilbY, 0, result, 9, 8);
                return result;
    }

    private static double getPolygonLength(double[] x, double[] y) {
        double totalLength = 0;
        for (int i = 1; i < x.length; i++) {
            double dx = x[i] - x[i-1];
            double dy = y[i] - y[i-1];
            totalLength += Math.sqrt((dx*dx) + (dy*dy));
        }
        return totalLength;
    }

    public static void pr(double[] data) {
        if (data == null || data.length == 0) {
            System.out.println("-");
            return;
        }
        System.out.printf("%3.3f", data[0]);
        for (int i = 1; i < data.length; i++) {
            System.out.printf("\t%3.3f", data[i]);
        }
        System.out.println();
    }

    public static double[] resample(double[] samples, double[] segmentLengths) {
        int n = 64;
        double[]result = new double[n];
        double total = sum(segmentLengths);
//        System.out.println("segmentLengths");
//        pr(segmentLengths);
        double offset = 0;
        int currSegment = 0;
        for (int i = 0; i < n; i++) {
            double samplePos = (i * total) / (n-1);
//            System.out.printf("curr/offset/samplePos = %d / %3.3g / %3.3g\n", currSegment, offset, samplePos);
            while (currSegment < segmentLengths.length && offset + segmentLengths[currSegment] < samplePos) {
                offset += segmentLengths[currSegment];
                currSegment++;
            }
            
            if (currSegment == segmentLengths.length) {
                result[i] = samples[samples.length-1];
                continue;
            }
            
            double a = samples[currSegment];
            double b = samples[currSegment + 1];
//            System.out.printf("a,b = %3.3g, %3.3g\n",a,b);
            double abpos = (samplePos - offset) / segmentLengths[currSegment]; // relative position between a (0) and b (1)
            if (Double.isNaN(abpos)) {
                result[i] = b;
            } else {
                result[i] = a + (b - a) * abpos;
            }
        }
//        System.out.println("resample in");
//        pr(samples);
//        System.out.println("resample out");
//        pr(result);

        return result;
    }

    private static double sum(double[] arr) {
        double result = 0.0;
        for (double d: arr) {
            result += d;
        }
        return result;
    }

    private static double[] getXCoords(List<Point> points) {
        double[] t = new double[points.size()];
        for (int i = 0; i < points.size(); i++) {
            t[i] = points.get(i).x;
        }
        return t;
    }
    private static double[] getYCoords(List<Point> points) {
        double[] t = new double[points.size()];
        for (int i = 0; i < points.size(); i++) {
            t[i] = points.get(i).y;
        }
        return t;
    }

//    private Point[] resamplePoints(List<Point> points, double totalLength, double sampleInterval) {
//        int numSamples = (int) (totalLength / sampleInterval);
//        Point[] result = new Point[numSamples];
//        int currentPoint = 0;
//        double nextPointOffset = getDistance(points.get(0), points.get(1));
//        for (int i = 0; i < numSamples; i++) {
//            result[i] = points.get(currentPoint);
//            while (i * sampleInterval > nextPointOffset) {
//                nextPointOffset += getDistance(points.get(currentPoint), points.get(currentPoint+1));
//                currentPoint++;
//            }
//        }
//        
//        return result;
//    }

    private static double[] getSegmentLengths(double[]x, double[]y) {
        double[] result = new double[x.length - 1];
        for (int i = 1; i<x.length; i++) {
            double dx = x[i] - x[i-1];
            double dy = y[i] - y[i-1];
            result[i-1] = Math.sqrt(dx*dx + dy*dy);
        }
        return result;
    }

    private static List<Point> getPoints(Map<String, Point> keys, String norm) {
        List<Point>result = new ArrayList<Point>();
        for (int i = 0; i < norm.length(); i++) {
            result.add(keys.get(norm.substring(i, i + 1)));
        }
        return result;
    }

    private static String normalizeWord(String word) {
        // To support java 5 we should include the normalized
        // words in the dictionary. For now let's silently
        // drop the accented letters
        //word = Normalizer.normalize(word, Normalizer.Form.NFD);
        
        return word.replaceAll("[^a-zA-Z]", "").toLowerCase();
    }


}
