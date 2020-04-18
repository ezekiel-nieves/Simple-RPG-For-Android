package com.example.finalrpg

import android.media.Image
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.view.isInvisible
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*
import kotlin.concurrent.timer
import kotlin.random.Random
import androidx.core.view.isVisible as isVisible1

class MainActivity : AppCompatActivity() {
    /* IN THIS PROJECT THE PLAYER ATTACK HAPPENS FIRST
IF YOU WANT YOU CAN MAKE A SPEED STAT LATER

/*
the goal of this is to pause it when you stop the view
start it when resumed
put this in every app with music btw
     */
     */
    override fun onStop() {
        super.onStop()
        mediaPlayer.pause()

    }

    override fun onResume() {
        super.onResume()
        mediaPlayer.start()
    }

    var mediaPlayer: MediaPlayer = MediaPlayer()
    // I GOT THE MUSIC TO PLAYYYYYYYYY
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var playerHealth = 50
        var playerAtk = 12
        var playerDef = 7
        var enemyHealth = 50
        var enemyAtk = 13
        var enemyDef = 8
        var playerMAtk = playerAtk / 2
        var enemyMAtk = enemyAtk / 2
        var enemyName = "Gohan"
        var juiceLeft = 1
        var soulsTaken = 0
        val bashButtonPressed = findViewById<Button>(R.id.bashButton)
        val psyButtonPressed = findViewById<Button>(R.id.psyButton)
        val itemButtonPressed = findViewById<Button>(R.id.itemButton)
        val runButtonPressed = findViewById<Button>(R.id.runButton)
        val healthItem = findViewById<TextView>(R.id.itemDrink)
        val enemyImage = findViewById<ImageView>(R.id.enemySprite) as ImageView
        val flavorText = findViewById<TextView>(R.id.FlavorText)
        val playerHPTextView = findViewById<TextView>(R.id.playerHP)
        val dButton = findViewById<Button>(R.id.devButton)
        var fightingTheFinalBoss = false
        // Made them minus 1to not trigger the code from the start
        var magicBuffDuration = 1999
        var attackBuffDuration = 1999
        var healAgainDuration = 0
        val analyzeHealth = findViewById<TextView>(R.id.healthText)
        val analyzeAttack = findViewById<TextView>(R.id.attackText)
        val analyzeDefense = findViewById<TextView>(R.id.defenseText)
        val analyzeMagic = findViewById<TextView>(R.id.magicText)
        // INITIAL OST SETTINGS
        mediaPlayer = MediaPlayer.create(applicationContext, R.raw.celica)
        mediaPlayer.isLooping = true
        mediaPlayer?.start()
        fun changeOST() {
            mediaPlayer.stop()
            if (enemyName == "Gohan") {
                mediaPlayer = MediaPlayer.create(applicationContext, R.raw.celica)
                mediaPlayer.start()
                println("cuz the enemy is gohan this ost")
                mediaPlayer.isLooping = true

            }
            if (enemyName == "Pepe") {
                mediaPlayer = MediaPlayer.create(applicationContext, R.raw.pathbattle)
                mediaPlayer.start()
                println("cuz the enemy is pepe this ost")
                mediaPlayer.isLooping = true

            }
            if (enemyName == "Jagger") {
                mediaPlayer = MediaPlayer.create(applicationContext, R.raw.reason)
                mediaPlayer.start()
                println("cuz the enemy is jagger this ost")
                mediaPlayer.isLooping = true

            }
        }


        // ITEMS

        fun removeChadJuiceItem() {
            healthItem.isEnabled = false
            juiceLeft -= 1
            healthItem.text = getString(R.string.chadJuice)
        }

        fun revealChadJuiceItem() {
            healthItem.isEnabled = true
            itemButtonPressed.isEnabled = false
            healthItem.text = getString(R.string.chadJuiceRevealed)
        }

        // TIMER


        // WIN/LOSE CONDITION

        fun youWin() {
            enemyImage.setImageResource(0)
            soulsTaken += 1
            juiceLeft += 1
            println("Enemy Killed")
            if (playerHealth == enemyHealth) {
                println("Wait, you also died...")
            }
            runButtonPressed.text = getString(R.string.nextString)
            bashButtonPressed.isClickable = false
            psyButtonPressed.isClickable = false
            itemButtonPressed.isClickable = false
            runButtonPressed.isClickable = true
            runButtonPressed.isEnabled = true
            healthItem.isEnabled = false
            bashButtonPressed.isEnabled = false
            psyButtonPressed.isEnabled = false
            itemButtonPressed.isEnabled = false

            fightingTheFinalBoss = false
            mediaPlayer.stop()
            mediaPlayer.isLooping = false
            mediaPlayer.prepare()

            if (fightingTheFinalBoss == false) {
                mediaPlayer = MediaPlayer.create(applicationContext, R.raw.victory)
            }
            mediaPlayer.start()
        }


