package industries.kyoudai.metrics

import android.graphics.Color
import android.graphics.Color.BLACK
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import kotlin.collections.ArrayList


/** This adapter:
 *  - takes in view in the MyViewHolder subclass
 *  - uses onBindViewHolder to bind those views to the MetricItem data class
 */
@Suppress("DEPRECATION")
class CustomAdapter(
    private val myList: ArrayList<MetricItem>,
    private val listener: OnItemClickListener
) : RecyclerView.Adapter<CustomAdapter.MyViewHolder>() {

    /** Constructor */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) : MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.recycler_row, parent, false)
        return MyViewHolder(view)

    }

    override fun getItemCount() = myList.size

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = myList[position]

        //holder.metric_id_txt.text = currentItem.metricID.toString()
        holder.metric_name_txt.text = currentItem.metricName
        //holder.metric_unit_txt.text = currentItem.metricUnit
        onBindChartViewHolder(holder, currentItem)

        //holder.metric_graph.addSeries(currentItem.graphSeries)
        //holder.metric_graph.draw(Canvas())
    }

    /** holds the views for which we want to give data to */
    inner class MyViewHolder(v: View) : RecyclerView.ViewHolder(v), OnClickListener{
       //var metric_id_txt : TextView = v.findViewById(R.id.metric_id_text)
        var metric_name_txt : TextView = v.findViewById(R.id.metric_name_text)
        //var metric_unit_txt : TextView = v.findViewById(R.id.metric_unit_text)
        var bar_chart : BarChart = v.findViewById(R.id.barChart)

        init {
            v.setOnClickListener(this)
        }

        override fun onClick(p0: View?) {
            listener.onItemClick(myList[adapterPosition])
        }
    }

    fun onBindChartViewHolder(holder: MyViewHolder, item: MetricItem) {
        val chart = holder.bar_chart

        var barDataSet = BarDataSet(item.chartDataList, "data list")
        barDataSet.color = Color.RED
        barDataSet.valueTextSize = 12f
        barDataSet.valueTextColor = BLACK

        var barData = BarData(barDataSet)

        //chart.setFitBars(false)
        chart.data = barData
        //chart.description.text = R.string.chart_description.toString()
        chart.animateY(500)
        //chart.setMaxVisibleValueCount(20)
        chart.setNoDataText("No Data")

        val xFormatter = DayAxisValueFormatter(chart)

        val xAxis = chart.xAxis
        xAxis.position = XAxis.XAxisPosition.BOTTOM
        xAxis.valueFormatter = xFormatter
        xAxis.setDrawAxisLine(false);
        xAxis.setDrawGridLines(false);
        xAxis.setDrawLabels(true)
        //var value = xAxis.valueFormatter.getAxisLabel(item.chartDataList[0].x, xAxis)
        //Log.i("Axis String", value)

        val yAxisLeft = chart.axisLeft
        yAxisLeft.setDrawAxisLine(false)
        //yAxisLeft.setDrawGridLines(false)
        //yAxisLeft.setDrawLabels(false)
        yAxisLeft.setDrawZeroLine(true)
        yAxisLeft.mAxisMinimum = 100.0F

        val yAxisRight = chart.axisRight
        yAxisRight.setDrawAxisLine(false)
        yAxisRight.setDrawGridLines(false)
        yAxisRight.setDrawLabels(false)
        //yAxisRight.setDrawZeroLine(false)

        val legend = chart.legend
        legend.isEnabled = false

        val description = chart.description
        description.isEnabled = false


        //chart.invalidate()

    }

    /** Interface to handle a click on a metric item */
    interface OnItemClickListener {
        fun onItemClick(item: MetricItem)
    }
}
