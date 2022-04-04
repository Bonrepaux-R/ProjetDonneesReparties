package linda.test;

import java.util.Random;

import linda.*;

public class TestConcurrenceReadWriteTake {

    public static void main(String[] a) {
                
        final Linda linda = new linda.shm.CentralizedLinda();
        // final Linda linda = new linda.server.LindaClient("//localhost:4000/aaa");
                
        new Thread() {
            // Ce thread va écrire de nombreux tuples dans l'espace partagé
            public void run() {
                int i ;
                for (i = 0 ; i < 1000 ; i++ ){
                    try {
                        Tuple motif = new Tuple(Integer.class, String.class);
                        linda.write(motif);
                        System.out.println("Write numéro "+i+": " + motif);
                        // On introduit une pause de durée aléatoire pour entrelacer les opérations
                        Random obj = new Random();
                        int nbr = obj.nextInt(500);
                        Thread.sleep(nbr);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                System.out.println("fin des write");
            }
        }.start();
                
        new Thread() {
            // Ce thread va lire de nombreux tuples dans l'espace partagé
            public void run() {
                int i ;
                for (i = 0 ; i < 1000 ; i++ ){
                    try {
                        Tuple motif = new Tuple(Integer.class, String.class);
                        linda.read(motif);
                        System.out.println("Read: "+i+": " + motif);
                        // On introduit une pause de durée aléatoire pour entrelacer les opérations
                        Random obj = new Random();
                        int nbr = obj.nextInt(500);
                        Thread.sleep(nbr);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                System.out.println("fin des reads");
            }
        }.start();

        new Thread() {
            // Ce thread va lire de nombreux tuples dans l'espace partagé
            public void run() {
                int i ;
                for (i = 0 ; i < 1000 ; i++ ){
                    try {
                        Tuple motif = new Tuple(Integer.class, String.class);
                        Tuple tuple = linda.take(motif);
                        System.out.println("Take: "+i+": " + motif);
                        // On introduit une pause de durée aléatoire pour entrelacer les opérations
                        Random obj = new Random();
                        int nbr = obj.nextInt(500);
                        Thread.sleep(nbr);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                System.out.println("fin des reads");
            }
        }.start();
    }
}
