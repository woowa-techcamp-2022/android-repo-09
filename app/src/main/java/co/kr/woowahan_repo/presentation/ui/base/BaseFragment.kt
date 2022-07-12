package co.kr.woowahan_repo.presentation.ui.base

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import timber.log.Timber

abstract class BaseFragment<T : ViewDataBinding> : Fragment() {
    companion object {
        fun newInstance() = this
    }

    private var _binding: T? = null
    protected val binding get() = _binding ?: error("Binding not Initialized")
    abstract val TAG: String
    abstract val layoutResId: Int

    override fun onAttach(context: Context) {
        super.onAttach(context)
        Timber.tag(TAG).i("onAttach")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        lifecycle.addObserver(DeartodayDefaultLifecycleObserver())
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Timber.tag(TAG).i("onCreateView")
        _binding = DataBindingUtil.inflate(layoutInflater, layoutResId, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Timber.tag(TAG).i("onViewCreated")
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        Timber.tag(TAG).i("onViewStateRestored")
    }


    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        Timber.tag(TAG).i("onSaveInstanceState")
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
        Timber.tag(TAG).i("onDestroyView")
    }

    override fun onDetach() {
        super.onDetach()
        Timber.tag(TAG).i("onDetach")
    }
}