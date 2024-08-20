package com.example.healthylivingapp

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
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
    var receta by remember { mutableStateOf("") }
    var imageUrl by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        OutlinedTextField(
            value = receta,
            onValueChange = { receta = it },
            label = { Text("Nombre de la Receta") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(10.dp))

        OutlinedTextField(
            value = imageUrl,
            onValueChange = { imageUrl = it },
            label = { Text("URL de Imagen") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                if (receta.isNotEmpty() && imageUrl.isNotEmpty()) {
                    recipes.add(Recipe(receta, imageUrl))
                    receta = ""
                    imageUrl = ""
                } else {
                    Toast.makeText(context, "Por favor, llena ambos campos", Toast.LENGTH_SHORT).show()
                }
            }
        ) {
            Text("Receta!")
        }

        Spacer(modifier = Modifier.height(16.dp))

        RecipeList(recipes)
    }
}

@Composable
fun RecipeList(recipes: MutableList<Recipe>) {
    LazyColumn {
        items(recipes.size) { index ->
            RecipeItem(
                recipe = recipes[index],
                onDelete = {
                    recipes.removeAt(index)
                }
            )
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}

@Composable
fun RecipeItem(recipe: Recipe, onDelete: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = recipe.name, style = MaterialTheme.typography.bodyLarge)

        AsyncImage(
            model = recipe.imageUrl,
            contentDescription = "Imagen de la Receta",
            modifier = Modifier
                .size(100.dp)
                .clickable {
                    onDelete()
                }
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