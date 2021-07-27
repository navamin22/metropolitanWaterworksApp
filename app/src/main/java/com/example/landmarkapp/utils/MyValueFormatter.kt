
import com.github.mikephil.charting.formatter.ValueFormatter
import java.text.DecimalFormat

class MyValueFormatter : ValueFormatter() {
    private lateinit var mFormat: DecimalFormat

    override fun getFormattedValue(value: Float): String? {
        mFormat = DecimalFormat("###,###,##0")
        return mFormat.format(value).toString() + " คน"
    }

}