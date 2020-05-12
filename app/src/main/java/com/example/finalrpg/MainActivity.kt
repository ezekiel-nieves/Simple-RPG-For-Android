package com.example.finalrpg

import android.graphics.Color
import android.media.AudioAttributes
import android.media.Image
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.core.view.isInvisible
import com.google.android.material.snackbar.Snackbar
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
        var damageCalc = 0
        var damageTaken = 0
        var healthHealed = 0
        var enemyHealthHealed = 0
        var playerMaxHP = 50
        var takenW = false
        val bashButtonPressed = findViewById<Button>(R.id.bashButton)
        val psyButtonPressed = findViewById<Button>(R.id.psyButton)
        val itemButtonPressed = findViewById<Button>(R.id.itemButton)
        val runButtonPressed = findViewById<Button>(R.id.runButton)
        val healthItem = findViewById<TextView>(R.id.itemDrink)
        val enemyImage = findViewById<ImageView>(R.id.enemySprite) as ImageView
        val flavorText = findViewById<TextView>(R.id.FlavorText)
        val playerHPTextView = findViewById<TextView>(R.id.playerHP)
        val dButton = findViewById<Button>(R.id.devButton)
        val persona = findViewById<Button>(R.id.akechiButton)
        var fightingTheFinalBoss = false
        // Made them minus 1to not trigger the code from the start
        var magicBuffDuration = 1999
        var attackBuffDuration = 1999
        var critBuffDuration = 1999
        var defenseBuffDuration = 1999
        var healAgainDuration = 0
        var runFailCounter = 2
        var choiceValue = 0
        persona.isInvisible = true
        var loki = false
        val analyzeHealth = findViewById<TextView>(R.id.healthText)
        val analyzeAttack = findViewById<TextView>(R.id.attackText)
        val analyzeDefense = findViewById<TextView>(R.id.defenseText)
        val analyzeMagic = findViewById<TextView>(R.id.magicText)
        val enemyInfo = findViewById<TextView>(R.id.enemyInfo)
        val playerInfo = findViewById<TextView>(R.id.playerInfo)
        // THESE UPDATE EACH TIME A WORTHWHILE ACTION HAPPENS
        // INITIAL OST SETTINGS


        mediaPlayer.release()
        mediaPlayer = MediaPlayer.create(applicationContext, R.raw.celica)
        mediaPlayer.isLooping = true
        mediaPlayer?.start()
        fun changeOST() {
            mediaPlayer.stop()
            mediaPlayer.reset()
            // THIS was a PAIN
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
            if (enemyName == "Chad") {
                mediaPlayer = MediaPlayer.create(applicationContext, R.raw.twilight)
                mediaPlayer.start()
                println("cuz the enemy is pepe this ost")
                mediaPlayer.isLooping = true

            }
            if (enemyName == "SoyBoy") {
                mediaPlayer = MediaPlayer.create(applicationContext, R.raw.retrohip)
                mediaPlayer.start()
                println("cuz the enemy is pepe this ost")
                mediaPlayer.isLooping = true

            }
            if (enemyName == "2Pac") {
                mediaPlayer = MediaPlayer.create(applicationContext, R.raw.lifegoeson)
                mediaPlayer.start()
                println("cuz the enemy is pepe this ost")
                mediaPlayer.isLooping = true

            }
            if (enemyName == "Slime") {
                mediaPlayer = MediaPlayer.create(applicationContext, R.raw.courage)
                mediaPlayer.start()
                println("cuz the enemy is slime this ost")
                mediaPlayer.isLooping = true

            }
            if (enemyName == "Jagger") {
                mediaPlayer = MediaPlayer.create(applicationContext, R.raw.god)
                mediaPlayer.start()
                println("cuz the enemy is jagger this ost")
                mediaPlayer.isLooping = true

            }
            if (enemyName == "Crow") {
                mediaPlayer = MediaPlayer.create(applicationContext, R.raw.ourdream)
                mediaPlayer.start()
                println("cuz the enemy is jagger this ost")
                mediaPlayer.isLooping = true
            }

            if (enemyName == "SoyBoy") {
                enemyInfo.setTextColor(Color.parseColor("#696969"))

                playerInfo.setTextColor(Color.parseColor("#696969"))
            } else {

                enemyInfo.setTextColor(Color.parseColor("#FFFFFF"))

                playerInfo.setTextColor(Color.parseColor("#FFFFFF"))

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

        // WIN/LOSE CONDITION

        fun youWin() {
            enemyInfo.isInvisible = true
            enemyInfo.setTextColor(Color.parseColor("#FFFFFF"))

            playerInfo.setTextColor(Color.parseColor("#FFFFFF"))
            enemyImage.setImageResource(0)
            soulsTaken += 1
            if (enemyName == "SoyBoy" ) {
                println("They carried nothing")
            }
            if (enemyName == "Slime"){
                juiceLeft += 3
                enemyInfo.text = "You found Chad Juice \n        +3"
                enemyInfo.isInvisible = false
            }
            if (enemyName != "Slime" && enemyName != "SoyBoy") {
                juiceLeft += 1
                enemyInfo.text = "You found Chad Juice!"
                enemyInfo.isInvisible = false
            }
            println("Enemy Killed")
            flavorText.text = " YOU WIN!!"

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
            if (fightingTheFinalBoss == true) {
                // INSERT VICTORY SONG
            }
            mediaPlayer.start()
        }


        fun youLose() {
            loki = false
            println("Player Died")
            flavorText.text = " YOU LOSE..."
            runButtonPressed.text = getString(R.string.restartString)
            if (playerHealth == enemyHealth) {
                println("Oh, they died too...cool")
            }
            if (enemyName == "Crow") {
                flavorText.text = " YOU DIED..."
                enemyInfo.text = "\t$enemyName shot you"
                playerInfo.text = "or did he?"
            }

            bashButtonPressed.isClickable = false
            psyButtonPressed.isClickable = false
            itemButtonPressed.isClickable = false
            runButtonPressed.isClickable = true
            runButtonPressed.isEnabled = true
            bashButtonPressed.isEnabled = false
            psyButtonPressed.isEnabled = false
            itemButtonPressed.isEnabled = false
            healthItem.isEnabled = false
            playerHPTextView.text = "HP: $playerHealth/$playerMaxHP"
            soulsTaken = -1
            fightingTheFinalBoss = false
            attackBuffDuration = 1999
            defenseBuffDuration = 1999
            critBuffDuration = 1999
            magicBuffDuration = 1999
            mediaPlayer.stop()
            mediaPlayer.prepare()
            mediaPlayer = MediaPlayer.create(applicationContext, R.raw.loss)
            mediaPlayer.start()
            println("can you hear me? Don't give up!")
        }


        fun youWonTheGame() {



            enemyImage.setImageResource(0)
            fightingTheFinalBoss = false
            runButtonPressed.text = "Reset?"
            enemyInfo.isInvisible = false
            playerInfo.isInvisible = false
            bashButtonPressed.isClickable = false
            psyButtonPressed.isClickable = false
            itemButtonPressed.isClickable = false
            runButtonPressed.isClickable = true
            runButtonPressed.isEnabled = true
            bashButtonPressed.isEnabled = false
            psyButtonPressed.isEnabled = false
            itemButtonPressed.isEnabled = false
            healthItem.isEnabled = false
            soulsTaken = -1
            fightingTheFinalBoss = false
            critBuffDuration = 1999
            attackBuffDuration = 1999
            defenseBuffDuration = 1999
            magicBuffDuration = 1999

            playerMAtk = playerAtk / 2
            enemyMAtk = enemyAtk / 2

            mediaPlayer.stop()
            mediaPlayer.prepare()
            if (loki == true) {
                enemyInfo.text = "\n     You did it. \n You made it this far. \n       Thanks..."
                playerInfo.text = " \n Play Persona 5."
                flavorText.isInvisible = true
                mediaPlayer = MediaPlayer.create(applicationContext, R.raw.sunsetbridge)
                println("insert bs")
                loki = false
                enemyName = ""
                soulsTaken = -1
            } else {
                enemyInfo.text = "\n Thanks for playing!!"
                playerInfo.text = " \n I enjoyed this class."
                flavorText.text = "\tINCREDIBLE!!"

                mediaPlayer = MediaPlayer.create(applicationContext, R.raw.results)
                persona.isClickable = true
                persona.isEnabled = true
                persona.isInvisible = false
            }

            mediaPlayer.start()
        }
        // ATTACKING CALCULATIONS

        // player


        fun normalPlayerAttack() {
            damageCalc = playerAtk - enemyDef
            if (damageCalc < 0) {
                damageCalc = 0
            }
            enemyHealth -= damageCalc
            if (enemyName == "Crow") {
                println("Enemy health is: $enemyHealth")
                playerInfo.text = " You used Cleave!\n You did $damageCalc damage!"
                playerInfo.isInvisible = false
                return
            }
            println("Enemy health is: $enemyHealth")
            playerInfo.text = "\t You used BASH! \n You did $damageCalc damage!"
            playerInfo.isInvisible = false
        }

        fun critPlayerAttack() {
            damageCalc = ((playerAtk - enemyDef) * 4)
            if (damageCalc  < 0 ) {
                damageCalc = 0
            }
            enemyHealth -= damageCalc
            // add a crit animation to show damage given is higher
            if (enemyName == "Crow") {
                println("Enemy health is: $enemyHealth")
                playerInfo.text = "YOU LANDED A CRIT!! \n You did $damageCalc damage!"
                playerInfo.isInvisible = false
            }
            println("Enemy health is: $enemyHealth")
            playerInfo.text = "\t YOU GOT A CRIT!! \n You did $damageCalc damage!"
            playerInfo.isInvisible = false
        }

        fun bashAtk() {
            if (enemyName == "2Pac") {
                playerInfo.text = "You shot $enemyName 5 times! \nhe took it and smiled"
                playerInfo.isInvisible = false
                return
            }
            var randomPlayerCritChance = Random.nextInt(100)
            if (randomPlayerCritChance <= 10) {
                critPlayerAttack()
            } else {
                normalPlayerAttack()
            }
        }

        fun psyAtk1() {
            if (enemyName == "2Pac") {
                playerInfo.text = "You shot $enemyName 5 times! \nhe took it and smiled"
                playerInfo.isInvisible = false
                return
            }
            if (enemyName == "Slime") {
                playerInfo.text = "\t You used PSY! \n \tbut it missed!!"
                playerInfo.isInvisible = false
                return
            }
            damageCalc = playerMAtk
            enemyHealth -= damageCalc
            println("Enemy health is: $enemyHealth")
            if (enemyName == "Crow") {
                playerInfo.text = "\t You used Eigaon! \n \t You did $damageCalc damage!"
                playerInfo.isInvisible = false
                return
            }
            playerInfo.text = "\t You used PSY! \n You did $damageCalc damage!"
            playerInfo.isInvisible = false
        }

        // Add a Guard Function


        // Enemy battle functions

        // Enemy Crit

        fun normalEnemyAttack() {
             damageTaken = enemyAtk - playerDef
            if (damageTaken < 0) {
                damageTaken = 0
            }
            playerHealth -= damageTaken
            if (enemyName == "Crow") {
                enemyInfo.text = " $enemyName used \n Megaton Raid! \n He did $damageTaken damage! "
                playerHPTextView.text = "HP: $playerHealth/$playerMaxHP"
                enemyInfo.isInvisible = false
                return
            }
            enemyInfo.text = "\t $enemyName used BASH! \n They did $damageTaken damage! "
            playerHPTextView.text = "HP: $playerHealth/$playerMaxHP"
            enemyInfo.isInvisible = false

        }

        fun critEnemyAttack() {
            damageTaken = ((enemyAtk - playerDef) * 4)
            if (damageTaken < 0) {
                damageTaken = 0
            }
            playerHealth -= damageTaken
            if (enemyName == "Crow") {
                enemyInfo.text = "\t HE LANDED A CRIT!! \n He did $damageTaken damage! "
                playerHPTextView.text = "HP: $playerHealth/$playerMaxHP"
                enemyInfo.isInvisible = false
                return
            }
            enemyInfo.text = "\t THEY GOT A CRIT!! \n They did $damageTaken damage! "
            playerHPTextView.text = "HP: $playerHealth/$playerMaxHP"
            enemyInfo.isInvisible = false
        }

        fun enemyBashAtk() {
            var randomEnemyCritChance = Random.nextInt(100)
            if (enemyName == "Chad" && randomEnemyCritChance <= 50) {
                critEnemyAttack()
                return
            }
            if (enemyName == "Crow" && critBuffDuration in 1..4 && randomEnemyCritChance <= 50) {
                critEnemyAttack()
                return
            }
            if (randomEnemyCritChance <= 12) {
                critEnemyAttack()
                return
            }
            normalEnemyAttack()
        }

        fun enemyPsyAtk() {
            damageTaken = enemyMAtk
            if (damageTaken < 0) {
                damageTaken = 0
            }
                playerHealth -= damageTaken

                if (enemyName == "2Pac") {
                    enemyInfo.text = " $enemyName called his goons\n \t \t    You died!"
                    playerHPTextView.text = "HP: $playerHealth/$playerMaxHP"
                    enemyInfo.isInvisible = false
                    return
                }
            if (enemyName == "Crow") {
                enemyInfo.text = " $enemyName used Kougaon! \n He did $damageTaken damage!"
                playerHPTextView.text = "HP: $playerHealth/$playerMaxHP"
                enemyInfo.isInvisible = false
                return
            }
                enemyInfo.text = "\t $enemyName used PSY! \n They did $damageTaken damage! "
                playerHPTextView.text = "HP: $playerHealth/$playerMaxHP"
                enemyInfo.isInvisible = false
        }


// Enemy Loaf picker

        fun finalBossLoaf() {
            var finalLoafNumber = Random.nextInt(1, 81)

            // ONLY OCCUPIES THR "1" SLOT UNTIL I ADD MORE LOAFS
            if (finalLoafNumber in 1..20 && healAgainDuration < 1) {
                if (enemyName == "Crow") {
                    println("Robinhood uses dia")
                    enemyInfo.text = "$enemyName used Diarama!"
                    healAgainDuration = 5
                    enemyHealth += 100
                    if (enemyHealth >= 500) {
                        enemyHealth = 500
                    }
                } else {
                    println("Jagger takes a large swig of Chad Juice")
                    enemyInfo.text = "\tJagger takes a large \n swig of Chad Juice"
                    healAgainDuration = 5
                    enemyHealth += 500
                    if (enemyHealth >= 10000) {
                        enemyHealth = 10000
                    }
                }
            } else {
                finalLoafNumber = Random.nextInt(21, 81)
                }

                if (finalLoafNumber in 21..40) {
                    if (enemyName == "Crow") {
                        defenseBuffDuration = 4
                        println("The enemy is  guarding!!")
                        enemyInfo.text = "$enemyName used Rakukaja! \n     DEFENSE UP!!"
                        enemyDef += 50
                        println(enemyAtk)
                        if (enemyDef >= 100) {
                            enemyDef = 100
                        }
                    }  else {
                        defenseBuffDuration = 4
                        println("The enemy is  guarding!!")
                        enemyInfo.text = "$enemyName is guarding!! \n    DEFENSE UP!!"
                        enemyDef += 1000
                        println(enemyAtk)
                        if (enemyDef > 500) {
                            enemyDef = 500
                        }
                    }
                }

                if (finalLoafNumber in 41..60) {
                    if (enemyName == "Crow") {
                        attackBuffDuration = 4
                        println("The enemy is getting serious!!")
                        enemyInfo.text = "$enemyName used Tarukaja! \n     ATTACK UP!!"
                        enemyAtk += 25
                        println(enemyAtk)
                        if (enemyAtk > 100) {
                            enemyAtk = 100
                        }
                    } else {
                        attackBuffDuration = 4
                        println("The enemy is getting serious!!")
                        enemyInfo.text = "$enemyName looks serious! \n     ATTACK UP!!"
                        enemyAtk += 1000
                        println(enemyAtk)
                        if (enemyAtk > 500) {
                            enemyAtk = 500
                        }
                    }

                }

                if (finalLoafNumber in 61..80) {
                    if (enemyName == "Crow") {
                    enemyInfo.text = "$enemyName used Rebellion! \n     CRIT UP!!"
                    critBuffDuration = 4
                } else {
                    println("Jagger looked over at Tyler")
                    println("He feels encouraged!")
                    println("Enemy's Magic Attacks are stronger!!")
                    enemyInfo.text = "   $enemyName feels\n   encouraged!\n   PSY ATTACK UP!!"
                    magicBuffDuration = 4
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
            enemyInfo.isInvisible = false
        }


        fun enemyLoaf() {
            var randomLoafNumber = Random.nextInt(4)
            if (enemyName == "SoyBoy") {
                println("before this heal again was $healAgainDuration")
                enemyInfo.text = "\t $enemyName takes a large \n \t swig of Soy Milk"
                enemyInfo.isInvisible = false
                enemyHealth += 20
                if (enemyHealth >= 17) {
                    enemyHealth = 17
                    return
                }
            }
            if (enemyName == "2Pac") {
                println("before this heal again was $healAgainDuration")
                enemyPsyAtk()
                enemyInfo.isInvisible = false
                return
            }
            if (enemyName == "Slime") {
                enemyInfo.text = "\t  The $enemyName\n  seems listless"
                enemyInfo.isInvisible = false
                return
            }
            if (randomLoafNumber == 0) {
                println("The enemy ...")
                enemyInfo.text = "$enemyName dozes off"
            }
            if (randomLoafNumber == 1) {
                enemyInfo.text = "$enemyName looks at Jagger."
            }
            if (randomLoafNumber == 2) {
                enemyInfo.text =
                    " $enemyName changes their\n wallpaper to\n    Baldur's Gay."
            }
            if (randomLoafNumber == 3) {
                enemyInfo.text = "$enemyName calls you a \n\tsimp."
            }
            enemyInfo.isInvisible = false
        }


        fun pepeSelector() {
            choiceValue = Random.nextInt(0, 5)
            if (choiceValue == 0 || choiceValue == 1 || choiceValue == 2) {
                println("bash attack")
                enemyBashAtk()
            }
            if (choiceValue == 3 || choiceValue == 4) {
                println("psy attack")
                enemyPsyAtk()
            }
            println("pepe value was $choiceValue")
        }


        fun slimeSelector() {
            choiceValue = Random.nextInt(0, 5)
            if (choiceValue == 0 || choiceValue == 1 || choiceValue == 2) {
                println("bash attack")
                enemyBashAtk()
            }
            if (choiceValue == 3 || choiceValue == 4) {
                println("loaf")
                enemyLoaf()
            }
            println("slime value was $choiceValue")
        }
        fun crowSelector() {
            choiceValue = Random.nextInt(0, 6)
            if (choiceValue == 0 || choiceValue == 1) {
                println("psy attack")
                enemyPsyAtk()
            }
            if (choiceValue == 2 || choiceValue == 5) {
                println("bash attack")
                enemyBashAtk()
            }
            if (choiceValue == 3 || choiceValue == 4) {
                println("loaf")
                finalBossLoaf()
            }
            println("akechi value was $choiceValue")
        }

        fun tupacShot() {
            enemyLoaf()
        }
        // Enemy Choice picker
        fun enemyAttackChoice() {
            // THIS METHOD COULD CHANGE DEPENDING ON EACH ENEMY IF I HAD THE TIME
            if (enemyName == "2Pac") {
                println("he got you")
                tupacShot()
                return
            }
            if (enemyName == "Pepe") {
                println("pepe attack")
                pepeSelector()
                return
            }
            if (enemyName == "Slime") {
                println("pepe attack")
                slimeSelector()
                return
            }
            if (enemyName == "Crow") {
                println("pepe attack")
                crowSelector()
                return
            }
                choiceValue = Random.nextInt(0,6)
                if (choiceValue == 0 || choiceValue == 1 || choiceValue == 2) {
                    println("bash attack")
                    enemyBashAtk()
                }
                if (choiceValue == 3 || choiceValue == 4) {
                    println("psy attack")
                    enemyPsyAtk()
                }
                if (choiceValue == 5) {
                    if (fightingTheFinalBoss == true || loki == true) {
                        finalBossLoaf()
                    } else {
                        println("loafed")
                        enemyLoaf()
                    }
                }
            println("the value for attack was $choiceValue")
        }


        fun spawnEnemy() {
                var randomSpawn = Random.nextInt(1, 11)
                if (randomSpawn in 1..2) {
                    enemyName = "Pepe"
                    enemyAtk = 30
                    enemyDef = 0
                    enemyHealth = 20
                    enemyMAtk = 1
                    enemyImage.setImageResource(R.drawable.pepe)
                    println("PEPE SPAWNED")


                }
            if (randomSpawn in 3..5) {
                enemyName = "Chad"
                enemyAtk = 8
                enemyDef = 5
                enemyHealth = 75
                enemyMAtk = 0
                enemyImage.setImageResource(R.drawable.chod)
                println("CHAD SPAWNED")


            }
            if (randomSpawn in 6..8) {
                enemyName = "SoyBoy"
                enemyAtk = 7
                enemyDef = 0
                enemyHealth = 17
                enemyMAtk = 5
                enemyImage.setImageResource(R.drawable.soyboy)
                println("SOYBOY SPAWNED")


            }
            if (randomSpawn == 9) {
                enemyName = "2Pac"
                enemyAtk = 100000
                enemyDef = 100000
                enemyHealth = 100000
                enemyMAtk = 100000
                enemyImage.setImageResource(R.drawable.tupac)
                println("2PAC SPAWNED")
                playerInfo.text = "You feel uneasy..."
                playerInfo.isInvisible = false

            }
            if (randomSpawn == 10) {
                enemyName = "Slime"
                enemyAtk = 10
                enemyDef = 11
                enemyHealth = 20
                enemyMAtk = 5
                enemyImage.setImageResource(R.drawable.metalslime)
                println("SLIME SPAWNED")


            }
            println(randomSpawn)
                changeOST()
                mediaPlayer.start()
            }
        // ALL ENEMIES


        // RUN FUNCTION
        //REVERTS EVERYTHING TO THE FIRST GOHAN
        fun lostBattle() {
            // FIX THIS BUG IDK WHY THIS SHOULD BE MUTABLE -_-
            //  enemyImage.drawable = getDrawable(R.drawable.test)
            enemyImage.setImageResource(R.drawable.gohan1)

            enemyHealth = 50
            enemyAtk = 13
            enemyDef = 8
            playerMaxHP = 50
            playerHealth = 50

            enemyMAtk = enemyAtk / 2
            playerAtk = 12
            playerDef = 7
            playerMAtk = playerAtk / 2
            soulsTaken = 0
            juiceLeft = 1
            enemyName = "Gohan"
            changeOST()
            playerInfo.isInvisible = true
            enemyInfo.isInvisible = true
            flavorText.isInvisible = false
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
            playerHPTextView.text = "HP: $playerHealth/$playerMaxHP"
            println("$soulsTaken is your souls")

            playerHPTextView.text = "HP: $playerHealth/$playerMaxHP"
        }


        fun finalBattle() {
            playerInfo.isInvisible = true
            enemyInfo.isInvisible = true

            if (loki == true) {
                persona.isInvisible = true


                enemyName = "Crow"
                flavorText.text = "$enemyName Appeared!"
                playerInfo.text = "$enemyName awaits."
                enemyImage.setImageResource(R.drawable.akechi)
                playerMaxHP = 500
                playerHealth = 500
                playerAtk = 50
                playerDef = 25
                playerMAtk = 30
                enemyAtk = 50
                enemyHealth = 500
                enemyDef = 25
                enemyMAtk = 30
            } else {

                playerInfo.isInvisible = false
                playerInfo.text = "You feel indomitable."
                fightingTheFinalBoss = true
                enemyName = "Jagger"


                flavorText.text = "$enemyName Appeared!"
                enemyImage.setImageResource(R.drawable.namelessking)
                playerMaxHP = 5000
                playerHealth = 5000
                playerAtk = 225
                playerDef = 50
                playerMAtk = 150
                enemyAtk = 150
                enemyHealth = 10000
                enemyDef = 100
                enemyMAtk = 125
                // magic surge
                // Insert the stats, OST, image, and other things in here.
            }
            itemButtonPressed.isEnabled = false
            itemButtonPressed.isClickable = false
            if (juiceLeft > 0) {
                itemButtonPressed.isEnabled = true
                itemButtonPressed.isClickable = true
            }
            playerHPTextView.text = "HP: $playerHealth/$playerMaxHP"
            playerHPTextView.text = "HP: $playerHealth/$playerMaxHP"
            runButtonPressed.text = "RUN?"
            bashButtonPressed.isClickable = true
            psyButtonPressed.isClickable = true
            itemButtonPressed.isClickable = true
            runButtonPressed.isClickable = true
            healthItem.isEnabled = false
            bashButtonPressed.isEnabled = true
            psyButtonPressed.isEnabled = true
            runButtonPressed.isEnabled = true
            enemyImage.isClickable = true
            changeOST()
        }

        fun nextBattle() {
            attackBuffDuration = 1999
            defenseBuffDuration = 1999
            magicBuffDuration = 1999
            critBuffDuration = 1999
            playerInfo.isInvisible = true
            enemyInfo.isInvisible = true
            bashButtonPressed.isClickable = true
            itemButtonPressed.isEnabled = false
            itemButtonPressed.isClickable = false
            if (juiceLeft > 0) {

                itemButtonPressed.isEnabled = true
                itemButtonPressed.isClickable = true
            }
            psyButtonPressed.isClickable = true
            runButtonPressed.isClickable = true
            bashButtonPressed.isEnabled = true
            psyButtonPressed.isEnabled = true
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
            if (enemyName == "2Pac") {
                playerInfo.text = "You feel uneasy..."
                playerInfo.isInvisible = false
            }
            println(enemyHealth)
            println(enemyAtk)
            println(enemyDef)
            println("your souls are$soulsTaken")
            changeOST()
        }

        // Have a chance for run to fail
        fun runAway() {
            // Wrap the stats and the image in the random generator when you make that
            // then assign the image change to the stats
            // but for now we hard coded it cuz I don't have the time yet
            if (juiceLeft > 0) {
                itemButtonPressed.isEnabled = true
                itemButtonPressed.isClickable = true
            }
            bashButtonPressed.isClickable = true
            psyButtonPressed.isClickable = true
            bashButtonPressed.isEnabled = true
            psyButtonPressed.isEnabled = true
            runButtonPressed.isEnabled = true
            playerInfo.isInvisible = true
            enemyInfo.isInvisible = true
            attackBuffDuration = 1999
            critBuffDuration = 1999
            defenseBuffDuration = 1999
            magicBuffDuration = 1999
            println(enemyHealth)
            println(playerHealth)

            runButtonPressed.text = "RUN"
            runFailCounter = 2
            spawnEnemy()
            flavorText.text = "$enemyName Appeared!"
//            enemyHealth = Random.nextInt(20,75)
//            enemyAtk = Random.nextInt(8,20)
//             enemyDef = Random.nextInt(0, 12)
            // MAKE AN EASER EGG BUTTON TO MAKE RANDOM GOHANS
            // Add limiting values tp the minimum and maximum atk/def like with HP
        mediaPlayer.stop()
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
            if (magicBuffDuration in 1..4) {
                magicBuffDuration -= 1
                println("the buff lasts for $magicBuffDuration turns")

            }
            if (attackBuffDuration in 1..4) {
                attackBuffDuration -= 1
                println("the buff lasts for $attackBuffDuration turns")
            }
            if (defenseBuffDuration in 1..4) {
                defenseBuffDuration -= 1
                println("the buff lasts for $attackBuffDuration turns")
            }
            if (critBuffDuration in 1..4) {
                defenseBuffDuration -= 1
                println("the buff lasts for $attackBuffDuration turns")
            }
            if (healAgainDuration > 0) {
                healAgainDuration -= 1
                println("the heal limit lasts for $healAgainDuration turns")
            }
            // THIS IS FOR JAGGER'S EXCLUSIVE MASSIVE BUFF JUST CHANGE THE ENEMYNAME FOR ENEMIES
            if (enemyName == "Jagger" && magicBuffDuration == 0) {
                // REPLACE THESE WITH REAL PERCENT DECREASES
                enemyMAtk = 125
                println("now that the buff is gone mag atk is $enemyMAtk")
                magicBuffDuration = 1999
            }
            if (enemyName == "Jagger" && attackBuffDuration == 0) {
                enemyAtk = 150
                println("now that the buff is gone  atk is $enemyAtk")
                attackBuffDuration = 1999
            }
            if (enemyName == "Jagger" && defenseBuffDuration == 0) {
                enemyDef = 100
                println("now that the buff is gone  atk is $enemyAtk")
                defenseBuffDuration = 1999
            }
            if (enemyName == "Crow" && magicBuffDuration == 0) {
                // REPLACE THESE WITH REAL PERCENT DECREASES
                enemyMAtk = 30
                println("now that the buff is gone mag atk is $enemyMAtk")
                magicBuffDuration = 1999
            }
            if (enemyName == "Crow" && attackBuffDuration == 0) {
                enemyAtk = 50
                println("now that the buff is gone  atk is $enemyAtk")
                attackBuffDuration = 1999
            }
            if (enemyName == "Crow" && defenseBuffDuration == 0) {
                enemyDef = 25
                println("now that the buff is gone  atk is $enemyAtk")
                defenseBuffDuration = 1999
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
            if (playerHealth <= 0) {
                playerHealth = 0
            }
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
            if (playerHealth <= 0) {
                playerHealth = 0
            }
            flavorText.text = ""
            bashButtonPressed.isEnabled = false
            psyButtonPressed.isEnabled = false
            itemButtonPressed.isEnabled = false
            runButtonPressed.isEnabled = false

            if (playerHealth < 1) {
                youLose()
            }
            if (enemyName == "Crow" && enemyHealth < 1) {
                    youWonTheGame()
                return
            }
            if (fightingTheFinalBoss == false && enemyHealth < 1) {
                youWin()
                return
            }
            if (enemyName == "Jagger" && enemyHealth < 1) {
                youWonTheGame()
                return
            }

            if (enemyHealth >= 1) {
                enemyTurnStart()
            }
        }


        //          run fail chance
        fun runCouldFail() {
            var failChance = Random.nextInt(100)
            if (failChance < 33) {
                if (runFailCounter == 0) {
                    runAway()
                }
                playerInfo.text = "RUN FAILED!!"
                playerInfo.isInvisible = false
                println("cuz the number was: $failChance")
                runFailCounter -= 1
                if (juiceLeft > 0) {
                    itemButtonPressed.isEnabled = true
                    itemButtonPressed.isClickable = true
                }
                println(runFailCounter)
                turnEnded()
            } else {
                runAway()
            }
        }

        // Healing Code (very hard to do)
        fun amountHealed() {


            if (enemyName == "Jagger") {
                playerHealth += 2500
                healthHealed = 2500
                playerInfo.text = "\t You used Chad Juice! \n \t You healed $healthHealed HP!"
            }
            if (enemyName == "Jagger" && playerHealth >= 5000) {
                playerHealth = 5000
                playerInfo.text = "\t You used Chad Juice! \n \t HP is max!"
            }
            if (enemyName == "Crow") {
                playerHealth += 250
                healthHealed = 250
                playerInfo.text = "\t You used Chad Juice! \n \t You healed $healthHealed HP!"
            }
            if (enemyName == "Crow" && playerHealth >= 500) {
                playerHealth = 500
                playerInfo.text = "\t You used Chad Juice! \n \t HP is max!"
            }
            if (fightingTheFinalBoss == false && loki == false) {
                playerHealth += 50
                healthHealed = 50
                playerInfo.text = "\t You used Chad Juice! \n \t You healed $healthHealed HP!"
            }
            if (playerHealth >= 50 && fightingTheFinalBoss == false && loki == false)  {
            playerHealth = 50
                playerInfo.text = "\t You used Chad Juice! \n \t HP is max!"
        }

            playerInfo.isInvisible = false
            playerHPTextView.text  = "HP: $playerHealth/$playerMaxHP"
            removeChadJuiceItem()
            turnEnded()


        }



        healthItem.setOnClickListener {
            if (playerHealth in 1..5000 && enemyName == "Jagger") {
                if (playerHealth >= 5000) {
                    println("max")
                } else {
                    amountHealed()
                }
            }
            if (playerHealth in 1..500 && enemyName == "Crow") {
                if (playerHealth >= 500) {
                    println("Already at max HP")
                } else {
                    amountHealed()
                }
            }
            if (playerHealth in 1..50 && fightingTheFinalBoss == false && loki == false) {
                if (playerHealth >= 50) {
                    println("Already at max HP")
                } else {
                    amountHealed()
                }
            }
        }


//  ??????

        persona.setOnClickListener {
            loki = true
            finalBattle()
            println("all of crow is in here")
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
            analyzeDefense.text = ""
            analyzeMagic.text = ""
            analyzeAttack.text = ""
            if (enemyName == "2Pac" && enemyHealth > 0 && playerHealth > 0) {
                runAway()
                return@setOnClickListener
            }
            if (enemyName == "Pepe" && enemyHealth > 0 && playerHealth > 0) {
                playerInfo.text = "You cannot escape."
                if (juiceLeft > 0) {
                    itemButtonPressed.isEnabled = true
                    itemButtonPressed.isClickable = true
                }
                playerInfo.isInvisible = false
                return@setOnClickListener
            }
            if (enemyName == "Jagger" && fightingTheFinalBoss == true) {
                println("You can't run now.")
                playerInfo.text = "You can't run now."
                if (juiceLeft > 0) {
                    itemButtonPressed.isEnabled = true
                    itemButtonPressed.isClickable = true
                }
                playerInfo.isInvisible = false
                return@setOnClickListener
            }
            if (enemyName == "Crow" && loki == true) {
                println("You can't run now.")
                playerInfo.text = "You cannot run"
                if (juiceLeft > 0) {
                    itemButtonPressed.isEnabled = true
                    itemButtonPressed.isClickable = true
                }
                playerInfo.isInvisible = false
                return@setOnClickListener
            }
            if (playerHealth > 1 && enemyHealth > 1 && fightingTheFinalBoss == false) {
                runCouldFail()
                return@setOnClickListener
            }
            if (soulsTaken in 0..4 && fightingTheFinalBoss == false) {
                nextBattle()
                return@setOnClickListener
            }
            if (soulsTaken == 5) {
                finalBattle()
                return@setOnClickListener
            }
            if (soulsTaken == -1) {
                lostBattle()
                return@setOnClickListener
            }

        }

        dButton.setOnClickListener {
            soulsTaken = 5
            juiceLeft = 10
            playerAtk = 9999
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
                if (enemyName == "SoyBoy") {
                    analyzeAttack.setTextColor(Color.parseColor("#696969"))
                    analyzeDefense.setTextColor(Color.parseColor("#696969"))
                    analyzeHealth.setTextColor(Color.parseColor("#696969"))
                    analyzeMagic.setTextColor(Color.parseColor("#696969"))
                } else {

                    analyzeAttack.setTextColor(Color.parseColor("#FFFFFF"))
                    analyzeDefense.setTextColor(Color.parseColor("#FFFFFF"))
                    analyzeHealth.setTextColor(Color.parseColor("#FFFFFF"))
                    analyzeMagic.setTextColor(Color.parseColor("#FFFFFF"))

                }
                println("stuff happens")
                analyzeHealth.text = "HP: $enemyHealth"
                analyzeAttack.text = "ATK: $enemyAtk"
                analyzeDefense.text = "DEF: $enemyDef"
                analyzeMagic.text = "MAG: $enemyMAtk"
                if (enemyName == "2Pac") {
                    analyzeHealth.text = "HP:  ???"
                    analyzeAttack.text = "ATK: ???"
                    analyzeDefense.text = "DEF: ???"
                    analyzeMagic.text = "MAG: ???"
                }
                if (juiceLeft > 0) {
                    itemButtonPressed.isClickable = true
                    itemButtonPressed.isEnabled = true
                }

                healthItem.text = ""
            }




            // I FIGURED IT OUT
            // HOW TO REMOVE AN IMAGE
            // enemyImage.setImageResource(0)
            // HOW TO CHANGE
            // enemyImage.setImageResource(R.drawable.test)
        }

    }
}