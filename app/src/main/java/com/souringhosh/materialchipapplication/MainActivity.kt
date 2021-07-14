package com.souringhosh.materialchipapplication

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.KeyEvent
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.chip.Chip
import com.google.android.material.textfield.TextInputEditText
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        horizontal_scroll_view.background = textInputEditText.background
        textInputEditText.background = null

        /*
        * Listening to text changes in the TextInputEdittext field and generate new chip on entering a comma */
        textInputEditText.addTextChangedListener(object: TextWatcher {

            override fun afterTextChanged(s: Editable?) {
                val trimmed = s.toString().trim { it <= ' ' }
                if (trimmed.length > 1 && trimmed.endsWith(",")) {
                    val chip = Chip(this@MainActivity)
                    chip.text = trimmed.substring(0, trimmed.length - 1)
                    chip.isCloseIconVisible = true
                    chip.setOnCloseIconClickListener {
                        chipGroup.removeView(chip)
                    }
                    chipGroup.addView(chip)
                    s?.clear()
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
        })

        /*
        * Deleting chips on pressing backspace on keypad*/
        textInputEditText.setOnKeyListener { _, _, event ->
            if (event != null && event.action == KeyEvent.ACTION_DOWN && event.keyCode == KeyEvent.KEYCODE_DEL) {
                if (textInputEditText.length() == 0 && chipGroup.childCount > 0) {
                    val chip = chipGroup.getChildAt(chipGroup.childCount - 1) as Chip
                    chipGroup.removeView(chip)
                }
            }
            false
        }

        setEmailTextAction()
    }

    private fun setEmailTextAction() {
        emailTextInputEditText.background = null

        /*
        * Deleting chips on pressing backspace on keypad*/
        emailTextInputEditText.setOnKeyListener { view, keyCode, event ->
            if (event != null && event.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_DEL) {
                if ((view as TextInputEditText).length() == 0 && emailChipGroup.childCount > 0) {
                    val chip = emailChipGroup.getChildAt(emailChipGroup.childCount - 1) as Chip
                    emailChipGroup.removeView(chip)
                }
            }
            if (event != null && event.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
                val s = emailTextInputEditText.text
                val trimmed = s.toString().trim { it <= ' ' }
                if (trimmed.length > 1) {
                    val chip = Chip(this@MainActivity)
                    chip.text = trimmed.substring(0, trimmed.length)
                    chip.isCloseIconVisible = true
                    chip.setOnCloseIconClickListener {
                        emailChipGroup.removeView(chip)
                    }
                    emailChipGroup.addView(chip)
                    s?.clear()
                }
            }
            false
        }
    }
}
