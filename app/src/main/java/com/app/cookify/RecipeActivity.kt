package com.app.cookify

import android.annotation.SuppressLint
import android.graphics.Color
import android.graphics.PorterDuff
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.app.cookify.databinding.ActivityRecipeBinding
import com.bumptech.glide.Glide

class RecipeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRecipeBinding
    private var imgCrop = true

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRecipeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Glide.with(this).load(intent.getStringExtra("IMAGE")).into(binding.imgRecipeItem)

        binding.tvRecipeItemName.text = intent.getStringExtra("TITLE")

        binding.tvRecipeItemTime.text = intent.getStringExtra("TIME")

        binding.tvSteps.text = intent.getStringExtra("STEPS")

        val ingredients =
            intent.getStringExtra("INGREDIENTS")?.split("\n".toRegex())?.dropWhile { it.isEmpty() }
                ?.toTypedArray()

        for (i in 1 until ingredients!!.size) {
            binding.tvIngredients.text =
                """${binding.tvIngredients.text} 🟢 ${ingredients[i]}
                    
                """.trimIndent()
        }

        binding.btnSteps.background = null
        binding.btnSteps.setTextColor(getColor(R.color.black))
        binding.btnSteps.setOnClickListener {
            binding.btnSteps.background = AppCompatResources.getDrawable(this, R.drawable.btn_ing)
            binding.btnSteps.setTextColor(getColor(R.color.white))
            binding.btnIngredients.setTextColor(getColor(R.color.black))
            binding.btnIngredients.background = null
            binding.scrollViewSteps.visibility = View.VISIBLE
            binding.scrollViewIngredients.visibility = View.GONE
        }

        binding.btnIngredients.setOnClickListener {
            binding.btnIngredients.background =
                AppCompatResources.getDrawable(this, R.drawable.btn_ing)
            binding.btnIngredients.setTextColor(getColor(R.color.white))
            binding.btnSteps.setTextColor(getColor(R.color.black))
            binding.btnSteps.background = null
            binding.scrollViewIngredients.visibility = View.VISIBLE
            binding.scrollViewSteps.visibility = View.GONE
        }

        binding.imgZoom.setOnClickListener {
            if (imgCrop) {
                binding.imgRecipeItem.scaleType = ImageView.ScaleType.FIT_CENTER
                Glide.with(this).load(intent.getStringExtra("IMAGE")).into(binding.imgRecipeItem)
                binding.imgShade.visibility = View.GONE
                binding.imgZoom.setColorFilter(Color.BLACK, PorterDuff.Mode.SRC_ATOP)
                imgCrop = false
            } else {
                binding.imgRecipeItem.scaleType = ImageView.ScaleType.CENTER_CROP
                Glide.with(this).load(intent.getStringExtra("IMAGE")).into(binding.imgRecipeItem)
                binding.imgShade.visibility = View.VISIBLE
                binding.imgZoom.setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_ATOP)
                imgCrop = true
            }
        }

        binding.imgBackBtn.setOnClickListener {
            finish()
        }
    }
}