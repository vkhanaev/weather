package com.example.weather.ui.main

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import com.example.weather.R

class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    private lateinit var viewModel: MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.main_fragment, container, false)
    }

     // Заменили onActivityCreated на onViewCreated
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Подключили viewModel
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        // Добавили observer, что такое it?
        // Получили LiveData, вызвали observe
        //val observer = Observer<Any> { renderData(it) }
        //val LiveData = viewModel.getData()
        //LiveData.observe(viewLifecycleOwner, observer)  // Теперь, если данные, которые хранит LiveData, изменятся, Observer сразу об этом узнает и вызовет метод renderData, куда передаст новые данные.

        // Подписываемся, Any - тип объекта в liveDataToObserve
        viewModel.liveDataToObserve.observe(viewLifecycleOwner, object : Observer<Any> {
            override fun onChanged(t: Any?) {
                Toast.makeText(context, "Работает $t", Toast.LENGTH_LONG).show()
            }

        })

        // Отправляем запрос на обновление данных
        viewModel.getData()
    }
    private fun renderData(data: Any) {
        Toast.makeText(context, "data", Toast.LENGTH_LONG).show()
    }



}