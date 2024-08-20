package com.example.healthylivingapp

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.ColorMatrix
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.lab4_moviles.ui.theme.Lab4_MovilesTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Lab4_MovilesTheme {
                RecipeInputScreen()
            }
        }
    }
}

@Composable
fun RecipeInputScreen() {
    val context = LocalContext.current
    val recipes = remember { mutableStateListOf<Recipe>() }
    var recipeName by remember { mutableStateOf("") }
    var recipeImageUrl by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        OutlinedTextField(
            value = recipeName,
            onValueChange = { recipeName = it },
            label = { Text("Nombre de la Receta") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = recipeImageUrl,
            onValueChange = { recipeImageUrl = it },
            label = { Text("URL de Imagen") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                if (recipeName.isNotEmpty() && recipeImageUrl.isNotEmpty()) {
                    recipes.add(Recipe(recipeName, recipeImageUrl))
                    recipeName = ""
                    recipeImageUrl = ""
                } else {
                    Toast.makeText(context, "Por favor, llena ambos campos", Toast.LENGTH_SHORT).show()
                }
            }
        ) {
            Text("Agregar")
        }

        Spacer(modifier = Modifier.height(16.dp))

        RecipeList(recipes)
    }
}

@Composable
fun RecipeList(recipes: List<Recipe>) {
    LazyColumn {
        items(recipes.size) { index ->
            RecipeItem(recipe = recipes[index])
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}

@Composable
fun RecipeItem(recipe: Recipe) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = recipe.name, style = MaterialTheme.typography.bodyLarge)

        AsyncImage(
            model = recipe.imageUrl,
            contentDescription = "Imagen de la Receta",
            modifier = Modifier.size(64.dp)
        )
    }
}

data class Recipe(val name: String, val imageUrl: String)

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    Lab4_MovilesTheme {
        RecipeInputScreen()
    }
}
