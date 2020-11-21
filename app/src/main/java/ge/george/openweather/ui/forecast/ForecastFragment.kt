package ge.george.openweather.ui.forecast

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ge.george.openweather.R

class ForecastFragmeny : Fragment() {

    companion object {
        fun newInstance() = ForecastFragmeny()
    }

    private lateinit var viewModel: ForecastFragmenyViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.forecast_fragmeny_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(ForecastFragmenyViewModel::class.java)
        // TODO: Use the ViewModel
    }

}