        fun youLose() {
            println("Player Died")
            if (playerHealth == enemyHealth) {
                println("Oh, they died too...cool")
            }
            runButtonPressed.text = getString(R.string.restartString)
            bashButtonPressed.isClickable = false
            psyButtonPressed.isClickable = false
            itemButtonPressed.isClickable = false
            runButtonPressed.isClickable = true
            runButtonPressed.isEnabled = true
            bashButtonPressed.isEnabled = false
            psyButtonPressed.isEnabled = false
            itemButtonPressed.isEnabled = false
            healthItem.isEnabled = false
            playerHPTextView.text = "HP: 0/50"
            soulsTaken = -1
            fightingTheFinalBoss = false
            attackBuffDuration = 0
            magicBuffDuration = 0
            healAgainDuration = 0
            mediaPlayer.stop()
            mediaPlayer.prepare()

            if (fightingTheFinalBoss == false) {
                mediaPlayer = MediaPlayer.create(applicationContext, R.raw.loss)
                // I wanna cry..
            }
            mediaPlayer.start()
            println("can you hear me? Don't give up!")
        }


        fun youWonTheGame() {
            println("YOU won, wow")
            fightingTheFinalBoss = false
            runButtonPressed.text = getString(R.string.restartString)
            bashButtonPressed.isClickable = false
            psyButtonPressed.isClickable = false
            itemButtonPressed.isClickable = false
            runButtonPressed.isClickable = true
            runButtonPressed.isEnabled = true
            bashButtonPressed.isEnabled = false
            psyButtonPressed.isEnabled = false
            itemButtonPressed.isEnabled = false
            healthItem.isEnabled = false
            playerHPTextView.text = "HP: 0/50"
            soulsTaken = -1
            fightingTheFinalBoss = false
            attackBuffDuration = 0
            magicBuffDuration = 0
            healAgainDuration = 0

            playerMAtk = playerAtk / 2
        }
        // ATTACKING CALCULATIONS

        // player


        fun normalPlayerAttack() {
            enemyHealth -= (playerAtk - enemyDef)
            println("Enemy health is: $enemyHealth")
        }

        fun critPlayerAttack() {
            enemyHealth -= ((playerAtk - enemyDef) * 2)
            println("YOU GOT A CRIT!!")
            println("Enemy health is $enemyHealth")
        }

        fun bashAtk() {
            var randomPlayerCritChance = Random.nextInt(100)
            if (randomPlayerCritChance <= 12) {
                critPlayerAttack()
            } else {
                normalPlayerAttack()
            }
        }

        fun psyAtk1() {
            enemyHealth -= playerMAtk
            println("Enemy health is: $enemyHealth")
        }

        // Add a Guard Function


        // Enemy battle functions

        // Enemy Crit

        fun normalEnemyAttack() {
            playerHealth -= (enemyAtk - playerDef)
            println("$enemyName did $enemyAtk - $playerDef damage!")
            playerHPTextView.text = "HP: $playerHealth/50"
        }

        fun critEnemyAttack() {
            playerHealth -= ((enemyAtk - playerDef) * 2)
            println("THEY GOT A CRIT!!")
            println("Player health is $playerHealth")
            playerHPTextView.text = "HP: $playerHealth/50"
        }

        fun enemyBashAtk() {
            var randomEnemyCritChance = Random.nextInt(100)
            if (randomEnemyCritChance <= 12) {
                critEnemyAttack()
            } else {
                normalEnemyAttack()
            }
        }


        fun enemyPsyAtk() {
            playerHealth -= enemyMAtk
            println("Player health is: $playerHealth")
            playerHPTextView.text = "HP: $playerHealth/50"
        }


// Enemy Loaf picker

