package com.lr.testvalues

import android.os.Bundle
import android.widget.Button
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.ComponentActivity
import android.graphics.Color
import android.graphics.Typeface
import android.view.Gravity
import android.view.View
import android.view.ViewGroup.LayoutParams

class AcceptedOrderActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN
        actionBar?.hide()

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

        // Mensagem de sucesso no topo
        val successText = TextView(this).apply {
            text = "Batch Accepted!"
            textSize = 32f // 64px
            setTextColor(Color.parseColor("#43B02A"))
            gravity = Gravity.CENTER
            typeface = Typeface.DEFAULT_BOLD
            layoutParams = LinearLayout.LayoutParams(
                LayoutParams.MATCH_PARENT,
                LayoutParams.WRAP_CONTENT
            ).apply {
                topMargin = 24
            }
        }
        rootLayout.addView(successText)

        // Spacer
        val topSpacer = View(this).apply {
            layoutParams = LinearLayout.LayoutParams(
                LayoutParams.MATCH_PARENT,
                0,
                1f
            )
        }
        rootLayout.addView(topSpacer)

        // Valor no meio
        val valueText = TextView(this).apply {
            text = "$$value"
            textSize = 49f // 98px
            setTextColor(Color.BLACK)
            gravity = Gravity.CENTER
            typeface = Typeface.DEFAULT_BOLD
            layoutParams = LinearLayout.LayoutParams(
                LayoutParams.MATCH_PARENT,
                LayoutParams.WRAP_CONTENT
            )
        }
        rootLayout.addView(valueText)

        // Spacer inferior
        val bottomSpacer = View(this).apply {
            layoutParams = LinearLayout.LayoutParams(
                LayoutParams.MATCH_PARENT,
                0,
                1f
            )
        }
        rootLayout.addView(bottomSpacer)

        // Frame Layout para o botão
        val buttonFrame = FrameLayout(this).apply {
            layoutParams = LinearLayout.LayoutParams(
                LayoutParams.MATCH_PARENT,
                172 // height em pixels
            ).apply {
                bottomMargin = 24
                marginStart = 41
                marginEnd = 41
            }

            // Button Start Shopping
            val startShoppingButton = Button(this@AcceptedOrderActivity).apply {
                text = "Start Shopping"
                isClickable = true
                isEnabled = true
                isFocusable = true
                transformationMethod = null
                setBackgroundColor(Color.parseColor("#43B02A"))
                setTextColor(Color.WHITE)
                textSize = 23.5f // 47px
                typeface = Typeface.DEFAULT_BOLD

                layoutParams = FrameLayout.LayoutParams(
                    998, // width em pixels
                    142  // height em pixels
                ).apply {
                    gravity = Gravity.CENTER
                }
            }
            addView(startShoppingButton)
        }
        rootLayout.addView(buttonFrame)

        setContentView(rootLayout)
    }
}