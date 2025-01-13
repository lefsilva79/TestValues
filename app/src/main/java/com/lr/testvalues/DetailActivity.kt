package com.lr.testvalues

import android.os.Bundle
import android.widget.Button
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.ComponentActivity
import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import android.view.Gravity
import android.view.View
import android.view.ViewGroup.LayoutParams

class DetailActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val value = intent.getStringExtra("value") ?: "0.00"

        // Root Layout
        val rootLayout = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            layoutParams = LinearLayout.LayoutParams(
                LayoutParams.MATCH_PARENT,
                LayoutParams.MATCH_PARENT
            )
            setBackgroundColor(Color.WHITE)
        }

        // Valor TextView
        val valueText = TextView(this).apply {
            text = "$$value"
            textSize = 49f // 98px
            setTextColor(Color.BLACK)
            gravity = Gravity.CENTER
            typeface = Typeface.DEFAULT_BOLD
            layoutParams = LinearLayout.LayoutParams(
                LayoutParams.MATCH_PARENT,
                LayoutParams.WRAP_CONTENT
            ).apply {
                topMargin = 24
            }
        }
        rootLayout.addView(valueText)

        // Spacer
        val spacer = View(this).apply {
            layoutParams = LinearLayout.LayoutParams(
                LayoutParams.MATCH_PARENT,
                0,
                1f
            )
        }
        rootLayout.addView(spacer)

        // Frame Layout para o botão
        val buttonFrame = FrameLayout(this).apply {
            isClickable = true
            isEnabled = true
            isFocusable = true
            layoutParams = LinearLayout.LayoutParams(
                LayoutParams.MATCH_PARENT,
                172 // height em pixels
            ).apply {
                bottomMargin = 24
                // Adiciona margem horizontal para centralizar o FrameLayout
                marginStart = 41 // layout_x em pixels
                marginEnd = 41   // layout_x em pixels
            }

// Button interno
            val acceptButton = Button(this@DetailActivity).apply {
                text = "Accept"
                isClickable = true
                isEnabled = true
                isFocusable = true
                transformationMethod = null  // Impede que o texto seja convertido para maiúsculas
                setBackgroundColor(Color.parseColor("#43B02A"))
                setTextColor(Color.WHITE)
                textSize = 23.5f // 47px

                // Ajusta o layout do botão para preencher o FrameLayout
                layoutParams = FrameLayout.LayoutParams(
                    998, // width em pixels
                    142  // height em pixels
                ).apply {
                    gravity = Gravity.CENTER // Centraliza o botão dentro do FrameLayout
                }

                setOnClickListener {
                    val intent = Intent(this@DetailActivity, AcceptedOrderActivity::class.java).apply {
                        putExtra("value", value)
                    }
                    startActivity(intent)
                }
            }
            addView(acceptButton)
        }
        rootLayout.addView(buttonFrame)

        setContentView(rootLayout)
    }
}