        fun finalBossLoaf() {
            var finalLoafNumber = Random.nextInt(1, 30)

            // ONLY OCCUPIES THR "1" SLOT UNTIL I ADD MORE LOAFS
            if (finalLoafNumber in 1..10 && healAgainDuration < 1) {
                println("Jagger takes a large swig of Chad Juice")
                healAgainDuration = 5
                enemyHealth += 500
            } else {
                finalLoafNumber = Random.nextInt(11, 30)
            }

            if (finalLoafNumber in 11..15) {
                println("bruh")
            }
            if (finalLoafNumber in 16..20) {
                attackBuffDuration = 3
                println("The enemy is getting serious!!")
                enemyAtk += 1000
                println(enemyAtk)
                if (enemyAtk > 500) {
                    enemyAtk = 500
                }
            }

            if (finalLoafNumber in 20..30) {
                println("Jagger looked over at Tyler")
                println("He feels encouraged!")
                println("Enemy's Magic Attacks are stronger!!")
                magicBuffDuration = 3
                if (magicBuffDuration > 0) {
                    // replace this with a percent
                    enemyMAtk += 300
                    if (enemyMAtk > 750) {
                        enemyMAtk = 750
                        println("wow unlucky")
                    }
                }
            }
        }


        fun enemyLoaf() {
            var randomLoafNumber = Random.nextInt(3)
            if (randomLoafNumber == 0) {
                println("The enemy ...")
            }
            if (randomLoafNumber == 1) {
                println("The enemy looked over at Jagger.")
            }
            if (randomLoafNumber == 2) {
                println("The enemy changed his wallpaper to Balder's Gay.")
            }
            if (randomLoafNumber == 3) {
                println("The enemy called you a simp.")
            }
        }

        // Enemy Choice picker
        fun enemyAttackChoice() {
            // THIS METHOD COULD CHANGE DEPENDING ON EACH ENEMY IF I HAD THE TIME
            var choiceValue = Random.nextInt(5)
            if (choiceValue == 1 || choiceValue == 0 || choiceValue == 4) {
                println("bash attack")
                enemyBashAtk()
            }
            if (choiceValue == 2 || choiceValue == 5) {
                println("psy attack")
                enemyPsyAtk()
            }
            if (choiceValue == 3) {
                if (fightingTheFinalBoss == true) {
                    finalBossLoaf()
                } else {
                    println("loafed")
                    enemyLoaf()
                }
            }
        }


        fun spawnEnemy() {
                var randomSpawn = Random.nextInt(1, 11)
                if (randomSpawn in 1..100) {
                    enemyName = "Pepe"
                    enemyAtk = 30
                    enemyDef = 0
                    enemyHealth = 20
                    enemyMAtk = 1
                    enemyImage.setImageResource(R.drawable.pepe)
                    println("PEPE SPAWNED")


                }
                changeOST()
                mediaPlayer.start()
            }
        // ALL ENEMIES


        // RUN FUNCTION
        //REVERTS EVERYTHING TO THE FIRST GOHAN
        fun lostBattle() {
            // FIX THIS BUG IDK WHY THIS SHOULD BE MUTABLE -_-
            //  enemyImage.drawable = getDrawable(R.drawable.test)
            enemyImage.setImageResource(R.drawable.gohan)
            enemyHealth = 50
            enemyAtk = 13
            enemyDef = 8
            playerHealth = 50

            enemyMAtk = enemyAtk / 2
            playerAtk = 12
            playerDef = 7
            playerMAtk = playerAtk / 2
            soulsTaken = 0
            juiceLeft = 1
            enemyName = "Gohan"
            changeOST()
            bashButtonPressed.isClickable = true
            psyButtonPressed.isClickable = true
            itemButtonPressed.isClickable = true
            runButtonPressed.isClickable = true
            healthItem.isEnabled = false
            bashButtonPressed.isEnabled = true
            psyButtonPressed.isEnabled = true
            itemButtonPressed.isEnabled = true
            runButtonPressed.isEnabled = true
            println(enemyHealth)
            println(playerHealth)
            runButtonPressed.text = "RUN"
            flavorText.text = "$enemyName Appeared!"
            println(enemyHealth)
            println(enemyAtk)
            println(enemyDef)
            playerHPTextView.text = "HP: $playerHealth/50"
            println("$soulsTaken is your souls")

            playerHPTextView.text = "HP: $playerHealth/50"

        }


