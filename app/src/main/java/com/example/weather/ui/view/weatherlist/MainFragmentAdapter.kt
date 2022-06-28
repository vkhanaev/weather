package com.example.weather.ui.view.weatherlist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.weather.R
import com.example.weather.domain.Weather
import com.example.weather.ui.view.details.OnItemViewClickListener

class MainFragmentAdapter(private var onItemViewClickListener: OnItemViewClickListener?) : RecyclerView.Adapter<MainFragmentAdapter.MainViewHolder>() {
    private var weatherData: List<Weather> = listOf() // список городов
    fun setWeather(data: List<Weather>) {
        weatherData = data  // обновляем список
        notifyDataSetChanged()  // уведомляем RecyclerView об изменении
    }

    // чтобы не возникало утечек памяти. Рекомендуется вызывать этот метод адаптера в методе onDestroy фрагмента MainFragment.
    fun removeListener() {
        onItemViewClickListener = null
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MainViewHolder {
        return MainViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.fragment_main_recycler_item, parent, false) as
                    View
        )
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        holder.bind(weatherData[position])
    }

    override fun getItemCount(): Int {
        return weatherData.size
    }

    inner class MainViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(weather: Weather) {
            itemView.findViewById<TextView>(R.id.mainFragmentRecyclerItemTextView).text =
                weather.city.name
            itemView.setOnClickListener {
                onItemViewClickListener?.onItemViewClick(weather)
                /*
                Toast.makeText(
                    itemView.context,
                    weather.city.name,
                    Toast.LENGTH_LONG
                ).show()
                */
            }
        }
    }

}
