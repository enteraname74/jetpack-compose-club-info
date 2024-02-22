package com.github.enteraname74.clubinfo

import android.content.SharedPreferences
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.github.enteraname74.clubinfo.composable.AppBar
import com.github.enteraname74.clubinfo.ui.theme.ClubInfoJetpackComposeTheme

// Une activité représente une page visible pour l'utilisateur.
class MainActivity : ComponentActivity() {
    /*
    Les shared preferences permettent de stocker des données simples (entier, string, booléen)
    en mode clé-valeur (comme un dictionnaire en python).

    On peut noter l'utilisation du mot clé lateinit, qui permet de dire qu'une variable n'est pas
    encore définie et qu'elle le sera plus tard (late init).
     */
    private lateinit var sharedPreferences: SharedPreferences

    /*
    Cette fonction est appelée automatiquement dès que l'activité se lance (dans notre cas,
    dès le lancement de l'application car MainActivity se lance dès le lancement de l'appli.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // On récupère une instance de shared preferences.
        sharedPreferences = applicationContext.getSharedPreferences("Stockage", MODE_PRIVATE)

        setContent {

            /*
            Pour permettre à Jetpack Compose de modifier une vue en fonction de l'état d'une variable,
            nous utilisons la structure suivante.
            le mot clé remember permet de dire à Jetpack Compose de se souvenir de la valeur de la variable
            lorsque la vue se recompose.
            L'élement mutableIntStateOf indique que la variable counter est un entier (int) mutable (qui
            peut donc changer).
             */
            var counter by remember {
                mutableIntStateOf(sharedPreferences.getInt("COUNTER", 0))
            }

            // On peut voir ici comment la hiérachie de vue est gérée par jetpack compose.
            ClubInfoJetpackComposeTheme {
                Scaffold(
                    topBar = {
                        AppBar()
                    }
                ) { padding ->
                    Column(
                        /*
                        l'élément Modifier est comme un "fourre-tout".
                        On peut faire énormement de choses avec.
                        De manière générale, on retrouve les mêmes éléments qu'en CSS, pour faire simple.

                        Par exemple, ici on dit à notre Column de prendre toute l'espace disponible
                        (largeur et hauteur) et d'avoir une certaine valeur de padding,
                        donnée par l'élément Scaffold.
                         */
                        modifier = Modifier
                            .padding(padding)
                            .fillMaxSize(),
                        verticalArrangement = Arrangement.Bottom,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(text = "Valeur du compteur: $counter")
                        Button(onClick = {
                            /*
                            Changer la valeur du compteur forcera Jetpack Compose à recomposer
                            la vue de l'appli pour se mettre à jour et afficher la valeur à jour du
                            compteur.
                             */
                            counter += 1
                            // On met à jour la valeur du compteur dans le stockage de l'appli.
                            sharedPreferences.edit().putInt("COUNTER", counter).apply()
                        }) {
                            Text("Incrémenter")
                        }
                    }
                }
            }
        }
    }
}

/*
On créer un Composable (élément de vue) en faisant une fonction (mot clé fun en Kotlin, soulignant
à quel point ce langage est amusant) avec l'annotation @Composable, permettant d'indiquer que cette
fonction est utilisée pour afficher une vue.
 */
@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    /*
    On remarque que les éléments de vues classiques proposées par Jetpack Compose sont très
    représentatifs de leur fonction (Button pour un bouton, Text pour du text...)
     */
    Button(onClick = {  }) {
        Text(
            text = "Hello $name!",
            modifier = modifier
        )
    }
}