        fun finalBattle() {
            fightingTheFinalBoss = true
            enemyName = "Jagger"
            changeOST()
            flavorText.text = "$enemyName Appeared!"
            enemyImage.setImageResource(R.drawable.namelessking)
            playerHPTextView.text = "$playerHealth/5000"
            playerHealth = 5000
            playerAtk = 200
            playerDef = 50
            playerMAtk = playerAtk - 50
            enemyAtk = 125
            enemyHealth = 10000
            enemyDef = 100
            enemyMAtk = 100
            playerHPTextView.text = "$playerHealth/5000"
            runButtonPressed.text = "RUN?"
            bashButtonPressed.isClickable = true
            psyButtonPressed.isClickable = true
            itemButtonPressed.isClickable = true
            runButtonPressed.isClickable = true
            healthItem.isEnabled = false
            bashButtonPressed.isEnabled = true
            psyButtonPressed.isEnabled = true
            itemButtonPressed.isEnabled = true
            runButtonPressed.isEnabled = true
            // magic surge

             // Insert the stats, OST, image, and other things in here.


        }


        fun nextBattle() {

            bashButtonPressed.isClickable = true
            psyButtonPressed.isClickable = true
            itemButtonPressed.isClickable = true
            runButtonPressed.isClickable = true
            healthItem.isEnabled = false
            bashButtonPressed.isEnabled = true
            psyButtonPressed.isEnabled = true
            itemButtonPressed.isEnabled = true
            runButtonPressed.isEnabled = true
            println(enemyHealth)
            println(playerHealth)

            runButtonPressed.text = "RUN"

            spawnEnemy()
            flavorText.text = "$enemyName Appeared!"
//                enemyHealth = Random.nextInt(20, 75)
//                enemyAtk = Random.nextInt(8, 20)
//                enemyDef = Random.nextInt(0, 12)

            // Add limiting values tp the minimum and maximum atk/def like with HP
            println(enemyHealth)
            println(enemyAtk)
            println(enemyDef)
            playerHPTextView.text = "HP: $playerHealth/50"
            println("your souls are$soulsTaken")
            changeOST()
        }

        // Have a chance for run to fail
        fun runAway() {
            // Wrap the stats and the image in the random generator when you make that
            // then assign the image change to the stats
            // but for now we hard coded it cuz I don't have the time yet
            bashButtonPressed.isClickable = true
            psyButtonPressed.isClickable = true
            itemButtonPressed.isClickable = true
            runButtonPressed.isClickable = true
            healthItem.isEnabled = false
            bashButtonPressed.isEnabled = true
            psyButtonPressed.isEnabled = true
            itemButtonPressed.isEnabled = true
            runButtonPressed.isEnabled = true
            println(enemyHealth)
            println(playerHealth)

            runButtonPressed.text = "RUN"
            spawnEnemy()
            flavorText.text = "$enemyName Appeared!"
//            enemyHealth = Random.nextInt(20,75)
//            enemyAtk = Random.nextInt(8,20)
//             enemyDef = Random.nextInt(0, 12)
            // MAKE AN EASER EGG BUTTON TO MAKE RANDOM GOHANS
            // Add limiting values tp the minimum and maximum atk/def like with HP
            playerHPTextView.text = "HP: $playerHealth/50"
            changeOST()
        }


        // TURNS
        fun playerTurnStart() {
            bashButtonPressed.isEnabled = true
            psyButtonPressed.isEnabled = true
            runButtonPressed.isEnabled = true
            itemButtonPressed.isEnabled = true
            if (juiceLeft <= 0) {
                itemButtonPressed.isEnabled = false
            }
            if (magicBuffDuration in 1..3) {
                magicBuffDuration -= 1
                println("the buff lasts for $magicBuffDuration turns")

            }
            if (attackBuffDuration in 1..3) {
                attackBuffDuration -= 1
                println("the buff lasts for $attackBuffDuration turns")
            }
            if (healAgainDuration > 0) {
                healAgainDuration -= 1
                println("the heal limit lasts for $healAgainDuration turns")
            }
            if (fightingTheFinalBoss == true && magicBuffDuration == 0) {
                // REPLACE THESE WITH REAL PERCENT DECREASES
                enemyMAtk = 100
                println("now that the buff is gone mag atk is $enemyMAtk")
                magicBuffDuration = 1999
            }
            if (fightingTheFinalBoss == true && attackBuffDuration == 0) {
                enemyAtk = 125
                println("now that the buff is gone  atk is $enemyAtk")
                attackBuffDuration = 1999
            }
        }


        fun enemyTurnEnded() {
            // This gets called when the second timer ends


        }

        fun enemyTurnStart() {
            //this starts a second timer and ends the first one

            /* Enemy randomly decides if they will normal attack
                normal attack crit
                or normal magic attack
                 */
            enemyAttackChoice()
            if (playerHealth < 1) {
                youLose()
            }
            if (enemyHealth < 1) {
                youWin()
            }
            if (playerHealth >= 1) {
                playerTurnStart()
            }

        }

