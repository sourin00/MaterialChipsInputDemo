# MaterialChipsInputDemo
Using the new material library from google to implement Chips just like the Gmail app. Using commas to generate new chips from EditText inside ChipGroup and deleting them on close icon click or backspace press from soft keypad.

## Getting Started

Use this if you want to implement material chips without any 3rd party library dependency just using EditText, Chip, ChipGroup, HorizontalScrollView

### Prerequisites

Must have SDK 28 included in dependency either support library or androidX and material. 
I have used androidX and material

```
compileSdkVersion 28

implementation 'com.google.android.material:material:1.0.0-rc01'

```

### Implementation

Layout code

```
    <com.google.android.material.textfield.TextInputLayout
        android:id="@+id/textInputLayout"
        android:layout_width="0dp"
        android:layout_height="wrap_content"
        android:layout_marginStart="16dp"
        android:layout_marginTop="16dp"
        android:layout_marginEnd="16dp"
        android:hint="@string/enter_chip_txt"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintTop_toTopOf="parent">

        <!--Horizontal ScrollView to accomodate chips overflow by making them scroll-->

        <HorizontalScrollView
            android:id="@+id/horizontal_scroll_view"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:scrollbars="none">

            <androidx.constraintlayout.widget.ConstraintLayout
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"
                tools:layout_editor_absoluteX="8dp"
                tools:layout_editor_absoluteY="503dp">

                <!--ChipGroup to hold multiple chips and help in removal by getting child elements-->

                <com.google.android.material.chip.ChipGroup
                    android:id="@+id/chipGroup"
                    android:layout_width="wrap_content"
                    android:layout_height="wrap_content"
                    app:layout_constraintBottom_toBottomOf="@+id/textInputEditText"
                    app:layout_constraintEnd_toStartOf="@+id/textInputEditText"
                    app:layout_constraintStart_toStartOf="parent"
                    app:layout_constraintTop_toTopOf="@+id/textInputEditText" />

                <!--Entering chip generation text and using commas to detect when a new chip is to be generated-->

                <com.google.android.material.textfield.TextInputEditText
                    android:id="@+id/textInputEditText"
                    android:layout_width="wrap_content"
                    android:layout_height="wrap_content"
                    android:hint="@string/enter_chip_txt"
                    app:layout_constraintBottom_toBottomOf="parent"
                    app:layout_constraintEnd_toEndOf="parent"
                    app:layout_constraintStart_toEndOf="@+id/chipGroup"
                    app:layout_constraintTop_toTopOf="parent" />

            </androidx.constraintlayout.widget.ConstraintLayout>

        </HorizontalScrollView>
    </com.google.android.material.textfield.TextInputLayout>
    
```

TextWatcher for adding chips on enter comma

```
override fun afterTextChanged(s: Editable?) {
                val trimmed = s.toString().trim { it <= ' ' }
                if (trimmed.length > 1 && trimmed.endsWith(",")) {
                    val chip = Chip(this@MainActivity)
                    chip.text = trimmed.substring(0, trimmed.length - 1)
                    chip.isCloseIconVisible = true
                    
                    //Callback fired when chip close icon is clicked 
                    chip.setOnCloseIconClickListener {
                        chipGroup.removeView(chip)
                    }
                    
                    chipGroup.addView(chip)
                    s?.clear()
                }
            }
            
```
Deletion on backspace press 

```
textInputEditText.setOnKeyListener { _, _, event ->
            if (event != null && event.action == KeyEvent.ACTION_DOWN && event.keyCode == KeyEvent.KEYCODE_DEL) {
                if (textInputEditText.length() == 0 && chipGroup.childCount > 0) {
                    val chip = chipGroup.getChildAt(chipGroup.childCount - 1) as Chip
                    chipGroup.removeView(chip)
                }
            }
            false
        }

```
## Reference

[Material Chips](https://material.io/develop/android/components/chip/)

## Authors

* **Sourin Ghosh** - [Sourin Ghosh](https://github.com/sourin00)
