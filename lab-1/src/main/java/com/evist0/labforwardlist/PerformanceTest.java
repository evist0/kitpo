package com.evist0.labforwardlist;

import com.evist0.labforwardlist.factory.ObjectFactoryRegistry;
import com.evist0.labforwardlist.model.ForwardList;

import java.util.Comparator;
import java.util.Random;

public class PerformanceTest {
    public static void main(String[] args) {
        Random random = new Random();

        for (int i = 1; i <= 10; i++) {
            ForwardList<Integer> forwardList = new ForwardList<>();

            int amount = 50_000 + (50_000 * i);

            System.out.println("[" + i + "]" + " Elements amount: " + amount);

            for (int j = 0; j < amount; j++) {
                Integer randomValue = random.nextInt();
                forwardList.add(randomValue);
            }

            long start = System.nanoTime();

            Comparator<Integer> comparator = ObjectFactoryRegistry.getFactory("Integer").getComparator();
            forwardList.sort(comparator);

            long end = System.nanoTime();

            System.out.println("Milliseconds elapsed " + (end - start) * 1.0 / 1_000_000);
            System.out.println();

            System.gc();
        }
    }
}
