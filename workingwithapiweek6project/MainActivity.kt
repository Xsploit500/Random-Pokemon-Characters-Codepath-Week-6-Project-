package com.example.workingwithapiweek6project

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.codepath.asynchttpclient.AsyncHttpClient
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler
import okhttp3.Headers


class MainActivity : AppCompatActivity() {

    private lateinit var pokemonList: MutableList<Triple<String,String,String>>
    private lateinit var rvPokemon: RecyclerView

    var pokemonImageURl = ""
    var pokemonNameURL = ""
    var pokemonAbilityURL = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        rvPokemon = findViewById(R.id.pokemon_list)
        pokemonList = mutableListOf()

        for (i in 0 until 100) {
            getPokemonURL()
        }

    }

    private fun getPokemonURL() {
        val client = AsyncHttpClient()
        val randInt = (1 until 898).random()
        val imageUrl = "https://pokeapi.co/api/v2/pokemon/$randInt"

        client[imageUrl, object : JsonHttpResponseHandler() {
            override fun onSuccess(statusCode: Int, headers: Headers?, json: JsonHttpResponseHandler.JSON) {

                pokemonImageURl = json.jsonObject.getJSONObject("sprites").getJSONObject("other")
                    .getJSONObject("official-artwork").getString("front_default")
                Log.d("Pokemon Character Success", "Response Successful!")

                pokemonNameURL = json.jsonObject.getString("name").replaceFirstChar { it.titlecase() }
                Log.d("PokeAPI", "Retrieval Successful")
                Log.d("Pokemon Name", pokemonNameURL)

                pokemonAbilityURL = json.jsonObject.getJSONArray("abilities").getJSONObject(0).
                getJSONObject("ability").getString("name").replaceFirstChar { it.titlecase() }
                Log.d("PokeAPI ability", pokemonAbilityURL)

                pokemonList.add(Triple(pokemonImageURl, pokemonNameURL, pokemonAbilityURL))

                rvPokemon.adapter = PokemonAdapter(pokemonList)

                rvPokemon.layoutManager = LinearLayoutManager(this@MainActivity, LinearLayoutManager.VERTICAL, false)

                rvPokemon.addItemDecoration(DividerItemDecoration(this@MainActivity, LinearLayoutManager.VERTICAL))

                Log.d("PokemonAPI list", pokemonList.toString())

            }

            override fun onFailure(
                statusCode: Int,
                headers: Headers?,
                response: String?,
                throwable: Throwable?
            ) {
                Log.d("Pokemon Character Fail", "Error fetching image!")
            }

        }]
    }

    class PokemonAdapter(view: View) : RecyclerView.ViewHolder(view) {
        private val pokemonImage: ImageView

        init {
            pokemonImage = view.findViewById(R.id.pokemon_character)
        }
    }
}
