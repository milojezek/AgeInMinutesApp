package eu.milo4apps.ageinminutes

import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var tvSelectedDate: TextView
    private lateinit var tvAgeInMinutes: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val buttonSelectDate: Button = findViewById(R.id.button_date_picker)
        tvSelectedDate = findViewById(R.id.textview_selected_date)
        tvAgeInMinutes = findViewById(R.id.textview_age_in_minutes)

        buttonSelectDate.setOnClickListener {
            selectDate()
        }
    }

    private fun selectDate() {
        // Get a calendar using the default time zone and locale
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePicker = DatePickerDialog(
            this,
            {_, selectedYear, selectedMonth, selectedDayOfMonth ->

                // Format: dd.mm.yyyy; Months counted in 0-11!
                val selectedDate = "$selectedDayOfMonth.${selectedMonth + 1}.$selectedYear"
                tvSelectedDate.text = selectedDate

                // Use SimpleDateFormat so we can work with millis
                // lets { } used to avoid app crashing
                val sdf = SimpleDateFormat("dd.MM.yyyy", Locale.ENGLISH)
                val theDate = sdf.parse(selectedDate)
                theDate?.let {
                    val selectedDateInMinutes = theDate.time / 60000

                    val currentDate = sdf.parse(sdf.format(System.currentTimeMillis()))
                    currentDate?.let {
                        val currentDayInMinutes = currentDate.time / 60000

                        val differenceInMinutes = currentDayInMinutes - selectedDateInMinutes
                        tvAgeInMinutes.text = differenceInMinutes.toString()
                    }
                }
            },
            year,
            month,
            day
        )

        // Set maxDate so we can choose only from dates in the past
        datePicker.datePicker.maxDate = System.currentTimeMillis() - 86400000

        datePicker.show()
    }
}