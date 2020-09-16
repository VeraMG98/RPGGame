package com.company;

import java.util.Random;
import java.util.RandomAccess;

public class Main {

    public static int bossHealth = 1300;
    public static int bossDamage = 75;
    public static String bossDefence = "";
    public static int[] heroesHealth = {260, 250, 240, 200, 280, 220, 210, 215};
    public static int[] heroesDamage = {20, 25, 15, 0, 5, 10, 20, 22};
    public static String[] heroesAttackType = {"Physical", "Magical", "Kinetic",
            "Medic", "Golem", "Lucky", "Berserk", "Thor"};
    public static int kRound = 0;
    public static boolean blockBossAttacks = false;

    public static void main(String[] args) {
        printStatistics();
        while (!isGameFinished()) {
            round();
        }
    }

    public static void round() {
        System.out.println("Round " + kRound++);
        changeBossDefence();
        if (heroesHealth[returnIndex("Thor")] != 0)
            thorChance();
        heroesHits();
        if (bossHealth > 0)
            bossHits();
        if (heroesHealth[returnIndex("Medic")] != 0)
            hillHeroes();
        printStatistics();
    }

    public static void bossHits() {
        Random r = new Random();
        int chance  = r.nextInt(3);
        int k = r.nextInt(3);
        if (heroesHealth[returnIndex("Golem")] != 0)
            golemChance();
        if (chance == 0)
            System.out.println("Boss became angry " + bossDamage * k);
        for (int i = 0; i < heroesHealth.length; i++) {
            if (heroesHealth[i] > 0) {
                if (chance == 0) {
                    if (heroesHealth[i] - bossDamage * k < 0)
                        heroesHealth[i] = 0;
                    else
                        heroesHealth[i] -= bossDamage *  k;
                } else {
                    if (heroesHealth[i] - bossDamage < 0)
                        heroesHealth[i] = 0;
                    else
                        heroesHealth[i] -= bossDamage;
                }
            }
        }
        if (chance == 1 && heroesHealth[returnIndex("Lucky")] != 0){
            luckyChance();
        }
        if (heroesHealth[returnIndex("Berserk")] != 0)
            berserkChance();
    }

    public static void heroesHits() {
        for (int i = 0; i < heroesDamage.length; i++) {
            if (heroesHealth[i] > 0 && bossHealth > 0) {
                if (heroesAttackType[i].equals(bossDefence)){
                    Random r = new Random();
                    int k = r.nextInt(6) + 2;
                    System.out.println("Critical damage " + heroesDamage[i] * k);
                    if (bossHealth - heroesDamage[i] * k < 0) {
                        bossHealth = 0;
                    } else {
                        bossHealth -= heroesDamage[i] * k;
                    }
                }
                if (bossHealth - heroesDamage[i] < 0) {
                    bossHealth = 0;
                } else {
                    bossHealth -= heroesDamage[i];
                }
            }
        }
    }

    public static void thorChance() {
        Random r = new Random();
        int chance = r.nextInt(5);
        if (chance == 2){
            blockBossAttacks = true;
            bossDamage = 0;
            System.out.println("Boss off");
        } else {
            blockBossAttacks = false;
            bossDamage = 50;
        }
        if (blockBossAttacks){
            bossDamage = 0;
        }
    }

    public static void berserkChance() {
        Random r = new Random();
        int kBlock = r.nextInt(6) + 1;
        int blockBossDamage = bossDamage/kBlock;
        System.out.println("Block boss damage " + blockBossDamage);
        heroesHealth[returnIndex("Berserk")] += blockBossDamage;
        bossHealth -= blockBossDamage;
    }

    public static void luckyChance() {
        System.out.println("Lucky");
        heroesHealth[returnIndex("Lucky")] += bossDamage;
    }

    public static void golemChance() {
        System.out.println("Boss damage " + bossDamage);
        int bossDamageRegress = bossDamage / 5;
        bossDamage -= bossDamageRegress;
        heroesHealth[returnIndex("Golem")] -= bossDamageRegress * 2;
        System.out.println("Boss damage regress " + bossDamageRegress);
    }

    public static void hillHeroes() {
        int min = heroesHealth[0];
        Random r = new Random();
        int hillK = r.nextInt(20) + 5;
        int k = 0;
        for (int i = 0; i < heroesHealth.length; i++) {
            if (heroesHealth[i] < min) {
                min = heroesHealth[i];
                k = i;
            }
        }
        if (min < 100 && min != 0){
            heroesHealth[k] += hillK;
            System.out.println("Hill hero " + heroesAttackType[k] + " for " + hillK);
        }
    }

    public static void changeBossDefence() {
        Random r = new Random();
        int randomIndex = r.nextInt(heroesAttackType.length);
        bossDefence = heroesAttackType[randomIndex];
        System.out.println("Boss choose " + bossDefence);
    }

    public static boolean isGameFinished() {
        if (bossHealth <= 0) {
            System.out.println("Heroes won!!!");
            return true;
        }
        boolean allHeroesDead = true;
        for (int value : heroesHealth) {
            if (value > 0) {
                allHeroesDead = false;
                break;
            }
        }
        if (allHeroesDead)
            System.out.println("Boss won!!!");
        return allHeroesDead;
    }

    public static void printStatistics() {
        System.out.println("_________________________");
        System.out.println("Boss health " + bossHealth);
        for (int i = 0; i < heroesAttackType.length; i++) {
            System.out.println(heroesAttackType[i] + " health " + heroesHealth[i]);
        }
        System.out.println("_________________________");
    }

    public static int returnIndex (String arrayElement) {
        int index = 0;
        for (int i = 0; i < heroesAttackType.length; i++) {
            if (heroesAttackType[i].equals(arrayElement))
                index = i;
        }
        return index;
    }
}
