package com.example.workingwithapiweek6project

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class PokemonAdapter (private val pokemonList: MutableList<Triple<String, String, String>>) : RecyclerView.Adapter<PokemonAdapter.ViewHolder>() {
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val pokemonImage: ImageView
        val nameTextView = itemView.findViewById<TextView>(R.id.Pokemon_name)
        val abilityTextView = itemView.findViewById<TextView>(R.id.Pokemon_ability)

        init {
            pokemonImage = view.findViewById(R.id.pokemon_character)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.pokemon_images, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.pokemonImage.setOnClickListener{
            Toast.makeText(holder.itemView.context, "Pokemon at position ${position.toInt()+1} clicked.", Toast.LENGTH_SHORT).show()
        }

        holder.nameTextView.text = "Name : " + pokemonList[position].second
        holder.abilityTextView.text = "Ability : " + pokemonList[position].third

        Glide.with(holder.itemView)
            .load(pokemonList[position].first)
            .override(360,480)
            .fitCenter()
            .into(holder.pokemonImage)

    }

    override fun getItemCount(): Int {
        return pokemonList.size
    }
}