        fun turnEnded() {
            // this starts the first timer
            flavorText.text = ""
            bashButtonPressed.isEnabled = false
            psyButtonPressed.isEnabled = false
            itemButtonPressed.isEnabled = false
            runButtonPressed.isEnabled = false
            if (playerHealth < 1) {
                youLose()
            }
            if (fightingTheFinalBoss == false && enemyHealth < 1) {
                youWin()
            }
            if (fightingTheFinalBoss == true && enemyHealth < 1) {
                youWonTheGame()
            }
            if (enemyHealth >= 1) {
                enemyTurnStart()
            }
        }


        //          run fail chance
        fun runCouldFail() {
            var failChance = Random.nextInt(100)
            if (failChance < 33) {
                println("RUN FAILED")
                println("cuz the number was: $failChance")
                turnEnded()
            } else {
                runAway()
            }
        }

        // Healing Code (very hard to do)
        fun amountHealed() {
            if (fightingTheFinalBoss == true) {
                playerHealth += 2500
            }
            if (fightingTheFinalBoss == true && playerHealth >= 5000) {
                playerHealth = 5000
            }
            if (fightingTheFinalBoss == false) {
                playerHealth += 50
            }
            if (playerHealth >= 50 && fightingTheFinalBoss == false) {
                playerHealth = 50
            }
            playerHPTextView.text = "HP: $playerHealth/50"
            println("You used Chad Juice")
            removeChadJuiceItem()
            turnEnded()


        }



        healthItem.setOnClickListener {
            if (playerHealth in 1..5000 && fightingTheFinalBoss == true) {
                if (playerHealth >= 5000) {
                    println("max")
                } else {
                    amountHealed()
                }
            }
            if (playerHealth in 1..50 && fightingTheFinalBoss == false) {
                if (playerHealth >= 50) {
                    println("Already at max HP")
                } else {
                    amountHealed()
                }
            }
        }
// Bash Button
        bashButtonPressed.setOnClickListener {
            healthItem.text = ""
            analyzeHealth.text = ""
            analyzeDefense.text = ""
            analyzeAttack.text = ""
            analyzeMagic.text = ""
            bashAtk()
            turnEnded()
        }


// Psy Turn
        psyButtonPressed.setOnClickListener {
            healthItem.text = ""
            analyzeHealth.text = ""
            analyzeDefense.text = ""
            analyzeAttack.text = ""
            analyzeMagic.text = ""
            psyAtk1()
            turnEnded()

        }
// Item Turn
        itemButtonPressed.setOnClickListener {
            analyzeHealth.text = ""
            analyzeDefense.text = ""
            analyzeAttack.text = ""
            analyzeMagic.text = ""
            revealChadJuiceItem()
            itemButtonPressed.isEnabled = false
        }
// Run Turn
        runButtonPressed.setOnClickListener {
            healthItem.text = ""
            analyzeHealth.text = ""

            if (playerHealth > 1 && enemyHealth > 1 && fightingTheFinalBoss == false) {
                runCouldFail()
            }
            if (soulsTaken in 0..4 && fightingTheFinalBoss == false) {
                nextBattle()
            }
            if (soulsTaken == 5) {
                finalBattle()
            }
            if (soulsTaken == -1) {
                lostBattle()
            }
            if (soulsTaken >= 6 && fightingTheFinalBoss == false) {
            println("uhhh I gues you won then")
                //     endGame()
                // wonGame() reduces souls to minus 1 and congrats you
            }
            if (enemyName == "Jagger" && fightingTheFinalBoss == true) {
                println("You can't run now.")
            }
        }

        dButton.setOnClickListener {
            soulsTaken = 5
            println("your souls are $soulsTaken")
        }


        enemyImage.setOnClickListener {
            if (playerHealth > 0 && enemyHealth > 0)
            if (analyzeHealth.text != "") {
                analyzeHealth.text = ""
                analyzeAttack.text = ""
                analyzeDefense.text = ""
                analyzeMagic.text = ""
            } else {
                println("stuff happens")
                analyzeHealth.text = "HP: $enemyHealth"
                analyzeAttack.text = "ATK: $enemyAtk"
                analyzeDefense.text = "DEF: $enemyDef"
                analyzeMagic.text = "MAG: $enemyMAtk"
            }

            // I FIGURED IT OUT
            // HOW TO REMOVE AN IMAGE
            // enemyImage.setImageResource(0)
            // HOW TO CHANGE
            // enemyImage.setImageResource(R.drawable.test)
        }

    }
}